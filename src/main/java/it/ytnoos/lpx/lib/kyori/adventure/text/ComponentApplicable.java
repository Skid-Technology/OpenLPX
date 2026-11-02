package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ComponentApplicable {

    @NotNull
    Component componentApply(@NotNull Component component);
}
