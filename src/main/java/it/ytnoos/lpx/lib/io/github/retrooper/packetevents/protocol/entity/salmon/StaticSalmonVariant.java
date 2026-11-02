package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.salmon;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticSalmonVariant extends AbstractMappedEntity implements SalmonVariant {

    @Internal
    public StaticSalmonVariant(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
