package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@NullMarked
public class MappedEntitySet<T extends MappedEntity> implements MappedEntityRefSet<T> {

    @Nullable private final ResourceLocation tagKey;

    @Nullable private final List<T> entities;

    public MappedEntitySet(ResourceLocation resourceLocation) {
        this(resourceLocation, null);
    }

    public MappedEntitySet(List<T> values) {
        this(null, values);
    }

    public MappedEntitySet(
            @Nullable ResourceLocation resourceLocation, @Nullable List<T> entities) {
        if (resourceLocation == null && entities == null) {
            throw new IllegalArgumentException(
                    "Illegal generic holder set: either tag key or holder ids have to be set");
        }
        this.tagKey = resourceLocation;
        this.entities = entities;
    }

    public static <Z extends MappedEntity> MappedEntitySet<Z> createEmpty() {
        return new MappedEntitySet<>(new ArrayList<>(0));
    }

    public static <Z extends MappedEntity> NbtCodec<MappedEntitySet<Z>> codec(
            final IRegistry<Z> registry) {
        return new NbtCodec<MappedEntitySet<Z>>() {

            public MappedEntitySet<Z> decode(NBT result, PacketWrapper<?> result2)
                    throws NbtCodecException {
                return MappedEntitySet.decode(result, result2, registry);
            }

            public NBT encode(PacketWrapper<?> result, MappedEntitySet<Z> result2)
                    throws NbtCodecException {
                return MappedEntitySet.encode(result, result2);
            }
        };
    }

    public static <Z extends MappedEntity> MappedEntityRefSet<Z> readRefSet(
            PacketWrapper<?> packetWrapper) {
        int value;
        if ((value = packetWrapper.readVarInt() - 1) == -1) {
            return new MappedEntitySet<>(packetWrapper.readIdentifier());
        }
        int[] values = packetWrapper.readVarIntArrayOfSize(Math.min(value, 65536));
        return new MappedEntitySet.IdRefSetImpl<>(values);
    }

    public static void writeRefSet(
            PacketWrapper<?> packetWrapper, MappedEntityRefSet<?> mappedEntityRefSet) {
        if (mappedEntityRefSet instanceof MappedEntitySet.IdRefSetImpl) {
            MappedEntitySet.IdRefSetImpl idRefSetImpl =
                    (MappedEntitySet.IdRefSetImpl) mappedEntityRefSet;
            packetWrapper.writeVarInt(idRefSetImpl.entries.length + 1);
            packetWrapper.writeVarIntArrayOfSize(idRefSetImpl.entries);
        } else {
            if (!(mappedEntityRefSet instanceof MappedEntitySet)) {
                throw new UnsupportedOperationException(
                        "Unsupported mapped entity reference set implementation: "
                                + mappedEntityRefSet);
            }
            write(packetWrapper, (MappedEntitySet) mappedEntityRefSet);
        }
    }

    public static <Z extends MappedEntity> MappedEntitySet<Z> read(
            PacketWrapper<?> packetWrapper, IRegistry<Z> registry) {
        IRegistry registry2 = packetWrapper.replaceRegistry(registry);
        return read(packetWrapper, (BiFunction<ClientVersion, Integer, Z>) registry2);
    }

    public static <Z extends MappedEntity> MappedEntitySet<Z> read(
            PacketWrapper<?> packetWrapper, BiFunction<ClientVersion, Integer, Z> biFunction) {
        int value;
        if ((value = packetWrapper.readVarInt() - 1) == -1) {
            return new MappedEntitySet<>(packetWrapper.readIdentifier(), null);
        }
        ArrayList<Z> values = new ArrayList<>(Math.min(value, 65536));
        for (int index = 0; index < value; index++) {
            values.add(packetWrapper.readMappedEntity(biFunction));
        }
        return new MappedEntitySet<>(null, values);
    }

    public static <Z extends MappedEntity> void write(
            PacketWrapper<?> packetWrapper, MappedEntitySet<Z> mappedEntitySet) {
        if (mappedEntitySet.tagKey != null) {
            packetWrapper.writeVarInt(0);
            packetWrapper.writeIdentifier(mappedEntitySet.tagKey);
        } else {
            assert mappedEntitySet.entities != null;
            packetWrapper.writeVarInt(mappedEntitySet.entities.size() + 1);
            for (MappedEntity mappedEntity : mappedEntitySet.entities) {
                packetWrapper.writeMappedEntity(mappedEntity);
            }
        }
    }

    @Deprecated
    public static <Z extends MappedEntity> MappedEntitySet<Z> decode(
            NBT nBT, ClientVersion clientVersion, IRegistry<Z> registry) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion), registry);
    }

    public static <Z extends MappedEntity> MappedEntitySet<Z> decode(
            NBT nBT, PacketWrapper<?> packetWrapper, IRegistry<Z> registry) {
        IRegistry<Z> registry2 = packetWrapper.replaceRegistry(registry);
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        ArrayList<Z> values;
        if (nBT instanceof NBTString) {
            String text;
            if (!(text = ((NBTString) nBT).getValue()).isEmpty() && text.charAt(0) == '#') {
                String text2 = text.substring(1);
                ResourceLocation resourceLocation = new ResourceLocation(text2);
                return new MappedEntitySet<>(resourceLocation);
            }
            values = new ArrayList(1);
            ResourceLocation resourceLocation2 = new ResourceLocation(text);
            values.add(registry2.getByNameOrThrow(clientVersion, resourceLocation2));
        } else {
            NBTList nBTList = (NBTList) nBT;
            values = new ArrayList(nBTList.size());
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) nBTList.getTags()))) {
                ResourceLocation resourceLocation3 =
                        new ResourceLocation(((NBTString) nBT2).getValue());
                values.add(registry2.getByNameOrThrow(clientVersion, resourceLocation3));
            }
        }
        return new MappedEntitySet<>(values);
    }

    @Deprecated
    public static <Z extends MappedEntity> NBT encode(
            MappedEntitySet<Z> mappedEntitySet, ClientVersion clientVersion) {
        return encodeRefSet(PacketWrapper.createDummyWrapper(clientVersion), mappedEntitySet);
    }

    public static <Z extends MappedEntity> NBT encode(
            PacketWrapper<?> packetWrapper, MappedEntitySet<Z> mappedEntitySet) {
        if (mappedEntitySet.tagKey != null) {
            return new NBTString("#" + mappedEntitySet.tagKey);
        }
        assert mappedEntitySet.entities != null;
        NBTList nBTList = NBTList.createStringList();
        for (MappedEntity mappedEntity : mappedEntitySet.entities) {
            nBTList.addTag(new NBTString(mappedEntity.getName().toString()));
        }
        return nBTList;
    }

    public static <Z extends MappedEntity> MappedEntityRefSet<Z> decodeRefSet(
            NBT nBT, PacketWrapper<?> packetWrapper) {
        return decodeRefSet(nBT, packetWrapper.getServerVersion().toClientVersion());
    }

    @Deprecated
    public static <Z extends MappedEntity> MappedEntityRefSet<Z> decodeRefSet(
            NBT nBT, ClientVersion clientVersion) {
        List<String> values;
        if (nBT instanceof NBTString) {
            String text;
            if (!(text = ((NBTString) nBT).getValue()).isEmpty() && text.charAt(0) == '#') {
                String text2 = text.substring(1);
                ResourceLocation resourceLocation = new ResourceLocation(text2);
                return new MappedEntitySet<>(resourceLocation);
            }
            values = Collections.singletonList(text);
        } else {
            NBTList nBTList = (NBTList) nBT;
            values = new ArrayList(nBTList.size());
            for (NBT nBT2 : ((Iterable<NBT>) ((Iterable<?>) nBTList.getTags()))) {
                values.add(((NBTString) nBT2).getValue());
            }
        }
        return new MappedEntitySet.NameRefSetImpl<>(values);
    }

    public static <Z extends MappedEntity> NBT encodeRefSet(
            PacketWrapper<?> packetWrapper, MappedEntityRefSet<Z> mappedEntityRefSet) {
        return encodeRefSet(mappedEntityRefSet, packetWrapper.getServerVersion().toClientVersion());
    }

    @Deprecated
    public static <Z extends MappedEntity> NBT encodeRefSet(
            MappedEntityRefSet<Z> mappedEntityRefSet, ClientVersion clientVersion) {
        if (!(mappedEntityRefSet instanceof MappedEntitySet.NameRefSetImpl)) {
            if (mappedEntityRefSet instanceof MappedEntitySet) {
                return encode((MappedEntitySet<Z>) mappedEntityRefSet, clientVersion);
            } else {
                throw new UnsupportedOperationException(
                        "Unsupported mapped entity reference set implementation: "
                                + mappedEntityRefSet);
            }
        } else {
            MappedEntitySet.NameRefSetImpl nameRefSetImpl =
                    (MappedEntitySet.NameRefSetImpl) mappedEntityRefSet;
            NBTList nBTList = NBTList.createStringList();
            for (String text : ((Iterable<String>) ((Iterable<?>) nameRefSetImpl.entries))) {
                nBTList.addTag(new NBTString(text));
            }
            return nBTList;
        }
    }

    @Override
    public MappedEntitySet<T> resolve(PacketWrapper<?> packetWrapper, IRegistry<T> registry) {
        return this;
    }

    @Override
    public MappedEntitySet<T> resolve(
            ClientVersion clientVersion, IRegistryHolder registryHolder, IRegistry<T> registry) {
        return this;
    }

    @Override
    public MappedEntitySet<T> resolve(ClientVersion clientVersion, IRegistry<T> registry) {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.entities != null && this.entities.isEmpty();
    }

    @Nullable
    public ResourceLocation getTagKey() {
        return this.tagKey;
    }

    @Nullable
    public List<T> getEntities() {
        return this.entities;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof MappedEntitySet)) {
            return false;
        }
        MappedEntitySet mappedEntitySet = (MappedEntitySet) result;
        return !Objects.equals(this.tagKey, mappedEntitySet.tagKey)
                ? false
                : Objects.equals(this.entities, mappedEntitySet.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tagKey, this.entities);
    }

    @Override
    public String toString() {
        return "MappedEntitySet{tagKey=" + this.tagKey + ", entities=" + this.entities + '}';
    }

    private static final class IdRefSetImpl<T extends MappedEntity>
            implements MappedEntityRefSet<T> {

        private final int[] entries;

        public IdRefSetImpl(int[] entries) {
            this.entries = entries;
        }

        @Override
        public MappedEntitySet<T> resolve(ClientVersion clientVersion, IRegistry<T> registry) {
            ArrayList<T> values = new ArrayList<>(this.entries.length);
            int[] values2 = this.entries;
            int value = this.entries.length;
            for (int index = 0; index < value; index++) {
                int value2 = values2[index];
                values.add(registry.getByIdOrThrow(clientVersion, value2));
            }
            return new MappedEntitySet<>(values);
        }

        @Override
        public boolean isEmpty() {
            return this.entries.length == 0;
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof MappedEntitySet.IdRefSetImpl)) {
                return false;
            }
            MappedEntitySet.IdRefSetImpl idRefSetImpl = (MappedEntitySet.IdRefSetImpl) result;
            return Arrays.equals(this.entries, idRefSetImpl.entries);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.entries);
        }

        @Override
        public String toString() {
            return "IdRefSetImpl{entries=" + Arrays.toString(this.entries) + 125;
        }
    }

    private static final class NameRefSetImpl<T extends MappedEntity>
            implements MappedEntityRefSet<T> {

        private final List<String> entries;

        public NameRefSetImpl(List<String> entries) {
            this.entries = entries;
        }

        @Override
        public MappedEntitySet<T> resolve(ClientVersion clientVersion, IRegistry<T> registry) {
            ArrayList<T> values = new ArrayList<>(this.entries.size());
            for (String text : this.entries) {
                values.add(registry.getByNameOrThrow(clientVersion, text));
            }
            return new MappedEntitySet<>(values);
        }

        @Override
        public boolean isEmpty() {
            return this.entries.isEmpty();
        }

        @Override
        public boolean equals(Object result) {
            if (!(result instanceof MappedEntitySet.NameRefSetImpl)) {
                return false;
            }
            MappedEntitySet.NameRefSetImpl nameRefSetImpl = (MappedEntitySet.NameRefSetImpl) result;
            return this.entries.equals(nameRefSetImpl.entries);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.entries);
        }

        @Override
        public String toString() {
            return "NameRefSetImpl{entries=" + this.entries + 125;
        }
    }
}
