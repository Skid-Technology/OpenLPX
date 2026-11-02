package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.adventure.util.IntFunction2;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public interface TextReplacementConfig
        extends Buildable<TextReplacementConfig, TextReplacementConfig.Builder>, Examinable {

    @NotNull
    static TextReplacementConfig.Builder builder() {
        return new TextReplacementConfigImpl.Builder();
    }

    @NotNull
    Pattern matchPattern();

    interface Builder
            extends AbstractBuilder<TextReplacementConfig>,
                    Buildable.Builder<TextReplacementConfig> {

        @Contract("_ -> this")
        default TextReplacementConfig.Builder matchLiteral(String text) {
            return this.match(Pattern.compile(text, 16));
        }

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder match(@NotNull @RegExp String text) {
            return this.match(Pattern.compile(text));
        }

        @Contract("_ -> this")
        @NotNull
        TextReplacementConfig.Builder match(@NotNull Pattern pattern);

        @NotNull
        default TextReplacementConfig.Builder once() {
            return this.times(1);
        }

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder times(int value) {
            return this.condition(
                    (result, result2) ->
                            result2 < value
                                    ? PatternReplacementResult.REPLACE
                                    : PatternReplacementResult.STOP);
        }

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder condition(
                @NotNull IntFunction2<PatternReplacementResult> intFunction2) {
            return this.condition(
                    (result, result2, result3) ->
                            (PatternReplacementResult)
                                    ((IntFunction2) intFunction2).apply(result2, result3));
        }

        @Contract("_ -> this")
        @NotNull
        TextReplacementConfig.Builder condition(
                @NotNull TextReplacementConfig.Condition condition2);

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder replacement(@NotNull String text) {
            Objects.requireNonNull(text, "replacement");
            return this.replacement(result -> result.content(text));
        }

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder replacement(@Nullable ComponentLike componentLike) {
            Component component = ComponentLike.unbox(componentLike);
            return this.replacement((result, result2) -> component);
        }

        @Contract("_ -> this")
        @NotNull
        default TextReplacementConfig.Builder replacement(
                @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
            Objects.requireNonNull(function, "replacement");
            return this.replacement(
                    (result, result2) -> (ComponentLike) ((Function) function).apply(result2));
        }

        @Contract("_ -> this")
        @NotNull
        TextReplacementConfig.Builder replacement(
                @NotNull
                        BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike>
                                biFunction);

        @Contract("_ -> this")
        @NotNull
        TextReplacementConfig.Builder replaceInsideHoverEvents(boolean matches);
    }

    @FunctionalInterface
    interface Condition {

        @NotNull
        PatternReplacementResult shouldReplace(
                @NotNull MatchResult matchResult, int value, int value2);
    }
}
