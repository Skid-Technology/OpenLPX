package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class LinearPositionPath implements PositionPath {

    private final Vector3d endPosition;

    public LinearPositionPath(Vector3d vector3d) {
        this.endPosition = vector3d;
    }

    public static LinearPositionPath read(PacketWrapper<?> packetWrapper) {
        return new LinearPositionPath(Vector3d.read(packetWrapper));
    }

    public static void write(
            PacketWrapper<?> packetWrapper, LinearPositionPath linearPositionPath) {
        Vector3d.write(packetWrapper, linearPositionPath.endPosition);
    }

    @Override
    public PositionPathType<?> getType() {
        return PositionPathTypes.LINEAR;
    }

    @Override
    public Vector3d getEndPosition() {
        return this.endPosition;
    }
}
