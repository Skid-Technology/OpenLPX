package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenType;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagPart;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.PreProcess;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StringResolvingMatchedTokenConsumer extends MatchedTokenConsumer<String> {

    private final StringBuilder builder;

    private final TokenParser.TagProvider tagProvider;

    public StringResolvingMatchedTokenConsumer(
            @NotNull String text, @NotNull TokenParser.TagProvider tagProvider2) {
        super(text);
        this.builder = new StringBuilder(text.length());
        this.tagProvider = tagProvider2;
    }

    @Override
    public void accept(int value2, int value3, @NotNull TokenType tokenType) {
        super.accept(value2, value3, tokenType);
        if (tokenType != TokenType.OPEN_TAG) {
            this.builder.append(this.input, value2, value3);
        } else {
            String text2 = this.input.substring(value2, value3);
            String text3;
            int value4;
            String text4;
            if (TagInternals.sanitizeAndCheckValidTagName(
                    text4 =
                            (value4 =
                                                    (text3 =
                                                                    this.input.substring(
                                                                            value2 + 1, value3 - 1))
                                                            .indexOf(58))
                                            == -1
                                    ? text3
                                    : text3.substring(0, value4))) {
                List<Token> values = TokenParser.tokenize(text2, false);
                ArrayList<TagPart> values2 = new ArrayList<>();
                List<Token> values3;
                if ((values3 = values.isEmpty() ? null : ((Token) values.get(0)).childTokens())
                        != null) {
                    for (int index = 1; index < values3.size(); index++) {
                        values2.add(
                                new TagPart(text2, (Token) values3.get(index), this.tagProvider));
                    }
                }
                Tag tag;
                if ((tag =
                                this.tagProvider.resolve(
                                        TokenParser.TagProvider.sanitizePlaceholderName(text4),
                                        values2,
                                        (Token) values.get(0)))
                        instanceof PreProcess) {
                    this.builder.append(
                            Objects.requireNonNull(
                                    ((PreProcess) tag).value(),
                                    "PreProcess replacements cannot return null"));
                    return;
                }
            }
            this.builder.append(text2);
        }
    }

    @NotNull
    public String result() {
        return this.builder.toString();
    }
}
