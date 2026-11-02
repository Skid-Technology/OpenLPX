package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class TagNode extends ElementNode {

    private final List<TagPart> parts;

    @Nullable private Tag tag = null;

    public TagNode(
            @NotNull ElementNode elementNode,
            @NotNull Token token2,
            @NotNull String text,
            TokenParser.@NotNull TagProvider tagProvider) {
        super(elementNode, token2, text);
        this.parts = genParts(token2, text, tagProvider);
        if (this.parts.isEmpty()) {
            throw new ParsingExceptionImpl(
                    "Tag has no parts? " + this, this.sourceMessage(), this.token());
        }
    }

    @NotNull
    private static List<TagPart> genParts(
            @NotNull Token token,
            @NotNull String text,
            TokenParser.@NotNull TagProvider tagProvider) {
        ArrayList<TagPart> values = new ArrayList<>();
        if (token.childTokens() != null) {
            for (Token token2 : token.childTokens()) {
                values.add(new TagPart(text, token2, tagProvider));
            }
        }
        return values;
    }

    @NotNull
    public List<TagPart> parts() {
        return this.parts;
    }

    @NotNull
    public String name() {
        return this.parts.get(0).value();
    }

    @NotNull
    @Override
    public Token token() {
        return Objects.requireNonNull(super.token(), "token is not set");
    }

    @NotNull
    public Tag tag() {
        return Objects.requireNonNull(this.tag, "no tag set");
    }

    public void tag(@NotNull Tag tag2) {
        this.tag = tag2;
    }

    @NotNull
    @Override
    public StringBuilder buildToString(@NotNull StringBuilder stringBuilder, int value2) {
        char[] values = this.ident(value2);
        stringBuilder.append(values).append("TagNode(");
        int size2 = this.parts.size();
        for (int index = 0; index < size2; index++) {
            TagPart tagPart = this.parts.get(index);
            stringBuilder.append('\'').append(tagPart.value()).append('\'');
            if (index != size2 - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") {\n");
        Iterator<
                        it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node
                                .ElementNode>
                iterator2 = this.children().iterator();
        while (iterator2.hasNext()) {
            ((ElementNode) iterator2.next()).buildToString(stringBuilder, value2 + 1);
        }
        stringBuilder.append(values).append("}\n");
        return stringBuilder;
    }
}
