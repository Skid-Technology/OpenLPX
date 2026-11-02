package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.InvalidHandshakeException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.PreViaSupport;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

public class EventManager {

    private final Map<PacketListenerPriority, Set<PacketListenerCommon>> listenersMap =
            new ConcurrentHashMap<>();

    private volatile PacketListenerCommon[] listeners = new PacketListenerCommon[0];

    private volatile boolean hasPreViaListeners = false;

    private volatile boolean hasPreViaInternalListener = false;

    private final Consumer<PacketListenerCommon> onRegisterListener;

    public EventManager() {
        this.onRegisterListener = result -> {};
    }

    public EventManager(Consumer<PacketListenerCommon> consumer) {
        this.onRegisterListener = consumer;
    }

    public void callEvent(PacketEvent event) {
        this.callEvent(event, null);
    }

    public void callEvent(PacketEvent event, @Nullable Runnable runnable, boolean matches) {
        PacketListenerCommon[] packetListenerCommons = this.listeners;
        int value = this.listeners.length;
        for (int index = 0; index < value; index++) {
            PacketListenerCommon packetListenerCommon = packetListenerCommons[index];
            try {
                if (packetListenerCommon.isPreVia() == matches) {
                    event.call(packetListenerCommon);
                }
            } catch (Exception exception) {
                if (exception.getClass() != InvalidHandshakeException.class
                        && (exception.getCause() == null
                                || exception.getCause().getClass()
                                        != InvalidHandshakeException.class)) {
                    PacketEvents.getAPI()
                            .getLogManager()
                            .warn(
                                    "PacketEvents caught an unhandled exception while calling your listener.",
                                    exception);
                }
            }
            if (runnable != null) {
                runnable.run();
            }
        }
        if (event instanceof ProtocolPacketEvent
                && !((ProtocolPacketEvent) event).needsReEncode()) {
            ((ProtocolPacketEvent) event).setLastUsedWrapper(null);
        }
    }

    public void callEvent(PacketEvent event, @Nullable Runnable runnable) {
        this.callEvent(event, runnable, false);
    }

    public PacketListenerCommon registerListener(
            PacketListener packetListener, PacketListenerPriority packetListenerPriority) {
        PacketListenerAbstract packetListenerAbstract =
                packetListener.asAbstract(packetListenerPriority);
        return this.registerListener(packetListenerAbstract);
    }

    public PacketListenerCommon registerListener(PacketListenerCommon packetListenerCommon) {
        this.registerListenerNoRecalculation(packetListenerCommon);
        this.recalculateListeners();
        return packetListenerCommon;
    }

    public PacketListenerCommon[] registerListeners(PacketListenerCommon... packetListenerCommon) {
        int value = packetListenerCommon.length;
        for (int index = 0; index < value; index++) {
            PacketListenerCommon packetListenerCommon2 =
                    ((PacketListenerCommon[]) packetListenerCommon)[index];
            this.registerListenerNoRecalculation(packetListenerCommon2);
        }
        this.recalculateListeners();
        return packetListenerCommon;
    }

    public void unregisterListener(PacketListenerCommon packetListenerCommon) {
        if (this.unregisterListenerNoRecalculation(packetListenerCommon)) {
            this.recalculateListeners();
        }
    }

    public void unregisterListeners(PacketListenerCommon... packetListenerCommon) {
        boolean matches = false;
        int value = packetListenerCommon.length;
        for (int index = 0; index < value; index++) {
            PacketListenerCommon packetListenerCommon2 =
                    ((PacketListenerCommon[]) packetListenerCommon)[index];
            matches |= this.unregisterListenerNoRecalculation(packetListenerCommon2);
        }
        if (matches) {
            this.recalculateListeners();
        }
    }

    public void unregisterAllListeners() {
        this.listenersMap.clear();
        synchronized (this) {
            this.listeners = new PacketListenerCommon[0];
            this.hasPreViaListeners = false;
            this.hasPreViaInternalListener = false;
        }
    }

    private void recalculateListeners() {
        synchronized (this) {
            ArrayList values2 = new ArrayList();
            boolean matches = false;
            boolean matches2 = false;
            PacketListenerPriority[] packetListenerPrioritys;
            int value = (packetListenerPrioritys = PacketListenerPriority.values()).length;
            for (int index = 0; index < value; index++) {
                PacketListenerPriority packetListenerPriority = packetListenerPrioritys[index];
                Set<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerCommon>
                        values3;
                if ((values3 = this.listenersMap.get(packetListenerPriority)) != null) {
                    values2.addAll(values3);
                    Iterator<
                                    it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event
                                            .PacketListenerCommon>
                            iterator2 = values3.iterator();
                    while (iterator2.hasNext()) {
                        PacketListenerCommon packetListenerCommon;
                        if ((packetListenerCommon = (PacketListenerCommon) iterator2.next())
                                .isPreVia()) {
                            if (PreViaSupport.isPreViaInternalListener(packetListenerCommon)) {
                                matches2 = true;
                            } else {
                                matches = true;
                            }
                        }
                    }
                }
            }
            this.listeners =
                    ((PacketListenerCommon[]) values2.toArray(new PacketListenerCommon[0]));
            this.hasPreViaListeners = matches;
            this.hasPreViaInternalListener = matches2;
        }
    }

    public boolean hasPreViaListeners() {
        return this.hasPreViaListeners;
    }

    public boolean hasPreViaInternalListener() {
        return this.hasPreViaInternalListener;
    }

    private void registerListenerNoRecalculation(PacketListenerCommon packetListenerCommon) {
        this.onRegisterListener.accept(packetListenerCommon);
        this.listenersMap
                .computeIfAbsent(
                        packetListenerCommon.getPriority(), result -> new CopyOnWriteArraySet<>())
                .add(packetListenerCommon);
    }

    private boolean unregisterListenerNoRecalculation(PacketListenerCommon packetListenerCommon) {
        Set<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketListenerCommon> values;
        return (values = this.listenersMap.get(packetListenerCommon.getPriority())) != null
                && values.remove(packetListenerCommon);
    }
}
