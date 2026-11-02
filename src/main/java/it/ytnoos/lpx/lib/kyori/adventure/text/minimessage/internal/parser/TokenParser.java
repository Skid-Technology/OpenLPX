package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match.MatchedTokenConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match.StringResolvingMatchedTokenConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.match.TokenListProducingMatchedTokenConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.ElementNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.RootNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagPart;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TextNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Inserting;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.ParserDirective;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

@Internal
public final class TokenParser {

    public static final char TAG_START = '<';

    public static final char TAG_END = '>';

    public static final char CLOSE_TAG = '/';

    public static final char SEPARATOR = ':';

    public static final char ESCAPE = '\\';

    private TokenParser() {}

    public static RootNode parse(
            @NotNull TokenParser.TagProvider tagProvider,
            @NotNull Predicate<String> predicate,
            @NotNull String text2,
            @NotNull String text3,
            boolean matches)
            throws ParsingException {
        List<it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token> values =
                tokenize(text2, false);
        return buildTree(tagProvider, predicate, values, text2, text3, matches);
    }

    public static String resolvePreProcessTags(String text, TokenParser.TagProvider tagProvider) {
        int value = 0;
        String text2 = text;
        String text3;
        do {
            text3 = text2;
            StringResolvingMatchedTokenConsumer stringResolvingMatchedTokenConsumer =
                    new StringResolvingMatchedTokenConsumer(text3, tagProvider);
            parseString(text3, false, stringResolvingMatchedTokenConsumer);
            text2 = stringResolvingMatchedTokenConsumer.result();
        } while (++value < 16 && !text3.equals(text2));
        return text3;
    }

    public static List<Token> tokenize(String text, boolean matches) {
        TokenListProducingMatchedTokenConsumer tokenListProducingMatchedTokenConsumer =
                new TokenListProducingMatchedTokenConsumer(text);
        parseString(text, matches, tokenListProducingMatchedTokenConsumer);
        List<Token> values = tokenListProducingMatchedTokenConsumer.result();
        parseSecondPass(text, values);
        return values;
    }

    public static void parseString(
            String text, boolean matches, MatchedTokenConsumer<?> matchedTokenConsumer) {
        TokenParser.FirstPassState firstPassState = TokenParser.FirstPassState.NORMAL;
        boolean matches2 = false;
        int value = 0;
        int value2 = -1;
        char value3 = 0;
        int size = text.length();
        for (int index = 0; index < size; index++) {
            int value4 = text.codePointAt(index);
            int value5;
            if (!matches
                    && value4 == 167
                    && index + 1 < size
                    && ((value5 = Character.toLowerCase(text.codePointAt(index + 1))) >= 48
                                    && value5 <= 57
                            || value5 >= 97 && value5 <= 102
                            || value5 == 114
                            || value5 >= 107 && value5 <= 111)) {
                throw new ParsingExceptionImpl(
                        "Legacy formatting codes have been detected in a MiniMessage string - this is unsupported behaviour. Please refer to the Adventure documentation (https://docs.advntr.dev) for more information.",
                        text,
                        null,
                        true,
                        new Token(index, index + 2, TokenType.TEXT));
            }
            if (!Character.isBmpCodePoint(value4)) {
                index++;
            }
            if (!matches2) {
                if (value4 == 92 && index + 1 < text.length()) {
                    value5 = text.codePointAt(index + 1);
                    switch (firstPassState) {
                        case NORMAL:
                            matches2 = value5 == 60 || value5 == 92;
                            break;
                        case STRING:
                            matches2 = value3 == value5 || value5 == 92;
                            break;
                        case TAG:
                            if (value5 == 60) {
                                matches2 = true;
                                firstPassState = TokenParser.FirstPassState.NORMAL;
                            }
                    }
                    if (matches2) {
                        continue;
                    }
                }
                switch (firstPassState) {
                    case NORMAL:
                        if (value4 == 60) {
                            value2 = index;
                            firstPassState = TokenParser.FirstPassState.TAG;
                        }
                        break;
                    case STRING:
                        if (value4 == value3) {
                            firstPassState = TokenParser.FirstPassState.TAG;
                        }
                        break;
                    case TAG:
                        switch (value4) {
                            case 34:
                            case 39:
                                value3 = (char) value4;
                                if (text.indexOf(value4, index + 1) != -1) {
                                    firstPassState = TokenParser.FirstPassState.STRING;
                                }
                                break;
                            case 60:
                                value2 = index;
                                break;
                            case 62:
                                if (index == value2 + 1) {
                                    firstPassState = TokenParser.FirstPassState.NORMAL;
                                } else {
                                    if (value != value2) {
                                        matchedTokenConsumer.accept(value, value2, TokenType.TEXT);
                                    }
                                    value = index + 1;
                                    TokenType tokenType = TokenType.OPEN_TAG;
                                    if (boundsCheck(text, value2, 1)
                                            && text.charAt(value2 + 1) == '/') {
                                        tokenType = TokenType.CLOSE_TAG;
                                    } else if (boundsCheck(text, value2, 2)
                                            && text.charAt(index - 1) == '/') {
                                        tokenType = TokenType.OPEN_CLOSE_TAG;
                                    }
                                    matchedTokenConsumer.accept(value2, value, tokenType);
                                    firstPassState = TokenParser.FirstPassState.NORMAL;
                                }
                        }
                }
                if (index == size - 1 && firstPassState == TokenParser.FirstPassState.TAG) {
                    index = value2;
                    firstPassState = TokenParser.FirstPassState.NORMAL;
                }
            } else {
                matches2 = false;
            }
        }
        int value6;
        if ((value6 = matchedTokenConsumer.lastEndIndex()) == -1) {
            matchedTokenConsumer.accept(0, text.length(), TokenType.TEXT);
        } else if (value6 != text.length()) {
            matchedTokenConsumer.accept(value6, text.length(), TokenType.TEXT);
        }
    }

    private static void parseSecondPass(String text2, List<Token> values) {
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token>
                iterator2 = values.iterator();
        while (iterator2.hasNext()) {
            Token token;
            TokenType tokenType;
            if ((tokenType = (token = (Token) iterator2.next()).type()) == TokenType.OPEN_TAG
                    || tokenType == TokenType.OPEN_CLOSE_TAG
                    || tokenType == TokenType.CLOSE_TAG) {
                int value =
                        tokenType == TokenType.CLOSE_TAG
                                ? token.startIndex() + 2
                                : token.startIndex() + 1;
                int value2 =
                        tokenType == TokenType.OPEN_CLOSE_TAG
                                ? token.endIndex() - 2
                                : token.endIndex() - 1;
                TokenParser.SecondPassState secondPassState = TokenParser.SecondPassState.NORMAL;
                boolean matches = false;
                char value3 = 0;
                int value4 = value;
                for (int value5 = value; value5 < value2; value5++) {
                    int value6 = text2.codePointAt(value5);
                    if (!Character.isBmpCodePoint(value5)) {
                        value5++;
                    }
                    if (!matches) {
                        if (value6 == 92 && value5 + 1 < text2.length()) {
                            int value7 = text2.codePointAt(value5 + 1);
                            switch (secondPassState) {
                                case NORMAL:
                                    matches = value7 == 60 || value7 == 92;
                                    break;
                                case STRING:
                                    matches = value3 == value7 || value7 == 92;
                            }
                            if (matches) {
                                continue;
                            }
                        }
                        switch (secondPassState) {
                            case NORMAL:
                                if (value6 == 58) {
                                    if (!boundsCheck(text2, value5, 2)
                                            || text2.charAt(value5 + 1) != '/'
                                            || text2.charAt(value5 + 2) != '/') {
                                        if (value4 == value5) {
                                            insert(
                                                    token,
                                                    new Token(value5, value5, TokenType.TAG_VALUE));
                                            value4++;
                                        } else {
                                            insert(
                                                    token,
                                                    new Token(value4, value5, TokenType.TAG_VALUE));
                                            value4 = value5 + 1;
                                        }
                                    }
                                } else if (value6 == 39 || value6 == 34) {
                                    secondPassState = TokenParser.SecondPassState.STRING;
                                    value3 = (char) value6;
                                }
                                break;
                            case STRING:
                                if (value6 == value3) {
                                    secondPassState = TokenParser.SecondPassState.NORMAL;
                                }
                        }
                    } else {
                        matches = false;
                    }
                }
                if (token.childTokens() != null && !token.childTokens().isEmpty()) {
                    int value8;
                    if ((value8 =
                                    token.childTokens()
                                            .get(token.childTokens().size() - 1)
                                            .endIndex())
                            != value2) {
                        insert(token, new Token(value8 + 1, value2, TokenType.TAG_VALUE));
                    }
                } else {
                    insert(token, new Token(value, value2, TokenType.TAG_VALUE));
                }
            }
        }
    }

    private static RootNode buildTree(
            @NotNull TokenParser.TagProvider tagProvider,
            @NotNull Predicate<String> predicate,
            @NotNull List<Token> values,
            @NotNull String text2,
            @NotNull String text3,
            boolean matches)
            throws ParsingException {
        RootNode rootNode;
        ElementNode elementNode = rootNode = new RootNode(text2, text3);
        Iterator<it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token>
                iterator2 = values.iterator();
        while (iterator2.hasNext()) {
            Token token2;
            TokenType tokenType = (token2 = (Token) iterator2.next()).type();
            switch (tokenType) {
                case TEXT:
                    elementNode.addChild(new TextNode(elementNode, token2, text2));
                    break;
                case OPEN_TAG:
                case OPEN_CLOSE_TAG:
                    Token token3 = token2.childTokens().get(0);
                    if (!TagInternals.sanitizeAndCheckValidTagName(
                            text2.substring(token3.startIndex(), token3.endIndex()))) {
                        elementNode.addChild(new TextNode(elementNode, token2, text2));
                    } else {
                        TagNode tagNode = new TagNode(elementNode, token2, text2, tagProvider);
                        if (predicate.test(tagNode.name())) {
                            Tag tag2;
                            if ((tag2 = tagProvider.resolve(tagNode)) == null) {
                                elementNode.addChild(new TextNode(elementNode, token2, text2));
                                continue;
                            }
                            if (tag2 == ParserDirective.RESET) {
                                if (matches) {
                                    throw new ParsingExceptionImpl(
                                            "<reset> tags are not allowed when strict mode is enabled",
                                            text2,
                                            token2);
                                }
                                elementNode = rootNode;
                                continue;
                            }
                            tagNode.tag(tag2);
                            elementNode.addChild(tagNode);
                            if (tokenType != TokenType.OPEN_CLOSE_TAG
                                    && (!(tag2 instanceof Inserting)
                                            || ((Inserting) tag2).allowsChildren())) {
                                elementNode = tagNode;
                            }
                            continue;
                        }
                        elementNode.addChild(new TextNode(elementNode, token2, text2));
                    }
                    break;
                case CLOSE_TAG:
                    List<it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token>
                            values2;
                    if ((values2 = token2.childTokens()).isEmpty()) {
                        throw new IllegalStateException(
                                "CLOSE_TAG token somehow has no children - the parser should not allow this. Original text: "
                                        + text2);
                    }
                    ArrayList<String> values3 = new ArrayList<>(values2.size());
                    for (Token token4 : values2) {
                        values3.add(
                                TagPart.unquoteAndEscape(
                                        text2, token4.startIndex(), token4.endIndex()));
                    }
                    String text4 = (String) values3.get(0);
                    if (!predicate.test(text4)) {
                        elementNode.addChild(new TextNode(elementNode, token2, text2));
                    } else if (tagProvider.resolve(text4) != ParserDirective.RESET) {
                        ElementNode elementNode2;
                        for (elementNode2 = elementNode;
                                elementNode2 instanceof TagNode;
                                elementNode2 = elementNode2.parent()) {
                            List<TagPart> values4 = ((TagNode) elementNode2).parts();
                            if (tagCloses(values3, values4)) {
                                if (elementNode2 != elementNode && matches) {
                                    String text5 =
                                            "Unclosed tag encountered; "
                                                    + ((TagNode) elementNode).name()
                                                    + " is not closed, because "
                                                    + (String) values3.get(0)
                                                    + " was closed first.";
                                    throw new ParsingExceptionImpl(
                                            text5,
                                            text2,
                                            elementNode2.token(),
                                            elementNode.token(),
                                            token2);
                                }
                                ElementNode elementNode3;
                                if ((elementNode3 = elementNode2.parent()) == null) {
                                    throw new IllegalStateException(
                                            "Root node matched with close tag value, this should not be possible. Original text: "
                                                    + text2);
                                }
                                elementNode = elementNode3;
                                break;
                            }
                        }
                        if (elementNode2 == null || elementNode2 instanceof RootNode) {
                            elementNode.addChild(new TextNode(elementNode, token2, text2));
                        }
                    }
            }
        }
        if (matches && rootNode != elementNode) {
            ArrayList<TagNode> values5 = new ArrayList<>();
            for (ElementNode elementNode4 = elementNode;
                    elementNode4 != null && elementNode4 instanceof TagNode;
                    elementNode4 = elementNode4.parent()) {
                values5.add((TagNode) elementNode4);
            }
            Token[] tokens = new Token[values5.size()];
            StringBuilder stringBuilder =
                    new StringBuilder(
                            "All tags must be explicitly closed while in strict mode. End of string found with open tags: ");
            int value = 0;
            ListIterator listIterator2 = values5.listIterator(values5.size());
            while (listIterator2.hasPrevious()) {
                TagNode tagNode2 = (TagNode) listIterator2.previous();
                tokens[value++] = tagNode2.token();
                stringBuilder.append(tagNode2.name());
                if (listIterator2.hasPrevious()) {
                    stringBuilder.append(", ");
                }
            }
            throw new ParsingExceptionImpl(stringBuilder.toString(), text2, tokens);
        } else {
            return rootNode;
        }
    }

    private static boolean tagCloses(List<String> values, List<TagPart> values2) {
        if (values.size() > values2.size()) {
            return false;
        }
        if (!((String) values.get(0)).equalsIgnoreCase(((TagPart) values2.get(0)).value())) {
            return false;
        }
        for (int index = 1; index < values.size(); index++) {
            if (!((String) values.get(index)).equals(((TagPart) values2.get(index)).value())) {
                return false;
            }
        }
        return true;
    }

    private static boolean boundsCheck(String text, int value, int value2) {
        return value + value2 < text.length();
    }

    private static void insert(Token token, Token token2) {
        if (token.childTokens() == null) {
            token.childTokens(Collections.singletonList(token2));
        } else {
            if (token.childTokens().size() == 1) {
                ArrayList<it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token>
                        values;
                (values = new ArrayList(3)).add(token.childTokens().get(0));
                values.add(token2);
                token.childTokens(values);
            } else {
                token.childTokens().add(token2);
            }
        }
    }

    public static String unescape(String text, int value, int value2, IntPredicate intPredicate) {
        int value3 = value;
        int value4;
        if ((value4 = text.indexOf(92, value3)) != -1 && value4 < value2) {
            StringBuilder stringBuilder = new StringBuilder(value2 - value);
            while (value4 != -1 && value4 + 1 < value2) {
                if (intPredicate.test(text.codePointAt(value4 + 1))) {
                    stringBuilder.append(text, value3, value4);
                    if (++value4 >= value2) {
                        value3 = value2;
                        break;
                    }
                    int value5 = text.codePointAt(value4);
                    stringBuilder.appendCodePoint(value5);
                    if (Character.isBmpCodePoint(value5)) {
                        value4++;
                    } else {
                        value4 += 2;
                    }
                    if (value4 >= value2) {
                        value3 = value2;
                        break;
                    }
                } else {
                    stringBuilder.append(text, value3, ++value4);
                }
                value3 = value4;
                value4 = text.indexOf(92, value3);
            }
            stringBuilder.append(text, value3, value2);
            return stringBuilder.toString();
        } else {
            return text.substring(value3, value2);
        }
    }

    enum FirstPassState {
        NORMAL,
        TAG,
        STRING
    }

    enum SecondPassState {
        NORMAL,
        STRING
    }

    @Internal
    public interface TagProvider {

        @Nullable
        Tag resolve(
                @NotNull String text,
                @NotNull List<? extends Tag.Argument> values,
                @Nullable Token token);

        @Nullable
        default Tag resolve(@NotNull String text) {
            return this.resolve(text, Collections.emptyList(), null);
        }

        @Nullable
        default Tag resolve(@NotNull TagNode tagNode) {
            return this.resolve(
                    sanitizePlaceholderName(tagNode.name()),
                    tagNode.parts().subList(1, tagNode.parts().size()),
                    tagNode.token());
        }

        @NotNull
        static String sanitizePlaceholderName(@NotNull String text) {
            return text.toLowerCase(Locale.ROOT);
        }
    }
}
