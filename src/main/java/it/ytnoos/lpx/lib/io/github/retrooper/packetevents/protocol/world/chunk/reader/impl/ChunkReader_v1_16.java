package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_16.Chunk_v1_9;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.ChunkReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.BitSet;

public class ChunkReader_v1_16 implements ChunkReader {

    @Override
    public BaseChunk[] read(
            DimensionType dimensionType,
            BitSet bitSet,
            BitSet bitSet2,
            boolean matches,
            boolean matches2,
            boolean matches3,
            int value,
            int value2,
            PacketWrapper<?> packetWrapper) {
        BaseChunk[] baseChunks = new BaseChunk[value];
        for (int index = 0; index < baseChunks.length; index++) {
            if (bitSet.get(index)) {
                baseChunks[index] = Chunk_v1_9.read(packetWrapper, false, false);
            }
        }
        return baseChunks;
    }
}
