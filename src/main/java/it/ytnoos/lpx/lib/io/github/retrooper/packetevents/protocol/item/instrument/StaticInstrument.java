package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.instrument;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticInstrument extends AbstractMappedEntity implements Instrument {

    private final Sound sound;

    private final float useSeconds;

    private final float range;

    private final Component description;

    @Deprecated
    public StaticInstrument(Sound sound, int value, float value2) {
        this(sound, value * 20.0F, value2, Component.empty());
    }

    public StaticInstrument(Sound sound, float value, float value2, Component component) {
        this(null, sound, value, value2, component);
    }

    @Internal
    public StaticInstrument(
            @Nullable TypesBuilderData typesBuilderData,
            Sound sound2,
            float useSeconds,
            float range,
            Component component) {
        super(typesBuilderData);
        this.sound = sound2;
        this.useSeconds = useSeconds;
        this.range = range;
        this.description = component;
    }

    public Instrument copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticInstrument(
                typesBuilderData, this.sound, this.useSeconds, this.range, this.description);
    }

    @Override
    public Sound getSound() {
        return this.sound;
    }

    @Override
    public float getUseSeconds() {
        return this.useSeconds;
    }

    @Override
    public float getRange() {
        return this.range;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticInstrument)) {
            return false;
        } else {
            StaticInstrument staticInstrument = (StaticInstrument) result;
            if (this.useSeconds != staticInstrument.useSeconds) {
                return false;
            } else if (Float.compare(staticInstrument.range, this.range) != 0) {
                return false;
            } else {
                return !this.sound.equals(staticInstrument.sound)
                        ? false
                        : this.description.equals(staticInstrument.description);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(this.sound, this.useSeconds, this.range, this.description);
    }
}
