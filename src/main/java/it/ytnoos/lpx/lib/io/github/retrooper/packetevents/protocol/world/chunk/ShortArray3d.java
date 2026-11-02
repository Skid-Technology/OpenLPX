package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import java.util.Arrays;

public class ShortArray3d {

    private final short[] data;

    public ShortArray3d(int value) {
        this.data = new short[value];
    }

    public ShortArray3d(short[] data) {
        this.data = data;
    }

    public short[] getData() {
        return this.data;
    }

    public int get(int value, int value2, int value3) {
        return this.data[value2 << 8 | value3 << 4 | value] & 65535;
    }

    public void set(int value, int value2, int value3, int value4) {
        this.data[value2 << 8 | value3 << 4 | value] = (short) value4;
    }

    public int getBlock(int value, int value2, int value3) {
        return this.get(value, value2, value3) >> 4;
    }

    public void setBlock(int value, int value2, int value3, int value4) {
        this.set(value, value2, value3, value4 << 4 | this.getData(value, value2, value3));
    }

    public int getData(int value, int value2, int value3) {
        return this.get(value, value2, value3) & 15;
    }

    public void setData(int value, int value2, int value3, int value4) {
        this.set(value, value2, value3, this.getBlock(value, value2, value3) << 4 | value4);
    }

    public void setBlockAndData(int value, int value2, int value3, int value4, int value5) {
        this.set(value, value2, value3, value4 << 4 | value5);
    }

    public void fill(int value) {
        Arrays.fill(this.data, (short) value);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            ShortArray3d shortArray3d = (ShortArray3d) result;
            return Arrays.equals(this.data, shortArray3d.data);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
}
