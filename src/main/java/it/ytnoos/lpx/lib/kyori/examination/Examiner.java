package it.ytnoos.lpx.lib.kyori.examination;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface Examiner<R> {

    @NotNull
    default R examine(@NotNull Examinable examinable) {
        return this.examine(examinable.examinableName(), examinable.examinableProperties());
    }

    @NotNull
    R examine(@NotNull String text, @NotNull Stream<? extends ExaminableProperty> stream);

    @NotNull
    R examine(@Nullable Object result);

    @NotNull
    R examine(boolean matches);

    @NotNull
    R examine(boolean @Nullable [] matchess);

    @NotNull
    R examine(byte value);

    @NotNull
    R examine(byte @Nullable [] values);

    @NotNull
    R examine(char value);

    @NotNull
    R examine(char @Nullable [] values);

    @NotNull
    R examine(double value);

    @NotNull
    R examine(double @Nullable [] values);

    @NotNull
    R examine(float value);

    @NotNull
    R examine(float @Nullable [] values);

    @NotNull
    R examine(int value);

    @NotNull
    R examine(int @Nullable [] values);

    @NotNull
    R examine(long value);

    @NotNull
    R examine(long @Nullable [] values);

    @NotNull
    R examine(short value);

    @NotNull
    R examine(short @Nullable [] values);

    @NotNull
    R examine(@Nullable String text);
}
