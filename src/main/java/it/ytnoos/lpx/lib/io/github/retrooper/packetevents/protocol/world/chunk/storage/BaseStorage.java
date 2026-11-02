package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.storage;

public abstract class BaseStorage {

    public abstract long[] getData();

    public abstract int getBitsPerEntry();

    abstract int getSize();

    public abstract int get(int value);

    public abstract void set(int value, int value2);
}
