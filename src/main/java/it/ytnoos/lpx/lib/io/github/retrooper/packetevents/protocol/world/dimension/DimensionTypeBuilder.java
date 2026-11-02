package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityBuilder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers.AttributeModifier;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines.Timeline;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.defaulttags.BlockTags;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.TagKey;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class DimensionTypeBuilder implements MappedEntityBuilder<DimensionType> {

    private boolean hasFixedTime = false;

    private DimensionType.Skybox skybox = DimensionType.Skybox.OVERWORLD;

    private DimensionType.CardinalLight cardinalLight = DimensionType.CardinalLight.DEFAULT;

    private final EnvironmentAttributeMap attributes = EnvironmentAttributeMap.create();

    private MappedEntityRefSet<Timeline> timelines = MappedEntitySet.createEmpty();

    @Obsolete @Nullable private Long fixedTime = null;

    @Obsolete private boolean natural;

    @Obsolete private boolean bedWorks;

    @Obsolete private boolean respawnAnchorWorks;

    @Obsolete @Nullable private ResourceLocation effects;

    private double coordinateScale = 1.0;

    private int minY = 0;

    private int height = 256;

    @Experimental private NBT monsterSpawnLightLevel = new NBTInt(7);

    private int monsterSpawnBlockLightLimit = 7;

    private boolean hasSkylight = true;

    private boolean hasCeiling = false;

    private int logicalHeight = 256;

    private TagKey infiniburn = BlockTags.INFINIBURN_OVERWORLD.getKey();

    private float ambientLight = 0.0F;

    @Nullable private MappedEntityRef<WorldClock> defaultClock;

    private boolean hasEnderDragonFight;

    private DimensionTypeBuilder() {}

    public static DimensionTypeBuilder dimensionTypeBuilder() {
        return new DimensionTypeBuilder();
    }

    @Internal
    public DimensionType build(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticDimensionType(
                typesBuilderData,
                this.hasFixedTime,
                this.skybox,
                this.cardinalLight,
                this.attributes.copyImmutable(),
                this.timelines,
                this.fixedTime,
                this.natural,
                this.bedWorks,
                this.respawnAnchorWorks,
                this.effects,
                this.coordinateScale,
                this.minY,
                this.height,
                this.monsterSpawnLightLevel,
                this.monsterSpawnBlockLightLimit,
                this.hasSkylight,
                this.hasCeiling,
                this.logicalHeight,
                this.infiniburn,
                this.ambientLight,
                this.defaultClock,
                this.hasEnderDragonFight);
    }

    public boolean isRespawnAnchorWorks() {
        return this.respawnAnchorWorks;
    }

    public DimensionTypeBuilder setRespawnAnchorWorks(boolean respawnAnchorWorks) {
        this.respawnAnchorWorks = respawnAnchorWorks;
        return this;
    }

    public boolean isHasFixedTime() {
        return this.hasFixedTime;
    }

    public DimensionTypeBuilder setHasFixedTime(boolean hasFixedTime) {
        this.hasFixedTime = hasFixedTime;
        return this;
    }

    public DimensionType.Skybox getSkybox() {
        return this.skybox;
    }

    public DimensionTypeBuilder setSkybox(DimensionType.Skybox skybox2) {
        this.skybox = skybox2;
        return this;
    }

    public DimensionType.CardinalLight getCardinalLight() {
        return this.cardinalLight;
    }

    public DimensionTypeBuilder setCardinalLight(DimensionType.CardinalLight cardinalLight2) {
        this.cardinalLight = cardinalLight2;
        return this;
    }

    public EnvironmentAttributeMap getAttributes() {
        return this.attributes;
    }

    public <T> DimensionTypeBuilder setAttribute(
            EnvironmentAttribute<T> environmentAttribute, T t) {
        return this.setAttribute(environmentAttribute, t, AttributeModifier.override());
    }

    public <T, A> DimensionTypeBuilder setAttribute(
            EnvironmentAttribute<T> environmentAttribute,
            A a,
            AttributeModifier<T, A> attributeModifier) {
        this.attributes.set(environmentAttribute, a, attributeModifier);
        return this;
    }

    public DimensionTypeBuilder setAttributes(EnvironmentAttributeMap environmentAttributeMap) {
        this.attributes.setAll(environmentAttributeMap);
        return this;
    }

    public MappedEntityRefSet<Timeline> getTimelines() {
        return this.timelines;
    }

    public DimensionTypeBuilder setTimelines(MappedEntityRefSet<Timeline> mappedEntityRefSet) {
        this.timelines = mappedEntityRefSet;
        return this;
    }

    @Nullable
    public Long getFixedTime() {
        return this.fixedTime;
    }

    public DimensionTypeBuilder setFixedTime(@Nullable Long fixedTime) {
        this.hasFixedTime = fixedTime != null;
        this.fixedTime = fixedTime;
        return this;
    }

    public boolean isNatural() {
        return this.natural;
    }

    public DimensionTypeBuilder setNatural(boolean natural) {
        this.natural = natural;
        return this;
    }

    public boolean isBedWorks() {
        return this.bedWorks;
    }

    public DimensionTypeBuilder setBedWorks(boolean bedWorks) {
        this.bedWorks = bedWorks;
        return this;
    }

    @Nullable
    public ResourceLocation getEffects() {
        return this.effects;
    }

    public DimensionTypeBuilder setEffects(ResourceLocation resourceLocation) {
        this.effects = resourceLocation;
        return this;
    }

    public double getCoordinateScale() {
        return this.coordinateScale;
    }

    public DimensionTypeBuilder setCoordinateScale(double coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    public int getMinY() {
        return this.minY;
    }

    public DimensionTypeBuilder setMinY(int minY) {
        this.minY = minY;
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public DimensionTypeBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    @Experimental
    public NBT getMonsterSpawnLightLevel() {
        return this.monsterSpawnLightLevel;
    }

    @Experimental
    public DimensionTypeBuilder setMonsterSpawnLightLevel(NBT nBT) {
        this.monsterSpawnLightLevel = nBT;
        return this;
    }

    public int getMonsterSpawnBlockLightLimit() {
        return this.monsterSpawnBlockLightLimit;
    }

    public DimensionTypeBuilder setMonsterSpawnBlockLightLimit(int monsterSpawnBlockLightLimit) {
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        return this;
    }

    public boolean isHasSkylight() {
        return this.hasSkylight;
    }

    public DimensionTypeBuilder setHasSkylight(boolean hasSkylight) {
        this.hasSkylight = hasSkylight;
        return this;
    }

    public boolean isHasCeiling() {
        return this.hasCeiling;
    }

    public DimensionTypeBuilder setHasCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    public int getLogicalHeight() {
        return this.logicalHeight;
    }

    public DimensionTypeBuilder setLogicalHeight(int logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

    public TagKey getInfiniburn() {
        return this.infiniburn;
    }

    public DimensionTypeBuilder setInfiniburn(TagKey tagKey) {
        this.infiniburn = tagKey;
        return this;
    }

    public float getAmbientLight() {
        return this.ambientLight;
    }

    public DimensionTypeBuilder setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    @Nullable
    public WorldClock getDefaultClock() {
        return this.defaultClock != null ? this.defaultClock.get() : null;
    }

    @Nullable
    public MappedEntityRef<WorldClock> getDefaultClockRef() {
        return this.defaultClock;
    }

    public DimensionTypeBuilder setDefaultClock(@Nullable WorldClock worldClock) {
        return this.setDefaultClock(
                worldClock != null ? new MappedEntityRef.Static<>(worldClock) : null);
    }

    public DimensionTypeBuilder setDefaultClock(
            @Nullable MappedEntityRef<WorldClock> mappedEntityRef) {
        this.defaultClock = mappedEntityRef;
        return this;
    }

    public boolean isHasEnderDragonFight() {
        return this.hasEnderDragonFight;
    }

    public DimensionTypeBuilder setHasEnderDragonFight(boolean hasEnderDragonFight) {
        this.hasEnderDragonFight = hasEnderDragonFight;
        return this;
    }
}
