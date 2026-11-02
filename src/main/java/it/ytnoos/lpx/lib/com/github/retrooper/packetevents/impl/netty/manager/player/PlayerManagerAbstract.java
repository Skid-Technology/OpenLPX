package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.manager.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player.PlayerManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.NotNull;

public abstract class PlayerManagerAbstract implements PlayerManager {

    @Override
    public abstract int getPing(@NotNull Object result);

    @Override
    public abstract Object getChannel(@NotNull Object result);

    @NotNull
    @Override
    public ClientVersion getClientVersion(@NotNull Object result) {
        return this.getUser(result).getClientVersion();
    }

    @Override
    public User getUser(@NotNull Object result) {
        Object channel = this.getChannel(result);
        return PacketEvents.getAPI().getProtocolManager().getUser(channel);
    }
}
