package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSourceType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSourceTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin.BlockPositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin.EntityPositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ParticleVibrationData extends ParticleData {

    private Vector3i startingPosition;

    private PositionSource source;

    private int ticks;

    public ParticleVibrationData(@Nullable Vector3i vector3i, Vector3i vector3i2, int value) {
        this(vector3i, new BlockPositionSource(vector3i2), value);
    }

    public ParticleVibrationData(@Nullable Vector3i vector3i, int value, int value2) {
        this(vector3i, new EntityPositionSource(value), value2);
    }

    public ParticleVibrationData(@Nullable Vector3i vector3i, int value, float value2, int value3) {
        this(vector3i, new EntityPositionSource(value, value2), value3);
    }

    public ParticleVibrationData(PositionSource positionSource, int value) {
        this(null, positionSource, value);
    }

    public ParticleVibrationData(
            @Nullable Vector3i vector3i, PositionSource positionSource, int ticks) {
        this.startingPosition = vector3i;
        this.source = positionSource;
        this.ticks = ticks;
    }

    public static ParticleVibrationData read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_4)
                        ? Vector3i.zero()
                        : packetWrapper.readBlockPosition();
        PositionSourceType positionSourceType;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            positionSourceType = packetWrapper.readMappedEntity(PositionSourceTypes::getById);
        } else {
            String text;
            if ((positionSourceType =
                            PositionSourceTypes.getByName(text = packetWrapper.readString()))
                    == null) {
                throw new IllegalArgumentException("Illegal position type: " + text);
            }
        }
        PositionSource positionSource = positionSourceType.read(packetWrapper);
        int value = packetWrapper.readVarInt();
        return new ParticleVibrationData(vector3i, positionSource, value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ParticleVibrationData particleVibrationData) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_19_4)) {
            packetWrapper.writeBlockPosition(particleVibrationData.getStartingPosition());
        }
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            packetWrapper.writeMappedEntity(particleVibrationData.getSourceType());
        } else {
            packetWrapper.writeIdentifier(particleVibrationData.getType().getName());
        }
        ((PositionSourceType<PositionSource>) particleVibrationData.getSourceType())
                .write(packetWrapper, particleVibrationData.getSource());
        packetWrapper.writeVarInt(particleVibrationData.getTicks());
    }

    public static ParticleVibrationData decode(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        Vector3i vector3i =
                clientVersion.isNewerThanOrEquals(ClientVersion.V_1_19)
                        ? null
                        : new Vector3i(
                                nBTCompound
                                        .getTagOfTypeOrThrow("origin", NBTIntArray.class)
                                        .getValue());
        PositionSource positionSource =
                PositionSource.decode(
                        nBTCompound.getCompoundTagOrThrow("destination"), clientVersion);
        int asInt = nBTCompound.getNumberTagOrThrow("arrival_in_ticks").getAsInt();
        return new ParticleVibrationData(vector3i, positionSource, asInt);
    }

    public static void encode(
            ParticleVibrationData particleVibrationData,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        Vector3i vector3i;
        if (clientVersion.isOlderThan(ClientVersion.V_1_19)
                && (vector3i = particleVibrationData.getStartingPosition()) != null) {
            nBTCompound.setTag(
                    "origin", new NBTIntArray(new int[] {vector3i.x, vector3i.y, vector3i.z}));
        }
        nBTCompound.setTag(
                "destination", PositionSource.encode(particleVibrationData.source, clientVersion));
        nBTCompound.setTag("arrival_in_ticks", new NBTInt(particleVibrationData.ticks));
    }

    @Obsolete
    public Vector3i getStartingPosition() {
        return this.startingPosition;
    }

    @Obsolete
    public void setStartingPosition(Vector3i vector3i) {
        this.startingPosition = vector3i;
    }

    @Deprecated
    public ParticleVibrationData.PositionType getType() {
        return ParticleVibrationData.PositionType.byModern(this.source.getType());
    }

    public PositionSourceType<?> getSourceType() {
        return this.source.getType();
    }

    public PositionSource getSource() {
        return this.source;
    }

    public Optional<Vector3i> getBlockPosition() {
        return this.source instanceof BlockPositionSource
                ? Optional.of(((BlockPositionSource) this.source).getPos())
                : Optional.empty();
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.source = new BlockPositionSource(vector3i);
    }

    public Optional<Integer> getEntityId() {
        return this.source instanceof EntityPositionSource
                ? Optional.of(((EntityPositionSource) this.source).getEntityId())
                : Optional.empty();
    }

    public void setEntityId(int value) {
        float value2 = this.getEntityEyeHeight().orElse(0.0F);
        this.source = new EntityPositionSource(value, value2);
    }

    public Optional<Float> getEntityEyeHeight() {
        return this.source instanceof EntityPositionSource
                ? Optional.of(((EntityPositionSource) this.source).getOffsetY())
                : Optional.empty();
    }

    public void setEntityEyeHeight(Float value) {
        this.setEntityEyeHeight(value == null ? 0.0F : value);
    }

    public void setEntityEyeHeight(float value) {
        int value2 = this.getEntityId().orElse(0);
        this.source = new EntityPositionSource(value2, value);
    }

    public int getTicks() {
        return this.ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            ParticleVibrationData particleVibrationData = (ParticleVibrationData) result;
            if (this.ticks != particleVibrationData.ticks) {
                return false;
            } else {
                return !this.startingPosition.equals(particleVibrationData.startingPosition)
                        ? false
                        : this.source.equals(particleVibrationData.source);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startingPosition, this.source, this.ticks);
    }

    @Deprecated
    public enum PositionType implements MappedEntity {
        BLOCK(PositionSourceTypes.BLOCK),
        ENTITY(PositionSourceTypes.ENTITY);

        private final PositionSourceType<?> type;

        PositionType(PositionSourceType<?> positionSourceType) {
            this.type = positionSourceType;
        }

        @Contract("null -> null; !null -> !null")
        @Nullable
        public static ParticleVibrationData.PositionType byModern(
                @Nullable PositionSourceType<?> positionSourceType) {
            if (positionSourceType == null) {
                return null;
            }
            ParticleVibrationData.PositionType[] positionTypes;
            int value = (positionTypes = values()).length;
            for (int index = 0; index < value; index++) {
                ParticleVibrationData.PositionType positionType;
                if ((positionType = positionTypes[index]).type == positionSourceType) {
                    return positionType;
                }
            }
            throw new UnsupportedOperationException(
                    "Unsupported modern type: " + positionSourceType.getName());
        }

        public static ParticleVibrationData.PositionType getById(int value) {
            return byModern(
                    PositionSourceTypes.getById(
                            PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                            value));
        }

        @Nullable
        public static ParticleVibrationData.PositionType getByName(String text) {
            return getByName(new ResourceLocation(text));
        }

        @Nullable
        public static ParticleVibrationData.PositionType getByName(
                ResourceLocation resourceLocation) {
            return byModern(PositionSourceTypes.getByName(resourceLocation.toString()));
        }

        @Override
        public ResourceLocation getName() {
            return this.type.getName();
        }

        @Override
        public int getId(ClientVersion clientVersion) {
            return this.type.getId(clientVersion);
        }
    }
}
