package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.rabbit;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticRabbitVariant extends AbstractMappedEntity implements RabbitVariant {

    @Internal
    public StaticRabbitVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
