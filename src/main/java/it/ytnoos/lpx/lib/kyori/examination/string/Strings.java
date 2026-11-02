package it.ytnoos.lpx.lib.kyori.examination.string;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

final class Strings {

    private Strings() {}

    @NotNull
    static String withSuffix(String text, char value) {
        return text + value;
    }

    @NotNull
    static String wrapIn(String text, char value) {
        return value + text + value;
    }

    static int maxLength(Stream<String> stream) {
        return stream.mapToInt(String::length).max().orElse(0);
    }

    @NotNull
    static String repeat(@NotNull String text, int value) {
        if (value == 0) {
            return "";
        }
        if (value == 1) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder(text.length() * value);
        for (int index = 0; index < value; index++) {
            stringBuilder.append(text);
        }
        return stringBuilder.toString();
    }

    @NotNull
    static String padEnd(@NotNull String text, int value, char value2) {
        return text.length() >= value ? text : String.format("%-" + value + "s", value2);
    }
}
