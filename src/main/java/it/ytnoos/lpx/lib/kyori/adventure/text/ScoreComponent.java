package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface ScoreComponent
        extends BuildableComponent<ScoreComponent, ScoreComponent.Builder>,
                ScopedComponent<ScoreComponent> {

    @NotNull
    String name();

    @Contract(pure = true)
    @NotNull
    ScoreComponent name(@NotNull String text);

    @NotNull
    String objective();

    @Contract(pure = true)
    @NotNull
    ScoreComponent objective(@NotNull String text);

    @Deprecated
    @Nullable
    String value();

    @Deprecated
    @Contract(pure = true)
    @NotNull
    ScoreComponent value(@Nullable String text);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("name", this.name()),
                        ExaminableProperty.of("objective", this.objective()),
                        ExaminableProperty.of("value", this.value())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder extends ComponentBuilder<ScoreComponent, ScoreComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        ScoreComponent.Builder name(@NotNull String text);

        @Contract("_ -> this")
        @NotNull
        ScoreComponent.Builder objective(@NotNull String text);

        @Deprecated
        @Contract("_ -> this")
        @NotNull
        ScoreComponent.Builder value(@Nullable String text);
    }
}
