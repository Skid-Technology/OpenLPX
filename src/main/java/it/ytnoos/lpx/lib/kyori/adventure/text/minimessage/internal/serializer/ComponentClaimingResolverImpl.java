package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

class ComponentClaimingResolverImpl implements TagResolver, SerializableResolver.Single {

    @NotNull private final Set<String> names;

    @NotNull private final BiFunction<ArgumentQueue, Context, Tag> handler;

    @NotNull private final Function<Component, @Nullable Emitable> componentClaim;

    ComponentClaimingResolverImpl(
            Set<String> names,
            BiFunction<ArgumentQueue, Context, Tag> biFunction,
            Function<Component, @Nullable Emitable> function) {
        this.names = names;
        this.handler = biFunction;
        this.componentClaim = function;
    }

    @Nullable
    @Override
    public Tag resolve(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
            throws ParsingException {
        return !this.names.contains(text) ? null : this.handler.apply(argumentQueue, context);
    }

    @Override
    public boolean has(@NotNull String text) {
        return this.names.contains(text);
    }

    @Nullable
    @Override
    public Emitable claimComponent(@NotNull Component component) {
        return this.componentClaim.apply(component);
    }
}
