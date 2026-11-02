package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Renderer(
        text = "\"CompoundBinaryTag[length=\" + this.tags.size() + \"]\"",
        childrenArray = "this.tags.entrySet().toArray()",
        hasChildren = "!this.tags.isEmpty()")
final class CompoundBinaryTagImpl extends AbstractBinaryTag implements CompoundBinaryTag {

    static final CompoundBinaryTag EMPTY = new CompoundBinaryTagImpl(Collections.emptyMap());

    private final Map<String, BinaryTag> tags;

    private final int hashCode;

    CompoundBinaryTagImpl(Map<String, BinaryTag> entries) {
        this.tags = Collections.unmodifiableMap(entries);
        this.hashCode = entries.hashCode();
    }

    @Override
    public boolean contains(@NotNull String text) {
        return this.tags.containsKey(text);
    }

    @Override
    public boolean contains(@NotNull String text, @NotNull BinaryTagType<?> binaryTagType) {
        BinaryTag binaryTag;
        return (binaryTag = this.tags.get(Objects.requireNonNull(text, "key"))) != null
                && Objects.requireNonNull(binaryTagType, "type").test(binaryTag.type());
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(this.tags.keySet());
    }

    @Nullable
    @Override
    public BinaryTag get(String text) {
        return this.tags.get(text);
    }

    @Override
    public int size() {
        return this.tags.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    @NotNull
    public CompoundBinaryTag put(@NotNull String text, @NotNull BinaryTag binaryTag) {
        return this.edit(result -> result.put(text, binaryTag));
    }

    @NotNull
    public CompoundBinaryTag put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        return this.edit(
                result -> {
                    for (String text : compoundBinaryTag.keySet()) {
                        result.put(text, compoundBinaryTag.get(text));
                    }
                });
    }

    @NotNull
    public CompoundBinaryTag put(@NotNull Map<String, ? extends BinaryTag> entries) {
        return this.edit(result -> result.putAll(((Map) entries)));
    }

    @NotNull
    public CompoundBinaryTag remove(
            @NotNull String text, @Nullable Consumer<? super BinaryTag> consumer) {
        if (!this.tags.containsKey(text)) {
            return this;
        }
        return this.edit(
                result -> {
                    BinaryTag binaryTag = result.remove(text);
                    if (((Consumer) consumer) != null) {
                        ((Consumer) consumer).accept(binaryTag);
                    }
                });
    }

    @Override
    public byte getByte(@NotNull String text, byte value) {
        return this.contains(text, BinaryTagTypes.BYTE)
                ? ((NumberBinaryTag) this.tags.get(text)).byteValue()
                : value;
    }

    @Override
    public short getShort(@NotNull String text, short value) {
        return this.contains(text, BinaryTagTypes.SHORT)
                ? ((NumberBinaryTag) this.tags.get(text)).shortValue()
                : value;
    }

    @Override
    public int getInt(@NotNull String text, int value) {
        return this.contains(text, BinaryTagTypes.INT)
                ? ((NumberBinaryTag) this.tags.get(text)).intValue()
                : value;
    }

    @Override
    public long getLong(@NotNull String text, long value) {
        return this.contains(text, BinaryTagTypes.LONG)
                ? ((NumberBinaryTag) this.tags.get(text)).longValue()
                : value;
    }

    @Override
    public float getFloat(@NotNull String text, float value) {
        return this.contains(text, BinaryTagTypes.FLOAT)
                ? ((NumberBinaryTag) this.tags.get(text)).floatValue()
                : value;
    }

    @Override
    public double getDouble(@NotNull String text, double value) {
        return this.contains(text, BinaryTagTypes.DOUBLE)
                ? ((NumberBinaryTag) this.tags.get(text)).doubleValue()
                : value;
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String text) {
        return this.contains(text, BinaryTagTypes.BYTE_ARRAY)
                ? ((ByteArrayBinaryTag) this.tags.get(text)).value()
                : new byte[0];
    }

    @Override
    public byte @NotNull [] getByteArray(@NotNull String text, byte @NotNull [] values) {
        return this.contains(text, BinaryTagTypes.BYTE_ARRAY)
                ? ((ByteArrayBinaryTag) this.tags.get(text)).value()
                : values;
    }

    @NotNull
    @Override
    public String getString(@NotNull String text, @NotNull String text2) {
        return this.contains(text, BinaryTagTypes.STRING)
                ? ((StringBinaryTag) this.tags.get(text)).value()
                : text2;
    }

    @NotNull
    @Override
    public ListBinaryTag getList(@NotNull String text, @NotNull ListBinaryTag listBinaryTag) {
        return this.contains(text, BinaryTagTypes.LIST)
                ? (ListBinaryTag) this.tags.get(text)
                : listBinaryTag;
    }

    @NotNull
    @Override
    public ListBinaryTag getList(
            @NotNull String text,
            @NotNull BinaryTagType<? extends BinaryTag> binaryTagType,
            @NotNull ListBinaryTag listBinaryTag) {
        if (this.contains(text, BinaryTagTypes.LIST)) {
            ListBinaryTag listBinaryTag2 = (ListBinaryTag) this.tags.get(text);
            if (binaryTagType.test(listBinaryTag2.elementType())) {
                return listBinaryTag2;
            }
        }
        return listBinaryTag;
    }

    @NotNull
    @Override
    public CompoundBinaryTag getCompound(
            @NotNull String text, @NotNull CompoundBinaryTag compoundBinaryTag) {
        return this.contains(text, BinaryTagTypes.COMPOUND)
                ? (CompoundBinaryTag) this.tags.get(text)
                : compoundBinaryTag;
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String text) {
        return this.contains(text, BinaryTagTypes.INT_ARRAY)
                ? ((IntArrayBinaryTag) this.tags.get(text)).value()
                : new int[0];
    }

    @Override
    public int @NotNull [] getIntArray(@NotNull String text, int @NotNull [] values) {
        return this.contains(text, BinaryTagTypes.INT_ARRAY)
                ? ((IntArrayBinaryTag) this.tags.get(text)).value()
                : values;
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String text) {
        return this.contains(text, BinaryTagTypes.LONG_ARRAY)
                ? ((LongArrayBinaryTag) this.tags.get(text)).value()
                : new long[0];
    }

    @Override
    public long @NotNull [] getLongArray(@NotNull String text, long @NotNull [] values) {
        return this.contains(text, BinaryTagTypes.LONG_ARRAY)
                ? ((LongArrayBinaryTag) this.tags.get(text)).value()
                : values;
    }

    @Override
    public Stream<Entry<String, ? extends BinaryTag>> stream() {
        return (Stream) this.tags.entrySet().stream();
    }

    private CompoundBinaryTag edit(Consumer<Map<String, BinaryTag>> consumer) {
        HashMap entries = new HashMap<>(this.tags);
        consumer.accept(entries);
        return new CompoundBinaryTagImpl(new HashMap<>(entries));
    }

    @Override
    public boolean equals(Object result) {
        return this == result
                || result instanceof CompoundBinaryTagImpl
                        && this.tags.equals(((CompoundBinaryTagImpl) result).tags);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("tags", this.tags));
    }

    @NotNull
    @Override
    public Iterator<Entry<String, ? extends BinaryTag>> iterator() {
        return (Iterator) this.tags.entrySet().iterator();
    }

    @Override
    public void forEach(@NotNull Consumer<? super Entry<String, ? extends BinaryTag>> consumer) {
        this.tags.entrySet().forEach(Objects.requireNonNull(consumer, "action"));
    }
}
