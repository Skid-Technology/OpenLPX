package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.key.InvalidKeyException;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

final class FontTag {

    static final String FONT = "font";

    static final TagResolver RESOLVER =
            SerializableResolver.claimingStyle(
                    "font", FontTag::create, StyleClaim.claim("font", Style::font, FontTag::emit));

    private FontTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text =
                argumentQueue
                        .popOr(
                                "A font tag must have either arguments of either <value> or <namespace:value>")
                        .value();
        Key key2;
        try {
            if (!argumentQueue.hasNext()) {
                key2 = Key.key(text);
            } else {
                String text2 = argumentQueue.pop().value();
                key2 = Key.key(text, text2);
            }
        } catch (InvalidKeyException invalidKeyException) {
            throw context.newException(invalidKeyException.getMessage(), argumentQueue);
        }
        Key key3 = key2;
        return Tag.styling(result -> result.font(key3));
    }

    static void emit(Key key, TokenEmitter tokenEmitter) {
        tokenEmitter.tag("font");
        if (key.namespace().equals("minecraft")) {
            tokenEmitter.argument(key.value());
        } else {
            tokenEmitter.arguments(key.namespace(), key.value());
        }
    }
}
