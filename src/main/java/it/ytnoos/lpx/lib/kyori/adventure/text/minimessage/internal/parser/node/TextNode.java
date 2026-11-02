package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenParser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TextNode extends ValueNode {

    private static boolean isEscape(int value) {
        return value == 60 || value == 92;
    }

    public TextNode(@Nullable ElementNode elementNode, @NotNull Token token, @NotNull String text) {
        super(
                elementNode,
                token,
                text,
                TokenParser.unescape(
                        text, token.startIndex(), token.endIndex(), TextNode::isEscape));
    }

    @Override
    String valueName() {
        return "TextNode";
    }
}
