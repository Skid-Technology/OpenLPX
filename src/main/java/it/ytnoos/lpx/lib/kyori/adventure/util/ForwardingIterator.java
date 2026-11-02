package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;

public final class ForwardingIterator<T> implements Iterable<T> {

    private final Supplier<Iterator<T>> iterator;

    private final Supplier<Spliterator<T>> spliterator;

    public ForwardingIterator(
            @NotNull Supplier<Iterator<T>> supplier, @NotNull Supplier<Spliterator<T>> supplier2) {
        this.iterator = Objects.requireNonNull(supplier, "iterator");
        this.spliterator = Objects.requireNonNull(supplier2, "spliterator");
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.iterator.get();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return this.spliterator.get();
    }
}
