package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NBTByte extends NBTNumber {

    protected final byte value;

    public NBTByte(byte value) {
        this.value = value;
    }

    public NBTByte(boolean matches) {
        this((byte) (matches ? 1 : 0));
    }

    public boolean getAsBool() {
        return this.value != 0;
    }

    @Override
    public NBTType<NBTByte> getType() {
        return NBTType.BYTE;
    }

    @Override
    public Number getAsNumber() {
        return this.value;
    }

    @Override
    public byte getAsByte() {
        return this.value;
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
    public int hashCode() {
        return Byte.hashCode(this.value);
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
        NBTByte nBTByte = (NBTByte) result;
        return this.value == nBTByte.value;
    }

    public NBTByte copy() {
        return this;
    }

    @Override
    public String toString() {
        return "Byte(" + this.value + ")";
    }
}
