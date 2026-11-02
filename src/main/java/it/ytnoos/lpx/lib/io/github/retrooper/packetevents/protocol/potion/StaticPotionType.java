package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticPotionType extends AbstractMappedEntity implements PotionType {

    @Internal
    public StaticPotionType(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
