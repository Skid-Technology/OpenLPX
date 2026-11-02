package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.ApiStatus.Obsolete;

public interface MapDecorationType extends MappedEntity {

    ResourceLocation getAssetId();

    boolean isShowOnItemFrame();

    @Obsolete
    int getMapColor();

    @Obsolete
    boolean isExplorationMapElement();

    boolean isTrackCount();
}
