package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal;

import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.TagPattern;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Internal
public final class TagInternals {

    @RegExp public static final String TAG_NAME_REGEX = "[!?#]?[a-z0-9_-]*";

    private static final Pattern TAG_NAME_PATTERN = Pattern.compile("[!?#]?[a-z0-9_-]*");

    private TagInternals() {}

    public static void assertValidTagName(@TagPattern @NotNull String text) {
        if (!TAG_NAME_PATTERN.matcher(Objects.requireNonNull(text)).matches()) {
            throw new IllegalArgumentException(
                    "Tag name must match pattern " + TAG_NAME_PATTERN.pattern() + ", was " + text);
        }
    }

    public static boolean sanitizeAndCheckValidTagName(@TagPattern @NotNull String text) {
        return TAG_NAME_PATTERN
                .matcher(Objects.requireNonNull(text).toLowerCase(Locale.ROOT))
                .matches();
    }

    public static void sanitizeAndAssertValidTagName(@TagPattern @NotNull String text) {
        assertValidTagName(Objects.requireNonNull(text).toLowerCase(Locale.ROOT));
    }
}
