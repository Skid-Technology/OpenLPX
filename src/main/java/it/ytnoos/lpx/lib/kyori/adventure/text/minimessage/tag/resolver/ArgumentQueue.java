package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@NonExtendable
public interface ArgumentQueue {

    Tag.@NotNull Argument pop();

    Tag.@NotNull Argument popOr(@NotNull String text);

    Tag.@NotNull Argument popOr(@NotNull Supplier<String> supplier);

    Tag.@Nullable Argument peek();

    boolean hasNext();

    void reset();
}
