package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v_1_18.Chunk_v1_18;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.ChunkReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage.BaseStorage;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.BitSet;

public class ChunkReader_v1_18 implements ChunkReader {

    @Internal
    public static int getMojangZeroByteSuffixLength(BaseChunk[] baseChunks) {
        int value = 0;
        int value2 = baseChunks.length;
        for (int index = 0; index < value2; index++) {
            BaseChunk baseChunk;
            BaseStorage baseStorage;
            int byteSize =
                    ByteBufHelper.getByteSize(
                            (baseStorage =
                                                    ((Chunk_v1_18) (baseChunk = baseChunks[index]))
                                                            .getChunkData()
                                                            .storage)
                                            != null
                                    ? baseStorage.getData().length
                                    : 0);
            BaseStorage baseStorage2;
            int byteSize2 =
                    ByteBufHelper.getByteSize(
                            (baseStorage2 = ((Chunk_v1_18) baseChunk).getBiomeData().storage)
                                            != null
                                    ? baseStorage2.getData().length
                                    : 0);
            value += byteSize + byteSize2;
        }
        return value;
    }

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
        int value3 = ByteBufHelper.readerIndex(packetWrapper.buffer);
        BaseChunk[] baseChunks = new BaseChunk[value];
        for (int index = 0; index < value; index++) {
            baseChunks[index] = Chunk_v1_18.read(packetWrapper);
        }
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_21_6)
                && packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)
                && ByteBufHelper.readerIndex(packetWrapper.buffer) - value3 < value2) {
            ByteBufHelper.skipBytes(
                    packetWrapper.buffer, getMojangZeroByteSuffixLength(baseChunks));
        }
        return baseChunks;
    }
}
