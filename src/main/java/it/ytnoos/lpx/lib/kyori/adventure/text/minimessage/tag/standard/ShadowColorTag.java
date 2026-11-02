package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.StyleGetter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;

import java.util.OptionalDouble;

final class ShadowColorTag {

    static final TagResolver RESOLVER =
            TagResolver.resolver(
                    SerializableResolver.claimingStyle(
                            "shadow",
                            ShadowColorTag::create,
                            StyleClaim.claim(
                                    "shadow", StyleGetter::shadowColor, ShadowColorTag::emit)),
                    TagResolver.resolver("!shadow", Tag.styling(ShadowColor.none())));

    static Tag create(@NotNull ArgumentQueue argumentQueue, @NotNull Context context)
            throws ParsingException {
        String text;
        ShadowColor shadowColor2;
        if ((text =
                                argumentQueue
                                        .popOr("Expected to find a color parameter: #RRGGBBAA")
                                        .lowerValue())
                        .startsWith("#")
                && text.length() == 9) {
            if ((shadowColor2 = ShadowColor.fromHexString(text)) == null) {
                throw context.newException(
                        String.format(
                                "Unable to parse a shadow color from '%s'. Please use #RRGGBBAA formatting.",
                                text));
            }
        } else {
            TextColor textColor = ColorTagResolver.resolveColor(text, context);
            float value;
            if (argumentQueue.hasNext()) {
                OptionalDouble optionalDouble = argumentQueue.pop().asDouble();
                value =
                        (float)
                                optionalDouble.orElseThrow(
                                        () ->
                                                context.newException(
                                                        "Number was expected to be a double"));
            } else {
                value = 0.25F;
            }
            float value2 = value;
            shadowColor2 = ShadowColor.shadowColor(textColor, (int) (value2 * 255.0F));
        }
        return Tag.styling(shadowColor2);
    }

    static void emit(@NotNull ShadowColor shadowColor, @NotNull TokenEmitter tokenEmitter) {
        if (ShadowColor.none().equals(shadowColor)) {
            tokenEmitter.tag("!shadow");
        } else {
            tokenEmitter.tag("shadow");
            NamedTextColor namedTextColor;
            if ((namedTextColor = NamedTextColor.namedColor(TextColor.color(shadowColor).value()))
                    != null) {
                tokenEmitter
                        .argument(NamedTextColor.NAMES.key(namedTextColor))
                        .argument(Float.toString(shadowColor.alpha() / 255.0F));
            } else {
                tokenEmitter.argument(shadowColor.asHexString());
            }
        }
    }

    private ShadowColorTag() {}
}
