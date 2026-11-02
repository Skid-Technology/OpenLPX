package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;

import java.io.IOException;

public interface NBTWriter<T extends NBT, OUT> {

    default void serializeTag(OUT oUT, T t) throws IOException {
        this.serializeTag((OUT) oUT, t, true);
    }

    void serializeTag(OUT oUT, NBT nBT, boolean matches) throws IOException;
}
