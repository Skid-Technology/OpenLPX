package it.ytnoos.lpx.inventories.smartinvs.opener;

import it.ytnoos.lpx.inventories.smartinvs.ClickableItem;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener {

    Inventory open(SmartInventory smartInventory, Player player);

    boolean supports(Object result);

    default void fill(Inventory inventory, InventoryContents inventoryContents) {
        ClickableItem[][] clickableItems = inventoryContents.all();
        for (int index = 0; index < clickableItems.length; index++) {
            for (int index2 = 0; index2 < clickableItems[index].length; index2++) {
                if (clickableItems[index][index2] != null) {
                    int value = 9 * index + index2;
                    ClickableItem clickableItem = clickableItems[index][index2];
                    inventory.setItem(value, clickableItem.getItem());
                }
            }
        }
    }
}
