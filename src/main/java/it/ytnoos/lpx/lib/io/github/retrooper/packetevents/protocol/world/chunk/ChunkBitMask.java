package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.BitSet;

@Internal
public class ChunkBitMask {

    private ChunkBitMask() {}

    public static long[] readBitSetLongs(PacketWrapper<?> packetWrapper) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_17)) {
            return packetWrapper.readLongArray();
        } else {
            return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)
                    ? new long[] {packetWrapper.readVarInt()}
                    : new long[] {packetWrapper.readUnsignedShort()};
        }
    }

    public static BitSet readChunkMask(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)
                ? BitSet.valueOf(packetWrapper.readByteArray())
                : BitSet.valueOf(readBitSetLongs(packetWrapper));
    }

    public static void writeChunkMask(PacketWrapper<?> packetWrapper, BitSet bitSet) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            packetWrapper.writeByteArray(bitSet.toByteArray());
        } else {
            long[] values = bitSet.toLongArray();
            if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_17)) {
                packetWrapper.writeLongArray(values);
            } else if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                if (values.length > 0) {
                    packetWrapper.writeVarInt((int) values[0]);
                } else {
                    packetWrapper.writeVarInt(0);
                }
            } else if (values.length > 0) {
                packetWrapper.writeShort((int) values[0]);
            } else {
                packetWrapper.writeShort(0);
            }
        }
    }
}
