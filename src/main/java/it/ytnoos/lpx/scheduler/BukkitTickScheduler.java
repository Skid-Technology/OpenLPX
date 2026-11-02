package it.ytnoos.lpx.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTickScheduler extends AbstractTickScheduler implements TickScheduler, Runnable {

    private final Plugin plugin;

    private BukkitTask task;

    public BukkitTickScheduler(Plugin plugin2) {
        this.plugin = plugin2;
    }

    @Override
    public void run() {
        this.handleTime();
    }

    @Override
    public void start() {
        this.task = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this, 1L, 1L);
    }

    @Override
    public void close() {
        this.task.cancel();
    }
}
