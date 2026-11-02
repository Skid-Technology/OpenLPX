package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

final class ComponentIterator implements Iterator<Component> {

    private Component component;

    private final ComponentIteratorType type;

    private final Set<ComponentIteratorFlag> flags;

    private final Deque<Component> deque;

    ComponentIterator(
            @NotNull Component component2,
            @NotNull ComponentIteratorType componentIteratorType,
            @NotNull Set<ComponentIteratorFlag> flags) {
        this.component = component2;
        this.type = componentIteratorType;
        this.flags = flags;
        this.deque = new ArrayDeque<>();
    }

    @Override
    public boolean hasNext() {
        return this.component != null || !this.deque.isEmpty();
    }

    public Component next() {
        if (this.component != null) {
            Component component2 = this.component;
            this.component = null;
            this.type.populate(component2, this.deque, this.flags);
            return component2;
        }
        if (this.deque.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.component = this.deque.poll();
        return this.next();
    }
}
