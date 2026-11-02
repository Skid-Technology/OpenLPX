package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class NbtTagHolder implements BinaryTagHolder {

    private final NBT tag;

    public NbtTagHolder(NBT nBT) {
        this.tag = nBT;
    }

    @Override
    public String string() {
        return AdventureNbtUtil.toString(this.tag);
    }

    @Override
    public <T, DX extends Exception> T get(Codec<T, String, DX, ?> codec) throws DX {
        return (T) codec.decode(this.string());
    }

    public NBT getTag() {
        return this.tag;
    }
}
