package org.craftercms.sites.editorial

class Utils {

    private Utils() {
    }

    private def getAvailableItems(sourceItems, siteItemService) {
        def count = 0
        def availableItems
        def items = []
        
        def dirs = sourceItems.item
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
