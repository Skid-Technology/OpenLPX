package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugEntityBlockIntersection {

    private final DebugEntityBlockIntersection.IntersectionType type;

    public DebugEntityBlockIntersection(
            DebugEntityBlockIntersection.IntersectionType intersectionType) {
        this.type = intersectionType;
    }

    public static DebugEntityBlockIntersection read(PacketWrapper<?> packetWrapper) {
        DebugEntityBlockIntersection.IntersectionType intersectionType =
                packetWrapper.readEnum(DebugEntityBlockIntersection.IntersectionType.values());
        return new DebugEntityBlockIntersection(intersectionType);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            DebugEntityBlockIntersection debugEntityBlockIntersection) {
        packetWrapper.writeEnum(debugEntityBlockIntersection.type);
    }

    public DebugEntityBlockIntersection.IntersectionType getType() {
        return this.type;
    }

    public enum IntersectionType {
        IN_BLOCK,
        IN_FLUID,
        IN_AIR
    }
}
