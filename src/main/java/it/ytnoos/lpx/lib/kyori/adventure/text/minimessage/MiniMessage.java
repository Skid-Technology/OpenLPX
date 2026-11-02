package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tree.Node;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.ComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface MiniMessage extends ComponentSerializer<Component, Component, String> {

    @NotNull
    static MiniMessage miniMessage() {
        return MiniMessageImpl.Instances.INSTANCE;
    }

    @NotNull
    String escapeTags(@NotNull String text);

    @NotNull
    String escapeTags(@NotNull String text, @NotNull TagResolver tagResolver);

    @NotNull
    default String escapeTags(@NotNull String text, @NotNull TagResolver... tagResolver) {
        return this.escapeTags(text, TagResolver.resolver(tagResolver));
    }

    @NotNull
    String stripTags(@NotNull String text);

    @NotNull
    String stripTags(@NotNull String text, @NotNull TagResolver tagResolver);

    @NotNull
    default String stripTags(@NotNull String text, @NotNull TagResolver... tagResolver) {
        return this.stripTags(text, TagResolver.resolver(tagResolver));
    }

    @NotNull
    Component deserialize(@NotNull String text, @NotNull Pointered pointered);

    @NotNull
    Component deserialize(@NotNull String text, @NotNull TagResolver tagResolver);

    @NotNull
    Component deserialize(
            @NotNull String text, @NotNull Pointered pointered, @NotNull TagResolver tagResolver);

    @NotNull
    default Component deserialize(@NotNull String text, @NotNull TagResolver... tagResolver) {
        return this.deserialize(text, TagResolver.resolver(tagResolver));
    }

    @NotNull
    default Component deserialize(
            @NotNull String text,
            @NotNull Pointered pointered,
            @NotNull TagResolver... tagResolver) {
        return this.deserialize(text, pointered, TagResolver.resolver(tagResolver));
    }

    Node.@NotNull Root deserializeToTree(@NotNull String text);

    Node.@NotNull Root deserializeToTree(@NotNull String text, @NotNull Pointered pointered);

    Node.@NotNull Root deserializeToTree(@NotNull String text, @NotNull TagResolver tagResolver);

    Node.@NotNull Root deserializeToTree(
            @NotNull String text, @NotNull Pointered pointered, @NotNull TagResolver tagResolver);

    default Node.@NotNull Root deserializeToTree(
            @NotNull String text, @NotNull TagResolver... tagResolver) {
        return this.deserializeToTree(text, TagResolver.resolver(tagResolver));
    }

    default Node.@NotNull Root deserializeToTree(
            @NotNull String text,
            @NotNull Pointered pointered,
            @NotNull TagResolver... tagResolver) {
        return this.deserializeToTree(text, pointered, TagResolver.resolver(tagResolver));
    }

    boolean strict();

    @NotNull
    TagResolver tags();

    static MiniMessage.Builder builder() {
        return new MiniMessageImpl.BuilderImpl();
    }

    interface Builder extends AbstractBuilder<MiniMessage> {

        @NotNull
        MiniMessage.Builder tags(@NotNull TagResolver tagResolver);

        @NotNull
        MiniMessage.Builder editTags(@NotNull Consumer<TagResolver.Builder> consumer);

        @NotNull
        MiniMessage.Builder strict(boolean matches);

        @NotNull
        MiniMessage.Builder emitVirtuals(boolean matches);

        @NotNull
        MiniMessage.Builder debug(@Nullable Consumer<String> consumer);

        @NotNull
        MiniMessage.Builder postProcessor(@NotNull UnaryOperator<Component> unaryOperator);

        @NotNull
        MiniMessage.Builder preProcessor(@NotNull UnaryOperator<String> unaryOperator);

        @NotNull
        MiniMessage build();
    }

    @PlatformAPI
    @Internal
    interface Provider {

        @PlatformAPI
        @Internal
        @NotNull
        MiniMessage miniMessage();

        @PlatformAPI
        @Internal
        @NotNull
        Consumer<MiniMessage.Builder> builder();
    }
}
