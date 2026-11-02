package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NBTComponentBuilder<
                C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
        extends ComponentBuilder<C, B> {

    @Contract("_ -> this")
    @NotNull
    B nbtPath(@NotNull String text);

    @Contract("_ -> this")
    @NotNull
    B interpret(boolean matches);

    @Contract("_ -> this")
    @NotNull
    B separator(@Nullable ComponentLike componentLike);
}
