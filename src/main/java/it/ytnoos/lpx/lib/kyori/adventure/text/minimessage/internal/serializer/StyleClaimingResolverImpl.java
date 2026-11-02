package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.BiFunction;

final class StyleClaimingResolverImpl implements TagResolver, SerializableResolver.Single {

    @NotNull private final Set<String> names;

    @NotNull private final BiFunction<ArgumentQueue, Context, Tag> handler;

    @NotNull private final StyleClaim<?> styleClaim;

    StyleClaimingResolverImpl(
            @NotNull Set<String> names,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction,
            @NotNull StyleClaim<?> styleClaim2) {
        this.names = names;
        this.handler = biFunction;
        this.styleClaim = styleClaim2;
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
    public StyleClaim<?> claimStyle() {
        return this.styleClaim;
    }
}
