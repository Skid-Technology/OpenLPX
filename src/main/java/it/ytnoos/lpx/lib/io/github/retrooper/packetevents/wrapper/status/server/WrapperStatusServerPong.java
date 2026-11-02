package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.status.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperStatusServerPong extends PacketWrapper<WrapperStatusServerPong> {

    private long time;

    public WrapperStatusServerPong(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperStatusServerPong(long time) {
        super(PacketType.Status.Server.PONG);
        this.time = time;
    }

    @Override
    public void read() {
        this.time = this.readLong();
    }

    @Override
    public void write() {
        this.writeLong(this.time);
    }

    public void copy(WrapperStatusServerPong wrapperStatusServerPong) {
        this.time = wrapperStatusServerPong.time;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
