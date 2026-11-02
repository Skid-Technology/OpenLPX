package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTLong extends NBTNumber {

    protected final long value;

    public NBTLong(long value) {
        this.value = value;
    }

    @Override
    public NBTType<NBTLong> getType() {
        return NBTType.LONG;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return (byte) (this.value & 255L);
    }

    @Override
    public short getAsShort() {
        return (short) (this.value & 65535L);
    }

    @Override
    public int getAsInt() {
        return (int) (this.value & -1L);
    }

    @Override
    public long getAsLong() {
        return this.value;
    }

    @Override
    public float getAsFloat() {
        return (float) this.value;
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
        NBTLong nBTLong = (NBTLong) result;
        return this.value == nBTLong.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }

    public NBTLong copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Long(" + this.value + ")";
    }
}
