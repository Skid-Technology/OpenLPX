package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.impl.v1_8;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.BaseChunk;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.NibbleArray3d;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.ShortArray3d;

public class Chunk_v1_8 implements BaseChunk {

    private ShortArray3d blocks;

    private NibbleArray3d blocklight;

    private NibbleArray3d skylight;

    public Chunk_v1_8(boolean matches) {
        this(
                new ShortArray3d(4096),
                new NibbleArray3d(4096),
                matches ? new NibbleArray3d(4096) : null);
    }

    public Chunk_v1_8(
            ShortArray3d shortArray3d, NibbleArray3d nibbleArray3d, NibbleArray3d nibbleArray3d2) {
        this.blocks = shortArray3d;
        this.blocklight = nibbleArray3d;
        this.skylight = nibbleArray3d2;
    }

    public ShortArray3d getBlocks() {
        return this.blocks;
    }

    public NibbleArray3d getBlockLight() {
        return this.blocklight;
    }

    public NibbleArray3d getSkyLight() {
        return this.skylight;
    }

    @Override
    public int getBlockId(int value, int value2, int value3) {
        return this.blocks.get(value, value2, value3);
    }

    @Override
    public void set(int value, int value2, int value3, int value4) {
        this.blocks.set(value, value2, value3, value4);
    }

    @Override
    public boolean isEmpty() {
        short[] values;
        int value = (values = this.blocks.getData()).length;
        for (int index = 0; index < value; index++) {
            if (values[index] != 0) {
                return false;
            }
        }
        return true;
    }
}
