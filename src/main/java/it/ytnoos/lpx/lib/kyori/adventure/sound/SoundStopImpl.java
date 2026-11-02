package it.ytnoos.lpx.lib.kyori.adventure.sound;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

abstract class SoundStopImpl implements SoundStop {

    static final SoundStop ALL =
            new SoundStopImpl(null) {

                @Nullable
                @Override
                public Key sound() {
                    return null;
                }
            };

    private final Sound.@Nullable Source source;

    SoundStopImpl(Sound.@Nullable Source source2) {
        this.source = source2;
    }

    @Override
    public Sound.@Nullable Source source() {
        return this.source;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof SoundStopImpl)) {
            return false;
        }
        SoundStopImpl soundStopImpl = (SoundStopImpl) result;
        return Objects.equals(this.sound(), soundStopImpl.sound())
                && Objects.equals(this.source, soundStopImpl.source);
    }

    @Override
    public int hashCode() {
        int value = Objects.hashCode(this.sound());
        return 31 * value + Objects.hashCode(this.source);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("name", this.sound()),
                ExaminableProperty.of("source", this.source));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }
}
