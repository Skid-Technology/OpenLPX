package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugVillageSections {

    public static final DebugVillageSections INSTANCE = new DebugVillageSections();

    private DebugVillageSections() {}

    public static DebugVillageSections read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DebugVillageSections debugVillageSections) {}
}
