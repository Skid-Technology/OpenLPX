package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerProjectilePower
        extends PacketWrapper<WrapperPlayServerProjectilePower> {

    private int entityId;

    private double powerX;

    private double powerY;

    private double powerZ;

    public WrapperPlayServerProjectilePower(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerProjectilePower(int value, double value2) {
        this(value, value2, value2, value2);
    }

    public WrapperPlayServerProjectilePower(
            int entityId, double powerX, double powerY, double powerZ) {
        super(PacketType.Play.Server.PROJECTILE_POWER);
        this.entityId = entityId;
        this.powerX = powerX;
        this.powerY = powerY;
        this.powerZ = powerZ;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
            this.setPower(this.readDouble());
        } else {
            this.powerX = this.readDouble();
            this.powerY = this.readDouble();
            this.powerZ = this.readDouble();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21)) {
            this.writeDouble(this.getPower());
        } else {
            this.writeDouble(this.powerX);
            this.writeDouble(this.powerY);
            this.writeDouble(this.powerZ);
        }
    }

    public void copy(WrapperPlayServerProjectilePower wrapperPlayServerProjectilePower) {
        this.entityId = wrapperPlayServerProjectilePower.entityId;
        this.powerX = wrapperPlayServerProjectilePower.powerX;
        this.powerY = wrapperPlayServerProjectilePower.powerY;
        this.powerZ = wrapperPlayServerProjectilePower.powerZ;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public double getPower() {
        return this.powerX;
    }

    public void setPower(double value) {
        this.powerX = value;
        this.powerY = value;
        this.powerZ = value;
    }

    public double getPowerX() {
        return this.powerX;
    }

    public void setPowerX(double powerX) {
        this.powerX = powerX;
    }

    public double getPowerY() {
        return this.powerY;
    }

    public void setPowerY(double powerY) {
        this.powerY = powerY;
    }

    public double getPowerZ() {
        return this.powerZ;
    }

    public void setPowerZ(double powerZ) {
        this.powerZ = powerZ;
    }
}
