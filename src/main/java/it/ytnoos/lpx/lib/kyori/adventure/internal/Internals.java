package it.ytnoos.lpx.lib.kyori.adventure.internal;

import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.string.StringExaminer;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public final class Internals {

    private Internals() {}

    @NotNull
    public static String toString(@NotNull Examinable examinable) {
        return examinable.examine(StringExaminer.simpleEscaping());
    }
}
