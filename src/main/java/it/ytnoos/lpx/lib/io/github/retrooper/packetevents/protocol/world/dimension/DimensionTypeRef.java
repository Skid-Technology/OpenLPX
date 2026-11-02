package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerJoinGame;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
@NonExtendable
public interface DimensionTypeRef {

    @Deprecated
    default DimensionType resolve(IRegistry<DimensionType> registry, ClientVersion clientVersion) {
        return this.resolve(registry, PacketWrapper.createDummyWrapper(clientVersion));
    }

    DimensionType resolve(IRegistry<DimensionType> registry, PacketWrapper<?> packetWrapper);

    default ResourceLocation getName() {
        throw new UnsupportedOperationException();
    }

    default int getId() {
        throw new UnsupportedOperationException();
    }

    default NBT getData() {
        throw new UnsupportedOperationException();
    }

    static DimensionTypeRef read(PacketWrapper<?> packetWrapper) {
        ServerVersion serverVersion;
        if ((serverVersion = packetWrapper.getServerVersion())
                .isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            return new DimensionTypeRef.IdRef(packetWrapper.readVarInt());
        } else {
            boolean newerThanOrEquals = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2);
            if (!serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                    && (newerThanOrEquals
                            || !serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16))) {
                return newerThanOrEquals
                        ? new DimensionTypeRef.DataRef(packetWrapper.readNBTRaw())
                        : new DimensionTypeRef.IdRef(
                                packetWrapper instanceof WrapperPlayServerJoinGame
                                                && serverVersion.isOlderThan(ServerVersion.V_1_9_2)
                                        ? packetWrapper.readByte()
                                        : packetWrapper.readInt());
            } else {
                return new DimensionTypeRef.NameRef(packetWrapper.readIdentifier());
            }
        }
    }

    static void write(PacketWrapper<?> packetWrapper, DimensionTypeRef dimensionTypeRef) {
        ServerVersion serverVersion;
        if ((serverVersion = packetWrapper.getServerVersion())
                .isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            packetWrapper.writeVarInt(dimensionTypeRef.getId());
        } else {
            boolean newerThanOrEquals = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2);
            if (!serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)
                    && (newerThanOrEquals
                            || !serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16))) {
                if (newerThanOrEquals) {
                    packetWrapper.writeNBTRaw(dimensionTypeRef.getData());
                } else if (packetWrapper instanceof WrapperPlayServerJoinGame
                        && serverVersion.isOlderThan(ServerVersion.V_1_9_2)) {
                    packetWrapper.writeByte(dimensionTypeRef.getId());
                } else {
                    packetWrapper.writeInt(dimensionTypeRef.getId());
                }
            } else {
                packetWrapper.writeIdentifier(dimensionTypeRef.getName());
            }
        }
    }

    final class DataRef implements DimensionTypeRef {

        private final NBT data;

        public DataRef(NBT nBT) {
            this.data = nBT;
        }

        @Override
        public DimensionType resolve(
                IRegistry<DimensionType> registry, PacketWrapper<?> packetWrapper) {
            ResourceLocation resourceLocation;
            DimensionType dimensionType;
            return ((DimensionType)
                    ((resourceLocation = this.getNullableName()) != null
                                    && (dimensionType = registry.getByName(resourceLocation))
                                            != null
                            ? dimensionType
                            : DimensionType.CODEC.decode(this.data, packetWrapper)));
        }

        @Nullable
        public ResourceLocation getNullableName() {
            String text;
            return this.data instanceof NBTCompound
                            && (text = ((NBTCompound) this.data).getStringTagValueOrNull("effects"))
                                    != null
                    ? new ResourceLocation(text)
                    : null;
        }

        @Override
        public ResourceLocation getName() {
            ResourceLocation resourceLocation;
            return (resourceLocation = this.getNullableName()) != null
                    ? resourceLocation
                    : DimensionTypeRef.super.getName();
        }

        @Override
        public NBT getData() {
            return this.data;
        }
    }

    final class DirectRef implements DimensionTypeRef {

        private final DimensionType dimensionType;

        private final PacketWrapper<?> wrapper;

        @Deprecated
        public DirectRef(DimensionType dimensionType, ClientVersion clientVersion) {
            this(dimensionType, PacketWrapper.createDummyWrapper(clientVersion));
        }

        public DirectRef(DimensionType dimensionType2, PacketWrapper<?> packetWrapper) {
            this.dimensionType = dimensionType2;
            this.wrapper = packetWrapper;
        }

        @Override
        public DimensionType resolve(
                IRegistry<DimensionType> registry, PacketWrapper<?> packetWrapper) {
            if (packetWrapper.getServerVersion() != this.wrapper.getServerVersion()) {
                throw new IllegalArgumentException(
                        "Expected version "
                                + this.wrapper.getServerVersion()
                                + ", received "
                                + packetWrapper.getServerVersion()
                                + " for direct dimension type ref "
                                + this.dimensionType);
            } else {
                return this.dimensionType;
            }
        }

        @Override
        public ResourceLocation getName() {
            return this.dimensionType.getName();
        }

        @Override
        public int getId() {
            return this.dimensionType.getId(this.getVersion());
        }

        @Override
        public NBT getData() {
            return DimensionType.CODEC.encode(this.wrapper, this.dimensionType);
        }

        public DimensionType getDimensionType() {
            return this.dimensionType;
        }

        public ClientVersion getVersion() {
            return this.wrapper.getServerVersion().toClientVersion();
        }
    }

    final class IdRef implements DimensionTypeRef {

        private final int id;

        public IdRef(int id) {
            this.id = id;
        }

        @Override
        public DimensionType resolve(
                IRegistry<DimensionType> registry, PacketWrapper<?> packetWrapper) {
            ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
            DimensionType dimensionType;
            if ((dimensionType = (DimensionType) registry.getById(clientVersion, this.id))
                    == null) {
                PacketEvents.getAPI()
                        .getLogger()
                        .warning(
                                "Unknown dimension type id #"
                                        + this.id
                                        + " ("
                                        + clientVersion
                                        + "), falling back to overworld");
                dimensionType = (DimensionType) registry.getByIdOrThrow(clientVersion, 0);
            }
            return dimensionType;
        }

        @Override
        public int getId() {
            return this.id;
        }
    }

    final class NameRef implements DimensionTypeRef {

        private final ResourceLocation name;

        public NameRef(ResourceLocation resourceLocation) {
            this.name = resourceLocation;
        }

        @Override
        public DimensionType resolve(
                IRegistry<DimensionType> registry, PacketWrapper<?> packetWrapper) {
            ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
            DimensionType dimensionType;
            if ((dimensionType = (DimensionType) registry.getByName(clientVersion, this.name))
                    == null) {
                PacketEvents.getAPI()
                        .getLogger()
                        .warning(
                                "Unknown dimension type '"
                                        + this.name
                                        + "' ("
                                        + clientVersion
                                        + "), falling back to overworld");
                dimensionType = (DimensionType) registry.getByIdOrThrow(clientVersion, 0);
            }
            return dimensionType;
        }

        @Override
        public ResourceLocation getName() {
            return this.name;
        }
    }
}
