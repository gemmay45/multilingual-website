import org.craftercms.sites.editorial.Utils

def count = 0
def availableItems = []

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

templateModel.count = count;
templateModel.availableItems = availableItems;
