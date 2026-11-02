package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

public interface Inserting extends Tag {

    @NotNull
    Component value();

    default boolean allowsChildren() {
        return true;
    }
}
