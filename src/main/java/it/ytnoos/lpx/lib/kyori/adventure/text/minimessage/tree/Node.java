package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@NonExtendable
public interface Node {

    @NotNull
    @Override
    String toString();

    @NotNull
    List<? extends Node> children();

    @Nullable
    Node parent();

    @NonExtendable
    interface Root extends Node {

        @NotNull
        String input();
    }
}
