package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class AsyncScheduler {

    private BukkitScheduler bukkitScheduler;

    private io.papermc.paper.threadedregions.scheduler.AsyncScheduler asyncScheduler;

    protected AsyncScheduler() {
        if (FoliaScheduler.isFolia) {
            this.asyncScheduler = Bukkit.getAsyncScheduler();
        } else {
            this.bukkitScheduler = Bukkit.getScheduler();
        }
    }

    public TaskWrapper runNow(@NotNull Plugin plugin, @NotNull Consumer<Object> consumer) {
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskAsynchronously(
                            plugin, () -> ((Consumer) consumer).accept(null)));
        } else {
            return new TaskWrapper(
                    this.asyncScheduler.runNow(
                            plugin, result -> ((Consumer) consumer).accept(null)));
        }
    }

    public TaskWrapper runDelayed(
            @NotNull Plugin plugin,
            @NotNull Consumer<Object> consumer,
            long value,
            @NotNull TimeUnit timeUnit) {
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskLaterAsynchronously(
                            plugin,
                            () -> ((Consumer) consumer).accept(null),
                            this.convertTimeToTicks(value, timeUnit)));
        } else {
            return new TaskWrapper(
                    this.asyncScheduler.runDelayed(
                            plugin, result -> ((Consumer) consumer).accept(null), value, timeUnit));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Plugin plugin,
            @NotNull Consumer<Object> consumer,
            long value,
            long value2,
            @NotNull TimeUnit timeUnit) {
        if (value2 < 1L) {
            value2 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskTimerAsynchronously(
                            plugin,
                            () -> ((Consumer) consumer).accept(null),
                            this.convertTimeToTicks(value, timeUnit),
                            this.convertTimeToTicks(value2, timeUnit)));
        } else {
            return new TaskWrapper(
                    this.asyncScheduler.runAtFixedRate(
                            plugin,
                            result -> ((Consumer) consumer).accept(null),
                            value,
                            value2,
                            timeUnit));
        }
    }

    public TaskWrapper runAtFixedRate(
            @NotNull Plugin plugin, @NotNull Consumer<Object> consumer, long value, long value2) {
        if (value2 < 1L) {
            value2 = 1L;
        }
        if (!FoliaScheduler.isFolia) {
            return new TaskWrapper(
                    this.bukkitScheduler.runTaskTimerAsynchronously(
                            plugin, () -> ((Consumer) consumer).accept(null), value, value2));
        } else {
            return new TaskWrapper(
                    this.asyncScheduler.runAtFixedRate(
                            plugin,
                            result -> ((Consumer) consumer).accept(null),
                            value * 50L,
                            value2 * 50L,
                            TimeUnit.MILLISECONDS));
        }
    }

    public void cancel(@NotNull Plugin plugin) {
        if (!FoliaScheduler.isFolia) {
            this.bukkitScheduler.cancelTasks(plugin);
        } else {
            this.asyncScheduler.cancelTasks(plugin);
        }
    }

    private long convertTimeToTicks(long value, TimeUnit timeUnit) {
        return timeUnit.toMillis(value) / 50L;
    }
}
