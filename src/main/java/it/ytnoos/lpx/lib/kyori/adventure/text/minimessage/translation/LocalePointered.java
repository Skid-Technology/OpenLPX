package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.translation;

import it.ytnoos.lpx.lib.kyori.adventure.identity.Identity;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointered;
import it.ytnoos.lpx.lib.kyori.adventure.pointer.Pointers;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

final class LocalePointered implements Pointered {

    private final Pointers pointers;

    LocalePointered(@NotNull Locale locale) {
        this.pointers = Pointers.builder().withStatic(Identity.LOCALE, locale).build();
    }

    @NotNull
    @Override
    public Pointers pointers() {
        return this.pointers;
    }
}
