package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.adventure.util.MonkeyBars;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@NonExtendable
public interface Style
        extends Buildable<Style, Style.Builder>, Examinable, StyleGetter, StyleSetter<Style> {

    Key DEFAULT_FONT = Key.key("default");

    @NotNull
    static Style empty() {
        return StyleImpl.EMPTY;
    }

    @NotNull
    static Style.Builder style() {
        return new StyleImpl.BuilderImpl();
    }

    @NotNull
    static Style style(@NotNull Consumer<Style.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(style(), consumer);
    }

    @NotNull
    static Style style(@Nullable TextColor textColor) {
        return empty().color(textColor);
    }

    @NotNull
    static Style style(@NotNull TextDecoration textDecoration) {
        return style().decoration(textDecoration, true).build();
    }

    @NotNull
    static Style style(@Nullable TextColor textColor, TextDecoration @NotNull ... textDecoration) {
        Style.Builder builder;
        (builder = style()).color(textColor);
        builder.decorate(textDecoration);
        return builder.build();
    }

    @NotNull
    static Style style(@Nullable TextColor textColor, Set<TextDecoration> values) {
        Style.Builder builder;
        (builder = style()).color(textColor);
        if (!values.isEmpty()) {
            for (TextDecoration textDecoration : values) {
                builder.decoration(textDecoration, true);
            }
        }
        return builder.build();
    }

    @NotNull
    static Style style(
            @UnknownNullability StyleBuilderApplicable @NotNull ... styleBuilderApplicable) {
        int value;
        if ((value = styleBuilderApplicable.length) == 0) {
            return empty();
        }
        Style.Builder builder = style();
        for (int index = 0; index < value; index++) {
            StyleBuilderApplicable styleBuilderApplicable2;
            if ((styleBuilderApplicable2 = styleBuilderApplicable[index]) != null) {
                styleBuilderApplicable2.styleApply(builder);
            }
        }
        return builder.build();
    }

    @NotNull
    static Style style(@NotNull Iterable<? extends StyleBuilderApplicable> iterable) {
        Style.Builder builder = style();
        Iterator<? extends it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable>
                iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            ((StyleBuilderApplicable) iterator2.next()).styleApply(builder);
        }
        return builder.build();
    }

    @NotNull
    default Style edit(@NotNull Consumer<Style.Builder> consumer) {
        return this.edit(consumer, Style.Merge.Strategy.ALWAYS);
    }

    @NotNull
    default Style edit(
            @NotNull Consumer<Style.Builder> consumer, Style.Merge.@NotNull Strategy strategy) {
        return style(
                result -> {
                    if (strategy == Style.Merge.Strategy.ALWAYS) {
                        result.merge(this, strategy);
                    }
                    ((Consumer) consumer).accept(result);
                    if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
                        result.merge(this, strategy);
                    }
                });
    }

    @Nullable
    @Override
    Key font();

    @NotNull
    Style font(@Nullable Key key);

    @Nullable
    @Override
    TextColor color();

    @NotNull
    Style color(@Nullable TextColor textColor);

    @NotNull
    Style colorIfAbsent(@Nullable TextColor textColor);

    @Override
    default boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return StyleGetter.super.hasDecoration(textDecoration);
    }

    @Override
    TextDecoration.@NotNull State decoration(@NotNull TextDecoration textDecoration);

    @NotNull
    default Style decorate(@NotNull TextDecoration textDecoration) {
        return (Style) StyleSetter.super.decorate(textDecoration);
    }

    @NotNull
    default Style decoration(@NotNull TextDecoration textDecoration, boolean matches) {
        return (Style) StyleSetter.super.decoration(textDecoration, matches);
    }

    @NotNull
    Style decoration(@NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @NotNull
    Style decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

    @NotNull
    @Override
    @Unmodifiable
    default Map<TextDecoration, TextDecoration.State> decorations() {
        return StyleGetter.super.decorations();
    }

    @NotNull
    Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries);

    @Nullable
    @Override
    ClickEvent clickEvent();

    @NotNull
    Style clickEvent(@Nullable ClickEvent clickEvent2);

    @Nullable
    @Override
    HoverEvent<?> hoverEvent();

    @NotNull
    Style hoverEvent(@Nullable HoverEventSource<?> hoverEventSource);

    @Nullable
    @Override
    String insertion();

    @NotNull
    Style insertion(@Nullable String text);

    @NotNull
    default Style merge(@NotNull Style style) {
        return this.merge(style, Style.Merge.all());
    }

    @NotNull
    default Style merge(@NotNull Style style, Style.Merge.@NotNull Strategy strategy) {
        return this.merge(style, strategy, Style.Merge.all());
    }

    @NotNull
    default Style merge(@NotNull Style style, @NotNull Style.Merge merge2) {
        return this.merge(style, Collections.singleton(merge2));
    }

    @NotNull
    default Style merge(
            @NotNull Style style,
            Style.Merge.@NotNull Strategy strategy,
            @NotNull Style.Merge merge2) {
        return this.merge(style, strategy, Collections.singleton(merge2));
    }

    @NotNull
    default Style merge(@NotNull Style style, @NotNull Style.Merge... merge2) {
        return this.merge(style, Style.Merge.merges(merge2));
    }

    @NotNull
    default Style merge(
            @NotNull Style style,
            Style.Merge.@NotNull Strategy strategy,
            @NotNull Style.Merge... merge2) {
        return this.merge(style, strategy, Style.Merge.merges(merge2));
    }

    @NotNull
    default Style merge(@NotNull Style style, @NotNull Set<Style.Merge> values) {
        return this.merge(style, Style.Merge.Strategy.ALWAYS, values);
    }

    @NotNull
    Style merge(
            @NotNull Style style,
            Style.Merge.@NotNull Strategy strategy,
            @NotNull Set<Style.Merge> values);

    @NotNull
    Style unmerge(@NotNull Style style);

    boolean isEmpty();

    @NotNull
    Style.Builder toBuilder();

    interface Builder
            extends AbstractBuilder<Style>,
                    Buildable.Builder<Style>,
                    MutableStyleSetter<Style.Builder> {

        @Contract("_ -> this")
        @NotNull
        Style.Builder font(@Nullable Key key);

        @Contract("_ -> this")
        @NotNull
        Style.Builder color(@Nullable TextColor textColor);

        @Contract("_ -> this")
        @NotNull
        Style.Builder colorIfAbsent(@Nullable TextColor textColor);

        @Contract("_ -> this")
        @NotNull
        default Style.Builder decorate(@NotNull TextDecoration textDecoration) {
            return (Style.Builder) MutableStyleSetter.super.decorate(textDecoration);
        }

        @Contract("_ -> this")
        @NotNull
        default Style.Builder decorate(@NotNull TextDecoration... textDecoration) {
            return (Style.Builder) MutableStyleSetter.super.decorate(textDecoration);
        }

        @Contract("_, _ -> this")
        @NotNull
        default Style.Builder decoration(@NotNull TextDecoration textDecoration, boolean matches) {
            return (Style.Builder) MutableStyleSetter.super.decoration(textDecoration, matches);
        }

        @Contract("_ -> this")
        @NotNull
        default Style.Builder decorations(
                @NotNull Map<TextDecoration, TextDecoration.State> entries) {
            return (Style.Builder) MutableStyleSetter.super.decorations(entries);
        }

        @Contract("_, _ -> this")
        @NotNull
        Style.Builder decoration(
                @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

        @Contract("_, _ -> this")
        @NotNull
        Style.Builder decorationIfAbsent(
                @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state);

        @Contract("_ -> this")
        @NotNull
        Style.Builder clickEvent(@Nullable ClickEvent clickEvent2);

        @Contract("_ -> this")
        @NotNull
        Style.Builder hoverEvent(@Nullable HoverEventSource<?> hoverEventSource);

        @Contract("_ -> this")
        @NotNull
        Style.Builder insertion(@Nullable String text);

        @Contract("_ -> this")
        @NotNull
        default Style.Builder merge(@NotNull Style style) {
            return this.merge(style, Style.Merge.all());
        }

        @Contract("_, _ -> this")
        @NotNull
        default Style.Builder merge(@NotNull Style style, Style.Merge.@NotNull Strategy strategy) {
            return this.merge(style, strategy, Style.Merge.all());
        }

        @Contract("_, _ -> this")
        @NotNull
        default Style.Builder merge(@NotNull Style style, @NotNull Style.Merge... merge2) {
            return merge2.length == 0 ? this : this.merge(style, Style.Merge.merges(merge2));
        }

        @Contract("_, _, _ -> this")
        @NotNull
        default Style.Builder merge(
                @NotNull Style style,
                Style.Merge.@NotNull Strategy strategy,
                @NotNull Style.Merge... merge2) {
            return merge2.length == 0
                    ? this
                    : this.merge(style, strategy, Style.Merge.merges(merge2));
        }

        @Contract("_, _ -> this")
        @NotNull
        default Style.Builder merge(@NotNull Style style, @NotNull Set<Style.Merge> values) {
            return this.merge(style, Style.Merge.Strategy.ALWAYS, values);
        }

        @Contract("_, _, _ -> this")
        @NotNull
        Style.Builder merge(
                @NotNull Style style,
                Style.Merge.@NotNull Strategy strategy,
                @NotNull Set<Style.Merge> values);

        @Contract("_ -> this")
        @NotNull
        default Style.Builder apply(@NotNull StyleBuilderApplicable styleBuilderApplicable) {
            styleBuilderApplicable.styleApply(this);
            return this;
        }

        @NotNull
        Style build();
    }

    enum Merge {
        COLOR,
        SHADOW_COLOR,
        DECORATIONS,
        EVENTS,
        INSERTION,
        FONT;

        static final Set<Style.Merge> ALL = merges(values());

        static final Set<Style.Merge> COLOR_AND_DECORATIONS = merges(COLOR, DECORATIONS);

        @NotNull
        @Unmodifiable
        public static Set<Style.Merge> all() {
            return ALL;
        }

        @NotNull
        @Unmodifiable
        public static Set<Style.Merge> colorAndDecorations() {
            return COLOR_AND_DECORATIONS;
        }

        @NotNull
        @Unmodifiable
        public static Set<Style.Merge> merges(Style.Merge @NotNull ... merge) {
            return MonkeyBars.enumSet(Style.Merge.class, merge);
        }

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        @Unmodifiable
        public static Set<Style.Merge> of(Style.Merge @NotNull ... merge) {
            return MonkeyBars.enumSet(Style.Merge.class, merge);
        }

        static boolean hasAll(@NotNull Set<Style.Merge> values) {
            return values.size() == ALL.size();
        }

        public enum Strategy {
            ALWAYS,
            NEVER,
            IF_ABSENT_ON_TARGET
        }
    }
}
