package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTInt extends NBTNumber {

    protected final int value;

    public NBTInt(int value) {
        this.value = value;
    }

    @Override
    public NBTType<NBTInt> getType() {
        return NBTType.INT;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return (byte) (this.value & 0xFF);
    }

    @Override
    public short getAsShort() {
        return (short) (this.value & 65535);
    }

    @Override
    public int getAsInt() {
        return this.value;
    }

    @Override
    public long getAsLong() {
        return this.value;
    }

    @Override
    public float getAsFloat() {
        return this.value;
    }

    @Override
    public double getAsDouble() {
        return this.value;
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
        NBTInt nBTInt = (NBTInt) result;
        return this.value == nBTInt.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.value);
    }

    public NBTInt copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Int(" + this.value + ")";
    }
}
