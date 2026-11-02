package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.stream.Stream;

final class ClickCallbackOptionsImpl implements ClickCallback.Options {

    static final ClickCallback.Options DEFAULT = new ClickCallbackOptionsImpl.BuilderImpl().build();

    private final int uses;

    private final Duration lifetime;

    ClickCallbackOptionsImpl(int uses, Duration duration) {
        this.uses = uses;
        this.lifetime = duration;
    }

    @Override
    public int uses() {
        return this.uses;
    }

    @NotNull
    @Override
    public Duration lifetime() {
        return this.lifetime;
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("uses", this.uses),
                ExaminableProperty.of("expiration", this.lifetime));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static final class BuilderImpl implements ClickCallback.Options.Builder {

        private int uses;

        private Duration lifetime;

        BuilderImpl() {
            this.uses = 1;
            this.lifetime = ClickCallback.DEFAULT_LIFETIME;
        }

        BuilderImpl(ClickCallback.@NotNull Options options) {
            this.uses = options.uses();
            this.lifetime = options.lifetime();
        }

        public ClickCallback.@NotNull Options build() {
            return new ClickCallbackOptionsImpl(this.uses, this.lifetime);
        }

        @NotNull
        @Override
        public ClickCallback.Options.Builder uses(int uses) {
            this.uses = uses;
            return this;
        }

        @NotNull
        @Override
        public ClickCallback.Options.Builder lifetime(@NotNull TemporalAmount temporalAmount) {
            this.lifetime =
                    temporalAmount instanceof Duration
                            ? (Duration) temporalAmount
                            : Duration.from(Objects.requireNonNull(temporalAmount, "lifetime"));
            return this;
        }
    }
}
