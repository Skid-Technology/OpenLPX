package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class ClockNetworkState {

    private final long totalTicks;

    private final float partialTick;

    private final float rate;

    @Obsolete
    public ClockNetworkState(long value, boolean matches) {
        this(value, 0.0F, matches ? 0.0F : 1.0F);
    }

    public ClockNetworkState(long totalTicks, float partialTick, float rate) {
        this.totalTicks = totalTicks;
        this.partialTick = partialTick;
        this.rate = rate;
    }

    public static ClockNetworkState read(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            long value = packetWrapper.readVarLong();
            float value2 = packetWrapper.readFloat();
            float value3 = packetWrapper.readFloat();
            return new ClockNetworkState(value, value2, value3);
        } else {
            long value4 = packetWrapper.readLong();
            boolean matches =
                    packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)
                            ? packetWrapper.readBoolean()
                            : value4 >= 0L;
            return new ClockNetworkState(value4, !matches);
        }
    }

    public static void write(PacketWrapper<?> packetWrapper, ClockNetworkState clockNetworkState) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_1)) {
            packetWrapper.writeVarLong(clockNetworkState.totalTicks);
            packetWrapper.writeFloat(clockNetworkState.partialTick);
            packetWrapper.writeFloat(clockNetworkState.rate);
        } else {
            packetWrapper.writeLong(clockNetworkState.totalTicks);
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2)) {
                packetWrapper.writeBoolean(!clockNetworkState.isPaused());
            }
        }
    }

    public long getTotalTicks() {
        return this.totalTicks;
    }

    public ClockNetworkState withTotalTicks(long value) {
        return new ClockNetworkState(value, this.partialTick, this.rate);
    }

    public float getPartialTick() {
        return this.partialTick;
    }

    public ClockNetworkState withPartialTick(float value) {
        return new ClockNetworkState(this.totalTicks, value, this.rate);
    }

    public float getRate() {
        return this.rate;
    }

    public ClockNetworkState withRate(float value) {
        return new ClockNetworkState(this.totalTicks, this.partialTick, value);
    }

    public boolean isPaused() {
        return Float.compare(this.rate, 0.0F) == 0;
    }

    public ClockNetworkState withPaused(boolean matches) {
        return new ClockNetworkState(this.totalTicks, this.partialTick, matches ? 0.0F : 1.0F);
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ClockNetworkState)) {
            return false;
        } else {
            ClockNetworkState clockNetworkState = (ClockNetworkState) result;
            if (this.totalTicks != clockNetworkState.totalTicks) {
                return false;
            } else {
                return Float.compare(clockNetworkState.partialTick, this.partialTick) != 0
                        ? false
                        : Float.compare(clockNetworkState.rate, this.rate) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.totalTicks, this.partialTick, this.rate);
    }
}
