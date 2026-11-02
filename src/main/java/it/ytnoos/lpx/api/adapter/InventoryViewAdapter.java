package it.ytnoos.lpx.api.adapter;

import org.bukkit.inventory.Inventory;

public interface InventoryViewAdapter {

    boolean isNull();

    String getType();

    Inventory getTopInventory();

    Inventory getBottomInventory();

    int countSlots();

    int convertSlot(int value);
}
