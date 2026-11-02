package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.event.connection.PlayerConnectionValidateLoginEvent;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.handlers.PacketEventsEncoder;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.SpigotReflectionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Internal
public class InternalPaperJoinListener implements Listener {

    private final InternalBukkitListener delegate;

    public InternalPaperJoinListener(Plugin plugin) {
        this.delegate = new InternalBukkitListener(plugin);
    }

    private void setChannelFreeze(Channel channel, boolean matches) {
        EventLoop eventLoop2 = channel.eventLoop();
        eventLoop2.execute(
                () -> {
                    try {
                        PacketEventsEncoder packetEventsEncoder;
                        if ((packetEventsEncoder =
                                        ((SpigotChannelInjector)
                                                        PacketEvents.getAPI().getInjector())
                                                .getEncoder(channel))
                                != null) {
                            packetEventsEncoder.setHold(channel, matches);
                        }
                    } catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerConnectionValidateLoginEvent playerConnectionValidateLoginEvent) {
        if (playerConnectionValidateLoginEvent.isAllowed()) {
            if (((PlayerConfigurationConnection) playerConnectionValidateLoginEvent.getConnection())
                    instanceof PlayerConfigurationConnection) {
                Channel channel =
                        (Channel)
                                SpigotReflectionUtil.getChannelFromPaperConnection(
                                        playerConnectionValidateLoginEvent.getConnection());
                this.setChannelFreeze(channel, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.delegate.onPostJoin(playerJoinEvent.getPlayer());
        Channel channel;
        if ((channel = (Channel) SpigotReflectionUtil.getChannel(playerJoinEvent.getPlayer()))
                != null) {
            this.setChannelFreeze(channel, false);
        }
    }
}
