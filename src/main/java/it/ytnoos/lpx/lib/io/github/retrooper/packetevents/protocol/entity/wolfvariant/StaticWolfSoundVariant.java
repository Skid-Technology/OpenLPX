package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sounds;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticWolfSoundVariant extends AbstractMappedEntity implements WolfSoundVariant {

    private final WolfSoundSet adultSounds;

    private final WolfSoundSet babySounds;

    @Deprecated
    public StaticWolfSoundVariant(
            Sound sound, Sound sound2, Sound sound3, Sound sound4, Sound sound5, Sound sound6) {
        this(
                new WolfSoundSet(
                        sound, sound2, sound3, sound4, sound5, sound6, Sounds.ENTITY_WOLF_STEP));
    }

    @Obsolete
    public StaticWolfSoundVariant(WolfSoundSet wolfSoundSet) {
        this(null, wolfSoundSet, wolfSoundSet);
    }

    public StaticWolfSoundVariant(WolfSoundSet wolfSoundSet, WolfSoundSet wolfSoundSet2) {
        this(null, wolfSoundSet, wolfSoundSet2);
    }

    @Internal
    public StaticWolfSoundVariant(
            @Nullable TypesBuilderData typesBuilderData,
            WolfSoundSet wolfSoundSet,
            WolfSoundSet wolfSoundSet2) {
        super(typesBuilderData);
        this.adultSounds = wolfSoundSet;
        this.babySounds = wolfSoundSet2;
    }

    public WolfSoundVariant copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticWolfSoundVariant(typesBuilderData, this.adultSounds, this.babySounds);
    }

    @Override
    public WolfSoundSet getAdultSounds() {
        return this.adultSounds;
    }

    @Override
    public WolfSoundSet getBabySounds() {
        return this.babySounds;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (!(result instanceof StaticWolfSoundVariant)) {
            return false;
        }
        StaticWolfSoundVariant staticWolfSoundVariant = (StaticWolfSoundVariant) result;
        return !this.adultSounds.equals(staticWolfSoundVariant.adultSounds)
                ? false
                : this.babySounds.equals(staticWolfSoundVariant.babySounds);
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.adultSounds, this.babySounds);
    }
}
