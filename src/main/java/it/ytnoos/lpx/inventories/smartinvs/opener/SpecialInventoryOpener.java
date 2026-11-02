package it.ytnoos.lpx.inventories.smartinvs.opener;

import com.google.common.collect.ImmutableList;

import it.ytnoos.lpx.inventories.smartinvs.InventoryManager;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;
import it.ytnoos.lpx.inventories.smartinvs.content.InventoryContents;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

    private static final List<InventoryType> VALUES =
            ImmutableList.of(
                    InventoryType.FURNACE,
                    InventoryType.WORKBENCH,
                    InventoryType.DISPENSER,
                    InventoryType.DROPPER,
                    InventoryType.ENCHANTING,
                    InventoryType.BREWING,
                    InventoryType.ANVIL,
                    InventoryType.BEACON,
                    InventoryType.HOPPER);

    @Override
    public Inventory open(SmartInventory smartInventory, Player player) {
        InventoryManager manager = smartInventory.getManager();
        InventoryType type = smartInventory.getType();
        Inventory inventory = Bukkit.createInventory(player, type, smartInventory.getTitle());
        InventoryContents inventoryContents = (InventoryContents) manager.getContents(player).get();
        this.fill(inventory, inventoryContents);
        player.openInventory(inventory);
        return inventory;
    }

    @Override
    public boolean supports(Object result) {
        return VALUES.contains(result);
    }
}
