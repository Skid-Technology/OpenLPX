package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientDebugPing extends PacketWrapper<WrapperPlayClientDebugPing> {

    private long timestamp;

    public WrapperPlayClientDebugPing(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientDebugPing(long timestamp) {
        super(PacketType.Play.Client.DEBUG_PING);
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

    public void copy(WrapperPlayClientDebugPing wrapperPlayClientDebugPing) {
        this.timestamp = wrapperPlayClientDebugPing.timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
