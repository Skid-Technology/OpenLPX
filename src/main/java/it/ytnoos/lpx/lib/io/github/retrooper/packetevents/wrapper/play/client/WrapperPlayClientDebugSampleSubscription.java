package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDebugSample;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class WrapperPlayClientDebugSampleSubscription
        extends PacketWrapper<WrapperPlayClientDebugSampleSubscription> {

    private WrapperPlayServerDebugSample.SampleType sampleType;

    public WrapperPlayClientDebugSampleSubscription(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientDebugSampleSubscription(
            WrapperPlayServerDebugSample.SampleType sampleType2) {
        super(PacketType.Play.Client.DEBUG_SAMPLE_SUBSCRIPTION);
        this.sampleType = sampleType2;
    }

    @Override
    public void read() {
        this.sampleType = WrapperPlayServerDebugSample.SampleType.values()[this.readVarInt()];
    }

    @Override
    public void write() {
        this.writeVarInt(this.sampleType.ordinal());
    }

    public void copy(
            WrapperPlayClientDebugSampleSubscription wrapperPlayClientDebugSampleSubscription) {
        this.sampleType = wrapperPlayClientDebugSampleSubscription.sampleType;
    }

    public WrapperPlayServerDebugSample.SampleType getSampleType() {
        return this.sampleType;
    }

    public void setSampleType(WrapperPlayServerDebugSample.SampleType sampleType2) {
        this.sampleType = sampleType2;
    }
}
