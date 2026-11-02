package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.SelectorComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.Nullable;

final class SelectorTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    StandardTags.names("sel", "selector"), SelectorTag::create, SelectorTag::claim);

    private SelectorTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text = argumentQueue.popOr("A selection key is required").value();
        Component component = null;
        if (argumentQueue.hasNext()) {
            component = context.deserialize(argumentQueue.pop().value());
        }
        return Tag.inserting(Component.selector(text, component));
    }

    @Nullable
    static Emitable claim(Component component) {
        if (!(component instanceof SelectorComponent)) {
            return null;
        }
        SelectorComponent selectorComponent = (SelectorComponent) component;
        return result -> {
            result.tag("sel");
            result.argument(selectorComponent.pattern());
            if (selectorComponent.separator() != null) {
                result.argument(selectorComponent.separator());
            }
        };
    }
}
