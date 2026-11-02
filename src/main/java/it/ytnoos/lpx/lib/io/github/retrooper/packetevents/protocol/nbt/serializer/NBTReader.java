package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;

import java.io.IOException;

public interface NBTReader<T extends NBT, IN> {

    default T deserializeTag(NBTLimiter nBTLimiter, IN n) throws IOException {
        return this.deserializeTag(nBTLimiter, (IN) n, true);
    }

    T deserializeTag(NBTLimiter nBTLimiter, IN n, boolean matches) throws IOException;
}
