package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@NullMarked
public class NBTList<T extends NBT> extends NBT {

    protected final NBTType<T> type;

    protected final List<T> tags;

    public NBTList(NBTType<T> nBTType) {
        this.type = nBTType;
        this.tags = new ArrayList<>();
    }

    public NBTList(NBTType<T> nBTType, int value) {
        this.type = nBTType;
        this.tags = new ArrayList<>(value);
    }

    public NBTList(NBTType<T> nBTType, List<T> values) {
        this.type = nBTType;
        this.tags = new ArrayList<>();
        this.tags.addAll(values);
    }

    public static NBTList<NBTCompound> createCompoundList() {
        return new NBTList<>(NBTType.COMPOUND);
    }

    public static NBTList<NBTString> createStringList() {
        return new NBTList<>(NBTType.STRING);
    }

    public static NBTType<?> getCommonTagType(List<? extends NBT> values) {
        NBTType nBTType = NBTType.END;
        for (NBT nBT : values) {
            if (nBTType == NBTType.END) {
                nBTType = nBT.getType();
            } else if (nBTType != nBT.getType()) {
                return NBTType.COMPOUND;
            }
        }
        return nBTType;
    }

    @Override
    public NBTType<NBTList> getType() {
        return NBTType.LIST;
    }

    public NBTType<T> getTagsType() {
        return this.type;
    }

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    public int size() {
        return this.tags.size();
    }

    public List<T> getTags() {
        return Collections.unmodifiableList(this.tags);
    }

    public T getTag(int value) {
        return this.tags.get(value);
    }

    public void setTag(int value, T t) {
        this.validateAddTag((T) t);
        this.tags.set(value, (T) t);
    }

    public void addTag(int value, T t) {
        this.validateAddTag((T) t);
        this.tags.add(value, (T) t);
    }

    public void addTag(T t) {
        this.validateAddTag((T) t);
        this.tags.add((T) t);
    }

    public void addTagUnsafe(int value, NBT nBT) {
        this.addTag(value, (T) nBT);
    }

    public void addTagUnsafe(NBT nBT) {
        this.addTag((T) nBT);
    }

    public void removeTag(int value) {
        this.tags.remove(value);
    }

    protected void validateAddTag(T t) {
        if (this.type != t.getType()) {
            throw new IllegalArgumentException(
                    MessageFormat.format(
                            "Invalid tag type. Expected {0}, got {1}.",
                            this.type.getNBTClass(), t.getClass()));
        }
    }

    public void addTagOrWrap(NBT nBT) {
        if (this.type == nBT.getType()) {
            this.tags.add((T) nBT);
        } else {
            if (this.type != NBTType.COMPOUND) {
                throw new IllegalArgumentException(
                        "Can't add or wrap tag " + nBT + " to list of type " + this.type);
            }
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound()).setTag("", nBT);
            this.tags.add((T) nBTCompound);
        }
    }

    private static NBT tryUnwrap(NBTCompound nBTCompound) {
        NBT nBT;
        return nBTCompound.tags.size() == 1 && (nBT = nBTCompound.getTagOrNull("")) != null
                ? nBT
                : nBTCompound;
    }

    public List<? extends NBT> unwrapTags() {
        if (this.type != NBTType.COMPOUND) {
            return new ArrayList<>(this.tags);
        }
        ArrayList<NBT> values = new ArrayList<>(this.tags.size());
        Iterator<T> iterator2 = this.tags.iterator();
        while (iterator2.hasNext()) {
            NBT nBT;
            if ((nBT = (NBT) iterator2.next()) instanceof NBTCompound) {
                values.add(tryUnwrap((NBTCompound) nBT));
            } else {
                values.add(nBT);
            }
        }
        return values;
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
        NBTList nBTList = (NBTList) result;
        return Objects.equals(this.type, nBTList.type) && Objects.equals(this.tags, nBTList.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.tags);
    }

    public NBTList<T> copy() {
        ArrayList<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT> values =
                new ArrayList<>();
        for (NBT nBT : this.tags) {
            values.add(nBT.copy());
        }
        return new NBTList<>(this.type, (List<T>) (List<?>) values);
    }

    @Override
    public String toString() {
        return "List(" + this.tags + ")";
    }
}
