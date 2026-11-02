package it.ytnoos.lpx.commands;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;

import org.bukkit.entity.Player;

public class GuiCommand extends SubCommand {

    public GuiCommand(LPXBukkit lPXBukkit) {
        super(lPXBukkit, "gui", "lpx.gui.main", "Open GUI", true);
    }

    @Override
    public void execute(Object result, String[] texts) {
        LPXBukkit lPXBukkit = this.lpx;
        SmartInventory smartInventory = lPXBukkit.getMenuFactory().createSmartInventory2();
        Player player = (Player) result;
        smartInventory.createInventory(player);
    }
}
