package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ProtocolVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.PacketTransformationUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface ProtocolManager {

    @Internal Map<UUID, Object> CHANNELS = new ConcurrentHashMap<>();

    @Internal Map<Object, User> USERS = new ConcurrentHashMap<>();

    default Collection<User> getUsers() {
        return USERS.values();
    }

    default Collection<Object> getChannels() {
        return CHANNELS.values();
    }

    ProtocolVersion getPlatformVersion();

    void sendPacket(Object result, Object result2);

    void sendPacketSilently(Object result, Object result2);

    void writePacket(Object result, Object result2);

    void writePacketSilently(Object result, Object result2);

    void receivePacket(Object result, Object result2);

    void receivePacketSilently(Object result, Object result2);

    @Nullable
    ClientVersion getClientVersion(Object result);

    default void sendPackets(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.sendPacket(result, result3);
        }
    }

    default void sendPacketsSilently(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.sendPacketSilently(result, result3);
        }
    }

    default void writePackets(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.writePacket(result, result3);
        }
    }

    default void writePacketsSilently(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.writePacketSilently(result, result3);
        }
    }

    default void receivePackets(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.receivePacket(result, result3);
        }
    }

    default void receivePacketsSilently(Object result, Object... result2) {
        int value = result2.length;
        for (int index = 0; index < value; index++) {
            Object result3 = ((Object[]) result2)[index];
            this.receivePacketSilently(result, result3);
        }
    }

    default void setClientVersion(Object result, ClientVersion clientVersion) {
        this.getUser(result).setClientVersion(clientVersion);
    }

    @Internal
    default Object[] transformWrappers(
            PacketWrapper<?> packetWrapper, Object result, boolean matches) {
        PacketWrapper[] packetWrappers;
        Object[] results =
                new Object
                        [(packetWrappers = PacketTransformationUtil.transform(packetWrapper))
                                .length];
        for (int index = 0; index < packetWrappers.length; index++) {
            PacketWrapper packetWrapper2;
            synchronized ((packetWrapper2 = packetWrappers[index]).bufferLock) {
                packetWrapper2.prepareForSend(result, matches);
                results[index] = packetWrapper2.buffer;
                packetWrapper2.buffer = null;
            }
        }
        return results;
    }

    default void sendPacket(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, true);
        this.sendPackets(result, results);
    }

    default void sendPacketSilently(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, true);
        this.sendPacketsSilently(result, results);
    }

    default void writePacket(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, true);
        this.writePackets(result, results);
    }

    default void writePacketSilently(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, true);
        this.writePacketsSilently(result, results);
    }

    default void receivePacket(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, false);
        this.receivePackets(result, results);
    }

    default void receivePacketSilently(Object result, PacketWrapper<?> packetWrapper) {
        Object[] results = this.transformWrappers(packetWrapper, result, false);
        this.receivePacketsSilently(result, results);
    }

    @Nullable
    default User getUser(Object result) {
        Object pipeline = ChannelHelper.getPipeline(result);
        return USERS.get(pipeline);
    }

    @Internal
    @Nullable
    default User removeUser(Object result) {
        Object pipeline = ChannelHelper.getPipeline(result);
        return USERS.remove(pipeline);
    }

    @Internal
    default void setUser(Object result, User user) {
        synchronized (result) {
            Object pipeline = ChannelHelper.getPipeline(result);
            USERS.put(pipeline, user);
        }
        PacketEvents.getAPI().getInjector().updateUser(result, user);
    }

    @Nullable
    default Object getChannel(UUID uUID) {
        return CHANNELS.get(uUID);
    }

    @Internal
    default void setChannel(UUID uUID, Object result) {
        CHANNELS.put(uUID, result);
    }

    @Internal
    default void removeChannel(Object result) {
        CHANNELS.values().remove(result);
    }

    @Internal
    default void removeChannelById(UUID uUID) {
        CHANNELS.remove(uUID);
    }

    default boolean hasChannel(Object result) {
        return CHANNELS.containsValue(result);
    }
}
