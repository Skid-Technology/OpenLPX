package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import java.util.Arrays;

public class ByteArray3d {

    private final byte[] data;

    public ByteArray3d(int value) {
        this.data = new byte[value];
    }

    public ByteArray3d(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public int get(int value, int value2, int value3) {
        return this.data[value2 << 8 | value3 << 4 | value] & 0xFF;
    }

    public void set(int value, int value2, int value3, int value4) {
        this.data[value2 << 8 | value3 << 4 | value] = (byte) value4;
    }

    public void fill(int value) {
        Arrays.fill(this.data, (byte) value);
    }
}
