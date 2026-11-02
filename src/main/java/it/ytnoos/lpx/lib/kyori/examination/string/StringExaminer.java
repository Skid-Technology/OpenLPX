package it.ytnoos.lpx.lib.kyori.examination.string;

import it.ytnoos.lpx.lib.kyori.examination.AbstractExaminer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StringExaminer extends AbstractExaminer<String> {

    private static final Function<String, String> DEFAULT_ESCAPER =
            result ->
                    result.replace("\"", "\\\"")
                            .replace("\\", "\\\\")
                            .replace("\b", "\\b")
                            .replace("\f", "\\f")
                            .replace("\n", "\\n")
                            .replace("\r", "\\r")
                            .replace("\t", "\\t");

    private static final Collector<CharSequence, ?, String> COMMA_CURLY =
            Collectors.joining(", ", "{", "}");

    private static final Collector<CharSequence, ?, String> COMMA_SQUARE =
            Collectors.joining(", ", "[", "]");

    private final Function<String, String> escaper;

    @NotNull
    public static StringExaminer simpleEscaping() {
        return StringExaminer.Instances.SIMPLE_ESCAPING;
    }

    public StringExaminer(@NotNull Function<String, String> function) {
        this.escaper = function;
    }

    @NotNull
    protected <E> String array(E @NotNull [] es, @NotNull Stream<String> stream) {
        return stream.collect(COMMA_SQUARE);
    }

    @NotNull
    protected <E> String collection(@NotNull Collection<E> values, @NotNull Stream<String> stream) {
        return stream.collect(COMMA_SQUARE);
    }

    @NotNull
    protected String examinable(
            @NotNull String text, @NotNull Stream<Entry<String, String>> stream) {
        return text
                + stream.<CharSequence>map(
                                result ->
                                        (String) result.getKey() + '=' + (String) result.getValue())
                        .collect(COMMA_CURLY);
    }

    @NotNull
    protected <K, V> String map(
            @NotNull Map<K, V> entries, @NotNull Stream<Entry<String, String>> stream) {
        return stream.<CharSequence>map(
                        result -> (String) result.getKey() + '=' + (String) result.getValue())
                .collect(COMMA_CURLY);
    }

    @NotNull
    protected String nil() {
        return "null";
    }

    @NotNull
    protected String scalar(@NotNull Object result) {
        return String.valueOf(result);
    }

    @NotNull
    public String examine(boolean matches) {
        return String.valueOf(matches);
    }

    @NotNull
    public String examine(byte value) {
        return String.valueOf(value);
    }

    @NotNull
    public String examine(char value) {
        return Strings.wrapIn(this.escaper.apply(String.valueOf(value)), '\'');
    }

    @NotNull
    public String examine(double value) {
        return Strings.withSuffix(String.valueOf(value), 'd');
    }

    @NotNull
    public String examine(float value) {
        return Strings.withSuffix(String.valueOf(value), 'f');
    }

    @NotNull
    public String examine(int value) {
        return String.valueOf(value);
    }

    @NotNull
    public String examine(long value) {
        return String.valueOf(value);
    }

    @NotNull
    public String examine(short value) {
        return String.valueOf(value);
    }

    @NotNull
    protected <T> String stream(@NotNull Stream<T> stream2) {
        return stream2.<CharSequence>map(this::examine).collect(COMMA_SQUARE);
    }

    @NotNull
    protected String stream(@NotNull DoubleStream doubleStream) {
        return doubleStream.<CharSequence>mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @NotNull
    protected String stream(@NotNull IntStream intStream) {
        return intStream.<CharSequence>mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @NotNull
    protected String stream(@NotNull LongStream longStream) {
        return longStream.<CharSequence>mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @NotNull
    public String examine(@Nullable String text) {
        return text == null ? this.nil() : Strings.wrapIn(this.escaper.apply(text), '"');
    }

    @NotNull
    protected String array(int value, IntFunction<String> intFunction) {
        StringBuilder stringBuilder;
        (stringBuilder = new StringBuilder()).append('[');
        for (int index = 0; index < value; index++) {
            stringBuilder.append((String) intFunction.apply(index));
            if (index + 1 < value) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static final class Instances {

        static final StringExaminer SIMPLE_ESCAPING =
                new StringExaminer(StringExaminer.DEFAULT_ESCAPER);
    }
}
