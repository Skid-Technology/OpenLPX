package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Optional;

public class WrapperPlayClientPlayerAbilities
        extends PacketWrapper<WrapperPlayClientPlayerAbilities> {

    private boolean flying;

    private Optional<Boolean> godMode;

    private Optional<Boolean> flightAllowed;

    private Optional<Boolean> creativeMode;

    private Optional<Float> flySpeed;

    private Optional<Float> walkSpeed;

    public WrapperPlayClientPlayerAbilities(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientPlayerAbilities(
            boolean flying,
            Optional<Boolean> optional,
            Optional<Boolean> optional2,
            Optional<Boolean> optional3,
            Optional<Float> optional4,
            Optional<Float> optional5) {
        super(PacketType.Play.Client.PLAYER_ABILITIES);
        this.flying = flying;
        this.godMode = optional;
        this.flightAllowed = optional2;
        this.creativeMode = optional3;
        this.flySpeed = optional4;
        this.walkSpeed = optional5;
    }

    public WrapperPlayClientPlayerAbilities(boolean matches) {
        this(
                matches,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());
    }

    @Override
    public void read() {
        byte value = this.readByte();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            this.flying = (value & 2) != 0;
            this.godMode = Optional.empty();
            this.flightAllowed = Optional.empty();
            this.creativeMode = Optional.empty();
            this.flySpeed = Optional.empty();
            this.walkSpeed = Optional.empty();
        } else {
            this.godMode = Optional.of((value & 1) != 0);
            this.flying = (value & 2) != 0;
            this.flightAllowed = Optional.of((value & 4) != 0);
            this.creativeMode = Optional.of((value & 8) != 0);
            this.flySpeed = Optional.of(this.readFloat());
            this.walkSpeed = Optional.of(this.readFloat());
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16)) {
            byte value = (byte) (this.flying ? 2 : 0);
            this.writeByte(value);
        } else {
            byte value2 = 0;
            if (this.godMode.orElse(false)) {
                value2 = 1;
            }
            if (this.flying) {
                value2 = (byte) (value2 | 2);
            }
            if (this.flightAllowed.orElse(false)) {
                value2 = (byte) (value2 | 4);
            }
            if (this.creativeMode.orElse(false)) {
                value2 = (byte) (value2 | 8);
            }
            this.writeByte(value2);
            this.writeFloat(this.flySpeed.orElse(0.1F));
            this.writeFloat(this.walkSpeed.orElse(0.2F));
        }
    }

    public void copy(WrapperPlayClientPlayerAbilities wrapperPlayClientPlayerAbilities) {
        this.godMode = wrapperPlayClientPlayerAbilities.godMode;
        this.flying = wrapperPlayClientPlayerAbilities.flying;
        this.flightAllowed = wrapperPlayClientPlayerAbilities.flightAllowed;
        this.creativeMode = wrapperPlayClientPlayerAbilities.creativeMode;
        this.flySpeed = wrapperPlayClientPlayerAbilities.flySpeed;
        this.walkSpeed = wrapperPlayClientPlayerAbilities.walkSpeed;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public Optional<Boolean> isInGodMode() {
        return this.godMode;
    }

    public void setInGodMode(Optional<Boolean> optional) {
        this.godMode = optional;
    }

    public Optional<Boolean> isFlightAllowed() {
        return this.flightAllowed;
    }

    public void setFlightAllowed(Optional<Boolean> optional) {
        this.flightAllowed = optional;
    }

    public Optional<Boolean> isInCreativeMode() {
        return this.creativeMode;
    }

    public void setCreativeMode(Optional<Boolean> optional) {
        this.creativeMode = optional;
    }

    public Optional<Float> getFlySpeed() {
        return this.flySpeed;
    }

    public void setFlySpeed(Optional<Float> optional) {
        this.flySpeed = optional;
    }

    public Optional<Float> getWalkSpeed() {
        return this.walkSpeed;
    }

    public void setWalkSpeed(Optional<Float> optional) {
        this.walkSpeed = optional;
    }
}
