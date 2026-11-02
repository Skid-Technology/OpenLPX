package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticSound extends AbstractMappedEntity implements Sound {

    private final ResourceLocation soundId;

    @Nullable private final Float range;

    public StaticSound(ResourceLocation resourceLocation, @Nullable Float value) {
        this(null, resourceLocation, value);
    }

    @Internal
    public StaticSound(
            @Nullable TypesBuilderData typesBuilderData,
            ResourceLocation resourceLocation,
            @Nullable Float range) {
        super(typesBuilderData);
        this.soundId = resourceLocation;
        this.range = range;
    }

    @Override
    public ResourceLocation getSoundId() {
        return this.soundId;
    }

    @Override
    @Nullable
    public Float getRange() {
        return this.range;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (result != null && this.getClass() == result.getClass()) {
            StaticSound staticSound = (StaticSound) result;
            return !this.soundId.equals(staticSound.soundId)
                    ? false
                    : Objects.equals(this.range, staticSound.range);
        } else {
            return false;
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.soundId, this.range);
    }
}
