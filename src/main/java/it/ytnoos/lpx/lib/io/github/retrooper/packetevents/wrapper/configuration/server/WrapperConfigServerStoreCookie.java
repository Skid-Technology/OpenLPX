package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.configuration.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperConfigServerStoreCookie extends PacketWrapper<WrapperConfigServerStoreCookie> {

    public static final int MAX_PAYLOAD_SIZE = 5120;

    private ResourceLocation key;

    private byte[] payload;

    public WrapperConfigServerStoreCookie(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperConfigServerStoreCookie(ResourceLocation resourceLocation, byte[] payload) {
        super(PacketType.Configuration.Server.STORE_COOKIE);
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

    public void copy(WrapperConfigServerStoreCookie wrapperConfigServerStoreCookie) {
        this.key = wrapperConfigServerStoreCookie.key;
        this.payload = wrapperConfigServerStoreCookie.payload;
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
