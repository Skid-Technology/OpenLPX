package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface AttributeType<T> extends MappedEntity {

    boolean isSynced();

    NbtCodec<T> getValueCodec();

    NbtCodec<AttributeModifier<T, ?>> getModifierCodec();
}
