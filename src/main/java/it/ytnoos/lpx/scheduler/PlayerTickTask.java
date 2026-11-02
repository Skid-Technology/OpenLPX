package it.ytnoos.lpx.scheduler;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.CheckContainer;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

public class PlayerTickTask extends BukkitRunnable {

    private final LPXBukkit lpx;

    public PlayerTickTask(LPXBukkit lPXBukkit) {
        this.lpx = lPXBukkit;
    }

    public void run() {
        Iterator<ProtocolPlayer> iterator2 =
                this.lpx.getRegistry().getPlayers().values().iterator();
        while (iterator2.hasNext()) {
            Iterator<CheckContainer<?>> iterator3 =
                    ((ProtocolPlayer) iterator2.next()).getChecks().values().iterator();
            while (iterator3.hasNext()) {
                for (Check check :
                        (Iterable<Check>) ((Iterable) (CheckContainer) iterator3.next())) {
                    check.getOptions().setVl(0);
                    check.getOptions().setBuffer(0.0);
                }
            }
        }
    }
}
