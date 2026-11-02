package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.LegacyMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;

import java.util.Optional;

public interface EntityType extends MappedEntity, LegacyMappedEntity {

    boolean isInstanceOf(EntityType entityType);

    Optional<EntityType> getParent();
}
