package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class CompoundTagBuilder implements CompoundBinaryTag.Builder {

    @Nullable private Map<String, BinaryTag> tags;

    private final int initialCapacity;

    CompoundTagBuilder() {
        this(-1);
    }

    CompoundTagBuilder(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    private Map<String, BinaryTag> tags() {
        if (this.tags == null) {
            if (this.initialCapacity != -1) {
                if (this.initialCapacity < 0) {
                    throw new IllegalArgumentException(
                            "initialCapacity cannot be less than 0, was " + this.initialCapacity);
                }
                this.tags = new HashMap<>(this.initialCapacity);
            } else {
                this.tags = new HashMap<>();
            }
        }
        return this.tags;
    }

    public CompoundBinaryTag.@NotNull Builder put(
            @NotNull String text, @NotNull BinaryTag binaryTag) {
        this.tags().put(text, binaryTag);
        return this;
    }

    public CompoundBinaryTag.@NotNull Builder put(@NotNull CompoundBinaryTag compoundBinaryTag) {
        Map<String, it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTag> entries = this.tags();
        for (String text : compoundBinaryTag.keySet()) {
            entries.put(text, compoundBinaryTag.get(text));
        }
        return this;
    }

    public CompoundBinaryTag.@NotNull Builder put(
            @NotNull Map<String, ? extends BinaryTag> entries) {
        this.tags().putAll(entries);
        return this;
    }

    public CompoundBinaryTag.@NotNull Builder remove(
            @NotNull String text, @Nullable Consumer<? super BinaryTag> consumer) {
        if (this.tags != null) {
            BinaryTag binaryTag = this.tags.remove(text);
            if (consumer != null) {
                consumer.accept(binaryTag);
            }
        }
        return this;
    }

    @NotNull
    @Override
    public CompoundBinaryTag build() {
        return this.tags == null
                ? CompoundBinaryTag.empty()
                : new CompoundBinaryTagImpl(new HashMap<>(this.tags));
    }
}
