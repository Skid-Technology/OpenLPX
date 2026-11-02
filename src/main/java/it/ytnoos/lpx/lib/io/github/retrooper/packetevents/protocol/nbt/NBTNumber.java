package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class NBTNumber extends NBT {

    public abstract Number getAsNumber();

    public abstract byte getAsByte();

    public abstract short getAsShort();

    public abstract int getAsInt();

    public abstract long getAsLong();

    public abstract float getAsFloat();

    public abstract double getAsDouble();
}
