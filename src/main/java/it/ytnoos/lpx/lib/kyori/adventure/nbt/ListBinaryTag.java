package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

@NonExtendable
public interface ListBinaryTag
        extends ListTagSetter<ListBinaryTag, BinaryTag>, BinaryTag, Iterable<BinaryTag> {

    @NotNull
    static ListBinaryTag empty() {
        return ListBinaryTagImpl.EMPTY;
    }

    @NotNull
    static ListBinaryTag from(@NotNull Iterable<? extends BinaryTag> iterable) {
        return builder().add(iterable).build();
    }

    @NotNull
    static ListBinaryTag.Builder<BinaryTag> builder() {
        return new ListTagBuilder<>(false);
    }

    @NotNull
    static ListBinaryTag.Builder<BinaryTag> builder(@Range(from = 0L, to = 2147483647L) int value) {
        return new ListTagBuilder<>(false, value);
    }

    @NotNull
    static ListBinaryTag.Builder<BinaryTag> heterogeneousListBinaryTag() {
        return new ListTagBuilder<>(true);
    }

    @NotNull
    static ListBinaryTag.Builder<BinaryTag> heterogeneousListBinaryTag(
            @Range(from = 0L, to = 2147483647L) int value) {
        return new ListTagBuilder<>(true, value);
    }

    @NotNull
    static <T extends BinaryTag> ListBinaryTag.Builder<T> builder(
            @NotNull BinaryTagType<T> binaryTagType) {
        if (binaryTagType == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        } else {
            return new ListTagBuilder<>(false, binaryTagType);
        }
    }

    @NotNull
    static <T extends BinaryTag> ListBinaryTag.Builder<T> builder(
            @NotNull BinaryTagType<T> binaryTagType,
            @Range(from = 0L, to = 2147483647L) int value) {
        if (binaryTagType == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        } else {
            return new ListTagBuilder<>(false, binaryTagType, value);
        }
    }

    @NotNull
    static ListBinaryTag listBinaryTag(
            @NotNull BinaryTagType<? extends BinaryTag> binaryTagType,
            @NotNull List<BinaryTag> values) {
        if (values.isEmpty()) {
            return empty();
        }
        if (binaryTagType == BinaryTagTypes.END) {
            throw new IllegalArgumentException("Cannot create a list of " + BinaryTagTypes.END);
        }
        ListBinaryTagImpl.validateTagType(values, binaryTagType == BinaryTagTypes.LIST_WILDCARD);
        return new ListBinaryTagImpl(
                binaryTagType,
                binaryTagType == BinaryTagTypes.LIST_WILDCARD,
                new ArrayList<>(values));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static ListBinaryTag of(
            @NotNull BinaryTagType<? extends BinaryTag> binaryTagType,
            @NotNull List<BinaryTag> values) {
        return listBinaryTag(binaryTagType, values);
    }

    @NotNull
    static Collector<BinaryTag, ?, ListBinaryTag> toListTag() {
        return toListTag(null);
    }

    @NotNull
    static Collector<BinaryTag, ?, ListBinaryTag> toListTag(@Nullable ListBinaryTag listBinaryTag) {
        Supplier<ListBinaryTag.Builder<BinaryTag>> supplier;
        if (listBinaryTag == null) {
            supplier = ListBinaryTag::builder;
        } else {
            supplier = () -> builder().add((Iterable<? extends BinaryTag>) listBinaryTag);
        }
        return Collector.of(
                supplier,
                ListTagSetter::add,
                (result, result2) -> result.add((Iterable<? extends BinaryTag>) result2.build()),
                ListBinaryTag.Builder::build);
    }

    @NotNull
    @Override
    default BinaryTagType<ListBinaryTag> type() {
        return BinaryTagTypes.LIST;
    }

    @Deprecated
    @NotNull
    default BinaryTagType<? extends BinaryTag> listType() {
        return this.elementType();
    }

    @NotNull
    BinaryTagType<? extends BinaryTag> elementType();

    int size();

    boolean isEmpty();

    @NotNull
    BinaryTag get(@Range(from = 0L, to = 2147483647L) int value);

    @NotNull
    ListBinaryTag set(
            int value,
            @NotNull BinaryTag binaryTag,
            @Nullable Consumer<? super BinaryTag> consumer);

    @NotNull
    ListBinaryTag remove(int value, @Nullable Consumer<? super BinaryTag> consumer);

    default byte getByte(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getByte(value, (byte) 0);
    }

    default byte getByte(@Range(from = 0L, to = 2147483647L) int value, byte value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).byteValue()
                : value2;
    }

    default short getShort(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getShort(value, (short) 0);
    }

    default short getShort(@Range(from = 0L, to = 2147483647L) int value, short value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).shortValue()
                : value2;
    }

    default int getInt(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getInt(value, 0);
    }

    default int getInt(@Range(from = 0L, to = 2147483647L) int value, int value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).intValue()
                : value2;
    }

    default long getLong(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getLong(value, 0L);
    }

    default long getLong(@Range(from = 0L, to = 2147483647L) int value, long value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).longValue()
                : value2;
    }

    default float getFloat(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getFloat(value, 0.0F);
    }

    default float getFloat(@Range(from = 0L, to = 2147483647L) int value, float value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).floatValue()
                : value2;
    }

    default double getDouble(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getDouble(value, 0.0);
    }

    default double getDouble(@Range(from = 0L, to = 2147483647L) int value, double value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type().numeric()
                ? ((NumberBinaryTag) binaryTag).doubleValue()
                : value2;
    }

    default byte @NotNull [] getByteArray(@Range(from = 0L, to = 2147483647L) int value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.BYTE_ARRAY
                ? ((ByteArrayBinaryTag) binaryTag).value()
                : new byte[0];
    }

    @Contract("_, !null -> !null")
    default byte @Nullable [] getByteArray(
            @Range(from = 0L, to = 2147483647L) int value2, byte @Nullable [] values) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.BYTE_ARRAY
                ? ((ByteArrayBinaryTag) binaryTag).value()
                : values;
    }

    @NotNull
    default String getString(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getString(value, "");
    }

    @Contract("_, !null -> !null")
    @Nullable
    default String getString(
            @Range(from = 0L, to = 2147483647L) int value2, @Nullable String text) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.STRING
                ? ((StringBinaryTag) binaryTag).value()
                : text;
    }

    @NotNull
    default ListBinaryTag getList(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getList(value, null, empty());
    }

    @NotNull
    default ListBinaryTag getList(
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable BinaryTagType<?> binaryTagType) {
        return this.getList(value, binaryTagType, empty());
    }

    @Contract("_, !null -> !null")
    @Nullable
    default ListBinaryTag getList(
            @Range(from = 0L, to = 2147483647L) int value, @Nullable ListBinaryTag listBinaryTag) {
        return this.getList(value, null, listBinaryTag);
    }

    @Contract("_, _, !null -> !null")
    @Nullable
    default ListBinaryTag getList(
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable BinaryTagType<?> binaryTagType,
            @Nullable ListBinaryTag listBinaryTag) {
        BinaryTag binaryTag;
        if ((binaryTag = this.get(value)).type() == BinaryTagTypes.LIST) {
            ListBinaryTag listBinaryTag2 = (ListBinaryTag) binaryTag;
            if (binaryTagType == null || listBinaryTag2.elementType() == binaryTagType) {
                return listBinaryTag2;
            }
        }
        return listBinaryTag;
    }

    @NotNull
    default CompoundBinaryTag getCompound(@Range(from = 0L, to = 2147483647L) int value) {
        return this.getCompound(value, CompoundBinaryTag.empty());
    }

    @Contract("_, !null -> !null")
    @Nullable
    default CompoundBinaryTag getCompound(
            @Range(from = 0L, to = 2147483647L) int value,
            @Nullable CompoundBinaryTag compoundBinaryTag) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value)).type() == BinaryTagTypes.COMPOUND
                ? (CompoundBinaryTag) binaryTag
                : compoundBinaryTag;
    }

    default int @NotNull [] getIntArray(@Range(from = 0L, to = 2147483647L) int value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.INT_ARRAY
                ? ((IntArrayBinaryTag) binaryTag).value()
                : new int[0];
    }

    @Contract("_, !null -> !null")
    default int @Nullable [] getIntArray(
            @Range(from = 0L, to = 2147483647L) int value2, int @Nullable [] values) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.INT_ARRAY
                ? ((IntArrayBinaryTag) binaryTag).value()
                : values;
    }

    default long @NotNull [] getLongArray(@Range(from = 0L, to = 2147483647L) int value2) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.LONG_ARRAY
                ? ((LongArrayBinaryTag) binaryTag).value()
                : new long[0];
    }

    @Contract("_, !null -> !null")
    default long @Nullable [] getLongArray(
            @Range(from = 0L, to = 2147483647L) int value2, long @Nullable [] values) {
        BinaryTag binaryTag;
        return (binaryTag = this.get(value2)).type() == BinaryTagTypes.LONG_ARRAY
                ? ((LongArrayBinaryTag) binaryTag).value()
                : values;
    }

    @NotNull
    Stream<BinaryTag> stream();

    @NotNull
    ListBinaryTag unwrapHeterogeneity();

    @NotNull
    ListBinaryTag wrapHeterogeneity();

    @NonExtendable
    interface Builder<T extends BinaryTag> extends ListTagSetter<ListBinaryTag.Builder<T>, T> {

        @NotNull
        ListBinaryTag build();
    }
}
