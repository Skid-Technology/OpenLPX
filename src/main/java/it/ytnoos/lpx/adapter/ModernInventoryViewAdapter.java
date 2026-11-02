package it.ytnoos.lpx.adapter;

import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class ModernInventoryViewAdapter implements InventoryViewAdapter {

    private final InventoryView inventoryView;

    public ModernInventoryViewAdapter(Player player) {
        this.inventoryView = player.getOpenInventory();
    }

    public boolean isNull() {
        return this.inventoryView == null;
    }

    public String getType() {
        return this.inventoryView.getType().name();
    }

    public Inventory getTopInventory() {
        return this.inventoryView.getTopInventory();
    }

    public Inventory getBottomInventory() {
        return this.inventoryView.getBottomInventory();
    }

    public int countSlots() {
        return this.inventoryView.countSlots();
    }

    public int convertSlot(int value) {
        return this.inventoryView.convertSlot(value);
    }
}
