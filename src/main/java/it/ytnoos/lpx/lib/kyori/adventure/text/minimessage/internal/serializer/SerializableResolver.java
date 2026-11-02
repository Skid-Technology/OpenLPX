package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.TagInternals;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface SerializableResolver {

    @NotNull
    static TagResolver claimingComponent(
            @NotNull String text,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction,
            @NotNull Function<Component, @Nullable Emitable> function) {
        return claimingComponent(Collections.singleton(text), biFunction, function);
    }

    @NotNull
    static TagResolver claimingComponent(
            @NotNull Set<String> values,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction,
            @NotNull Function<Component, @Nullable Emitable> function) {
        HashSet values2;
        Iterator iterator2 = (values2 = new HashSet(values)).iterator();
        while (iterator2.hasNext()) {
            TagInternals.assertValidTagName((String) iterator2.next());
        }
        Objects.requireNonNull(biFunction, "handler");
        return new ComponentClaimingResolverImpl(values2, biFunction, function);
    }

    @NotNull
    static TagResolver claimingStyle(
            @NotNull String text,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction,
            @NotNull StyleClaim<?> styleClaim) {
        return claimingStyle(Collections.singleton(text), biFunction, styleClaim);
    }

    @NotNull
    static TagResolver claimingStyle(
            @NotNull Set<String> values,
            @NotNull BiFunction<ArgumentQueue, Context, Tag> biFunction,
            @NotNull StyleClaim<?> styleClaim) {
        HashSet values2;
        Iterator iterator2 = (values2 = new HashSet(values)).iterator();
        while (iterator2.hasNext()) {
            TagInternals.assertValidTagName((String) iterator2.next());
        }
        Objects.requireNonNull(biFunction, "handler");
        return new StyleClaimingResolverImpl(values2, biFunction, styleClaim);
    }

    void handle(@NotNull Component component, @NotNull ClaimConsumer claimConsumer);

    interface Single extends SerializableResolver {

        @Override
        default void handle(@NotNull Component component2, @NotNull ClaimConsumer claimConsumer) {
            StyleClaim styleClaim;
            Emitable emitable;
            if ((styleClaim = this.claimStyle()) != null
                    && !claimConsumer.styleClaimed(styleClaim.claimKey())
                    && (emitable = styleClaim.apply(component2.style())) != null) {
                claimConsumer.style(styleClaim.claimKey(), emitable);
            }
            if (!claimConsumer.componentClaimed()
                    && (emitable = this.claimComponent(component2)) != null) {
                claimConsumer.component(emitable);
            }
        }

        @Nullable
        default StyleClaim<?> claimStyle() {
            return null;
        }

        @Nullable
        default Emitable claimComponent(@NotNull Component component) {
            return null;
        }
    }
}
