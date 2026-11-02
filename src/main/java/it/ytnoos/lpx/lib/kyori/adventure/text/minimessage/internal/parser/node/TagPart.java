package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;

public final class TagPart implements Tag.Argument {

    private final String value;

    private final Token token;

    public TagPart(
            @NotNull String text,
            @NotNull Token token2,
            TokenParser.@NotNull TagProvider tagProvider) {
        String text2 =
                TokenParser.resolvePreProcessTags(
                        unquoteAndEscape(text, token2.startIndex(), token2.endIndex()),
                        tagProvider);
        this.value = text2;
        this.token = token2;
    }

    @NotNull
    @Override
    public String value() {
        return this.value;
    }

    @NotNull
    public Token token() {
        return this.token;
    }

    @NotNull
    public static String unquoteAndEscape(@NotNull String text, int value, int value2) {
        if (value == value2) {
            return "";
        }
        int value3 = value;
        int value4 = value2;
        char value5 = text.charAt(value3);
        char value6 = text.charAt(value4 - 1);
        if (value5 != '\'' && value5 != '"') {
            return text.substring(value3, value4);
        }
        value3++;
        if (value6 == '\'' || value6 == '"') {
            value4--;
        }
        if (value3 > value4) {
            return text.substring(value, value2);
        }
        return TokenParser.unescape(
                text, value3, value4, result -> result == value5 || result == 92);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
