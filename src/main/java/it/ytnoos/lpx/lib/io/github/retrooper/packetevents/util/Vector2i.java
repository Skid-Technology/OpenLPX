package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public final class Vector2i {

    private final int x;

    private final int z;

    public Vector2i(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public static Vector2i read(PacketWrapper<?> packetWrapper) {
        return fromLong(packetWrapper.readLong());
    }

    public static void write(PacketWrapper<?> packetWrapper, Vector2i vector2i) {
        packetWrapper.writeLong(vector2i.asLong());
    }

    public static Vector2i fromLong(long value) {
        return new Vector2i((int) value, (int) (value >> 32));
    }

    public long asLong() {
        return this.x & 4294967295L | (this.z & 4294967295L) << 32;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof Vector2i)) {
            return false;
        }
        Vector2i vector2i = (Vector2i) result;
        return this.x != vector2i.x ? false : this.z == vector2i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.z);
    }

    @Override
    public String toString() {
        return "Vector2i[" + this.x + "," + this.z + "]";
    }
}
