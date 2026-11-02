package it.ytnoos.lpx.api;

import it.ytnoos.lpx.api.adapter.InventoryViewAdapter;
import it.ytnoos.lpx.api.check.abstraction.CheckManager;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;

import org.bukkit.entity.Player;

import java.util.logging.Logger;

public interface LPX {

    String getServer();

    LPXPlugin getPlugin();

    PlayerRegistry getRegistry();

    CheckManager getCheckManager();

    void disable(boolean matches);

    void reloadConfig();

    default Logger getLogger() {
        return this.getPlugin().getLogger();
    }

    InventoryViewAdapter getInventoryViewAdapter(Player player);

    boolean isUpdated();

    void setUpdated(boolean matches);

    long getFirstJoinTime();

    void setFirstJoinTime(long value);

    boolean canPacketLogHeavy();
}
