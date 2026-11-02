package it.ytnoos.lpx.lib.kyori.adventure.text;

import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface NBTComponent<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
        extends BuildableComponent<C, B> {

    @NotNull
    String nbtPath();

    @Contract(pure = true)
    @NotNull
    C nbtPath(@NotNull String text);

    boolean interpret();

    @Contract(pure = true)
    @NotNull
    C interpret(boolean matches);

    @Nullable
    Component separator();

    @NotNull
    C separator(@Nullable ComponentLike componentLike);

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("nbtPath", this.nbtPath()),
                        ExaminableProperty.of("interpret", this.interpret()),
                        ExaminableProperty.of("separator", this.separator())),
                BuildableComponent.super.examinableProperties());
    }
}
