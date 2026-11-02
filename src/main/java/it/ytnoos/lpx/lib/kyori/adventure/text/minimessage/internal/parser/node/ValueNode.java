package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class ValueNode extends ElementNode {

    private final String value;

    ValueNode(
            @Nullable ElementNode elementNode,
            @Nullable Token token,
            @NotNull String text,
            @NotNull String value) {
        super(elementNode, token, text);
        this.value = value;
    }

    abstract String valueName();

    @NotNull
    public String value() {
        return this.value;
    }

    @NotNull
    @Override
    public Token token() {
        return Objects.requireNonNull(super.token(), "token is not set");
    }

    @NotNull
    @Override
    public StringBuilder buildToString(@NotNull StringBuilder stringBuilder, int value2) {
        char[] values = this.ident(value2);
        stringBuilder
                .append(values)
                .append(this.valueName())
                .append("('")
                .append(this.value)
                .append("')\n");
        return stringBuilder;
    }
}
