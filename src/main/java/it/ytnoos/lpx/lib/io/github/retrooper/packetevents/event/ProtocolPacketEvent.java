package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.InvalidDisconnectPacketSend;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

@NullMarked
public abstract class ProtocolPacketEvent extends PacketEvent
        implements PlayerEvent, CancellableEvent, UserEvent {

    private final int packetID;

    private final PacketTypeCommon packetType;

    private ServerVersion serverVersion;

    private final Object channel;

    private final ConnectionState connectionState;

    private final User user;

    @UnknownNullability private Object player;

    private Object byteBuf;

    private boolean cancel;

    @Nullable private PacketWrapper<?> lastUsedWrapper;

    @Nullable private List<Runnable> postTasks = null;

    private boolean cloned;

    private boolean autoProtocolTranslation;

    private boolean needsReEncode = PacketEvents.getAPI().getSettings().reEncodeByDefault();

    public ProtocolPacketEvent(
            PacketSide packetSide,
            Object result,
            User user,
            @UnknownNullability Object result2,
            Object result3,
            boolean matches)
            throws PacketProcessException {
        this(
                packetSide,
                result,
                user,
                result2,
                result3,
                matches,
                packetSide == PacketSide.CLIENT ? user.getDecoderState() : user.getEncoderState());
    }

    protected ProtocolPacketEvent(
            PacketSide packetSide,
            Object channel,
            User user2,
            @UnknownNullability Object player,
            Object byteBuf,
            boolean autoProtocolTranslation,
            ConnectionState connectionState2)
            throws PacketProcessException {
        this.channel = channel;
        this.user = user2;
        this.player = player;
        this.autoProtocolTranslation = autoProtocolTranslation;
        if (!autoProtocolTranslation && user2.getClientVersion() != null) {
            this.serverVersion = user2.getClientVersion().toServerVersion();
        } else {
            this.serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        }
        this.byteBuf = byteBuf;
        int value;
        if ((value = ByteBufHelper.readableBytes(byteBuf)) == 0) {
            throw new PacketProcessException(
                    "Trying to process a packet, but it has no content. (Size=0)");
        }
        try {
            this.packetID = ByteBufHelper.readVarInt(byteBuf);
        } catch (Exception exception) {
            throw new PacketProcessException(
                    "Failed to read the Packet ID of a packet. (Size: " + value + ")");
        }
        ClientVersion clientVersion = this.serverVersion.toClientVersion();
        this.connectionState = connectionState2;
        PacketTypeCommon packetTypeCommon;
        if ((packetTypeCommon =
                        PacketType.getById(
                                packetSide, this.connectionState, clientVersion, this.packetID))
                == null) {
            if (PacketType.getById(packetSide, ConnectionState.PLAY, clientVersion, this.packetID)
                    == PacketType.Play.Server.DISCONNECT) {
                throw new InvalidDisconnectPacketSend();
            } else {
                throw new PacketProcessException(
                        "Failed to map the Packet ID "
                                + this.packetID
                                + " to a PacketType constant. Bound: "
                                + packetSide.getOpposite()
                                + ", Connection state: "
                                + this.connectionState
                                + ", Server version: "
                                + this.serverVersion.getReleaseName()
                                + ", Previa: "
                                + !autoProtocolTranslation);
            }
        } else {
            this.packetType = packetTypeCommon;
        }
    }

    public ProtocolPacketEvent(
            int packetID,
            PacketTypeCommon packetTypeCommon,
            ServerVersion serverVersion2,
            Object channel,
            User user2,
            @UnknownNullability Object player,
            Object byteBuf) {
        this.channel = channel;
        this.user = user2;
        this.player = player;
        this.serverVersion = serverVersion2;
        this.byteBuf = byteBuf;
        this.packetID = packetID;
        this.packetType = packetTypeCommon;
        this.connectionState =
                packetTypeCommon != null && packetTypeCommon.getSide() == PacketSide.SERVER
                        ? user2.getEncoderState()
                        : user2.getDecoderState();
        this.cloned = true;
    }

    public void markForReEncode(boolean needsReEncode) {
        this.needsReEncode = needsReEncode;
    }

    public boolean needsReEncode() {
        return this.needsReEncode;
    }

    public boolean isClone() {
        return this.cloned;
    }

    public Object getChannel() {
        return this.channel;
    }

    public SocketAddress getAddress() {
        return ChannelHelper.remoteAddress(this.channel);
    }

    public InetSocketAddress getSocketAddress() {
        return (InetSocketAddress) this.getAddress();
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @NullUnmarked
    @Override
    @UnknownNullability
    public <T> T getPlayer() {
        return (T) this.player;
    }

    @Deprecated
    @Internal
    public void setPlayer(Object player) {
        this.player = player;
    }

    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Deprecated
    public ClientVersion getClientVersion() {
        return this.user.getClientVersion();
    }

    @Deprecated
    public void setClientVersion(ClientVersion clientVersion) {
        PacketEvents.getAPI()
                .getLogManager()
                .debug(
                        "Setting client version with deprecated method "
                                + clientVersion.getReleaseName());
        this.user.setClientVersion(clientVersion);
    }

    public ServerVersion getServerVersion() {
        return this.serverVersion;
    }

    @Deprecated
    public void setServerVersion(ServerVersion serverVersion2) {
        this.serverVersion = serverVersion2;
    }

    public Object getByteBuf() {
        return this.byteBuf;
    }

    public void setByteBuf(Object byteBuf) {
        this.byteBuf = byteBuf;
    }

    public int getPacketId() {
        return this.packetID;
    }

    public PacketTypeCommon getPacketType() {
        return this.packetType;
    }

    public boolean isAutoProtocolTranslation() {
        return this.autoProtocolTranslation;
    }

    @Deprecated
    public String getPacketName() {
        return ((Enum) this.packetType).name();
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Nullable
    public PacketWrapper<?> getLastUsedWrapper() {
        return this.lastUsedWrapper;
    }

    public void setLastUsedWrapper(@Nullable PacketWrapper<?> packetWrapper) {
        this.lastUsedWrapper = packetWrapper;
    }

    public List<Runnable> getPostTasks() {
        if (this.postTasks == null) {
            this.postTasks = new ArrayList<>();
        }
        return this.postTasks;
    }

    public boolean hasPostTasks() {
        return this.postTasks != null && !this.postTasks.isEmpty();
    }

    public ProtocolPacketEvent clone() {
        return this instanceof PacketReceiveEvent
                ? ((PacketReceiveEvent) this).clone()
                : ((PacketSendEvent) this).clone();
    }

    public void cleanUp() {
        if (this.isClone()) {
            ByteBufHelper.release(this.byteBuf);
        }
    }

    public Object getFullBufferClone() {
        byte[] values = ByteBufHelper.copyBytes(this.getByteBuf());
        Object result;
        ByteBufHelper.writeVarInt(
                result = UnpooledByteBufAllocationHelper.buffer(), this.getPacketId());
        ByteBufHelper.writeBytes(result, values);
        return result;
    }
}
