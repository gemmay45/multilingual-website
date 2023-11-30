
def recTenants = []
def relatedMags = []
def recs = contentModel.recommended_o.item
def rels = contentModel.relatedMAGArticle_o.item
recs.each { rec ->
    def recTenant = [:]
    
    def dirItem = siteItemService.getSiteItem(rec.key.text)
    
    recTenant.id = dirItem.objectId
    recTenant.objectId = dirItem.objectId
    recTenant.path = dirItem.localId
    recTenant.storeUrl = dirItem.storeUrl
    recTenant.url = urlTransformationService.transform("storeUrlToRenderUrl", recTenant.storeUrl)

    recTenant.title = dirItem.queryValue('name_s')
    recTenant.tagline = dirItem.queryValue('tagline_s')
    recTenant.images = dirItem.images_o

    recTenants << recTenant
}

rels.each { rel ->
    def relatedMag = [:]
    
    def dirItem = siteItemService.getSiteItem(rel.key.text)
    
    relatedMag.id = dirItem.objectId
    relatedMag.objectId = dirItem.objectId
    relatedMag.path = dirItem.localId
    relatedMag.storeUrl = dirItem.storeUrl
    relatedMag.url = urlTransformationService.transform("storeUrlToRenderUrl", relatedMag.storeUrl)

    relatedMag.title = dirItem.queryValue('subject_t')

    relatedMags << relatedMag
}


templateModel.recTenants = recTenants
templateModel.relatedMags = relatedMags
