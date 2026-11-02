package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.Nullable;

public class WrapperCommonClientCustomClickAction<T extends WrapperCommonClientCustomClickAction<T>>
        extends PacketWrapper<T> {

    public static final int MAX_PAYLOAD_SIZE = 65536;

    private ResourceLocation id;

    @Nullable private NBT payload;

    public WrapperCommonClientCustomClickAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperCommonClientCustomClickAction(
            PacketTypeCommon packetTypeCommon,
            ResourceLocation resourceLocation,
            @Nullable NBT nBT) {
        super(packetTypeCommon);
        this.id = resourceLocation;
        this.payload = nBT;
    }

    @Override
    public void read() {
        this.id = ResourceLocation.read(this);
        this.payload = this.readLengthPrefixed(65536, PacketWrapper::readNullableNBT);
    }

    @Override
    public void write() {
        ResourceLocation.write(this, this.id);
        this.writeLengthPrefixed(this.payload, PacketWrapper::writeNBTRaw);
    }

    public void copy(T t) {
        this.id = t.getId();
        this.payload = t.getPayload();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setId(ResourceLocation resourceLocation) {
        this.id = resourceLocation;
    }

    @Nullable
    public NBT getPayload() {
        return this.payload;
    }

    public void setPayload(@Nullable NBT nBT) {
        this.payload = nBT;
    }
}
