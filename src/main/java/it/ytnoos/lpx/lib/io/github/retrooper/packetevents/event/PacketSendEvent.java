package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public class PacketSendEvent extends ProtocolPacketEvent {

    @Nullable private List<Runnable> tasksAfterSend = null;

    protected PacketSendEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches)
            throws PacketProcessException {
        super(PacketSide.SERVER, result, user, result2, result3, matches);
    }

    public PacketSendEvent(
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches,
            ConnectionState connectionState)
            throws PacketProcessException {
        super(PacketSide.SERVER, result, user, result2, result3, matches, connectionState);
    }

    public PacketSendEvent(
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

    @Override
    public void call(PacketListenerCommon packetListenerCommon) {
        packetListenerCommon.onPacketSend(this);
    }

    public List<Runnable> getTasksAfterSend() {
        if (this.tasksAfterSend == null) {
            this.tasksAfterSend = new ArrayList<>();
        }
        return this.tasksAfterSend;
    }

    public boolean hasTasksAfterSend() {
        return this.tasksAfterSend != null && !this.tasksAfterSend.isEmpty();
    }

    public PacketSendEvent clone() {
        Object result = ByteBufHelper.retainedDuplicate(this.getByteBuf());
        return new PacketSendEvent(
                this.getPacketId(),
                this.getPacketType(),
                this.getServerVersion(),
                this.getChannel(),
                this.getUser(),
                this.getPlayer(),
                result);
    }
}
