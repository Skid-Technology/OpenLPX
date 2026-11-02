package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.level;

import org.jetbrains.annotations.Nullable;

public enum VillagerLevel {
    NOVICE,
    APPRENTICE,
    JOURNEYMAN,
    EXPERT,
    MASTER;

    private static final VillagerLevel[] VALUES = values();

    @Nullable
    public static VillagerLevel getById(int value) {
        return value >= 1 && value <= VALUES.length ? VALUES[value - 1] : null;
    }

    public int getId() {
        return this.ordinal() + 1;
    }
}
