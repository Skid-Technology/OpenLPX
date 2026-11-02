package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.FakeChannelUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Internal
public class InternalBukkitLoginListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent playerLoginEvent) {
        PacketEventsAPI packetEventsAPI;
        User user;
        if ((user =
                        (packetEventsAPI = PacketEvents.getAPI())
                                .getPlayerManager()
                                .getUser(playerLoginEvent.getPlayer()))
                != null) {
            ((SpigotChannelInjector) packetEventsAPI.getInjector())
                    .updatePlayer(user, playerLoginEvent.getPlayer());
        } else {
            Object result;
            if (((result =
                                            packetEventsAPI
                                                    .getPlayerManager()
                                                    .getChannel(playerLoginEvent.getPlayer()))
                                    == null
                            || !FakeChannelUtil.isFakeChannel(result))
                    && (!packetEventsAPI.isTerminated()
                            || packetEventsAPI.getSettings().isKickIfTerminated())
                    && !PacketEvents.getAPI().getSettings().shouldIgnoreFailures()) {
                playerLoginEvent.disallow(
                        Result.KICK_OTHER, "PacketEvents failed to inject into a channel");
            }
        }
    }
}
