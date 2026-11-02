package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NetworkChunkData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NibbleArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.ShortArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_8.Chunk_v1_8;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.ChunkReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.BitSet;

public class ChunkReader_v1_8 implements ChunkReader {

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
        byte[] values = packetWrapper.readByteArrayOfSize(value2);
        Chunk_v1_8[] chunk_v1_8s = new Chunk_v1_8[16];
        int value3 = 0;
        int value4 = matches ? 256 : 0;
        boolean matches4 = false;
        ShortBuffer shortBuffer =
                ByteBuffer.wrap(values).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
        for (int index = 0; index < 4; index++) {
            for (int index2 = 0; index2 < 16; index2++) {
                if (bitSet.get(index2)) {
                    if (index == 0) {
                        value4 += 10240;
                    }
                    if (index == 1) {
                        chunk_v1_8s[index2] = new Chunk_v1_8(matches4 || matches2);
                        ShortArray3d blocks = chunk_v1_8s[index2].getBlocks();
                        ((Buffer) shortBuffer).position(value3 / 2);
                        shortBuffer.get(blocks.getData(), 0, blocks.getData().length);
                        value3 += blocks.getData().length * 2;
                    }
                    if (index == 2) {
                        NibbleArray3d blockLight = chunk_v1_8s[index2].getBlockLight();
                        System.arraycopy(
                                values,
                                value3,
                                blockLight.getData(),
                                0,
                                blockLight.getData().length);
                        value3 += blockLight.getData().length;
                    }
                    if (index == 3 && (matches4 || matches2)) {
                        NibbleArray3d skyLight = chunk_v1_8s[index2].getSkyLight();
                        System.arraycopy(
                                values, value3, skyLight.getData(), 0, skyLight.getData().length);
                        value3 += skyLight.getData().length;
                    }
                }
            }
            if (index == 0 && values.length > value4) {
                matches4 = matches3;
            }
        }
        int value5 = ByteBufHelper.readerIndex(packetWrapper.buffer);
        ByteBufHelper.readerIndex(packetWrapper.buffer, value5 - (value2 - value3));
        return chunk_v1_8s;
    }

    public static NetworkChunkData chunksToData(Chunk_v1_8[] chunk_v1_8s, byte[] values) {
        int value = 0;
        boolean matches = values != null;
        boolean matches2 = false;
        int value2 = matches ? values.length : 0;
        byte[] values2 = null;
        int value3 = 0;
        ShortBuffer shortBuffer = null;
        for (int index = 0; index < 4; index++) {
            for (int index2 = 0; index2 < chunk_v1_8s.length; index2++) {
                Chunk_v1_8 chunk_v1_8;
                if ((chunk_v1_8 = chunk_v1_8s[index2]) != null
                        && (!matches || !chunk_v1_8.isEmpty())) {
                    if (index == 0) {
                        value |= 1 << index2;
                        value2 =
                                value2
                                        + chunk_v1_8.getBlocks().getData().length * 2
                                        + chunk_v1_8.getBlockLight().getData().length;
                        if (chunk_v1_8.getSkyLight() != null) {
                            value2 += chunk_v1_8.getSkyLight().getData().length;
                        }
                    }
                    if (index == 1) {
                        short[] data = chunk_v1_8.getBlocks().getData();
                        ((Buffer) shortBuffer).position(value3 / 2);
                        shortBuffer.put(data, 0, data.length);
                        value3 += data.length * 2;
                    }
                    if (index == 2) {
                        byte[] values3;
                        System.arraycopy(
                                values3 = chunk_v1_8.getBlockLight().getData(),
                                0,
                                values2,
                                value3,
                                values3.length);
                        value3 += values3.length;
                    }
                    if (index == 3 && chunk_v1_8.getSkyLight() != null) {
                        byte[] values4;
                        System.arraycopy(
                                values4 = chunk_v1_8.getSkyLight().getData(),
                                0,
                                values2,
                                value3,
                                values4.length);
                        value3 += values4.length;
                        matches2 = true;
                    }
                }
            }
            if (index == 0) {
                shortBuffer =
                        ByteBuffer.wrap(values2 = new byte[value2])
                                .order(ByteOrder.LITTLE_ENDIAN)
                                .asShortBuffer();
            }
        }
        if (matches) {
            System.arraycopy(values, 0, values2, value3, values.length);
            int value4 = value3 + values.length;
        }
        return new NetworkChunkData(value, matches, matches2, values2);
    }
}
