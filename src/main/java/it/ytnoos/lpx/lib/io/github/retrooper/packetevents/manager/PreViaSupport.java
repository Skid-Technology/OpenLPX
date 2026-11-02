package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerCommon;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class PreViaSupport {

    private PreViaSupport() {}

    public static boolean hasPreViaListeners() {
        return PacketEvents.getAPI().getEventManager().hasPreViaListeners();
    }

    public static boolean hasPreViaInternalListener() {
        return PacketEvents.getAPI().getEventManager().hasPreViaInternalListener();
    }

    public static void ensurePreViaInternalListenerRegistered() {
        if (!hasPreViaInternalListener()) {
            PacketEvents.getAPI().getEventManager().registerListener(new PreViaInternalListener());
        }
    }

    public static boolean isPreViaInternalListener(PacketListenerCommon packetListenerCommon) {
        return packetListenerCommon instanceof PreViaInternalListener;
    }

    public static boolean isExternalPreViaListener(PacketListenerCommon packetListenerCommon) {
        return packetListenerCommon.isPreVia() && !isPreViaInternalListener(packetListenerCommon);
    }
}
