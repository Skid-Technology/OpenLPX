package it.ytnoos.lpx.inventories;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.inventories.smartinvs.SmartInventory;

public class MenuFactory {

    private final LPXBukkit lpx;

    public MenuFactory(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    public SmartInventory createSmartInventory() {
        LPXBukkit lPXBukkit = this.lpx;
        ViolationLogMenu violationLogMenu = new ViolationLogMenu(lPXBukkit);
        return violationLogMenu.getInventory();
    }

    public SmartInventory createSmartInventory2() {
        MainMenu mainMenu = new MainMenu(this.lpx);
        return mainMenu.getInventory();
    }
}
