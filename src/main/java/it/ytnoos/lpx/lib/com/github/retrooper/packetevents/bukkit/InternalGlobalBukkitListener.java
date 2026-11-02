package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.UserLoginEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class InternalGlobalBukkitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(playerJoinEvent.getPlayer());
        PacketEvents.getAPI()
                .getEventManager()
                .callEvent(new UserLoginEvent(user, playerJoinEvent.getPlayer()));
    }
}
