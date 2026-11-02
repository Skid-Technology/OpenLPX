package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server;

import java.util.Locale;

public enum SystemOS {
    WINDOWS,
    MACOS,
    LINUX,
    OTHER;

    private static SystemOS CACHE;

    public static SystemOS getOSNoCache() {
        String text = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        SystemOS[] systemOSs;
        int value = (systemOSs = values()).length;
        for (int index = 0; index < value; index++) {
            SystemOS systemOS = systemOSs[index];
            if (text.contains(systemOS.name().toLowerCase(Locale.ROOT))) {
                return systemOS;
            }
        }
        return OTHER;
    }

    public static SystemOS getOS() {
        if (CACHE == null) {
            CACHE = getOSNoCache();
        }
        return CACHE;
    }
}
