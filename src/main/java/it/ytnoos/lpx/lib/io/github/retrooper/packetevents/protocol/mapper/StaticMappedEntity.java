package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

public interface StaticMappedEntity extends MappedEntity {

    int getId();

    @Override
    default int getId(ClientVersion clientVersion) {
        return this.getId();
    }
}
