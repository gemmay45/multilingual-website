import org.craftercms.sites.editorial.Utils

def count = 0
def availableItems
availableItems.item = []

    def dirs = contentModel.pageSections_o.item
    dirs.each { dir ->
        def dirItem = siteItemService.getSiteItem(dir.key.text)
        if (dirItem != null) {
            count=count+1
            availableItems.item << dirItem
        }
    }

templateModel.count = count;
templateModel.availableItems = availableItems;
