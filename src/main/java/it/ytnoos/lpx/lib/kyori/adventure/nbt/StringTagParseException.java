package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.io.IOException;

class StringTagParseException extends IOException {

    private static final long serialVersionUID = -3001637554903912905L;

    private final CharSequence buffer;

    private final int position;

    StringTagParseException(String text, CharSequence charSequence, int position) {
        super(text);
        this.buffer = charSequence;
        this.position = position;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "(at position " + this.position + ")";
    }
}
