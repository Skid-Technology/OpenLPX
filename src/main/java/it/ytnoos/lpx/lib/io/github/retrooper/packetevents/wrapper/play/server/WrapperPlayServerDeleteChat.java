package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDeleteChat extends PacketWrapper<WrapperPlayServerDeleteChat> {

    private byte[] signature;

    public WrapperPlayServerDeleteChat(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDeleteChat(byte[] signature) {
        super(PacketType.Play.Server.DELETE_CHAT);
        this.signature = signature;
    }

    @Override
    public void read() {
        this.signature = this.readByteArray();
    }

    @Override
    public void write() {
        this.writeByteArray(this.signature);
    }

    public void copy(WrapperPlayServerDeleteChat wrapperPlayServerDeleteChat) {
        this.signature = wrapperPlayServerDeleteChat.signature;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
