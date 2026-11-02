package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Either;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public final class TrackedWaypoint {

    private final Either<UUID, String> identifier;

    private final WaypointIcon icon;

    private final WaypointInfo info;

    public TrackedWaypoint(
            Either<UUID, String> either, WaypointIcon waypointIcon, WaypointInfo waypointInfo) {
        this.identifier = either;
        this.icon = waypointIcon;
        this.info = waypointInfo;
    }

    public static TrackedWaypoint read(PacketWrapper<?> packetWrapper) {
        Either either =
                packetWrapper.readEither(PacketWrapper::readUUID, PacketWrapper::readString);
        WaypointIcon waypointIcon = WaypointIcon.read(packetWrapper);
        WaypointInfo waypointInfo =
                packetWrapper.readEnum(WaypointInfo.Type.class).read(packetWrapper);
        return new TrackedWaypoint(either, waypointIcon, waypointInfo);
    }

    public static void write(PacketWrapper<?> packetWrapper, TrackedWaypoint trackedWaypoint) {
        packetWrapper.writeEither(
                trackedWaypoint.identifier, PacketWrapper::writeUUID, PacketWrapper::writeString);
        WaypointIcon.write(packetWrapper, trackedWaypoint.icon);
        packetWrapper.writeEnum(trackedWaypoint.info.getType());
        trackedWaypoint.info.getType().write(packetWrapper, trackedWaypoint.info);
    }

    public Either<UUID, String> getIdentifier() {
        return this.identifier;
    }

    public WaypointIcon getIcon() {
        return this.icon;
    }

    public WaypointInfo getInfo() {
        return this.info;
    }
}
