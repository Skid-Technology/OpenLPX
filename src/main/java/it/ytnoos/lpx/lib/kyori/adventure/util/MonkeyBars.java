package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public final class MonkeyBars {

    private MonkeyBars() {}

    @SafeVarargs
    @NotNull
    public static <E extends Enum<E>> Set<E> enumSet(Class<E> classValue, E @NotNull ... e) {
        EnumSet values;
        Collections.addAll(values = EnumSet.noneOf(classValue), (E[]) e);
        return Collections.unmodifiableSet(values);
    }

    @NotNull
    public static <T> List<T> addOne(@NotNull List<T> values, T t) {
        if (values.isEmpty()) {
            return Collections.singletonList((T) t);
        }
        ArrayList<T> values2;
        (values2 = new ArrayList(values.size() + 1)).addAll(values);
        values2.add(t);
        return Collections.unmodifiableList(values2);
    }

    @SafeVarargs
    @NotNull
    public static <I, O> List<O> nonEmptyArrayToList(
            @NotNull Function<I, O> function, @NotNull I i, @NotNull I... i2) {
        ArrayList<O> values;
        (values = new ArrayList(i2.length + 1)).add(function.apply(i));
        int value = i2.length;
        for (int index = 0; index < value; index++) {
            I i3 = i2[index];
            values.add(
                    Objects.requireNonNull(
                            function.apply(Objects.requireNonNull(i3, "source[?]")),
                            "mapper(source[?])"));
        }
        return Collections.unmodifiableList(values);
    }

    @NotNull
    public static <I, O> List<O> toUnmodifiableList(
            @NotNull Function<I, O> function, @NotNull Iterable<? extends I> iterable) {
        ArrayList<O> values =
                iterable instanceof Collection
                        ? new ArrayList(((Collection) iterable).size())
                        : new ArrayList();
        for (I i : iterable) {
            values.add(
                    Objects.requireNonNull(
                            function.apply(Objects.requireNonNull(i, "source[?]")),
                            "mapper(source[?])"));
        }
        return Collections.unmodifiableList(values);
    }
}
