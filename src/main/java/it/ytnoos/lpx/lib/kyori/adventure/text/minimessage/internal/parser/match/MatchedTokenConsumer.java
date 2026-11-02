package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenType;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public abstract class MatchedTokenConsumer<T> {

    protected final String input;

    private int lastIndex = -1;

    public MatchedTokenConsumer(@NotNull String input) {
        this.input = input;
    }

    @MustBeInvokedByOverriders
    public void accept(int value, int lastIndex, @NotNull TokenType tokenType) {
        this.lastIndex = lastIndex;
    }

    @UnknownNullability
    public abstract T result();

    public final int lastEndIndex() {
        return this.lastIndex;
    }
}
