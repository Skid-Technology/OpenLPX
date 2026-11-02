package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.vector.positionpath;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class SteppedPositionPath implements PositionPath {

    private final List<SteppedPositionPath.Step> steps;

    public SteppedPositionPath(List<SteppedPositionPath.Step> steps) {
        this.steps = steps;
    }

    public static SteppedPositionPath read(PacketWrapper<?> packetWrapper) {
        return new SteppedPositionPath(packetWrapper.readList(SteppedPositionPath.Step::read));
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SteppedPositionPath steppedPositionPath) {
        packetWrapper.writeList(steppedPositionPath.steps, SteppedPositionPath.Step::write);
    }

    @Override
    public PositionPathType<?> getType() {
        return PositionPathTypes.STEPPED;
    }

    @Override
    public Vector3d getEndPosition() throws IllegalStateException {
        if (this.steps.isEmpty()) {
            throw new IllegalStateException("No steps present, can't get end position");
        } else {
            return this.steps.get(this.steps.size() - 1).position();
        }
    }

    public List<SteppedPositionPath.Step> getSteps() {
        return this.steps;
    }

    public static final class Step {

        private final double x;

        private final double y;

        private final double z;

        private final int tickOffset;

        public Step(Vector3d vector3d, int value) {
            this(vector3d.x, vector3d.y, vector3d.z, value);
        }

        public Step(double x, double y, double z, int tickOffset) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.tickOffset = tickOffset;
        }

        public static SteppedPositionPath.Step read(PacketWrapper<?> packetWrapper) {
            double value = packetWrapper.readDouble();
            double value2 = packetWrapper.readDouble();
            double value3 = packetWrapper.readDouble();
            int value4 = packetWrapper.readVarInt();
            return new SteppedPositionPath.Step(value, value2, value3, value4);
        }

        public static void write(PacketWrapper<?> packetWrapper, SteppedPositionPath.Step step) {
            packetWrapper.writeDouble(step.x);
            packetWrapper.writeDouble(step.y);
            packetWrapper.writeDouble(step.z);
            packetWrapper.writeVarInt(step.tickOffset);
        }

        public Vector3d position() {
            return new Vector3d(this.x, this.y, this.z);
        }

        public double x() {
            return this.x;
        }

        public double y() {
            return this.y;
        }

        public double z() {
            return this.z;
        }

        public int tickOffset() {
            return this.tickOffset;
        }
    }
}
