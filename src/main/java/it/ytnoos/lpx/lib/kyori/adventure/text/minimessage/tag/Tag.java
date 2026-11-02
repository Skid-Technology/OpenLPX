package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleBuilderApplicable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;

public interface Tag {

    @NotNull
    static PreProcess preProcessParsed(@NotNull String text) {
        return new PreProcessTagImpl(Objects.requireNonNull(text, "content"));
    }

    @NotNull
    static Tag inserting(@NotNull Component component) {
        return new InsertingImpl(
                true, Objects.requireNonNull(component, "content must not be null"));
    }

    @NotNull
    static Tag inserting(@NotNull ComponentLike componentLike) {
        return inserting(Objects.requireNonNull(componentLike, "value").asComponent());
    }

    @NotNull
    static Tag selfClosingInserting(@NotNull Component component) {
        return new InsertingImpl(
                false, Objects.requireNonNull(component, "content must not be null"));
    }

    @NotNull
    static Tag selfClosingInserting(@NotNull ComponentLike componentLike) {
        return selfClosingInserting(Objects.requireNonNull(componentLike, "value").asComponent());
    }

    @NotNull
    static Tag styling(Consumer<Style.Builder> consumer) {
        return new CallbackStylingTagImpl(consumer);
    }

    @NotNull
    static Tag styling(@NotNull StyleBuilderApplicable... styleBuilderApplicable) {
        Objects.requireNonNull(styleBuilderApplicable, "actions");
        int value = 0;
        for (int value2 = styleBuilderApplicable.length; value < value2; value++) {
            if (styleBuilderApplicable[value] == null) {
                throw new NullPointerException("actions[" + value + "]");
            }
        }
        return new StylingTagImpl(
                Arrays.copyOf(styleBuilderApplicable, styleBuilderApplicable.length));
    }

    @NonExtendable
    interface Argument {

        @NotNull
        String value();

        @NotNull
        default String lowerValue() {
            return this.value().toLowerCase(Locale.ROOT);
        }

        default boolean isTrue() {
            return "true".equals(this.value()) || "on".equals(this.value());
        }

        default boolean isFalse() {
            return "false".equals(this.value()) || "off".equals(this.value());
        }

        @NotNull
        default OptionalInt asInt() {
            try {
                return OptionalInt.of(Integer.parseInt(this.value()));
            } catch (NumberFormatException numberFormatException) {
                return OptionalInt.empty();
            }
        }

        @NotNull
        default OptionalDouble asDouble() {
            try {
                return OptionalDouble.of(Double.parseDouble(this.value()));
            } catch (NumberFormatException numberFormatException) {
                return OptionalDouble.empty();
            }
        }
    }
}
