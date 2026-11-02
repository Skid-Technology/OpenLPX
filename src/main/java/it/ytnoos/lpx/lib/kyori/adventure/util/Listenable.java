package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public abstract class Listenable<L> {

    private final List<L> listeners = new CopyOnWriteArrayList<>();

    protected final void forEachListener(@NotNull Consumer<L> consumer) {
        for (Object result : this.listeners) {
            consumer.accept(((L) result));
        }
    }

    protected final void addListener0(@NotNull L l) {
        this.listeners.add((L) l);
    }

    protected final void removeListener0(@NotNull L l) {
        this.listeners.remove(l);
    }
}
