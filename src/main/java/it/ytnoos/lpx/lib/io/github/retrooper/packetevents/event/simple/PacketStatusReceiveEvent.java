package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PacketStatusReceiveEvent extends PacketReceiveEvent {

    public PacketStatusReceiveEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches)
            throws PacketProcessException {
        super(result, user, result2, result3, matches);
    }

    public PacketStatusReceiveEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws PacketProcessException {
        super(result, user, result2, result3, matches, connectionState);
    }

    protected PacketStatusReceiveEvent(
            int value,
            PacketTypeCommon packetTypeCommon,
            ServerVersion serverVersion,
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3)
            throws PacketProcessException {
        super(value, packetTypeCommon, serverVersion, result, user, result2, result3);
    }

    public PacketStatusReceiveEvent clone() {
        Object result = ByteBufHelper.retainedDuplicate(this.getByteBuf());
        return new PacketStatusReceiveEvent(
                this.getPacketId(),
                this.getPacketType(),
                this.getServerVersion(),
                this.getChannel(),
                this.getUser(),
                this.getPlayer(),
                result);
    }

    public PacketType.Status.Client getPacketType() {
        return (PacketType.Status.Client) super.getPacketType();
    }
}
