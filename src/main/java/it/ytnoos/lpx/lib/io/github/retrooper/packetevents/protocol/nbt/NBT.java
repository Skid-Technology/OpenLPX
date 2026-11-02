package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class NBT {

    private int decodedSizeBytes = -1;

    public abstract NBTType<?> getType();

    public int getDecodedSizeBytes() {
        return this.decodedSizeBytes;
    }

    public void setDecodedSizeBytes(int decodedSizeBytes) {
        this.decodedSizeBytes = decodedSizeBytes;
    }

    @Override
    public abstract boolean equals(@Nullable Object result);

    @Override
    public abstract int hashCode();

    @Override
    public String toString() {
        return "nbt";
    }

    public abstract NBT copy();

    public <T> T castOrThrow(Class<T> classValue) throws NbtCodecException {
        if (classValue.isInstance(this)) {
            return (T) this;
        } else {
            throw new NbtCodecException("expected: " + classValue.getName() + ", actual: " + this);
        }
    }
}
