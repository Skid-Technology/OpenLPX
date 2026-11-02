package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.io.ByteArrayInputStream;
import java.util.BitSet;

public interface ChunkReader {

    @Deprecated
    default BaseChunk[] read(
            Dimension dimension,
            BitSet bitSet,
            BitSet bitSet2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            int value,
            byte[] values,
            NetStreamInput netStreamInput) {
        DimensionType dimensionType = dimension.asDimensionType(null, null);
        PacketWrapper packetWrapper =
                PacketWrapper.createUniversalPacketWrapper(
                        UnpooledByteBufAllocationHelper.wrappedBuffer(values));
        try {
            return this.read(
                    dimensionType,
                    bitSet,
                    bitSet2,
                    matches,
                    matches2,
                    matches3,
                    value,
                    values.length,
                    packetWrapper);
        } finally {
            ByteBufHelper.release(packetWrapper.buffer);
        }
    }

    @Deprecated
    default BaseChunk[] read(
            DimensionType dimensionType,
            BitSet bitSet,
            BitSet bitSet2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            int value,
            byte[] values,
            NetStreamInput netStreamInput) {
        Dimension dimension = Dimension.fromDimensionType(dimensionType, null, null);
        return this.read(
                dimension,
                bitSet,
                bitSet2,
                matches,
                matches2,
                matches3,
                value,
                values,
                netStreamInput);
    }

    default BaseChunk[] read(
            DimensionType dimensionType,
            BitSet bitSet,
            BitSet bitSet2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            int value,
            int value2,
            PacketWrapper<?> packetWrapper) {
        byte[] values = packetWrapper.readByteArrayOfSize(value2);
        NetStreamInput netStreamInput = new NetStreamInput(new ByteArrayInputStream(values));
        return this.read(
                dimensionType,
                bitSet,
                bitSet2,
                matches,
                matches2,
                matches3,
                value,
                values,
                netStreamInput);
    }
}
