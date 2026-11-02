package it.ytnoos.lpx.lib.kyori.adventure.text;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

abstract class AbstractNBTComponentBuilder<
                C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
        extends AbstractComponentBuilder<C, B> implements NBTComponentBuilder<C, B> {

    @Nullable protected String nbtPath;

    protected boolean interpret = false;

    @Nullable protected Component separator;

    AbstractNBTComponentBuilder() {}

    AbstractNBTComponentBuilder(@NotNull C c) {
        super((C) c);
        this.nbtPath = c.nbtPath();
        this.interpret = c.interpret();
        this.separator = c.separator();
    }

    @NotNull
    @Override
    public B nbtPath(@NotNull String text) {
        this.nbtPath = Objects.requireNonNull(text, "nbtPath");
        return (B) this;
    }

    @NotNull
    @Override
    public B interpret(boolean interpret) {
        this.interpret = interpret;
        return (B) this;
    }

    @NotNull
    @Override
    public B separator(@Nullable ComponentLike componentLike) {
        this.separator = ComponentLike.unbox(componentLike);
        return (B) this;
    }
}
