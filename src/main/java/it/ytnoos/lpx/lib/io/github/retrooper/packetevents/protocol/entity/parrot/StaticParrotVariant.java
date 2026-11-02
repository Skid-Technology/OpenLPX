package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.parrot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticParrotVariant extends AbstractMappedEntity implements ParrotVariant {

    @Internal
    public StaticParrotVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
