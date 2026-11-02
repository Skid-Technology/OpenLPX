package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSpawnWeatherEntity
        extends PacketWrapper<WrapperPlayServerSpawnWeatherEntity> {

    private int entityId;

    private byte type;

    private double x;

    private double y;

    private double z;

    public WrapperPlayServerSpawnWeatherEntity(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnWeatherEntity(
            int entityId, byte type, double x, double y, double z) {
        super(PacketType.Play.Server.SPAWN_WEATHER_ENTITY);
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.type = this.readByte();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.x = this.readDouble();
            this.y = this.readDouble();
            this.z = this.readDouble();
        } else {
            this.x = this.readInt() / 32.0;
            this.y = this.readInt() / 32.0;
            this.z = this.readInt() / 32.0;
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeByte(this.type);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeDouble(this.x);
            this.writeDouble(this.y);
            this.writeDouble(this.z);
        } else {
            this.writeInt(MathUtil.floor(this.x * 32.0));
            this.writeInt(MathUtil.floor(this.y * 32.0));
            this.writeInt(MathUtil.floor(this.z * 32.0));
        }
    }

    public void copy(WrapperPlayServerSpawnWeatherEntity wrapperPlayServerSpawnWeatherEntity) {
        this.entityId = wrapperPlayServerSpawnWeatherEntity.entityId;
        this.type = wrapperPlayServerSpawnWeatherEntity.type;
        this.x = wrapperPlayServerSpawnWeatherEntity.x;
        this.y = wrapperPlayServerSpawnWeatherEntity.y;
        this.z = wrapperPlayServerSpawnWeatherEntity.z;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
