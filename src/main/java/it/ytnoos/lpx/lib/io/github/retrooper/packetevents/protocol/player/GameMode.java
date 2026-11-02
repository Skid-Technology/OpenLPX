package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

public enum GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    private static final GameMode[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static GameMode getById(int value) {
        return value >= 0 && value < VALUES.length ? VALUES[value] : SURVIVAL;
    }

    public static GameMode defaultGameMode() {
        return SURVIVAL;
    }
}
