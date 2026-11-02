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

final class TranslatableTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    StandardTags.names("lang", "translate", "tr"),
                    TranslatableTag::create,
                    TranslatableTag::claim);

    private TranslatableTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text2 = argumentQueue.popOr("A translation key is required").value();
        List<Component> values;
        if (argumentQueue.hasNext()) {
            values = new ArrayList();
            while (argumentQueue.hasNext()) {
                values.add(context.deserialize(argumentQueue.pop().value()));
            }
        } else {
            values = Collections.emptyList();
        }
        return Tag.inserting(Component.translatable(text2, values));
    }

    @Nullable
    static Emitable claim(Component component) {
        if (component instanceof TranslatableComponent
                && ((TranslatableComponent) component).fallback() == null) {
            TranslatableComponent translatableComponent = (TranslatableComponent) component;
            return result -> {
                result.tag("lang");
                result.argument(translatableComponent.key());
                for (TranslationArgument translationArgument : translatableComponent.arguments()) {
                    result.argument(translationArgument.asComponent());
                }
            };
        } else {
            return null;
        }
    }
}
