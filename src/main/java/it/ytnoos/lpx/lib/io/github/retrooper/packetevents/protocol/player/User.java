package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEventsAPI;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.ConnectionState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessageLegacy;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_16;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.ResolvableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerCloseWindow;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleSubtitle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleText;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleTimes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSystemChatMessage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTitle;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class User implements IRegistryHolder {

    private final Object channel;

    private ConnectionState decoderState;

    private ConnectionState preViaDecoderState;

    private ConnectionState encoderState;

    private ConnectionState preViaEncoderState;

    private ClientVersion clientVersion;

    private final UserProfile profile;

    private int entityId = -1;

    private DimensionType dimensionType = DimensionTypes.OVERWORLD;

    private final Map<ResourceLocation, IRegistry<?>> registries = new ConcurrentHashMap<>();

    public User(
            Object channel,
            ConnectionState connectionState,
            ClientVersion clientVersion2,
            UserProfile userProfile) {
        this.channel = channel;
        this.decoderState = connectionState;
        this.preViaDecoderState = connectionState;
        this.encoderState = connectionState;
        this.preViaEncoderState = connectionState;
        this.clientVersion = clientVersion2;
        this.profile = userProfile;
    }

    @Internal
    @Nullable
    @Override
    public IRegistry<?> getRegistry(
            ResourceLocation resourceLocation, ClientVersion clientVersion) {
        return this.registries.get(resourceLocation);
    }

    @Internal
    public void putRegistry(IRegistry<?> registry) {
        this.registries.put(registry.getRegistryKey(), registry);
    }

    @Internal
    public void finalizeRegistries(PacketWrapper<?> packetWrapper) {
        Iterator<IRegistry<?>> iterator2 = this.registries.values().iterator();
        while (iterator2.hasNext()) {
            Iterator iterator3 = ((IRegistry) iterator2.next()).getEntries().iterator();
            while (iterator3.hasNext()) {
                MappedEntity mappedEntity;
                if ((mappedEntity = (MappedEntity) iterator3.next()) instanceof ResolvableEntity) {
                    ((ResolvableEntity) mappedEntity).doResolve(packetWrapper);
                }
            }
        }
    }

    public Object getChannel() {
        return this.channel;
    }

    public InetSocketAddress getAddress() {
        return (InetSocketAddress) ChannelHelper.remoteAddress(this.channel);
    }

    @Obsolete
    public ConnectionState getConnectionState() throws IllegalStateException {
        ConnectionState connectionState = this.decoderState;
        ConnectionState connectionState2 = this.encoderState;
        if (connectionState != connectionState2) {
            throw new IllegalStateException(
                    "Can't get common connection state: "
                            + connectionState
                            + " != "
                            + connectionState2);
        } else {
            return connectionState;
        }
    }

    @Internal
    public void setConnectionState(ConnectionState connectionState) {
        this.setDecoderState(connectionState);
        this.setEncoderState(connectionState);
        this.setPreViaDecoderState(connectionState);
        this.setPreViaEncoderState(connectionState);
    }

    public ConnectionState getDecoderState() {
        return this.decoderState;
    }

    public ConnectionState getPreViaDecoderState() {
        return this.preViaDecoderState;
    }

    @Internal
    public void setPreViaDecoderState(ConnectionState connectionState) {
        this.preViaDecoderState = connectionState;
        PacketEvents.getAPI()
                .getLogManager()
                .debug(
                        "Transitioned "
                                + this.getName()
                                + "'s pre-via decoder into "
                                + connectionState
                                + " state!");
    }

    @Internal
    public void setDecoderState(ConnectionState connectionState) {
        this.decoderState = connectionState;
        PacketEvents.getAPI()
                .getLogManager()
                .debug(
                        "Transitioned "
                                + this.getName()
                                + "'s decoder into "
                                + connectionState
                                + " state!");
    }

    public ConnectionState getEncoderState() {
        return this.encoderState;
    }

    public ConnectionState getPreViaEncoderState() {
        return this.preViaEncoderState;
    }

    @Internal
    public void setPreViaEncoderState(ConnectionState connectionState) {
        this.preViaEncoderState = connectionState;
        PacketEvents.getAPI()
                .getLogManager()
                .debug(
                        "Transitioned "
                                + this.getName()
                                + "'s pre-via encoder into "
                                + connectionState
                                + " state!");
    }

    @Internal
    public void setEncoderState(ConnectionState connectionState) {
        this.encoderState = connectionState;
        PacketEvents.getAPI()
                .getLogManager()
                .debug(
                        "Transitioned "
                                + this.getName()
                                + "'s encoder into "
                                + connectionState
                                + " state!");
    }

    public ClientVersion getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(ClientVersion clientVersion2) {
        this.clientVersion = clientVersion2;
    }

    public UserProfile getProfile() {
        return this.profile;
    }

    public String getName() {
        return this.profile.getName();
    }

    public UUID getUUID() {
        return this.profile.getUUID();
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public void sendPacket(Object result) {
        PacketEvents.getAPI().getProtocolManager().sendPacket(this.channel, result);
    }

    public void sendPacket(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI().getProtocolManager().sendPacket(this.channel, packetWrapper);
    }

    public void sendPacketSilently(Object result) {
        PacketEvents.getAPI().getProtocolManager().sendPacketSilently(this.channel, result);
    }

    public void sendPacketSilently(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI().getProtocolManager().sendPacketSilently(this.channel, packetWrapper);
    }

    public void writePacket(Object result) {
        PacketEvents.getAPI().getProtocolManager().writePacket(this.channel, result);
    }

    public void writePacket(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI().getProtocolManager().writePacket(this.channel, packetWrapper);
    }

    public void writePacketSilently(Object result) {
        PacketEvents.getAPI().getProtocolManager().writePacketSilently(this.channel, result);
    }

    public void writePacketSilently(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI().getProtocolManager().writePacketSilently(this.channel, packetWrapper);
    }

    public void receivePacket(Object result) {
        PacketEvents.getAPI().getProtocolManager().receivePacket(this.channel, result);
    }

    public void receivePacket(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI().getProtocolManager().receivePacket(this.channel, packetWrapper);
    }

    public void receivePacketSilently(Object result) {
        PacketEvents.getAPI().getProtocolManager().receivePacketSilently(this.channel, result);
    }

    public void receivePacketSilently(PacketWrapper<?> packetWrapper) {
        PacketEvents.getAPI()
                .getProtocolManager()
                .receivePacketSilently(this.channel, packetWrapper);
    }

    public void flushPackets() {
        ChannelHelper.flush(this.channel);
    }

    public void closeConnection() {
        ChannelHelper.close(this.channel);
    }

    public void closeInventory() {
        WrapperPlayServerCloseWindow wrapperPlayServerCloseWindow =
                new WrapperPlayServerCloseWindow(0);
        PacketEvents.getAPI()
                .getProtocolManager()
                .sendPacket(this.channel, wrapperPlayServerCloseWindow);
    }

    public void sendMessage(String text) {
        this.sendMessage(this.getSerializers().fromLegacy(text));
    }

    public void sendMessage(Component component) {
        this.sendMessage(component, ChatTypes.CHAT);
    }

    public void sendMessage(Component component, ChatType chatType) {
        ClientVersion clientVersion;
        PacketWrapper packetWrapper;
        if ((clientVersion = this.getPacketVersion()).isNewerThanOrEquals(ClientVersion.V_1_19)) {
            packetWrapper = new WrapperPlayServerSystemChatMessage(false, component);
        } else {
            ChatMessage chatMessage;
            if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_16)) {
                chatMessage = new ChatMessage_v1_16(component, chatType, new UUID(0L, 0L));
            } else {
                chatMessage = new ChatMessageLegacy(component, chatType);
            }
            packetWrapper = new WrapperPlayServerChatMessage(chatMessage);
        }
        PacketEvents.getAPI().getProtocolManager().sendPacket(this.channel, packetWrapper);
    }

    public void sendTitle(String text, String text2, int value, int value2, int value3) {
        LegacyComponentSerializer legacyComponentSerializer;
        TextComponent textComponent =
                (legacyComponentSerializer = this.getSerializers().legacy()).deserialize(text);
        TextComponent textComponent2 = legacyComponentSerializer.deserialize(text2);
        this.sendTitle(textComponent, textComponent2, value, value2, value3);
    }

    public void sendTitle(
            Component component, Component component2, int value, int value2, int value3) {
        boolean newerThanOrEquals =
                this.getPacketVersion().isNewerThanOrEquals(ClientVersion.V_1_17);
        PacketWrapper packetWrapper = null;
        PacketWrapper packetWrapper2 = null;
        PacketWrapper packetWrapper3;
        if (newerThanOrEquals) {
            packetWrapper3 = new WrapperPlayServerSetTitleTimes(value, value2, value3);
            if (component != null) {
                packetWrapper = new WrapperPlayServerSetTitleText(component);
            }
            if (component2 != null) {
                packetWrapper2 = new WrapperPlayServerSetTitleSubtitle(component2);
            }
        } else {
            packetWrapper3 =
                    new WrapperPlayServerTitle(
                            WrapperPlayServerTitle.TitleAction.SET_TIMES_AND_DISPLAY,
                            (Component) null,
                            (Component) null,
                            (Component) null,
                            value,
                            value2,
                            value3);
            if (component != null) {
                packetWrapper =
                        new WrapperPlayServerTitle(
                                WrapperPlayServerTitle.TitleAction.SET_TITLE,
                                component,
                                null,
                                null,
                                0,
                                0,
                                0);
            }
            if (component2 != null) {
                packetWrapper2 =
                        new WrapperPlayServerTitle(
                                WrapperPlayServerTitle.TitleAction.SET_SUBTITLE,
                                null,
                                component2,
                                null,
                                0,
                                0,
                                0);
            }
        }
        this.sendPacket(packetWrapper3);
        if (packetWrapper != null) {
            this.sendPacket(packetWrapper);
        }
        if (packetWrapper2 != null) {
            this.sendPacket(packetWrapper2);
        }
    }

    public ClientVersion getPacketVersion() {
        PacketEventsAPI packetEventsAPI;
        return (packetEventsAPI = PacketEvents.getAPI()).getInjector().isProxy()
                ? this.getClientVersion()
                : packetEventsAPI.getServerManager().getVersion().toClientVersion();
    }

    public AdventureSerializer getSerializers() {
        return AdventureSerializer.serializer(this.getPacketVersion());
    }

    public int getMinWorldHeight() {
        return this.dimensionType.getMinY();
    }

    @Deprecated
    public int getMinWorldHeight(@Nullable ClientVersion clientVersion) {
        return this.getMinWorldHeight();
    }

    public int getTotalWorldHeight() {
        return this.dimensionType.getHeight();
    }

    @Deprecated
    public int getTotalWorldHeight(@Nullable ClientVersion clientVersion) {
        return this.getTotalWorldHeight();
    }

    public DimensionType getDimensionType() {
        return this.dimensionType;
    }

    @Internal
    public void setDimensionType(DimensionType dimensionType2) {
        this.dimensionType = dimensionType2;
    }

    @Deprecated
    public void setMinWorldHeight(int value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setTotalWorldHeight(int value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void switchDimensionType(ServerVersion serverVersion, Dimension dimension) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setDefaultWorldHeights(ServerVersion serverVersion, Dimension dimension) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setDefaultWorldHeights(boolean matches) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setWorldNBT(NBTList<NBTCompound> nBTList) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public Dimension getDimension() {
        return Dimension.fromDimensionType(this.dimensionType, this, null);
    }

    @Deprecated
    public void setDimension(Dimension dimension) {
        this.dimensionType = dimension.asDimensionType(this, null);
    }

    @Deprecated
    @Nullable
    public NBTCompound getWorldNBT(String text) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nullable
    public NBTCompound getWorldNBT(int value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nullable
    public NBTCompound getWorldNBT(Dimension dimension) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Nullable
    public String getWorldName(int value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public String getWorldName(Dimension dimension) {
        throw new UnsupportedOperationException();
    }
}
