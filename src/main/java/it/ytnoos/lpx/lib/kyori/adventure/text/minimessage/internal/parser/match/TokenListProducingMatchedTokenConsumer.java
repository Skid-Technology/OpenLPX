package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TokenListProducingMatchedTokenConsumer
        extends MatchedTokenConsumer<List<Token>> {

    private List<Token> result = null;

    public TokenListProducingMatchedTokenConsumer(@NotNull String text) {
        super(text);
    }

    @Override
    public void accept(int value, int value2, @NotNull TokenType tokenType) {
        super.accept(value, value2, tokenType);
        if (this.result == null) {
            this.result = new ArrayList<>();
        }
        this.result.add(new Token(value, value2, tokenType));
    }

    @NotNull
    public List<Token> result() {
        return this.result == null ? Collections.emptyList() : this.result;
    }
}
