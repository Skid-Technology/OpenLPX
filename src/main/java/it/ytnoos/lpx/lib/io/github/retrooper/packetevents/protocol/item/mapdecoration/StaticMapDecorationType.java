package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.mapdecoration;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class StaticMapDecorationType extends AbstractMappedEntity implements MapDecorationType {

    private final ResourceLocation assetId;

    private final boolean showOnItemFrame;

    private final int mapColor;

    private final boolean explorationMapElement;

    private final boolean trackCount;

    @Internal
    public StaticMapDecorationType(
            @Nullable TypesBuilderData typesBuilderData,
            ResourceLocation resourceLocation,
            boolean showOnItemFrame,
            int mapColor,
            boolean explorationMapElement,
            boolean trackCount) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
        this.showOnItemFrame = showOnItemFrame;
        this.mapColor = mapColor;
        this.explorationMapElement = explorationMapElement;
        this.trackCount = trackCount;
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public boolean isShowOnItemFrame() {
        return this.showOnItemFrame;
    }

    @Override
    public int getMapColor() {
        return this.mapColor;
    }

    @Override
    public boolean isExplorationMapElement() {
        return this.explorationMapElement;
    }

    @Override
    public boolean isTrackCount() {
        return this.trackCount;
    }
}
