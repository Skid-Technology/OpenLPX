package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTDouble extends NBTNumber {

    protected final double value;

    public NBTDouble(double value) {
        this.value = value;
    }

    @Override
    public NBTType<NBTDouble> getType() {
        return NBTType.DOUBLE;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return (byte) (MathHelper.floor(this.value) & 0xFF);
    }

    @Override
    public short getAsShort() {
        return (short) (MathHelper.floor(this.value) & 65535);
    }

    @Override
    public int getAsInt() {
        return MathHelper.floor(this.value);
    }

    @Override
    public long getAsLong() {
        return (long) Math.floor(this.value);
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
    public int hashCode() {
        return Double.hashCode(this.value);
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
        NBTDouble nBTDouble = (NBTDouble) result;
        return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(nBTDouble.value);
    }

    public NBTDouble copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Double(" + this.value + ")";
    }
}
