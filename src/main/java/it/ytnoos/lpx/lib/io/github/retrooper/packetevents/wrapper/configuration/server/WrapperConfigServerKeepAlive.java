package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigServerKeepAlive extends PacketWrapper<WrapperConfigServerKeepAlive> {

    private long id;

    public WrapperConfigServerKeepAlive(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerKeepAlive(long id) {
        super(PacketType.Configuration.Server.KEEP_ALIVE);
        this.id = id;
    }

    @Override
    public void read() {
        this.id = this.readLong();
    }

    @Override
    public void write() {
        this.writeLong(this.id);
    }

    public void copy(WrapperConfigServerKeepAlive wrapperConfigServerKeepAlive) {
        this.id = wrapperConfigServerKeepAlive.id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
