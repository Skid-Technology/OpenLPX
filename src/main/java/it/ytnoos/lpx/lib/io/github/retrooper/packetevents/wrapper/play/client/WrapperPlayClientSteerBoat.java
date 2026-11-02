package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSteerBoat extends PacketWrapper<WrapperPlayClientSteerBoat> {

    private boolean leftPaddleTurning;

    private boolean rightPaddleTurning;

    public WrapperPlayClientSteerBoat(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSteerBoat(boolean leftPaddleTurning, boolean rightPaddleTurning) {
        super(PacketType.Play.Client.STEER_BOAT);
        this.leftPaddleTurning = leftPaddleTurning;
        this.rightPaddleTurning = rightPaddleTurning;
    }

    @Override
    public void read() {
        this.leftPaddleTurning = this.readBoolean();
        this.rightPaddleTurning = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBoolean(this.leftPaddleTurning);
        this.writeBoolean(this.rightPaddleTurning);
    }

    public void copy(WrapperPlayClientSteerBoat wrapperPlayClientSteerBoat) {
        this.leftPaddleTurning = wrapperPlayClientSteerBoat.leftPaddleTurning;
        this.rightPaddleTurning = wrapperPlayClientSteerBoat.rightPaddleTurning;
    }

    public boolean isLeftPaddleTurning() {
        return this.leftPaddleTurning;
    }

    public void setLeftPaddleTurning(boolean leftPaddleTurning) {
        this.leftPaddleTurning = leftPaddleTurning;
    }

    public boolean isRightPaddleTurning() {
        return this.rightPaddleTurning;
    }

    public void setRightPaddleTurning(boolean rightPaddleTurning) {
        this.rightPaddleTurning = rightPaddleTurning;
    }
}
