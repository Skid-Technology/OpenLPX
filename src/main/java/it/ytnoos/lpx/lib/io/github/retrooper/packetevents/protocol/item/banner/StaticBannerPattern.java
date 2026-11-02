package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.banner;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticBannerPattern extends AbstractMappedEntity implements BannerPattern {

    private final ResourceLocation assetId;

    private final String translationKey;

    public StaticBannerPattern(ResourceLocation resourceLocation, String text) {
        this(null, resourceLocation, text);
    }

    @Internal
    public StaticBannerPattern(
            @Nullable TypesBuilderData typesBuilderData,
            ResourceLocation resourceLocation,
            String translationKey) {
        super(typesBuilderData);
        this.assetId = resourceLocation;
        this.translationKey = translationKey;
    }

    public BannerPattern copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticBannerPattern(typesBuilderData, this.assetId, this.translationKey);
    }

    @Override
    public ResourceLocation getAssetId() {
        return this.assetId;
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof StaticBannerPattern)) {
            return false;
        }
        StaticBannerPattern staticBannerPattern = (StaticBannerPattern) result;
        return !this.assetId.equals(staticBannerPattern.assetId)
                ? false
                : this.translationKey.equals(staticBannerPattern.translationKey);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assetId, this.translationKey);
    }

    @Override
    public String toString() {
        return "StaticBannerPattern{assetId="
                + this.assetId
                + ", translationKey='"
                + this.translationKey
                + '\''
                + '}';
    }
}
