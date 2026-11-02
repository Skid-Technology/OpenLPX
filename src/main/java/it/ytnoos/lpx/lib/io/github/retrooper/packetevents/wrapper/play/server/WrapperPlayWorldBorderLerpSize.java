package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayWorldBorderLerpSize extends PacketWrapper<WrapperPlayWorldBorderLerpSize> {

    private double oldDiameter;

    private double newDiameter;

    private long speed;

    public WrapperPlayWorldBorderLerpSize(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayWorldBorderLerpSize(double oldDiameter, double newDiameter, long speed) {
        super(PacketType.Play.Server.WORLD_BORDER_LERP_SIZE);
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.speed = speed;
    }

    @Override
    public void read() {
        this.oldDiameter = this.readDouble();
        this.newDiameter = this.readDouble();
        this.speed = this.readVarLong();
    }

    @Override
    public void write() {
        this.writeDouble(this.oldDiameter);
        this.writeDouble(this.newDiameter);
        this.writeVarLong(this.speed);
    }

    public void copy(WrapperPlayWorldBorderLerpSize wrapperPlayWorldBorderLerpSize) {
        this.oldDiameter = wrapperPlayWorldBorderLerpSize.oldDiameter;
        this.newDiameter = wrapperPlayWorldBorderLerpSize.newDiameter;
        this.speed = wrapperPlayWorldBorderLerpSize.speed;
    }

    public double getOldDiameter() {
        return this.oldDiameter;
    }

    public double getNewDiameter() {
        return this.newDiameter;
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setOldDiameter(double oldDiameter) {
        this.oldDiameter = oldDiameter;
    }

    public void setNewDiameter(double newDiameter) {
        this.newDiameter = newDiameter;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }
}
