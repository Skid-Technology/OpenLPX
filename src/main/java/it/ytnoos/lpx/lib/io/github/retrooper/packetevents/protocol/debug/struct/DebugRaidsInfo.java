package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class DebugRaidsInfo {

    private final List<Vector3i> positions;

    public DebugRaidsInfo(List<Vector3i> positions) {
        this.positions = positions;
    }

    public static DebugRaidsInfo read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(PacketWrapper::readBlockPosition);
        return new DebugRaidsInfo(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugRaidsInfo debugRaidsInfo) {
        packetWrapper.writeList(debugRaidsInfo.positions, PacketWrapper::writeBlockPosition);
    }

    public List<Vector3i> getPositions() {
        return this.positions;
    }
}
