package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerPlayerRotation
        extends PacketWrapper<WrapperPlayServerPlayerRotation> {

    private float yaw;

    private boolean relativeYaw;

    private float pitch;

    private boolean relativePitch;

    public WrapperPlayServerPlayerRotation(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerRotation(float value, float value2) {
        this(value, false, value2, false);
    }

    public WrapperPlayServerPlayerRotation(
            float yaw, boolean relativeYaw, float pitch, boolean relativePitch) {
        super(PacketType.Play.Server.PLAYER_ROTATION);
        this.yaw = yaw;
        this.relativeYaw = relativeYaw;
        this.pitch = pitch;
        this.relativePitch = relativePitch;
    }

    @Override
    public void read() {
        this.yaw = this.readFloat();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.relativeYaw = this.readBoolean();
        }
        this.pitch = this.readFloat();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.relativePitch = this.readBoolean();
        }
    }

    @Override
    public void write() {
        this.writeFloat(this.yaw);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.writeBoolean(this.relativeYaw);
        }
        this.writeFloat(this.pitch);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_9)) {
            this.writeBoolean(this.relativePitch);
        }
    }

    public void copy(WrapperPlayServerPlayerRotation wrapperPlayServerPlayerRotation) {
        this.yaw = wrapperPlayServerPlayerRotation.yaw;
        this.relativeYaw = wrapperPlayServerPlayerRotation.relativeYaw;
        this.pitch = wrapperPlayServerPlayerRotation.pitch;
        this.relativePitch = wrapperPlayServerPlayerRotation.relativePitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public boolean isRelativeYaw() {
        return this.relativeYaw;
    }

    public void setRelativeYaw(boolean relativeYaw) {
        this.relativeYaw = relativeYaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isRelativePitch() {
        return this.relativePitch;
    }

    public void setRelativePitch(boolean relativePitch) {
        this.relativePitch = relativePitch;
    }
}
