package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.LinearPositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.PositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class LinearVecDelta implements VecDelta {

    private final short dx;

    private final short dy;

    private final short dz;

    public LinearVecDelta(double value, double value2, double value3) {
        this(
                (short) VecDelta.encode(value),
                (short) VecDelta.encode(value2),
                (short) VecDelta.encode(value3));
    }

    public LinearVecDelta(short dx, short dy, short dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public static LinearVecDelta read(PacketWrapper<?> packetWrapper) {
        short value = packetWrapper.readShort();
        short value2 = packetWrapper.readShort();
        short value3 = packetWrapper.readShort();
        return new LinearVecDelta(value, value2, value3);
    }

    public static void write(PacketWrapper<?> packetWrapper, LinearVecDelta linearVecDelta) {
        packetWrapper.writeShort(linearVecDelta.dx);
        packetWrapper.writeShort(linearVecDelta.dy);
        packetWrapper.writeShort(linearVecDelta.dz);
    }

    @Override
    public PositionPath applyAsPath(Vector3d vector3d) {
        return new LinearPositionPath(this.apply(vector3d));
    }

    @Override
    public Vector3d apply(Vector3d vector3d) {
        return this.dx == 0 && this.dy == 0 && this.dz == 0
                ? vector3d
                : VecDelta.decode(vector3d, this.dx, this.dy, this.dz);
    }

    @Override
    public int getStepCount() {
        return 0;
    }

    @Override
    public boolean hasDeltaX() {
        return this.dx != 0;
    }

    @Override
    public boolean hasDeltaZ() {
        return this.dz != 0;
    }

    public short dxRaw() {
        return this.dx;
    }

    public short dyRaw() {
        return this.dy;
    }

    public short dzRaw() {
        return this.dz;
    }

    public double dx() {
        return VecDelta.decode(this.dx);
    }

    public double dy() {
        return VecDelta.decode(this.dy);
    }

    public double dz() {
        return VecDelta.decode(this.dz);
    }
}
