package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.ClaimConsumer;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

final class SequentialTagResolver implements TagResolver, SerializableResolver {

    final TagResolver[] resolvers;

    SequentialTagResolver(@NotNull TagResolver[] tagResolvers) {
        this.resolvers = tagResolvers;
    }

    @Nullable
    @Override
    public Tag resolve(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
            throws ParsingException {
        ParsingException parsingException2 = null;
        TagResolver[] tagResolvers = this.resolvers;
        int value = this.resolvers.length;
        for (int index = 0; index < value; index++) {
            TagResolver tagResolver = tagResolvers[index];
            try {
                Tag tag;
                if (tagResolver.has(text)
                        && (tag = tagResolver.resolve(text, argumentQueue, context)) != null) {
                    return tag;
                }
            } catch (ParsingException parsingException) {
                argumentQueue.reset();
                if (parsingException2 == null) {
                    parsingException2 = parsingException;
                } else {
                    parsingException2.addSuppressed(parsingException);
                }
            } catch (Exception exception) {
                argumentQueue.reset();
                ParsingException parsingException3 =
                        context.newException(
                                "Exception thrown while parsing <" + text + ">",
                                exception,
                                argumentQueue);
                if (parsingException2 == null) {
                    parsingException2 = parsingException3;
                } else {
                    parsingException2.addSuppressed(parsingException3);
                }
            }
        }
        if (parsingException2 != null) {
            throw parsingException2;
        } else {
            return null;
        }
    }

    @Override
    public boolean has(@NotNull String text) {
        TagResolver[] tagResolvers = this.resolvers;
        int value = this.resolvers.length;
        for (int index = 0; index < value; index++) {
            if (tagResolvers[index].has(text)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handle(@NotNull Component component, @NotNull ClaimConsumer claimConsumer) {
        TagResolver[] tagResolvers = this.resolvers;
        int value = this.resolvers.length;
        for (int index = 0; index < value; index++) {
            TagResolver tagResolver;
            if ((tagResolver = tagResolvers[index]) instanceof SerializableResolver) {
                ((SerializableResolver) tagResolver).handle(component, claimConsumer);
            }
        }
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (result == this) {
            return true;
        }
        if (!(result instanceof SequentialTagResolver)) {
            return false;
        }
        SequentialTagResolver sequentialTagResolver = (SequentialTagResolver) result;
        return Arrays.equals(this.resolvers, sequentialTagResolver.resolvers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.resolvers);
    }
}
