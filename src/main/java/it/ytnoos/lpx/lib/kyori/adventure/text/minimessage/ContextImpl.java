package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.Token;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.parser.node.TagPart;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

class ContextImpl implements Context {

    private static final Token[] EMPTY_TOKEN_ARRAY = new Token[0];

    private final boolean strict;

    private final boolean emitVirtuals;

    private final Consumer<String> debugOutput;

    private String message;

    private final MiniMessage miniMessage;

    @Nullable private final Pointered target;

    private final TagResolver tagResolver;

    private final UnaryOperator<String> preProcessor;

    private final UnaryOperator<Component> postProcessor;

    ContextImpl(
            boolean strict,
            boolean emitVirtuals,
            Consumer<String> consumer,
            String message,
            MiniMessage miniMessage2,
            @Nullable Pointered pointered,
            @Nullable TagResolver tagResolver2,
            @Nullable UnaryOperator<String> unaryOperator,
            @Nullable UnaryOperator<Component> unaryOperator2) {
        this.strict = strict;
        this.emitVirtuals = emitVirtuals;
        this.debugOutput = consumer;
        this.message = message;
        this.miniMessage = miniMessage2;
        this.target = pointered;
        this.tagResolver = tagResolver2 == null ? TagResolver.empty() : tagResolver2;
        this.preProcessor = unaryOperator == null ? UnaryOperator.identity() : unaryOperator;
        this.postProcessor = unaryOperator2 == null ? UnaryOperator.identity() : unaryOperator2;
    }

    public boolean strict() {
        return this.strict;
    }

    @Override
    public boolean emitVirtuals() {
        return this.emitVirtuals;
    }

    public Consumer<String> debugOutput() {
        return this.debugOutput;
    }

    @NotNull
    public String message() {
        return this.message;
    }

    void message(@NotNull String message) {
        this.message = message;
    }

    @NotNull
    public TagResolver extraTags() {
        return this.tagResolver;
    }

    public UnaryOperator<Component> postProcessor() {
        return this.postProcessor;
    }

    public UnaryOperator<String> preProcessor() {
        return this.preProcessor;
    }

    @Nullable
    @Override
    public Pointered target() {
        return this.target;
    }

    @NotNull
    @Override
    public Pointered targetOrThrow() {
        return this.target;
    }

    @NotNull
    @Override
    public <T extends Pointered> T targetAsType(@NotNull Class<T> classValue) {
        if (Objects.requireNonNull(classValue, "targetClass").isInstance(this.target)) {
            return (T) classValue.cast(this.target);
        } else {
            throw this.newException(
                    "A target with type "
                            + classValue.getSimpleName()
                            + " is required for this deserialization attempt");
        }
    }

    @NotNull
    @Override
    public Component deserialize(@NotNull String text) {
        return this.deserializeWithOptionalTarget(
                Objects.requireNonNull(text, "message"), this.tagResolver);
    }

    @NotNull
    @Override
    public Component deserialize(@NotNull String text, @NotNull TagResolver tagResolver2) {
        Objects.requireNonNull(text, "message");
        TagResolver tagResolver3 =
                TagResolver.builder().resolver(this.tagResolver).resolver(tagResolver2).build();
        return this.deserializeWithOptionalTarget(text, tagResolver3);
    }

    @NotNull
    @Override
    public Component deserialize(@NotNull String text, @NotNull TagResolver... tagResolver2) {
        Objects.requireNonNull(text, "message");
        TagResolver tagResolver3 =
                TagResolver.builder().resolver(this.tagResolver).resolvers(tagResolver2).build();
        return this.deserializeWithOptionalTarget(text, tagResolver3);
    }

    @NotNull
    @Override
    public ParsingException newException(@NotNull String text) {
        return new ParsingExceptionImpl(text, this.message, null, false, EMPTY_TOKEN_ARRAY);
    }

    @NotNull
    @Override
    public ParsingException newException(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue) {
        return new ParsingExceptionImpl(
                text,
                this.message,
                null,
                false,
                tagsToTokens(((ArgumentQueueImpl) argumentQueue).args));
    }

    @NotNull
    @Override
    public ParsingException newException(
            @NotNull String text,
            @Nullable Throwable throwable,
            @NotNull ArgumentQueue argumentQueue) {
        return new ParsingExceptionImpl(
                text,
                this.message,
                throwable,
                false,
                tagsToTokens(((ArgumentQueueImpl) argumentQueue).args));
    }

    @NotNull
    private Component deserializeWithOptionalTarget(
            @NotNull String text, @NotNull TagResolver tagResolver) {
        return this.target != null
                ? this.miniMessage.deserialize(text, this.target, tagResolver)
                : this.miniMessage.deserialize(text, tagResolver);
    }

    private static Token[] tagsToTokens(List<? extends Tag.Argument> values) {
        Token[] tokens = new Token[values.size()];
        int value = 0;
        for (int value2 = tokens.length; value < value2; value++) {
            tokens[value] = ((TagPart) values.get(value)).token();
        }
        return tokens;
    }
}
