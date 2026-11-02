package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RegionScheduler {

    private BukkitScheduler bukkitScheduler;

    private io.papermc.paper.threadedregions.scheduler.RegionScheduler regionScheduler;

    protected RegionScheduler() {
        if (FoliaScheduler.isFolia) {
            this.regionScheduler = Bukkit.getRegionScheduler();
        } else {
            this.bukkitScheduler = Bukkit.getScheduler();
        }
    }

    public void execute(
            @NotNull Plugin plugin,
            @NotNull World world,
            int value,
            int value2,
            @NotNull Runnable runnable) {
        if (!FoliaScheduler.isFolia) {
            this.bukkitScheduler.runTask(plugin, runnable);
        } else {
            this.regionScheduler.execute(plugin, world, value, value2, runnable);
        }
    }

    public void execute(
            @NotNull Plugin plugin, @NotNull Location location, @NotNull Runnable runnable) {
        if (!FoliaScheduler.isFolia) {
            Bukkit.getScheduler().runTask(plugin, runnable);
        } else {
            this.regionScheduler.execute(plugin, location, runnable);
        }
    }

    public TaskWrapper run(
            @NotNull Plugin plugin,
            @NotNull World world,
            int value,
            int value2,
            @NotNull Consumer<Object> consumer) {
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTask(plugin, () -> ((Consumer) consumer).accept(null)));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.run(
                            plugin,
                            world,
                            value,
                            value2,
                            result -> ((Consumer) consumer).accept(null)));
        }
    }

    public TaskWrapper run(
            @NotNull Plugin plugin,
            @NotNull Location location,
            @NotNull Consumer<Object> consumer) {
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTask(plugin, () -> ((Consumer) consumer).accept(null)));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.run(
                            plugin, location, result -> ((Consumer) consumer).accept(null)));
        }
    }

    public TaskWrapper runDelayed(
            @NotNull Plugin plugin,
            @NotNull World world,
            int value,
            int value2,
            @NotNull Consumer<Object> consumer,
            long value3) {
        if (value3 < 1L) {
            value3 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTaskLater(
                            plugin, () -> ((Consumer) consumer).accept(null), value3));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.runDelayed(
                            plugin,
                            world,
                            value,
                            value2,
                            result -> ((Consumer) consumer).accept(null),
                            value3));
        }
    }

    public TaskWrapper runDelayed(
            @NotNull Plugin plugin,
            @NotNull Location location,
            @NotNull Consumer<Object> consumer,
            long value) {
        if (value < 1L) {
            value = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTaskLater(
                            plugin, () -> ((Consumer) consumer).accept(null), value));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.runDelayed(
                            plugin, location, result -> ((Consumer) consumer).accept(null), value));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Plugin plugin,
            @NotNull World world,
            int value,
            int value2,
            @NotNull Consumer<Object> consumer,
            long value3,
            long value4) {
        if (value3 < 1L) {
            value3 = 1L;
        }
        if (value4 < 1L) {
            value4 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTaskTimer(
                            plugin, () -> ((Consumer) consumer).accept(null), value3, value4));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.runAtFixedRate(
                            plugin,
                            world,
                            value,
                            value2,
                            result -> ((Consumer) consumer).accept(null),
                            value3,
                            value4));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Plugin plugin,
            @NotNull Location location,
            @NotNull Consumer<Object> consumer,
            long value,
            long value2) {
        if (value < 1L) {
            value = 1L;
        }
        if (value2 < 1L) {
            value2 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            return new TaskWrapper(
                    scheduler.runTaskTimer(
                            plugin, () -> ((Consumer) consumer).accept(null), value, value2));
        } else {
            return new TaskWrapper(
                    this.regionScheduler.runAtFixedRate(
                            plugin,
                            location,
                            result -> ((Consumer) consumer).accept(null),
                            value,
                            value2));
        }
    }
}
