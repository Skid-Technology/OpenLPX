package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentSerializer<I extends Component, O extends Component, R>
        extends ComponentEncoder<I, R>, ComponentDecoder<R, O> {

    @NotNull
    @Override
    O deserialize(@NotNull R r);

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default O deseializeOrNull(@Nullable R r) {
        return ComponentDecoder.super.deserializeOrNull(r);
    }

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    @Override
    default O deserializeOrNull(@Nullable R r) {
        return ComponentDecoder.super.deserializeOr(r, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    @Override
    default O deserializeOr(@Nullable R r, @Nullable O o) {
        return ComponentDecoder.super.deserializeOr(r, (O) o);
    }

    @NotNull
    @Override
    R serialize(@NotNull I i);

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    @Override
    default R serializeOrNull(@Nullable I i) {
        return this.serializeOr((I) i, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    @Override
    default R serializeOr(@Nullable I i, @Nullable R r) {
        return (R) (i == null ? r : this.serialize((I) i));
    }
}
