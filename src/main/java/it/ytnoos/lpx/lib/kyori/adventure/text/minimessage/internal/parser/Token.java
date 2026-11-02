package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public final class Token implements Examinable {

    private final int startIndex;

    private final int endIndex;

    private final TokenType type;

    private List<Token> childTokens = null;

    public Token(int startIndex, int endIndex, TokenType tokenType) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.type = tokenType;
    }

    public int startIndex() {
        return this.startIndex;
    }

    public int endIndex() {
        return this.endIndex;
    }

    public TokenType type() {
        return this.type;
    }

    public List<Token> childTokens() {
        return this.childTokens;
    }

    public void childTokens(List<Token> childTokens) {
        this.childTokens = childTokens;
    }

    public CharSequence get(CharSequence charSequence) {
        return charSequence.subSequence(this.startIndex, this.endIndex);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("startIndex", this.startIndex),
                ExaminableProperty.of("endIndex", this.endIndex),
                ExaminableProperty.of("type", this.type));
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof Token)) {
            return false;
        }
        Token token = (Token) result;
        return this.startIndex == token.startIndex
                && this.endIndex == token.endIndex
                && this.type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.startIndex, this.endIndex, this.type);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }
}
