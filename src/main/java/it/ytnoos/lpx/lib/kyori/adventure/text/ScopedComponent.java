package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface ScopedComponent<C extends Component> extends Component {

    @NotNull
    @Override
    default C asComponent() {
        return (C) this;
    }

    @NotNull
    @Override
    C children(@NotNull List<? extends ComponentLike> values);

    @NotNull
    @Override
    C style(@NotNull Style style2);

    @NotNull
    @Override
    default C style(@NotNull Consumer<Style.Builder> consumer) {
        return (C) Component.super.style(consumer);
    }

    @NotNull
    @Override
    default C style(Style.@NotNull Builder builder) {
        return (C) Component.super.style(builder);
    }

    @NotNull
    @Override
    default C style(
            @NotNull Consumer<Style.Builder> consumer, Style.Merge.@NotNull Strategy strategy) {
        return (C) Component.super.style(consumer, strategy);
    }

    @NotNull
    @Override
    default C mergeStyle(@NotNull Component component) {
        return (C) Component.super.mergeStyle(component);
    }

    @NotNull
    @Override
    default C mergeStyle(@NotNull Component component, Style.@NotNull Merge @NotNull ... merge) {
        return (C) Component.super.mergeStyle(component, merge);
    }

    @NotNull
    @Override
    default C append(@NotNull Component component) {
        return (C) Component.super.append(component);
    }

    @NotNull
    @Override
    default C append(@NotNull ComponentLike componentLike) {
        return (C) Component.super.append(componentLike);
    }

    @NotNull
    @Override
    default C append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return (C) Component.super.append(componentBuilder);
    }

    @NotNull
    @Override
    default C append(@NotNull List<? extends ComponentLike> values) {
        return (C) Component.super.append(values);
    }

    @NotNull
    @Override
    default C append(@NotNull ComponentLike... componentLike) {
        return (C) Component.super.append(componentLike);
    }

    @NotNull
    @Override
    default C appendNewline() {
        return (C) Component.super.appendNewline();
    }

    @NotNull
    @Override
    default C appendSpace() {
        return (C) Component.super.appendSpace();
    }

    @NotNull
    @Override
    default C applyFallbackStyle(@NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        return (C) Component.super.applyFallbackStyle(styleBuilderApplicable);
    }

    @NotNull
    @Override
    default C applyFallbackStyle(@NotNull Style style) {
        return (C) Component.super.applyFallbackStyle(style);
    }

    @NotNull
    @Override
    default C mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> values) {
        return (C) Component.super.mergeStyle(component, values);
    }

    @NotNull
    @Override
    default C color(@Nullable TextColor textColor) {
        return (C) Component.super.color(textColor);
    }

    @NotNull
    @Override
    default C colorIfAbsent(@Nullable TextColor textColor) {
        return (C) Component.super.colorIfAbsent(textColor);
    }

    @NotNull
    @Override
    default C shadowColor(@Nullable ARGBLike aRGBLike) {
        return (C) Component.super.shadowColor(aRGBLike);
    }

    @NotNull
    @Override
    default C shadowColorIfAbsent(@Nullable ARGBLike aRGBLike) {
        return (C) Component.super.shadowColorIfAbsent(aRGBLike);
    }

    @NotNull
    @Override
    default C decorate(@NotNull TextDecoration textDecoration) {
        return (C) Component.super.decorate(textDecoration);
    }

    @NotNull
    @Override
    default C decoration(@NotNull TextDecoration textDecoration, boolean matches) {
        return (C) Component.super.decoration(textDecoration, matches);
    }

    @NotNull
    @Override
    default C decoration(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        return (C) Component.super.decoration(textDecoration, state);
    }

    @NotNull
    @Override
    default C decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        return (C) Component.super.decorationIfAbsent(textDecoration, state);
    }

    @NotNull
    @Override
    default C decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries) {
        return (C) Component.super.decorations(entries);
    }

    @NotNull
    @Override
    default C clickEvent(@Nullable ClickEvent clickEvent2) {
        return (C) Component.super.clickEvent(clickEvent2);
    }

    @NotNull
    @Override
    default C hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return (C) Component.super.hoverEvent(hoverEventSource);
    }

    @NotNull
    @Override
    default C insertion(@Nullable String text) {
        return (C) Component.super.insertion(text);
    }

    @NotNull
    @Override
    default C font(@Nullable Key key) {
        return (C) Component.super.font(key);
    }
}
