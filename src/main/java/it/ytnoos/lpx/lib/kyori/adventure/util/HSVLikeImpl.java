package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

final class HSVLikeImpl implements HSVLike {

    private final float h;

    private final float s;

    private final float v;

    HSVLikeImpl(float h, float s, float v) {
        requireInsideRange(h, "h");
        requireInsideRange(s, "s");
        requireInsideRange(v, "v");
        this.h = h;
        this.s = s;
        this.v = v;
    }

    @Override
    public float h() {
        return this.h;
    }

    @Override
    public float s() {
        return this.s;
    }

    @Override
    public float v() {
        return this.v;
    }

    private static void requireInsideRange(float value, String text)
            throws IllegalArgumentException {
        if (value < 0.0F || 1.0F < value) {
            throw new IllegalArgumentException(
                    text + " (" + value + ") is not inside the required range: [0,1]");
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        HSVLikeImpl hSVLikeImpl;
        return !(result instanceof HSVLikeImpl)
                ? false
                : ShadyPines.equals((hSVLikeImpl = (HSVLikeImpl) result).h, this.h)
                        && ShadyPines.equals(hSVLikeImpl.s, this.s)
                        && ShadyPines.equals(hSVLikeImpl.v, this.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.h, this.s, this.v);
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }
}
