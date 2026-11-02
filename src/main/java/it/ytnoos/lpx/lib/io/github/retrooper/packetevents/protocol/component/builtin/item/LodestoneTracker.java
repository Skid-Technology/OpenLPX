package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LodestoneTracker {

    @Nullable private WorldBlockPosition target;

    private boolean tracked;

    public LodestoneTracker(@Nullable WorldBlockPosition worldBlockPosition, boolean tracked) {
        this.target = worldBlockPosition;
        this.tracked = tracked;
    }

    public static LodestoneTracker read(PacketWrapper<?> packetWrapper) {
        WorldBlockPosition worldBlockPosition =
                packetWrapper.readOptional(PacketWrapper::readWorldBlockPosition);
        boolean matches = packetWrapper.readBoolean();
        return new LodestoneTracker(worldBlockPosition, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, LodestoneTracker lodestoneTracker) {
        packetWrapper.writeOptional(
                lodestoneTracker.target, PacketWrapper::writeWorldBlockPosition);
        packetWrapper.writeBoolean(lodestoneTracker.tracked);
    }

    @Nullable
    public WorldBlockPosition getTarget() {
        return this.target;
    }

    public void setTarget(@Nullable WorldBlockPosition worldBlockPosition) {
        this.target = worldBlockPosition;
    }

    public boolean isTracked() {
        return this.tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof LodestoneTracker)) {
            return false;
        }
        LodestoneTracker lodestoneTracker = (LodestoneTracker) result;
        return this.tracked != lodestoneTracker.tracked
                ? false
                : Objects.equals(this.target, lodestoneTracker.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.target, this.tracked);
    }
}
