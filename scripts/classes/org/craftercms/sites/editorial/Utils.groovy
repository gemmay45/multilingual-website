package org.craftercms.sites.editorial

class Utils {

  private Utils() {
  }

  static def getAvailableItems(items) {
    def availableItems = ["xyz"]
 
    items.each { item ->

      def availableItem = siteItemService.getSiteItem(item.key.text)

      if (availableItem) {
        availableItems << availableItem
      }
    }
    return availableItems
  }

}
