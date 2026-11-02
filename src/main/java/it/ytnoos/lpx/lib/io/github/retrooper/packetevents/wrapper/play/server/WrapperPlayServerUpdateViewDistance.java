package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerUpdateViewDistance
        extends PacketWrapper<WrapperPlayServerUpdateViewDistance> {

    private int viewDistance;

    public WrapperPlayServerUpdateViewDistance(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerUpdateViewDistance(int viewDistance) {
        super(PacketType.Play.Server.UPDATE_VIEW_DISTANCE);
        this.viewDistance = viewDistance;
    }

    @Override
    public void read() {
        this.viewDistance = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.viewDistance);
    }

    public void copy(WrapperPlayServerUpdateViewDistance wrapperPlayServerUpdateViewDistance) {
        this.viewDistance = wrapperPlayServerUpdateViewDistance.viewDistance;
    }

    public int getViewDistance() {
        return this.viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }
}
