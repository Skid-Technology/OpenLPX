package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

final class TranslationArgumentImpl implements TranslationArgument {

    private static final Component TRUE = Component.text("true");

    private static final Component FALSE = Component.text("false");

    private final Object value;

    TranslationArgumentImpl(Object value) {
        this.value = value;
    }

    @NotNull
    @Override
    public Object value() {
        return this.value;
    }

    @NotNull
    @Override
    public Component asComponent() {
        if (this.value instanceof Component) {
            return (Component) this.value;
        } else if (this.value instanceof Boolean) {
            return (Boolean) this.value ? TRUE : FALSE;
        } else {
            return Component.text(String.valueOf(this.value));
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            TranslationArgumentImpl translationArgumentImpl = (TranslationArgumentImpl) result;
            return Objects.equals(this.value, translationArgumentImpl.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
