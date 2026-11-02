package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

public interface MappedEntity {

    ResourceLocation getName();

    int getId(ClientVersion clientVersion);

    default boolean isRegistered() {
        return true;
    }
}
