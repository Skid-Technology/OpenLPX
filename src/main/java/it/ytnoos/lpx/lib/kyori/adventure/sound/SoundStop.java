package it.ytnoos.lpx.lib.kyori.adventure.sound;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

@NonExtendable
public interface SoundStop extends Examinable {

    @NotNull
    static SoundStop all() {
        return SoundStopImpl.ALL;
    }

    @NotNull
    static SoundStop named(@NotNull final Key key) {
        Objects.requireNonNull(key, "sound");
        return new SoundStopImpl(null) {

            @NotNull
            @Override
            public Key sound() {
                return key;
            }
        };
    }

    @NotNull
    static SoundStop named(final Sound.@NotNull Type type) {
        Objects.requireNonNull(type, "sound");
        return new SoundStopImpl(null) {

            @NotNull
            @Override
            public Key sound() {
                return type.key();
            }
        };
    }

    @NotNull
    static SoundStop named(@NotNull final Supplier<? extends Sound.Type> supplier) {
        Objects.requireNonNull(supplier, "sound");
        return new SoundStopImpl(null) {

            @NotNull
            @Override
            public Key sound() {
                return ((Sound.Type) supplier.get()).key();
            }
        };
    }

    @NotNull
    static SoundStop source(Sound.@NotNull Source source2) {
        Objects.requireNonNull(source2, "source");
        return new SoundStopImpl(source2) {

            @Nullable
            @Override
            public Key sound() {
                return null;
            }
        };
    }

    @NotNull
    static SoundStop namedOnSource(@NotNull final Key key, Sound.@NotNull Source source) {
        Objects.requireNonNull(key, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) {

            @NotNull
            @Override
            public Key sound() {
                return key;
            }
        };
    }

    @NotNull
    static SoundStop namedOnSource(Sound.@NotNull Type type, Sound.@NotNull Source source) {
        Objects.requireNonNull(type, "sound");
        return namedOnSource(type.key(), source);
    }

    @NotNull
    static SoundStop namedOnSource(
            @NotNull final Supplier<? extends Sound.Type> supplier, Sound.@NotNull Source source) {
        Objects.requireNonNull(supplier, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source) {

            @NotNull
            @Override
            public Key sound() {
                return ((Sound.Type) supplier.get()).key();
            }
        };
    }

    @Nullable
    Key sound();

    Sound.@Nullable Source source();
}
