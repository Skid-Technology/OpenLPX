package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class EmptyWaypointInfo implements WaypointInfo {

    public static final EmptyWaypointInfo EMPTY = new EmptyWaypointInfo();

    private EmptyWaypointInfo() {}

    @Internal
    public static EmptyWaypointInfo read(PacketWrapper<?> packetWrapper) {
        return EMPTY;
    }

    @Internal
    public static void write(PacketWrapper<?> packetWrapper, WaypointInfo waypointInfo) {}

    @Override
    public WaypointInfo.Type getType() {
        return WaypointInfo.Type.EMPTY;
    }
}
