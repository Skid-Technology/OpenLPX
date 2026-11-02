package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound;

public enum SoundCategory {
    MASTER,
    MUSIC,
    RECORD,
    WEATHER,
    BLOCK,
    HOSTILE,
    NEUTRAL,
    PLAYER,
    AMBIENT,
    VOICE,
    UI;

    private static final SoundCategory[] VALUES = values();

    public static SoundCategory fromId(int value) {
        return VALUES[value];
    }
}
