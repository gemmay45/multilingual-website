import org.craftercms.sites.editorial.Utils

def count = 0
def dirName
def topNavItems = [:]
def siteDir = siteItemService.getSiteTree("/site/components/en/the-mag", 2)
if(siteDir) {
    def dirs = siteDir.childItems
    dirs.each { dir ->
    count=count+1
            dirName = dir.getStoreName()
            def dirItem = siteItemService.getSiteItem("/site/components/en/the-mag/${dirName}/2f7299fc-d17d-4de7-b99b-e019a228d25f.xml")
            if (dirItem != null) {
                def dirDisplayName = dirItem.queryValue('internal-name')
                   topNavItems.put(dirName, dirDisplayName)
            }
   }
}
templateModel.topNavItems = topNavItems;
templateModel.count = count;
templateModel.dirName = dirName;
