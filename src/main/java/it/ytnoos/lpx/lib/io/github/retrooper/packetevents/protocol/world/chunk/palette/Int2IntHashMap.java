package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

@NullMarked
@Internal
final class Int2IntHashMap {

    public static final int EMPTY_VALUE = -1;

    private long[] data;

    private int size;

    private int threshold;

    public Int2IntHashMap(int value) {
        int value2 = tableSizeFor(value);
        this.threshold = (int) (value2 * 0.75F);
        this.data = new long[value2];
        Arrays.fill(this.data, -1L);
    }

    private static int tableSizeFor(int value) {
        return value <= 1 ? 1 : 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
    }

    public int get(int value) {
        int value2 = this.data.length - 1;
        long value3;
        for (int value4 = this.hash(value) & value2;
                (value3 = this.data[value4]) != -1L;
                value4 = value4 + 1 & value2) {
            if ((int) (value3 & 4294967295L) == value) {
                return (int) (value3 >>> 32);
            }
        }
        return -1;
    }

    public void put(int value, int value2) {
        if (this.size >= this.threshold) {
            this.resize(this.data.length << 1);
        }
        long[] values = this.data;
        int value3 = this.data.length - 1;
        int value4;
        long value5;
        for (value4 = this.hash(value) & value3;
                (value5 = values[value4]) != -1L;
                value4 = value4 + 1 & value3) {
            if ((int) (value5 & 4294967295L) == value) {
                values[value4] = value5 & 4294967295L | (value2 & 4294967295L) << 32;
                return;
            }
        }
        values[value4] = value & 4294967295L | (value2 & 4294967295L) << 32;
        this.size++;
    }

    public void putIfAbsent(int value, int value2) {
        if (this.size >= this.threshold) {
            this.resize(this.data.length << 1);
        }
        long[] values = this.data;
        int value3 = this.data.length - 1;
        int value4;
        long value5;
        for (value4 = this.hash(value) & value3;
                (value5 = values[value4]) != -1L;
                value4 = value4 + 1 & value3) {
            if ((int) (value5 & 4294967295L) == value) {
                return;
            }
        }
        values[value4] = value & 4294967295L | (value2 & 4294967295L) << 32;
        this.size++;
    }

    private void resize(int value) {
        long[] values = this.data;
        this.threshold = (int) (value * 0.75F);
        long[] values2;
        Arrays.fill(values2 = new long[value], -1L);
        this.data = values2;
        int value2 = value - 1;
        int value3 = 0;
        for (int value4 = values.length; value3 < value4; value3++) {
            long value5;
            if ((value5 = values[value3]) != -1L) {
                int value6 = (int) (value5 & 4294967295L);
                int value7 = this.hash(value6) & value2;
                while (values2[value7] != -1L) {
                    value7 = value7 + 1 & value2;
                }
                values2[value7] = value5;
            }
        }
    }

    private int hash(int value) {
        return ((value ^ value >>> 16) * -2048144789 ^ (value ^ value >>> 16) * -2048144789 >>> 13)
                        * -1028477387
                ^ ((value ^ value >>> 16) * -2048144789
                                        ^ (value ^ value >>> 16) * -2048144789 >>> 13)
                                * -1028477387
                        >>> 16;
    }
}
