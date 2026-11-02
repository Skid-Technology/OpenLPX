package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerChangeGameState
        extends PacketWrapper<WrapperPlayServerChangeGameState> {

    private WrapperPlayServerChangeGameState.Reason reason;

    private float value;

    public WrapperPlayServerChangeGameState(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerChangeGameState(int value2, float value) {
        super(PacketType.Play.Server.CHANGE_GAME_STATE);
        this.reason = WrapperPlayServerChangeGameState.Reason.VALUES[value2];
        this.value = value;
    }

    public WrapperPlayServerChangeGameState(
            WrapperPlayServerChangeGameState.Reason reason2, float value) {
        super(PacketType.Play.Server.CHANGE_GAME_STATE);
        this.reason = reason2;
        this.value = value;
    }

    @Override
    public void read() {
        this.reason = WrapperPlayServerChangeGameState.Reason.getById(this.readUnsignedByte());
        this.value = this.readFloat();
    }

    @Override
    public void write() {
        this.writeByte(this.reason.ordinal());
        this.writeFloat(this.value);
    }

    public void copy(WrapperPlayServerChangeGameState wrapperPlayServerChangeGameState) {
        this.reason = wrapperPlayServerChangeGameState.reason;
        this.value = wrapperPlayServerChangeGameState.value;
    }

    public WrapperPlayServerChangeGameState.Reason getReason() {
        return this.reason;
    }

    public void setReason(WrapperPlayServerChangeGameState.Reason reason2) {
        this.reason = reason2;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public enum Reason {
        NO_RESPAWN_BLOCK_AVAILABLE,
        END_RAINING,
        BEGIN_RAINING,
        CHANGE_GAME_MODE,
        WIN_GAME,
        DEMO_EVENT,
        ARROW_HIT_PLAYER,
        RAIN_LEVEL_CHANGE,
        THUNDER_LEVEL_CHANGE,
        PLAY_PUFFER_FISH_STING_SOUND,
        PLAY_ELDER_GUARDIAN_MOB_APPEARANCE,
        ENABLE_RESPAWN_SCREEN,
        LIMITED_CRAFTING,
        START_LOADING_CHUNKS;

        private static final WrapperPlayServerChangeGameState.Reason[] VALUES = values();

        public static WrapperPlayServerChangeGameState.Reason getById(int value) {
            return VALUES[value];
        }
    }
}
