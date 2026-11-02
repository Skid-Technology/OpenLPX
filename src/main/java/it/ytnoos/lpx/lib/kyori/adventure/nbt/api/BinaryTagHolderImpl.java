package it.ytnoos.lpx.lib.kyori.adventure.nbt.api;

import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class BinaryTagHolderImpl implements BinaryTagHolder {

    private final String string;

    BinaryTagHolderImpl(String text) {
        this.string = Objects.requireNonNull(text, "string");
    }

    @NotNull
    @Override
    public String string() {
        return this.string;
    }

    @NotNull
    @Override
    public <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> codec) throws DX {
        return (T) codec.decode(this.string);
    }

    @Override
    public int hashCode() {
        return 31 * this.string.hashCode();
    }

    @Override
    public boolean equals(Object result) {
        return !(result instanceof BinaryTagHolderImpl)
                ? false
                : this.string.equals(((BinaryTagHolderImpl) result).string);
    }

    @Override
    public String toString() {
        return this.string;
    }
}
