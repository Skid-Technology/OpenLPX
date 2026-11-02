package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.EnumMap;
import java.util.Map;

@NonExtendable
public interface StyleGetter {

    @Nullable
    Key font();

    @Nullable
    TextColor color();

    @Nullable
    ShadowColor shadowColor();

    default boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return this.decoration(textDecoration) == TextDecoration.State.TRUE;
    }

    TextDecoration.@NotNull State decoration(@NotNull TextDecoration textDecoration);

    @NotNull
    @Unmodifiable
    default Map<TextDecoration, TextDecoration.State> decorations() {
        EnumMap enumMap = new EnumMap<>(TextDecoration.class);
        int value = 0;
        for (int value2 = DecorationMap.DECORATIONS.length; value < value2; value++) {
            TextDecoration textDecoration = DecorationMap.DECORATIONS[value];
            TextDecoration.State state = this.decoration(textDecoration);
            enumMap.put(textDecoration, state);
        }
        return enumMap;
    }

    @Nullable
    ClickEvent clickEvent();

    @Nullable
    HoverEvent<?> hoverEvent();

    @Nullable
    String insertion();
}
