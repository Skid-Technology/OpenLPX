package it.ytnoos.lpx.lib.kyori.examination;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface Examinable {

    @NotNull
    default String examinableName() {
        return this.getClass().getSimpleName();
    }

    @NotNull
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.empty();
    }

    @NotNull
    default <R> R examine(@NotNull Examiner<R> examiner) {
        return (R) examiner.examine(this);
    }
}
