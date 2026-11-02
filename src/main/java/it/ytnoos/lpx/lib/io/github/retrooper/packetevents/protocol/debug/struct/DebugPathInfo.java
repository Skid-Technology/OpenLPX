package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.path.DebugPath;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugPathInfo {

    private final DebugPath path;

    private final float maxNodeDistance;

    public DebugPathInfo(DebugPath debugPath, float maxNodeDistance) {
        this.path = debugPath;
        this.maxNodeDistance = maxNodeDistance;
    }

    public static DebugPathInfo read(PacketWrapper<?> packetWrapper) {
        DebugPath debugPath = DebugPath.read(packetWrapper);
        float value = packetWrapper.readFloat();
        return new DebugPathInfo(debugPath, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugPathInfo debugPathInfo) {
        DebugPath.write(packetWrapper, debugPathInfo.path);
        packetWrapper.writeFloat(debugPathInfo.maxNodeDistance);
    }

    public DebugPath getPath() {
        return this.path;
    }

    public float getMaxNodeDistance() {
        return this.maxNodeDistance;
    }
}
