package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Stream;

@NonExtendable
public interface CompoundBinaryTag
        extends BinaryTag,
                CompoundTagSetter<CompoundBinaryTag>,
                Iterable<Entry<String, ? extends BinaryTag>> {

    @NotNull
    static CompoundBinaryTag empty() {
        return CompoundBinaryTagImpl.EMPTY;
    }

    @NotNull
    static CompoundBinaryTag from(@NotNull Map<String, ? extends BinaryTag> entries) {
        return entries.isEmpty() ? empty() : new CompoundBinaryTagImpl(new HashMap<>(entries));
    }

    @NotNull
    static Collector<Entry<String, ? extends BinaryTag>, ?, CompoundBinaryTag> toCompoundTag() {
        return toCompoundTag(Entry::getKey, Entry::getValue);
    }

    @NotNull
    static <T> Collector<T, ?, CompoundBinaryTag> toCompoundTag(
            @NotNull Function<T, String> function,
            @NotNull Function<T, ? extends BinaryTag> function2) {
        Objects.requireNonNull(function, "keyLens");
        Objects.requireNonNull(function2, "valueLens");
        Supplier supplier = CompoundBinaryTag::builder;
        return Collector.of(
                supplier,
                (result, result2) ->
                        result.put(
                                (String) ((Function) function).apply(result2),
                                (BinaryTag) ((Function) function2).apply(result2)),
                (result3, result4) -> result3.put(result4.build()),
                CompoundBinaryTag.Builder::build,
                Characteristics.UNORDERED);
    }

    @NotNull
    static Collector<Entry<String, ? extends BinaryTag>, ?, CompoundBinaryTag> toCompoundTag(
            @NotNull CompoundBinaryTag compoundBinaryTag) {
        return toCompoundTag(compoundBinaryTag, Entry::getKey, Entry::getValue);
    }

    @NotNull
    static <T> Collector<T, ?, CompoundBinaryTag> toCompoundTag(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull Function<T, String> function,
            @NotNull Function<T, ? extends BinaryTag> function2) {
        Objects.requireNonNull(compoundBinaryTag, "initial");
        Objects.requireNonNull(function, "keyLens");
        Objects.requireNonNull(function2, "valueLens");
        Supplier supplier = () -> builder().put(compoundBinaryTag);
        return Collector.of(
                supplier,
                (result, result2) ->
                        result.put(
                                (String) ((Function) function).apply(result2),
                                (BinaryTag) ((Function) function2).apply(result2)),
                (result3, result4) -> result3.put(result4.build()),
                CompoundBinaryTag.Builder::build,
                Characteristics.UNORDERED);
    }

    @NotNull
    static CompoundBinaryTag.Builder builder() {
        return new CompoundTagBuilder();
    }

    @NotNull
    static CompoundBinaryTag.Builder builder(@Range(from = 0L, to = 2147483647L) int value) {
        return new CompoundTagBuilder(value);
    }

    @NotNull
    @Override
    default BinaryTagType<CompoundBinaryTag> type() {
        return BinaryTagTypes.COMPOUND;
    }

    boolean contains(@NotNull String text);

    boolean contains(@NotNull String text, @NotNull BinaryTagType<?> binaryTagType);

    @NotNull
    Set<String> keySet();

    @Nullable
    BinaryTag get(String text);

    int size();

    boolean isEmpty();

    default boolean getBoolean(@NotNull String text) {
        return this.getBoolean(text, false);
    }

    default boolean getBoolean(@NotNull String text, boolean matches) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(text)) instanceof ByteBinaryTag
                ? ((ByteBinaryTag) binaryTag).value() != 0
                : matches;
    }

    default byte getByte(@NotNull String text) {
        return this.getByte(text, (byte) 0);
    }

    byte getByte(@NotNull String text, byte value);

    default short getShort(@NotNull String text) {
        return this.getShort(text, (short) 0);
    }

    short getShort(@NotNull String text, short value);

    default int getInt(@NotNull String text) {
        return this.getInt(text, 0);
    }

    int getInt(@NotNull String text, int value);

    default long getLong(@NotNull String text) {
        return this.getLong(text, 0L);
    }

    long getLong(@NotNull String text, long value);

    default float getFloat(@NotNull String text) {
        return this.getFloat(text, 0.0F);
    }

    float getFloat(@NotNull String text, float value);

    default double getDouble(@NotNull String text) {
        return this.getDouble(text, 0.0);
    }

    double getDouble(@NotNull String text, double value);

    byte @NotNull [] getByteArray(@NotNull String text);

    @Contract("_, !null -> !null")
    byte @Nullable [] getByteArray(@NotNull String text, byte @Nullable [] values);

    @NotNull
    default String getString(@NotNull String text) {
        return this.getString(text, "");
    }

    @Contract("_, !null -> !null")
    @Nullable
    String getString(@NotNull String text, @Nullable String text2);

    @NotNull
    default ListBinaryTag getList(@NotNull String text) {
        return this.getList(text, ListBinaryTag.empty());
    }

    @Contract("_, !null -> !null")
    @Nullable
    ListBinaryTag getList(@NotNull String text, @Nullable ListBinaryTag listBinaryTag);

    @NotNull
    default ListBinaryTag getList(
            @NotNull String text, @NotNull BinaryTagType<? extends BinaryTag> binaryTagType) {
        return this.getList(text, binaryTagType, ListBinaryTag.empty());
    }

    @Contract("_, _, !null -> !null")
    @Nullable
    ListBinaryTag getList(
            @NotNull String text,
            @NotNull BinaryTagType<? extends BinaryTag> binaryTagType,
            @Nullable ListBinaryTag listBinaryTag);

    @NotNull
    default CompoundBinaryTag getCompound(@NotNull String text) {
        return this.getCompound(text, empty());
    }

    @Contract("_, !null -> !null")
    @Nullable
    CompoundBinaryTag getCompound(
            @NotNull String text, @Nullable CompoundBinaryTag compoundBinaryTag);

    int @NotNull [] getIntArray(@NotNull String text);

    @Contract("_, !null -> !null")
    int @Nullable [] getIntArray(@NotNull String text, int @Nullable [] values);

    long @NotNull [] getLongArray(@NotNull String text);

    @Contract("_, !null -> !null")
    long @Nullable [] getLongArray(@NotNull String text, long @Nullable [] values);

    Stream<Entry<String, ? extends BinaryTag>> stream();

    interface Builder extends CompoundTagSetter<CompoundBinaryTag.Builder> {

        @NotNull
        CompoundBinaryTag build();
    }
}
