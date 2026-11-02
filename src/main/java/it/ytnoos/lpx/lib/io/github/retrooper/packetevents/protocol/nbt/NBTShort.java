package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTShort extends NBTNumber {

    protected final short value;

    public NBTShort(short value) {
        this.value = value;
    }

    @Override
    public NBTType<NBTShort> getType() {
        return NBTType.SHORT;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return (byte) (this.value & 255);
    }

    @Override
    public short getAsShort() {
        return this.value;
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
        NBTShort nBTShort = (NBTShort) result;
        return this.value == nBTShort.value;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(this.value);
    }

    public NBTShort copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Short(" + this.value + ")";
    }
}
