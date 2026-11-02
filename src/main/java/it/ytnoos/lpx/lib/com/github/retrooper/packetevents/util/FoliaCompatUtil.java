package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.function.Consumer;

@Deprecated
public class FoliaCompatUtil {

    private static boolean folia;

    private static BukkitScheduler bukkitScheduler;

    private static AsyncScheduler asyncScheduler;

    private static GlobalRegionScheduler globalRegionScheduler;

    private static Class<? extends Event> regionizedServerInitEventClass;

    public static boolean isFolia() {
        return folia;
    }

    public static void runTaskAsync(Plugin plugin, Runnable runnable) {
        if (!folia) {
            bukkitScheduler.runTaskAsynchronously(plugin, runnable);
        } else {
            asyncScheduler.runNow(plugin, result -> runnable.run());
        }
    }

    public static void runTaskTimerAsync(
            Plugin plugin, Consumer<Object> consumer, long value, long value2) {
        if (!folia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskTimerAsynchronously(
                    plugin, () -> ((Consumer) consumer).accept(null), value, value2);
        } else {
            globalRegionScheduler.runAtFixedRate(
                    plugin, result -> ((Consumer) consumer).accept(null), value, value2);
        }
    }

    public static void runTask(Plugin plugin, Consumer<Object> consumer) {
        if (!folia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTask(plugin, () -> ((Consumer) consumer).accept(null));
        } else {
            globalRegionScheduler.run(plugin, result -> ((Consumer) consumer).accept(null));
        }
    }

    public static void runTaskOnInit(Plugin plugin, Runnable runnable) {
        if (!folia) {
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

    public static void runTaskForEntity(
            Entity entity, Plugin plugin, Runnable runnable, Runnable runnable2, long value) {
        if (!folia) {
            Bukkit.getScheduler().runTaskLater(plugin, runnable, value);
        } else {
            entity.getScheduler().execute(plugin, runnable, runnable2, value);
        }
    }

    static {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
            asyncScheduler = Bukkit.getAsyncScheduler();
            globalRegionScheduler = Bukkit.getGlobalRegionScheduler();
            regionizedServerInitEventClass =
                    (Class<? extends Event>)
                            Class.forName(
                                    "io.papermc.paper.threadedregions.RegionizedServerInitEvent");
        } catch (ClassNotFoundException classNotFoundException) {
            folia = false;
            bukkitScheduler = Bukkit.getScheduler();
        }
    }
}
