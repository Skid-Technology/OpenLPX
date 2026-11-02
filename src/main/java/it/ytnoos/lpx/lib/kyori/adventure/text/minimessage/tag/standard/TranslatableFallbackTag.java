package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class TranslatableFallbackTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    StandardTags.names("lang_or", "translate_or", "tr_or"),
                    TranslatableFallbackTag::create,
                    TranslatableFallbackTag::claim);

    private TranslatableFallbackTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text2 = argumentQueue.popOr("A translation key is required").value();
        String text3 = argumentQueue.popOr("A fallback messages is required").value();
        List<Component> values;
        if (argumentQueue.hasNext()) {
            values = new ArrayList();
            while (argumentQueue.hasNext()) {
                values.add(context.deserialize(argumentQueue.pop().value()));
            }
        } else {
            values = Collections.emptyList();
        }
        return Tag.inserting(Component.translatable(text2, text3, values));
    }

    @Nullable
    static Emitable claim(Component component) {
        if (component instanceof TranslatableComponent
                && ((TranslatableComponent) component).fallback() != null) {
            TranslatableComponent translatableComponent = (TranslatableComponent) component;
            return result -> {
                result.tag("lang_or");
                result.argument(translatableComponent.key());
                result.argument(translatableComponent.fallback());
                for (TranslationArgument translationArgument : translatableComponent.arguments()) {
                    result.argument(translationArgument.asComponent());
                }
            };
        } else {
            return null;
        }
    }
}
