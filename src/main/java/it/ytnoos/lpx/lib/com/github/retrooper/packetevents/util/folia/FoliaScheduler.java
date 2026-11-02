package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class FoliaScheduler {

    static final boolean isFolia;

    private static Class<? extends Event> regionizedServerInitEventClass;

    private static AsyncScheduler asyncScheduler;

    private static EntityScheduler entityScheduler;

    private static GlobalRegionScheduler globalRegionScheduler;

    private static RegionScheduler regionScheduler;

    public static boolean isFolia() {
        return isFolia;
    }

    public static AsyncScheduler getAsyncScheduler() {
        if (asyncScheduler == null) {
            asyncScheduler = new AsyncScheduler();
        }
        return asyncScheduler;
    }

    public static EntityScheduler getEntityScheduler() {
        if (entityScheduler == null) {
            entityScheduler = new EntityScheduler();
        }
        return entityScheduler;
    }

    public static GlobalRegionScheduler getGlobalRegionScheduler() {
        if (globalRegionScheduler == null) {
            globalRegionScheduler = new GlobalRegionScheduler();
        }
        return globalRegionScheduler;
    }

    public static RegionScheduler getRegionScheduler() {
        if (regionScheduler == null) {
            regionScheduler = new RegionScheduler();
        }
        return regionScheduler;
    }

    public static void runTaskOnInit(Plugin plugin, Runnable runnable) {
        if (!isFolia) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable);
        } else {
            PluginManager pluginManager = Bukkit.getServer().getPluginManager();
            Class<? extends Event> classValue = regionizedServerInitEventClass;
            Listener listener = new Listener() {};
            pluginManager.registerEvent(
                    classValue,
                    listener,
                    EventPriority.HIGHEST,
                    (result, result2) -> runnable.run(),
                    plugin);
        }
    }

    static {
        boolean matches;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            matches = true;
            regionizedServerInitEventClass =
                    (Class<? extends Event>)
                            Class.forName(
                                    "io.papermc.paper.threadedregions.RegionizedServerInitEvent");
        } catch (ClassNotFoundException classNotFoundException) {
            matches = false;
        }
        isFolia = matches;
    }
}
