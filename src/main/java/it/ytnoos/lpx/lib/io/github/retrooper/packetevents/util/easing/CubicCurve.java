package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.easing;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CubicCurve {

    private final float a;

    private final float b;

    private final float c;

    public CubicCurve(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public float calcSample(float value) {
        return ((this.a * value + this.b) * value + this.c) * value;
    }

    public float calcSampleGradient(float value) {
        return (3.0F * this.a * value + 2.0F * this.b) * value + this.c;
    }

    public float getA() {
        return this.a;
    }

    public float getB() {
        return this.b;
    }

    public float getC() {
        return this.c;
    }
}
