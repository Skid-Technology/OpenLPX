package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.common.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public abstract class WrapperCommonServerPostEffects<T extends WrapperCommonServerPostEffects<T>>
        extends PacketWrapper<T> {

    @MonotonicNonNull protected List<ResourceLocation> postEffects;

    public WrapperCommonServerPostEffects(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperCommonServerPostEffects(PacketTypeCommon packetTypeCommon) {
        super(packetTypeCommon);
    }

    public WrapperCommonServerPostEffects(
            PacketTypeCommon packetTypeCommon, List<ResourceLocation> postEffects) {
        super(packetTypeCommon);
        this.postEffects = postEffects;
    }

    @Override
    public void read() {
        this.postEffects = this.readList(ResourceLocation::read);
    }

    @Override
    public void write() {
        this.writeList(this.postEffects, ResourceLocation::write);
    }

    public void copy(T t) {
        this.postEffects = t.postEffects;
    }

    public List<ResourceLocation> getPostEffects() {
        return this.postEffects;
    }

    public void setPostEffects(List<ResourceLocation> postEffects) {
        this.postEffects = postEffects;
    }
}
