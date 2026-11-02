package it.ytnoos.lpx.lib.kyori.adventure.text.flattener;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FlattenerListener {

    default void pushStyle(@NotNull Style style) {}

    void component(@NotNull String text);

    default boolean shouldContinue() {
        return true;
    }

    default void popStyle(@NotNull Style style) {}
}
