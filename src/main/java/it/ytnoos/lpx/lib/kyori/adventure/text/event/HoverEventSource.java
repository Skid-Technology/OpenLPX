package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public interface HoverEventSource<V> {

    @Nullable
    static <V> HoverEvent<V> unbox(@Nullable HoverEventSource<V> hoverEventSource) {
        return hoverEventSource != null ? hoverEventSource.asHoverEvent() : null;
    }

    @NotNull
    default HoverEvent<V> asHoverEvent() {
        return this.asHoverEvent(UnaryOperator.identity());
    }

    @NotNull
    HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> unaryOperator);
}
