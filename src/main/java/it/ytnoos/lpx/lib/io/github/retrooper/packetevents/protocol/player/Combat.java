package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

public enum Combat {
    ENTER_COMBAT,
    END_COMBAT,
    ENTITY_DEAD;

    public static final Combat[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static Combat getById(int value) {
        return VALUES[value];
    }
}
