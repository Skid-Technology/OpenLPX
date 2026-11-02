package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;

final class TagResolverBuilderImpl implements TagResolver.Builder {

    static final Collector<TagResolver, TagResolver.Builder, TagResolver> COLLECTOR =
            Collector.of(
                    TagResolver::builder,
                    TagResolver.Builder::resolver,
                    (result, result2) ->
                            TagResolver.builder().resolvers(result.build(), result2.build()),
                    TagResolver.Builder::build);

    private final Map<String, Tag> replacements = new HashMap<>();

    private final List<TagResolver> resolvers = new ArrayList<>();

    @Override
    public TagResolver.@NotNull Builder tag(@NotNull String text, @NotNull Tag tag2) {
        TagInternals.assertValidTagName(Objects.requireNonNull(text, "name"));
        this.replacements.put(text, Objects.requireNonNull(tag2, "tag"));
        return this;
    }

    @Override
    public TagResolver.@NotNull Builder resolver(@NotNull TagResolver tagResolver) {
        if (tagResolver instanceof SequentialTagResolver) {
            this.resolvers(((SequentialTagResolver) tagResolver).resolvers, false);
        } else if (!this.consumePotentialMappable(tagResolver)) {
            this.popMap();
            this.resolvers.add(Objects.requireNonNull(tagResolver, "resolver"));
        }
        return this;
    }

    @Override
    public TagResolver.@NotNull Builder resolvers(@NotNull TagResolver... tagResolver) {
        return this.resolvers(tagResolver, true);
    }

    private TagResolver.@NotNull Builder resolvers(
            @NotNull TagResolver[] tagResolvers, boolean matches) {
        boolean matches2 = false;
        Objects.requireNonNull(tagResolvers, "resolvers");
        if (matches) {
            int value = tagResolvers.length;
            for (int index = 0; index < value; index++) {
                TagResolver tagResolver = tagResolvers[index];
                matches2 = this.single(tagResolver, matches2);
            }
        } else {
            for (int value2 = tagResolvers.length - 1; value2 >= 0; value2--) {
                matches2 = this.single(tagResolvers[value2], matches2);
            }
        }
        return this;
    }

    @Override
    public TagResolver.@NotNull Builder resolvers(
            @NotNull Iterable<? extends TagResolver> iterable) {
        boolean matches = false;
        for (TagResolver tagResolver : Objects.requireNonNull(iterable, "resolvers")) {
            matches = this.single(tagResolver, matches);
        }
        return this;
    }

    private boolean single(TagResolver tagResolver, boolean matches) {
        if (tagResolver instanceof SequentialTagResolver) {
            this.resolvers(((SequentialTagResolver) tagResolver).resolvers, false);
        } else if (!this.consumePotentialMappable(tagResolver)) {
            if (!matches) {
                this.popMap();
            }
            this.resolvers.add(Objects.requireNonNull(tagResolver, "resolvers[?]"));
            return true;
        }
        return false;
    }

    private void popMap() {
        if (!this.replacements.isEmpty()) {
            this.resolvers.add(new MapTagResolver(new HashMap<>(this.replacements)));
            this.replacements.clear();
        }
    }

    private boolean consumePotentialMappable(TagResolver tagResolver) {
        return tagResolver instanceof MappableResolver
                ? ((MappableResolver) tagResolver).contributeToMap(this.replacements)
                : false;
    }

    @NotNull
    @Override
    public TagResolver build() {
        this.popMap();
        if (this.resolvers.size() == 0) {
            return EmptyTagResolver.INSTANCE;
        }
        if (this.resolvers.size() == 1) {
            return this.resolvers.get(0);
        }
        TagResolver[] tagResolvers;
        Collections.reverse(
                Arrays.asList(tagResolvers = this.resolvers.toArray(new TagResolver[0])));
        return new SequentialTagResolver(tagResolvers);
    }
}
