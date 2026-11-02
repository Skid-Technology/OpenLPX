package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Debug.Renderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Renderer(
        text = "\"ListBinaryTag[type=\" + this.type.toString() + \"]\"",
        childrenArray = "this.tags.toArray()",
        hasChildren = "!this.tags.isEmpty()")
final class ListBinaryTagImpl extends AbstractBinaryTag implements ListBinaryTag {

    static final ListBinaryTag EMPTY =
            new ListBinaryTagImpl(BinaryTagTypes.END, false, Collections.emptyList());

    private final List<BinaryTag> tags;

    private final boolean permitsHeterogeneity;

    private final BinaryTagType<? extends BinaryTag> elementType;

    private final int hashCode;

    ListBinaryTagImpl(
            BinaryTagType<? extends BinaryTag> binaryTagType,
            boolean permitsHeterogeneity,
            List<BinaryTag> values) {
        this.tags = Collections.unmodifiableList(values);
        this.permitsHeterogeneity = permitsHeterogeneity;
        this.elementType = binaryTagType;
        this.hashCode = values.hashCode();
    }

    @NotNull
    @Override
    public BinaryTagType<? extends BinaryTag> elementType() {
        return this.elementType;
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
    @Override
    public BinaryTag get(@Range(from = 0L, to = 2147483647L) int value) {
        return this.tags.get(value);
    }

    @NotNull
    @Override
    public ListBinaryTag set(
            int value,
            @NotNull BinaryTag binaryTag,
            @Nullable Consumer<? super BinaryTag> consumer) {
        BinaryTagType binaryTagType =
                validateTagType(binaryTag, this.elementType, this.permitsHeterogeneity);
        return this.edit(
                result -> {
                    BinaryTag binaryTag2 = result.set(value, binaryTag);
                    if (((Consumer) consumer) != null) {
                        ((Consumer) consumer).accept(binaryTag2);
                    }
                },
                binaryTagType);
    }

    @NotNull
    @Override
    public ListBinaryTag remove(int value, @Nullable Consumer<? super BinaryTag> consumer) {
        return this.edit(
                result -> {
                    BinaryTag binaryTag = result.remove(value);
                    if (((Consumer) consumer) != null) {
                        ((Consumer) consumer).accept(binaryTag);
                    }
                },
                null);
    }

    @NotNull
    public ListBinaryTag add(BinaryTag binaryTag) {
        BinaryTagType binaryTagType =
                validateTagType(binaryTag, this.elementType, this.permitsHeterogeneity);
        return this.edit(result -> result.add(binaryTag), binaryTagType);
    }

    @NotNull
    public ListBinaryTag add(Iterable<? extends BinaryTag> iterable) {
        if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
            return this;
        }
        BinaryTagType binaryTagType = validateTagType(iterable, this.permitsHeterogeneity);
        return this.edit(
                result -> {
                    for (BinaryTag binaryTag :
                            (Iterable<BinaryTag>) ((Iterable) ((Iterable) iterable))) {
                        result.add(binaryTag);
                    }
                },
                binaryTagType);
    }

    static void noAddEnd(BinaryTag binaryTag) {
        if (binaryTag.type() == BinaryTagTypes.END) {
            throw new IllegalArgumentException(
                    String.format(
                            "Cannot add a %s to a %s", BinaryTagTypes.END, BinaryTagTypes.LIST));
        }
    }

    static BinaryTagType<?> validateTagType(
            Iterable<? extends BinaryTag> iterable, boolean matches) {
        BinaryTagType binaryTagType = null;
        for (BinaryTag binaryTag : iterable) {
            if (binaryTagType == null) {
                noAddEnd(binaryTag);
                binaryTagType = binaryTag.type();
            } else {
                validateTagType(binaryTag, binaryTagType, matches);
                if (binaryTagType != binaryTag.type()) {
                    binaryTagType = BinaryTagTypes.LIST_WILDCARD;
                }
            }
        }
        return binaryTagType;
    }

    static BinaryTagType<?> validateTagType(
            BinaryTag binaryTag,
            BinaryTagType<? extends BinaryTag> binaryTagType,
            boolean matches) {
        noAddEnd(binaryTag);
        if (binaryTagType == BinaryTagTypes.END) {
            return binaryTag.type();
        } else if (binaryTag.type() != binaryTagType && !matches) {
            throw new IllegalArgumentException(
                    String.format(
                            "Trying to add tag of type %s to list of %s",
                            binaryTag.type(), binaryTagType));
        } else {
            return binaryTag.type() != binaryTagType ? BinaryTagTypes.LIST_WILDCARD : binaryTagType;
        }
    }

    private ListBinaryTag edit(
            Consumer<List<BinaryTag>> consumer,
            @Nullable BinaryTagType<? extends BinaryTag> binaryTagType) {
        ArrayList values = new ArrayList<>(this.tags);
        consumer.accept(values);
        BinaryTagType binaryTagType2 = this.elementType;
        if (binaryTagType != null) {
            binaryTagType2 = binaryTagType;
        }
        return new ListBinaryTagImpl(
                binaryTagType2, this.permitsHeterogeneity, new ArrayList<>(values));
    }

    @NotNull
    @Override
    public Stream<BinaryTag> stream() {
        return this.tags.stream();
    }

    @NotNull
    @Override
    public ListBinaryTag unwrapHeterogeneity() {
        if (this.permitsHeterogeneity) {
            return this;
        }
        if (this.elementType != BinaryTagTypes.COMPOUND) {
            return new ListBinaryTagImpl(this.elementType, true, this.tags);
        }
        ArrayList<it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTag> values = null;
        ListIterator listIterator2 = this.tags.listIterator();
        while (listIterator2.hasNext()) {
            BinaryTag binaryTag;
            BinaryTag binaryTag2;
            if ((binaryTag2 =
                                    ListBinaryTag0.unbox(
                                            (CompoundBinaryTag)
                                                    (binaryTag = (BinaryTag) listIterator2.next())))
                            != binaryTag
                    && values == null) {
                values = new ArrayList(this.tags.size());
                int value = listIterator2.nextIndex() - 1;
                for (int index = 0; index < value; index++) {
                    values.add(this.tags.get(index));
                }
            }
            if (values != null) {
                values.add(binaryTag2);
            }
        }
        return new ListBinaryTagImpl(
                values == null ? BinaryTagTypes.COMPOUND : BinaryTagTypes.LIST_WILDCARD,
                true,
                values == null ? this.tags : values);
    }

    @NotNull
    @Override
    public ListBinaryTag wrapHeterogeneity() {
        if (this.elementType != BinaryTagTypes.LIST_WILDCARD) {
            return this;
        }
        ArrayList<BinaryTag> values = new ArrayList<>(this.tags.size());
        for (BinaryTag binaryTag : this.tags) {
            values.add(ListBinaryTag0.box(binaryTag));
        }
        return new ListBinaryTagImpl(BinaryTagTypes.COMPOUND, false, values);
    }

    @NotNull
    @Override
    public Iterator<BinaryTag> iterator() {
        final Iterator<it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTag> iterator2 =
                this.tags.iterator();
        return new Iterator<BinaryTag>() {

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            public BinaryTag next() {
                return (BinaryTag) iterator2.next();
            }

            @Override
            public void forEachRemaining(Consumer<? super BinaryTag> consumer) {
                iterator2.forEachRemaining(consumer);
            }
        };
    }

    @Override
    public void forEach(Consumer<? super BinaryTag> consumer) {
        this.tags.forEach(consumer);
    }

    @Override
    public Spliterator<BinaryTag> spliterator() {
        return Spliterators.spliterator(this.tags, 1040);
    }

    @Override
    public boolean equals(Object result) {
        return this == result
                || result instanceof ListBinaryTagImpl
                        && this.tags.equals(((ListBinaryTagImpl) result).tags);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("tags", this.tags),
                ExaminableProperty.of("type", this.elementType));
    }
}
