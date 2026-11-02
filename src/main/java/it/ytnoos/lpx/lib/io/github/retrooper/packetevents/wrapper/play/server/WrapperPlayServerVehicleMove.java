package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerVehicleMove extends PacketWrapper<WrapperPlayServerVehicleMove> {

    private Vector3d position;

    private float yaw;

    private float pitch;

    public WrapperPlayServerVehicleMove(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerVehicleMove(Vector3d vector3d, float yaw, float pitch) {
        super(PacketType.Play.Server.VEHICLE_MOVE);
        this.position = vector3d;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void read() {
        this.position = new Vector3d(this.readDouble(), this.readDouble(), this.readDouble());
        this.yaw = this.readFloat();
        this.pitch = this.readFloat();
    }

    @Override
    public void write() {
        this.writeDouble(this.position.x);
        this.writeDouble(this.position.y);
        this.writeDouble(this.position.z);
        this.writeFloat(this.yaw);
        this.writeFloat(this.pitch);
    }

    public void copy(WrapperPlayServerVehicleMove wrapperPlayServerVehicleMove) {
        this.position = wrapperPlayServerVehicleMove.position;
        this.yaw = wrapperPlayServerVehicleMove.yaw;
        this.pitch = wrapperPlayServerVehicleMove.pitch;
    }

    public Vector3d getPosition() {
        return this.position;
    }

    public void setPosition(Vector3d vector3d) {
        this.position = vector3d;
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
