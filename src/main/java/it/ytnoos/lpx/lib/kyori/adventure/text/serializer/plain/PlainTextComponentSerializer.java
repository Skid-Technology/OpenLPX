package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.plain;

import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.ComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface PlainTextComponentSerializer
        extends ComponentSerializer<Component, TextComponent, String>,
                Buildable<PlainTextComponentSerializer, PlainTextComponentSerializer.Builder> {

    @NotNull
    static PlainTextComponentSerializer plainText() {
        return PlainTextComponentSerializerImpl.Instances.INSTANCE;
    }

    static PlainTextComponentSerializer.@NotNull Builder builder() {
        return new PlainTextComponentSerializerImpl.BuilderImpl();
    }

    @NotNull
    default TextComponent deserialize(@NotNull String text2) {
        return Component.text(text2);
    }

    @NotNull
    default String serialize(@NotNull Component component) {
        StringBuilder stringBuilder = new StringBuilder();
        this.serialize(stringBuilder, component);
        return stringBuilder.toString();
    }

    void serialize(@NotNull StringBuilder stringBuilder, @NotNull Component component);

    interface Builder
            extends AbstractBuilder<PlainTextComponentSerializer>,
                    Buildable.Builder<PlainTextComponentSerializer> {

        @NotNull
        PlainTextComponentSerializer.Builder flattener(
                @NotNull ComponentFlattener componentFlattener);
    }

    @PlatformAPI
    @Internal
    interface Provider {

        @PlatformAPI
        @Internal
        @NotNull
        PlainTextComponentSerializer plainTextSimple();

        @PlatformAPI
        @Internal
        @NotNull
        Consumer<PlainTextComponentSerializer.Builder> plainText();
    }
}
