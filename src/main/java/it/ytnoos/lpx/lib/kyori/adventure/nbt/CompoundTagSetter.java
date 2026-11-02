package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

@NonExtendable
public interface CompoundTagSetter<R> {

    @NotNull
    R put(@NotNull String text, @NotNull BinaryTag binaryTag);

    @NotNull
    R put(@NotNull CompoundBinaryTag compoundBinaryTag);

    @NotNull
    R put(@NotNull Map<String, ? extends BinaryTag> entries);

    @NotNull
    default R remove(@NotNull String text) {
        return this.remove(text, null);
    }

    @NotNull
    R remove(@NotNull String text, @Nullable Consumer<? super BinaryTag> consumer);

    @NotNull
    default R putBoolean(@NotNull String text, boolean matches) {
        return this.put(text, matches ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
    }

    @NotNull
    default R putByte(@NotNull String text, byte value) {
        return this.put(text, ByteBinaryTag.byteBinaryTag(value));
    }

    @NotNull
    default R putShort(@NotNull String text, short value) {
        return this.put(text, ShortBinaryTag.shortBinaryTag(value));
    }

    @NotNull
    default R putInt(@NotNull String text, int value) {
        return this.put(text, IntBinaryTag.intBinaryTag(value));
    }

    @NotNull
    default R putLong(@NotNull String text, long value) {
        return this.put(text, LongBinaryTag.longBinaryTag(value));
    }

    @NotNull
    default R putFloat(@NotNull String text, float value) {
        return this.put(text, FloatBinaryTag.floatBinaryTag(value));
    }

    @NotNull
    default R putDouble(@NotNull String text, double value) {
        return this.put(text, DoubleBinaryTag.doubleBinaryTag(value));
    }

    @NotNull
    default R putByteArray(@NotNull String text, byte @NotNull [] values) {
        return this.put(text, ByteArrayBinaryTag.byteArrayBinaryTag(values));
    }

    @NotNull
    default R putString(@NotNull String text, @NotNull String text2) {
        return this.put(text, StringBinaryTag.stringBinaryTag(text2));
    }

    @NotNull
    default R putIntArray(@NotNull String text, int @NotNull [] values) {
        return this.put(text, IntArrayBinaryTag.intArrayBinaryTag(values));
    }

    @NotNull
    default R putLongArray(@NotNull String text, long @NotNull [] values) {
        return this.put(text, LongArrayBinaryTag.longArrayBinaryTag(values));
    }
}
