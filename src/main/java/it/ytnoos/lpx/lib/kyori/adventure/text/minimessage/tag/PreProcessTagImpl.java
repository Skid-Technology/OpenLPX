package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

final class PreProcessTagImpl extends AbstractTag implements PreProcess {

    private final String value;

    PreProcessTagImpl(String value) {
        this.value = value;
    }

    @NotNull
    @Override
    public String value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof PreProcessTagImpl)) {
            return false;
        }
        PreProcessTagImpl preProcessTagImpl = (PreProcessTagImpl) result;
        return Objects.equals(this.value, preProcessTagImpl.value);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
