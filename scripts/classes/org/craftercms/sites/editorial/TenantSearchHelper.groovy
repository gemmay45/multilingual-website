/*
 * Copyright (C) 2007-2022 Crafter Software Corporation. All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.craftercms.sites.editorial

import org.opensearch.client.opensearch._types.SortOrder
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery
import org.opensearch.client.opensearch._types.query_dsl.Query
import org.opensearch.client.opensearch._types.query_dsl.TextQueryType
import org.opensearch.client.opensearch._types.analysis.Analyzer
import org.opensearch.client.opensearch.core.SearchRequest
import org.opensearch.client.opensearch.core.search.Highlight
import org.apache.commons.lang3.StringUtils
import org.craftercms.engine.service.UrlTransformationService
import org.craftercms.search.opensearch.client.OpenSearchClientWrapper

class TenantSearchHelper {

  static final String TENANT_CONTENT_TYPE = "/page/tenant"
  static final List<String> TENANT_SEARCH_FIELDS = [
    'name_s^1.5',
    'sections_o.item.section_html^1.0'
  ]
  static final String[] HIGHLIGHT_FIELDS = ["name_s", "sections_o.item.section_html"]
  static final int DEFAULT_START = 0
  static final int DEFAULT_ROWS = 10
  static final String MULTIPLE_VALUES_SEARCH_ANALYZER = Analyzer.Kind.Whitespace.jsonValue()

  OpenSearchClientWrapper searchClient
  UrlTransformationService urlTransformationService

  TenantSearchHelper(OpenSearchClientWrapper searchClient, UrlTransformationService urlTransformationService) {
    this.searchClient = searchClient
    this.urlTransformationService = urlTransformationService
  }

  def search(userTerm, categories, start = DEFAULT_START, rows = DEFAULT_ROWS) {
    def query = new BoolQuery.Builder()

    // Filter by content-type
    query.filter(q -> q
      .match(m -> m
        .field("content-type")
        .query(v -> v
          .stringValue(TENANT_CONTENT_TYPE)
        )
      )
    )
    if (categories) {
      // Filter by categories
      query.filter(getFieldQueryWithMultipleValues("categories_o.item.key", categories))
    }

    if (userTerm) {
      // Check if the user is requesting an exact match with quotes
      def matcher = userTerm =~ /.*("([^"]+)").*/
      if (matcher.matches()) {
        // Using must excludes any doc that doesn't match with the input from the user
        query.must(q -> q
          .multiMatch(m -> m
            .query(matcher.group(2))
            .fields(TENANT_SEARCH_FIELDS)
            .fuzzyTranspositions(false)
            .autoGenerateSynonymsPhraseQuery(false)
          )
        )

        // Remove the exact match to continue processing the user input
        userTerm = StringUtils.remove(userTerm, matcher.group(1))
      } else {
        // Exclude docs that do not have any optional matches
        query.minimumShouldMatch("1")
      }

      if (userTerm) {
        // Using should makes it optional and each additional match will increase the score of the doc
        query
          // Search for phrase matches including a wildcard at the end and increase the score for this match
          .should(q -> q
            .multiMatch(m -> m
              .query(userTerm)
              .fields(TENANT_SEARCH_FIELDS)
              .type(TextQueryType.PhrasePrefix)
              .boost(1.5f)
            )
          )
          // Search for matches on individual terms
          .should(q -> q
            .multiMatch(m -> m
              .query(userTerm)
              .fields(TENANT_SEARCH_FIELDS)
            )
          )
      }
    }

    def highlighter = new Highlight.Builder();
    HIGHLIGHT_FIELDS.each{ field -> highlighter.fields(field, f -> f ) }

    SearchRequest request = SearchRequest.of(r -> r
      .query(query.build()._toQuery())
      .from(start)
      .size(rows)
      .highlight(highlighter.build())
    )

    def result = searchClient.search(request, Map)

    if (result) {
      return processUserSearchResults(result)
    } else {
      return []
    }
  }

  def searchTenants(featured, categories, segments, start = DEFAULT_START, rows = DEFAULT_ROWS, additionalCriteria = null) {
    SearchRequest request = SearchRequest.of(r -> r
      .query(q -> q
        .bool(b -> {
          b.filter(f -> f
            .match(m -> m
              .field("content-type")
              .query(v -> v
                .stringValue(TENANT_CONTENT_TYPE)
              )
            )
          )
          if (featured) {
            b.filter(f -> f
              .term(t -> t
                .field("featured_b")
                .value(v -> v
                  .booleanValue(true)
                )
              )
            )
          }
          if (categories) {
            b.filter(getFieldQueryWithMultipleValues("categories_o.item.key", categories))
          }
          if (segments) {
            b.filter(getFieldQueryWithMultipleValues("segments_o.item.key", segments))
          }
          if (additionalCriteria) {
            b.filter(f -> f
              .queryString(s -> s
                .query(additionalCriteria)
              )
            )
          }
          return b
        })
      )
      .from(start)
      .size(rows)
      .sort(s -> s
        .field(f -> f
          .field("name_s")
          .order(SortOrder.Asc)
        )
      )
    )

    def result = searchClient.search(request, Map)

    if (result) {
      return processTenantListingResults(result)
    } else {
      return []
    }
  }

  private def processUserSearchResults(result) {
    def tenants = []
    def hits = result.hits().hits()

    if (hits) {
      hits.each {hit ->
        def doc = hit.source()
        def tenant = [:]
            tenant.id = doc.objectId
            tenant.objectId = doc.objectId
            tenant.path = doc.localId
            tenant.title = doc.name_s
            tenant.url = urlTransformationService.transform("storeUrlToRenderUrl", doc.localId)

        if (hit.highlight()) {
          def tenantHighlights = hit.highlight().values()
          if (tenantHighlights) {
              def highlightValues = []

              tenantHighlights.each { value ->
                  highlightValues.addAll(value)
              }

              tenant.highlight = StringUtils.join(highlightValues, "... ")
              tenant.highlight = StringUtils.strip(tenant.highlight)
          }
        }

        tenants << tenant
      }
    }

    return tenants
  }

  private def processTenantListingResults(result) {
    def tenants = []
    def documents = result.hits().hits()*.source()

    if (documents) {
      documents.each {doc ->
        def tenant = [:]
        tenant.id = doc.objectId
        tenant.objectId = doc.objectId
        tenant.path = doc.localId
        tenant.storeUrl = doc.localId
        tenant.title = doc.name_s
        tenant.summary = doc.summary_t
        tenant.url = urlTransformationService.transform("storeUrlToRenderUrl", doc.localId)
        tenant.image = doc.images_o.item[0].image_s
        tenant.tagline = doc.tagline_s
        
        
        def categories = doc.categories_o.item.value_smv

        def catCount = (doc.categories_o.item[1]) ? doc.categories_o.item.value_smv.size() : 1
        if (catCount==1) {
            tenant.categories = [categories]
        }else {
            tenant.categories = categories
        }
        
        tenants << tenant
      }
    }

    return tenants
  }

  private Query getFieldQueryWithMultipleValues(field, values) {
    if (values.class.isArray()) {
      values = values as List
    }

    if (values instanceof Iterable) {
      values = StringUtils.join((Iterable)values, " ") as String
    } else {
      values = values as String
    }

    return Query.of(q -> q
      .match(m -> m
        .field(field)
        .query(v -> v
          .stringValue(values)
        )
        .analyzer(MULTIPLE_VALUES_SEARCH_ANALYZER)
      )
    );
  }

}
