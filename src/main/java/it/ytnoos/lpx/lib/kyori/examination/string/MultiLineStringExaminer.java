package it.ytnoos.lpx.lib.kyori.examination.string;

import it.ytnoos.lpx.lib.kyori.examination.AbstractExaminer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MultiLineStringExaminer extends AbstractExaminer<Stream<String>> {

    private final StringExaminer examiner;

    @NotNull
    public static MultiLineStringExaminer simpleEscaping() {
        return MultiLineStringExaminer.Instances.SIMPLE_ESCAPING;
    }

    public MultiLineStringExaminer(@NotNull StringExaminer stringExaminer) {
        this.examiner = stringExaminer;
    }

    @NotNull
    protected <E> Stream<String> array(E @NotNull [] es, @NotNull Stream<Stream<String>> stream) {
        return this.arrayLike(stream);
    }

    @NotNull
    protected <E> Stream<String> collection(
            @NotNull Collection<E> values, @NotNull Stream<Stream<String>> stream) {
        return this.arrayLike(stream);
    }

    @NotNull
    protected Stream<String> examinable(
            @NotNull String text, @NotNull Stream<Entry<String, Stream<String>>> stream) {
        return enclose(
                indent(
                        flatten(
                                ",",
                                stream.map(
                                        result ->
                                                association(
                                                        this.examine((String) result.getKey()),
                                                        " = ",
                                                        (Stream<String>) result.getValue())))),
                text + "{",
                "}");
    }

    @NotNull
    protected <K, V> Stream<String> map(
            @NotNull Map<K, V> entries,
            @NotNull Stream<Entry<Stream<String>, Stream<String>>> stream) {
        return enclose(
                indent(
                        flatten(
                                ",",
                                stream.map(
                                        result ->
                                                association(
                                                        (Stream<String>) result.getKey(),
                                                        " = ",
                                                        (Stream<String>) result.getValue())))),
                "{",
                "}");
    }

    @NotNull
    protected Stream<String> nil() {
        return Stream.of(this.examiner.nil());
    }

    @NotNull
    protected Stream<String> scalar(@NotNull Object result) {
        return Stream.of(this.examiner.scalar(result));
    }

    @NotNull
    public Stream<String> examine(boolean matches) {
        return Stream.of(this.examiner.examine(matches));
    }

    @NotNull
    public Stream<String> examine(byte value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(char value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(double value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(float value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(int value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(long value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    public Stream<String> examine(short value) {
        return Stream.of(this.examiner.examine(value));
    }

    @NotNull
    protected Stream<String> array(int value, IntFunction<Stream<String>> intFunction) {
        return this.arrayLike(
                value == 0 ? Stream.empty() : IntStream.range(0, value).mapToObj(intFunction));
    }

    @NotNull
    protected <T> Stream<String> stream(@NotNull Stream<T> stream2) {
        return this.arrayLike(stream2.map(this::examine));
    }

    @NotNull
    protected Stream<String> stream(@NotNull DoubleStream doubleStream) {
        return this.arrayLike(doubleStream.mapToObj(this::examine));
    }

    @NotNull
    protected Stream<String> stream(@NotNull IntStream intStream) {
        return this.arrayLike(intStream.mapToObj(this::examine));
    }

    @NotNull
    protected Stream<String> stream(@NotNull LongStream longStream) {
        return this.arrayLike(longStream.mapToObj(this::examine));
    }

    @NotNull
    public Stream<String> examine(@Nullable String text) {
        return Stream.of(this.examiner.examine(text));
    }

    private Stream<String> arrayLike(Stream<Stream<String>> stream) {
        return enclose(indent(flatten(",", stream)), "[", "]");
    }

    private static Stream<String> enclose(Stream<String> stream, String text, String text2) {
        return enclose(stream.collect(Collectors.toList()), text, text2);
    }

    private static Stream<String> enclose(List<String> values, String text, String text2) {
        return values.isEmpty()
                ? Stream.of(text + text2)
                : Stream.of(Stream.of(text), indent(values.stream()), Stream.of(text2))
                        .reduce(Stream.empty(), Stream::concat);
    }

    private static Stream<String> flatten(String text, Stream<Stream<String>> stream2) {
        ArrayList values = new ArrayList();
        stream2.forEachOrdered(
                result -> {
                    if (!((List) values).isEmpty()) {
                        int value = ((List) values).size() - 1;
                        ((List) values).set(value, (String) ((List) values).get(value) + text);
                    }
                    result.forEachOrdered(values::add);
                });
        return values.stream();
    }

    private static Stream<String> association(
            Stream<String> stream, String text, Stream<String> stream2) {
        return association(
                stream.collect(Collectors.toList()), text, stream2.collect(Collectors.toList()));
    }

    private static Stream<String> association(
            List<String> values, String text, List<String> values2) {
        int size2 = values.size();
        int size3 = values2.size();
        int value = Math.max(size2, size3);
        int value2 = Strings.maxLength(values.stream());
        String text2 = size2 < 2 ? "" : Strings.repeat(" ", value2);
        String text3 = size2 < 2 ? "" : Strings.repeat(" ", text.length());
        ArrayList<String> values3 = new ArrayList<>(value);
        for (int index = 0; index < value; index++) {
            String text4 =
                    index < size2
                            ? Strings.padEnd((String) values.get(index), value2, (char) (32))
                            : text2;
            String text5 = index == 0 ? text : text3;
            String text6 = index < size3 ? (String) values2.get(index) : "";
            values3.add(text4 + text5 + text6);
        }
        return values3.stream();
    }

    private static Stream<String> indent(Stream<String> stream) {
        return stream.map(result -> "  " + result);
    }

    private static final class Instances {

        static final MultiLineStringExaminer SIMPLE_ESCAPING =
                new MultiLineStringExaminer(StringExaminer.simpleEscaping());
    }
}
