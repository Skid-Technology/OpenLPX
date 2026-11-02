package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientPlayerInput extends PacketWrapper<WrapperPlayClientPlayerInput> {

    private boolean forward;

    private boolean backward;

    private boolean left;

    private boolean right;

    private boolean jump;

    private boolean shift;

    private boolean sprint;

    public WrapperPlayClientPlayerInput(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerInput(
            boolean forward,
            boolean backward,
            boolean left,
            boolean right,
            boolean jump,
            boolean shift,
            boolean sprint) {
        super(PacketType.Play.Client.PLAYER_INPUT);
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.jump = jump;
        this.shift = shift;
        this.sprint = sprint;
    }

    @Override
    public void read() {
        byte value = this.readByte();
        this.forward = (value & 1) != 0;
        this.backward = (value & 2) != 0;
        this.left = (value & 4) != 0;
        this.right = (value & 8) != 0;
        this.jump = (value & 16) != 0;
        this.shift = (value & 32) != 0;
        this.sprint = (value & 64) != 0;
    }

    @Override
    public void write() {
        byte value =
                (byte)
                        ((byte)
                                        ((byte)
                                                        ((byte)
                                                                        ((byte)
                                                                                        ((byte)
                                                                                                        ((byte)
                                                                                                                        (0
                                                                                                                                | (this
                                                                                                                                                .forward
                                                                                                                                        ? 1
                                                                                                                                        : 0))
                                                                                                                | (this
                                                                                                                                .backward
                                                                                                                        ? 2
                                                                                                                        : 0))
                                                                                                | (this
                                                                                                                .left
                                                                                                        ? 4
                                                                                                        : 0))
                                                                                | (this.right
                                                                                        ? 8
                                                                                        : 0))
                                                                | (this.jump ? 16 : 0))
                                                | (this.shift ? 32 : 0))
                                | (this.sprint ? 64 : 0));
        this.writeByte(value);
    }

    public void copy(WrapperPlayClientPlayerInput wrapperPlayClientPlayerInput) {
        this.forward = wrapperPlayClientPlayerInput.forward;
        this.backward = wrapperPlayClientPlayerInput.backward;
        this.left = wrapperPlayClientPlayerInput.left;
        this.right = wrapperPlayClientPlayerInput.right;
        this.jump = wrapperPlayClientPlayerInput.jump;
        this.shift = wrapperPlayClientPlayerInput.shift;
        this.sprint = wrapperPlayClientPlayerInput.sprint;
    }

    public boolean isForward() {
        return this.forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isBackward() {
        return this.backward;
    }

    public void setBackward(boolean backward) {
        this.backward = backward;
    }

    public boolean isLeft() {
        return this.left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return this.right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isJump() {
        return this.jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isShift() {
        return this.shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public boolean isSprint() {
        return this.sprint;
    }

    public void setSprint(boolean sprint) {
        this.sprint = sprint;
    }
}
