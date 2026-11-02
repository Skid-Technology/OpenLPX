package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public class WrapperPlayClientTeleportConfirm
        extends PacketWrapper<WrapperPlayClientTeleportConfirm> {

    private int teleportId;

    private double x;

    private double y;

    private double z;

    private float yaw;

    private float pitch;

    public WrapperPlayClientTeleportConfirm(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    @Obsolete
    public WrapperPlayClientTeleportConfirm(int teleportId) {
        super(PacketType.Play.Client.TELEPORT_CONFIRM);
        this.teleportId = teleportId;
    }

    public WrapperPlayClientTeleportConfirm(
            int teleportId, double x, double y, double z, float yaw, float pitch) {
        super(PacketType.Play.Client.TELEPORT_CONFIRM);
        this.teleportId = teleportId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void read() {
        this.teleportId = this.readVarInt();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.x = this.readDouble();
            this.y = this.readDouble();
            this.z = this.readDouble();
            this.yaw = this.readFloat();
            this.pitch = this.readFloat();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.teleportId);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_26_3)) {
            this.writeDouble(this.x);
            this.writeDouble(this.y);
            this.writeDouble(this.z);
            this.writeFloat(this.yaw);
            this.writeFloat(this.pitch);
        }
    }

    public void copy(WrapperPlayClientTeleportConfirm wrapperPlayClientTeleportConfirm) {
        this.teleportId = wrapperPlayClientTeleportConfirm.teleportId;
        this.x = wrapperPlayClientTeleportConfirm.x;
        this.y = wrapperPlayClientTeleportConfirm.y;
        this.z = wrapperPlayClientTeleportConfirm.z;
        this.yaw = wrapperPlayClientTeleportConfirm.yaw;
        this.pitch = wrapperPlayClientTeleportConfirm.pitch;
    }

    public int getTeleportId() {
        return this.teleportId;
    }

    public void setTeleportId(int teleportId) {
        this.teleportId = teleportId;
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

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
