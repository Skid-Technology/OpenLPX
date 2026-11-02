package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

@Deprecated
public enum TileEntityType {
    MOB_SPAWNER,
    COMMAND_BLOCK,
    BEACON,
    SKULL,
    CONDUIT,
    BANNER,
    STRUCTURE_BLOCK,
    END_GATEWAY,
    SIGN,
    BED,
    JIGSAW,
    CAMPFIRE,
    BEEHIVE,
    UNKNOWN;

    public static final TileEntityType[] VALUES = values();

    public int getId() {
        return this.ordinal() + 1;
    }

    public static TileEntityType getById(int value) {
        return value >= 0 && value < VALUES.length ? VALUES[value - 1] : UNKNOWN;
    }
}
