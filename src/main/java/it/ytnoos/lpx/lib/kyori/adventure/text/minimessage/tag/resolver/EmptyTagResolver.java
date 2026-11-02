package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

final class EmptyTagResolver implements TagResolver, MappableResolver, SerializableResolver {

    static final EmptyTagResolver INSTANCE = new EmptyTagResolver();

    private EmptyTagResolver() {}

    @Nullable
    @Override
    public Tag resolve(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue, @NotNull Context context) {
        return null;
    }

    @Override
    public boolean has(@NotNull String text) {
        return false;
    }

    @Override
    public boolean contributeToMap(@NotNull Map<String, Tag> entries) {
        return true;
    }

    @Override
    public void handle(@NotNull Component component, @NotNull ClaimConsumer claimConsumer) {}
}
