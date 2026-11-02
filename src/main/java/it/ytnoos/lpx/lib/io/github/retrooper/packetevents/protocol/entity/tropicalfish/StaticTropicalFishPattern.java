package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.tropicalfish;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticTropicalFishPattern extends AbstractMappedEntity implements TropicalFishPattern {

    private final TropicalFishPattern.Base base;

    @Internal
    public StaticTropicalFishPattern(
            @Nullable TypesBuilderData typesBuilderData, TropicalFishPattern.Base base2) {
        super(typesBuilderData);
        this.base = base2;
    }

    @Override
    public TropicalFishPattern.Base getBase() {
        return this.base;
    }
}
