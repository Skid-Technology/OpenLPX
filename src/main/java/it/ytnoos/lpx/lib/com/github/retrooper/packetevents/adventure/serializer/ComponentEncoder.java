package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentEncoder<I extends Component, R> {

    @NotNull
    R serialize(@NotNull I i);

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default R serializeOrNull(@Nullable I i) {
        return this.serializeOr((I) i, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default R serializeOr(@Nullable I i, @Nullable R r) {
        return (R) (i == null ? r : this.serialize((I) i));
    }
}
