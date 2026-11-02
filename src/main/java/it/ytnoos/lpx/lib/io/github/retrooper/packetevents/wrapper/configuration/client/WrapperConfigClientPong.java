package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigClientPong extends PacketWrapper<WrapperConfigClientPong> {

    private int id;

    public WrapperConfigClientPong(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperConfigClientPong(int id) {
        super(PacketType.Configuration.Client.PONG);
        this.id = id;
    }

    @Override
    public void read() {
        this.id = this.readInt();
    }

    @Override
    public void write() {
        this.writeInt(this.id);
    }

    public void copy(WrapperConfigClientPong wrapperConfigClientPong) {
        this.id = wrapperConfigClientPong.id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
