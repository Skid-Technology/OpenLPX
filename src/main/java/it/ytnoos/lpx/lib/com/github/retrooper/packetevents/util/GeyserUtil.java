package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class GeyserUtil {

    private static boolean CHECKED_FOR_GEYSER = false;

    private static boolean GEYSER_PRESENT = false;

    private static Class<?> GEYSER_CLASS;

    private static Class<?> GEYSER_API_CLASS;

    private static Method GEYSER_API_METHOD;

    private static Method CONNECTION_BY_UUID_METHOD;

    public static boolean isGeyserPlayer(UUID uUID) {
        if (!CHECKED_FOR_GEYSER) {
            try {
                GEYSER_CLASS =
                        PacketEvents.getAPI()
                                .getPlugin()
                                .getClass()
                                .getClassLoader()
                                .loadClass("org.geysermc.api.Geyser");
                GEYSER_PRESENT = true;
            } catch (ClassNotFoundException classNotFoundException) {
                GEYSER_PRESENT = false;
            }
            CHECKED_FOR_GEYSER = true;
        }
        if (GEYSER_PRESENT) {
            if (GEYSER_API_CLASS == null) {
                try {
                    GEYSER_API_CLASS =
                            PacketEvents.getAPI()
                                    .getPlugin()
                                    .getClass()
                                    .getClassLoader()
                                    .loadClass("org.geysermc.api.GeyserApiBase");
                } catch (ClassNotFoundException classNotFoundException2) {
                    classNotFoundException2.printStackTrace();
                }
            }
            if (GEYSER_API_METHOD == null) {
                GEYSER_API_METHOD = Reflection.getMethodExact(GEYSER_CLASS, "api", null);
            }
            if (CONNECTION_BY_UUID_METHOD == null) {
                CONNECTION_BY_UUID_METHOD =
                        Reflection.getMethod(GEYSER_API_CLASS, "connectionByUuid", 0);
            }
            Object result = null;
            try {
                result = GEYSER_API_METHOD.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
            Object result2 = null;
            try {
                if (result != null) {
                    result2 = CONNECTION_BY_UUID_METHOD.invoke(result, uUID);
                }
            } catch (IllegalAccessException | InvocationTargetException exception2) {
                exception2.printStackTrace();
            }
            return result2 != null;
        } else {
            return false;
        }
    }
}
