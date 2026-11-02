package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticCatSoundVariant extends AbstractMappedEntity implements CatSoundVariant {

    private final CatSoundSet adultSounds;

    private final CatSoundSet babySounds;

    public StaticCatSoundVariant(CatSoundSet catSoundSet, CatSoundSet catSoundSet2) {
        this(null, catSoundSet, catSoundSet2);
    }

    @Internal
    public StaticCatSoundVariant(
            @Nullable TypesBuilderData typesBuilderData,
            CatSoundSet catSoundSet,
            CatSoundSet catSoundSet2) {
        super(typesBuilderData);
        this.adultSounds = catSoundSet;
        this.babySounds = catSoundSet2;
    }

    public CatSoundVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticCatSoundVariant(typesBuilderData, this.adultSounds, this.babySounds);
    }

    @Override
    public CatSoundSet getAdultSounds() {
        return this.adultSounds;
    }

    @Override
    public CatSoundSet getBabySounds() {
        return this.babySounds;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticCatSoundVariant)) {
            return false;
        }
        StaticCatSoundVariant staticCatSoundVariant = (StaticCatSoundVariant) result;
        return !this.adultSounds.equals(staticCatSoundVariant.adultSounds)
                ? false
                : this.babySounds.equals(staticCatSoundVariant.babySounds);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.adultSounds, this.babySounds);
    }
}
