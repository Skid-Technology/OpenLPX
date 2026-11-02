package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPong extends PacketWrapper<WrapperPlayClientPong> {

    private int id;

    public WrapperPlayClientPong(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPong(int id) {
        super(PacketType.Play.Client.PONG);
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

    public void copy(WrapperPlayClientPong wrapperPlayClientPong) {
        this.id = wrapperPlayClientPong.id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
