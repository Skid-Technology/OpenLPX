package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util.viaversion;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ViaVersionUtil {

    private static ViaState available = ViaState.UNKNOWN;

    private static ViaVersionAccessor viaVersionAccessor;

    private ViaVersionUtil() {}

    private static void load() {
        if (viaVersionAccessor == null) {
            ClassLoader classLoader = PacketEvents.getAPI().getPlugin().getClass().getClassLoader();
            try {
                classLoader.loadClass("com.viaversion.viaversion.api.Via");
                viaVersionAccessor = new ViaVersionAccessorImpl();
            } catch (Exception exception) {
                try {
                    classLoader.loadClass("us.myles.ViaVersion.api.Via");
                    viaVersionAccessor = new ViaVersionAccessorImplLegacy();
                } catch (ClassNotFoundException classNotFoundException) {
                    viaVersionAccessor = null;
                }
            }
        }
    }

    public static void checkIfViaIsPresent() {
        available =
                Bukkit.getPluginManager().isPluginEnabled("ViaVersion")
                        ? ViaState.ENABLED
                        : ViaState.DISABLED;
    }

    public static boolean isAvailable() {
        return available == ViaState.UNKNOWN
                ? getViaVersionAccessor() != null
                : available == ViaState.ENABLED;
    }

    public static ViaVersionAccessor getViaVersionAccessor() {
        load();
        return viaVersionAccessor;
    }

    public static int getProtocolVersion(User user) {
        return getViaVersionAccessor().getProtocolVersion(user);
    }

    public static int getProtocolVersion(Player player) {
        return getViaVersionAccessor().getProtocolVersion(player);
    }

    public static Class<?> getUserConnectionClass() {
        return getViaVersionAccessor().getUserConnectionClass();
    }

    public static Class<?> getBukkitDecodeHandlerClass() {
        return getViaVersionAccessor().getBukkitDecodeHandlerClass();
    }

    public static Class<?> getBukkitEncodeHandlerClass() {
        return getViaVersionAccessor().getBukkitEncodeHandlerClass();
    }
}
