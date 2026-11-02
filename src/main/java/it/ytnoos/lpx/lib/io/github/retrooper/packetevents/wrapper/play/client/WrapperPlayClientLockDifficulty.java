package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientLockDifficulty
        extends PacketWrapper<WrapperPlayClientLockDifficulty> {

    private boolean locked;

    public WrapperPlayClientLockDifficulty(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientLockDifficulty(boolean locked) {
        super(PacketType.Play.Client.LOCK_DIFFICULTY);
        this.locked = locked;
    }

    @Override
    public void read() {
        this.locked = this.readBoolean();
    }

    @Override
    public void write() {
        this.writeBoolean(this.locked);
    }

    public void copy(WrapperPlayClientLockDifficulty wrapperPlayClientLockDifficulty) {
        this.locked = wrapperPlayClientLockDifficulty.locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
