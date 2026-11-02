package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Difficulty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerDifficulty extends PacketWrapper<WrapperPlayServerDifficulty> {

    private Difficulty difficulty;

    private boolean locked;

    public WrapperPlayServerDifficulty(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDifficulty(Difficulty difficulty2, boolean locked) {
        super(PacketType.Play.Server.SERVER_DIFFICULTY);
        this.difficulty = difficulty2;
        this.locked = locked;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
            this.difficulty = this.readEnum(Difficulty.class);
        } else {
            this.difficulty = Difficulty.getById(this.readUnsignedByte());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.locked = this.readBoolean();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
            this.writeEnum(this.difficulty);
        } else {
            this.writeByte(this.difficulty.getId());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_14)) {
            this.writeBoolean(this.locked);
        }
    }

    public void copy(WrapperPlayServerDifficulty wrapperPlayServerDifficulty) {
        this.difficulty = wrapperPlayServerDifficulty.difficulty;
        this.locked = wrapperPlayServerDifficulty.locked;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty2) {
        this.difficulty = difficulty2;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
