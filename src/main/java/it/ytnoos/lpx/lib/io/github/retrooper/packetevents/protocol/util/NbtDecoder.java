package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
@FunctionalInterface
public interface NbtDecoder<T> {

    T decode(NBT nBT, PacketWrapper<?> packetWrapper) throws NbtCodecException;
}
