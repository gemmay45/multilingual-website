import org.craftercms.sites.editorial.Utils

def count = 0
def dirName = ""
def topNavItems = [:]
def availableItems = []
/*def siteDir = siteItemService.getSiteTree("/site/components/en/the-mag/mag-with-shared-cmp", 1)
if(siteDir) {*/
    def dirs = contentModel.pageSections_o.item
    dirs.each { dir ->
    count=count+1
            /*dirName = dir.getStoreName()*/
            /*
            def dirItem = siteItemService.getSiteItem(dir.key.text)
            if (dirItem != null) {
                availableItems << dirItem
            }
            */
   }
/*}
templateModel.topNavItems = topNavItems;*/
templateModel.count = count;
/*templateModel.dirName = dirName;*/
templateModel.availableItems = availableItems;
