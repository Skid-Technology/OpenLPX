package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import org.jetbrains.annotations.Nullable;

@Deprecated
public enum DimensionType {
    NETHER(-1, "minecraft:the_nether"),
    OVERWORLD(0, "minecraft:overworld"),
    END(1, "minecraft:the_end"),
    CUSTOM(-999, "minecraft:custom");

    private static final DimensionType[] VALUES = values();

    private final int id;

    private final String name;

    DimensionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DimensionType getById(int value) {
        return VALUES[value + 1];
    }

    @Nullable
    public static DimensionType getByName(String text) {
        DimensionType[] dimensionTypes = VALUES;
        int value = VALUES.length;
        for (int index = 0; index < value; index++) {
            DimensionType dimensionType;
            if ((dimensionType = dimensionTypes[index]).name.equals(text)) {
                return dimensionType;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static boolean isFlat(String text) {
        return WorldType.FLAT.getName().equals(text);
    }

    public static boolean isDebug(String text) {
        return WorldType.DEBUG_ALL_BLOCK_STATES.getName().equals(text);
    }
}
