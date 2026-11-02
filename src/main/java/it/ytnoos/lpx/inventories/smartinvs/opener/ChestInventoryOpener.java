package it.ytnoos.lpx.inventories.smartinvs.opener;

import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestInventoryOpener implements InventoryOpener {

    @Override
    public Inventory open(SmartInventory smartInventory, Player player) {
        InventoryManager manager = smartInventory.getManager();
        int rows = smartInventory.getRows();
        int value = rows * smartInventory.getColumns();
        Inventory inventory = Bukkit.createInventory(player, value, smartInventory.getTitle());
        InventoryContents inventoryContents = (InventoryContents) manager.getContents(player).get();
        this.fill(inventory, inventoryContents);
        player.openInventory(inventory);
        return inventory;
    }

    @Override
    public boolean supports(Object result) {
        InventoryType inventoryType = (InventoryType) result;
        return inventoryType == InventoryType.CHEST || inventoryType == InventoryType.ENDER_CHEST;
    }
}
