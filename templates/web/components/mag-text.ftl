 <#import "/templates/system/common/crafter.ftl" as crafter />
 
<section id="${contentModel.sectionId_s}" class="parallax-section">
  <div class="container">
    <div class="content-row">
        <div class="content-col">
            <@crafter.div
                $field="content_html"
                $index=index
              >
                ${contentModel.content_html}
            </@crafter.div>
        </div>
    </div>
    </div>
</section>
