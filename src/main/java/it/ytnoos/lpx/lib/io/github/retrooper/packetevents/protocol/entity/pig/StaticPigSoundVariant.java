package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticPigSoundVariant extends AbstractMappedEntity implements PigSoundVariant {

    private final PigSoundSet adultSounds;

    private final PigSoundSet babySounds;

    public StaticPigSoundVariant(PigSoundSet pigSoundSet, PigSoundSet pigSoundSet2) {
        this(null, pigSoundSet, pigSoundSet2);
    }

    @Internal
    public StaticPigSoundVariant(
            @Nullable TypesBuilderData typesBuilderData,
            PigSoundSet pigSoundSet,
            PigSoundSet pigSoundSet2) {
        super(typesBuilderData);
        this.adultSounds = pigSoundSet;
        this.babySounds = pigSoundSet2;
    }

    public PigSoundVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticPigSoundVariant(typesBuilderData, this.adultSounds, this.babySounds);
    }

    @Override
    public PigSoundSet getAdultSounds() {
        return this.adultSounds;
    }

    @Override
    public PigSoundSet getBabySounds() {
        return this.babySounds;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticPigSoundVariant)) {
            return false;
        }
        StaticPigSoundVariant staticPigSoundVariant = (StaticPigSoundVariant) result;
        return !this.adultSounds.equals(staticPigSoundVariant.adultSounds)
                ? false
                : this.babySounds.equals(staticPigSoundVariant.babySounds);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.adultSounds, this.babySounds);
    }
}
