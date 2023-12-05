import org.craftercms.sites.editorial.Utils


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



/*
static def getAvailableItems(sourceItems) {
        def count = 0
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
        return count;
  }


def count = getAvailableItems(contentModel.pageSections_o.item)
templateModel.count = count
*/