package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class NBTType<T extends NBT> {

    public static final NBTType<NBTEnd> END = new NBTType<>(NBTEnd.class);

    public static final NBTType<NBTByte> BYTE = new NBTType<>(NBTByte.class);

    public static final NBTType<NBTShort> SHORT = new NBTType<>(NBTShort.class);

    public static final NBTType<NBTInt> INT = new NBTType<>(NBTInt.class);

    public static final NBTType<NBTLong> LONG = new NBTType<>(NBTLong.class);

    public static final NBTType<NBTFloat> FLOAT = new NBTType<>(NBTFloat.class);

    public static final NBTType<NBTDouble> DOUBLE = new NBTType<>(NBTDouble.class);

    public static final NBTType<NBTByteArray> BYTE_ARRAY = new NBTType<>(NBTByteArray.class);

    public static final NBTType<NBTString> STRING = new NBTType<>(NBTString.class);

    public static final NBTType<NBTList> LIST = new NBTType<>(NBTList.class);

    public static final NBTType<NBTCompound> COMPOUND = new NBTType<>(NBTCompound.class);

    public static final NBTType<NBTIntArray> INT_ARRAY = new NBTType<>(NBTIntArray.class);

    public static final NBTType<NBTLongArray> LONG_ARRAY = new NBTType<>(NBTLongArray.class);

    protected final Class<T> clazz;

    protected NBTType(Class<T> classValue) {
        this.clazz = classValue;
    }

    public Class<T> getNBTClass() {
        return this.clazz;
    }

    @Override
    public String toString() {
        return this.clazz.getSimpleName();
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
        NBTType nBTType = (NBTType) result;
        return Objects.equals(this.clazz, nBTType.clazz);
    }

    @Override
    public int hashCode() {
        return this.clazz.hashCode();
    }
}
