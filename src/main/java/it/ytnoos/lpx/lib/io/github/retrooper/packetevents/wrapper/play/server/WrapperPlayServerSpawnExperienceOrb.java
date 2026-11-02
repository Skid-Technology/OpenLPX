package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class WrapperPlayServerSpawnExperienceOrb
        extends PacketWrapper<WrapperPlayServerSpawnExperienceOrb> {

    private int entityId;

    private double x;

    private double y;

    private double z;

    private short count;

    public WrapperPlayServerSpawnExperienceOrb(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSpawnExperienceOrb(
            int entityId, double x, double y, double z, short count) {
        super(PacketType.Play.Server.SPAWN_EXPERIENCE_ORB);
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.x = this.readDouble();
            this.y = this.readDouble();
            this.z = this.readDouble();
        } else {
            this.x = this.readInt() / 32.0;
            this.y = this.readInt() / 32.0;
            this.z = this.readInt() / 32.0;
        }
        this.count = this.readShort();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_9)) {
            this.writeDouble(this.x);
            this.writeDouble(this.y);
            this.writeDouble(this.z);
        } else {
            this.writeInt(MathUtil.floor(this.x * 32.0));
            this.writeInt(MathUtil.floor(this.y * 32.0));
            this.writeInt(MathUtil.floor(this.z * 32.0));
        }
        this.writeShort(this.count);
    }

    public void copy(WrapperPlayServerSpawnExperienceOrb wrapperPlayServerSpawnExperienceOrb) {
        this.entityId = wrapperPlayServerSpawnExperienceOrb.entityId;
        this.x = wrapperPlayServerSpawnExperienceOrb.x;
        this.y = wrapperPlayServerSpawnExperienceOrb.y;
        this.z = wrapperPlayServerSpawnExperienceOrb.z;
        this.count = wrapperPlayServerSpawnExperienceOrb.count;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
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

    public short getCount() {
        return this.count;
    }

    public void setCount(short count) {
        this.count = count;
    }
}
