package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

public abstract class WrapperCommonCookieResponse<T extends WrapperCommonCookieResponse<T>>
        extends PacketWrapper<T> {

    public static final int MAX_PAYLOAD_SIZE = 5120;

    private ResourceLocation key;

    private byte @Nullable [] payload;

    @Deprecated
    public WrapperCommonCookieResponse(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonCookieResponse(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperCommonCookieResponse(
            PacketTypeCommon packetTypeCommon,
            ResourceLocation resourceLocation,
            byte @Nullable [] payload) {
        super(packetTypeCommon);
        this.key = resourceLocation;
        this.payload = payload;
    }

    @Override
    public void read() {
        this.key = this.readIdentifier();
        this.payload = this.readOptional(result -> result.readByteArray(5120));
    }

    @Override
    public void write() {
        this.writeIdentifier(this.key);
        this.writeOptional(this.payload, PacketWrapper::writeByteArray);
    }

    public void copy(T t) {
        this.key = t.getKey();
        this.payload = t.getPayload();
    }

    public ResourceLocation getKey() {
        return this.key;
    }

    public void setKey(ResourceLocation resourceLocation) {
        this.key = resourceLocation;
    }

    public byte @Nullable [] getPayload() {
        return this.payload;
    }

    public void setPayload(byte @Nullable [] payload) {
        this.payload = payload;
    }
}
