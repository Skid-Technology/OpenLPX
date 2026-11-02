package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerWorldBorderSize
        extends PacketWrapper<WrapperPlayServerWorldBorderSize> {

    private double diameter;

    public WrapperPlayServerWorldBorderSize(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWorldBorderSize(double diameter) {
        super(PacketType.Play.Server.WORLD_BORDER_SIZE);
        this.diameter = diameter;
    }

    @Override
    public void read() {
        this.diameter = this.readDouble();
    }

    @Override
    public void write() {
        this.writeDouble(this.diameter);
    }

    public void copy(WrapperPlayServerWorldBorderSize wrapperPlayServerWorldBorderSize) {
        this.diameter = wrapperPlayServerWorldBorderSize.diameter;
    }

    public double getDiameter() {
        return this.diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
}
