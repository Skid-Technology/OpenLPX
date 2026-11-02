package it.ytnoos.lpx.lib.kyori.adventure.text.renderer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface ComponentRenderer<C> {

    @NotNull
    Component render(@NotNull Component component, @NotNull C c);

    default <T> ComponentRenderer<T> mapContext(Function<T, C> function) {
        return (result, result2) -> this.render(result, (C) ((Function) function).apply(result2));
    }
}
