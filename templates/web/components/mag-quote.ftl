 <#import "/templates/system/common/crafter.ftl" as crafter />
 
<section id="${contentModel.sectionId_s}" class="parallax-section">
  <div class="container">
    <div class="content-row">
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
    </div>
    </div>
</section>