package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.armormaterial;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticArmorMaterial extends AbstractMappedEntity implements ArmorMaterial {

    @Internal
    public StaticArmorMaterial(@Nullable TypesBuilderData typesBuilderData) {
        super(typesBuilderData);
    }
}
