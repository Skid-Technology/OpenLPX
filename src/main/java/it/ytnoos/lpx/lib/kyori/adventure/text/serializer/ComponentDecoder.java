package it.ytnoos.lpx.lib.kyori.adventure.text.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentDecoder<S, O extends Component> {

    @NotNull
    O deserialize(@NotNull S s);

    @Contract(value = "!null -> !null; null -> null", pure = true)
    @Nullable
    default O deserializeOrNull(@Nullable S s) {
        return this.deserializeOr((S) s, null);
    }

    @Contract(value = "!null, _ -> !null; null, _ -> param2", pure = true)
    @Nullable
    default O deserializeOr(@Nullable S s, @Nullable O o) {
        return (O) (s == null ? o : this.deserialize((S) s));
    }
}
