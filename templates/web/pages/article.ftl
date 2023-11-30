<#import "/templates/system/common/crafter.ftl" as crafter />

<!DOCTYPE HTML>
<!--
	Editorial by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html lang="en" data-craftercms-preview="${modePreview?c}">
<head>
	<#include "/templates/web/fragments/head.ftl">
	<@crafter.head/>
</head>
<body class="the-mag">
<@crafter.body_top/>

<!-- Wrapper -->
<div id="wrapper">
	<!-- Main -->
	<div id="main">

			<!-- Header -->
			<@renderComponent component = contentModel.header_o.item />

			<!-- Content -->
			<section>
        <div class="the-mag-detail__header">

          <@crafter.div class="the-mag-cat" $field="categories_s">
            <#list contentModel.categories_o.item as category>
                <@crafter.span $field="categories_o">${category.value_smv}</@crafter.span>
            </#list>
          </@crafter.div>
          
          <@crafter.h1 $field="subject_t">
            ${contentModel.subject_t!""}
          </@crafter.h1>
		</div>

		<div class="inner the-mag-detail__content the-mag-detail__main-content">
		    <@crafter.renderComponentCollection $field="pageSections_o" $model=contentModel/>
        
            <ul class="the-mag-detail__content__info">
                <#setting time_zone = siteConfig.getString("timeZone")>
                <li><i aria-hidden="true" class="fas fa-clock"> </i>
                <@crafter.span>
                    ${contentModel.date_dt?date?string['dd MMM yyyy']}
                </@crafter.span>
                </li>
            </ul>
                
            <p class="the-mag-detail__content__tags">
                <i aria-hidden="true" class="fas fa-tag"></i>
                <#list contentModel.categories_o.item as category>
                    <@crafter.a $field="categories_o" href="javascript:searchByTag('tag:${category.value_smv}')">
                        <@crafter.span $field="categories_o">${category.value_smv}</@crafter.span>
                    </@crafter.a>
                </#list>
                <#--
                <@crafter.span $field="categories_o">${category.value_smv}</@crafter.span>
                <@crafter.a $field="categories_s" href="javascript:searchByTag('tag:${contentModel.categories_s}')">
                    
                    ${contentModel.categories_s!""}
                </@crafter.a>
                -->
            </p>
        </div>
        
			</section>
		</div>
	</div>
                
	<#assign articleCategories = contentModel.queryValues("//categories_o/item/key")/>
	<#assign articlePath = contentModel.storeUrl />
	<#assign additionalModel = {"articleCategories": articleCategories, "articlePath": articlePath }/>

	<!-- Left Rail -->
	<@renderComponent component = contentModel.left_rail_o.item additionalModel = additionalModel />

</div>

<#include "/templates/web/fragments/scripts.ftl">

<@crafter.body_bottom/>
</body>
</html>
