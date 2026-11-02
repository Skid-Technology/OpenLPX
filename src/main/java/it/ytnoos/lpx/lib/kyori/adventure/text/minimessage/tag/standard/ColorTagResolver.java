package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

final class ColorTagResolver implements TagResolver, SerializableResolver.Single {

    static final TagResolver INSTANCE = new ColorTagResolver();

    private static final StyleClaim<TextColor> STYLE =
            StyleClaim.claim(
                    "color",
                    Style::color,
                    (result, result2) -> {
                        if (result instanceof NamedTextColor) {
                            result2.tag(NamedTextColor.NAMES.key((NamedTextColor) result));
                        } else {
                            result2.tag(result.asHexString());
                        }
                    });

    private static final Map<String, TextColor> COLOR_ALIASES = new HashMap<>();

    private static boolean isColorOrAbbreviation(String text) {
        return text.equals("color") || text.equals("colour") || text.equals("c");
    }

    @Nullable
    @Override
    public Tag resolve(
            @NotNull String text, @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
            throws ParsingException {
        if (!this.has(text)) {
            return null;
        }
        String text2;
        if (isColorOrAbbreviation(text)) {
            text2 =
                    argumentQueue
                            .popOr("Expected to find a color parameter: <name>|#RRGGBB")
                            .lowerValue();
        } else {
            text2 = text;
        }
        TextColor textColor = resolveColor(text2, context);
        return Tag.styling(textColor);
    }

    @Nullable
    static TextColor resolveColorOrNull(String text) {
        TextColor textColor;
        if (COLOR_ALIASES.containsKey(text)) {
            textColor = COLOR_ALIASES.get(text);
        } else if (text.charAt(0) == 35) {
            textColor = TextColor.fromHexString(text);
        } else {
            textColor = NamedTextColor.NAMES.value(text);
        }
        return textColor;
    }

    @NotNull
    static TextColor resolveColor(@NotNull String text, @NotNull Context context)
            throws ParsingException {
        TextColor textColor;
        if ((textColor = resolveColorOrNull(text)) == null) {
            throw context.newException(
                    String.format(
                            "Unable to parse a color from '%s'. Please use named colours or hex (#RRGGBB) colors.",
                            text));
        } else {
            return textColor;
        }
    }

    @Override
    public boolean has(@NotNull String text) {
        return isColorOrAbbreviation(text)
                || NamedTextColor.NAMES.value(text) != null
                || COLOR_ALIASES.containsKey(text)
                || TextColor.fromHexString(text) != null;
    }

    @Nullable
    @Override
    public StyleClaim<?> claimStyle() {
        return STYLE;
    }

    static {
        COLOR_ALIASES.put("dark_grey", NamedTextColor.DARK_GRAY);
        COLOR_ALIASES.put("grey", NamedTextColor.GRAY);
    }
}
