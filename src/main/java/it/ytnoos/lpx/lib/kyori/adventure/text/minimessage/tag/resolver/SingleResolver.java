package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

final class SingleResolver implements TagResolver.Single, MappableResolver {

    private final String key;

    private final Tag tag;

    SingleResolver(String key, Tag tag2) {
        this.key = key;
        this.tag = tag2;
    }

    @NotNull
    @Override
    public String key() {
        return this.key;
    }

    @NotNull
    @Override
    public Tag tag() {
        return this.tag;
    }

    @Override
    public boolean has(@NotNull String text) {
        return this.key.equals(text);
    }

    @Override
    public boolean contributeToMap(@NotNull Map<String, Tag> entries) {
        entries.put(this.key, this.tag);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.tag);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (result == null) {
            return false;
        }
        if (this.getClass() != result.getClass()) {
            return false;
        }
        SingleResolver singleResolver = (SingleResolver) result;
        return Objects.equals(this.key, singleResolver.key)
                && Objects.equals(this.tag, singleResolver.tag);
    }
}
