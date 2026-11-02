package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigServerTransfer extends PacketWrapper<WrapperConfigServerTransfer> {

    private String host;

    private int port;

    public WrapperConfigServerTransfer(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerTransfer(String host, int port) {
        super(PacketType.Configuration.Server.TRANSFER);
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

    public void copy(WrapperConfigServerTransfer wrapperConfigServerTransfer) {
        this.host = wrapperConfigServerTransfer.host;
        this.port = wrapperConfigServerTransfer.port;
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
