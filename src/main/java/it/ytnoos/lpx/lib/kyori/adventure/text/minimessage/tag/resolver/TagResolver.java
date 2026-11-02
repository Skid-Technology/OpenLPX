package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.TagPattern;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard.StandardTags;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collector;

public interface TagResolver {

    @NotNull
    static TagResolver.Builder builder() {
        return new TagResolverBuilderImpl();
    }

    @NotNull
    static TagResolver standard() {
        return StandardTags.defaults();
    }

    @NotNull
    static TagResolver empty() {
        return EmptyTagResolver.INSTANCE;
    }

    static TagResolver.@NotNull Single resolver(
            @TagPattern @NotNull String text, @NotNull Tag tag) {
        TagInternals.assertValidTagName(text);
        return new SingleResolver(text, Objects.requireNonNull(tag, "tag"));
    }

    @NotNull
    static TagResolver resolver(
            @TagPattern @NotNull String text,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction) {
        return resolver(Collections.singleton(text), biFunction);
    }

    @NotNull
    static TagResolver resolver(
            @NotNull final Set<String> values,
            @NotNull final BiFunction<ArgumentQueue, Context, Tag> biFunction) {
        Iterator iterator2 = new HashSet(values).iterator();
        while (iterator2.hasNext()) {
            TagInternals.assertValidTagName((String) iterator2.next());
        }
        Objects.requireNonNull(biFunction, "handler");
        return new TagResolver() {

            @Nullable
            @Override
            public Tag resolve(
                    @NotNull String text,
                    @NotNull ArgumentQueue argumentQueue,
                    @NotNull Context context)
                    throws ParsingException {
                return !values.contains(text)
                        ? null
                        : (Tag) biFunction.apply(argumentQueue, context);
            }

            @Override
            public boolean has(@NotNull String text) {
                return values.contains(text);
            }
        };
    }

    @NotNull
    static TagResolver resolver(@NotNull TagResolver... tagResolver) {
        return ((TagResolver[]) Objects.requireNonNull(tagResolver, "resolvers")).length == 1
                ? Objects.requireNonNull(tagResolver[0], "resolvers must not contain null elements")
                : builder().resolvers(tagResolver).build();
    }

    @NotNull
    static TagResolver resolver(@NotNull Iterable<? extends TagResolver> iterable) {
        if (iterable instanceof Collection) {
            int value;
            if ((value = ((Collection) iterable).size()) == 0) {
                return empty();
            }
            if (value == 1) {
                return Objects.requireNonNull(
                        (TagResolver) iterable.iterator().next(),
                        "resolvers must not contain null elements");
            }
        }
        return builder().resolvers(iterable).build();
    }

    @NotNull
    static TagResolver caching(TagResolver.@NotNull WithoutArguments withoutArguments) {
        return withoutArguments instanceof CachingTagResolver
                ? withoutArguments
                : new CachingTagResolver(Objects.requireNonNull(withoutArguments, "resolver"));
    }

    @NotNull
    static Collector<TagResolver, ?, TagResolver> toTagResolver() {
        return TagResolverBuilderImpl.COLLECTOR;
    }

    @Nullable
    Tag resolve(
            @TagPattern @NotNull String text,
            @NotNull ArgumentQueue argumentQueue,
            @NotNull Context context)
            throws ParsingException;

    boolean has(@NotNull String text);

    interface Builder {

        @NotNull
        TagResolver.Builder tag(@TagPattern @NotNull String text, @NotNull Tag tag2);

        @NotNull
        default TagResolver.Builder tag(
                @TagPattern @NotNull String text,
                @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction) {
            return this.tag(Collections.singleton(text), biFunction);
        }

        @NotNull
        default TagResolver.Builder tag(
                @NotNull Set<String> values,
                @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction) {
            return this.resolver(TagResolver.resolver(values, biFunction));
        }

        @NotNull
        TagResolver.Builder resolver(@NotNull TagResolver tagResolver);

        @NotNull
        TagResolver.Builder resolvers(@NotNull TagResolver... tagResolver);

        @NotNull
        TagResolver.Builder resolvers(@NotNull Iterable<? extends TagResolver> iterable);

        @NotNull
        default TagResolver.Builder caching(
                TagResolver.@NotNull WithoutArguments withoutArguments) {
            return this.resolver(TagResolver.caching(withoutArguments));
        }

        @NotNull
        TagResolver build();
    }

    @NonExtendable
    interface Single extends TagResolver.WithoutArguments {

        @NotNull
        String key();

        @NotNull
        Tag tag();

        @Nullable
        @Override
        default Tag resolve(@TagPattern @NotNull String text) {
            return this.has(text) ? this.tag() : null;
        }

        @Override
        default boolean has(@NotNull String text) {
            return text.equalsIgnoreCase(this.key());
        }
    }

    @FunctionalInterface
    interface WithoutArguments extends TagResolver {

        @Nullable
        Tag resolve(@TagPattern @NotNull String text);

        @Override
        default boolean has(@NotNull String text) {
            return this.resolve(text) != null;
        }

        @Nullable
        @Override
        default Tag resolve(
                @TagPattern @NotNull String text,
                @NotNull ArgumentQueue argumentQueue,
                @NotNull Context context)
                throws ParsingException {
            Tag tag;
            if ((tag = this.resolve(text)) != null && argumentQueue.hasNext()) {
                throw context.newException("Tag '<" + text + ">' does not accept any arguments");
            } else {
                return tag;
            }
        }
    }
}
