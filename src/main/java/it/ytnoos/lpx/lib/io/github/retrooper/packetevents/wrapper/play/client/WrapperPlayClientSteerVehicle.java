package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class WrapperPlayClientSteerVehicle extends PacketWrapper<WrapperPlayClientSteerVehicle> {

    private float sideways;

    private float forward;

    private byte flags;

    public WrapperPlayClientSteerVehicle(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSteerVehicle(float sideways, float forward, byte flags) {
        super(PacketType.Play.Client.STEER_VEHICLE);
        this.sideways = sideways;
        this.forward = forward;
        this.flags = flags;
    }

    @Override
    public void read() {
        this.sideways = this.readFloat();
        this.forward = this.readFloat();
        this.flags = this.readByte();
    }

    @Override
    public void write() {
        this.writeFloat(this.sideways);
        this.writeFloat(this.forward);
        this.writeByte(this.flags);
    }

    public void copy(WrapperPlayClientSteerVehicle wrapperPlayClientSteerVehicle) {
        this.sideways = wrapperPlayClientSteerVehicle.sideways;
        this.forward = wrapperPlayClientSteerVehicle.forward;
        this.flags = wrapperPlayClientSteerVehicle.flags;
    }

    public float getSideways() {
        return this.sideways;
    }

    public void setSideways(float sideways) {
        this.sideways = sideways;
    }

    public float getForward() {
        return this.forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public byte getFlags() {
        return this.flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public boolean isJump() {
        return (this.flags & 1) != 0;
    }

    public void setJump(boolean matches) {
        if (matches) {
            this.flags = (byte) (this.flags | 1);
        } else {
            this.flags = (byte) (this.flags & -2);
        }
    }

    public boolean isUnmount() {
        return (this.flags & 2) != 0;
    }

    public void setUnmount(boolean matches) {
        if (matches) {
            this.flags = (byte) (this.flags | 2);
        } else {
            this.flags = (byte) (this.flags & -3);
        }
    }
}
