package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerPlayerAbilities
        extends PacketWrapper<WrapperPlayServerPlayerAbilities> {

    private boolean godMode;

    private boolean flying;

    private boolean flightAllowed;

    private boolean creativeMode;

    private float flySpeed;

    private float fovModifier;

    public WrapperPlayServerPlayerAbilities(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerPlayerAbilities(
            boolean godMode,
            boolean flying,
            boolean flightAllowed,
            boolean creativeMode,
            float flySpeed,
            float fovModifier) {
        super(PacketType.Play.Server.PLAYER_ABILITIES);
        this.godMode = godMode;
        this.flying = flying;
        this.flightAllowed = flightAllowed;
        this.creativeMode = creativeMode;
        this.flySpeed = flySpeed;
        this.fovModifier = fovModifier;
    }

    @Override
    public void read() {
        byte value = this.readByte();
        this.godMode = (value & 1) != 0;
        this.flying = (value & 2) != 0;
        this.flightAllowed = (value & 4) != 0;
        this.creativeMode = (value & 8) != 0;
        this.flySpeed = this.readFloat();
        this.fovModifier = this.readFloat();
    }

    @Override
    public void write() {
        byte value = 0;
        if (this.godMode) {
            value = 1;
        }
        if (this.flying) {
            value = (byte) (value | 2);
        }
        if (this.flightAllowed) {
            value = (byte) (value | 4);
        }
        if (this.creativeMode) {
            value = (byte) (value | 8);
        }
        this.writeByte(value);
        this.writeFloat(this.flySpeed);
        this.writeFloat(this.fovModifier);
    }

    public void copy(WrapperPlayServerPlayerAbilities wrapperPlayServerPlayerAbilities) {
        this.godMode = wrapperPlayServerPlayerAbilities.godMode;
        this.flying = wrapperPlayServerPlayerAbilities.flying;
        this.flightAllowed = wrapperPlayServerPlayerAbilities.flightAllowed;
        this.creativeMode = wrapperPlayServerPlayerAbilities.creativeMode;
        this.flySpeed = wrapperPlayServerPlayerAbilities.flySpeed;
        this.fovModifier = wrapperPlayServerPlayerAbilities.fovModifier;
    }

    public boolean isInGodMode() {
        return this.godMode;
    }

    public void setInGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isFlightAllowed() {
        return this.flightAllowed;
    }

    public void setFlightAllowed(boolean flightAllowed) {
        this.flightAllowed = flightAllowed;
    }

    public boolean isInCreativeMode() {
        return this.creativeMode;
    }

    public void setInCreativeMode(boolean creativeMode) {
        this.creativeMode = creativeMode;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void setFlySpeed(float flySpeed) {
        this.flySpeed = flySpeed;
    }

    public float getFOVModifier() {
        return this.fovModifier;
    }

    public void setFOVModifier(float fovModifier) {
        this.fovModifier = fovModifier;
    }
}
