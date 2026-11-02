package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerTransfer extends PacketWrapper<WrapperPlayServerTransfer> {

    private String host;

    private int port;

    public WrapperPlayServerTransfer(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTransfer(String host, int port) {
        super(PacketType.Play.Server.TRANSFER);
        this.host = host;
        this.port = port;
    }

    @Override
    public void read() {
        this.host = this.readString();
        this.port = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeString(this.host);
        this.writeVarInt(this.port);
    }

    public void copy(WrapperPlayServerTransfer wrapperPlayServerTransfer) {
        this.host = wrapperPlayServerTransfer.host;
        this.port = wrapperPlayServerTransfer.port;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
