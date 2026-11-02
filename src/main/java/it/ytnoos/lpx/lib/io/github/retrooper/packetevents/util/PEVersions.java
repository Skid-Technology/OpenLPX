package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import java.time.Instant;

public final class PEVersions {

    public static final String RAW = "2.14.1-LPX+6aecc331c-SNAPSHOT";

    public static final Instant BUILD_TIMESTAMP = Instant.ofEpochMilli(1790252908330L);

    public static final PEVersion CURRENT = new PEVersion(2, 14, 1, "6aecc331c");

    public static final PEVersion UNKNOWN = new PEVersion(0, 0, 0);

    private PEVersions() {
        throw new IllegalStateException();
    }
}
