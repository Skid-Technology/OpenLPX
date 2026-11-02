package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree.Node;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

final class MiniMessageImpl implements MiniMessage {

    private static final Optional<MiniMessage.Provider> SERVICE =
            Services.service(MiniMessage.Provider.class);

    static final Consumer<MiniMessage.Builder> BUILDER =
            SERVICE.map(MiniMessage.Provider::builder).orElseGet(() -> result -> {});

    static final UnaryOperator<String> DEFAULT_NO_OP = UnaryOperator.identity();

    static final UnaryOperator<Component> DEFAULT_COMPACTING_METHOD = Component::compact;

    private final boolean strict;

    private final boolean emitVirtuals;

    @Nullable private final Consumer<String> debugOutput;

    private final UnaryOperator<Component> postProcessor;

    private final UnaryOperator<String> preProcessor;

    final MiniMessageParser parser;

    MiniMessageImpl(
            @NotNull TagResolver tagResolver,
            boolean strict,
            boolean emitVirtuals,
            @Nullable Consumer<String> consumer,
            @NotNull UnaryOperator<String> unaryOperator,
            @NotNull UnaryOperator<Component> unaryOperator2) {
        this.parser = new MiniMessageParser(tagResolver);
        this.strict = strict;
        this.emitVirtuals = emitVirtuals;
        this.debugOutput = consumer;
        this.preProcessor = unaryOperator;
        this.postProcessor = unaryOperator2;
    }

    @NotNull
    public Component deserialize(@NotNull String text) {
        return this.parser.parseFormat(this.newContext(text, null, null));
    }

    @NotNull
    @Override
    public Component deserialize(@NotNull String text, @NotNull Pointered pointered) {
        return this.parser.parseFormat(
                this.newContext(text, Objects.requireNonNull(pointered, "target"), null));
    }

    @NotNull
    @Override
    public Component deserialize(@NotNull String text, @NotNull TagResolver tagResolver) {
        return this.parser.parseFormat(
                this.newContext(text, null, Objects.requireNonNull(tagResolver, "tagResolver")));
    }

    @NotNull
    @Override
    public Component deserialize(
            @NotNull String text, @NotNull Pointered pointered, @NotNull TagResolver tagResolver) {
        return this.parser.parseFormat(
                this.newContext(
                        text,
                        Objects.requireNonNull(pointered, "target"),
                        Objects.requireNonNull(tagResolver, "tagResolver")));
    }

    @Override
    public Node.@NotNull Root deserializeToTree(@NotNull String text) {
        return this.parser.parseToTree(this.newContext(text, null, null));
    }

    @Override
    public Node.@NotNull Root deserializeToTree(
            @NotNull String text, @NotNull Pointered pointered) {
        return this.parser.parseToTree(
                this.newContext(text, Objects.requireNonNull(pointered, "target"), null));
    }

    @Override
    public Node.@NotNull Root deserializeToTree(
            @NotNull String text, @NotNull TagResolver tagResolver) {
        return this.parser.parseToTree(
                this.newContext(text, null, Objects.requireNonNull(tagResolver, "tagResolver")));
    }

    @Override
    public Node.@NotNull Root deserializeToTree(
            @NotNull String text, @NotNull Pointered pointered, @NotNull TagResolver tagResolver) {
        return this.parser.parseToTree(
                this.newContext(
                        text,
                        Objects.requireNonNull(pointered, "target"),
                        Objects.requireNonNull(tagResolver, "tagResolver")));
    }

    @NotNull
    public String serialize(@NotNull Component component) {
        return MiniMessageSerializer.serialize(component, this.serialResolver(null), this.strict);
    }

    private SerializableResolver serialResolver(@Nullable TagResolver tagResolver2) {
        if (tagResolver2 == null) {
            if (this.parser.tagResolver instanceof SerializableResolver) {
                return (SerializableResolver) this.parser.tagResolver;
            }
        } else {
            TagResolver tagResolver3;
            if ((tagResolver3 = TagResolver.resolver(this.parser.tagResolver, tagResolver2))
                    instanceof SerializableResolver) {
                return (SerializableResolver) tagResolver3;
            }
        }
        return (SerializableResolver) TagResolver.empty();
    }

    @NotNull
    @Override
    public String escapeTags(@NotNull String text) {
        return this.parser.escapeTokens(this.newContext(text, null, null));
    }

    @NotNull
    @Override
    public String escapeTags(@NotNull String text, @NotNull TagResolver tagResolver) {
        return this.parser.escapeTokens(this.newContext(text, null, tagResolver));
    }

    @NotNull
    @Override
    public String stripTags(@NotNull String text) {
        return this.parser.stripTokens(this.newContext(text, null, null));
    }

    @NotNull
    @Override
    public String stripTags(@NotNull String text, @NotNull TagResolver tagResolver) {
        return this.parser.stripTokens(this.newContext(text, null, tagResolver));
    }

    @Override
    public boolean strict() {
        return this.strict;
    }

    @NotNull
    @Override
    public TagResolver tags() {
        return this.parser.tagResolver;
    }

    @NotNull
    private ContextImpl newContext(
            @NotNull String text,
            @Nullable Pointered pointered,
            @Nullable TagResolver tagResolver) {
        Objects.requireNonNull(text, "input");
        return new ContextImpl(
                this.strict,
                this.emitVirtuals,
                this.debugOutput,
                text,
                this,
                pointered,
                tagResolver,
                this.preProcessor,
                this.postProcessor);
    }

    static final class BuilderImpl implements MiniMessage.Builder {

        private TagResolver tagResolver = TagResolver.standard();

        private boolean strict = false;

        private boolean emitVirtuals = true;

        private Consumer<String> debug = null;

        private UnaryOperator<Component> postProcessor = MiniMessageImpl.DEFAULT_COMPACTING_METHOD;

        private UnaryOperator<String> preProcessor = MiniMessageImpl.DEFAULT_NO_OP;

        BuilderImpl() {
            MiniMessageImpl.BUILDER.accept(this);
        }

        BuilderImpl(MiniMessageImpl miniMessageImpl) {
            this();
            this.tagResolver = miniMessageImpl.parser.tagResolver;
            this.strict = miniMessageImpl.strict;
            this.debug = miniMessageImpl.debugOutput;
            this.postProcessor = miniMessageImpl.postProcessor;
            this.preProcessor = miniMessageImpl.preProcessor;
        }

        @NotNull
        @Override
        public MiniMessage.Builder tags(@NotNull TagResolver tagResolver2) {
            this.tagResolver = Objects.requireNonNull(tagResolver2, "tags");
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder editTags(@NotNull Consumer<TagResolver.Builder> consumer) {
            Objects.requireNonNull(consumer, "adder");
            TagResolver.Builder builder2 = TagResolver.builder().resolver(this.tagResolver);
            consumer.accept(builder2);
            this.tagResolver = builder2.build();
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder strict(boolean strict) {
            this.strict = strict;
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder emitVirtuals(boolean emitVirtuals) {
            this.emitVirtuals = emitVirtuals;
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder debug(@Nullable Consumer<String> consumer) {
            this.debug = consumer;
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder postProcessor(@NotNull UnaryOperator<Component> unaryOperator) {
            this.postProcessor = Objects.requireNonNull(unaryOperator, "postProcessor");
            return this;
        }

        @NotNull
        @Override
        public MiniMessage.Builder preProcessor(@NotNull UnaryOperator<String> unaryOperator) {
            this.preProcessor = Objects.requireNonNull(unaryOperator, "preProcessor");
            return this;
        }

        @NotNull
        @Override
        public MiniMessage build() {
            return new MiniMessageImpl(
                    this.tagResolver,
                    this.strict,
                    this.emitVirtuals,
                    this.debug,
                    this.preProcessor,
                    this.postProcessor);
        }
    }

    static final class Instances {

        static final MiniMessage INSTANCE =
                MiniMessageImpl.SERVICE
                        .map(MiniMessage.Provider::miniMessage)
                        .orElseGet(
                                () ->
                                        new MiniMessageImpl(
                                                TagResolver.standard(),
                                                false,
                                                true,
                                                null,
                                                MiniMessageImpl.DEFAULT_NO_OP,
                                                MiniMessageImpl.DEFAULT_COMPACTING_METHOD));
    }
}
