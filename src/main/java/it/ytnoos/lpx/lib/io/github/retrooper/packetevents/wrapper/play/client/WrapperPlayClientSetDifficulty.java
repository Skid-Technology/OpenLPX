package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Difficulty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientSetDifficulty extends PacketWrapper<WrapperPlayClientSetDifficulty> {

    private Difficulty difficulty;

    public WrapperPlayClientSetDifficulty(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientSetDifficulty(Difficulty difficulty2) {
        super(PacketType.Play.Client.SET_DIFFICULTY);
        this.difficulty = difficulty2;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
            this.difficulty = this.readEnum(Difficulty.class);
        } else {
            this.difficulty = Difficulty.getById(this.readUnsignedByte());
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)) {
            this.writeEnum(this.difficulty);
        } else {
            this.writeByte(this.difficulty.getId());
        }
    }

    public void copy(WrapperPlayClientSetDifficulty wrapperPlayClientSetDifficulty) {
        this.difficulty = wrapperPlayClientSetDifficulty.difficulty;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty2) {
        this.difficulty = difficulty2;
    }
}
