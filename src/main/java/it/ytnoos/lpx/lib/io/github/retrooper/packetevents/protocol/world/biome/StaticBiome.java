package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticBiome extends AbstractMappedEntity implements Biome {

    private final boolean precipitation;

    private final float temperature;

    private final Biome.TemperatureModifier temperatureModifier;

    private final float downfall;

    private final Biome.@Nullable Category category;

    @Nullable private final Float depth;

    @Nullable private final Float scale;

    private final BiomeEffects effects;

    private final EnvironmentAttributeMap attributes;

    public StaticBiome(
            boolean matches,
            float value,
            Biome.TemperatureModifier temperatureModifier,
            float value2,
            BiomeEffects biomeEffects,
            EnvironmentAttributeMap environmentAttributeMap) {
        this(
                null,
                matches,
                value,
                temperatureModifier,
                value2,
                null,
                null,
                null,
                biomeEffects,
                environmentAttributeMap);
    }

    public StaticBiome(
            boolean matches,
            float value,
            Biome.TemperatureModifier temperatureModifier,
            float value2,
            BiomeEffects biomeEffects) {
        this(
                matches,
                value,
                temperatureModifier,
                value2,
                biomeEffects,
                EnvironmentAttributeMap.EMPTY);
    }

    public StaticBiome(
            boolean matches,
            float value,
            Biome.TemperatureModifier temperatureModifier,
            float value2,
            Biome.@Nullable Category category,
            @Nullable Float value3,
            @Nullable Float value4,
            BiomeEffects biomeEffects) {
        this(
                null,
                matches,
                value,
                temperatureModifier,
                value2,
                category,
                value3,
                value4,
                biomeEffects,
                EnvironmentAttributeMap.EMPTY);
    }

    @Internal
    public StaticBiome(
            @Nullable TypesBuilderData typesBuilderData,
            boolean precipitation,
            float temperature,
            Biome.TemperatureModifier temperatureModifier2,
            float downfall,
            Biome.@Nullable Category category2,
            @Nullable Float depth,
            @Nullable Float scale,
            BiomeEffects biomeEffects,
            EnvironmentAttributeMap environmentAttributeMap) {
        super(typesBuilderData);
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.temperatureModifier = temperatureModifier2;
        this.downfall = downfall;
        this.category = category2;
        this.depth = depth;
        this.scale = scale;
        this.effects = biomeEffects;
        this.attributes = environmentAttributeMap;
    }

    public Biome copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticBiome(
                typesBuilderData,
                this.precipitation,
                this.temperature,
                this.temperatureModifier,
                this.downfall,
                this.category,
                this.depth,
                this.scale,
                this.effects,
                this.attributes);
    }

    @Override
    public boolean hasPrecipitation() {
        return this.precipitation;
    }

    @Override
    public Biome.Precipitation getPrecipitation() {
        if (!this.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        switch (this.getTemperatureModifier()) {
            case NONE:
                return Biome.Precipitation.RAIN;
            case FROZEN:
                return Biome.Precipitation.SNOW;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public Biome.TemperatureModifier getTemperatureModifier() {
        return this.temperatureModifier;
    }

    @Override
    public float getDownfall() {
        return this.downfall;
    }

    @Override
    public Biome.@Nullable Category getCategory() {
        return this.category;
    }

    @Override
    @Nullable
    public Float getDepth() {
        return this.depth;
    }

    @Override
    @Nullable
    public Float getScale() {
        return this.scale;
    }

    @Override
    public BiomeEffects getEffects() {
        return this.effects;
    }

    @Override
    public EnvironmentAttributeMap getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticBiome)) {
            return false;
        } else {
            StaticBiome staticBiome = (StaticBiome) result;
            if (this.precipitation != staticBiome.precipitation) {
                return false;
            } else if (Float.compare(staticBiome.temperature, this.temperature) != 0) {
                return false;
            } else if (Float.compare(staticBiome.downfall, this.downfall) != 0) {
                return false;
            } else if (this.temperatureModifier != staticBiome.temperatureModifier) {
                return false;
            } else if (this.category != staticBiome.category) {
                return false;
            } else if (!Objects.equals(this.depth, staticBiome.depth)) {
                return false;
            } else if (!Objects.equals(this.scale, staticBiome.scale)) {
                return false;
            } else {
                return !this.effects.equals(staticBiome.effects)
                        ? false
                        : this.attributes.equals(staticBiome.attributes);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.precipitation,
                this.temperature,
                this.temperatureModifier,
                this.downfall,
                this.category,
                this.depth,
                this.scale,
                this.effects,
                this.attributes);
    }

    @Override
    public String toString() {
        return "StaticBiome{precipitation="
                + this.precipitation
                + ", temperature="
                + this.temperature
                + ", temperatureModifier="
                + this.temperatureModifier
                + ", downfall="
                + this.downfall
                + ", category="
                + this.category
                + ", depth="
                + this.depth
                + ", scale="
                + this.scale
                + ", effects="
                + this.effects
                + ", attributes="
                + this.attributes
                + '}';
    }
}
