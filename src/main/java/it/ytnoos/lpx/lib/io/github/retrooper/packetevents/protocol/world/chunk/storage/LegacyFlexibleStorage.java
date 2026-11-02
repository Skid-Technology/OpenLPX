package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage;

public class LegacyFlexibleStorage extends BaseStorage {

    private final long[] data;

    private final int bitsPerEntry;

    private final int size;

    private final long maxEntryValue;

    public LegacyFlexibleStorage(int value, int value2) {
        this(value, new long[roundToNearest(value2 * value, 64) / 64]);
    }

    public LegacyFlexibleStorage(int bitsPerEntry, long[] data) {
        if (bitsPerEntry < 4) {
            bitsPerEntry = 4;
        }
        this.bitsPerEntry = bitsPerEntry;
        this.data = data;
        this.size = this.data.length * 64 / this.bitsPerEntry;
        this.maxEntryValue = (1L << this.bitsPerEntry) - 1L;
    }

    private static int roundToNearest(int value, int value2) {
        if (value2 == 0) {
            return 0;
        }
        if (value == 0) {
            return value2;
        }
        if (value < 0) {
            value2 *= -1;
        }
        int value3;
        return (value3 = value % value2) != 0 ? value + value2 - value3 : value;
    }

    @Override
    public int get(int value) {
        int value2;
        int value3 = (value2 = value * this.bitsPerEntry) / 64;
        int value4 = ((value + 1) * this.bitsPerEntry - 1) / 64;
        int value5 = value2 % 64;
        if (value3 == value4) {
            return (int) (this.data[value3] >>> value5 & this.maxEntryValue);
        }
        int value6 = 64 - value5;
        return (int)
                ((this.data[value3] >>> value5 | this.data[value4] << value6) & this.maxEntryValue);
    }

    @Override
    public void set(int value, int value2) {
        int value3;
        int value4 = (value3 = value * this.bitsPerEntry) / 64;
        int value5 = ((value + 1) * this.bitsPerEntry - 1) / 64;
        int value6 = value3 % 64;
        this.data[value4] =
                this.data[value4] & ~(this.maxEntryValue << value6)
                        | (value2 & this.maxEntryValue) << value6;
        if (value4 != value5) {
            int value7 = 64 - value6;
            int value8 = this.bitsPerEntry - value7;
            this.data[value5] =
                    this.data[value5] >>> value8 << value8
                            | (value2 & this.maxEntryValue) >> value7;
        }
    }

    @Override
    public long[] getData() {
        return this.data;
    }

    @Override
    public int getBitsPerEntry() {
        return this.bitsPerEntry;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public long getMaxEntryValue() {
        return this.maxEntryValue;
    }
}
