package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
@FunctionalInterface
public interface NbtMapEncoder<T> {

    void encode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper, T t)
            throws NbtCodecException;
}
