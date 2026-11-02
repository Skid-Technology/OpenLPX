package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

final class ArgumentTag implements TagResolver {

    private final List<Tag> arguments;

    private final TagResolver tagResolver;

    ArgumentTag(@NotNull List<Tag> arguments, @NotNull TagResolver tagResolver2) {
        this.arguments = arguments;
        this.tagResolver = tagResolver2;
    }

    @Nullable
    @Override
    public Tag resolve(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
            throws ParsingException {
        if (!text.equals("argument") && !text.equals("arg")) {
            return this.tagResolver.resolve(text, argumentQueue, context);
        } else {
            OptionalInt optionalInt = argumentQueue.popOr("No argument number provided").asInt();
            int value;
            if ((value =
                                    optionalInt.orElseThrow(
                                            () ->
                                                    context.newException(
                                                            "Invalid argument number",
                                                            argumentQueue)))
                            >= 0
                    && value < this.arguments.size()) {
                return this.arguments.get(value);
            } else {
                throw context.newException("Invalid argument number", argumentQueue);
            }
        }
    }

    @Override
    public boolean has(@NotNull String text) {
        return text.equals("argument") || text.equals("arg") || this.tagResolver.has(text);
    }
}
