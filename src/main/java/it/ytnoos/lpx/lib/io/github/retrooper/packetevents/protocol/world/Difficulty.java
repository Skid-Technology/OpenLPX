package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

public enum Difficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    private static final Difficulty[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static Difficulty getById(int value) {
        return VALUES[value];
    }
}
