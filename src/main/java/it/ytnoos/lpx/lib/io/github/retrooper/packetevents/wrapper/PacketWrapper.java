package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ItemStackDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.NBTDecodeEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple.ReadSlotsEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.VersionComparison;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.channel.ChannelHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.PacketSide;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.LastSeenMessages;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.MessageSignature;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.Node;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.Parsers;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.RemoteChatSession;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.SignedCommandArgument;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMask;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter.FilterMaskType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityMetadataProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.VillagerData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfession;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.profession.VillagerProfessions;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type.VillagerTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTElementCounter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.codec.NBTCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.PublicProfileKey;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.MerchantItemCost;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data.MerchantOffer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.settings.PacketEventsSettings;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.KnownPack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.StringUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.MinecraftEncryptionUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SaltSignature;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.crypto.SignatureData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.GlobalRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public class PacketWrapper<T extends PacketWrapper<T>> {

    @Nullable public Object buffer;

    @Internal public final Object bufferLock = new Object();

    protected ClientVersion clientVersion;

    protected ServerVersion serverVersion;

    private PacketTypeData packetTypeData;

    @Nullable protected User user;

    protected IRegistryHolder registries = GlobalRegistryHolder.INSTANCE;

    @Nullable protected ProtocolPacketEvent event;

    private int itemDepth = 0;

    private int readItemstacks = 0;

    private boolean receivedData;

    @Nullable private NBTElementCounter receivedNbtElements;

    public PacketWrapper(ClientVersion clientVersion2, ServerVersion serverVersion2, int value) {
        if (value == -1) {
            throw new IllegalArgumentException("Packet does not exist on this protocol version!");
        }
        this.clientVersion = clientVersion2;
        this.serverVersion = serverVersion2;
        this.buffer = null;
        this.packetTypeData = new PacketTypeData(null, value);
    }

    public PacketWrapper(PacketReceiveEvent packetReceiveEvent) {
        this(packetReceiveEvent, true);
    }

    public PacketWrapper(PacketReceiveEvent packetReceiveEvent, boolean matches) {
        this.event = packetReceiveEvent;
        this.clientVersion = packetReceiveEvent.getUser().getClientVersion();
        this.serverVersion = packetReceiveEvent.getServerVersion();
        this.user = packetReceiveEvent.getUser();
        this.buffer = packetReceiveEvent.getByteBuf();
        this.packetTypeData =
                new PacketTypeData(
                        packetReceiveEvent.getPacketType(), packetReceiveEvent.getPacketId());
        if (matches) {
            this.readEvent(packetReceiveEvent);
        }
    }

    public PacketWrapper(PacketSendEvent packetSendEvent) {
        this(packetSendEvent, true);
    }

    public PacketWrapper(PacketSendEvent packetSendEvent, boolean matches) {
        this.event = packetSendEvent;
        this.clientVersion = packetSendEvent.getUser().getClientVersion();
        this.serverVersion = packetSendEvent.getServerVersion();
        this.buffer = packetSendEvent.getByteBuf();
        this.packetTypeData =
                new PacketTypeData(packetSendEvent.getPacketType(), packetSendEvent.getPacketId());
        this.user = packetSendEvent.getUser();
        if (matches) {
            this.readEvent(packetSendEvent);
        }
    }

    public PacketWrapper(int value, ClientVersion clientVersion) {
        this(clientVersion, PacketEvents.getAPI().getServerManager().getVersion(), value);
    }

    public PacketWrapper(int value) {
        if (value == -1) {
            throw new IllegalArgumentException("Packet does not exist on this protocol version!");
        }
        this.clientVersion = ClientVersion.UNKNOWN;
        this.serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        this.buffer = null;
        this.packetTypeData = new PacketTypeData(null, value);
    }

    public PacketWrapper(PacketTypeCommon packetTypeCommon) {
        this.clientVersion = ClientVersion.UNKNOWN;
        this.serverVersion = PacketEvents.getAPI().getServerManager().getVersion();
        this.buffer = null;
        int id = packetTypeCommon.getId(this.serverVersion.toClientVersion());
        this.packetTypeData = new PacketTypeData(packetTypeCommon, id);
    }

    public static PacketWrapper<?> createDummyWrapper(ClientVersion clientVersion) {
        return new PacketWrapper(clientVersion, clientVersion.toServerVersion(), -2);
    }

    public static PacketWrapper<?> createUniversalPacketWrapper(Object result) {
        return createUniversalPacketWrapper(
                result, PacketEvents.getAPI().getServerManager().getVersion());
    }

    public static PacketWrapper<?> createUniversalPacketWrapper(
            Object result, ServerVersion serverVersion) {
        PacketWrapper packetWrapper;
        (packetWrapper = new PacketWrapper(ClientVersion.UNKNOWN, serverVersion, -2)).buffer =
                result;
        return packetWrapper;
    }

    public static int getChunkX(long value) {
        return (int) (value & 4294967295L);
    }

    public static int getChunkZ(long value) {
        return (int) (value >>> 32 & 4294967295L);
    }

    public static long getChunkKey(int value, int value2) {
        return value & 4294967295L | (value2 & 4294967295L) << 32;
    }

    @Internal
    public final void prepareForSend(Object result, boolean matches, boolean matches2) {
        if (this.buffer == null || ByteBufHelper.refCnt(this.buffer) == 0) {
            this.buffer = ChannelHelper.pooledByteBuf(result);
        }
        if (matches2) {
            User user = PacketEvents.getAPI().getProtocolManager().getUser(result);
            if (this.packetTypeData.getPacketType() == null) {
                this.packetTypeData.setPacketType(
                        PacketType.getById(
                                matches ? PacketSide.SERVER : PacketSide.CLIENT,
                                user.getConnectionState(),
                                this.serverVersion.toClientVersion(),
                                this.packetTypeData.getNativePacketId()));
            }
            this.serverVersion = user.getClientVersion().toServerVersion();
            int id = this.packetTypeData.getPacketType().getId(user.getClientVersion());
            this.writeVarInt(id);
        } else {
            this.writeVarInt(this.packetTypeData.getNativePacketId());
        }
        this.write();
    }

    @Internal
    public final void prepareForSend(Object result, boolean matches) {
        this.prepareForSend(result, matches, PacketEvents.getAPI().getInjector().isProxy());
    }

    public void read() {}

    public void write() {}

    public void copy(T t) {}

    public final void readEvent(ProtocolPacketEvent protocolPacketEvent) {
        PacketWrapper lastUsedWrapper = protocolPacketEvent.getLastUsedWrapper();
        if (this.getClass().isInstance(lastUsedWrapper)) {
            this.copy((T) lastUsedWrapper);
        } else {
            this.read();
        }
        protocolPacketEvent.setLastUsedWrapper(this);
    }

    public ClientVersion getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(ClientVersion clientVersion2) {
        this.clientVersion = clientVersion2;
    }

    public final ServerVersion getServerVersion() {
        return this.serverVersion;
    }

    public void setServerVersion(ServerVersion serverVersion2) {
        this.serverVersion = serverVersion2;
    }

    public Object getBuffer() {
        return this.buffer;
    }

    public void setBuffer(Object buffer) {
        this.buffer = buffer;
    }

    @Deprecated
    public int getPacketId() {
        return this.getNativePacketId();
    }

    @Deprecated
    public void setPacketId(int value) {
        this.setNativePacketId(value);
    }

    public int getNativePacketId() {
        return this.packetTypeData.getNativePacketId();
    }

    public void setNativePacketId(int value) {
        this.packetTypeData.setNativePacketId(value);
    }

    @Internal
    public PacketTypeData getPacketTypeData() {
        return this.packetTypeData;
    }

    public int getMaxMessageLength() {
        return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13) ? 262144 : 32767;
    }

    @Deprecated
    public void resetByteBuf() {
        ByteBufHelper.clear(this.buffer);
    }

    public void resetBuffer() {
        ByteBufHelper.clear(this.buffer);
    }

    public byte readByte() {
        return ByteBufHelper.readByte(this.buffer);
    }

    public void writeByte(int value) {
        ByteBufHelper.writeByte(this.buffer, value);
    }

    public short readUnsignedByte() {
        return ByteBufHelper.readUnsignedByte(this.buffer);
    }

    public boolean readBoolean() {
        return this.readByte() != 0;
    }

    public void writeBoolean(boolean matches) {
        this.writeByte(matches ? 1 : 0);
    }

    public int readInt() {
        return ByteBufHelper.readInt(this.buffer);
    }

    public void writeInt(int value) {
        ByteBufHelper.writeInt(this.buffer, value);
    }

    public long readUnsignedInt() {
        return ByteBufHelper.readUnsignedInt(this.buffer);
    }

    public int readMedium() {
        return ByteBufHelper.readMedium(this.buffer);
    }

    public void writeMedium(int value) {
        ByteBufHelper.writeMedium(this.buffer, value);
    }

    public int readVarInt() {
        int value = 0;
        int value2 = 0;
        byte value3;
        do {
            value3 = this.readByte();
            value |= (value3 & 127) << value2 * 7;
            if (++value2 > 5) {
                throw new RuntimeException("VarInt is too large. Must be smaller than 5 bytes.");
            }
        } while ((value3 & 128) == 128);
        return value;
    }

    public void writeVarInt(int value) {
        if ((value & -128) == 0) {
            this.writeByte(value);
        } else if ((value & -16384) == 0) {
            int value2 = (value & 127 | 128) << 8 | value >>> 7;
            this.writeShort(value2);
        } else if ((value & -2097152) == 0) {
            int value3 = (value & 127 | 128) << 16 | (value >>> 7 & 127 | 128) << 8 | value >>> 14;
            this.writeMedium(value3);
        } else if ((value & -268435456) == 0) {
            int value4 =
                    (value & 127 | 128) << 24
                            | (value >>> 7 & 127 | 128) << 16
                            | (value >>> 14 & 127 | 128) << 8
                            | value >>> 21;
            this.writeInt(value4);
        } else {
            int value5 =
                    (value & 127 | 128) << 24
                            | (value >>> 7 & 127 | 128) << 16
                            | (value >>> 14 & 127 | 128) << 8
                            | value >>> 21 & 127
                            | 128;
            this.writeInt(value5);
            this.writeByte(value >>> 28);
        }
    }

    public <K, V> Map<K, V> readMap(
            PacketWrapper.Reader<K> reader, PacketWrapper.Reader<V> reader2) {
        return this.readMap(reader, reader2, 2560);
    }

    public <K, V> Map<K, V> readMap(
            PacketWrapper.Reader<K> reader, PacketWrapper.Reader<V> reader2, int value) {
        return this.readMap(reader, reader2, result -> {}, value);
    }

    public <K, V> Map<K, V> readMap(
            PacketWrapper.Reader<K> reader,
            PacketWrapper.Reader<V> reader2,
            Consumer<Integer> consumer,
            int value) {
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new PacketLimitException(PacketLimitException.Limit.MAP_SIZE, value2, value);
        }
        consumer.accept(value2);
        HashMap<K, V> entries = new HashMap<>(value2);
        for (int index = 0; index < value2; index++) {
            K k = reader.apply(this);
            V v = reader2.apply(this);
            entries.put(k, v);
        }
        return entries;
    }

    public <K, V> void writeMap(
            Map<K, V> entries, PacketWrapper.Writer<K> writer, PacketWrapper.Writer<V> writer2) {
        this.writeVarInt(entries.size());
        Iterator<Entry<K, V>> iterator2 = entries.entrySet().iterator();
        while (iterator2.hasNext()) {
            Entry entry;
            Object key = (entry = (Entry) iterator2.next()).getKey();
            Object value = entry.getValue();
            writer.accept(this, ((K) (PacketWrapper<T>) key));
            writer2.accept(this, ((V) (PacketWrapper<T>) value));
        }
    }

    public VillagerData readVillagerData() {
        VillagerType villagerType = this.readMappedEntity(VillagerTypes.getRegistry());
        VillagerProfession villagerProfession =
                this.readMappedEntity(VillagerProfessions.getRegistry());
        int value = this.readVarInt();
        return new VillagerData(villagerType, villagerProfession, value);
    }

    public void writeVillagerData(VillagerData villagerData) {
        this.writeMappedEntity(villagerData.getType());
        this.writeMappedEntity(villagerData.getProfession());
        this.writeVarInt(villagerData.getLevel());
    }

    public ItemStack readItemStackModern() {
        return ItemStackSerialization.readModern(this);
    }

    public ItemStack readPresentItemStack() {
        ItemStack itemStack;
        if ((itemStack = this.readItemStack()).isEmpty()) {
            throw new RuntimeException("Empty ItemStack not allowed");
        } else {
            return itemStack;
        }
    }

    @NotNull
    public ItemStack readItemStack() {
        return ItemStackSerialization.read(this);
    }

    public void writeItemStackModern(ItemStack itemStack) {
        ItemStackSerialization.writeModern(this, itemStack);
    }

    public void writePresentItemStack(ItemStack itemStack) {
        if (itemStack != null && !itemStack.isEmpty()) {
            this.writeItemStack(itemStack);
        } else {
            throw new RuntimeException("Empty ItemStack not allowed");
        }
    }

    public void writeItemStack(ItemStack itemStack) {
        ItemStackSerialization.write(this, itemStack);
    }

    public NBTCompound readNBT() {
        return (NBTCompound) this.readNBTRaw();
    }

    @Nullable
    public NBT readNullableNBT() {
        NBT nBT;
        return (nBT = this.readNBTRaw()) == NBTEnd.INSTANCE ? null : nBT;
    }

    public NBT readNBTRaw() {
        int value = ByteBufHelper.readerIndex(this.buffer);
        NBT nBT =
                NBTCodec.readNBTFromBuffer(
                        this.buffer, this.serverVersion, this.createNBTLimiter());
        this.setDecodedNBTSize(nBT, value);
        return this.callNBTDecodeEvent(nBT);
    }

    public void markReceivedData() {
        this.receivedData = true;
    }

    private NBTLimiter createNBTLimiter() {
        if (!this.receivedData && !(this.event instanceof PacketReceiveEvent)) {
            return NBTLimiter.forBuffer(this.buffer);
        }
        PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        if (this.receivedNbtElements == null) {
            this.receivedNbtElements =
                    new NBTElementCounter(settings.getMaxReceivedNbtElementsPerPacket());
        }
        return NBTLimiter.forBuffer(
                this.buffer,
                NBTLimiter.DEFAULT_MAX_SIZE,
                settings.getMaxReceivedNbtDepth(),
                this.receivedNbtElements);
    }

    public NBTCompound readUnlimitedNBT() {
        return (NBTCompound) this.readUnlimitedNBTRaw();
    }

    public NBT readUnlimitedNBTRaw() {
        int value = ByteBufHelper.readerIndex(this.buffer);
        NBT nBT = NBTCodec.readNBTFromBuffer(this.buffer, this.serverVersion, NBTLimiter.noop());
        this.setDecodedNBTSize(nBT, value);
        return nBT;
    }

    private void setDecodedNBTSize(NBT nBT, int value) {
        if (nBT != null && nBT != NBTEnd.INSTANCE) {
            nBT.setDecodedSizeBytes(ByteBufHelper.readerIndex(this.buffer) - value);
        }
    }

    public void writeNBT(NBTCompound nBTCompound) {
        this.writeNBTRaw(nBTCompound);
    }

    public void writeNBTRaw(NBT nBT) {
        NBTCodec.writeNBTToBuffer(this.buffer, this.serverVersion, nBT);
    }

    public String readString() {
        return this.readString(32767);
    }

    public String readString(int value) {
        int value2;
        if ((value2 = this.readVarInt()) > value * 4) {
            throw new PacketLimitException(
                    PacketLimitException.Limit.STRING_LENGTH, value2, value * 4L);
        } else if (value2 < 0) {
            throw new RuntimeException(
                    "The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String text =
                    ByteBufHelper.toString(
                            this.buffer,
                            ByteBufHelper.readerIndex(this.buffer),
                            value2,
                            StandardCharsets.UTF_8);
            ByteBufHelper.readerIndex(this.buffer, ByteBufHelper.readerIndex(this.buffer) + value2);
            if (text.length() > value) {
                throw new PacketLimitException(
                        PacketLimitException.Limit.STRING_LENGTH, text.length(), value);
            } else {
                return text;
            }
        }
    }

    @Deprecated
    public String readComponentJSON() {
        return this.getSerializers().asJson(this.readComponent());
    }

    public void writeString(String text) {
        this.writeString(text, 32767);
    }

    public void writeString(String text, int value) {
        this.writeString(text, value, true);
    }

    public void writeString(String text, int value, boolean matches) {
        if (matches) {
            text = StringUtil.maximizeLength(text, value);
        }
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        if (!matches && bytes.length > value) {
            throw new IllegalStateException(
                    "String too big (was " + bytes.length + " bytes encoded, max " + value + ")");
        }
        this.writeVarInt(bytes.length);
        ByteBufHelper.writeBytes(this.buffer, bytes);
    }

    public AdventureSerializer getSerializers() {
        return AdventureSerializer.serializer(this);
    }

    @Deprecated
    public void writeComponentJSON(String text) {
        this.writeComponent(this.getSerializers().fromJson(text));
    }

    public Component readComponent() {
        return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)
                ? this.readComponentAsNBT()
                : this.readComponentAsJSON();
    }

    public Component readComponentAsNBT() {
        NBT nBT = this.readNBTRaw();
        return this.getSerializers().fromNbtTag(nBT, this);
    }

    public Component readComponentAsJSON() {
        String text = this.readString(this.getMaxMessageLength());
        return this.getSerializers().fromJson(text);
    }

    public void writeComponent(Component component) {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.writeComponentAsNBT(component);
        } else {
            this.writeComponentAsJSON(component);
        }
    }

    public void writeComponentAsNBT(Component component) {
        this.writeNBTRaw(this.getSerializers().asNbtTag(component, this));
    }

    public void writeComponentAsJSON(Component component) {
        String text = this.getSerializers().asJson(component);
        this.writeString(text, this.getMaxMessageLength());
    }

    public Style readStyle() {
        return this.getSerializers().nbt().deserializeStyle(this.readNBT(), this);
    }

    public void writeStyle(Style style) {
        this.writeNBT(this.getSerializers().nbt().serializeStyle(style, this));
    }

    public ResourceLocation readIdentifier(int value) {
        return new ResourceLocation(this.readString(value));
    }

    public ResourceLocation readIdentifier() {
        return this.readIdentifier(32767);
    }

    public void writeIdentifier(ResourceLocation resourceLocation, int value) {
        this.writeString(resourceLocation.toString(), value);
    }

    public void writeIdentifier(ResourceLocation resourceLocation) {
        this.writeIdentifier(resourceLocation, 32767);
    }

    public int readUnsignedShort() {
        return ByteBufHelper.readUnsignedShort(this.buffer);
    }

    public short readShort() {
        return ByteBufHelper.readShort(this.buffer);
    }

    public void writeShort(int value) {
        ByteBufHelper.writeShort(this.buffer, value);
    }

    public void writeShortLE(int value) {
        ByteBufHelper.writeShortLE(this.buffer, value);
    }

    public int readVarShort() {
        int value = this.readUnsignedShort();
        short value2 = 0;
        if ((value & 32768) != 0) {
            value &= 32767;
            value2 = this.readUnsignedByte();
        }
        return (value2 & 0xFF) << 15 | value;
    }

    public void writeVarShort(int value) {
        int value2 = value & 32767;
        int value3;
        if ((value3 = (value & 8355840) >> 15) != 0) {
            value2 |= 32768;
        }
        this.writeShort(value2);
        if (value3 != 0) {
            this.writeByte(value3);
        }
    }

    public long readLong() {
        return ByteBufHelper.readLong(this.buffer);
    }

    public void writeLong(long value) {
        ByteBufHelper.writeLong(this.buffer, value);
    }

    public long readVarLong() {
        long value = 0L;
        int value2 = 0;
        byte value3;
        while (((value3 = this.readByte()) & 128) == 128) {
            value |= (long) (value3 & 127) << value2++ * 7;
        }
        return value | (long) (value3 & 127) << value2 * 7;
    }

    public void writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int) (value & 127L) | 128);
            value >>>= 7;
        }
        this.writeByte((int) value);
    }

    public float readFloat() {
        return ByteBufHelper.readFloat(this.buffer);
    }

    public void writeFloat(float value) {
        ByteBufHelper.writeFloat(this.buffer, value);
    }

    public double readDouble() {
        return ByteBufHelper.readDouble(this.buffer);
    }

    public void writeDouble(double value) {
        ByteBufHelper.writeDouble(this.buffer, value);
    }

    public byte[] readRemainingBytes() {
        return this.readBytes(ByteBufHelper.readableBytes(this.buffer));
    }

    public byte[] readBytes(int value) {
        byte[] values = new byte[value];
        ByteBufHelper.readBytes(this.buffer, values);
        return values;
    }

    public void writeBytes(byte[] values) {
        ByteBufHelper.writeBytes(this.buffer, values);
    }

    public byte[] readByteArray(int value) {
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new PacketLimitException(
                    PacketLimitException.Limit.BYTE_ARRAY_LENGTH, value2, value);
        } else {
            return this.readBytes(value2);
        }
    }

    public byte[] readByteArray() {
        return this.readByteArray(ByteBufHelper.readableBytes(this.buffer));
    }

    public void writeByteArray(byte[] values) {
        this.writeVarInt(values.length);
        this.writeBytes(values);
    }

    public int[] readVarIntArray() {
        int value = ByteBufHelper.readableBytes(this.buffer);
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new IllegalStateException(
                    "VarIntArray with size " + value2 + " is bigger than allowed " + value);
        }
        int[] values = new int[value2];
        for (int index = 0; index < value2; index++) {
            values[index] = this.readVarInt();
        }
        return values;
    }

    public void writeVarIntArray(int[] values) {
        this.writeVarInt(values.length);
        int value = values.length;
        for (int index = 0; index < value; index++) {
            int value2 = values[index];
            this.writeVarInt(value2);
        }
    }

    public long[] readLongArray(int value) {
        long[] values = new long[value];
        for (int index = 0; index < values.length; index++) {
            values[index] = this.readLong();
        }
        return values;
    }

    public byte[] readByteArrayOfSize(int value) {
        byte[] values = new byte[value];
        ByteBufHelper.readBytes(this.buffer, values);
        return values;
    }

    public void writeByteArrayOfSize(byte[] values) {
        ByteBufHelper.writeBytes(this.buffer, values);
    }

    public int[] readVarIntArrayOfSize(int value) {
        int[] values = new int[value];
        for (int index = 0; index < values.length; index++) {
            values[index] = this.readVarInt();
        }
        return values;
    }

    public void writeVarIntArrayOfSize(int[] values) {
        int value = values.length;
        for (int index = 0; index < value; index++) {
            int value2 = values[index];
            this.writeVarInt(value2);
        }
    }

    public long[] readLongArray() {
        int value = ByteBufHelper.readableBytes(this.buffer) / 8;
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new IllegalStateException(
                    "LongArray with size " + value2 + " is bigger than allowed " + value);
        } else {
            return this.readLongArray(new long[value2]);
        }
    }

    public long[] readLongArray(long[] values) {
        for (int index = 0; index < values.length; index++) {
            values[index] = this.readLong();
        }
        return values;
    }

    public void writeLongArray(long[] values) {
        this.writeVarInt(values.length);
        int value = values.length;
        for (int index = 0; index < value; index++) {
            long value2 = values[index];
            this.writeLong(value2);
        }
    }

    public UUID readUUID() {
        long value = this.readLong();
        long value2 = this.readLong();
        return new UUID(value, value2);
    }

    public void writeUUID(UUID uUID) {
        this.writeLong(uUID.getMostSignificantBits());
        this.writeLong(uUID.getLeastSignificantBits());
    }

    public Vector3i readBlockPosition() {
        long value = this.readLong();
        return new Vector3i(value, this.serverVersion);
    }

    public void writeBlockPosition(Vector3i vector3i) {
        long serializedPosition = vector3i.getSerializedPosition(this.serverVersion);
        this.writeLong(serializedPosition);
    }

    public GameMode readGameMode() {
        return GameMode.getById(this.readByte());
    }

    public void writeGameMode(@Nullable GameMode gameMode) {
        int value = gameMode == null ? -1 : gameMode.getId();
        this.writeByte(value);
    }

    public List<EntityData<?>> readEntityMetadata() {
        ArrayList<EntityData<?>> values = new ArrayList<>();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            boolean newerThanOrEquals =
                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10);
            short value;
            while ((value = this.readUnsignedByte()) != 255) {
                int value2 = newerThanOrEquals ? this.readVarInt() : this.readUnsignedByte();
                EntityDataType entityDataType;
                if ((entityDataType =
                                EntityDataTypes.getById(
                                        this.serverVersion.toClientVersion(), value2))
                        == null) {
                    throw new IllegalStateException(
                            "Unknown entity metadata type id: "
                                    + value2
                                    + " version "
                                    + this.serverVersion.toClientVersion());
                }
                values.add(new EntityData<>(value, entityDataType, entityDataType.read(this)));
            }
        } else {
            for (byte value3 = this.readByte(); value3 != 127; value3 = this.readByte()) {
                int value4 = (value3 & 224) >> 5;
                int value5 = value3 & 31;
                EntityDataType byId =
                        EntityDataTypes.getById(this.serverVersion.toClientVersion(), value4);
                EntityData entityData = new EntityData<>(value5, byId, byId.read(this));
                values.add(entityData);
            }
        }
        return values;
    }

    public void writeEntityMetadata(List<EntityData<?>> values) {
        if (values == null) {
            values = new ArrayList();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            boolean newerThanOrEquals =
                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_10);
            for (EntityData<?> entityData : values) {
                this.writeByte(entityData.getIndex());
                if (newerThanOrEquals) {
                    this.writeVarInt(
                            entityData.getType().getId(this.serverVersion.toClientVersion()));
                } else {
                    this.writeByte(
                            entityData.getType().getId(this.serverVersion.toClientVersion()));
                }
                ((it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                        .ComponentType)
                                entityData.getType())
                        .write(this, entityData.getValue());
            }
            this.writeByte(255);
        } else {
            Iterator<EntityData<?>> iterator2 = values.iterator();
            while (iterator2.hasNext()) {
                EntityData entityData2;
                int id =
                        (entityData2 = (EntityData) iterator2.next())
                                .getType()
                                .getId(this.serverVersion.toClientVersion());
                int index = entityData2.getIndex();
                int value = (id << 5 | index & 31) & 0xFF;
                this.writeByte(value);
                entityData2.getType().write(this, entityData2.getValue());
            }
            this.writeByte(127);
        }
    }

    public void writeEntityMetadata(EntityMetadataProvider entityMetadataProvider) {
        this.writeEntityMetadata(
                entityMetadataProvider.entityData(this.serverVersion.toClientVersion()));
    }

    @Deprecated
    public Dimension readDimension() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            return new Dimension(this.readVarInt());
        }
        if (!this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                && !this.serverVersion.isOlderThan(ServerVersion.V_1_16_2)) {
            return new Dimension(this.readNBT());
        }
        Dimension dimension;
        (dimension = new Dimension(new NBTCompound()))
                .setDimensionName(this.readIdentifier().toString());
        return dimension;
    }

    @Deprecated
    public void writeDimension(Dimension dimension) {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            this.writeVarInt(dimension.getId());
        } else {
            if (!this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                    && !this.serverVersion.isOlderThan(ServerVersion.V_1_16_2)) {
                this.writeNBT(dimension.getAttributes());
            } else {
                this.writeString(dimension.getDimensionName(), 32767);
            }
        }
    }

    public SaltSignature readSaltSignature() {
        long value = this.readLong();
        byte[] values;
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            if (this.readBoolean()) {
                values = this.readBytes(256);
            } else {
                values = new byte[0];
            }
        } else {
            values = this.readByteArray(256);
        }
        return new SaltSignature(value, values);
    }

    public void writeSaltSignature(SaltSignature saltSignature) {
        this.writeLong(saltSignature.getSalt());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            boolean matches = saltSignature.getSignature().length != 0;
            this.writeBoolean(matches);
            if (matches) {
                this.writeBytes(saltSignature.getSignature());
            }
        } else {
            this.writeByteArray(saltSignature.getSignature());
        }
    }

    public PublicKey readPublicKey() {
        return MinecraftEncryptionUtil.publicKey(this.readByteArray(512));
    }

    public void writePublicKey(PublicKey publicKey) {
        this.writeByteArray(publicKey.getEncoded());
    }

    public PublicProfileKey readPublicProfileKey() {
        Instant instant = this.readTimestamp();
        PublicKey publicKey = this.readPublicKey();
        byte[] values = this.readByteArray(4096);
        return new PublicProfileKey(instant, publicKey, values);
    }

    public void writePublicProfileKey(PublicProfileKey publicProfileKey) {
        this.writeTimestamp(publicProfileKey.getExpiresAt());
        this.writePublicKey(publicProfileKey.getKey());
        this.writeByteArray(publicProfileKey.getKeySignature());
    }

    public RemoteChatSession readRemoteChatSession() {
        return new RemoteChatSession(this.readUUID(), this.readPublicProfileKey());
    }

    public void writeRemoteChatSession(RemoteChatSession remoteChatSession) {
        this.writeUUID(remoteChatSession.getSessionId());
        this.writePublicProfileKey(remoteChatSession.getPublicProfileKey());
    }

    public Instant readTimestamp() {
        return Instant.ofEpochMilli(this.readLong());
    }

    public void writeTimestamp(Instant instant) {
        this.writeLong(instant.toEpochMilli());
    }

    public SignatureData readSignatureData() {
        return new SignatureData(
                this.readTimestamp(), this.readPublicKey(), this.readByteArray(4096));
    }

    public void writeSignatureData(SignatureData signatureData) {
        this.writeTimestamp(signatureData.getTimestamp());
        this.writePublicKey(signatureData.getPublicKey());
        this.writeByteArray(signatureData.getSignature());
    }

    public static <K> IntFunction<K> limitValue(IntFunction<K> intFunction, int value) {
        return result -> {
            if (result > value) {
                throw new PacketLimitException(
                        PacketLimitException.Limit.COLLECTION_SIZE, result, value);
            } else {
                return (K) ((IntFunction) intFunction).apply(result);
            }
        };
    }

    public WorldBlockPosition readWorldBlockPosition() {
        return new WorldBlockPosition(this.readIdentifier(), this.readBlockPosition());
    }

    public void writeWorldBlockPosition(WorldBlockPosition worldBlockPosition) {
        this.writeIdentifier(worldBlockPosition.getWorld());
        this.writeBlockPosition(worldBlockPosition.getBlockPosition());
    }

    public LastSeenMessages.Entry readLastSeenMessagesEntry() {
        return new LastSeenMessages.Entry(this.readUUID(), this.readByteArray());
    }

    public void writeLastMessagesEntry(LastSeenMessages.Entry entry) {
        this.writeUUID(entry.getUUID());
        this.writeByteArray(entry.getLastVerifier());
    }

    public LastSeenMessages.Update readLastSeenMessagesUpdate() {
        int value = this.readVarInt();
        BitSet bitSet = BitSet.valueOf(this.readBytes(3));
        byte value2 =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? this.readByte()
                        : 0;
        return new LastSeenMessages.Update(value, bitSet, value2);
    }

    public void writeLastSeenMessagesUpdate(LastSeenMessages.Update update) {
        this.writeVarInt(update.getOffset());
        this.writeBytes(Arrays.copyOf(update.getAcknowledged().toByteArray(), 3));
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            this.writeByte(update.getChecksum());
        }
    }

    public LastSeenMessages.LegacyUpdate readLegacyLastSeenMessagesUpdate() {
        LastSeenMessages lastSeenMessages = this.readLastSeenMessages();
        LastSeenMessages.Entry entry = this.readOptional(PacketWrapper::readLastSeenMessagesEntry);
        return new LastSeenMessages.LegacyUpdate(lastSeenMessages, entry);
    }

    public void writeLegacyLastSeenMessagesUpdate(LastSeenMessages.LegacyUpdate legacyUpdate) {
        this.writeLastSeenMessages(legacyUpdate.getLastSeenMessages());
        this.writeOptional(legacyUpdate.getLastReceived(), PacketWrapper::writeLastMessagesEntry);
    }

    public MessageSignature readMessageSignature() {
        return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_3)
                ? new MessageSignature(this.readBytes(256))
                : new MessageSignature(this.readByteArray());
    }

    public void writeMessageSignature(MessageSignature messageSignature) {
        this.writeBytes(messageSignature.getBytes());
    }

    public MessageSignature.Packed readMessageSignaturePacked() {
        int value;
        return (value = this.readVarInt() - 1) == -1
                ? new MessageSignature.Packed(new MessageSignature(this.readBytes(256)))
                : new MessageSignature.Packed(value);
    }

    public void writeMessageSignaturePacked(MessageSignature.Packed packed) {
        this.writeVarInt(packed.getId() + 1);
        if (packed.getFullSignature().isPresent()) {
            this.writeBytes(packed.getFullSignature().get().getBytes());
        }
    }

    public LastSeenMessages.Packed readLastSeenMessagesPacked() {
        List values =
                this.readCollection(
                        limitValue(ArrayList::new, 20), PacketWrapper::readMessageSignaturePacked);
        return new LastSeenMessages.Packed(values);
    }

    public void writeLastSeenMessagesPacked(LastSeenMessages.Packed packed) {
        this.writeCollection(
                packed.getPackedMessageSignatures(), PacketWrapper::writeMessageSignaturePacked);
    }

    public LastSeenMessages readLastSeenMessages() {
        List values =
                this.readCollection(
                        limitValue(ArrayList::new, 5), PacketWrapper::readLastSeenMessagesEntry);
        return new LastSeenMessages(values);
    }

    public void writeLastSeenMessages(LastSeenMessages lastSeenMessages) {
        this.writeCollection(lastSeenMessages.getEntries(), PacketWrapper::writeLastMessagesEntry);
    }

    public List<SignedCommandArgument> readSignedCommandArguments() {
        IntFunction intFunction = limitValue(ArrayList::new, 8);
        return ((List<SignedCommandArgument>)
                this.readCollection(
                        intFunction,
                        result ->
                                new SignedCommandArgument(
                                        this.readString(16), this.readMessageSignature())));
    }

    public void writeSignedCommandArguments(List<SignedCommandArgument> values) {
        this.writeCollection(
                values,
                (result, result2) -> {
                    this.writeString(result2.getArgument(), 16);
                    this.writeMessageSignature(result2.getSignature());
                });
    }

    public BitSet readBitSet() {
        return BitSet.valueOf(this.readLongArray());
    }

    public void writeBitSet(BitSet bitSet) {
        this.writeLongArray(bitSet.toLongArray());
    }

    public FilterMask readFilterMask() {
        FilterMaskType byId = FilterMaskType.getById(this.readVarInt());
        switch (byId) {
            case PARTIALLY_FILTERED:
                if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
                    BitSet bitSet = BitSet.valueOf(this.readByteArray());
                    return new FilterMask(bitSet);
                }
                return new FilterMask(this.readBitSet());
            case PASS_THROUGH:
                return FilterMask.PASS_THROUGH;
            case FULLY_FILTERED:
                return FilterMask.FULLY_FILTERED;
            default:
                return null;
        }
    }

    public void writeFilterMask(FilterMask filterMask) {
        this.writeVarInt(filterMask.getType().getId());
        if (filterMask.getType() == FilterMaskType.PARTIALLY_FILTERED) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
                this.writeByteArray(filterMask.getMask().toByteArray());
            } else {
                this.writeBitSet(filterMask.getMask());
            }
        }
    }

    public MerchantOffer readMerchantOffer() {
        ItemStack itemStack = MerchantItemCost.readItem(this);
        ItemStack itemStack2 = this.readItemStack();
        ItemStack itemStack3 =
                !this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)
                                && !this.getServerVersion().isOlderThan(ServerVersion.V_1_19)
                        ? this.readItemStack()
                        : this.readOptional(MerchantItemCost::readItem);
        boolean matches = this.readBoolean();
        int value = this.readInt();
        int value2 = this.readInt();
        int value3 = this.readInt();
        int value4 = this.readInt();
        float value5 = this.readFloat();
        int value6 = this.readInt();
        MerchantOffer merchantOffer =
                MerchantOffer.of(
                        itemStack,
                        itemStack3,
                        itemStack2,
                        value,
                        value2,
                        value3,
                        value4,
                        value5,
                        value6);
        if (matches) {
            merchantOffer.setUses(merchantOffer.getMaxUses());
        }
        return merchantOffer;
    }

    public void writeMerchantOffer(MerchantOffer merchantOffer) {
        MerchantItemCost.writeItem(this, merchantOffer.getFirstInputItem());
        this.writeItemStack(merchantOffer.getOutputItem());
        ItemStack itemStack;
        if ((itemStack = merchantOffer.getSecondInputItem()) != null && itemStack.isEmpty()) {
            itemStack = null;
        }
        if (!this.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_5)
                && !this.getServerVersion().isOlderThan(ServerVersion.V_1_19)) {
            this.writeItemStack(itemStack);
        } else {
            this.writeOptional(itemStack, MerchantItemCost::writeItem);
        }
        this.writeBoolean(merchantOffer.getUses() >= merchantOffer.getMaxUses());
        this.writeInt(merchantOffer.getUses());
        this.writeInt(merchantOffer.getMaxUses());
        this.writeInt(merchantOffer.getXp());
        this.writeInt(merchantOffer.getSpecialPrice());
        this.writeFloat(merchantOffer.getPriceMultiplier());
        this.writeInt(merchantOffer.getDemand());
    }

    public ChatType.Bound readChatTypeBoundNetwork() {
        ChatType chatType =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)
                        ? this.readMappedEntityOrDirect(
                                ChatTypes.getRegistry(), ChatType::readDirect)
                        : this.readMappedEntity(ChatTypes.getRegistry());
        Component component = this.readComponent();
        Component component2 = this.readOptional(PacketWrapper::readComponent);
        return new ChatType.Bound(chatType, component, component2);
    }

    public void writeChatTypeBoundNetwork(ChatType.Bound bound) {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
            this.writeMappedEntityOrDirect(bound.getType(), ChatType::writeDirect);
        } else {
            this.writeMappedEntity(bound.getType());
        }
        this.writeComponent(bound.getName());
        this.writeOptional(bound.getTargetName(), PacketWrapper::writeComponent);
    }

    public Node readNode() {
        byte value;
        int value2 = (value = this.readByte()) & 3;
        List values = this.readList(PacketWrapper::readVarInt);
        int value3 = (value & 8) != 0 ? this.readVarInt() : 0;
        if (value2 == 2) {
            String text = this.readString();
            Parsers.Parser parser;
            List values2 =
                    (parser =
                                    this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                                            ? this.readMappedEntity(Parsers::getById)
                                            : Parsers.getByName(this.readIdentifier().toString()))
                            .readProperties(this)
                            .orElse(null);
            ResourceLocation resourceLocation = (value & 16) != 0 ? this.readIdentifier() : null;
            return new Node(value, values, value3, text, parser, values2, resourceLocation);
        } else if (value2 == 1) {
            String text2 = this.readString();
            return new Node(value, values, value3, text2, (Parsers.Parser) null, null, null);
        } else {
            return new Node(value, values, value3, null, (Parsers.Parser) null, null, null);
        }
    }

    public void writeNode(Node node) {
        this.writeByte(node.getFlags());
        this.writeList(node.getChildren(), PacketWrapper::writeVarInt);
        if ((node.getFlags() & 8) != 0) {
            this.writeVarInt(node.getRedirectNodeIndex());
        }
        Optional<String> name = node.getName();
        name.ifPresent(this::writeString);
        if (node.getParser().isPresent()) {
            Parsers.Parser parser = node.getParser().get();
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                this.writeMappedEntity(parser);
            } else {
                this.writeIdentifier(parser.getName());
            }
            if (node.getProperties().isPresent()) {
                parser.writeProperties(this, node.getProperties().get());
            }
        }
        Optional<ResourceLocation> suggestions = node.getSuggestionsType();
        suggestions.ifPresent(this::writeIdentifier);
    }

    public KnownPack readKnownPack() {
        String text = this.readString();
        String text2 = this.readString();
        String text3 = this.readString();
        return new KnownPack(text, text2, text3);
    }

    public void writeKnownPack(KnownPack knownPack) {
        this.writeString(knownPack.getNamespace());
        this.writeString(knownPack.getId());
        this.writeString(knownPack.getVersion());
    }

    public <T extends Enum<T>> EnumSet<T> readEnumSet(Class<T> classValue) {
        Enum[] enums;
        byte[] values =
                new byte
                        [-Math.floorDiv(
                                -(enums = (Enum[]) classValue.getEnumConstants()).length, 8)];
        ByteBufHelper.readBytes(this.getBuffer(), values);
        BitSet bitSet = BitSet.valueOf(values);
        EnumSet values2 = EnumSet.noneOf(classValue);
        for (int index = 0; index < enums.length; index++) {
            if (bitSet.get(index)) {
                values2.add(enums[index]);
            }
        }
        return values2;
    }

    public <T extends Enum<T>> void writeEnumSet(EnumSet<T> values, Class<T> classValue) {
        Enum[] enums = (Enum[]) classValue.getEnumConstants();
        BitSet bitSet = new BitSet(enums.length);
        for (int index = 0; index < enums.length; index++) {
            if (values.contains(enums[index])) {
                bitSet.set(index);
            }
        }
        this.writeBytes(Arrays.copyOf(bitSet.toByteArray(), -Math.floorDiv(-enums.length, 8)));
    }

    @Experimental
    public <U, V, R> U readMultiVersional(
            VersionComparison versionComparison,
            ServerVersion serverVersion2,
            PacketWrapper.Reader<V> reader,
            PacketWrapper.Reader<R> reader2) {
        return (U)
                (this.serverVersion.is(versionComparison, serverVersion2)
                        ? reader.apply(this)
                        : reader2.apply(this));
    }

    @Experimental
    public <V> void writeMultiVersional(
            VersionComparison versionComparison,
            ServerVersion serverVersion2,
            V v,
            PacketWrapper.Writer<V> writer,
            PacketWrapper.Writer<V> writer2) {
        if (this.serverVersion.is(versionComparison, serverVersion2)) {
            writer.accept(this, ((V) (PacketWrapper<T>) v));
        } else {
            writer2.accept(this, ((V) (PacketWrapper<T>) v));
        }
    }

    @Nullable
    public <R> R readOptional(PacketWrapper.Reader<R> reader) {
        return (R) (this.readBoolean() ? reader.apply(this) : null);
    }

    public <V> void writeOptional(@Nullable V v, PacketWrapper.Writer<V> writer) {
        if (v != null) {
            this.writeBoolean(true);
            writer.accept(this, ((V) (PacketWrapper<T>) v));
        } else {
            this.writeBoolean(false);
        }
    }

    public <R> Optional<R> readJavaOptional(PacketWrapper.Reader<R> reader) {
        return this.readBoolean() ? Optional.of(reader.apply(this)) : Optional.empty();
    }

    public <V> void writeJavaOptional(Optional<V> optional, PacketWrapper.Writer<V> writer) {
        if (optional.isPresent()) {
            this.writeBoolean(true);
            writer.accept(this, ((V) (PacketWrapper<T>) optional.get()));
        } else {
            this.writeBoolean(false);
        }
    }

    public <K, C extends Collection<K>> C readCollection(
            IntFunction<C> intFunction, PacketWrapper.Reader<K> reader, int value) {
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new PacketLimitException(
                    PacketLimitException.Limit.COLLECTION_SIZE, value2, value);
        } else {
            return this.readFixedCollection(intFunction, reader, value2);
        }
    }

    public <K, C extends Collection<K>> C readFixedCollection(
            IntFunction<C> intFunction, PacketWrapper.Reader<K> reader, int value) {
        Collection<K> values = (Collection) intFunction.apply(value);
        for (int index = 0; index < value; index++) {
            values.add(reader.apply(this));
        }
        return (C) values;
    }

    public <K, C extends Collection<K>> C readCollection(
            IntFunction<C> intFunction, PacketWrapper.Reader<K> reader) {
        return this.readCollection(intFunction, reader, 2048);
    }

    public <K> void writeCollection(Collection<K> values, PacketWrapper.Writer<K> writer) {
        this.writeVarInt(values.size());
        for (Object result : values) {
            writer.accept(this, ((K) (PacketWrapper<T>) result));
        }
    }

    public <K> List<K> readList(PacketWrapper.Reader<K> reader) {
        return this.readCollection(ArrayList::new, reader);
    }

    public <K> List<K> readList(PacketWrapper.Reader<K> reader, int value) {
        return this.readCollection(ArrayList::new, reader, value);
    }

    public <K> void writeList(List<K> values, PacketWrapper.Writer<K> writer) {
        this.writeVarInt(values.size());
        for (Object result : values) {
            writer.accept(this, ((K) (PacketWrapper<T>) result));
        }
    }

    public <K> Set<K> readSet(PacketWrapper.Reader<K> reader) {
        return this.readCollection(HashSet::new, reader);
    }

    public <K> Set<K> readSet(PacketWrapper.Reader<K> reader, int value) {
        return this.readCollection(HashSet::new, reader, value);
    }

    public <K> void writeSet(Set<K> values, PacketWrapper.Writer<K> writer) {
        this.writeVarInt(values.size());
        for (Object result : values) {
            writer.accept(this, ((K) (PacketWrapper<T>) result));
        }
    }

    public <K> K[] readArray(PacketWrapper.Reader<K> reader, Class<K> classValue) {
        int value;
        if ((value = this.readVarInt()) > 2048) {
            throw new PacketLimitException(PacketLimitException.Limit.ARRAY_SIZE, value, 2048L);
        }
        Object[] results = (Object[]) Array.newInstance(classValue, value);
        for (int index = 0; index < value; index++) {
            results[index] = reader.apply(this);
        }
        return (K[]) results;
    }

    public <K> void writeArray(K[] ks, PacketWrapper.Writer<K> writer) {
        this.writeVarInt(ks.length);
        int value = ks.length;
        for (int index = 0; index < value; index++) {
            Object result = ((Object[]) ks)[index];
            writer.accept(this, ((K) (PacketWrapper<T>) result));
        }
    }

    public <K> void writeFixedCollection(Collection<K> values, PacketWrapper.Writer<K> writer) {
        for (Object result : values) {
            writer.accept(this, ((K) (PacketWrapper<T>) result));
        }
    }

    public <K> List<K> readFixedList(PacketWrapper.Reader<K> reader, int value) {
        return this.readFixedCollection(ArrayList::new, reader, value);
    }

    public <K> void writeFixedList(List<K> values, PacketWrapper.Writer<K> writer) {
        this.writeFixedCollection(values, writer);
    }

    public <Z extends Enum<?>> Z readEnum(Class<Z> classValue) {
        return this.readEnum((Z[]) classValue.getEnumConstants());
    }

    public <Z extends Enum<?>> Z readEnum(Z[] zs) {
        return (Z) zs[this.readVarInt()];
    }

    public <Z extends Enum<?>> Z readEnum(Class<Z> classValue, Z z) {
        return this.readEnum((Z[]) classValue.getEnumConstants(), (Z) z);
    }

    public <Z extends Enum<?>> Z readEnum(Z[] zs, Z z) {
        int value;
        return (Z) ((value = this.readVarInt()) >= 0 && value < zs.length ? zs[value] : z);
    }

    public void writeEnum(Enum<?> enumValue) {
        this.writeVarInt(enumValue.ordinal());
    }

    public <Z extends MappedEntity> Z readMappedEntity(
            BiFunction<ClientVersion, Integer, Z> biFunction) {
        int value = this.readVarInt();
        MappedEntity mappedEntity;
        if ((mappedEntity =
                        (MappedEntity)
                                biFunction.apply(this.serverVersion.toClientVersion(), value))
                == null) {
            throw new IllegalStateException(
                    "Can't find mapped entity with id " + value + " using " + biFunction);
        } else {
            return (Z) mappedEntity;
        }
    }

    public <Z extends MappedEntity> Z replaceRegistry(IRegistry<Z> registry, Z z) {
        IRegistry registry2;
        return (Z)
                ((registry2 = this.replaceRegistry(registry)) != registry
                        ? registry2.getByNameOrThrow(
                                this.serverVersion.toClientVersion(), z.getName())
                        : z);
    }

    public <Z extends MappedEntity> IRegistry<Z> replaceRegistry(IRegistry<Z> registry) {
        return this.getRegistryHolder()
                .getRegistryOr(registry, this.serverVersion.toClientVersion());
    }

    public IRegistryHolder getRegistryHolder() {
        return this.user != null ? this.user : this.registries;
    }

    @Internal
    public void setRegistryHolder(IRegistryHolder registryHolder) {
        this.registries = registryHolder;
    }

    public <Z extends MappedEntity> Z readMappedEntityOrDirect(
            BiFunction<ClientVersion, Integer, Z> biFunction, PacketWrapper.Reader<Z> reader) {
        int value;
        if ((value = this.readVarInt()) == 0) {
            return (Z) reader.apply(this);
        } else {
            MappedEntity mappedEntity;
            if ((mappedEntity =
                            (MappedEntity)
                                    biFunction.apply(
                                            this.serverVersion.toClientVersion(), value - 1))
                    == null) {
                throw new IllegalStateException(
                        "Can't find mapped entity with id " + value + " using " + biFunction);
            } else {
                return (Z) mappedEntity;
            }
        }
    }

    public <Z extends MappedEntity> Z readMappedEntity(IRegistry<Z> registry) {
        return this.readMappedEntity(
                (BiFunction<ClientVersion, Integer, Z>) this.replaceRegistry(registry));
    }

    public <Z extends MappedEntity> Z readMappedEntityOrDirect(
            IRegistry<Z> registry, PacketWrapper.Reader<Z> reader) {
        return this.readMappedEntityOrDirect(
                (BiFunction<ClientVersion, Integer, Z>) this.replaceRegistry(registry), reader);
    }

    public void writeMappedEntity(MappedEntity mappedEntity) {
        if (!mappedEntity.isRegistered()) {
            throw new IllegalArgumentException(
                    "Can't write id of unregistered entity "
                            + mappedEntity.getName()
                            + " ("
                            + mappedEntity
                            + ")");
        }
        this.writeVarInt(mappedEntity.getId(this.serverVersion.toClientVersion()));
    }

    public <Z extends MappedEntity> void writeMappedEntityOrDirect(
            Z z, PacketWrapper.Writer<Z> writer) {
        if (!z.isRegistered()) {
            this.writeVarInt(0);
            writer.accept(this, z);
        } else {
            int id = z.getId(this.serverVersion.toClientVersion());
            this.writeVarInt(id + 1);
        }
    }

    public int readContainerId() {
        return this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)
                ? this.readVarInt()
                : this.readUnsignedByte();
    }

    public void writeContainerId(int value) {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
            this.writeVarInt(value);
        } else {
            this.writeByte(value);
        }
    }

    public <L, R> Either<L, R> readEither(
            PacketWrapper.Reader<L> reader, PacketWrapper.Reader<R> reader2) {
        return this.readBoolean()
                ? Either.createLeft((L) reader.apply(this))
                : Either.createRight((R) reader2.apply(this));
    }

    public <L, R> void writeEither(
            Either<L, R> either, PacketWrapper.Writer<L> writer, PacketWrapper.Writer<R> writer2) {
        if (either.isLeft()) {
            this.writeBoolean(true);
            writer.accept(this, ((L) (PacketWrapper<T>) either.getLeft()));
        } else {
            this.writeBoolean(false);
            writer2.accept(this, ((R) (PacketWrapper<T>) either.getRight()));
        }
    }

    public void writeRotation(float value) {
        this.writeByte((byte) MathUtil.floor(value * 256.0F / 360.0F));
    }

    public float readRotation() {
        return this.readByte() * 360 / 256.0F;
    }

    @Nullable
    public Integer readNullableVarInt() {
        int value;
        return (value = this.readVarInt()) == 0 ? null : value - 1;
    }

    public void writeNullableVarInt(@Nullable Integer value) {
        this.writeVarInt(value == null ? 0 : value + 1);
    }

    public <Z> Z readLengthPrefixed(int value, PacketWrapper.Reader<Z> reader) {
        int value2;
        if ((value2 = this.readVarInt()) > value) {
            throw new PacketLimitException(
                    PacketLimitException.Limit.LENGTH_PREFIXED_SIZE, value2, value);
        }
        Object result = this.buffer;
        try {
            this.buffer = ByteBufHelper.readSlice(result, value2);
            return (Z) reader.apply(this);
        } finally {
            this.buffer = result;
        }
    }

    public <Z> void writeLengthPrefixed(Z z, PacketWrapper.Writer<Z> writer) {
        Object result = ByteBufHelper.allocateNewBuffer(this.buffer);
        Object result2 = this.buffer;
        try {
            this.buffer = result;
            writer.accept(this, ((Z) (PacketWrapper<T>) z));
        } finally {
            this.buffer = result2;
        }
        this.writeVarInt(ByteBufHelper.readableBytes(result));
        ByteBufHelper.writeBytes(result2, result);
    }

    public ItemStack callItemStackDecodeEvent(ItemStack itemStack) {
        if (this.event != null) {
            ItemStackDecodeEvent itemStackDecodeEvent =
                    new ItemStackDecodeEvent(this, this.event, itemStack);
            PacketEvents.getAPI()
                    .getEventManager()
                    .callEvent(
                            itemStackDecodeEvent,
                            () -> {},
                            !this.event.isAutoProtocolTranslation());
            return itemStackDecodeEvent.getItemStack();
        } else {
            return itemStack;
        }
    }

    public void callReadSlotsEvent(int value) {
        if (this.event != null) {
            ReadSlotsEvent readSlotsEvent = new ReadSlotsEvent(this, this.event, value);
            PacketEvents.getAPI()
                    .getEventManager()
                    .callEvent(readSlotsEvent, () -> {}, !this.event.isAutoProtocolTranslation());
        }
    }

    private NBT callNBTDecodeEvent(NBT nBT) {
        if (this.event != null && nBT != null) {
            NBTDecodeEvent nBTDecodeEvent =
                    new NBTDecodeEvent(this, this.event, nBT, nBT.getDecodedSizeBytes());
            PacketEvents.getAPI()
                    .getEventManager()
                    .callEvent(nBTDecodeEvent, () -> {}, !this.event.isAutoProtocolTranslation());
            return nBTDecodeEvent.getNbt();
        } else {
            return nBT;
        }
    }

    public int getItemDepth() {
        return this.itemDepth;
    }

    public void enterItemDepth() {
        this.itemDepth++;
    }

    public void exitItemDepth() {
        this.itemDepth--;
    }

    public int getReadItemstacks() {
        return this.readItemstacks;
    }

    public int incrementReadItemstacks() {
        return ++this.readItemstacks;
    }

    @Nullable
    public ProtocolPacketEvent getProtocolEvent() {
        return this.event;
    }

    @FunctionalInterface
    public interface Reader<T> extends Function<PacketWrapper<?>, T> {}

    @FunctionalInterface
    public interface Writer<T> extends BiConsumer<PacketWrapper<?>, T> {}
}
