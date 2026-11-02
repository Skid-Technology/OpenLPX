package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import org.jetbrains.annotations.Nullable;

public enum WorldType {
    DEFAULT("default"),
    FLAT("flat"),
    LARGE_BIOMES("largeBiomes"),
    AMPLIFIED("amplified"),
    CUSTOMIZED("customized"),
    BUFFET("buffet"),
    DEBUG_ALL_BLOCK_STATES("debug_all_block_states"),
    DEFAULT_1_1("default_1_1");

    private static final WorldType[] VALUES = values();

    private final String name;

    WorldType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public static WorldType getByName(String text) {
        WorldType[] worldTypes = VALUES;
        int value = VALUES.length;
        for (int index = 0; index < value; index++) {
            WorldType worldType;
            if ((worldType = worldTypes[index]).name.equals(text)) {
                return worldType;
            }
        }
        return null;
    }
}
