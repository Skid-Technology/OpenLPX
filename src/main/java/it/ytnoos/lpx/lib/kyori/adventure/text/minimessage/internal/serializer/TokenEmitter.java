package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

public interface TokenEmitter {

    @NotNull
    TokenEmitter tag(@NotNull String text);

    @NotNull
    TokenEmitter selfClosingTag(@NotNull String text);

    @NotNull
    default TokenEmitter arguments(@NotNull String... text) {
        int value = text.length;
        for (int index = 0; index < value; index++) {
            String text2 = ((String[]) text)[index];
            this.argument(text2);
        }
        return this;
    }

    @NotNull
    TokenEmitter argument(@NotNull String text);

    @NotNull
    TokenEmitter argument(@NotNull String text, @NotNull QuotingOverride quotingOverride);

    @NotNull
    TokenEmitter argument(@NotNull Component component);

    @NotNull
    TokenEmitter text(@NotNull String text2);

    @NotNull
    TokenEmitter pop();
}
