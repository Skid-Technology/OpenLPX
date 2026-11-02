package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

@NullMarked
@Internal
public class InternalPaperListener implements Listener {

    private final InternalBukkitListener delegate;

    public InternalPaperListener(Plugin plugin) {
        this.delegate = new InternalBukkitListener(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawnLocation(PlayerSpawnLocationEvent playerSpawnLocationEvent) {
        this.delegate.onPreJoin(playerSpawnLocationEvent.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.delegate.onPostJoin(playerJoinEvent.getPlayer());
    }
}
