package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Function;

public interface ComponentType<T> extends MappedEntity {

    T read(PacketWrapper<?> packetWrapper);

    void write(PacketWrapper<?> packetWrapper, T t);

    T decode(NBT nBT, ClientVersion clientVersion);

    NBT encode(T t, ClientVersion clientVersion);

    @Internal
    <Z> ComponentType<Z> legacyMap(Function<T, Z> function, Function<Z, T> function2);

    interface Decoder<T> {

        T decode(NBT nBT, ClientVersion clientVersion);
    }

    interface Encoder<T> {

        NBT encode(T t, ClientVersion clientVersion);
    }
}
