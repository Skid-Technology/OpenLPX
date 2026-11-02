package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.jukebox;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.sound.Sound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class JukeboxSong extends AbstractMappedEntity implements IJukeboxSong {

    private Sound sound;

    private Component description;

    private float lengthInSeconds;

    private int comparatorOutput;

    public JukeboxSong(Sound sound, Component component, float value, int value2) {
        this(null, sound, component, value, value2);
    }

    @Internal
    public JukeboxSong(
            @Nullable TypesBuilderData typesBuilderData,
            Sound sound2,
            Component component,
            float lengthInSeconds,
            int comparatorOutput) {
        super(typesBuilderData);
        this.sound = sound2;
        this.description = component;
        this.lengthInSeconds = lengthInSeconds;
        this.comparatorOutput = comparatorOutput;
    }

    @Deprecated
    public static JukeboxSong read(PacketWrapper<?> packetWrapper) {
        return (JukeboxSong) IJukeboxSong.read(packetWrapper);
    }

    @Deprecated
    public static void write(PacketWrapper<?> packetWrapper, JukeboxSong jukeboxSong) {
        IJukeboxSong.write(packetWrapper, jukeboxSong);
    }

    public IJukeboxSong copy(@Nullable TypesBuilderData typesBuilderData) {
        return new JukeboxSong(
                typesBuilderData,
                this.sound,
                this.description,
                this.lengthInSeconds,
                this.comparatorOutput);
    }

    @Override
    public Sound getSound() {
        return this.sound;
    }

    @Deprecated
    public void setSound(Sound sound2) {
        this.sound = sound2;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    @Deprecated
    public void setDescription(Component component) {
        this.description = component;
    }

    @Override
    public float getLengthInSeconds() {
        return this.lengthInSeconds;
    }

    @Deprecated
    public void setLengthInSeconds(float lengthInSeconds) {
        this.lengthInSeconds = lengthInSeconds;
    }

    @Override
    public int getComparatorOutput() {
        return this.comparatorOutput;
    }

    @Deprecated
    public void setComparatorOutput(int comparatorOutput) {
        this.comparatorOutput = comparatorOutput;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof JukeboxSong)) {
            return false;
        } else {
            JukeboxSong jukeboxSong;
            if (Float.compare(
                            (jukeboxSong = (JukeboxSong) result).lengthInSeconds,
                            this.lengthInSeconds)
                    != 0) {
                return false;
            } else if (this.comparatorOutput != jukeboxSong.comparatorOutput) {
                return false;
            } else {
                return !this.sound.equals(jukeboxSong.sound)
                        ? false
                        : this.description.equals(jukeboxSong.description);
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.sound, this.description, this.lengthInSeconds, this.comparatorOutput);
    }

    @Override
    public String toString() {
        return "JukeboxSong{sound="
                + this.sound
                + ", description="
                + this.description
                + ", lengthInSeconds="
                + this.lengthInSeconds
                + ", comparatorOutput="
                + this.comparatorOutput
                + 1249282199;
    }
}
