package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.injector;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

public interface ChannelInjector {

    default boolean isServerBound() {
        return true;
    }

    void inject();

    void uninject();

    void updateUser(Object result, User user);

    void setPlayer(Object result, Object result2);

    boolean isPlayerSet(Object result);

    boolean isProxy();

    default PacketSide getPacketSide() {
        return PacketSide.SERVER;
    }
}
