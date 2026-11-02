package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.floats;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface FloatProviderType<T extends FloatProvider> extends MappedEntity {

    NbtCodec<FloatProviderType<?>> CODEC = NbtCodecs.forRegistry(FloatProviderTypes.getRegistry());

    NbtMapCodec<T> getCodec();
}
