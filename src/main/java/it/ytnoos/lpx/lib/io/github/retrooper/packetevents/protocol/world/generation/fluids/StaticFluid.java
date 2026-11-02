package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.fluids;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
final class StaticFluid extends AbstractMappedEntity implements Fluid {

    public StaticFluid(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
