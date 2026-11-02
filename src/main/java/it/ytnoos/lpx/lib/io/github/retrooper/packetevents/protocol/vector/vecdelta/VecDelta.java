package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.PositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.NullMarked;

@NullMarked
@NonExtendable
public interface VecDelta {

    double TRUNCATION_STEPS = 4096.0;

    static long encode(double value) {
        return Math.round(value * 4096.0);
    }

    static double decode(long value) {
        return value / 4096.0;
    }

    static Vector3d decode(Vector3d vector3d, long value, long value2, long value3) {
        return new Vector3d(
                value == 0L ? vector3d.x : decode(encode(vector3d.x) + value),
                value2 == 0L ? vector3d.y : decode(encode(vector3d.y) + value2),
                value3 == 0L ? vector3d.z : decode(encode(vector3d.z) + value3));
    }

    static VecDelta read(PacketWrapper<?> packetWrapper, int value) {
        return value <= 0
                ? LinearVecDelta.read(packetWrapper)
                : SteppedVecDelta.read(packetWrapper, value);
    }

    static void write(PacketWrapper<?> packetWrapper, VecDelta vecDelta) {
        if (vecDelta instanceof LinearVecDelta) {
            LinearVecDelta.write(packetWrapper, (LinearVecDelta) vecDelta);
        } else {
            if (!(vecDelta instanceof SteppedVecDelta)) {
                throw new UnsupportedOperationException(
                        "Unsupported VecDelta implementation: " + vecDelta);
            }
            SteppedVecDelta.write(packetWrapper, (SteppedVecDelta) vecDelta);
        }
    }

    PositionPath applyAsPath(Vector3d vector3d);

    Vector3d apply(Vector3d vector3d);

    int getStepCount();

    boolean hasDeltaX();

    boolean hasDeltaZ();
}
