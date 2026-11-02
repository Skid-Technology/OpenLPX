package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface Ticks {

    int TICKS_PER_SECOND = 20;

    long SINGLE_TICK_DURATION_MS = 50L;

    @NotNull
    static Duration duration(long value) {
        return Duration.ofMillis(value * 50L);
    }
}
