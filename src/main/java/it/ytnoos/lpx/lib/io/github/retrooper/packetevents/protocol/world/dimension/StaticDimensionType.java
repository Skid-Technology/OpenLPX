package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.ResolvableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines.Timeline;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines.Timelines;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.TagKey;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.OptionalLong;

@NullMarked
public class StaticDimensionType extends AbstractMappedEntity
        implements DimensionType, ResolvableEntity {

    private final boolean hasFixedTime;

    private final DimensionType.Skybox skybox;

    private final DimensionType.CardinalLight cardinalLight;

    private final EnvironmentAttributeMap attributes;

    private final MappedEntityRefSet<Timeline> timelinesRef;

    @Nullable private MappedEntitySet<Timeline> timelines;

    @Obsolete @Nullable private final Long fixedTime;

    @Obsolete private final boolean natural;

    @Obsolete private final boolean bedWorks;

    @Obsolete private final boolean respawnAnchorWorks;

    @Obsolete @Nullable private final ResourceLocation effects;

    private final double coordinateScale;

    private final int minY;

    private final int height;

    @Experimental private final NBT monsterSpawnLightLevel;

    private final int monsterSpawnBlockLightLimit;

    private final boolean hasSkylight;

    private final boolean hasCeiling;

    private final int logicalHeight;

    private final TagKey infiniburn;

    private final float ambientLight;

    @Nullable private final MappedEntityRef<WorldClock> defaultClock;

    private final boolean hasEnderDragonFight;

    @Deprecated
    public StaticDimensionType(
            OptionalLong optionalLong,
            boolean matches,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            double value,
            boolean matches5,
            boolean matches6,
            int value2,
            int value3,
            int value4,
            String text,
            @Nullable ResourceLocation resourceLocation,
            float value5,
            boolean matches7,
            boolean matches8,
            @Nullable NBT nBT,
            int value6) {
        this(
                optionalLong,
                matches,
                matches2,
                matches3,
                matches4,
                value,
                matches5,
                matches6,
                value2,
                value3,
                value4,
                text,
                resourceLocation,
                value5,
                192,
                matches7,
                matches8,
                nBT,
                value6);
    }

    @Deprecated
    public StaticDimensionType(
            OptionalLong optionalLong,
            boolean matches,
            boolean matches2,
            boolean matches3,
            boolean matches4,
            double value,
            boolean matches5,
            boolean matches6,
            int value2,
            int value3,
            int value4,
            String text,
            @Nullable ResourceLocation resourceLocation,
            float value5,
            @Nullable Integer value6,
            boolean matches7,
            boolean matches8,
            @Nullable NBT nBT,
            int value7) {
        this(
                null,
                optionalLong.isPresent(),
                DimensionType.Skybox.OVERWORLD,
                DimensionType.CardinalLight.DEFAULT,
                EnvironmentAttributeMap.create()
                        .set(EnvironmentAttributes.GAMEPLAY_WATER_EVAPORATES, matches3)
                        .set(
                                EnvironmentAttributes.VISUAL_CLOUD_HEIGHT,
                                value6 != null ? value6.intValue() : 192.0F)
                        .set(EnvironmentAttributes.GAMEPLAY_NETHER_PORTAL_SPAWNS_PIGLIN, !matches7)
                        .set(EnvironmentAttributes.GAMEPLAY_CAN_START_RAID, matches8)
                        .copyImmutable(),
                MappedEntitySet.createEmpty(),
                optionalLong.isPresent() ? optionalLong.getAsLong() : null,
                matches4,
                matches5,
                matches6,
                resourceLocation,
                value,
                value2,
                value3,
                nBT != null ? nBT : new NBTInt(7),
                value7,
                matches,
                matches2,
                value4,
                TagKey.parse(text),
                value5,
                null,
                false);
    }

    @Internal
    public StaticDimensionType(
            @Nullable TypesBuilderData typesBuilderData,
            boolean hasFixedTime,
            DimensionType.Skybox skybox2,
            DimensionType.CardinalLight cardinalLight2,
            EnvironmentAttributeMap environmentAttributeMap,
            MappedEntityRefSet<Timeline> mappedEntityRefSet,
            @Nullable Long fixedTime,
            boolean natural,
            boolean bedWorks,
            boolean respawnAnchorWorks,
            @Nullable ResourceLocation resourceLocation,
            double coordinateScale,
            int minY,
            int height,
            NBT nBT,
            int monsterSpawnBlockLightLimit,
            boolean hasSkylight,
            boolean hasCeiling,
            int logicalHeight,
            TagKey tagKey,
            float ambientLight,
            @Nullable MappedEntityRef<WorldClock> mappedEntityRef,
            boolean hasEnderDragonFight) {
        super(typesBuilderData);
        this.hasFixedTime = hasFixedTime;
        this.skybox = skybox2;
        this.cardinalLight = cardinalLight2;
        this.attributes = environmentAttributeMap;
        this.timelinesRef = mappedEntityRefSet;
        this.fixedTime = fixedTime;
        this.natural = natural;
        this.bedWorks = bedWorks;
        this.respawnAnchorWorks = respawnAnchorWorks;
        this.effects = resourceLocation;
        this.coordinateScale = coordinateScale;
        this.minY = minY;
        this.height = height;
        this.monsterSpawnLightLevel = nBT;
        this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
        this.hasSkylight = hasSkylight;
        this.hasCeiling = hasCeiling;
        this.logicalHeight = logicalHeight;
        this.infiniburn = tagKey;
        this.ambientLight = ambientLight;
        this.defaultClock = mappedEntityRef;
        this.hasEnderDragonFight = hasEnderDragonFight;
    }

    @Override
    public void doResolve(PacketWrapper<?> packetWrapper) {
        this.timelines = this.timelinesRef.resolve(packetWrapper, Timelines.getRegistry());
    }

    public DimensionType copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticDimensionType(
                typesBuilderData,
                this.hasFixedTime,
                this.skybox,
                this.cardinalLight,
                this.attributes,
                this.timelinesRef,
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

    @Override
    public boolean hasFixedTime() {
        return this.hasFixedTime;
    }

    @Override
    public OptionalLong getFixedTime() {
        return this.fixedTime != null ? OptionalLong.of(this.fixedTime) : OptionalLong.empty();
    }

    @Override
    public boolean hasSkyLight() {
        return this.hasSkylight;
    }

    @Override
    public boolean hasCeiling() {
        return this.hasCeiling;
    }

    @Override
    public boolean isUltraWarm() {
        return this.attributes.getOrDefault(EnvironmentAttributes.GAMEPLAY_WATER_EVAPORATES);
    }

    @Override
    public boolean isNatural() {
        return this.natural;
    }

    @Override
    public double getCoordinateScale() {
        return this.coordinateScale;
    }

    @Override
    public boolean isBedWorking() {
        return this.bedWorks;
    }

    @Override
    public boolean isRespawnAnchorWorking() {
        return this.respawnAnchorWorks;
    }

    @Override
    public int getMinY() {
        return this.minY;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getLogicalHeight() {
        return this.logicalHeight;
    }

    @Override
    public TagKey getInfiniburn() {
        return this.infiniburn;
    }

    @Override
    public ResourceLocation getEffectsLocation() {
        if (this.effects == null) {
            throw new UnsupportedOperationException();
        } else {
            return this.effects;
        }
    }

    @Override
    public float getAmbientLight() {
        return this.ambientLight;
    }

    @Override
    @Nullable
    public Integer getCloudHeight() {
        return this.attributes.getOrDefault(EnvironmentAttributes.VISUAL_CLOUD_HEIGHT).intValue();
    }

    @Override
    public boolean isPiglinSafe() {
        return !this.attributes.getOrDefault(EnvironmentAttributes.GAMEPLAY_PIGLINS_ZOMBIFY);
    }

    @Override
    public boolean hasRaids() {
        return this.attributes.getOrDefault(EnvironmentAttributes.GAMEPLAY_CAN_START_RAID);
    }

    @Override
    public NBT getMonsterSpawnLightLevel() {
        return this.monsterSpawnLightLevel;
    }

    @Override
    public int getMonsterSpawnBlockLightLimit() {
        return this.monsterSpawnBlockLightLimit;
    }

    @Override
    public DimensionType.Skybox getSkybox() {
        return this.skybox;
    }

    @Override
    public DimensionType.CardinalLight getCardinalLight() {
        return this.cardinalLight;
    }

    @Override
    public EnvironmentAttributeMap getAttributes() {
        return this.attributes;
    }

    @Override
    public MappedEntitySet<Timeline> getTimelines() {
        if (this.timelines == null) {
            throw new UnsupportedOperationException();
        } else {
            return this.timelines;
        }
    }

    @Override
    public MappedEntityRefSet<Timeline> getTimelinesRef() {
        return this.timelinesRef;
    }

    @Override
    public boolean isHasEnderDragonFight() {
        return this.hasEnderDragonFight;
    }

    @Override
    @Nullable
    public WorldClock getDefaultClock() {
        return this.defaultClock != null ? this.defaultClock.get() : null;
    }

    @Override
    @Nullable
    public MappedEntityRef<WorldClock> getDefaultClockRef() {
        return this.defaultClock;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            StaticDimensionType staticDimensionType = (StaticDimensionType) result;
            if (this.hasFixedTime != staticDimensionType.hasFixedTime) {
                return false;
            } else if (this.natural != staticDimensionType.natural) {
                return false;
            } else if (this.bedWorks != staticDimensionType.bedWorks) {
                return false;
            } else if (this.respawnAnchorWorks != staticDimensionType.respawnAnchorWorks) {
                return false;
            } else if (Double.compare(staticDimensionType.coordinateScale, this.coordinateScale)
                    != 0) {
                return false;
            } else if (this.minY != staticDimensionType.minY) {
                return false;
            } else if (this.height != staticDimensionType.height) {
                return false;
            } else if (this.monsterSpawnBlockLightLimit
                    != staticDimensionType.monsterSpawnBlockLightLimit) {
                return false;
            } else if (this.hasSkylight != staticDimensionType.hasSkylight) {
                return false;
            } else if (this.hasCeiling != staticDimensionType.hasCeiling) {
                return false;
            } else if (this.logicalHeight != staticDimensionType.logicalHeight) {
                return false;
            } else if (Float.compare(staticDimensionType.ambientLight, this.ambientLight) != 0) {
                return false;
            } else if (this.skybox != staticDimensionType.skybox) {
                return false;
            } else if (this.cardinalLight != staticDimensionType.cardinalLight) {
                return false;
            } else if (this.hasEnderDragonFight != staticDimensionType.hasEnderDragonFight) {
                return false;
            } else if (!this.attributes.equals(staticDimensionType.attributes)) {
                return false;
            } else if (!this.timelinesRef.equals(staticDimensionType.timelinesRef)) {
                return false;
            } else if (!Objects.equals(this.fixedTime, staticDimensionType.fixedTime)) {
                return false;
            } else if (!Objects.equals(this.effects, staticDimensionType.effects)) {
                return false;
            } else if (!Objects.equals(this.defaultClock, staticDimensionType.defaultClock)) {
                return false;
            } else {
                return !this.monsterSpawnLightLevel.equals(
                                staticDimensionType.monsterSpawnLightLevel)
                        ? false
                        : this.infiniburn.equals(staticDimensionType.infiniburn);
            }
        } else {
            return false;
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.hasFixedTime,
                this.skybox,
                this.cardinalLight,
                this.attributes,
                this.timelinesRef,
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
}
