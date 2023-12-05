import org.craftercms.sites.editorial.Utils

def count = 0
def availableItems = []

    def dirs = contentModel.pageSections_o.item
    dirs.each { dir ->
            /*dirName = dir.getStoreName()*/
            
            def dirItem = siteItemService.getSiteItem(dir.key.text)
            if (dirItem != null) {
                count=count+1
                availableItems << dirItem
            }
            
    }

templateModel.count = count;
templateModel.availableItems = availableItems;
