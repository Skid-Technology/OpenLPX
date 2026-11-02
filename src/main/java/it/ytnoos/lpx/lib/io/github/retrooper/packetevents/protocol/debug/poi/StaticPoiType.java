package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.poi;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class StaticPoiType extends AbstractMappedEntity implements PoiType {

    @Internal
    public StaticPoiType(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
