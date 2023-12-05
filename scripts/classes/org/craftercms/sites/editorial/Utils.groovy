package org.craftercms.sites.editorial

class Utils {

  private Utils() {
  }

  def getAvailableItems(items) {
    def availableItems = []
 
/*
    items.each { item ->

      def availableItem = siteItemService.getSiteItem(item.key.text)

      if (availableItem != null) {
        availableItems << availableItem
      }
    }
*/

    return availableItems
  }

}
