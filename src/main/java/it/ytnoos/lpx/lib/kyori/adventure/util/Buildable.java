package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface Buildable<R, B extends Buildable.Builder<R>> {

    @Deprecated
    @Contract(mutates = "param1")
    @NotNull
    static <R extends Buildable<R, B>, B extends Buildable.Builder<R>> R configureAndBuild(
            @NotNull B b, @Nullable Consumer<? super B> consumer) {
        return AbstractBuilder.configureAndBuild(b, consumer);
    }

    @Contract(value = "-> new", pure = true)
    @NotNull
    B toBuilder();

    @Deprecated
    interface Builder<R> extends AbstractBuilder<R> {

        @Contract(value = "-> new", pure = true)
        @NotNull
        @Override
        R build();
    }
}
