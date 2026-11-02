package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.builtin;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSource;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.positionsource.PositionSourceTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.UniqueIdUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public class EntityPositionSource extends PositionSource {

    private static final UUID EMPTY_UNIQUE_ID = new UUID(0L, 0L);

    @Nullable private UUID entityUniqueId;

    private int entityId;

    private float offsetY;

    public EntityPositionSource(int value) {
        this(value, 0.0F);
    }

    public EntityPositionSource(int entityId, float offsetY) {
        super(PositionSourceTypes.ENTITY);
        this.entityId = entityId;
        this.offsetY = offsetY;
    }

    public EntityPositionSource(Optional<UUID> optional, float offsetY) {
        super(PositionSourceTypes.ENTITY);
        this.entityUniqueId = (UUID) optional.orElse(null);
        this.offsetY = offsetY;
    }

    public static EntityPositionSource read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        float value2 = 0.0F;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19)) {
            value2 = packetWrapper.readFloat();
        }
        return new EntityPositionSource(value, value2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, EntityPositionSource entityPositionSource) {
        packetWrapper.writeVarInt(entityPositionSource.entityId);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19)) {
            packetWrapper.writeFloat(entityPositionSource.offsetY);
        }
    }

    public static EntityPositionSource decodeSource(
            NBTCompound nBTCompound, ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_19)) {
            UUID uUID =
                    UniqueIdUtil.fromIntArray(
                            nBTCompound
                                    .getTagOfTypeOrThrow("source_entity", NBTIntArray.class)
                                    .getValue());
            NBTNumber nBTNumber;
            float value =
                    (nBTNumber = nBTCompound.getNumberTagOrNull("y_offset")) == null
                            ? 0.0F
                            : nBTNumber.getAsFloat();
            return new EntityPositionSource(Optional.of(uUID), value);
        } else {
            int asInt = nBTCompound.getNumberTagOrThrow("source_entity_id").getAsInt();
            return new EntityPositionSource(asInt);
        }
    }

    public static void encodeSource(
            EntityPositionSource entityPositionSource,
            ClientVersion clientVersion,
            NBTCompound nBTCompound) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_19)) {
            UUID uUID =
                    entityPositionSource.entityUniqueId != null
                            ? entityPositionSource.entityUniqueId
                            : EMPTY_UNIQUE_ID;
            nBTCompound.setTag("source_entity", new NBTIntArray(UniqueIdUtil.toIntArray(uUID)));
            nBTCompound.setTag("y_offset", new NBTFloat(entityPositionSource.offsetY));
        } else {
            nBTCompound.setTag("source_entity_id", new NBTInt(entityPositionSource.entityId));
        }
    }

    public Optional<UUID> getEntityUniqueId() {
        return Optional.ofNullable(this.entityUniqueId);
    }

    public void setEntityUniqueId(Optional<UUID> optional) {
        this.entityUniqueId = (UUID) optional.orElse(null);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public boolean equals(Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            EntityPositionSource entityPositionSource = (EntityPositionSource) result;
            if (this.entityId != entityPositionSource.entityId) {
                return false;
            } else {
                return Float.compare(entityPositionSource.offsetY, this.offsetY) != 0
                        ? false
                        : Objects.equals(this.entityUniqueId, entityPositionSource.entityUniqueId);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityUniqueId, this.entityId, this.offsetY);
    }
}
