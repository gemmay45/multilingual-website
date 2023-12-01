<#import "/templates/system/common/crafter.ftl" as crafter />

<section id="${contentModel.sectionId_s}" class="parallax-section">
    <div class="content-row">
            <div class="content-col hero">
                <a href="#" data-toggle="modal" data-target=".theMagGallerySingleModal" class="theMagGalleryImageLink">
                    <figure class="figure">
                        <@crafter.img $field="imageOnDesktop_s" class="figure-img img-responsive lazy lz-loading" alt="" src=(contentModel.imageOnDesktop_s)
                            $attributes={'data-ll-status': 'loading', 'data-large':(contentModel.imageOnDesktop_s), 'data-lazy':(contentModel.imageOnDesktop_s)} />
                            <div class="icon-enlargephoto"></div>
                    </figure>
                </a>
            </div>
    </div>
</section>