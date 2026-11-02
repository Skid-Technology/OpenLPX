package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWorldBorderCenter
        extends PacketWrapper<WrapperPlayServerWorldBorderCenter> {

    private double x;

    private double z;

    public WrapperPlayServerWorldBorderCenter(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWorldBorderCenter(double x, double z) {
        super(PacketType.Play.Server.WORLD_BORDER_CENTER);
        this.x = x;
        this.z = z;
    }

    @Override
    public void read() {
        this.x = this.readDouble();
        this.z = this.readDouble();
    }

    @Override
    public void write() {
        this.writeDouble(this.x);
        this.writeDouble(this.z);
    }

    public void copy(WrapperPlayServerWorldBorderCenter wrapperPlayServerWorldBorderCenter) {
        this.x = wrapperPlayServerWorldBorderCenter.x;
        this.z = wrapperPlayServerWorldBorderCenter.z;
    }

    public double getX() {
        return this.x;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
