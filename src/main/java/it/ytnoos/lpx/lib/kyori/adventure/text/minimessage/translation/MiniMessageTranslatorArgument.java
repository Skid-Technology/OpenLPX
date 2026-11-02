package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.VirtualComponentRenderer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.TagPattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Objects;

final class MiniMessageTranslatorArgument<T> implements VirtualComponentRenderer<Void> {

    @NotNull private final String name;

    @NotNull private final T data;

    MiniMessageTranslatorArgument(@TagPattern @NotNull String name, @NotNull T t) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(t, "data");
        TagInternals.assertValidTagName(name);
        this.name = name;
        this.data = (T) t;
    }

    @NotNull
    public String name() {
        return this.name;
    }

    @NotNull
    public T data() {
        return this.data;
    }

    @UnknownNullability
    public ComponentLike apply(@NotNull Void voidValue) {
        return this.data instanceof ComponentLike ? (ComponentLike) this.data : null;
    }
}
