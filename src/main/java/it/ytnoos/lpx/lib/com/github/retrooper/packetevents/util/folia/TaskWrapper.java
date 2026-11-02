package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class TaskWrapper {

    private BukkitTask bukkitTask;

    private ScheduledTask scheduledTask;

    public TaskWrapper(@NotNull BukkitTask bukkitTask2) {
        this.bukkitTask = bukkitTask2;
    }

    public TaskWrapper(@NotNull ScheduledTask scheduledTask2) {
        this.scheduledTask = scheduledTask2;
    }

    public Plugin getOwner() {
        return this.bukkitTask != null
                ? this.bukkitTask.getOwner()
                : this.scheduledTask.getOwningPlugin();
    }

    public boolean isCancelled() {
        return this.bukkitTask != null
                ? this.bukkitTask.isCancelled()
                : this.scheduledTask.isCancelled();
    }

    public void cancel() {
        if (this.bukkitTask != null) {
            this.bukkitTask.cancel();
        } else {
            this.scheduledTask.cancel();
        }
    }
}
