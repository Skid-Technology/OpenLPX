package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

@NonExtendable
public interface MutableStyleSetter<T extends MutableStyleSetter<?>> extends StyleSetter<T> {

    @Contract("_ -> this")
    @NotNull
    default T decorate(@NotNull TextDecoration... textDecoration) {
        int value = 0;
        for (int value2 = textDecoration.length; value < value2; value++) {
            this.decorate(textDecoration[value]);
        }
        return (T) this;
    }

    @Contract("_ -> this")
    @NotNull
    default T decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries) {
        Objects.requireNonNull(entries, "decorations");
        for (Entry<
                        it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration,
                        it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration.State>
                entry : entries.entrySet()) {
            this.decoration(
                    (TextDecoration) entry.getKey(), (TextDecoration.State) entry.getValue());
        }
        return (T) this;
    }

    @Contract("_, _ -> this")
    @NotNull
    default T decorations(@NotNull Set<TextDecoration> values, boolean matches) {
        TextDecoration.State state = TextDecoration.State.byBoolean(matches);
        values.forEach(result -> this.decoration(result, state));
        return (T) this;
    }
}
