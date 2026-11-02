package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

final class MapTagResolver implements TagResolver.WithoutArguments, MappableResolver {

    private final Map<String, ? extends Tag> tagMap;

    MapTagResolver(@NotNull Map<String, ? extends Tag> entries) {
        this.tagMap = entries;
    }

    @Nullable
    @Override
    public Tag resolve(@NotNull String text) {
        return this.tagMap.get(text);
    }

    @Override
    public boolean has(@NotNull String text) {
        return this.tagMap.containsKey(text);
    }

    @Override
    public boolean contributeToMap(@NotNull Map<String, Tag> entries) {
        entries.putAll(this.tagMap);
        return true;
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof MapTagResolver)) {
            return false;
        }
        MapTagResolver mapTagResolver = (MapTagResolver) result;
        return Objects.equals(this.tagMap, mapTagResolver.tagMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tagMap);
    }
}
