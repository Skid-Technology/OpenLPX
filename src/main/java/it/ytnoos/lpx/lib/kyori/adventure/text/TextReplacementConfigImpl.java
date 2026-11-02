package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

final class TextReplacementConfigImpl implements TextReplacementConfig {

    private final Pattern matchPattern;

    private final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike>
            replacement;

    private final TextReplacementConfig.Condition continuer;

    private final boolean replaceInsideHoverEvents;

    TextReplacementConfigImpl(TextReplacementConfigImpl.Builder builder) {
        this.matchPattern = builder.matchPattern;
        this.replacement = builder.replacement;
        this.continuer = builder.continuer;
        this.replaceInsideHoverEvents = builder.replaceInsideHoverEvents;
    }

    @NotNull
    @Override
    public Pattern matchPattern() {
        return this.matchPattern;
    }

    TextReplacementRenderer.State createState() {
        return new TextReplacementRenderer.State(
                this.matchPattern, this.replacement, this.continuer, this.replaceInsideHoverEvents);
    }

    public TextReplacementConfig.@NotNull Builder toBuilder() {
        return new TextReplacementConfigImpl.Builder(this);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("matchPattern", this.matchPattern),
                ExaminableProperty.of("replacement", this.replacement),
                ExaminableProperty.of("continuer", this.continuer));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class Builder implements TextReplacementConfig.Builder {

        @Nullable Pattern matchPattern;

        @Nullable BiFunction<MatchResult, TextComponent.Builder, ComponentLike> replacement;

        TextReplacementConfig.Condition continuer =
                (result, result2, result3) -> PatternReplacementResult.REPLACE;

        boolean replaceInsideHoverEvents = true;

        Builder() {}

        Builder(TextReplacementConfigImpl textReplacementConfigImpl) {
            this.matchPattern = textReplacementConfigImpl.matchPattern;
            this.replacement = textReplacementConfigImpl.replacement;
            this.continuer = textReplacementConfigImpl.continuer;
        }

        @NotNull
        public TextReplacementConfigImpl.Builder match(@NotNull Pattern pattern) {
            this.matchPattern = Objects.requireNonNull(pattern, "pattern");
            return this;
        }

        @NotNull
        public TextReplacementConfigImpl.Builder condition(
                TextReplacementConfig.@NotNull Condition condition2) {
            this.continuer = Objects.requireNonNull(condition2, "continuation");
            return this;
        }

        @NotNull
        public TextReplacementConfigImpl.Builder replacement(
                @NotNull
                        BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike>
                                biFunction) {
            this.replacement = Objects.requireNonNull(biFunction, "replacement");
            return this;
        }

        @Override
        public TextReplacementConfig.@NotNull Builder replaceInsideHoverEvents(
                boolean replaceInsideHoverEvents) {
            this.replaceInsideHoverEvents = replaceInsideHoverEvents;
            return this;
        }

        @NotNull
        public TextReplacementConfig build() {
            if (this.matchPattern == null) {
                throw new IllegalStateException("A pattern must be provided to match against");
            } else if (this.replacement == null) {
                throw new IllegalStateException("A replacement action must be provided");
            } else {
                return new TextReplacementConfigImpl(this);
            }
        }
    }
}
