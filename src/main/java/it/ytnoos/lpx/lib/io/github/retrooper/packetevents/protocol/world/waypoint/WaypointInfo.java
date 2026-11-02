package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.waypoint;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface WaypointInfo {

    WaypointInfo.Type getType();

    enum Type {
        EMPTY(EmptyWaypointInfo::read, EmptyWaypointInfo::write),
        VEC3I(Vec3iWaypointInfo::read, Vec3iWaypointInfo::write),
        CHUNK(ChunkWaypointInfo::read, ChunkWaypointInfo::write),
        AZIMUTH(AzimuthWaypointInfo::read, AzimuthWaypointInfo::write);

        private final PacketWrapper.Reader<WaypointInfo> reader;

        private final PacketWrapper.Writer<WaypointInfo> writer;

        Type(
                PacketWrapper.Reader<WaypointInfo> reader2,
                PacketWrapper.Writer<WaypointInfo> writer2) {
            this.reader = reader2;
            this.writer = writer2;
        }

        @Internal
        public WaypointInfo read(PacketWrapper<?> packetWrapper) {
            return this.reader.apply(packetWrapper);
        }

        @Internal
        public void write(PacketWrapper<?> packetWrapper, WaypointInfo waypointInfo) {
            this.writer.accept(packetWrapper, waypointInfo);
        }
    }
}
