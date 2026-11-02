package it.ytnoos.lpx.lib.kyori.adventure.key;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface KeyedValue<T> extends Keyed {

    @NotNull
    static <T> KeyedValue<T> keyedValue(@NotNull Key key, @NotNull T t) {
        return new KeyedValueImpl<>(key, Objects.requireNonNull((T) t, "value"));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static <T> KeyedValue<T> of(@NotNull Key key, @NotNull T t) {
        return new KeyedValueImpl<>(key, Objects.requireNonNull((T) t, "value"));
    }

    @NotNull
    T value();
}
