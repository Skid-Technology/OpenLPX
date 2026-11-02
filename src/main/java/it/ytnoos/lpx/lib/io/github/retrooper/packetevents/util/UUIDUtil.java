package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtil {

    private static final Pattern PATTERN =
            Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public static UUID fromStringWithoutDashes(String text) {
        return UUID.fromString(PATTERN.matcher(text).replaceAll("$1-$2-$3-$4-$5"));
    }

    public static UUID fromString(String text) {
        return UUID.fromString(text);
    }

    public static String toString(UUID uUID) {
        return uUID.toString();
    }

    public static String toStringWithoutDashes(UUID uUID) {
        return uUID.toString().replace("-", "");
    }
}
