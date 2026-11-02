package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.impl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.ByteArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NetworkChunkData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NibbleArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_7.Chunk_v1_7;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.reader.ChunkReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.BitSet;

public class ChunkReader_v1_7 implements ChunkReader {

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
        Chunk_v1_7[] chunk_v1_7s = new Chunk_v1_7[16];
        int value3 = 0;
        short value4 = 0;
        boolean matches4 = false;
        for (int index = 0; index < 5; index++) {
            for (int index2 = 0; index2 < 16; index2++) {
                if (bitSet.get(index2)) {
                    if (index == 0) {
                        value4 += 10240;
                        if (bitSet2.get(index2)) {
                            value4 += 2048;
                        }
                    }
                    if (index == 1) {
                        chunk_v1_7s[index2] = new Chunk_v1_7(matches4, bitSet2.get(index2));
                        ByteArray3d blocks = chunk_v1_7s[index2].getBlocks();
                        System.arraycopy(
                                values, value3, blocks.getData(), 0, blocks.getData().length);
                        value3 += blocks.getData().length;
                    }
                    if (index == 2) {
                        NibbleArray3d metadata = chunk_v1_7s[index2].getMetadata();
                        System.arraycopy(
                                values, value3, metadata.getData(), 0, metadata.getData().length);
                        value3 += metadata.getData().length;
                    }
                    if (index == 3) {
                        NibbleArray3d blockLight = chunk_v1_7s[index2].getBlockLight();
                        System.arraycopy(
                                values,
                                value3,
                                blockLight.getData(),
                                0,
                                blockLight.getData().length);
                        value3 += blockLight.getData().length;
                    }
                    if (index == 4 && matches4) {
                        NibbleArray3d skyLight = chunk_v1_7s[index2].getSkyLight();
                        System.arraycopy(
                                values, value3, skyLight.getData(), 0, skyLight.getData().length);
                        value3 += skyLight.getData().length;
                    }
                }
            }
            if (index == 0 && values.length >= value4) {
                matches4 = true;
            }
        }
        int value5 = ByteBufHelper.readerIndex(packetWrapper.buffer);
        ByteBufHelper.readerIndex(packetWrapper.buffer, value5 - (value2 - value3));
        return chunk_v1_7s;
    }

    public static NetworkChunkData chunksToData(Chunk_v1_7[] chunk_v1_7s, byte[] values) {
        int value = 0;
        int value2 = 0;
        boolean matches = values != null;
        boolean matches2 = false;
        int value3 = matches ? values.length : 0;
        byte[] values2 = null;
        int value4 = 0;
        for (int index = 0; index < 6; index++) {
            for (int index2 = 0; index2 < chunk_v1_7s.length; index2++) {
                Chunk_v1_7 chunk_v1_7;
                if ((chunk_v1_7 = chunk_v1_7s[index2]) != null
                        && (!matches || !chunk_v1_7.isEmpty())) {
                    if (index == 0) {
                        value |= 1 << index2;
                        if (chunk_v1_7.getExtendedBlocks() != null) {
                            value2 |= 1 << index2;
                        }
                        value3 =
                                value3
                                        + chunk_v1_7.getBlocks().getData().length
                                        + chunk_v1_7.getMetadata().getData().length
                                        + chunk_v1_7.getBlockLight().getData().length;
                        if (chunk_v1_7.getSkyLight() != null) {
                            value3 += chunk_v1_7.getSkyLight().getData().length;
                        }
                        if (chunk_v1_7.getExtendedBlocks() != null) {
                            value3 += chunk_v1_7.getExtendedBlocks().getData().length;
                        }
                    }
                    if (index == 1) {
                        ByteArray3d byteArray3d;
                        System.arraycopy(
                                (byteArray3d = chunk_v1_7.getBlocks()).getData(),
                                0,
                                values2,
                                value4,
                                byteArray3d.getData().length);
                        value4 += byteArray3d.getData().length;
                    }
                    if (index == 2) {
                        byte[] values3;
                        System.arraycopy(
                                values3 = chunk_v1_7.getMetadata().getData(),
                                0,
                                values2,
                                value4,
                                values3.length);
                        value4 += values3.length;
                    }
                    if (index == 3) {
                        byte[] values4;
                        System.arraycopy(
                                values4 = chunk_v1_7.getBlockLight().getData(),
                                0,
                                values2,
                                value4,
                                values4.length);
                        value4 += values4.length;
                    }
                    if (index == 4 && chunk_v1_7.getSkyLight() != null) {
                        byte[] values5;
                        System.arraycopy(
                                values5 = chunk_v1_7.getSkyLight().getData(),
                                0,
                                values2,
                                value4,
                                values5.length);
                        value4 += values5.length;
                        matches2 = true;
                    }
                    if (index == 5 && chunk_v1_7.getExtendedBlocks() != null) {
                        byte[] values6;
                        System.arraycopy(
                                values6 = chunk_v1_7.getExtendedBlocks().getData(),
                                0,
                                values2,
                                value4,
                                values6.length);
                        value4 += values6.length;
                    }
                }
            }
            if (index == 0) {
                values2 = new byte[value3];
            }
        }
        if (matches) {
            System.arraycopy(values, 0, values2, value4, values.length);
            int value5 = value4 + values.length;
        }
        return new NetworkChunkData(value, value2, matches, matches2, values2);
    }
}
