package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticCowSoundVariant extends AbstractMappedEntity implements CowSoundVariant {

    private final CowSoundSet sounds;

    public StaticCowSoundVariant(CowSoundSet cowSoundSet) {
        this(null, cowSoundSet);
    }

    @Internal
    public StaticCowSoundVariant(
            @Nullable TypesBuilderData typesBuilderData, CowSoundSet cowSoundSet) {
        super(typesBuilderData);
        this.sounds = cowSoundSet;
    }

    @Override
    public CowSoundSet getSounds() {
        return this.sounds;
    }

    public CowSoundVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticCowSoundVariant(typesBuilderData, this.sounds);
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticCowSoundVariant)) {
            return false;
        }
        StaticCowSoundVariant staticCowSoundVariant = (StaticCowSoundVariant) result;
        return this.sounds.equals(staticCowSoundVariant.sounds);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.sounds);
    }
}
