package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.LinearPositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.PositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath.SteppedPositionPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NullMarked
public final class SteppedVecDelta implements VecDelta {

    private final List<SteppedVecDelta.DeltaStep> steps;

    public SteppedVecDelta(List<SteppedVecDelta.DeltaStep> steps) {
        this.steps = steps;
    }

    public static SteppedVecDelta read(PacketWrapper<?> packetWrapper, int value) {
        int value2 = ByteBufHelper.readableBytes(packetWrapper.buffer) / 7;
        if (value > value2) {
            throw new IllegalStateException(
                    "VecDelta with size " + value + " is bigger than allowed " + value2);
        }
        ArrayList<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta
                                .SteppedVecDelta.DeltaStep>
                values = new ArrayList<>(value);
        for (int index = 0; index < value; index++) {
            values.add(SteppedVecDelta.DeltaStep.read(packetWrapper));
        }
        return new SteppedVecDelta(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, SteppedVecDelta steppedVecDelta) {
        for (SteppedVecDelta.DeltaStep deltaStep : steppedVecDelta.steps) {
            SteppedVecDelta.DeltaStep.write(packetWrapper, deltaStep);
        }
    }

    @Override
    public PositionPath applyAsPath(Vector3d vector3d) {
        if (this.steps.isEmpty()) {
            return new LinearPositionPath(vector3d);
        }
        ArrayList<SteppedPositionPath.Step> values = new ArrayList<>(this.steps.size());
        for (SteppedVecDelta.DeltaStep deltaStep : this.steps) {
            Vector3d vector3d2 =
                    VecDelta.decode(vector3d, deltaStep.dx, deltaStep.dy, deltaStep.dz);
            values.add(new SteppedPositionPath.Step(vector3d2, deltaStep.ticks));
            vector3d = vector3d2;
        }
        return new SteppedPositionPath(values);
    }

    @Override
    public Vector3d apply(Vector3d vector3d) {
        if (this.steps.isEmpty()) {
            return vector3d;
        }
        double value = vector3d.x;
        double value2 = vector3d.y;
        double value3 = vector3d.z;
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta
                                .SteppedVecDelta.DeltaStep>
                iterator2 = this.steps.iterator();
        while (iterator2.hasNext()) {
            SteppedVecDelta.DeltaStep deltaStep;
            if ((deltaStep = (SteppedVecDelta.DeltaStep) iterator2.next()).dx != 0L) {
                value = VecDelta.decode(VecDelta.encode(value) + deltaStep.dx);
            }
            if (deltaStep.dy != 0L) {
                value2 = VecDelta.decode(VecDelta.encode(value2) + deltaStep.dy);
            }
            if (deltaStep.dz != 0L) {
                value3 = VecDelta.decode(VecDelta.encode(value3) + deltaStep.dz);
            }
        }
        return new Vector3d(value, value2, value3);
    }

    @Override
    public int getStepCount() {
        return this.steps.size();
    }

    @Override
    public boolean hasDeltaX() {
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta
                                .SteppedVecDelta.DeltaStep>
                iterator2 = this.steps.iterator();
        while (iterator2.hasNext()) {
            if (((SteppedVecDelta.DeltaStep) iterator2.next()).dx != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasDeltaZ() {
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.vecdelta
                                .SteppedVecDelta.DeltaStep>
                iterator2 = this.steps.iterator();
        while (iterator2.hasNext()) {
            if (((SteppedVecDelta.DeltaStep) iterator2.next()).dz != 0) {
                return true;
            }
        }
        return false;
    }

    public List<SteppedVecDelta.DeltaStep> steps() {
        return this.steps;
    }

    public static final class DeltaStep {

        private final int ticks;

        private final short dx;

        private final short dy;

        private final short dz;

        public DeltaStep(int ticks, short dx, short dy, short dz) {
            this.ticks = ticks;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
        }

        public static SteppedVecDelta.DeltaStep read(PacketWrapper<?> packetWrapper) {
            int value = packetWrapper.readVarInt();
            short value2 = packetWrapper.readShort();
            short value3 = packetWrapper.readShort();
            short value4 = packetWrapper.readShort();
            return new SteppedVecDelta.DeltaStep(value, value2, value3, value4);
        }

        public static void write(
                PacketWrapper<?> packetWrapper, SteppedVecDelta.DeltaStep deltaStep) {
            packetWrapper.writeVarInt(deltaStep.ticks);
            packetWrapper.writeShort(deltaStep.dx);
            packetWrapper.writeShort(deltaStep.dy);
            packetWrapper.writeShort(deltaStep.dz);
        }

        public int ticks() {
            return this.ticks;
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
}
