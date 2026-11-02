package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import java.util.Arrays;

public class BinaryNBTCompound {

    private final byte[] data;

    private final int hash;

    public BinaryNBTCompound(byte[] data) {
        this.data = data;
        if (data[0] == 10 && data[data.length - 1] == 0) {
            this.hash = this.calculateHash(data);
        } else {
            throw new IllegalArgumentException("Data is not a valid NBT compound");
        }
    }

    public byte[] getData() {
        return this.data;
    }

    private int calculateHash(byte[] values) {
        return Arrays.hashCode(values);
    }

    @Override
    public boolean equals(Object result) {
        if (result instanceof BinaryNBTCompound) {
            byte[] values = ((BinaryNBTCompound) result).data;
            if (((BinaryNBTCompound) result).data.length != this.data.length) {
                return false;
            }
            for (int index = 1; index < this.data.length - 1; index++) {
                if (this.data[index] != values[index]) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.data) + " hash: " + this.hash;
    }
}
