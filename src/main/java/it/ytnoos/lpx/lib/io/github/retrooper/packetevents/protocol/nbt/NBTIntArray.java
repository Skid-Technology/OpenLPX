package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

@NullMarked
public class NBTIntArray extends NBT {

    protected final int[] array;

    public NBTIntArray(int[] array) {
        this.array = array;
    }

    @Override
    public NBTType<NBTIntArray> getType() {
        return NBTType.INT_ARRAY;
    }

    public int[] getValue() {
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
        NBTIntArray nBTIntArray = (NBTIntArray) result;
        return Arrays.equals(this.array, nBTIntArray.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    public NBTIntArray copy() {
        int[] values = new int[this.array.length];
        System.arraycopy(this.array, 0, values, 0, this.array.length);
        return new NBTIntArray(values);
    }

    @Override
    public String toString() {
        return "IntArray(" + Arrays.toString(this.array) + ")";
    }
}
