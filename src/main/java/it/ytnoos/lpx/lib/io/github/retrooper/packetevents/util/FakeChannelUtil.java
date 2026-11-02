package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public class FakeChannelUtil {

    public static boolean isFakeChannel(Object result) {
        return result.getClass().getSimpleName().equals("FakeChannel")
                || result.getClass().getSimpleName().equals("SpoofedChannel")
                || result.getClass().getSimpleName().equals("EmbeddedChannel");
    }
}
