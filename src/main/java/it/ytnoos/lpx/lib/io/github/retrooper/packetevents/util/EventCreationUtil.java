package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketConfigReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketConfigSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketHandshakeReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketHandshakeSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketLoginReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketLoginSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketPlaySendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketStatusReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.PacketStatusSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

public class EventCreationUtil {

    public static PacketReceiveEvent createReceiveEvent(
            Object result, User user, Object result2, Object result3, boolean matches)
            throws PacketProcessException {
        return createReceiveEvent(result, user, result2, result3, matches, user.getDecoderState());
    }

    public static PacketReceiveEvent createReceiveEvent(
            Object result,
            User user,
            Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws PacketProcessException {
        switch (connectionState) {
            case HANDSHAKING:
                return new PacketHandshakeReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
            case STATUS:
                return new PacketStatusReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
            case LOGIN:
                return new PacketLoginReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
            case PLAY:
                return new PacketPlayReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
            case CONFIGURATION:
                return new PacketConfigReceiveEvent(
                        result, user, result2, result3, matches, connectionState);
            default:
                throw new RuntimeException("Unknown connection state " + connectionState + "!");
        }
    }

    public static PacketSendEvent createSendEvent(
            Object result, User user, Object result2, Object result3, boolean matches)
            throws PacketProcessException {
        return createSendEvent(result, user, result2, result3, matches, user.getEncoderState());
    }

    public static PacketSendEvent createSendEvent(
            Object result,
            User user,
            Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws PacketProcessException {
        switch (connectionState) {
            case HANDSHAKING:
                return new PacketHandshakeSendEvent(
                        result, user, result2, result3, matches, connectionState);
            case STATUS:
                return new PacketStatusSendEvent(
                        result, user, result2, result3, matches, connectionState);
            case LOGIN:
                return new PacketLoginSendEvent(
                        result, user, result2, result3, matches, connectionState);
            case PLAY:
                return new PacketPlaySendEvent(
                        result, user, result2, result3, matches, connectionState);
            case CONFIGURATION:
                return new PacketConfigSendEvent(
                        result, user, result2, result3, matches, connectionState);
            default:
                throw new RuntimeException("Unknown connection state " + connectionState + "!");
        }
    }
}
