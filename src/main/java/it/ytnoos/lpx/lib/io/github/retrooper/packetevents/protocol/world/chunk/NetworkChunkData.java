package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

public class NetworkChunkData {

    private final int mask;

    private int extendedChunkMask;

    private final boolean fullChunk;

    private final boolean sky;

    private byte[] data;

    public NetworkChunkData(int mask, boolean fullChunk, boolean sky, byte[] data) {
        this.mask = mask;
        this.fullChunk = fullChunk;
        this.sky = sky;
        this.data = data;
    }

    public NetworkChunkData(
            int value, int extendedChunkMask, boolean matches, boolean matches2, byte[] values) {
        this(value, matches, matches2, values);
        this.extendedChunkMask = extendedChunkMask;
    }

    public int getMask() {
        return this.mask;
    }

    public int getExtendedChunkMask() {
        return this.extendedChunkMask;
    }

    public boolean isFullChunk() {
        return this.fullChunk;
    }

    public boolean hasSkyLight() {
        return this.sky;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
