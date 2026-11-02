package it.ytnoos.lpx.inventories.smartinvs.content;

import org.bukkit.entity.Player;

public interface InventoryProvider {

    void init(Player player, InventoryContents inventoryContents);
}
