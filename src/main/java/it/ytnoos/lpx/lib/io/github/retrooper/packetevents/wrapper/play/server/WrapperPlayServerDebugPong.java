package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDebugPong extends PacketWrapper<WrapperPlayServerDebugPong> {

    private long timestamp;

    public WrapperPlayServerDebugPong(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDebugPong(long timestamp) {
        super(PacketType.Play.Server.DEBUG_PONG);
        this.timestamp = timestamp;
    }

    @Override
    public void read() {
        this.timestamp = this.readLong();
    }

    @Override
    public void write() {
        this.writeLong(this.timestamp);
    }

    public void copy(WrapperPlayServerDebugPong wrapperPlayServerDebugPong) {
        this.timestamp = wrapperPlayServerDebugPong.timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
