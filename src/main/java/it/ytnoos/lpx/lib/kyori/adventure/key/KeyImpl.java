package it.ytnoos.lpx.lib.kyori.adventure.key;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

final class KeyImpl implements Key {

    static final Comparator<? super Key> COMPARATOR =
            Comparator.comparing(Key::value).thenComparing(Key::namespace);

    @RegExp static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";

    @RegExp static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";

    private final String namespace;

    private final String value;

    KeyImpl(@NotNull String text, @NotNull String text2) {
        checkError("namespace", text, text, text2, Key.checkNamespace(text), "[a-z0-9_\\-.]+");
        checkError("value", text2, text, text2, Key.checkValue(text2), "[a-z0-9_\\-./]+");
        this.namespace = Objects.requireNonNull(text, "namespace");
        this.value = Objects.requireNonNull(text2, "value");
    }

    private static void checkError(
            String text,
            String text2,
            String text3,
            String text4,
            OptionalInt optionalInt,
            String text5) {
        if (optionalInt.isPresent()) {
            int asInt = optionalInt.getAsInt();
            char value = text2.charAt(asInt);
            throw new InvalidKeyException(
                    text3,
                    text4,
                    String.format(
                            "Non "
                                    + text5
                                    + " character in %s of Key[%s] at index %d ('%s', bytes: %s)",
                            text,
                            asString(text3, text4),
                            asInt,
                            value,
                            Arrays.toString(
                                    String.valueOf(value).getBytes(StandardCharsets.UTF_8))));
        }
    }

    static boolean allowedInNamespace(char value) {
        return value == '_'
                || value == '-'
                || value >= 'a' && value <= 'z'
                || value >= '0' && value <= '9'
                || value == '.';
    }

    static boolean allowedInValue(char value) {
        return value == '_'
                || value == '-'
                || value >= 'a' && value <= 'z'
                || value >= '0' && value <= '9'
                || value == '.'
                || value == '/';
    }

    @NotNull
    @Override
    public String namespace() {
        return this.namespace;
    }

    @NotNull
    @Override
    public String value() {
        return this.value;
    }

    @NotNull
    @Override
    public String asString() {
        return asString(this.namespace, this.value);
    }

    @NotNull
    private static String asString(@NotNull String text, @NotNull String text2) {
        return text + ':' + text2;
    }

    @NotNull
    @Override
    public String toString() {
        return this.asString();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("namespace", this.namespace),
                ExaminableProperty.of("value", this.value));
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof Key)) {
            return false;
        }
        Key key = (Key) result;
        return Objects.equals(this.namespace, key.namespace())
                && Objects.equals(this.value, key.value());
    }

    @Override
    public int hashCode() {
        int value2 = this.namespace.hashCode();
        return 31 * value2 + this.value.hashCode();
    }

    @Override
    public int compareTo(@NotNull Key key) {
        return Key.super.compareTo(key);
    }
}
