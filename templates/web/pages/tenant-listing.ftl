<#import "/templates/system/common/crafter.ftl" as crafter />

<!doctype html>
<html lang="en">
<head>
  <#include "/templates/web/fragments/head.ftl">
  <@crafter.head/>
</head>
<body class="is-preload">
<@crafter.body_top/>

<!-- Wrapper -->
<div id="wrapper">

  <!-- Main -->
  <div id="main">
    <div class="inner">

      <!-- Header -->
      <@crafter.renderComponentCollection $field="header_o"/>
      <!-- /Header -->
      
      
      <@crafter.section $model=contentModel>
        <header class="main">
          <h1>${contentModel.tenantsTitle_s}</h1>
        </header>
        
        <div class="posts">
          <#list tenants as tenant>
            <@crafter.article $model=tenant>
                <a href="${tenant.url}" class="image">
                <@crafter.img
                  $model=tenant
                  $field="images_o"
                  src=tenant.image???then(tenant.image, "/static-assets/images/placeholder.png")
                  alt="" width="320px"
                />
                </a>
                    
                <#list tenant.categories as category>
                    [<@crafter.span $model=tenant $field="categories_o">${category}</@crafter.span>]
                </#list>
                
                <h3>
                    <@crafter.a $model=tenant $field="name_s" href="${tenant.url}">
                        ${tenant.title}
                    </@crafter.a>
                </h3>
                <@crafter.p $model=tenant $field="tagline_s">
                    ${tenant.tagline}
                </@crafter.p>
        
            </@crafter.article>
          </#list>
        </div>
      </@crafter.section>

    </div>
  </div>
  <!-- /Main -->

  <!-- Left Rail -->
  <@crafter.renderComponentCollection $field="left_rail_o" />
  <!-- /Left Rail -->

</div>
<!-- /Wrapper -->

<#include "/templates/web/fragments/scripts.ftl">
<@crafter.body_bottom/>

</body>
</html>
