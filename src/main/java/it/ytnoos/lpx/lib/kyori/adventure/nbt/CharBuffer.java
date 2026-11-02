package it.ytnoos.lpx.lib.kyori.adventure.nbt;

final class CharBuffer {

    private final CharSequence sequence;

    private int index;

    CharBuffer(CharSequence charSequence) {
        this.sequence = charSequence;
    }

    public char peek() {
        return this.sequence.charAt(this.index);
    }

    public char peek(int value) {
        return this.sequence.charAt(this.index + value);
    }

    public char take() {
        return this.sequence.charAt(this.index++);
    }

    public boolean advance() {
        this.index++;
        return this.hasMore();
    }

    public boolean hasMore() {
        return this.index < this.sequence.length();
    }

    public boolean hasMore(int value) {
        return this.index + value < this.sequence.length();
    }

    public CharSequence takeUntil(char value) throws StringTagParseException {
        value = Character.toLowerCase(value);
        int value2 = -1;
        for (int value3 = this.index; value3 < this.sequence.length(); value3++) {
            if (this.sequence.charAt(value3) == 92) {
                value3++;
            } else if (Character.toLowerCase(this.sequence.charAt(value3)) == value) {
                value2 = value3;
                break;
            }
        }
        if (value2 == -1) {
            throw this.makeError("No occurrence of " + value + " was found");
        }
        CharSequence charSequence = this.sequence.subSequence(this.index, value2);
        this.index = value2 + 1;
        return charSequence;
    }

    public CharSequence takeRest() {
        int size = this.sequence.length();
        CharSequence charSequence = this.sequence.subSequence(this.index, size);
        this.index = size;
        return charSequence;
    }

    public CharBuffer expect(char value) throws StringTagParseException {
        this.skipWhitespace();
        if (!this.hasMore()) {
            throw this.makeError("Expected character '" + value + "' but got EOF");
        }
        if (this.peek() != value) {
            throw this.makeError(
                    "Expected character '" + value + "' but got '" + this.peek() + "'");
        }
        this.take();
        return this;
    }

    public boolean takeIf(char value) {
        this.skipWhitespace();
        if (this.hasMore() && this.peek() == value) {
            this.advance();
            return true;
        } else {
            return false;
        }
    }

    public CharBuffer skipWhitespace() {
        while (this.hasMore() && Character.isWhitespace(this.peek())) {
            this.advance();
        }
        return this;
    }

    public StringTagParseException makeError(String text) {
        return new StringTagParseException(text, this.sequence, this.index);
    }
}
