package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GlobalRegionScheduler {

    private BukkitScheduler bukkitScheduler;

    private io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler globalRegionScheduler;

    protected GlobalRegionScheduler() {
        if (FoliaScheduler.isFolia) {
            this.globalRegionScheduler = Bukkit.getGlobalRegionScheduler();
        } else {
            this.bukkitScheduler = Bukkit.getScheduler();
        }
    }

    public void execute(@NotNull Plugin plugin, @NotNull Runnable runnable) {
        if (!FoliaScheduler.isFolia) {
            this.bukkitScheduler.runTask(plugin, runnable);
        } else {
            this.globalRegionScheduler.execute(plugin, runnable);
        }
    }

    public TaskWrapper run(@NotNull Plugin plugin, @NotNull Consumer<Object> consumer) {
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTask(plugin, () -> ((Consumer) consumer).accept(null)));
        } else {
            return new TaskWrapper(
                    this.globalRegionScheduler.run(
                            plugin, result -> ((Consumer) consumer).accept(null)));
        }
    }

    public TaskWrapper runDelayed(
            @NotNull Plugin plugin, @NotNull Consumer<Object> consumer, long value) {
        if (value < 1L) {
            value = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskLater(
                            plugin, () -> ((Consumer) consumer).accept(null), value));
        } else {
            return new TaskWrapper(
                    this.globalRegionScheduler.runDelayed(
                            plugin, result -> ((Consumer) consumer).accept(null), value));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Plugin plugin, @NotNull Consumer<Object> consumer, long value, long value2) {
        if (value < 1L) {
            value = 1L;
        }
        if (value2 < 1L) {
            value2 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskTimer(
                            plugin, () -> ((Consumer) consumer).accept(null), value, value2));
        } else {
            return new TaskWrapper(
                    this.globalRegionScheduler.runAtFixedRate(
                            plugin, result -> ((Consumer) consumer).accept(null), value, value2));
        }
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!FoliaScheduler.isFolia) {
            Bukkit.getScheduler().cancelTasks(plugin);
        } else {
            this.globalRegionScheduler.cancelTasks(plugin);
        }
    }
}
