package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_7;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.ByteArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NibbleArray3d;

public class Chunk_v1_7 implements BaseChunk {

    private final ByteArray3d blocks;

    private NibbleArray3d metadata;

    private NibbleArray3d blocklight;

    private NibbleArray3d skylight;

    private NibbleArray3d extendedBlocks;

    public Chunk_v1_7(boolean matches, boolean matches2) {
        this(
                new ByteArray3d(4096),
                new NibbleArray3d(4096),
                new NibbleArray3d(4096),
                matches ? new NibbleArray3d(4096) : null,
                matches2 ? new NibbleArray3d(4096) : null);
    }

    public Chunk_v1_7(
            ByteArray3d byteArray3d,
            NibbleArray3d nibbleArray3d,
            NibbleArray3d nibbleArray3d2,
            NibbleArray3d nibbleArray3d3,
            NibbleArray3d nibbleArray3d4) {
        this.blocks = byteArray3d;
        this.metadata = nibbleArray3d;
        this.blocklight = nibbleArray3d2;
        this.skylight = nibbleArray3d3;
        this.extendedBlocks = nibbleArray3d4;
    }

    @Override
    public int getBlockId(int value, int value2, int value3) {
        int value4 =
                (this.blocks.get(value, value2, value3) & 0xFF) << 4
                        | this.metadata.get(value, value2, value3) & 15;
        if (this.extendedBlocks != null) {
            value4 |= (this.extendedBlocks.get(value, value2, value3) & 15) << 8;
        }
        return value4;
    }

    @Override
    public void set(int value, int value2, int value3, int value4) {
        this.blocks.set(value, value2, value3, value4 >> 4 & 0xFF);
        this.metadata.set(value, value2, value3, value4 & 15);
        if (this.extendedBlocks != null) {
            this.extendedBlocks.set(value, value2, value3, value4 >> 8 & 15);
        }
    }

    @Override
    public boolean isEmpty() {
        byte[] values;
        int value = (values = this.blocks.getData()).length;
        for (int index = 0; index < value; index++) {
            if (values[index] != 0) {
                return false;
            }
        }
        return true;
    }

    public ByteArray3d getBlocks() {
        return this.blocks;
    }

    public NibbleArray3d getMetadata() {
        return this.metadata;
    }

    public NibbleArray3d getBlockLight() {
        return this.blocklight;
    }

    public NibbleArray3d getSkyLight() {
        return this.skylight;
    }

    public NibbleArray3d getExtendedBlocks() {
        return this.extendedBlocks;
    }
}
