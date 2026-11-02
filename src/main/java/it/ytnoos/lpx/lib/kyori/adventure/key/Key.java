package it.ytnoos.lpx.lib.kyori.adventure.key;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

public interface Key extends Comparable<Key>, Examinable, Namespaced, Keyed {

    String MINECRAFT_NAMESPACE = "minecraft";

    char DEFAULT_SEPARATOR = ':';

    @NotNull
    static Key key(@KeyPattern @NotNull String text) {
        return key(text, ':');
    }

    @NotNull
    static Key key(@NotNull String text, char value) {
        Objects.requireNonNull(text, "string");
        int value2;
        String text2 =
                (value2 = text.indexOf(value)) >= 1 ? text.substring(0, value2) : "minecraft";
        String text3 = value2 >= 0 ? text.substring(value2 + 1) : text;
        return key(text2, text3);
    }

    @NotNull
    static Key key(@NotNull Namespaced namespaced, @KeyPattern.Value @NotNull String text) {
        return key(Objects.requireNonNull(namespaced, "namespaced").namespace(), text);
    }

    @NotNull
    static Key key(
            @KeyPattern.Namespace @NotNull String text, @KeyPattern.Value @NotNull String text2) {
        return new KeyImpl(text, text2);
    }

    @NotNull
    static Comparator<? super Key> comparator() {
        return KeyImpl.COMPARATOR;
    }

    static boolean parseable(@Nullable String text) {
        if (text == null) {
            return false;
        }
        int value;
        String text2 = (value = text.indexOf(58)) >= 1 ? text.substring(0, value) : "minecraft";
        String text3 = value >= 0 ? text.substring(value + 1) : text;
        return parseableNamespace(text2) && parseableValue(text3);
    }

    static boolean parseableNamespace(@NotNull String text) {
        return !checkNamespace(text).isPresent();
    }

    @NotNull
    static OptionalInt checkNamespace(@NotNull String text) {
        Objects.requireNonNull(text, "namespace");
        int value = 0;
        for (int size = text.length(); value < size; value++) {
            if (!allowedInNamespace(text.charAt(value))) {
                return OptionalInt.of(value);
            }
        }
        return OptionalInt.empty();
    }

    static boolean parseableValue(@NotNull String text) {
        return !checkValue(text).isPresent();
    }

    @NotNull
    static OptionalInt checkValue(@NotNull String text) {
        Objects.requireNonNull(text, "value");
        int value = 0;
        for (int size = text.length(); value < size; value++) {
            if (!allowedInValue(text.charAt(value))) {
                return OptionalInt.of(value);
            }
        }
        return OptionalInt.empty();
    }

    static boolean allowedInNamespace(char value) {
        return KeyImpl.allowedInNamespace(value);
    }

    static boolean allowedInValue(char value) {
        return KeyImpl.allowedInValue(value);
    }

    @KeyPattern.Namespace
    @NotNull
    @Override
    String namespace();

    @KeyPattern.Value
    @NotNull
    String value();

    @NotNull
    String asString();

    @NotNull
    default String asMinimalString() {
        return this.namespace().equals("minecraft") ? this.value() : this.asString();
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("namespace", this.namespace()),
                ExaminableProperty.of("value", this.value()));
    }

    default int compareTo(@NotNull Key key) {
        return comparator().compare(this, key);
    }

    @NotNull
    @Override
    default Key key() {
        return this;
    }
}
