package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class NBTString extends NBT {

    protected String string;

    public NBTString(String string) {
        this.string = string;
    }

    @Override
    public NBTType<NBTString> getType() {
        return NBTType.STRING;
    }

    public String getValue() {
        return this.string;
    }

    public void setValue(String string) {
        this.string = string;
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
        NBTString nBTString = (NBTString) result;
        return Objects.equals(this.string, nBTString.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.string);
    }

    public NBTString copy() {
        return new NBTString(this.string);
    }

    @Override
    public String toString() {
        return "String(" + this.string + ")";
    }
}
