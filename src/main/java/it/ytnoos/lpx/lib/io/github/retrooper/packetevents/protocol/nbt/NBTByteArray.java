package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;

@NullMarked
public class NBTByteArray extends NBT {

    protected final byte[] array;

    public NBTByteArray(byte[] array) {
        this.array = array;
    }

    @Override
    public NBTType<NBTByteArray> getType() {
        return NBTType.BYTE_ARRAY;
    }

    public byte[] getValue() {
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
        NBTByteArray nBTByteArray = (NBTByteArray) result;
        return Arrays.equals(this.array, nBTByteArray.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    public NBTByteArray copy() {
        byte[] values = new byte[this.array.length];
        System.arraycopy(this.array, 0, values, 0, this.array.length);
        return new NBTByteArray(values);
    }

    @Override
    public String toString() {
        return "ByteArray(" + Arrays.toString(this.array) + ")";
    }
}
