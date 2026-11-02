package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

public class StaticEntityType extends AbstractMappedEntity implements EntityType {

    private final Optional<EntityType> parent;

    private final Map<EntityType, Boolean> parents;

    @Nullable private TypesBuilderData legacyData;

    @Internal
    public StaticEntityType(
            @Nullable TypesBuilderData typesBuilderData, @Nullable EntityType entityType) {
        super(typesBuilderData);
        this.parent = Optional.ofNullable(entityType);
        this.parents = new IdentityHashMap<>();
        this.parents.put(this, true);
        while (entityType != null) {
            this.parents.put(entityType, true);
            entityType = entityType.getParent().orElse(null);
        }
    }

    StaticEntityType setLegacyData(@Nullable TypesBuilderData typesBuilderData) {
        this.legacyData = typesBuilderData;
        return this;
    }

    @Override
    public boolean isInstanceOf(EntityType entityType) {
        return entityType != null && this.parents.containsKey(entityType);
    }

    @Override
    public Optional<EntityType> getParent() {
        return this.parent;
    }

    @Override
    public int getLegacyId(ClientVersion clientVersion) {
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_14)) {
            return -1;
        } else if (this.legacyData != null) {
            return this.legacyData.getId(clientVersion);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
