package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticChickenSoundVariant extends AbstractMappedEntity implements ChickenSoundVariant {

    private final ChickenSoundSet adultSounds;

    private final ChickenSoundSet babySounds;

    public StaticChickenSoundVariant(
            ChickenSoundSet chickenSoundSet, ChickenSoundSet chickenSoundSet2) {
        this(null, chickenSoundSet, chickenSoundSet2);
    }

    @Internal
    public StaticChickenSoundVariant(
            @Nullable TypesBuilderData typesBuilderData,
            ChickenSoundSet chickenSoundSet,
            ChickenSoundSet chickenSoundSet2) {
        super(typesBuilderData);
        this.adultSounds = chickenSoundSet;
        this.babySounds = chickenSoundSet2;
    }

    public ChickenSoundVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticChickenSoundVariant(typesBuilderData, this.adultSounds, this.babySounds);
    }

    @Override
    public ChickenSoundSet getAdultSounds() {
        return this.adultSounds;
    }

    @Override
    public ChickenSoundSet getBabySounds() {
        return this.babySounds;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticChickenSoundVariant)) {
            return false;
        }
        StaticChickenSoundVariant staticChickenSoundVariant = (StaticChickenSoundVariant) result;
        return !this.adultSounds.equals(staticChickenSoundVariant.adultSounds)
                ? false
                : this.babySounds.equals(staticChickenSoundVariant.babySounds);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.adultSounds, this.babySounds);
    }
}
