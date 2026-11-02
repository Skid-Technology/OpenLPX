package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MathUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Internal
public final class LpVector3d {

    private static final double ABS_MAX_VALUE = Double.longBitsToDouble(4760304806130089984L);

    private static final double ABS_MIN_VALUE = Double.longBitsToDouble(4539628699284145152L);

    private LpVector3d() {}

    public static Vector3d read(PacketWrapper<?> packetWrapper) {
        short value;
        if ((value = packetWrapper.readUnsignedByte()) == 0) {
            return Vector3d.zero();
        }
        long value2 =
                value
                        | (long) packetWrapper.readUnsignedByte() << 8
                        | packetWrapper.readUnsignedInt() << 16;
        long value3 = value & 3L;
        if ((value & 4L) != 0L) {
            value3 |= (packetWrapper.readVarInt() & 4294967295L) << 2;
        }
        return new Vector3d(
                unpack(value2 >> 3) * value3,
                unpack(value2 >> 18) * value3,
                unpack(value2 >> 33) * value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, Vector3d vector3d) {
        double value = sanitize(vector3d.x);
        double value2 = sanitize(vector3d.y);
        double value3 = sanitize(vector3d.z);
        double value4;
        if ((value4 = MathUtil.absMax(value, MathUtil.absMax(value2, value3))) < ABS_MIN_VALUE) {
            packetWrapper.writeByte(0);
        } else {
            long value5;
            boolean matches;
            long value6 =
                    (matches = ((value5 = MathUtil.ceilLong(value4)) & 3L) != value5)
                            ? value5 & 3L | 4L
                            : value5;
            long value7 = pack(value / value5) << 3;
            long value8 = pack(value2 / value5) << 18;
            long value9 = pack(value3 / value5) << 33;
            long value10 = value6 | value7 | value8 | value9;
            packetWrapper.writeShortLE((short) value10);
            packetWrapper.writeInt((int) (value10 >> 16));
            if (matches) {
                packetWrapper.writeVarInt((int) (value5 >> 2));
            }
        }
    }

    private static double sanitize(double value) {
        return !Double.isNaN(value) ? MathUtil.clamp(value, -ABS_MAX_VALUE, ABS_MAX_VALUE) : 0.0;
    }

    private static long pack(double value) {
        return Math.round((value * 0.5 + 0.5) * 32766.0);
    }

    private static double unpack(long value) {
        return Math.min(value & 32767L, 32766.0) * 2.0 / 32766.0 - 1.0;
    }
}
