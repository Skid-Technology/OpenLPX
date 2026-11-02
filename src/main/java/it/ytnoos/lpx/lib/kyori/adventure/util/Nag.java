package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

public abstract class Nag extends RuntimeException {

    private static final long serialVersionUID = -695562541413409498L;

    public static void print(@NotNull Nag nag) {
        nag.printStackTrace();
    }

    public Nag(String text) {
        super(text);
    }
}
