import org.craftercms.sites.editorial.Utils

/*
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

templateModel.count = count;
templateModel.availableItems = items;
*/


def getAvailableItems(sourceItems) {
    return 10
    
        /*def count = 0
        def availableItems
        def items = []
        
        def dirs = sourceItems
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
        return count;*/
  }


def count = Utils.getAvailableItems(contentModel.pageSections_o, siteItemService)
templateModel.count = count
