package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;

import org.jetbrains.annotations.NotNull;

@Deprecated
public interface BuildableComponent<
                C extends BuildableComponent<C, B>, B extends ComponentBuilder<C, B>>
        extends Buildable<C, B>, Component {

    @NotNull
    @Override
    B toBuilder();
}
