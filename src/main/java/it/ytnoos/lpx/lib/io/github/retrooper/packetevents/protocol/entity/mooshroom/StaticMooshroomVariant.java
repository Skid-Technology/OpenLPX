package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.mooshroom;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticMooshroomVariant extends AbstractMappedEntity implements MooshroomVariant {

    @Internal
    public StaticMooshroomVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
