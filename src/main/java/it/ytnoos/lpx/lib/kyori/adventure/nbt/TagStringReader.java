package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;
import java.util.stream.LongStream;

final class TagStringReader {

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final int[] EMPTY_INT_ARRAY = new int[0];

    private static final long[] EMPTY_LONG_ARRAY = new long[0];

    private final CharBuffer buffer;

    private boolean acceptLegacy;

    private boolean acceptHeterogeneousLists;

    private int depth;

    TagStringReader(CharBuffer charBuffer) {
        this.buffer = charBuffer;
    }

    public CompoundBinaryTag compound() throws StringTagParseException {
        this.buffer.expect('{');
        if (this.buffer.takeIf('}')) {
            return CompoundBinaryTag.empty();
        }
        CompoundBinaryTag.Builder builder2 = CompoundBinaryTag.builder();
        while (this.buffer.hasMore()) {
            builder2.put(this.key(), this.tag());
            if (this.separatorOrCompleteWith('}')) {
                return builder2.build();
            }
        }
        throw this.buffer.makeError("Unterminated compound tag!");
    }

    public ListBinaryTag list() throws StringTagParseException {
        ListBinaryTag.Builder<it.ytnoos.lpx.lib.kyori.adventure.nbt.BinaryTag> builder2 =
                this.acceptHeterogeneousLists
                        ? ListBinaryTag.heterogeneousListBinaryTag()
                        : ListBinaryTag.builder();
        this.buffer.expect('[');
        boolean matches;
        if (!(matches =
                        this.acceptLegacy
                                && this.buffer.peek() == '0'
                                && this.buffer.peek(1) == ':')
                && this.buffer.takeIf(']')) {
            return ListBinaryTag.empty();
        }
        while (this.buffer.hasMore()) {
            if (matches) {
                this.buffer.takeUntil(':');
            }
            BinaryTag binaryTag = this.tag();
            builder2.add(binaryTag);
            if (this.separatorOrCompleteWith(']')) {
                return builder2.build();
            }
        }
        throw this.buffer.makeError("Reached end of file without end of list tag!");
    }

    public BinaryTag array(char value) throws StringTagParseException {
        this.buffer.expect('[').expect(value).expect(';');
        if ((value = Character.toLowerCase(value)) == 'b') {
            return ByteArrayBinaryTag.byteArrayBinaryTag(this.byteArray());
        } else if (value == 'i') {
            return IntArrayBinaryTag.intArrayBinaryTag(this.intArray());
        } else if (value == 'l') {
            return LongArrayBinaryTag.longArrayBinaryTag(this.longArray());
        } else {
            throw this.buffer.makeError(
                    "Type " + value + " is not a valid element type in an array!");
        }
    }

    private byte[] byteArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_BYTE_ARRAY;
        }
        ArrayList<Byte> values = new ArrayList<>();
        while (this.buffer.hasMore()) {
            CharSequence charSequence = this.buffer.skipWhitespace().takeUntil('b');
            try {
                values.add(Byte.valueOf(charSequence.toString()));
            } catch (NumberFormatException numberFormatException) {
                throw this.buffer.makeError("All elements of a byte array must be bytes!");
            }
            if (this.separatorOrCompleteWith(']')) {
                byte[] values2 = new byte[values.size()];
                for (int index = 0; index < values.size(); index++) {
                    values2[index] = (Byte) values.get(index);
                }
                return values2;
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private int[] intArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_INT_ARRAY;
        }
        Builder builder2 = IntStream.builder();
        while (this.buffer.hasMore()) {
            BinaryTag binaryTag;
            if (!((binaryTag = this.tag()) instanceof IntBinaryTag)) {
                throw this.buffer.makeError("All elements of an int array must be ints!");
            }
            builder2.add(((IntBinaryTag) binaryTag).intValue());
            if (this.separatorOrCompleteWith(']')) {
                return builder2.build().toArray();
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    private long[] longArray() throws StringTagParseException {
        if (this.buffer.takeIf(']')) {
            return EMPTY_LONG_ARRAY;
        }
        LongStream.Builder builder2 = LongStream.builder();
        while (this.buffer.hasMore()) {
            CharSequence charSequence = this.buffer.skipWhitespace().takeUntil('l');
            try {
                builder2.add(Long.parseLong(charSequence.toString()));
            } catch (NumberFormatException numberFormatException) {
                throw this.buffer.makeError("All elements of a long array must be longs!");
            }
            if (this.separatorOrCompleteWith(']')) {
                return builder2.build().toArray();
            }
        }
        throw this.buffer.makeError("Reached end of document without array close");
    }

    public String key() throws StringTagParseException {
        this.buffer.skipWhitespace();
        char value = this.buffer.peek();
        try {
            if (value != '\'' && value != '"') {
                StringBuilder stringBuilder = new StringBuilder();
                while (this.buffer.hasMore()) {
                    char value2;
                    if (!Tokens.id(value2 = this.buffer.peek())) {
                        if (!this.acceptLegacy) {
                            break;
                        }
                        if (value2 == '\\') {
                            this.buffer.take();
                        } else {
                            if (value2 == ':') {
                                break;
                            }
                            stringBuilder.append(this.buffer.take());
                        }
                    } else {
                        stringBuilder.append(this.buffer.take());
                    }
                }
                return stringBuilder.toString();
            } else {
                return unescape(this.buffer.takeUntil(this.buffer.take()).toString());
            }
        } finally {
            this.buffer.expect(':');
        }
    }

    public BinaryTag tag() throws StringTagParseException {
        if (this.depth++ > 512) {
            throw this.buffer.makeError("Exceeded maximum allowed depth of 512 when reading tag");
        }
        try {
            char value;
            switch (value = this.buffer.skipWhitespace().peek()) {
                case '"':
                case '\'':
                    this.buffer.advance();
                    return StringBinaryTag.stringBinaryTag(
                            unescape(this.buffer.takeUntil(value).toString()));
                case '[':
                    if (this.buffer.hasMore(2) && this.buffer.peek(2) == ';') {
                        return this.array(this.buffer.peek(1));
                    }
                    return this.list();
                case '{':
                    return this.compound();
                default:
                    return this.scalar();
            }
        } finally {
            this.depth--;
        }
    }

    private BinaryTag scalar() throws StringTagParseException {
        StringBuilder stringBuilder = new StringBuilder();
        while (this.buffer.hasMore()) {
            char value;
            if ((value = this.buffer.peek()) == '\\') {
                this.buffer.advance();
                value = this.buffer.take();
            } else {
                if (!Tokens.id(value)) {
                    break;
                }
                this.buffer.advance();
            }
            stringBuilder.append(value);
        }
        if (stringBuilder.length() == 0) {
            throw this.buffer.makeError("Expected a value but got nothing");
        }
        String text = stringBuilder.toString();
        int value2 = this.extractRadix(stringBuilder, text);
        if (stringBuilder.length() == 0) {
            throw this.buffer.makeError("Input is a radix, not a number");
        }
        char value3 = stringBuilder.charAt(stringBuilder.length() - 1);
        boolean matches = false;
        boolean matches2 = value2 != 16;
        char value4;
        if (stringBuilder.length() > 2
                && ((value4 = stringBuilder.charAt(stringBuilder.length() - 2)) == 's'
                        || value4 == 'u')) {
            matches = true;
            matches2 = value4 == 's';
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        boolean matches3 = false;
        char value5 = 'i';
        if (Tokens.numericType(value3) && (matches || value2 != 16)) {
            matches3 = true;
            value5 = Character.toLowerCase(value3);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        if (matches2 || value5 != 'f' && value5 != 'd') {
            String text2 = stringBuilder.toString().replace("_", "");
            if (matches3) {
                try {
                    NumberBinaryTag numberBinaryTag;
                    if ((numberBinaryTag = this.parseNumberTag(text2, value5, value2, matches2))
                            != null) {
                        return numberBinaryTag;
                    }
                } catch (NumberFormatException numberFormatException) {
                }
            } else {
                try {
                    return IntBinaryTag.intBinaryTag(this.parseInt(text2, value2, matches2));
                } catch (NumberFormatException numberFormatException2) {
                    if (text2.indexOf(46) != -1) {
                        try {
                            return DoubleBinaryTag.doubleBinaryTag(Double.parseDouble(text2));
                        } catch (NumberFormatException numberFormatException3) {
                        }
                    }
                }
            }
            if (text.equalsIgnoreCase("true")) {
                return ByteBinaryTag.ONE;
            } else {
                return text.equalsIgnoreCase("false")
                        ? ByteBinaryTag.ZERO
                        : StringBinaryTag.stringBinaryTag(text);
            }
        } else {
            throw this.buffer.makeError("Cannot create unsigned floating point numbers");
        }
    }

    private int extractRadix(StringBuilder stringBuilder, String text) {
        byte value = 0;
        char value2;
        if ((value2 = stringBuilder.charAt(0)) == '+' || value2 == '-') {
            value = 1;
        }
        int value3 = 2 + value;
        byte value4;
        if (text.length() <= value3
                || !text.startsWith("0b", value) && !text.startsWith("0B", value)) {
            if (!text.startsWith("0x", value) && !text.startsWith("0X", value)) {
                value4 = 10;
            } else {
                value4 = 16;
            }
        } else {
            value4 = 2;
        }
        if (value4 != 10) {
            stringBuilder.delete(value, value3);
        }
        return value4;
    }

    @Nullable
    private NumberBinaryTag parseNumberTag(String text, char value, int value2, boolean matches) {
        switch (value) {
            case 'b':
                return ByteBinaryTag.byteBinaryTag(this.parseByte(text, value2, matches));
            case 'c':
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            default:
                break;
            case 'd':
                double value3;
                if (Double.isFinite(value3 = Double.parseDouble(text))) {
                    return DoubleBinaryTag.doubleBinaryTag(value3);
                }
                break;
            case 'f':
                float value4;
                if (Float.isFinite(value4 = Float.parseFloat(text))) {
                    return FloatBinaryTag.floatBinaryTag(value4);
                }
                break;
            case 'i':
                return IntBinaryTag.intBinaryTag(this.parseInt(text, value2, matches));
            case 'l':
                return LongBinaryTag.longBinaryTag(this.parseLong(text, value2, matches));
            case 's':
                return ShortBinaryTag.shortBinaryTag(this.parseShort(text, value2, matches));
        }
        return null;
    }

    private byte parseByte(String text, int value, boolean matches) {
        if (matches) {
            return Byte.parseByte(text, value);
        } else {
            int value2;
            if ((value2 = Integer.parseInt(text, value)) >> 8 == 0) {
                return (byte) value2;
            } else {
                throw new NumberFormatException();
            }
        }
    }

    private short parseShort(String text, int value, boolean matches) {
        if (matches) {
            return Short.parseShort(text, value);
        } else {
            int value2;
            if ((value2 = Integer.parseInt(text, value)) >> 16 == 0) {
                return (short) value2;
            } else {
                throw new NumberFormatException();
            }
        }
    }

    private int parseInt(String text, int value, boolean matches) {
        return matches ? Integer.parseInt(text, value) : Integer.parseUnsignedInt(text, value);
    }

    private long parseLong(String text, int value, boolean matches) {
        return matches ? Long.parseLong(text, value) : Long.parseUnsignedLong(text, value);
    }

    private boolean separatorOrCompleteWith(char value) throws StringTagParseException {
        if (this.buffer.takeIf(value)) {
            return true;
        }
        this.buffer.expect(',');
        return this.buffer.takeIf(value);
    }

    private static String unescape(String text) {
        int value;
        if ((value = text.indexOf(92)) == -1) {
            return text;
        }
        int value2 = 0;
        StringBuilder stringBuilder = new StringBuilder(text.length());
        do {
            stringBuilder.append(text, value2, value);
            value2 = value + 1;
        } while ((value = text.indexOf(92, value2 + 1)) != -1);
        stringBuilder.append(text.substring(value2));
        return stringBuilder.toString();
    }

    public void legacy(boolean acceptLegacy) {
        this.acceptLegacy = acceptLegacy;
    }

    public void heterogeneousLists(boolean acceptHeterogeneousLists) {
        this.acceptHeterogeneousLists = acceptHeterogeneousLists;
    }
}
