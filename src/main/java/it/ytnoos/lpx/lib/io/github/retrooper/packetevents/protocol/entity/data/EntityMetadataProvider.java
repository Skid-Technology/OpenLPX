package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import java.util.List;

public interface EntityMetadataProvider {

    List<EntityData<?>> entityData(ClientVersion clientVersion);

    @Deprecated
    default List<EntityData<?>> entityData() {
        return this.entityData(ClientVersion.getLatest());
    }
}
