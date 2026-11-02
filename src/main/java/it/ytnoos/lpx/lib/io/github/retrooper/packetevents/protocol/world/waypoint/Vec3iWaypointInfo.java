package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Vec3iWaypointInfo implements WaypointInfo {

    private final Vector3i position;

    public Vec3iWaypointInfo(Vector3i vector3i) {
        this.position = vector3i;
    }

    @Internal
    public static Vec3iWaypointInfo read(PacketWrapper<?> packetWrapper) {
        return new Vec3iWaypointInfo(Vector3i.read(packetWrapper));
    }

    @Internal
    public static void write(PacketWrapper<?> packetWrapper, WaypointInfo waypointInfo) {
        Vector3i.write(packetWrapper, ((Vec3iWaypointInfo) waypointInfo).position);
    }

    @Override
    public WaypointInfo.Type getType() {
        return WaypointInfo.Type.VEC3I;
    }

    public Vector3i getPosition() {
        return this.position;
    }
}
