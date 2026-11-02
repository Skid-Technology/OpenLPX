package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

@NullMarked
public class NBTCompound extends NBT {

    protected final Map<String, NBT> tags = new LinkedHashMap<>();

    @Override
    public NBTType<NBTCompound> getType() {
        return NBTType.COMPOUND;
    }

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    public boolean contains(String text) {
        return this.tags.containsKey(text);
    }

    public Set<String> getTagNames() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }

    public Map<String, NBT> getTags() {
        return Collections.unmodifiableMap(this.tags);
    }

    public int size() {
        return this.tags.size();
    }

    public NBT getTagOrThrow(String text) throws NbtCodecException {
        NBT nBT;
        if ((nBT = this.getTagOrNull(text)) == null) {
            throw new NbtCodecException("Tag " + text + " doesn't exist");
        } else {
            return nBT;
        }
    }

    @Nullable
    public NBT getTagOrNull(String text) {
        return this.tags.get(text);
    }

    public <T extends NBT> T getTagOfTypeOrThrow(String text, Class<T> classValue)
            throws NbtCodecException {
        NBT tagOrThrow = this.getTagOrThrow(text);
        if (classValue.isInstance(tagOrThrow)) {
            return (T) tagOrThrow;
        } else {
            throw new NbtCodecException(
                    MessageFormat.format(
                            "NBT {0} has unexpected type, expected {1}, but got {2}",
                            text, classValue, tagOrThrow.getClass()));
        }
    }

    @Nullable
    public <T extends NBT> T getTagOfTypeOrNull(String text, Class<T> classValue) {
        NBT tagOrNull = this.getTagOrNull(text);
        return (T) (classValue.isInstance(tagOrNull) ? tagOrNull : null);
    }

    public <T extends NBT> NBTList<T> getTagListOfTypeOrThrow(String text, Class<T> classValue)
            throws NbtCodecException {
        NBTList tagOfTypeOrThrow = this.getTagOfTypeOrThrow(text, NBTList.class);
        if (!classValue.isAssignableFrom(tagOfTypeOrThrow.getTagsType().getNBTClass())) {
            throw new NbtCodecException(
                    MessageFormat.format(
                            "NBTList {0} tags type has unexpected type, expected {1}, but got {2}",
                            text, classValue, tagOfTypeOrThrow.getTagsType().getNBTClass()));
        } else {
            return tagOfTypeOrThrow;
        }
    }

    @Nullable
    public <T extends NBT> NBTList<T> getTagListOfTypeOrNull(String text, Class<T> classValue) {
        NBTList nBTList;
        return (nBTList = this.getTagOfTypeOrNull(text, NBTList.class)) != null
                        && classValue.isAssignableFrom(nBTList.getTagsType().getNBTClass())
                ? nBTList
                : null;
    }

    public NBTCompound getCompoundTagOrThrow(String text) {
        return this.getTagOfTypeOrThrow(text, NBTCompound.class);
    }

    @Nullable
    public NBTCompound getCompoundTagOrNull(String text) {
        return this.getTagOfTypeOrNull(text, NBTCompound.class);
    }

    public Number getNumberTagValueOrThrow(String text) {
        return this.getNumberTagOrThrow(text).getAsNumber();
    }

    @Nullable
    public Number getNumberTagValueOrNull(String text) {
        return this.getNumberTagValueOrDefault(text, null);
    }

    @Contract("_, !null -> !null")
    @Nullable
    public Number getNumberTagValueOrDefault(String text, @Nullable Number number) {
        NBTNumber nBTNumber;
        return (nBTNumber = this.getNumberTagOrNull(text)) != null
                ? nBTNumber.getAsNumber()
                : number;
    }

    public NBTNumber getNumberTagOrThrow(String text) {
        return this.getTagOfTypeOrThrow(text, NBTNumber.class);
    }

    @Nullable
    public NBTNumber getNumberTagOrNull(String text) {
        return this.getTagOfTypeOrNull(text, NBTNumber.class);
    }

    public NBTString getStringTagOrThrow(String text) {
        return this.getTagOfTypeOrThrow(text, NBTString.class);
    }

    @Nullable
    public NBTString getStringTagOrNull(String text) {
        return this.getTagOfTypeOrNull(text, NBTString.class);
    }

    public NBTList<NBTCompound> getCompoundListTagOrThrow(String text) {
        return this.getTagListOfTypeOrThrow(text, NBTCompound.class);
    }

    @Nullable
    public NBTList<NBTCompound> getCompoundListTagOrNull(String text) {
        return this.getTagListOfTypeOrNull(text, NBTCompound.class);
    }

    public NBTList<NBTNumber> getNumberTagListTagOrThrow(String text) {
        return this.getTagListOfTypeOrThrow(text, NBTNumber.class);
    }

    @Nullable
    public NBTList<NBTNumber> getNumberListTagOrNull(String text) {
        return this.getTagListOfTypeOrNull(text, NBTNumber.class);
    }

    public NBTList<NBTString> getStringListTagOrThrow(String text) {
        return this.getTagListOfTypeOrThrow(text, NBTString.class);
    }

    @Nullable
    public NBTList<NBTString> getStringListTagOrNull(String text) {
        return this.getTagListOfTypeOrNull(text, NBTString.class);
    }

    public String getStringTagValueOrThrow(String text) {
        return this.getStringTagOrThrow(text).getValue();
    }

    @Nullable
    public String getStringTagValueOrNull(String text) {
        NBT nBT;
        return (nBT = this.getTagOrNull(text)) instanceof NBTString
                ? ((NBTString) nBT).getValue()
                : null;
    }

    public String getStringTagValueOrDefault(String text, String text2) {
        NBT nBT;
        return (nBT = this.getTagOrNull(text)) instanceof NBTString
                ? ((NBTString) nBT).getValue()
                : text2;
    }

    public NBT removeTag(String text) {
        return this.tags.remove(text);
    }

    @Nullable
    public <T extends NBT> T removeTagAndReturnIfType(String text, Class<T> classValue) {
        NBT nBT = this.removeTag(text);
        return (T) (classValue.isInstance(nBT) ? nBT : null);
    }

    @Nullable
    public <T extends NBT> NBTList<T> removeTagAndReturnIfListType(
            String text, Class<T> classValue) {
        NBTList nBTList;
        return (nBTList = this.removeTagAndReturnIfType(text, NBTList.class)) != null
                        && classValue.isAssignableFrom(nBTList.getTagsType().getNBTClass())
                ? nBTList
                : null;
    }

    public void setTag(String text, @Nullable NBT nBT) {
        if (nBT != null) {
            this.tags.put(text, nBT);
        } else {
            this.tags.remove(text);
        }
    }

    public NBTCompound copy() {
        NBTCompound nBTCompound = new NBTCompound();
        for (Entry<String, it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT>
                entry : this.tags.entrySet()) {
            nBTCompound.setTag((String) entry.getKey(), ((NBT) entry.getValue()).copy());
        }
        return nBTCompound;
    }

    public boolean getBoolean(String text) {
        return this.getBooleanOr(text, false);
    }

    public boolean getBooleanOr(String text, boolean matches) {
        NBTNumber nBTNumber;
        return (nBTNumber = this.getTagOfTypeOrNull(text, NBTNumber.class)) != null
                ? nBTNumber.getAsByte() != 0
                : matches;
    }

    public boolean getBooleanOrThrow(String text) {
        return this.getTagOfTypeOrThrow(text, NBTNumber.class).getAsByte() != 0;
    }

    @Contract("_, _, !null, _ -> !null")
    @Nullable
    public <T> T getOr(
            String text, NbtDecoder<T> nbtDecoder, @Nullable T t, PacketWrapper<?> packetWrapper) {
        NBT nBT;
        return (T)
                ((nBT = this.getTagOrNull(text)) != null
                        ? nbtDecoder.decode(nBT, packetWrapper)
                        : t);
    }

    @Nullable
    public <T> T getOrSupply(
            String text,
            NbtDecoder<T> nbtDecoder,
            Supplier<@Nullable T> supplier,
            PacketWrapper<?> packetWrapper) {
        NBT nBT;
        return (T)
                ((nBT = this.getTagOrNull(text)) != null
                        ? nbtDecoder.decode(nBT, packetWrapper)
                        : supplier.get());
    }

    @Nullable
    public <T> T getOrNull(String text, NbtDecoder<T> nbtDecoder, PacketWrapper<?> packetWrapper) {
        return this.getOr(text, nbtDecoder, null, packetWrapper);
    }

    public <T> T getOrThrow(String text, NbtDecoder<T> nbtDecoder, PacketWrapper<?> packetWrapper) {
        return (T) nbtDecoder.decode(this.getTagOrThrow(text), packetWrapper);
    }

    @Contract("_, _, !null, _ -> !null")
    @Nullable
    public <T> List<T> getListOr(
            String text,
            NbtDecoder<T> nbtDecoder,
            @Nullable List<T> values,
            PacketWrapper<?> packetWrapper) {
        NBT nBT;
        if (!((nBT = this.getTagOrNull(text)) instanceof NBTList)) {
            if (nBT != null) {
                ArrayList<T> values2;
                (values2 = new ArrayList(1)).add(nbtDecoder.decode(nBT, packetWrapper));
                return values2;
            } else {
                return values;
            }
        } else {
            List<NBT> values3 = ((NBTList) nBT).unwrapTags();
            ArrayList<T> values4 = new ArrayList<>(values3.size());
            for (NBT nBT2 : values3) {
                values4.add(nbtDecoder.decode(nBT2, packetWrapper));
            }
            return values4;
        }
    }

    @Nullable
    public <T> List<T> getListOrNull(
            String text, NbtDecoder<T> nbtDecoder, PacketWrapper<?> packetWrapper) {
        return this.getListOr(text, nbtDecoder, null, packetWrapper);
    }

    public <T> List<T> getListOrEmpty(
            String text, NbtDecoder<T> nbtDecoder, PacketWrapper<?> packetWrapper) {
        return this.getListOr(text, nbtDecoder, Collections.emptyList(), packetWrapper);
    }

    public <T> List<T> getListOrThrow(
            String text, NbtDecoder<T> nbtDecoder, PacketWrapper<?> packetWrapper) {
        List<T> values;
        if ((values = this.getListOrNull(text, nbtDecoder, packetWrapper)) == null) {
            throw new IllegalStateException(MessageFormat.format("NBT {0} does not exist", text));
        } else {
            return values;
        }
    }

    public <T> void set(
            String text, T t, NbtEncoder<T> nbtEncoder, PacketWrapper<?> packetWrapper) {
        this.setTag(text, nbtEncoder.encode(packetWrapper, t));
    }

    public <T> void setList(
            String text, List<T> values, NbtEncoder<T> nbtEncoder, PacketWrapper<?> packetWrapper) {
        if (values.isEmpty()) {
            this.setTag(text, new NBTList<>(NBTType.END, 0));
        } else {
            int size2 = values.size();
            ArrayList values2 = new ArrayList(size2);
            for (Object result : values) {
                values2.add(nbtEncoder.encode(packetWrapper, ((T) result)));
            }
            NBTList nBTList = new NBTList<>(NBTList.getCommonTagType(values2), size2);
            for (NBT nBT : (Iterable<NBT>) ((Iterable) values2)) {
                nBTList.addTagOrWrap(nBT);
            }
            this.setTag(text, nBTList);
        }
    }

    public <T> void setCompactList(
            String text, List<T> values, NbtEncoder<T> nbtEncoder, PacketWrapper<?> packetWrapper) {
        if (values.size() == 1) {
            this.set(text, values.get(0), nbtEncoder, packetWrapper);
        } else {
            this.setList(text, values, nbtEncoder, packetWrapper);
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result instanceof NBTCompound) {
            return this.isEmpty() && ((NBTCompound) result).isEmpty()
                    ? true
                    : this.tags.equals(((NBTCompound) result).tags);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.tags.hashCode();
    }

    @Override
    public String toString() {
        return "Compound{" + this.tags + "}";
    }
}
