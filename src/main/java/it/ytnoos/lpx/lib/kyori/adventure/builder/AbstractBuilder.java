package it.ytnoos.lpx.lib.kyori.adventure.builder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@FunctionalInterface
public interface AbstractBuilder<R> {

    @Contract(mutates = "param1")
    @NotNull
    static <R, B extends AbstractBuilder<R>> R configureAndBuild(
            @NotNull B b, @Nullable Consumer<? super B> consumer) {
        if (consumer != null) {
            consumer.accept(b);
        }
        return (R) b.build();
    }

    @Contract(value = "-> new", pure = true)
    @NotNull
    R build();
}
