package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

@NullMarked
public class NBTLongArray extends NBT {

    protected final long[] array;

    public NBTLongArray(long[] array) {
        this.array = array;
    }

    @Override
    public NBTType<NBTLongArray> getType() {
        return NBTType.LONG_ARRAY;
    }

    public long[] getValue() {
        return this.array;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (result == null) {
            return false;
        }
        if (this.getClass() != result.getClass()) {
            return false;
        }
        NBTLongArray nBTLongArray = (NBTLongArray) result;
        return Arrays.equals(this.array, nBTLongArray.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    public NBTLongArray copy() {
        long[] values = new long[this.array.length];
        System.arraycopy(this.array, 0, values, 0, this.array.length);
        return new NBTLongArray(values);
    }

    @Override
    public String toString() {
        return "LongArray(" + Arrays.toString(this.array) + ")";
    }
}
