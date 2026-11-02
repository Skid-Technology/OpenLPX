package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public class StringUtil {

    public static String maximizeLength(String text, int value) {
        return text.length() > value ? text.substring(0, value) : text;
    }
}
