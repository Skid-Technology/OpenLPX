package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

@NullMarked
public class WrapperPlayServerGameRuleValues
        extends PacketWrapper<WrapperPlayServerGameRuleValues> {

    @MonotonicNonNull private Map<ResourceLocation, String> values;

    public WrapperPlayServerGameRuleValues(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerGameRuleValues(
            PacketTypeCommon packetTypeCommon, Map<ResourceLocation, String> entries) {
        super(packetTypeCommon);
        this.values = entries;
    }

    @Override
    public void read() {
        this.values = this.readMap(ResourceLocation::read, PacketWrapper::readString);
    }

    @Override
    public void write() {
        this.writeMap(this.values, ResourceLocation::write, PacketWrapper::writeString);
    }

    public void copy(WrapperPlayServerGameRuleValues wrapperPlayServerGameRuleValues) {
        this.values = wrapperPlayServerGameRuleValues.values;
    }

    public Map<ResourceLocation, String> getValues() {
        return this.values;
    }

    public void setValues(Map<ResourceLocation, String> entries) {
        this.values = entries;
    }
}
