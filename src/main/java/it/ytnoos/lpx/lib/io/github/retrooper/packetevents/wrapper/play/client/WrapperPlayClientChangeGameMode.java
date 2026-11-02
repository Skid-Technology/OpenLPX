package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientChangeGameMode
        extends PacketWrapper<WrapperPlayClientChangeGameMode> {

    private GameMode gameMode;

    public WrapperPlayClientChangeGameMode(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientChangeGameMode(GameMode gameMode2) {
        super(PacketType.Play.Client.CHANGE_GAME_MODE);
        this.gameMode = gameMode2;
    }

    @Override
    public void read() {
        this.gameMode = GameMode.getById(this.readVarInt());
    }

    @Override
    public void write() {
        this.writeVarInt(this.gameMode.getId());
    }

    public void copy(WrapperPlayClientChangeGameMode wrapperPlayClientChangeGameMode) {
        this.gameMode = wrapperPlayClientChangeGameMode.gameMode;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }

    public void setGameMode(GameMode gameMode2) {
        this.gameMode = gameMode2;
    }
}
