package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.llama;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticLlamaVariant extends AbstractMappedEntity implements LlamaVariant {

    @Internal
    public StaticLlamaVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
