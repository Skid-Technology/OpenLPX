package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface CopyableEntity<T extends MappedEntity> {

    T copy(@Nullable TypesBuilderData typesBuilderData);
}
