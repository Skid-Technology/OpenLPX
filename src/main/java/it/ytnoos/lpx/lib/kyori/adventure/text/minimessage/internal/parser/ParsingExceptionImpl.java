package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Internal
public class ParsingExceptionImpl extends ParsingException {

    private static final long serialVersionUID = 2507190809441787202L;

    private final String originalText;

    private Token @NotNull [] tokens;

    public ParsingExceptionImpl(
            String text, @Nullable String originalText, @NotNull Token... token) {
        super(text, null, true, false);
        this.tokens = token;
        this.originalText = originalText;
    }

    public ParsingExceptionImpl(
            String text,
            @Nullable String originalText,
            @Nullable Throwable throwable,
            boolean matches,
            @NotNull Token... token) {
        super(text, throwable, true, matches);
        this.tokens = token;
        this.originalText = originalText;
    }

    @Override
    public String getMessage() {
        String text = this.tokens().length != 0 ? "\n\t" + this.arrow() : "";
        String text2 = this.originalText() != null ? "\n\t" + this.originalText() + text : "";
        return super.getMessage() + text2;
    }

    @Nullable
    @Override
    public String detailMessage() {
        return super.getMessage();
    }

    @Nullable
    @Override
    public String originalText() {
        return this.originalText;
    }

    @NotNull
    public Token[] tokens() {
        return this.tokens;
    }

    public void tokens(@NotNull Token[] tokens2) {
        this.tokens = tokens2;
    }

    private String arrow() {
        Token[] tokens2;
        Token[] tokens3 = tokens2 = this.tokens();
        char[] values = new char[tokens3[tokens3.length - 1].endIndex()];
        int value = 0;
        Token[] tokens4 = tokens2;
        int value2 = tokens2.length;
        for (int index = 0; index < value2; index++) {
            Token token = tokens4[index];
            Arrays.fill(values, value, token.startIndex(), ' ');
            values[token.startIndex()] = '^';
            if (Math.abs(token.startIndex() - token.endIndex()) > 1) {
                Arrays.fill(values, token.startIndex() + 1, token.endIndex() - 1, '~');
            }
            values[token.endIndex() - 1] = '^';
            value = token.endIndex();
        }
        return new String(values);
    }

    @Override
    public int startIndex() {
        return this.tokens.length == 0 ? -1 : this.tokens[0].startIndex();
    }

    @Override
    public int endIndex() {
        return this.tokens.length == 0 ? -1 : this.tokens[this.tokens.length - 1].endIndex();
    }
}
