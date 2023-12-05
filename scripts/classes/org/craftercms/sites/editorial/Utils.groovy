package org.craftercms.sites.editorial

class Utils {

  private Utils() {
  }

  def getAvailableItems(items) {
        def count = 0
        def availableItems
        def items = []
        
            def dirs = contentModel.pageSections_o.item
            dirs.each { dir ->
                def dirItem
                
                if (dir.component) {
        			dirItem = dir.component
                } else {
        				dirItem = siteItemService.getSiteItem(dir.key.text)
                }
            
                if (dirItem != null) {
                    count=count+1
                    items << dirItem
                }
            }
        
        return items;
  }

}
