package it.ytnoos.lpx.lib.kyori.adventure.text.format;

import it.ytnoos.lpx.lib.kyori.adventure.util.HSVLike;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Renderer(text = "asHexString()")
final class TextColorImpl implements TextColor {

    private final int value;

    TextColorImpl(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof TextColorImpl)) {
            return false;
        }
        TextColorImpl textColorImpl = (TextColorImpl) result;
        return this.value == textColorImpl.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.asHexString();
    }

    static float distance(@NotNull HSVLike hSVLike, @NotNull HSVLike hSVLike2) {
        float value =
                3.0F
                        * Math.min(
                                Math.abs(hSVLike.h() - hSVLike2.h()),
                                1.0F - Math.abs(hSVLike.h() - hSVLike2.h()));
        float value2 = hSVLike.s() - hSVLike2.s();
        float value3 = hSVLike.v() - hSVLike2.v();
        return value * value + value2 * value2 + value3 * value3;
    }
}
