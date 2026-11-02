package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

public class InjectedList<E> implements List<E> {

    private final List<E> originalList;

    private final Consumer<E> pushBackAction;

    public InjectedList(List<E> originalList, Consumer<E> consumer) {
        for (Object result : originalList) {
            consumer.accept(((E) result));
        }
        this.originalList = originalList;
        this.pushBackAction = consumer;
    }

    public List<E> originalList() {
        return this.originalList;
    }

    public Consumer<E> pushBackAction() {
        return this.pushBackAction;
    }

    @Override
    public synchronized boolean add(E e) {
        this.pushBackAction.accept((E) e);
        return this.originalList.add((E) e);
    }

    @Override
    public synchronized boolean addAll(@NotNull Collection<? extends E> values) {
        for (Object result : values) {
            this.pushBackAction.accept((E) result);
        }
        return this.originalList.addAll(values);
    }

    @Override
    public synchronized boolean addAll(int value, @NotNull Collection<? extends E> values) {
        for (Object result : values) {
            this.pushBackAction.accept((E) result);
        }
        return this.originalList.addAll(value, values);
    }

    @Override
    public synchronized void add(int value, E e) {
        this.pushBackAction.accept((E) e);
        this.originalList.add(value, (E) e);
    }

    @Override
    public synchronized int size() {
        return this.originalList.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.originalList.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object result) {
        return this.originalList.contains(result);
    }

    @NotNull
    @Override
    public synchronized Iterator<E> iterator() {
        return this.originalList.iterator();
    }

    @NotNull
    @Override
    public synchronized Object[] toArray() {
        return this.originalList.toArray();
    }

    @NotNull
    @Override
    public synchronized <T> T[] toArray(@NotNull T[] ts) {
        return (T[]) this.originalList.toArray(ts);
    }

    @Override
    public synchronized boolean remove(Object result) {
        return this.originalList.remove(result);
    }

    @Override
    public synchronized boolean containsAll(@NotNull Collection<?> values) {
        return this.originalList.containsAll(values);
    }

    @Override
    public synchronized boolean removeAll(@NotNull Collection<?> values) {
        return this.originalList.removeAll(values);
    }

    @Override
    public synchronized boolean retainAll(@NotNull Collection<?> values) {
        return this.originalList.retainAll(values);
    }

    @Override
    public synchronized void clear() {
        this.originalList.clear();
    }

    @Override
    public synchronized E get(int value) {
        return this.originalList.get(value);
    }

    @Override
    public synchronized E set(int value, E e) {
        return this.originalList.set(value, (E) e);
    }

    @Override
    public synchronized E remove(int value) {
        return this.originalList.remove(value);
    }

    @Override
    public synchronized int indexOf(Object result) {
        return this.originalList.indexOf(result);
    }

    @Override
    public synchronized int lastIndexOf(Object result) {
        return this.originalList.lastIndexOf(result);
    }

    @NotNull
    @Override
    public synchronized ListIterator<E> listIterator() {
        return this.originalList.listIterator();
    }

    @NotNull
    @Override
    public synchronized ListIterator<E> listIterator(int value) {
        return this.originalList.listIterator(value);
    }

    @NotNull
    @Override
    public synchronized List<E> subList(int value, int value2) {
        return this.originalList.subList(value, value2);
    }
}
