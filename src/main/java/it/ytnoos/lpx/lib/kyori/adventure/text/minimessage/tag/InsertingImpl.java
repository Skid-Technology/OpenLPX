package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

final class InsertingImpl extends AbstractTag implements Inserting {

    private final boolean allowsChildren;

    private final Component value;

    InsertingImpl(boolean allowsChildren, Component component) {
        this.allowsChildren = allowsChildren;
        this.value = component;
    }

    @Override
    public boolean allowsChildren() {
        return this.allowsChildren;
    }

    @NotNull
    @Override
    public Component value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.allowsChildren, this.value);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof InsertingImpl)) {
            return false;
        }
        InsertingImpl insertingImpl = (InsertingImpl) result;
        return this.allowsChildren == insertingImpl.allowsChildren
                && Objects.equals(this.value, insertingImpl.value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("allowsChildren", this.allowsChildren),
                ExaminableProperty.of("value", this.value));
    }
}
