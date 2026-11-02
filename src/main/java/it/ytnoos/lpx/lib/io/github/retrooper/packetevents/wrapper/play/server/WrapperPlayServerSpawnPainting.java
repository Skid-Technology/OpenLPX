package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Direction;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.PaintingType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class WrapperPlayServerSpawnPainting extends PacketWrapper<WrapperPlayServerSpawnPainting> {

    private int entityId;

    private UUID uuid;

    @Nullable private PaintingType type;

    private Vector3i position;

    private Direction direction;

    public WrapperPlayServerSpawnPainting(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnPainting(int value, Vector3i vector3i, Direction direction) {
        this(value, new UUID(0L, 0L), null, vector3i, direction);
    }

    public WrapperPlayServerSpawnPainting(
            int value, UUID uUID, Vector3i vector3i, Direction direction) {
        this(value, uUID, null, vector3i, direction);
    }

    public WrapperPlayServerSpawnPainting(
            int entityId,
            UUID uuid,
            @Nullable PaintingType paintingType,
            Vector3i vector3i,
            Direction direction2) {
        super(PacketType.Play.Server.SPAWN_PAINTING);
        this.entityId = entityId;
        this.uuid = uuid;
        this.type = paintingType;
        this.position = vector3i;
        this.direction = direction2;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.uuid = this.readUUID();
        } else {
            this.uuid = new UUID(0L, 0L);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.type = PaintingType.getById(this.readVarInt());
        } else {
            this.type = PaintingType.getByTitle(this.readString(13));
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.position = this.readBlockPosition();
        } else {
            int value = this.readInt();
            int value2 = this.readInt();
            int value3 = this.readInt();
            this.position = new Vector3i(value, value2, value3);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.direction = Direction.getByHorizontalIndex(this.readUnsignedByte());
        } else {
            this.direction = Direction.getByHorizontalIndex(this.readInt());
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeUUID(this.uuid);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            this.writeVarInt(this.type.getId());
        } else {
            this.writeString(this.type.getTitle(), 13);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            long serializedPosition = this.position.getSerializedPosition(this.serverVersion);
            this.writeLong(serializedPosition);
        } else {
            this.writeInt(this.position.x);
            this.writeShort(this.position.y);
            this.writeInt(this.position.z);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_8)) {
            this.writeByte(this.direction.getHorizontalIndex());
        } else {
            this.writeInt(this.direction.getHorizontalIndex());
        }
    }

    public void copy(WrapperPlayServerSpawnPainting wrapperPlayServerSpawnPainting) {
        this.entityId = wrapperPlayServerSpawnPainting.entityId;
        this.uuid = wrapperPlayServerSpawnPainting.uuid;
        this.type = wrapperPlayServerSpawnPainting.type;
        this.position = wrapperPlayServerSpawnPainting.position;
        this.direction = wrapperPlayServerSpawnPainting.direction;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public Optional<PaintingType> getType() {
        return Optional.ofNullable(this.type);
    }

    public void setType(@Nullable PaintingType paintingType) {
        this.type = paintingType;
    }

    public Vector3i getPosition() {
        return this.position;
    }

    public void setPosition(Vector3i vector3i) {
        this.position = vector3i;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction2) {
        this.direction = direction2;
    }
}
