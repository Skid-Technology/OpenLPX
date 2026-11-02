package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.bukkit;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.injector.SpigotChannelInjector;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.manager.player.PlayerManagerImpl;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.EntityScheduler;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.folia.FoliaScheduler;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.FakeChannelUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.lang.ref.WeakReference;

@NullMarked
@Internal
public class InternalBukkitListener implements Listener {

    static final String KICK_MESSAGE = "PacketEvents failed to inject into a channel";

    private final Plugin plugin;

    public InternalBukkitListener(Plugin plugin2) {
        this.plugin = plugin2;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent playerLoginEvent) {
        if (playerLoginEvent.getResult() == Result.ALLOWED) {
            this.onPreJoin(playerLoginEvent.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.onPostJoin(playerJoinEvent.getPlayer());
    }

    void onPreJoin(Player player) {
        ((PlayerManagerImpl) PacketEvents.getAPI().getPlayerManager())
                .joiningPlayers.put(player.getUniqueId(), new WeakReference<>(player));
    }

    void onPostJoin(Player player) {
        PacketEventsAPI packetEventsAPI;
        User user;
        if ((user = (packetEventsAPI = PacketEvents.getAPI()).getPlayerManager().getUser(player))
                != null) {
            ((SpigotChannelInjector) PacketEvents.getAPI().getInjector())
                    .setPlayer(user.getChannel(), player);
            ((PlayerManagerImpl) packetEventsAPI.getPlayerManager())
                    .joiningPlayers.remove(player.getUniqueId());
        } else {
            ((PlayerManagerImpl) packetEventsAPI.getPlayerManager())
                    .joiningPlayers.remove(player.getUniqueId());
            Object result2;
            if (((result2 = packetEventsAPI.getPlayerManager().getChannel(player)) == null
                            || !FakeChannelUtil.isFakeChannel(result2))
                    && (!packetEventsAPI.isTerminated()
                            || packetEventsAPI.getSettings().isKickIfTerminated())
                    && !PacketEvents.getAPI().getSettings().shouldIgnoreFailures()) {
                EntityScheduler entityScheduler = FoliaScheduler.getEntityScheduler();
                Object result3 = result2;
                entityScheduler.runDelayed(
                        player,
                        this.plugin,
                        result -> {
                            if (result3 != null
                                    ? ChannelHelper.isOpen(result3)
                                    : player.isOnline()) {
                                player.kickPlayer("PacketEvents failed to inject into a channel");
                            }
                        },
                        null,
                        0L);
            }
        }
    }
}
