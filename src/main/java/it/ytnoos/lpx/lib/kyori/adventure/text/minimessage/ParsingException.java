package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NonExtendable
public abstract class ParsingException extends RuntimeException {

    private static final long serialVersionUID = 4502774670340827070L;

    public static final int LOCATION_UNKNOWN = -1;

    protected ParsingException(@Nullable String text) {
        super(text);
    }

    protected ParsingException(@Nullable String text, @Nullable Throwable throwable) {
        super(text, throwable);
    }

    public ParsingException(String text, Throwable throwable, boolean matches, boolean matches2) {
        super(text, throwable, matches, matches2);
    }

    @NotNull
    public abstract String originalText();

    @Nullable
    public abstract String detailMessage();

    public abstract int startIndex();

    public abstract int endIndex();
}
