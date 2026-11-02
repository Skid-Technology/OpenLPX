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
public class PacketConfigReceiveEvent extends PacketReceiveEvent {

    public PacketConfigReceiveEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches)
            throws PacketProcessException {
        super(result, user, result2, result3, matches);
    }

    public PacketConfigReceiveEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws PacketProcessException {
        super(result, user, result2, result3, matches, connectionState);
    }

    protected PacketConfigReceiveEvent(
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

    public PacketConfigReceiveEvent clone() {
        Object result = ByteBufHelper.retainedDuplicate(this.getByteBuf());
        return new PacketConfigReceiveEvent(
                this.getPacketId(),
                this.getPacketType(),
                this.getServerVersion(),
                this.getChannel(),
                this.getUser(),
                this.getPlayer(),
                result);
    }

    public PacketType.Configuration.Client getPacketType() {
        return (PacketType.Configuration.Client) super.getPacketType();
    }
}
