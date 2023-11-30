<#import "/templates/system/common/crafter.ftl" as crafter />

<div class="content-row">
    <div class="content-col">
        <@crafter.div class="${contentModel.imagePosition_s}">
            <a href="#" data-toggle="modal" data-target=".theMagGallerySingleModal" class="theMagGalleryImageLink">
                <figure class="figure">
                    <@crafter.img $field="image_s" class="img-responsive" src=(contentModel.image_s) alt="" />
                    <div class="icon-enlargephoto"></div>
                </figure>
            </a>
        </@crafter.div>
        <@crafter.span $field="sideContent_html">
        ${contentModel.sideContent_html!""}
        </@crafter.span>
    </div>
</div>