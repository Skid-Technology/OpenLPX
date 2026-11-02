package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDebugSample extends PacketWrapper<WrapperPlayServerDebugSample> {

    private long[] sample;

    private WrapperPlayServerDebugSample.SampleType sampleType;

    public WrapperPlayServerDebugSample(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDebugSample(
            long[] sample, WrapperPlayServerDebugSample.SampleType sampleType2) {
        super(PacketType.Play.Server.DEBUG_SAMPLE);
        this.sample = sample;
        this.sampleType = sampleType2;
    }

    @Override
    public void read() {
        this.sample = this.readLongArray();
        this.sampleType = WrapperPlayServerDebugSample.SampleType.values()[this.readVarInt()];
    }

    @Override
    public void write() {
        this.writeLongArray(this.sample);
        this.writeVarInt(this.sampleType.ordinal());
    }

    public void copy(WrapperPlayServerDebugSample wrapperPlayServerDebugSample) {
        this.sample = wrapperPlayServerDebugSample.sample;
        this.sampleType = wrapperPlayServerDebugSample.sampleType;
    }

    public long[] getSample() {
        return this.sample;
    }

    public void setSample(long[] sample) {
        this.sample = sample;
    }

    public WrapperPlayServerDebugSample.SampleType getSampleType() {
        return this.sampleType;
    }

    public void setSampleType(WrapperPlayServerDebugSample.SampleType sampleType2) {
        this.sampleType = sampleType2;
    }

    public enum SampleType {
        TICK_DURATION
    }
}
