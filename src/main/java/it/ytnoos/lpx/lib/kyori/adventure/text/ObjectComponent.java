package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface ObjectComponent
        extends BuildableComponent<ObjectComponent, ObjectComponent.Builder>,
                ScopedComponent<ObjectComponent> {

    @NotNull
    ObjectContents contents();

    @NotNull
    ObjectComponent contents(@NotNull ObjectContents objectContents);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("contents", this.contents())),
                BuildableComponent.super.examinableProperties());
    }

    interface Builder extends ComponentBuilder<ObjectComponent, ObjectComponent.Builder> {

        @NotNull
        ObjectComponent.Builder contents(@NotNull ObjectContents objectContents);
    }
}
