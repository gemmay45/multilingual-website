<#import "/templates/system/common/crafter.ftl" as crafter />
 
<section id="${contentModel.sectionId_s}" class="parallax-section">
  <div class="container">
        <div class="content-row eq-height">
            <div class="content-col">
                <@crafter.div class="the-mag-quote ${contentModel.quoteStyle_s}">
                    <@crafter.div $field="quote_t" class="quote">
                        ${contentModel.quote_t}
                    </@crafter.div>
                    
                    <div class="hr"></div>
                                
                    <@crafter.div $field="quoteBy_s" class="author">
                        ${contentModel.quoteBy_s}
                    </@crafter.div>
                </@crafter.div>
            </div>
            <div class="content-col ">
                <a href="#" data-toggle="modal" data-target=".theMagGallerySingleModal" class="theMagGalleryImageLink">
                    <figure class="figure">
                        <@crafter.img
                    		$field="sideImage_s"
                    		class="figure-img img-responsive lazy lz-loaded"
                    		src=(contentModel.sideImage_s)
                    		$attributes={'data-ll-status': 'loaded'}
                    		alt=""
                    	/>
                                        <figcaption data-content=" " class="figure-caption">&nbsp;</figcaption>
                            <div class="icon-enlargephoto"></div>
                    </figure>
                </a>
            </div>
        </div>
    </div>
</section>