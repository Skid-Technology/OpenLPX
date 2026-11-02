package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerTickingStep extends PacketWrapper<WrapperPlayServerTickingStep> {

    private int tickSteps;

    public WrapperPlayServerTickingStep(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerTickingStep(int tickSteps) {
        super(PacketType.Play.Server.TICKING_STEP);
        this.tickSteps = tickSteps;
    }

    @Override
    public void read() {
        this.tickSteps = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.tickSteps);
    }

    public void copy(WrapperPlayServerTickingStep wrapperPlayServerTickingStep) {
        this.tickSteps = wrapperPlayServerTickingStep.tickSteps;
    }

    public int getTickSteps() {
        return this.tickSteps;
    }

    public void setTickSteps(int tickSteps) {
        this.tickSteps = tickSteps;
    }
}
