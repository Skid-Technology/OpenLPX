package it.ytnoos.lpx.lib.kyori.adventure.title;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Ticks;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.time.Duration;

@NonExtendable
public interface Title extends Examinable {

    Title.Times DEFAULT_TIMES =
            Title.Times.times(Ticks.duration(10L), Ticks.duration(70L), Ticks.duration(20L));

    @NotNull
    static Title title(@NotNull Component component, @NotNull Component component2) {
        return title(component, component2, DEFAULT_TIMES);
    }

    @NotNull
    static Title title(
            @NotNull Component component,
            @NotNull Component component2,
            @Nullable Title.Times times) {
        return new TitleImpl(component, component2, times);
    }

    @NotNull
    static Title title(
            @NotNull Component component,
            @NotNull Component component2,
            int value,
            int value2,
            int value3) {
        return new TitleImpl(
                component,
                component2,
                Title.Times.times(
                        Ticks.duration(value), Ticks.duration(value2), Ticks.duration(value3)));
    }

    @NotNull
    Component title();

    @NotNull
    Component subtitle();

    @Nullable
    Title.Times times();

    @UnknownNullability
    <T> T part(@NotNull TitlePart<T> titlePart);

    @NonExtendable
    interface Times extends Examinable {

        @Deprecated
        @ScheduledForRemoval(inVersion = "5.0.0")
        @NotNull
        static Title.Times of(
                @NotNull Duration duration,
                @NotNull Duration duration2,
                @NotNull Duration duration3) {
            return times(duration, duration2, duration3);
        }

        @NotNull
        static Title.Times times(
                @NotNull Duration duration,
                @NotNull Duration duration2,
                @NotNull Duration duration3) {
            return new TitleImpl.TimesImpl(duration, duration2, duration3);
        }

        @NotNull
        Duration fadeIn();

        @NotNull
        Duration stay();

        @NotNull
        Duration fadeOut();
    }
}
