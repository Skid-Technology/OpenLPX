package it.ytnoos.lpx.lib.kyori.adventure.title;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;

final class TitleImpl implements Title {

    private final Component title;

    private final Component subtitle;

    @Nullable private final Title.Times times;

    TitleImpl(
            @NotNull Component component,
            @NotNull Component component2,
            @Nullable Title.Times times2) {
        this.title = Objects.requireNonNull(component, "title");
        this.subtitle = Objects.requireNonNull(component2, "subtitle");
        this.times = times2;
    }

    @NotNull
    @Override
    public Component title() {
        return this.title;
    }

    @NotNull
    @Override
    public Component subtitle() {
        return this.subtitle;
    }

    @Nullable
    @Override
    public Title.Times times() {
        return this.times;
    }

    @Override
    @UnknownNullability
    public <T> T part(@NotNull TitlePart<T> titlePart) {
        Objects.requireNonNull(titlePart, "part");
        if (titlePart == TitlePart.TITLE) {
            return (T) this.title;
        } else if (titlePart == TitlePart.SUBTITLE) {
            return (T) this.subtitle;
        } else if (titlePart == TitlePart.TIMES) {
            return (T) this.times;
        } else {
            throw new IllegalArgumentException("Don't know what " + titlePart + " is.");
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            TitleImpl titleImpl = (TitleImpl) result;
            return this.title.equals(titleImpl.title)
                    && this.subtitle.equals(titleImpl.subtitle)
                    && Objects.equals(this.times, titleImpl.times);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int value = this.title.hashCode();
        value = 31 * value + this.subtitle.hashCode();
        return 31 * value + Objects.hashCode(this.times);
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("title", this.title),
                ExaminableProperty.of("subtitle", this.subtitle),
                ExaminableProperty.of("times", this.times));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    static class TimesImpl implements Title.Times {

        private final Duration fadeIn;

        private final Duration stay;

        private final Duration fadeOut;

        TimesImpl(
                @NotNull Duration duration,
                @NotNull Duration duration2,
                @NotNull Duration duration3) {
            this.fadeIn = Objects.requireNonNull(duration, "fadeIn");
            this.stay = Objects.requireNonNull(duration2, "stay");
            this.fadeOut = Objects.requireNonNull(duration3, "fadeOut");
        }

        @NotNull
        @Override
        public Duration fadeIn() {
            return this.fadeIn;
        }

        @NotNull
        @Override
        public Duration stay() {
            return this.stay;
        }

        @NotNull
        @Override
        public Duration fadeOut() {
            return this.fadeOut;
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            }
            if (!(result instanceof TitleImpl.TimesImpl)) {
                return false;
            }
            TitleImpl.TimesImpl timesImpl = (TitleImpl.TimesImpl) result;
            return this.fadeIn.equals(timesImpl.fadeIn)
                    && this.stay.equals(timesImpl.stay)
                    && this.fadeOut.equals(timesImpl.fadeOut);
        }

        @Override
        public int hashCode() {
            int value = this.fadeIn.hashCode();
            value = 31 * value + this.stay.hashCode();
            return 31 * value + this.fadeOut.hashCode();
        }

        @NotNull
        @Override
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("fadeIn", this.fadeIn),
                    ExaminableProperty.of("stay", this.stay),
                    ExaminableProperty.of("fadeOut", this.fadeOut));
        }

        @Override
        public String toString() {
            return Internals.toString(this);
        }
    }
}
