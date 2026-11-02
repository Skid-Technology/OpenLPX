package it.ytnoos.lpx.utils;

public class TimeUtils {

    private TimeUtils() {}

    public static long calculateTime(long value) {
        return current() - value;
    }

    public static long current() {
        return System.currentTimeMillis();
    }
}
