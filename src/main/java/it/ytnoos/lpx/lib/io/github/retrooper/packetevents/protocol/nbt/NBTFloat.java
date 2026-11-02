package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTFloat extends NBTNumber {

    protected final float value;

    public NBTFloat(float value) {
        this.value = value;
    }

    @Override
    public NBTType<NBTFloat> getType() {
        return NBTType.FLOAT;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return (byte) (MathHelper.d(this.value) & 0xFF);
    }

    @Override
    public short getAsShort() {
        return (short) (MathHelper.d(this.value) & 65535);
    }

    @Override
    public int getAsInt() {
        return MathHelper.d(this.value);
    }

    @Override
    public long getAsLong() {
        return (long) this.value;
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
        NBTFloat nBTFloat = (NBTFloat) result;
        return Float.floatToIntBits(this.value) == Float.floatToIntBits(nBTFloat.value);
    }

    @Override
    public int hashCode() {
        return Float.hashCode(this.value);
    }

    public NBTFloat copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Float(" + this.value + ")";
    }
}
