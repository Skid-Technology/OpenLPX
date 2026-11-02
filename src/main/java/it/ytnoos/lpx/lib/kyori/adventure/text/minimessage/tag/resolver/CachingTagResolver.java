package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Inserting;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class CachingTagResolver
        implements TagResolver.WithoutArguments, MappableResolver, SerializableResolver {

    private static final Tag NULL_REPLACEMENT =
            (Inserting)
                    () -> {
                        throw new UnsupportedOperationException("no-op null tag");
                    };

    private final Map<String, Tag> cache = new HashMap<>();

    private final TagResolver.WithoutArguments resolver;

    CachingTagResolver(TagResolver.WithoutArguments withoutArguments) {
        this.resolver = withoutArguments;
    }

    private Tag query(@NotNull String text) {
        return this.cache.computeIfAbsent(
                text,
                result -> {
                    Tag tag;
                    return (tag = this.resolver.resolve(result)) == null ? NULL_REPLACEMENT : tag;
                });
    }

    @Nullable
    @Override
    public Tag resolve(@NotNull String text) {
        Tag tag;
        return (tag = this.query(text)) == NULL_REPLACEMENT ? null : tag;
    }

    @Override
    public boolean has(@NotNull String text) {
        return this.query(text) != NULL_REPLACEMENT;
    }

    @Override
    public boolean contributeToMap(@NotNull Map<String, Tag> entries) {
        return this.resolver instanceof MappableResolver
                ? ((MappableResolver) this.resolver).contributeToMap(entries)
                : false;
    }

    @Override
    public void handle(@NotNull Component component, @NotNull ClaimConsumer claimConsumer) {
        if (this.resolver instanceof SerializableResolver) {
            ((SerializableResolver) this.resolver).handle(component, claimConsumer);
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof CachingTagResolver)) {
            return false;
        }
        CachingTagResolver cachingTagResolver = (CachingTagResolver) result;
        return Objects.equals(this.resolver, cachingTagResolver.resolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.resolver);
    }
}
