package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.status.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperStatusClientPing extends PacketWrapper<WrapperStatusClientPing> {

    private long time;

    public WrapperStatusClientPing(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperStatusClientPing(long time) {
        super(PacketType.Status.Client.PING.getId(), ClientVersion.UNKNOWN);
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

    public void copy(WrapperStatusClientPing wrapperStatusClientPing) {
        this.time = wrapperStatusClientPing.time;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
