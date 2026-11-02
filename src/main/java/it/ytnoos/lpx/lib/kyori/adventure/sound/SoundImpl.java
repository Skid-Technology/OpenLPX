package it.ytnoos.lpx.lib.kyori.adventure.sound;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.util.ShadyPines;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class SoundImpl implements Sound {

    static final Sound.Emitter EMITTER_SELF =
            new Sound.Emitter() {

                @Override
                public String toString() {
                    return "SelfSoundEmitter";
                }
            };

    private final Sound.Source source;

    private final float volume;

    private final float pitch;

    private final OptionalLong seed;

    private SoundStop stop;

    SoundImpl(@NotNull Sound.Source source2, float volume, float pitch, OptionalLong optionalLong) {
        this.source = source2;
        this.volume = volume;
        this.pitch = pitch;
        this.seed = optionalLong;
    }

    @NotNull
    @Override
    public Sound.Source source() {
        return this.source;
    }

    @Override
    public float volume() {
        return this.volume;
    }

    @Override
    public float pitch() {
        return this.pitch;
    }

    @NotNull
    @Override
    public OptionalLong seed() {
        return this.seed;
    }

    @NotNull
    @Override
    public SoundStop asStop() {
        if (this.stop == null) {
            this.stop = SoundStop.namedOnSource(this.name(), this.source());
        }
        return this.stop;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof SoundImpl)) {
            return false;
        }
        SoundImpl soundImpl = (SoundImpl) result;
        return this.name().equals(soundImpl.name())
                && this.source == soundImpl.source
                && ShadyPines.equals(this.volume, soundImpl.volume)
                && ShadyPines.equals(this.pitch, soundImpl.pitch)
                && this.seed.equals(soundImpl.seed);
    }

    @Override
    public int hashCode() {
        int value = this.name().hashCode();
        value = 31 * value + this.source.hashCode();
        value = 31 * value + Float.hashCode(this.volume);
        value = 31 * value + Float.hashCode(this.pitch);
        return 31 * value + this.seed.hashCode();
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("name", this.name()),
                ExaminableProperty.of("source", this.source),
                ExaminableProperty.of("volume", this.volume),
                ExaminableProperty.of("pitch", this.pitch),
                ExaminableProperty.of("seed", this.seed));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class BuilderImpl implements Sound.Builder {

        private Key eagerType;

        private Supplier<? extends Sound.Type> lazyType;

        private Sound.Source source = Sound.Source.MASTER;

        private float volume = 1.0F;

        private float pitch = 1.0F;

        private OptionalLong seed = OptionalLong.empty();

        BuilderImpl() {}

        BuilderImpl(@NotNull Sound sound) {
            if (sound instanceof SoundImpl.Eager) {
                this.type(((SoundImpl.Eager) sound).name);
            } else {
                if (!(sound instanceof SoundImpl.Lazy)) {
                    throw new IllegalArgumentException(
                            "Unknown sound type " + sound + ", must be Eager or Lazy");
                }
                this.type(((SoundImpl.Lazy) sound).supplier);
            }
            this.source(sound.source())
                    .volume(sound.volume())
                    .pitch(sound.pitch())
                    .seed(sound.seed());
        }

        @NotNull
        @Override
        public Sound.Builder type(@NotNull Key key) {
            this.eagerType = Objects.requireNonNull(key, "type");
            this.lazyType = null;
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder type(@NotNull Sound.Type type2) {
            this.eagerType =
                    Objects.requireNonNull(
                            Objects.requireNonNull(type2, "type").key(), "type.key()");
            this.lazyType = null;
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder type(@NotNull Supplier<? extends Sound.Type> supplier) {
            this.lazyType = Objects.requireNonNull(supplier, "typeSupplier");
            this.eagerType = null;
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder source(@NotNull Sound.Source source2) {
            this.source = Objects.requireNonNull(source2, "source");
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder source(Sound.Source.@NotNull Provider provider) {
            return this.source(provider.soundSource());
        }

        @NotNull
        @Override
        public Sound.Builder volume(@Range(from = 0L, to = 2147483647L) float volume) {
            this.volume = volume;
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder pitch(@Range(from = -1L, to = 1L) float pitch) {
            this.pitch = pitch;
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder seed(long value) {
            this.seed = OptionalLong.of(value);
            return this;
        }

        @NotNull
        @Override
        public Sound.Builder seed(@NotNull OptionalLong optionalLong) {
            this.seed = Objects.requireNonNull(optionalLong, "seed");
            return this;
        }

        @NotNull
        public Sound build() {
            if (this.eagerType != null) {
                return new SoundImpl.Eager(
                        this.eagerType, this.source, this.volume, this.pitch, this.seed);
            } else if (this.lazyType != null) {
                return new SoundImpl.Lazy(
                        this.lazyType, this.source, this.volume, this.pitch, this.seed);
            } else {
                throw new IllegalStateException("A sound type must be provided to build a sound");
            }
        }
    }

    static final class Eager extends SoundImpl {

        final Key name;

        Eager(
                @NotNull Key key,
                @NotNull Sound.Source source,
                float value,
                float value2,
                OptionalLong optionalLong) {
            super(source, value, value2, optionalLong);
            this.name = key;
        }

        @NotNull
        @Override
        public Key name() {
            return this.name;
        }
    }

    static final class Lazy extends SoundImpl {

        final Supplier<? extends Sound.Type> supplier;

        Lazy(
                @NotNull Supplier<? extends Sound.Type> supplier2,
                @NotNull Sound.Source source,
                float value,
                float value2,
                OptionalLong optionalLong) {
            super(source, value, value2, optionalLong);
            this.supplier = supplier2;
        }

        @NotNull
        @Override
        public Key name() {
            return this.supplier.get().key();
        }
    }
}
