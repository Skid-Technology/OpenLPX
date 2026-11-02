package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public final class MathUtil {

    public static final float EPSILON = 1.0E-5F;

    public static final float F_PI = (float) Math.PI;

    private MathUtil() {}

    public static float square(float value) {
        return value * value;
    }

    public static float cube(float value) {
        return value * value * value;
    }

    public static float lerp(float value, float value2, float value3) {
        return value * (value3 - value2) + value2;
    }

    public static double lerp(double value, double value2, double value3) {
        return value * (value3 - value2) + value2;
    }

    public static int lerp(float value, int value2, int value3) {
        return floor(value * (value3 - value2)) + value2;
    }

    public static int clamp(int value, int value2, int value3) {
        return value < value2 ? value2 : Math.min(value, value3);
    }

    public static double clamp(double value, double value2, double value3) {
        return value < value2 ? value2 : Math.min(value, value3);
    }

    public static float clamp(float value, float value2, float value3) {
        return value < value2 ? value2 : Math.min(value, value3);
    }

    public static int floor(double value) {
        int value2 = (int) value;
        return value < value2 ? value2 - 1 : value2;
    }

    public static int floor(float value) {
        int value2 = (int) value;
        return value < value2 ? value2 - 1 : value2;
    }

    public static long ceilLong(double value) {
        long value2 = (long) value;
        return value > value2 ? value2 + 1L : value2;
    }

    public static double absMax(double value, double value2) {
        return Math.max(Math.abs(value), Math.abs(value2));
    }
}
