package it.ytnoos.lpx.lib.kyori.adventure.nbt.api;

import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

public interface BinaryTagHolder extends DataComponentValue.TagSerializable {

    @NotNull
    static <T, EX extends Exception> BinaryTagHolder encode(
            @NotNull T t, @NotNull Codec<? super T, String, ?, EX> codec) throws EX {
        return new BinaryTagHolderImpl((String) codec.encode(t));
    }

    @NotNull
    static BinaryTagHolder binaryTagHolder(@NotNull String text) {
        return new BinaryTagHolderImpl(text);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static BinaryTagHolder of(@NotNull String text) {
        return new BinaryTagHolderImpl(text);
    }

    @NotNull
    String string();

    @NotNull
    @Override
    default BinaryTagHolder asBinaryTag() {
        return this;
    }

    @NotNull
    <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> codec) throws DX;
}
