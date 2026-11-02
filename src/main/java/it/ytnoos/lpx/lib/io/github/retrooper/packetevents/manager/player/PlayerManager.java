package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlayerManager {

    int getPing(@NotNull Object result);

    @NotNull
    ClientVersion getClientVersion(@NotNull Object result);

    @Nullable
    Object getChannel(@NotNull Object result);

    @Nullable
    User getUser(@NotNull Object result);

    @Obsolete
    default ConnectionState getConnectionState(@NotNull Object result)
            throws IllegalStateException {
        return this.getUser(result).getConnectionState();
    }

    default void sendPacket(@NotNull Object result, @NotNull Object result2) {
        PacketEvents.getAPI().getProtocolManager().sendPacket(this.getChannel(result), result2);
    }

    default void sendPacket(@NotNull Object result, @NotNull PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .sendPacket(this.getChannel(result), packetWrapper);
    }

    default void sendPacketSilently(@NotNull Object result, @NotNull Object result2) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .sendPacketSilently(this.getChannel(result), result2);
    }

    default void sendPacketSilently(
            @NotNull Object result, @NotNull PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .sendPacketSilently(this.getChannel(result), packetWrapper);
    }

    default void writePacket(@NotNull Object result, @NotNull Object result2) {
        PacketEvents.getAPI().getProtocolManager().writePacket(this.getChannel(result), result2);
    }

    default void writePacket(@NotNull Object result, @NotNull PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .writePacket(this.getChannel(result), packetWrapper);
    }

    default void writePacketSilently(@NotNull Object result, @NotNull Object result2) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .writePacketSilently(this.getChannel(result), result2);
    }

    default void writePacketSilently(
            @NotNull Object result, @NotNull PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .writePacketSilently(this.getChannel(result), packetWrapper);
    }

    default void receivePacket(Object result, Object result2) {
        PacketEvents.getAPI().getProtocolManager().receivePacket(this.getChannel(result), result2);
    }

    default void receivePacket(Object result, PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .receivePacket(this.getChannel(result), packetWrapper);
    }

    default void receivePacketSilently(Object result, Object result2) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .receivePacketSilently(this.getChannel(result), result2);
    }

    default void receivePacketSilently(Object result, PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .receivePacketSilently(this.getChannel(result), packetWrapper);
    }
}
