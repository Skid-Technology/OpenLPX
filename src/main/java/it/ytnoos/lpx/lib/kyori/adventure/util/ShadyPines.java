package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class ShadyPines {

    private ShadyPines() {}

    @Deprecated
    @SafeVarargs
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> classValue, E @NotNull ... e) {
        return MonkeyBars.enumSet(classValue, (E[]) e);
    }

    public static boolean equals(double value, double value2) {
        return Double.doubleToLongBits(value) == Double.doubleToLongBits(value2);
    }

    public static boolean equals(float value, float value2) {
        return Float.floatToIntBits(value) == Float.floatToIntBits(value2);
    }
}
