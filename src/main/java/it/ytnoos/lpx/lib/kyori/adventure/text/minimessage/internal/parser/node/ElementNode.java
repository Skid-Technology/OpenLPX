package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenType;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree.Node;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ElementNode implements Node {

    @Nullable private final ElementNode parent;

    @Nullable private final Token token;

    private final String sourceMessage;

    private final List<ElementNode> children = new ArrayList<>();

    ElementNode(
            @Nullable ElementNode elementNode,
            @Nullable Token token2,
            @NotNull String sourceMessage) {
        this.parent = elementNode;
        this.token = token2;
        this.sourceMessage = sourceMessage;
    }

    @Nullable
    public ElementNode parent() {
        return this.parent;
    }

    @Nullable
    public Token token() {
        return this.token;
    }

    @NotNull
    public String sourceMessage() {
        return this.sourceMessage;
    }

    @NotNull
    @Override
    public List<ElementNode> children() {
        return Collections.unmodifiableList(this.children);
    }

    @NotNull
    public List<ElementNode> unsafeChildren() {
        return this.children;
    }

    public void addChild(@NotNull ElementNode elementNode) {
        int value = this.children.size() - 1;
        if (elementNode instanceof TextNode
                && !this.children.isEmpty()
                && this.children.get(value) instanceof TextNode) {
            TextNode textNode;
            if ((textNode = (TextNode) this.children.remove(value)).token().endIndex()
                    == elementNode.token().startIndex()) {
                Token token2 =
                        new Token(
                                textNode.token().startIndex(),
                                elementNode.token().endIndex(),
                                TokenType.TEXT);
                this.children.add(new TextNode(this, token2, textNode.sourceMessage()));
            } else {
                this.children.add(textNode);
                this.children.add(elementNode);
            }
        } else {
            this.children.add(elementNode);
        }
    }

    @NotNull
    public StringBuilder buildToString(@NotNull StringBuilder stringBuilder, int value) {
        char[] values = this.ident(value);
        stringBuilder.append(values).append("Node {\n");
        Iterator<
                        it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node
                                .ElementNode>
                iterator2 = this.children.iterator();
        while (iterator2.hasNext()) {
            ((ElementNode) iterator2.next()).buildToString(stringBuilder, value + 1);
        }
        stringBuilder.append(values).append("}\n");
        return stringBuilder;
    }

    char @NotNull [] ident(int value) {
        char[] values;
        Arrays.fill(values = new char[value * 2], (char) (32));
        return values;
    }

    @NotNull
    @Override
    public String toString() {
        return this.buildToString(new StringBuilder(), 0).toString();
    }
}
