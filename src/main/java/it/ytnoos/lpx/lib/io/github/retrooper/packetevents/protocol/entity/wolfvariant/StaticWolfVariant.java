package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome.Biome;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticWolfVariant extends AbstractMappedEntity implements WolfVariant {

    private final WolfAssetSet assets;

    private final WolfAssetSet babyAssets;

    private final MappedEntitySet<Biome> biomes;

    @Deprecated
    @Obsolete
    public StaticWolfVariant(
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2,
            ResourceLocation resourceLocation3,
            MappedEntitySet<Biome> mappedEntitySet) {
        this(
                new WolfAssetSet(resourceLocation, resourceLocation2, resourceLocation3),
                mappedEntitySet);
    }

    @Deprecated
    @Obsolete
    public StaticWolfVariant(
            ResourceLocation resourceLocation,
            ResourceLocation resourceLocation2,
            ResourceLocation resourceLocation3) {
        this(new WolfAssetSet(resourceLocation3, resourceLocation2, resourceLocation));
    }

    @Obsolete
    public StaticWolfVariant(WolfAssetSet wolfAssetSet, MappedEntitySet<Biome> mappedEntitySet) {
        this(null, wolfAssetSet, wolfAssetSet, mappedEntitySet);
    }

    @Obsolete
    public StaticWolfVariant(WolfAssetSet wolfAssetSet) {
        this(null, wolfAssetSet, wolfAssetSet, MappedEntitySet.createEmpty());
    }

    public StaticWolfVariant(WolfAssetSet wolfAssetSet, WolfAssetSet wolfAssetSet2) {
        this(null, wolfAssetSet, wolfAssetSet2, MappedEntitySet.createEmpty());
    }

    @Internal
    public StaticWolfVariant(
            @Nullable TypesBuilderData typesBuilderData,
            WolfAssetSet wolfAssetSet,
            WolfAssetSet wolfAssetSet2,
            MappedEntitySet<Biome> mappedEntitySet) {
        super(typesBuilderData);
        this.assets = wolfAssetSet;
        this.babyAssets = wolfAssetSet2;
        this.biomes = mappedEntitySet;
    }

    public WolfVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticWolfVariant(typesBuilderData, this.assets, this.babyAssets, this.biomes);
    }

    @Override
    public WolfAssetSet getAssets() {
        return this.assets;
    }

    @Override
    public WolfAssetSet getBabyAssets() {
        return this.babyAssets;
    }

    @Override
    public MappedEntitySet<Biome> getBiomes() {
        return this.biomes;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticWolfVariant)) {
            return false;
        } else {
            StaticWolfVariant staticWolfVariant = (StaticWolfVariant) result;
            if (!this.assets.equals(staticWolfVariant.assets)) {
                return false;
            } else {
                return !this.babyAssets.equals(staticWolfVariant.babyAssets)
                        ? false
                        : this.biomes.equals(staticWolfVariant.biomes);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.assets, this.babyAssets, this.biomes);
    }
}
