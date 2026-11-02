package it.ytnoos.lpx.lib.kyori.adventure.sound;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.key.Keyed;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.OptionalLong;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NonExtendable
public interface Sound extends Examinable {

    @NotNull
    static Sound.Builder sound() {
        return new SoundImpl.BuilderImpl();
    }

    @NotNull
    static Sound.Builder sound(@NotNull Sound sound2) {
        return new SoundImpl.BuilderImpl(sound2);
    }

    @NotNull
    static Sound sound(@NotNull Consumer<Sound.Builder> consumer) {
        return AbstractBuilder.configureAndBuild(sound(), consumer);
    }

    @NotNull
    static Sound sound(@NotNull Key key, @NotNull Sound.Source source2, float value, float value2) {
        return sound().type(key).source(source2).volume(value).pitch(value2).build();
    }

    @NotNull
    static Sound sound(
            @NotNull Sound.Type type, @NotNull Sound.Source source, float value, float value2) {
        Objects.requireNonNull(type, "type");
        return sound(type.key(), source, value, value2);
    }

    @NotNull
    static Sound sound(
            @NotNull Supplier<? extends Sound.Type> supplier,
            @NotNull Sound.Source source2,
            float value,
            float value2) {
        return sound().type(supplier).source(source2).volume(value).pitch(value2).build();
    }

    @NotNull
    static Sound sound(
            @NotNull Key key, Sound.Source.@NotNull Provider provider, float value, float value2) {
        return sound(key, provider.soundSource(), value, value2);
    }

    @NotNull
    static Sound sound(
            @NotNull Sound.Type type,
            Sound.Source.@NotNull Provider provider,
            float value,
            float value2) {
        return sound(type, provider.soundSource(), value, value2);
    }

    @NotNull
    static Sound sound(
            @NotNull Supplier<? extends Sound.Type> supplier,
            Sound.Source.@NotNull Provider provider,
            float value,
            float value2) {
        return sound(supplier, provider.soundSource(), value, value2);
    }

    @NotNull
    Key name();

    @NotNull
    Sound.Source source();

    float volume();

    float pitch();

    @NotNull
    OptionalLong seed();

    @NotNull
    SoundStop asStop();

    @NonExtendable
    interface Builder extends AbstractBuilder<Sound> {

        @NotNull
        Sound.Builder type(@NotNull Key key);

        @NotNull
        Sound.Builder type(@NotNull Sound.Type type2);

        @NotNull
        Sound.Builder type(@NotNull Supplier<? extends Sound.Type> supplier);

        @NotNull
        Sound.Builder source(@NotNull Sound.Source source2);

        @NotNull
        Sound.Builder source(Sound.Source.@NotNull Provider provider);

        @NotNull
        Sound.Builder volume(@Range(from = 0L, to = 2147483647L) float value);

        @NotNull
        Sound.Builder pitch(@Range(from = -1L, to = 1L) float value);

        @NotNull
        Sound.Builder seed(long value);

        @NotNull
        Sound.Builder seed(@NotNull OptionalLong optionalLong);
    }

    interface Emitter {

        @NotNull
        static Sound.Emitter self() {
            return SoundImpl.EMITTER_SELF;
        }
    }

    enum Source {
        MASTER("master"),
        MUSIC("music"),
        RECORD("record"),
        WEATHER("weather"),
        BLOCK("block"),
        HOSTILE("hostile"),
        NEUTRAL("neutral"),
        PLAYER("player"),
        AMBIENT("ambient"),
        VOICE("voice"),
        UI("ui");

        public static final Index<String, Sound.Source> NAMES =
                Index.create(Sound.Source.class, result -> result.name);

        private final String name;

        Source(String name) {
            this.name = name;
        }

        public interface Provider {

            @NotNull
            Sound.Source soundSource();
        }
    }

    interface Type extends Keyed {

        @NotNull
        @Override
        Key key();
    }
}
