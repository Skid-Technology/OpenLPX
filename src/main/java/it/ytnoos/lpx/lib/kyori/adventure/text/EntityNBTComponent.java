package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface EntityNBTComponent
        extends NBTComponent<EntityNBTComponent, EntityNBTComponent.Builder>,
                ScopedComponent<EntityNBTComponent> {

    @NotNull
    String selector();

    @Contract(pure = true)
    @NotNull
    EntityNBTComponent selector(@NotNull String text);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("selector", this.selector())),
                NBTComponent.super.examinableProperties());
    }

    interface Builder extends NBTComponentBuilder<EntityNBTComponent, EntityNBTComponent.Builder> {

        @Contract("_ -> this")
        @NotNull
        EntityNBTComponent.Builder selector(@NotNull String text);
    }
}
