package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketLimitException;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class NBTElementCounter {

    private final int max;

    private int count;

    public NBTElementCounter(int max) {
        this.max = max;
    }

    public void add(int value) {
        if (value < 0) {
            throw new IllegalArgumentException(
                    "Can't count a negative amount of NBT elements: " + value);
        }
        long value2;
        if ((value2 = (long) this.count + value) > this.max) {
            throw new PacketLimitException(
                    PacketLimitException.Limit.NBT_ELEMENTS, value2, this.max);
        }
        this.count = (int) value2;
    }
}
