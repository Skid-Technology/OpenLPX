package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;

public class MathHelper {

    public static int d(float value) {
        return MathUtil.floor(value);
    }

    public static int floor(double value) {
        return MathUtil.floor(value);
    }

    public static long d(double value) {
        long value2 = (long) value;
        return value < value2 ? value2 - 1L : value2;
    }

    public static float e(float value) {
        return value >= 0.0F ? value : -value;
    }

    public static int a(int value) {
        return value >= 0 ? value : -value;
    }

    public static int f(float value) {
        int value2 = (int) value;
        return value > value2 ? value2 + 1 : value2;
    }

    public static int f(double value) {
        int value2 = (int) value;
        return value > value2 ? value2 + 1 : value2;
    }
}
