package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@Renderer(text = "\"\\\"\" + this.value + \"\\\"\"", hasChildren = "false")
final class StringBinaryTagImpl extends AbstractBinaryTag implements StringBinaryTag {

    private final String value;

    StringBinaryTagImpl(String value) {
        this.value = value;
    }

    @NotNull
    @Override
    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            StringBinaryTagImpl stringBinaryTagImpl = (StringBinaryTagImpl) result;
            return this.value.equals(stringBinaryTagImpl.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
}
