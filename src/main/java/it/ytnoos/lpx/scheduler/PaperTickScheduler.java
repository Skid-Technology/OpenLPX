package it.ytnoos.lpx.scheduler;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PaperTickScheduler extends AbstractTickScheduler implements TickScheduler, Listener {

    private final Plugin plugin;

    public PaperTickScheduler(Plugin plugin2) {
        this.plugin = plugin2;
    }

    @EventHandler
    public void onServerTickEvent(ServerTickStartEvent serverTickStartEvent) {
        this.handleTime();
    }

    @Override
    public void start() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @Override
    public void close() {
        HandlerList.unregisterAll(this);
    }
}
