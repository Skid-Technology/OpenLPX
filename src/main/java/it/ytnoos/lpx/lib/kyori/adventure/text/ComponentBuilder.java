package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.MutableStyleSetter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@NonExtendable
public interface ComponentBuilder<
                C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
        extends AbstractBuilder<C>,
                Buildable.Builder<C>,
                ComponentBuilderApplicable,
                ComponentLike,
                MutableStyleSetter<B> {

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull Component component);

    @Contract("_ -> this")
    @NotNull
    default B append(@NotNull ComponentLike componentLike) {
        return this.append(componentLike.asComponent());
    }

    @Contract("_ -> this")
    @NotNull
    default B append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return this.append(componentBuilder.build());
    }

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull Component... component);

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull ComponentLike... componentLike);

    @Contract("_ -> this")
    @NotNull
    B append(@NotNull Iterable<? extends ComponentLike> iterable);

    @NotNull
    default B appendNewline() {
        return this.append(Component.newline());
    }

    @NotNull
    default B appendSpace() {
        return this.append(Component.space());
    }

    @Contract("_ -> this")
    @NotNull
    default B apply(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer) {
        consumer.accept(this);
        return (B) this;
    }

    @Contract("_ -> this")
    @NotNull
    B applyDeep(@NotNull Consumer<? super ComponentBuilder<?, ?>> consumer);

    @Contract("_ -> this")
    @NotNull
    B mapChildren(
            @NotNull
                    Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>>
                            function);

    @Contract("_ -> this")
    @NotNull
    B mapChildrenDeep(
            @NotNull
                    Function<BuildableComponent<?, ?>, ? extends BuildableComponent<?, ?>>
                            function);

    @NotNull
    List<Component> children();

    @Contract("_ -> this")
    @NotNull
    B style(@NotNull Style style2);

    @Contract("_ -> this")
    @NotNull
    B style(@NotNull Consumer<Style.Builder> consumer);

    @Contract("_ -> this")
    @NotNull
    B font(@Nullable Key key);

    @Contract("_ -> this")
    @NotNull
    B color(@Nullable TextColor textColor);

    @Contract("_ -> this")
    @NotNull
    B colorIfAbsent(@Nullable TextColor textColor);

    @Contract("_, _ -> this")
    @NotNull
    default B decorations(@NotNull Set<TextDecoration> values, boolean matches) {
        return (B) MutableStyleSetter.super.decorations(values, matches);
    }

    @Contract("_ -> this")
    @NotNull
    default B decorate(@NotNull TextDecoration textDecoration) {
        return this.decoration(textDecoration, TextDecoration.State.TRUE);
    }

    @Contract("_ -> this")
    @NotNull
    default B decorate(@NotNull TextDecoration... textDecoration) {
        return (B) MutableStyleSetter.super.decorate(textDecoration);
    }

    @Contract("_, _ -> this")
    @NotNull
    default B decoration(@NotNull TextDecoration textDecoration, boolean matches) {
        return this.decoration(textDecoration, TextDecoration.State.byBoolean(matches));
    }

    @Contract("_ -> this")
    @NotNull
    default B decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries) {
        return (B) MutableStyleSetter.super.decorations(entries);
    }

    @Contract("_, _ -> this")
    @NotNull
    B decoration(@NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @Contract("_, _ -> this")
    @NotNull
    B decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @Contract("_ -> this")
    @NotNull
    B clickEvent(@Nullable ClickEvent clickEvent2);

    @Contract("_ -> this")
    @NotNull
    B hoverEvent(@Nullable HoverEventSource<?> hoverEventSource);

    @Contract("_ -> this")
    @NotNull
    B insertion(@Nullable String text);

    @Contract("_ -> this")
    @NotNull
    default B mergeStyle(@NotNull Component component) {
        return this.mergeStyle(component, Style.Merge.all());
    }

    @Contract("_, _ -> this")
    @NotNull
    default B mergeStyle(@NotNull Component component, Style.@NotNull Merge @NotNull ... merge) {
        return this.mergeStyle(component, Style.Merge.merges(merge));
    }

    @Contract("_, _ -> this")
    @NotNull
    B mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> values);

    @NotNull
    B resetStyle();

    @NotNull
    C build();

    @Contract("_ -> this")
    @NotNull
    default B applicableApply(@NotNull ComponentBuilderApplicable componentBuilderApplicable) {
        componentBuilderApplicable.componentBuilderApply(this);
        return (B) this;
    }

    @Override
    default void componentBuilderApply(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        componentBuilder.append(this);
    }

    @NotNull
    @Override
    default Component asComponent() {
        return this.build();
    }
}
