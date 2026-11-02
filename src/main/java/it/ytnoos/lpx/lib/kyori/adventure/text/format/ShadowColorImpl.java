package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

final class ShadowColorImpl implements ShadowColor, Examinable {

    static final int NONE_VALUE = 0;

    static final ShadowColorImpl NONE = new ShadowColorImpl(0);

    private final int value;

    ShadowColorImpl(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (!(result instanceof ShadowColorImpl)) {
            return false;
        }
        ShadowColorImpl shadowColorImpl = (ShadowColorImpl) result;
        return this.value == shadowColorImpl.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
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
