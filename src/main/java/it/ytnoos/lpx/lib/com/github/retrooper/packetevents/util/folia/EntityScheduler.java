package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EntityScheduler {

    private BukkitScheduler bukkitScheduler;

    protected EntityScheduler() {
        if (!FoliaScheduler.isFolia) {
            this.bukkitScheduler = Bukkit.getScheduler();
        }
    }

    public void execute(
            @NotNull Entity entity,
            @NotNull Plugin plugin,
            @NotNull Runnable runnable,
            @Nullable Runnable runnable2,
            long value) {
        if (!FoliaScheduler.isFolia) {
            this.bukkitScheduler.runTaskLater(plugin, runnable, value);
        } else {
            entity.getScheduler().execute(plugin, runnable, runnable2, value);
        }
    }

    public TaskWrapper run(
            @NotNull Entity entity,
            @NotNull Plugin plugin,
            @NotNull Consumer<Object> consumer,
            @Nullable Runnable runnable) {
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTask(plugin, () -> ((Consumer) consumer).accept(null)));
        } else {
            io.papermc.paper.threadedregions.scheduler.EntityScheduler scheduler2 =
                    entity.getScheduler();
            return new TaskWrapper(
                    scheduler2.run(plugin, result -> ((Consumer) consumer).accept(null), runnable));
        }
    }

    public TaskWrapper runDelayed(
            @NotNull Entity entity,
            @NotNull Plugin plugin,
            @NotNull Consumer<Object> consumer,
            @Nullable Runnable runnable,
            long value) {
        if (value < 1L) {
            value = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskLater(
                            plugin, () -> ((Consumer) consumer).accept(null), value));
        } else {
            io.papermc.paper.threadedregions.scheduler.EntityScheduler scheduler2 =
                    entity.getScheduler();
            return new TaskWrapper(
                    scheduler2.runDelayed(
                            plugin, result -> ((Consumer) consumer).accept(null), runnable, value));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Entity entity,
            @NotNull Plugin plugin,
            @NotNull Consumer<Object> consumer,
            @Nullable Runnable runnable,
            long value,
            long value2) {
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
            io.papermc.paper.threadedregions.scheduler.EntityScheduler scheduler2 =
                    entity.getScheduler();
            return new TaskWrapper(
                    scheduler2.runAtFixedRate(
                            plugin,
                            result -> ((Consumer) consumer).accept(null),
                            runnable,
                            value,
                            value2));
        }
    }
}
