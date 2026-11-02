package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerStoreCookie extends PacketWrapper<WrapperPlayServerStoreCookie> {

    public static final int MAX_PAYLOAD_SIZE = 5120;

    private ResourceLocation key;

    private byte[] payload;

    public WrapperPlayServerStoreCookie(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerStoreCookie(ResourceLocation resourceLocation, byte[] payload) {
        super(PacketType.Play.Server.STORE_COOKIE);
        this.key = resourceLocation;
        this.payload = payload;
    }

    @Override
    public void read() {
        this.key = this.readIdentifier();
        this.payload = this.readByteArray(5120);
    }

    @Override
    public void write() {
        this.writeIdentifier(this.key);
        this.writeByteArray(this.payload);
    }

    public void copy(WrapperPlayServerStoreCookie wrapperPlayServerStoreCookie) {
        this.key = wrapperPlayServerStoreCookie.key;
        this.payload = wrapperPlayServerStoreCookie.payload;
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public void setKey(ResourceLocation resourceLocation) {
        this.key = resourceLocation;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
