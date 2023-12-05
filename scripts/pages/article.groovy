import org.craftercms.sites.editorial.Utils

def topNavItems = [:]
def siteDir = siteItemService.getSiteTree("/site/components/en/the-mag", 1)
if(siteDir) {
    def dirs = siteDir.childItems
    dirs.each { dir ->
            def dirName = dir.getStoreName()
            def dirItem = siteItemService.getSiteItem("/site/website/${dirName}/2f7299fc-d17d-4de7-b99b-e019a228d25f.xml")
            if (dirItem != null) {
                def dirDisplayName = dirItem.queryValue('internal-name')
                   topNavItems.put(dirName, dirDisplayName)
            }
   }
}
model.topNavItems = topNavItems;
