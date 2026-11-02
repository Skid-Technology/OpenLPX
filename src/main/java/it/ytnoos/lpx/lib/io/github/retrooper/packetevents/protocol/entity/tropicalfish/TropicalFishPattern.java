package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.tropicalfish;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;

public interface TropicalFishPattern extends MappedEntity {

    TropicalFishPattern.Base getBase();

    enum Base {
        SMALL,
        LARGE
    }
}
