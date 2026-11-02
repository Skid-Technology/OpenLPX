package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ObjectComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.SpriteObjectContents;

import org.jetbrains.annotations.Nullable;

final class SpriteTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    "sprite", SpriteTag::create, SpriteTag::claimComponent);

    private SpriteTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text =
                argumentQueue
                        .popOr(
                                "An atlas id and or a sprite id is required to produce a sprite object component")
                        .value();
        String text2;
        return (text2 = argumentQueue.hasNext() ? argumentQueue.pop().value() : null) == null
                ? Tag.selfClosingInserting(Component.object(ObjectContents.sprite(Key.key(text))))
                : Tag.selfClosingInserting(
                        Component.object(ObjectContents.sprite(Key.key(text), Key.key(text2))));
    }

    @Nullable
    static Emitable claimComponent(Component component) {
        if (!(component instanceof ObjectComponent)) {
            return null;
        } else {
            ObjectContents objectContents;
            if (!((objectContents = ((ObjectComponent) component).contents())
                    instanceof SpriteObjectContents)) {
                return null;
            } else {
                SpriteObjectContents spriteObjectContents;
                Key key = (spriteObjectContents = (SpriteObjectContents) objectContents).atlas();
                Key key2 = spriteObjectContents.sprite();
                if (key == SpriteObjectContents.DEFAULT_ATLAS) {
                    return result -> result.tag("sprite").argument(key2.asString());
                } else {
                    return result2 ->
                            result2.tag("sprite")
                                    .argument(key.asString())
                                    .argument(key2.asString());
                }
            }
        }
    }
}
