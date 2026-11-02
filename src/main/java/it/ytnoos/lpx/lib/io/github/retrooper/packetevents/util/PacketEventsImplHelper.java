package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.EventManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.UserDisconnectEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.protocol.ProtocolManager;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.UUID;

public final class PacketEventsImplHelper {

    private PacketEventsImplHelper() {}

    @Nullable
    public static Object handlePacket(
            Object result,
            User user,
            Object result2,
            Object result3,
            boolean matches,
            PacketSide packetSide)
            throws Exception {
        return packetSide == PacketSide.SERVER
                ? handleClientBoundPacket(result, user, result2, result3, matches)
                : handleServerBoundPacket(result, user, result2, result3, matches);
    }

    @Nullable
    public static PacketSendEvent handleClientBoundPacket(
            Object result, User user, Object result2, Object result3, boolean matches)
            throws Exception {
        return handleClientBoundPacket(
                result, user, result2, result3, matches, user.getEncoderState());
    }

    @Nullable
    public static PacketSendEvent handleClientBoundPacket(
            Object result,
            User user,
            Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws Exception {
        if (!ByteBufHelper.isReadable(result3)) {
            return null;
        }
        int value = ByteBufHelper.readerIndex(result3);
        PacketSendEvent packetSendEvent =
                EventCreationUtil.createSendEvent(
                        result, user, result2, result3, matches, connectionState);
        int value2 = ByteBufHelper.readerIndex(result3);
        EventManager eventManager = PacketEvents.getAPI().getEventManager();
        eventManager.callEvent(
                packetSendEvent, () -> ByteBufHelper.readerIndex(result3, value2), !matches);
        if (!packetSendEvent.isCancelled()) {
            if (packetSendEvent.getLastUsedWrapper() != null) {
                ByteBufHelper.clear(result3);
                packetSendEvent.getLastUsedWrapper().writeVarInt(packetSendEvent.getPacketId());
                packetSendEvent.getLastUsedWrapper().write();
            } else {
                ByteBufHelper.readerIndex(result3, value);
            }
        } else {
            ByteBufHelper.clear(result3);
        }
        if (packetSendEvent.hasPostTasks()) {
            Iterator<Runnable> iterator2 = packetSendEvent.getPostTasks().iterator();
            while (iterator2.hasNext()) {
                ((Runnable) iterator2.next()).run();
            }
        }
        return packetSendEvent;
    }

    @Nullable
    public static Object handleServerBoundPacket(
            Object result, User user, Object result2, Object result3, boolean matches)
            throws Exception {
        return handleServerBoundPacket(
                result, user, result2, result3, matches, user.getDecoderState());
    }

    @Nullable
    public static Object handleServerBoundPacket(
            Object result,
            User user,
            Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws Exception {
        if (!ByteBufHelper.isReadable(result3)) {
            return result3;
        }
        int value = ByteBufHelper.readerIndex(result3);
        PacketReceiveEvent packetReceiveEvent =
                EventCreationUtil.createReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
        int value2 = ByteBufHelper.readerIndex(result3);
        Object result4 = result3;
        EventManager eventManager = PacketEvents.getAPI().getEventManager();
        eventManager.callEvent(
                packetReceiveEvent, () -> ByteBufHelper.readerIndex(result4, value2), !matches);
        if (!packetReceiveEvent.isCancelled()) {
            if (packetReceiveEvent.getLastUsedWrapper() != null) {
                ByteBufHelper.clear(result3);
                result3 = UnpooledByteBufAllocationHelper.buffer();
                packetReceiveEvent.getLastUsedWrapper().setBuffer(result3);
                packetReceiveEvent
                        .getLastUsedWrapper()
                        .writeVarInt(packetReceiveEvent.getPacketId());
                packetReceiveEvent.getLastUsedWrapper().write();
            } else {
                ByteBufHelper.readerIndex(result3, value);
            }
        } else {
            ByteBufHelper.clear(result3);
        }
        if (packetReceiveEvent.hasPostTasks()) {
            Iterator<Runnable> iterator2 = packetReceiveEvent.getPostTasks().iterator();
            while (iterator2.hasNext()) {
                ((Runnable) iterator2.next()).run();
            }
        }
        return result3;
    }

    public static void handleDisconnection(Object result, @Nullable UUID uUID) {
        synchronized (result) {
            ProtocolManager protocolManager;
            User user;
            if ((user =
                            (protocolManager = PacketEvents.getAPI().getProtocolManager())
                                    .getUser(result))
                    != null) {
                UserDisconnectEvent userDisconnectEvent = new UserDisconnectEvent(user);
                PacketEvents.getAPI().getEventManager().callEvent(userDisconnectEvent);
                protocolManager.removeUser(user.getChannel());
            }
            if (uUID == null) {
                protocolManager.removeChannel(result);
            } else {
                protocolManager.removeChannelById(uUID);
            }
        }
    }
}
