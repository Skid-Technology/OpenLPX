package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import java.util.UUID;

public final class UniqueIdUtil {

    private UniqueIdUtil() {}

    public static UUID fromIntArray(int[] values) {
        if (values.length != 4) {
            throw new IllegalStateException(
                    "Invalid encoded uuid length: " + values.length + " != 4");
        } else {
            return new UUID(
                    (long) values[0] << 32 | values[1] & 4294967295L,
                    (long) values[2] << 32 | values[3] & 4294967295L);
        }
    }

    public static int[] toIntArray(UUID uUID) {
        return new int[] {
            (int) (uUID.getMostSignificantBits() >> 32),
            (int) uUID.getMostSignificantBits(),
            (int) (uUID.getLeastSignificantBits() >> 32),
            (int) uUID.getLeastSignificantBits()
        };
    }
}
