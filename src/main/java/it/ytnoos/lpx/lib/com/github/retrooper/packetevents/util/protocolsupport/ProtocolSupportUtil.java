package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.protocolsupport;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.net.SocketAddress;

@NullMarked
@Deprecated
public final class ProtocolSupportUtil {

    private static ProtocolSupportState available = ProtocolSupportState.UNKNOWN;

    @Nullable private static Method getProtocolVersionByAddress;

    @Nullable private static Method getProtocolVersionByPlayer;

    @Nullable private static Method getId;

    private ProtocolSupportUtil() {}

    public static boolean isAvailable() {
        if (available != ProtocolSupportState.UNKNOWN) {
            return available == ProtocolSupportState.ENABLED;
        }
        try {
            ClassLoader classLoader;
            Class<?> classValue;
            getProtocolVersionByAddress =
                    (classValue =
                                    (classLoader =
                                                    PacketEvents.getAPI()
                                                            .getPlugin()
                                                            .getClass()
                                                            .getClassLoader())
                                            .loadClass("protocolsupport.api.ProtocolSupportAPI"))
                            .getMethod("getProtocolVersion", SocketAddress.class);
            getProtocolVersionByPlayer = classValue.getMethod("getProtocolVersion", Player.class);
            getId = classLoader.loadClass("protocolsupport.api.ProtocolVersion").getMethod("getId");
            available = ProtocolSupportState.ENABLED;
            return true;
        } catch (Exception exception) {
            available = ProtocolSupportState.DISABLED;
            return false;
        }
    }

    @Deprecated
    @Internal
    public static void checkIfProtocolSupportIsPresent() {
        isAvailable();
    }

    public static int getProtocolVersion(SocketAddress socketAddress) {
        if (getId != null && getProtocolVersionByAddress != null) {
            try {
                return (Integer)
                        getId.invoke(getProtocolVersionByAddress.invoke(null, socketAddress));
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new IllegalStateException(
                        "Error while trying to access ProtocolSupport",
                        reflectiveOperationException);
            }
        } else {
            throw new IllegalStateException("ProtocolSupport is not loaded");
        }
    }

    public static int getProtocolVersion(Player player) {
        if (getId != null && getProtocolVersionByPlayer != null) {
            try {
                return (Integer) getId.invoke(getProtocolVersionByPlayer.invoke(null, player));
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new IllegalStateException(
                        "Error while trying to access ProtocolSupport",
                        reflectiveOperationException);
            }
        } else {
            throw new IllegalStateException("ProtocolSupport is not loaded");
        }
    }
}
