package it.ytnoos.lpx.player;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.alerts.AlertManager;
import it.ytnoos.lpx.api.player.ProtocolPlayer;
import it.ytnoos.lpx.api.protocol.PlayerRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.EventManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.netty.ChannelInjectionListener;
import it.ytnoos.lpx.netty.NettyHealthMonitor;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerRegistryImpl implements PlayerRegistry {

    private final LPXBukkit lpx;

    private final Map<User, ProtocolPlayer> players;

    public PlayerRegistryImpl(LPXBukkit lPXBukkit) {
        ConcurrentHashMap entries = new ConcurrentHashMap();
        this.players = entries;
        this.lpx = lPXBukkit;
        EventManager eventManager = PacketEvents.getAPI().getEventManager();
        ChannelInjectionListener channelInjectionListener =
                new ChannelInjectionListener(lPXBukkit, this);
        eventManager.registerListener(channelInjectionListener);
    }

    public void uninjectAll() {
        for (User user : (Iterable<User>) ((Iterable) new HashSet<>(this.players.keySet()))) {
            this.uninject(user);
        }
    }

    public void uninject(User user) {
        if (this.players.remove(user) != null) {
            UUID uUID = user.getUUID();
            if (uUID != null) {
                LPXBukkit lPXBukkit = this.lpx;
                AlertManager alertManager = lPXBukkit.getAlertManager();
                alertManager.handleValue(uUID);
                Object channel = user.getChannel();
                if (channel != null) {
                    EventLoop eventLoop2 = ((Channel) channel).eventLoop();
                    if (eventLoop2 != null) {
                        eventLoop2.execute(
                                () -> {
                                    LPXBukkit lPXBukkit2 = this.lpx;
                                    NettyHealthMonitor nettyHealthMonitor =
                                            lPXBukkit2.getNettyHealthMonitor();
                                    Thread thread = Thread.currentThread();
                                    Thread thread2 = thread;
                                    thread = thread2;
                                    Thread thread3 = thread;
                                    nettyHealthMonitor.handleEventLoop2(uUID, thread3, eventLoop2);
                                });
                    }
                }
            }
        }
    }

    public Optional<ProtocolPlayer> getPlayer(Player player) {
        User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
        return user == null ? Optional.empty() : this.getPlayer(user);
    }

    public Optional<ProtocolPlayer> getPlayer(User user) {
        return Optional.ofNullable(this.players.get(user));
    }

    public Map<User, ProtocolPlayer> getPlayers() {
        return Collections.unmodifiableMap(this.players);
    }

    @Nullable
    public ProtocolPlayer addPlayer(User user, ProtocolPlayer protocolPlayer) {
        return this.players.put(user, protocolPlayer);
    }
}
