package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigClientKeepAlive extends PacketWrapper<WrapperConfigClientKeepAlive> {

    private long id;

    public WrapperConfigClientKeepAlive(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientKeepAlive(long id) {
        super(PacketType.Configuration.Client.KEEP_ALIVE);
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

    public void copy(WrapperConfigClientKeepAlive wrapperConfigClientKeepAlive) {
        this.id = wrapperConfigClientKeepAlive.id;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
