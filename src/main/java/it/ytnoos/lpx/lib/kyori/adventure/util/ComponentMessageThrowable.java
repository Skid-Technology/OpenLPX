package it.ytnoos.lpx.lib.kyori.adventure.util;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

public interface ComponentMessageThrowable {

    @Nullable
    static Component getMessage(@Nullable Throwable throwable) {
        return throwable instanceof ComponentMessageThrowable
                ? ((ComponentMessageThrowable) throwable).componentMessage()
                : null;
    }

    @Nullable
    static Component getOrConvertMessage(@Nullable Throwable throwable) {
        if (throwable instanceof ComponentMessageThrowable) {
            return ((ComponentMessageThrowable) throwable).componentMessage();
        }
        String text2;
        return throwable != null && (text2 = throwable.getMessage()) != null
                ? Component.text(text2)
                : null;
    }

    @Nullable
    Component componentMessage();
}
