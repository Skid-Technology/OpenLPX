package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

final class ListTagBuilder<T extends BinaryTag> implements ListBinaryTag.Builder<T> {

    @Nullable private List<BinaryTag> tags;

    private final boolean permitsHeterogeneity;

    private BinaryTagType<? extends BinaryTag> elementType;

    private final int initialCapacity;

    ListTagBuilder(boolean matches) {
        this(matches, BinaryTagTypes.END);
    }

    ListTagBuilder(boolean matches, int value) {
        this(matches, BinaryTagTypes.END, value);
    }

    ListTagBuilder(boolean matches, BinaryTagType<? extends BinaryTag> binaryTagType) {
        this(matches, binaryTagType, -1);
    }

    ListTagBuilder(
            boolean permitsHeterogeneity,
            BinaryTagType<? extends BinaryTag> binaryTagType,
            int initialCapacity) {
        this.permitsHeterogeneity = permitsHeterogeneity;
        this.elementType = binaryTagType;
        this.initialCapacity = initialCapacity;
    }

    public ListBinaryTag.@NotNull Builder<T> add(BinaryTag binaryTag) {
        this.elementType =
                (BinaryTagType<? extends BinaryTag>)
                        ListBinaryTagImpl.validateTagType(
                                binaryTag, this.elementType, this.permitsHeterogeneity);
        if (this.tags == null) {
            if (this.initialCapacity != -1) {
                if (this.initialCapacity < 0) {
                    throw new IllegalArgumentException(
                            "initialCapacity cannot be less than 0, was " + this.initialCapacity);
                }
                this.tags = new ArrayList<>(this.initialCapacity);
            } else {
                this.tags = new ArrayList<>();
            }
        }
        this.tags.add(binaryTag);
        return this;
    }

    public ListBinaryTag.@NotNull Builder<T> add(Iterable<? extends T> iterable) {
        for (BinaryTag binaryTag : iterable) {
            this.add(binaryTag);
        }
        return this;
    }

    @NotNull
    @Override
    public ListBinaryTag build() {
        return this.tags == null
                ? ListBinaryTag.empty()
                : new ListBinaryTagImpl(
                        this.elementType, this.permitsHeterogeneity, new ArrayList<>(this.tags));
    }
}
