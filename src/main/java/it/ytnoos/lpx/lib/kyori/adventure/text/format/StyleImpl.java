package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEventSource;
import it.ytnoos.lpx.lib.kyori.adventure.util.ARGBLike;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

final class StyleImpl implements Style {

    static final StyleImpl EMPTY =
            new StyleImpl(null, null, null, DecorationMap.EMPTY, null, null, null);

    @Nullable final Key font;

    @Nullable final TextColor color;

    @Nullable final ShadowColor shadowColor;

    @NotNull final DecorationMap decorations;

    @Nullable final ClickEvent clickEvent;

    @Nullable final HoverEvent<?> hoverEvent;

    @Nullable final String insertion;

    StyleImpl(
            @Nullable Key key,
            @Nullable TextColor textColor,
            @Nullable ShadowColor shadowColor2,
            @NotNull Map<TextDecoration, TextDecoration.State> entries,
            @Nullable ClickEvent clickEvent2,
            @Nullable HoverEvent<?> hoverEvent2,
            @Nullable String insertion) {
        this.font = key;
        this.color = textColor;
        this.shadowColor = shadowColor2;
        this.decorations = DecorationMap.fromMap(entries);
        this.clickEvent = clickEvent2;
        this.hoverEvent = hoverEvent2;
        this.insertion = insertion;
    }

    @Nullable
    @Override
    public Key font() {
        return this.font;
    }

    @NotNull
    @Override
    public Style font(@Nullable Key key) {
        return Objects.equals(this.font, key)
                ? this
                : new StyleImpl(
                        key,
                        this.color,
                        this.shadowColor,
                        this.decorations,
                        this.clickEvent,
                        this.hoverEvent,
                        this.insertion);
    }

    @Nullable
    @Override
    public TextColor color() {
        return this.color;
    }

    @NotNull
    @Override
    public Style color(@Nullable TextColor textColor) {
        return Objects.equals(this.color, textColor)
                ? this
                : new StyleImpl(
                        this.font,
                        textColor,
                        this.shadowColor,
                        this.decorations,
                        this.clickEvent,
                        this.hoverEvent,
                        this.insertion);
    }

    @NotNull
    @Override
    public Style colorIfAbsent(@Nullable TextColor textColor) {
        return this.color == null ? this.color(textColor) : this;
    }

    @Nullable
    @Override
    public ShadowColor shadowColor() {
        return this.shadowColor;
    }

    @NotNull
    public Style shadowColor(@Nullable ARGBLike aRGBLike) {
        return Objects.equals(this.shadowColor, aRGBLike)
                ? this
                : new StyleImpl(
                        this.font,
                        this.color,
                        aRGBLike == null ? null : ShadowColor.shadowColor(aRGBLike),
                        this.decorations,
                        this.clickEvent,
                        this.hoverEvent,
                        this.insertion);
    }

    @NotNull
    public Style shadowColorIfAbsent(@Nullable ARGBLike aRGBLike) {
        return this.shadowColor == null ? this.shadowColor(aRGBLike) : this;
    }

    @Override
    public TextDecoration.@NotNull State decoration(@NotNull TextDecoration textDecoration) {
        TextDecoration.State state;
        if ((state = this.decorations.get(textDecoration)) != null) {
            return state;
        } else {
            throw new IllegalArgumentException(
                    String.format("unknown decoration '%s'", textDecoration));
        }
    }

    @NotNull
    @Override
    public Style decoration(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        Objects.requireNonNull(state, "state");
        return this.decoration(textDecoration) == state
                ? this
                : new StyleImpl(
                        this.font,
                        this.color,
                        this.shadowColor,
                        this.decorations.with(textDecoration, state),
                        this.clickEvent,
                        this.hoverEvent,
                        this.insertion);
    }

    @NotNull
    @Override
    public Style decorationIfAbsent(
            @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
        Objects.requireNonNull(state, "state");
        TextDecoration.State state2;
        if ((state2 = this.decorations.get(textDecoration)) == TextDecoration.State.NOT_SET) {
            return new StyleImpl(
                    this.font,
                    this.color,
                    this.shadowColor,
                    this.decorations.with(textDecoration, state),
                    this.clickEvent,
                    this.hoverEvent,
                    this.insertion);
        } else if (state2 != null) {
            return this;
        } else {
            throw new IllegalArgumentException(
                    String.format("unknown decoration '%s'", textDecoration));
        }
    }

    @NotNull
    @Override
    public Map<TextDecoration, TextDecoration.State> decorations() {
        return this.decorations;
    }

    @NotNull
    @Override
    public Style decorations(@NotNull Map<TextDecoration, TextDecoration.State> entries) {
        return new StyleImpl(
                this.font,
                this.color,
                this.shadowColor,
                DecorationMap.merge(entries, this.decorations),
                this.clickEvent,
                this.hoverEvent,
                this.insertion);
    }

    @Nullable
    @Override
    public ClickEvent clickEvent() {
        return this.clickEvent;
    }

    @NotNull
    @Override
    public Style clickEvent(@Nullable ClickEvent clickEvent2) {
        return new StyleImpl(
                this.font,
                this.color,
                this.shadowColor,
                this.decorations,
                clickEvent2,
                this.hoverEvent,
                this.insertion);
    }

    @Nullable
    @Override
    public HoverEvent<?> hoverEvent() {
        return this.hoverEvent;
    }

    @NotNull
    @Override
    public Style hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return new StyleImpl(
                this.font,
                this.color,
                this.shadowColor,
                this.decorations,
                this.clickEvent,
                HoverEventSource.unbox(hoverEventSource),
                this.insertion);
    }

    @Nullable
    @Override
    public String insertion() {
        return this.insertion;
    }

    @NotNull
    @Override
    public Style insertion(@Nullable String text) {
        return Objects.equals(this.insertion, text)
                ? this
                : new StyleImpl(
                        this.font,
                        this.color,
                        this.shadowColor,
                        this.decorations,
                        this.clickEvent,
                        this.hoverEvent,
                        text);
    }

    @NotNull
    @Override
    public Style merge(
            @NotNull Style style,
            Style.Merge.@NotNull Strategy strategy,
            @NotNull Set<Style.Merge> values) {
        if (nothingToMerge(style, strategy, values)) {
            return this;
        }
        if (this.isEmpty() && Style.Merge.hasAll(values)) {
            return style;
        }
        Style.Builder builder;
        (builder = this.toBuilder()).merge(style, strategy, values);
        return builder.build();
    }

    @NotNull
    @Override
    public Style unmerge(@NotNull Style style) {
        if (this.isEmpty()) {
            return this;
        }
        StyleImpl.BuilderImpl builderImpl = new StyleImpl.BuilderImpl(this);
        if (Objects.equals(this.font(), style.font())) {
            builderImpl.font(null);
        }
        if (Objects.equals(this.color(), style.color())) {
            builderImpl.color(null);
        }
        if (Objects.equals(this.shadowColor(), style.shadowColor())) {
            builderImpl.shadowColor(null);
        }
        int value = 0;
        for (int value2 = DecorationMap.DECORATIONS.length; value < value2; value++) {
            TextDecoration textDecoration = DecorationMap.DECORATIONS[value];
            if (this.decoration(textDecoration) == style.decoration(textDecoration)) {
                builderImpl.decoration(textDecoration, TextDecoration.State.NOT_SET);
            }
        }
        if (Objects.equals(this.clickEvent(), style.clickEvent())) {
            builderImpl.clickEvent(null);
        }
        if (Objects.equals(this.hoverEvent(), style.hoverEvent())) {
            builderImpl.hoverEvent(null);
        }
        if (Objects.equals(this.insertion(), style.insertion())) {
            builderImpl.insertion(null);
        }
        return builderImpl.build();
    }

    static boolean nothingToMerge(
            @NotNull Style style,
            Style.Merge.@NotNull Strategy strategy,
            @NotNull Set<Style.Merge> values) {
        if (strategy == Style.Merge.Strategy.NEVER) {
            return true;
        } else {
            return style.isEmpty() ? true : values.isEmpty();
        }
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY;
    }

    @NotNull
    @Override
    public Style.Builder toBuilder() {
        return new StyleImpl.BuilderImpl(this);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                this.decorations.examinableProperties(),
                Stream.of(
                        ExaminableProperty.of("color", this.color),
                        ExaminableProperty.of("shadowColor", this.shadowColor),
                        ExaminableProperty.of("clickEvent", this.clickEvent),
                        ExaminableProperty.of("hoverEvent", this.hoverEvent),
                        ExaminableProperty.of("insertion", this.insertion),
                        ExaminableProperty.of("font", this.font)));
    }

    @NotNull
    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StyleImpl)) {
            return false;
        }
        StyleImpl styleImpl = (StyleImpl) result;
        return Objects.equals(this.color, styleImpl.color)
                && this.decorations.equals(styleImpl.decorations)
                && Objects.equals(this.shadowColor, styleImpl.shadowColor)
                && Objects.equals(this.clickEvent, styleImpl.clickEvent)
                && Objects.equals(this.hoverEvent, styleImpl.hoverEvent)
                && Objects.equals(this.insertion, styleImpl.insertion)
                && Objects.equals(this.font, styleImpl.font);
    }

    @Override
    public int hashCode() {
        int value = Objects.hashCode(this.color);
        value = 31 * value + Objects.hashCode(this.shadowColor);
        value = 31 * value + this.decorations.hashCode();
        value = 31 * value + Objects.hashCode(this.clickEvent);
        value = 31 * value + Objects.hashCode(this.hoverEvent);
        value = 31 * value + Objects.hashCode(this.insertion);
        return 31 * value + Objects.hashCode(this.font);
    }

    static final class BuilderImpl implements Style.Builder {

        @Nullable Key font;

        @Nullable TextColor color;

        @Nullable ShadowColor shadowColor;

        DecorationMap decorations;

        @Nullable ClickEvent clickEvent;

        @Nullable HoverEvent<?> hoverEvent;

        @Nullable String insertion;

        BuilderImpl() {
            this.decorations = DecorationMap.EMPTY;
        }

        BuilderImpl(@NotNull StyleImpl styleImpl) {
            this.color = styleImpl.color;
            this.shadowColor = styleImpl.shadowColor;
            this.decorations = styleImpl.decorations;
            this.clickEvent = styleImpl.clickEvent;
            this.hoverEvent = styleImpl.hoverEvent;
            this.insertion = styleImpl.insertion;
            this.font = styleImpl.font;
        }

        @NotNull
        @Override
        public Style.Builder font(@Nullable Key key) {
            this.font = key;
            return this;
        }

        @NotNull
        @Override
        public Style.Builder color(@Nullable TextColor textColor) {
            this.color = textColor;
            return this;
        }

        @NotNull
        @Override
        public Style.Builder colorIfAbsent(@Nullable TextColor textColor) {
            if (this.color == null) {
                this.color = textColor;
            }
            return this;
        }

        @NotNull
        public Style.Builder shadowColor(@Nullable ARGBLike aRGBLike) {
            this.shadowColor = aRGBLike == null ? null : ShadowColor.shadowColor(aRGBLike);
            return this;
        }

        @NotNull
        public Style.Builder shadowColorIfAbsent(@Nullable ARGBLike aRGBLike) {
            if (this.shadowColor == null) {
                this.shadowColor = aRGBLike == null ? null : ShadowColor.shadowColor(aRGBLike);
            }
            return this;
        }

        @NotNull
        @Override
        public Style.Builder decoration(
                @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
            Objects.requireNonNull(state, "state");
            Objects.requireNonNull(textDecoration, "decoration");
            this.decorations = this.decorations.with(textDecoration, state);
            return this;
        }

        @NotNull
        @Override
        public Style.Builder decorationIfAbsent(
                @NotNull TextDecoration textDecoration, TextDecoration.@NotNull State state) {
            Objects.requireNonNull(state, "state");
            TextDecoration.State state2;
            if ((state2 = this.decorations.get(textDecoration)) == TextDecoration.State.NOT_SET) {
                this.decorations = this.decorations.with(textDecoration, state);
            }
            if (state2 != null) {
                return this;
            } else {
                throw new IllegalArgumentException(
                        String.format("unknown decoration '%s'", textDecoration));
            }
        }

        @NotNull
        @Override
        public Style.Builder clickEvent(@Nullable ClickEvent clickEvent2) {
            this.clickEvent = clickEvent2;
            return this;
        }

        @NotNull
        @Override
        public Style.Builder hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
            this.hoverEvent = HoverEventSource.unbox(hoverEventSource);
            return this;
        }

        @NotNull
        @Override
        public Style.Builder insertion(@Nullable String insertion) {
            this.insertion = insertion;
            return this;
        }

        @NotNull
        @Override
        public Style.Builder merge(
                @NotNull Style style,
                Style.Merge.@NotNull Strategy strategy,
                @NotNull Set<Style.Merge> values) {
            Objects.requireNonNull(style, "style");
            Objects.requireNonNull(strategy, "strategy");
            Objects.requireNonNull(values, "merges");
            if (StyleImpl.nothingToMerge(style, strategy, values)) {
                return this;
            }
            TextColor textColor;
            if (values.contains(Style.Merge.COLOR)
                    && (textColor = style.color()) != null
                    && (strategy == Style.Merge.Strategy.ALWAYS
                            || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                    && this.color == null)) {
                this.color(textColor);
            }
            ShadowColor shadowColor2;
            if (values.contains(Style.Merge.SHADOW_COLOR)
                    && (shadowColor2 = style.shadowColor()) != null
                    && (strategy == Style.Merge.Strategy.ALWAYS
                            || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                    && this.shadowColor == null)) {
                this.shadowColor(shadowColor2);
            }
            if (values.contains(Style.Merge.DECORATIONS)) {
                int value = 0;
                for (int value2 = DecorationMap.DECORATIONS.length; value < value2; value++) {
                    TextDecoration textDecoration = DecorationMap.DECORATIONS[value];
                    TextDecoration.State state;
                    if ((state = style.decoration(textDecoration))
                            != TextDecoration.State.NOT_SET) {
                        if (strategy == Style.Merge.Strategy.ALWAYS) {
                            this.decoration(textDecoration, state);
                        } else if (strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET) {
                            this.decorationIfAbsent(textDecoration, state);
                        }
                    }
                }
            }
            if (values.contains(Style.Merge.EVENTS)) {
                ClickEvent clickEvent2;
                if ((clickEvent2 = style.clickEvent()) != null
                        && (strategy == Style.Merge.Strategy.ALWAYS
                                || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                        && this.clickEvent == null)) {
                    this.clickEvent(clickEvent2);
                }
                HoverEvent hoverEvent2;
                if ((hoverEvent2 = style.hoverEvent()) != null
                        && (strategy == Style.Merge.Strategy.ALWAYS
                                || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                        && this.hoverEvent == null)) {
                    this.hoverEvent(hoverEvent2);
                }
            }
            String text;
            if (values.contains(Style.Merge.INSERTION)
                    && (text = style.insertion()) != null
                    && (strategy == Style.Merge.Strategy.ALWAYS
                            || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                    && this.insertion == null)) {
                this.insertion(text);
            }
            Key key;
            if (values.contains(Style.Merge.FONT)
                    && (key = style.font()) != null
                    && (strategy == Style.Merge.Strategy.ALWAYS
                            || strategy == Style.Merge.Strategy.IF_ABSENT_ON_TARGET
                                    && this.font == null)) {
                this.font(key);
            }
            return this;
        }

        @NotNull
        public StyleImpl build() {
            return this.isEmpty()
                    ? StyleImpl.EMPTY
                    : new StyleImpl(
                            this.font,
                            this.color,
                            this.shadowColor,
                            this.decorations,
                            this.clickEvent,
                            this.hoverEvent,
                            this.insertion);
        }

        private boolean isEmpty() {
            return this.color == null
                    && this.shadowColor == null
                    && this.decorations == DecorationMap.EMPTY
                    && this.clickEvent == null
                    && this.hoverEvent == null
                    && this.insertion == null
                    && this.font == null;
        }
    }
}
