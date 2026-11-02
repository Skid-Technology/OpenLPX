package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.axolotl;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticAxolotlVariant extends AbstractMappedEntity implements AxolotlVariant {

    @Internal
    public StaticAxolotlVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
