package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenParser;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.TokenType;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.ElementNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.RootNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.ValueNode;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Inserting;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Modifying;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.string.MultiLineStringExaminer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

final class MiniMessageParser {

    final TagResolver tagResolver;

    MiniMessageParser() {
        this.tagResolver = TagResolver.standard();
    }

    MiniMessageParser(TagResolver tagResolver2) {
        this.tagResolver = tagResolver2;
    }

    @NotNull
    String escapeTokens(@NotNull ContextImpl contextImpl) {
        StringBuilder stringBuilder = new StringBuilder(contextImpl.message().length());
        this.escapeTokens(stringBuilder, contextImpl);
        return stringBuilder.toString();
    }

    void escapeTokens(StringBuilder stringBuilder, @NotNull ContextImpl contextImpl) {
        this.escapeTokens(stringBuilder, contextImpl.message(), contextImpl);
    }

    private void escapeTokens(StringBuilder stringBuilder, String text2, ContextImpl contextImpl) {
        this.processTokens(
                stringBuilder,
                text2,
                contextImpl,
                (result, result2) -> {
                    result2.append('\\').append('<');
                    if (result.type() == TokenType.CLOSE_TAG) {
                        result2.append('/');
                    }
                    List<Token> values = result.childTokens();
                    for (int index = 0; index < values.size(); index++) {
                        if (index != 0) {
                            result2.append(':');
                        }
                        this.escapeTokens(
                                result2,
                                ((Token) values.get(index)).get(text2).toString(),
                                contextImpl);
                    }
                    result2.append('>');
                });
    }

    @NotNull
    String stripTokens(@NotNull ContextImpl contextImpl) {
        StringBuilder stringBuilder = new StringBuilder(contextImpl.message().length());
        this.processTokens(stringBuilder, contextImpl, (result, result2) -> {});
        return stringBuilder.toString();
    }

    private void processTokens(
            @NotNull StringBuilder stringBuilder,
            @NotNull ContextImpl contextImpl,
            BiConsumer<Token, StringBuilder> biConsumer) {
        this.processTokens(stringBuilder, contextImpl.message(), contextImpl, biConsumer);
    }

    private void processTokens(
            @NotNull StringBuilder stringBuilder,
            @NotNull String text,
            @NotNull ContextImpl contextImpl,
            BiConsumer<Token, StringBuilder> biConsumer) {
        TagResolver tagResolver2 = TagResolver.resolver(this.tagResolver, contextImpl.extraTags());
        for (Token token : TokenParser.tokenize(text, true)) {
            switch (token.type()) {
                case TEXT:
                    stringBuilder.append(text, token.startIndex(), token.endIndex());
                    break;
                case OPEN_TAG:
                case CLOSE_TAG:
                case OPEN_CLOSE_TAG:
                    if (token.childTokens().isEmpty()) {
                        stringBuilder.append(text, token.startIndex(), token.endIndex());
                    } else {
                        String text2 =
                                TokenParser.TagProvider.sanitizePlaceholderName(
                                        token.childTokens().get(0).get(text).toString());
                        if (tagResolver2.has(text2)) {
                            biConsumer.accept(token, stringBuilder);
                        } else {
                            stringBuilder.append(text, token.startIndex(), token.endIndex());
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported token type " + token.type());
            }
        }
    }

    @NotNull
    RootNode parseToTree(@NotNull ContextImpl contextImpl) {
        TagResolver tagResolver2 = TagResolver.resolver(this.tagResolver, contextImpl.extraTags());
        String text = contextImpl.preProcessor().apply(contextImpl.message());
        Consumer<String> consumer;
        if ((consumer = contextImpl.debugOutput()) != null) {
            consumer.accept("Beginning parsing message ");
            consumer.accept(text);
            consumer.accept("\n");
        }
        TokenParser.TagProvider tagProvider;
        if (consumer != null) {
            Consumer<String> consumer2 = consumer;
            tagProvider =
                    (result, result2, result3) -> {
                        try {
                            consumer2.accept("Attempting to match node '");
                            consumer2.accept(result);
                            consumer2.accept("'");
                            if (result3 != null) {
                                consumer2.accept(" at column ");
                                consumer2.accept(String.valueOf(result3.startIndex()));
                            }
                            consumer2.accept("\n");
                            Tag tag;
                            if ((tag =
                                            tagResolver2.resolve(
                                                    result,
                                                    new ArgumentQueueImpl<>(contextImpl, result2),
                                                    contextImpl))
                                    == null) {
                                consumer2.accept("Could not match node '");
                                consumer2.accept(result);
                                consumer2.accept("'\n");
                            } else {
                                consumer2.accept("Successfully matched node '");
                                consumer2.accept(result);
                                consumer2.accept("' to tag ");
                                consumer2.accept(
                                        tag instanceof Examinable
                                                ? ((Examinable) tag).examinableName()
                                                : tag.getClass().getName());
                                consumer2.accept("\n");
                            }
                            return tag;
                        } catch (ParsingException parsingException) {
                            ParsingExceptionImpl parsingExceptionImpl;
                            if (result3 != null
                                    && parsingException instanceof ParsingExceptionImpl
                                    && (parsingExceptionImpl =
                                                            (ParsingExceptionImpl) parsingException)
                                                    .tokens()
                                                    .length
                                            == 0) {
                                parsingExceptionImpl.tokens(new Token[] {result3});
                            }
                            consumer2.accept("Could not match node '");
                            consumer2.accept(result);
                            consumer2.accept("' - ");
                            consumer2.accept(parsingException.getMessage());
                            consumer2.accept("\n");
                            return null;
                        }
                    };
        } else {
            tagProvider =
                    (result4, result, result2) -> {
                        try {
                            return tagResolver2.resolve(
                                    result4,
                                    new ArgumentQueueImpl<>(contextImpl, result),
                                    contextImpl);
                        } catch (ParsingException result3) {
                            return null;
                        }
                    };
        }
        Predicate predicate =
                result5 -> {
                    String result4 =
                            TokenParser.TagProvider.sanitizePlaceholderName(((String) result5));
                    return tagResolver2.has(result4);
                };
        String text2 = TokenParser.resolvePreProcessTags(text, tagProvider);
        contextImpl.message(text2);
        RootNode rootNode =
                TokenParser.parse(tagProvider, predicate, text2, text, contextImpl.strict());
        if (consumer != null) {
            consumer.accept("Text parsed into element tree:\n");
            consumer.accept(rootNode.toString());
        }
        return rootNode;
    }

    @NotNull
    Component parseFormat(@NotNull ContextImpl contextImpl) {
        RootNode rootNode = this.parseToTree(contextImpl);
        return Objects.requireNonNull(
                contextImpl.postProcessor().apply(this.treeToComponent(rootNode, contextImpl)),
                "Post-processor must not return null");
    }

    @NotNull
    Component treeToComponent(@NotNull ElementNode elementNode, @NotNull ContextImpl contextImpl) {
        Component component = Component.empty();
        Tag tag2 = null;
        if (elementNode instanceof ValueNode) {
            component = Component.text(((ValueNode) elementNode).value());
        } else if (elementNode instanceof TagNode) {
            TagNode tagNode;
            if ((tag2 = (tagNode = (TagNode) elementNode).tag()) instanceof Modifying) {
                Modifying modifying = (Modifying) tag2;
                this.visitModifying(modifying, tagNode, 0);
                modifying.postVisit();
            }
            if (tag2 instanceof Inserting) {
                component = ((Inserting) tag2).value();
            }
        }
        if (!elementNode.unsafeChildren().isEmpty()) {
            ArrayList<Component> values;
            (values = new ArrayList(component.children().size() + elementNode.children().size()))
                    .addAll(component.children());
            for (ElementNode elementNode2 : elementNode.unsafeChildren()) {
                values.add(this.treeToComponent(elementNode2, contextImpl));
            }
            component = component.children(values);
        }
        if (tag2 instanceof Modifying) {
            component = this.handleModifying((Modifying) tag2, component, 0);
        }
        Consumer<String> consumer;
        if ((consumer = contextImpl.debugOutput()) != null) {
            consumer.accept("==========\ntreeToComponent \n");
            consumer.accept(elementNode.toString());
            consumer.accept("\n");
            consumer.accept(
                    component
                            .examine(MultiLineStringExaminer.simpleEscaping())
                            .collect(Collectors.joining("\n")));
            consumer.accept("\n==========\n");
        }
        return component;
    }

    private void visitModifying(Modifying modifying, ElementNode elementNode, int value) {
        modifying.visit(elementNode, value);
        for (ElementNode elementNode2 : elementNode.unsafeChildren()) {
            this.visitModifying(modifying, elementNode2, value + 1);
        }
    }

    private Component handleModifying(Modifying modifying, Component component, int value) {
        Component component2 = modifying.apply(component, value);
        for (Component component3 : component.children()) {
            component2 = component2.append(this.handleModifying(modifying, component3, value + 1));
        }
        return component2;
    }
}
