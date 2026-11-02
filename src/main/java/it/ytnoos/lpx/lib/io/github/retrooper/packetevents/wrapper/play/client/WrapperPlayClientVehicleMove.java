package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientVehicleMove extends PacketWrapper<WrapperPlayClientVehicleMove> {

    private Vector3d position;

    private float yaw;

    private float pitch;

    private boolean onGround;

    public WrapperPlayClientVehicleMove(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientVehicleMove(Vector3d vector3d, float value, float value2) {
        this(vector3d, value, value2, false);
    }

    public WrapperPlayClientVehicleMove(
            Vector3d vector3d, float yaw, float pitch, boolean onGround) {
        super(PacketType.Play.Client.VEHICLE_MOVE);
        this.position = vector3d;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void read() {
        this.position = Vector3d.read(this);
        this.yaw = this.readFloat();
        this.pitch = this.readFloat();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            this.onGround = this.readBoolean();
        }
    }

    @Override
    public void write() {
        Vector3d.write(this, this.position);
        this.writeFloat(this.yaw);
        this.writeFloat(this.pitch);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            this.writeBoolean(this.onGround);
        }
    }

    public void copy(WrapperPlayClientVehicleMove wrapperPlayClientVehicleMove) {
        this.position = wrapperPlayClientVehicleMove.position;
        this.yaw = wrapperPlayClientVehicleMove.yaw;
        this.pitch = wrapperPlayClientVehicleMove.pitch;
        this.onGround = wrapperPlayClientVehicleMove.onGround;
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

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
