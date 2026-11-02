package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import org.jetbrains.annotations.NotNull;

public interface ClaimConsumer {

    void style(@NotNull String text, @NotNull Emitable emitable);

    boolean component(@NotNull Emitable emitable);

    boolean styleClaimed(@NotNull String text);

    boolean componentClaimed();
}
