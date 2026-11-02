package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public final class LegacyFormat {

    private LegacyFormat() {}

    public static String trimLegacyFormat(String text, int value) {
        if (text.length() <= value) {
            return text;
        } else {
            return text.charAt(value - 1) == 167
                    ? text.substring(0, value - 1)
                    : text.substring(0, value);
        }
    }
}
