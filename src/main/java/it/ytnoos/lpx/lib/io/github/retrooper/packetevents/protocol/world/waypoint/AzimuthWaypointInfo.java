package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class AzimuthWaypointInfo implements WaypointInfo {

    private final float angle;

    public AzimuthWaypointInfo(float angle) {
        this.angle = angle;
    }

    @Internal
    public static AzimuthWaypointInfo read(PacketWrapper<?> packetWrapper) {
        return new AzimuthWaypointInfo(packetWrapper.readFloat());
    }

    @Internal
    public static void write(PacketWrapper<?> packetWrapper, WaypointInfo waypointInfo) {
        packetWrapper.writeFloat(((AzimuthWaypointInfo) waypointInfo).angle);
    }

    @Override
    public WaypointInfo.Type getType() {
        return WaypointInfo.Type.AZIMUTH;
    }

    public float getAngle() {
        return this.angle;
    }
}
