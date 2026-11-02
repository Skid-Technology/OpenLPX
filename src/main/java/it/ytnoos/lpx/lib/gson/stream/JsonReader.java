package it.ytnoos.lpx.lib.gson.stream;

import it.ytnoos.lpx.lib.gson.internal.JsonReaderInternalAccess;
import it.ytnoos.lpx.lib.gson.internal.bind.JsonTreeReader;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class JsonReader implements Closeable {

    private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();

    private final Reader in;

    private boolean lenient = false;

    private final char[] buffer = new char[1024];

    private int pos = 0;

    private int limit = 0;

    private int lineNumber = 0;

    private int lineStart = 0;

    int peeked = 0;

    private long peekedLong;

    private int peekedNumberLength;

    private String peekedString;

    private int[] stack = new int[32];

    private int stackSize = 0;

    private String[] pathNames;

    private int[] pathIndices;

    public JsonReader(Reader reader) {
        this.stack[this.stackSize++] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public void beginArray() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 3) {
            this.push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new IllegalStateException(
                    "Expected BEGIN_ARRAY but was " + this.peek() + this.locationString());
        }
    }

    public void endArray() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 4) {
            this.stackSize--;
            this.pathIndices[this.stackSize - 1]++;
            this.peeked = 0;
        } else {
            throw new IllegalStateException(
                    "Expected END_ARRAY but was " + this.peek() + this.locationString());
        }
    }

    public void beginObject() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 1) {
            this.push(3);
            this.peeked = 0;
        } else {
            throw new IllegalStateException(
                    "Expected BEGIN_OBJECT but was " + this.peek() + this.locationString());
        }
    }

    public void endObject() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 2) {
            this.stackSize--;
            this.pathNames[this.stackSize] = null;
            this.pathIndices[this.stackSize - 1]++;
            this.peeked = 0;
        } else {
            throw new IllegalStateException(
                    "Expected END_OBJECT but was " + this.peek() + this.locationString());
        }
    }

    public boolean hasNext() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        return value != 2 && value != 4;
    }

    public JsonToken peek() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        switch (value) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    int doPeek() throws IOException {
        int value;
        if ((value = this.stack[this.stackSize - 1]) == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else if (value == 2) {
            switch (this.nextNonWhitespace(true)) {
                case 44:
                    break;
                case 59:
                    this.checkLenient();
                    break;
                case 93:
                    return this.peeked = 4;
                default:
                    throw this.syntaxError("Unterminated array");
            }
        } else {
            if (value == 3 || value == 5) {
                this.stack[this.stackSize - 1] = 4;
                if (value == 5) {
                    switch (this.nextNonWhitespace(true)) {
                        case 44:
                            break;
                        case 59:
                            this.checkLenient();
                            break;
                        case 125:
                            return this.peeked = 2;
                        default:
                            throw this.syntaxError("Unterminated object");
                    }
                }
                int value2;
                switch (value2 = this.nextNonWhitespace(true)) {
                    case 34:
                        return this.peeked = 13;
                    case 39:
                        this.checkLenient();
                        return this.peeked = 12;
                    case 125:
                        if (value != 5) {
                            return this.peeked = 2;
                        }
                        throw this.syntaxError("Expected name");
                    default:
                        this.checkLenient();
                        this.pos--;
                        if (this.isLiteral((char) value2)) {
                            return this.peeked = 14;
                        }
                        throw this.syntaxError("Expected name");
                }
            }
            if (value == 4) {
                this.stack[this.stackSize - 1] = 5;
                switch (this.nextNonWhitespace(true)) {
                    case 58:
                        break;
                    case 61:
                        this.checkLenient();
                        if ((this.pos < this.limit || this.fillBuffer(1))
                                && this.buffer[this.pos] == '>') {
                            this.pos++;
                        }
                        break;
                    default:
                        throw this.syntaxError("Expected ':'");
                }
            } else if (value == 6) {
                if (this.lenient) {
                    this.consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = 7;
            } else if (value == 7) {
                if (this.nextNonWhitespace(false) == -1) {
                    return this.peeked = 17;
                }
                this.checkLenient();
                this.pos--;
            } else if (value == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        switch (this.nextNonWhitespace(true)) {
            case 34:
                return this.peeked = 9;
            case 39:
                this.checkLenient();
                return this.peeked = 8;
            case 91:
                return this.peeked = 3;
            case 93:
                if (value == 1) {
                    return this.peeked = 4;
                }
            case 44:
            case 59:
                if (value != 1 && value != 2) {
                    throw this.syntaxError("Unexpected value");
                }
                this.checkLenient();
                this.pos--;
                return this.peeked = 7;
            case 123:
                return this.peeked = 1;
            default:
                this.pos--;
                int value3;
                if ((value3 = this.peekKeyword()) != 0) {
                    return value3;
                } else if ((value3 = this.peekNumber()) != 0) {
                    return value3;
                } else if (!this.isLiteral(this.buffer[this.pos])) {
                    throw this.syntaxError("Expected value");
                } else {
                    this.checkLenient();
                    return this.peeked = 10;
                }
        }
    }

    private int peekKeyword() throws IOException {
        char value;
        String text;
        String text2;
        byte value2;
        if ((value = this.buffer[this.pos]) == 't' || value == 'T') {
            text = "true";
            text2 = "TRUE";
            value2 = 5;
        } else if (value != 'f' && value != 'F') {
            if (value != 'n' && value != 'N') {
                return 0;
            }
            text = "null";
            text2 = "NULL";
            value2 = 7;
        } else {
            text = "false";
            text2 = "FALSE";
            value2 = 6;
        }
        int size = text.length();
        for (int index = 1; index < size; index++) {
            if (this.pos + index >= this.limit && !this.fillBuffer(index + 1)) {
                return 0;
            }
            if ((value = this.buffer[this.pos + index]) != text.charAt(index)
                    && value != text2.charAt(index)) {
                return 0;
            }
        }
        if ((this.pos + size < this.limit || this.fillBuffer(size + 1))
                && this.isLiteral(this.buffer[this.pos + size])) {
            return 0;
        }
        this.pos += size;
        return this.peeked = value2;
    }

    private int peekNumber() throws IOException {
        char[] values = this.buffer;
        int value = this.pos;
        int value2 = this.limit;
        long value3 = 0L;
        boolean matches = false;
        boolean matches2 = true;
        byte value4 = 0;
        int value5 = 0;
        label177:
        while (true) {
            if (value + value5 == value2) {
                if (value5 == values.length) {
                    return 0;
                }
                if (!this.fillBuffer(value5 + 1)) {
                    break;
                }
                value = this.pos;
                value2 = this.limit;
            }
            char value6;
            switch (value6 = values[value + value5]) {
                case '+':
                    if (value4 != 5) {
                        return 0;
                    }
                    value4 = 6;
                    break;
                case '-':
                    if (value4 == 0) {
                        matches = true;
                        value4 = 1;
                    } else {
                        if (value4 != 5) {
                            return 0;
                        }
                        value4 = 6;
                    }
                    break;
                case '.':
                    if (value4 != 2) {
                        return 0;
                    }
                    value4 = 3;
                    break;
                case 'E':
                case 'e':
                    if (value4 != 2 && value4 != 4) {
                        return 0;
                    }
                    value4 = 5;
                    break;
                default:
                    if (value6 < '0' || value6 > '9') {
                        if (this.isLiteral(value6)) {
                            return 0;
                        }
                        break label177;
                    }
                    if (value4 == 1 || value4 == 0) {
                        value3 = -(value6 - '0');
                        value4 = 2;
                    } else if (value4 == 2) {
                        if (value3 == 0L) {
                            return 0;
                        }
                        long value7 = value3 * 10L - (value6 - '0');
                        matches2 &=
                                value3 > -922337203685477580L
                                        || value3 == -922337203685477580L && value7 < value3;
                        value3 = value7;
                    } else if (value4 == 3) {
                        value4 = 4;
                    } else if (value4 == 5 || value4 == 6) {
                        value4 = 7;
                    }
            }
            value5++;
        }
        if (value4 != 2 || !matches2 || value3 == Long.MIN_VALUE && !matches) {
            if (value4 != 2 && value4 != 4 && value4 != 7) {
                return 0;
            }
            this.peekedNumberLength = value5;
            return this.peeked = 16;
        } else {
            this.peekedLong = matches ? value3 : -value3;
            this.pos += value5;
            return this.peeked = 15;
        }
    }

    private boolean isLiteral(char value) throws IOException {
        switch (value) {
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                this.checkLenient();
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                return false;
            default:
                return true;
        }
    }

    public String nextName() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        String text;
        if (value == 14) {
            text = this.nextUnquotedValue();
        } else if (value == 12) {
            text = this.nextQuotedValue('\'');
        } else {
            if (value != 13) {
                throw new IllegalStateException(
                        "Expected a name but was " + this.peek() + this.locationString());
            }
            text = this.nextQuotedValue('"');
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = text;
        return text;
    }

    public String nextString() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        String text;
        if (value == 10) {
            text = this.nextUnquotedValue();
        } else if (value == 8) {
            text = this.nextQuotedValue('\'');
        } else if (value == 9) {
            text = this.nextQuotedValue('"');
        } else if (value == 11) {
            text = this.peekedString;
            this.peekedString = null;
        } else if (value == 15) {
            text = Long.toString(this.peekedLong);
        } else {
            if (value != 16) {
                throw new IllegalStateException(
                        "Expected a string but was " + this.peek() + this.locationString());
            }
            text = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
        }
        this.peeked = 0;
        this.pathIndices[this.stackSize - 1]++;
        return text;
    }

    public boolean nextBoolean() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 5) {
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return true;
        } else if (value == 6) {
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return false;
        } else {
            throw new IllegalStateException(
                    "Expected a boolean but was " + this.peek() + this.locationString());
        }
    }

    public void nextNull() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 7) {
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
        } else {
            throw new IllegalStateException(
                    "Expected null but was " + this.peek() + this.locationString());
        }
    }

    public double nextDouble() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 15) {
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return this.peekedLong;
        }
        if (value == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
        } else if (value == 8 || value == 9) {
            this.peekedString = this.nextQuotedValue((char) (value == 8 ? '\'' : '"'));
        } else if (value == 10) {
            this.peekedString = this.nextUnquotedValue();
        } else if (value != 11) {
            throw new IllegalStateException(
                    "Expected a double but was " + this.peek() + this.locationString());
        }
        this.peeked = 11;
        double value2 = Double.parseDouble(this.peekedString);
        if (this.lenient || !Double.isNaN(value2) && !Double.isInfinite(value2)) {
            this.peekedString = null;
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return value2;
        } else {
            throw new MalformedJsonException(
                    "JSON forbids NaN and infinities: " + value2 + this.locationString());
        }
    }

    public long nextLong() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 15) {
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return this.peekedLong;
        }
        if (value == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos = this.pos + this.peekedNumberLength;
        } else {
            if (value != 8 && value != 9 && value != 10) {
                throw new IllegalStateException(
                        "Expected a long but was " + this.peek() + this.locationString());
            }
            if (value == 10) {
                this.peekedString = this.nextUnquotedValue();
            } else {
                this.peekedString = this.nextQuotedValue((char) (value == 8 ? '\'' : '"'));
            }
            try {
                long value2 = Long.parseLong(this.peekedString);
                this.peeked = 0;
                this.pathIndices[this.stackSize - 1]++;
                return value2;
            } catch (NumberFormatException numberFormatException) {
            }
        }
        this.peeked = 11;
        double value3;
        long value4;
        if ((value4 = (long) (value3 = Double.parseDouble(this.peekedString))) != value3) {
            throw new NumberFormatException(
                    "Expected a long but was " + this.peekedString + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        this.pathIndices[this.stackSize - 1]++;
        return value4;
    }

    private String nextQuotedValue(char value) throws IOException {
        char[] values = this.buffer;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int value2 = this.pos;
            int value3 = this.limit;
            int value4 = value2;
            while (value2 < value3) {
                char value5;
                if ((value5 = values[value2++]) == value) {
                    this.pos = value2;
                    stringBuilder.append(values, value4, value2 - value4 - 1);
                    return stringBuilder.toString();
                }
                if (value5 == '\\') {
                    this.pos = value2;
                    stringBuilder.append(values, value4, value2 - value4 - 1);
                    stringBuilder.append(this.readEscapeCharacter());
                    value2 = this.pos;
                    value3 = this.limit;
                    value4 = value2;
                } else if (value5 == '\n') {
                    this.lineNumber++;
                    this.lineStart = value2;
                }
            }
            stringBuilder.append(values, value4, value2 - value4);
            this.pos = value2;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    private String nextUnquotedValue() throws IOException {
        StringBuilder stringBuilder = null;
        int value = 0;
        label41:
        while (true) {
            if (this.pos + value < this.limit) {
                switch (this.buffer[this.pos + value]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break label41;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        this.checkLenient();
                        break label41;
                    default:
                        value++;
                }
            } else if (value < this.buffer.length) {
                if (this.fillBuffer(value + 1)) {
                    continue;
                }
                break;
            } else {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.buffer, this.pos, value);
                this.pos += value;
                value = 0;
                if (!this.fillBuffer(1)) {
                    break;
                }
            }
        }
        String text;
        if (stringBuilder == null) {
            text = new String(this.buffer, this.pos, value);
        } else {
            stringBuilder.append(this.buffer, this.pos, value);
            text = stringBuilder.toString();
        }
        this.pos += value;
        return text;
    }

    private void skipQuotedValue(char value) throws IOException {
        char[] values = this.buffer;
        do {
            int value2 = this.pos;
            int value3 = this.limit;
            while (value2 < value3) {
                char value4;
                if ((value4 = values[value2++]) == value) {
                    this.pos = value2;
                    return;
                }
                if (value4 == '\\') {
                    this.pos = value2;
                    this.readEscapeCharacter();
                    value2 = this.pos;
                    value3 = this.limit;
                } else if (value4 == '\n') {
                    this.lineNumber++;
                    this.lineStart = value2;
                }
            }
            this.pos = value2;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int value;
            for (value = 0; this.pos + value < this.limit; value++) {
                switch (this.buffer[this.pos + value]) {
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        this.checkLenient();
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        this.pos += value;
                        return;
                }
            }
            this.pos += value;
        } while (this.fillBuffer(1));
    }

    public int nextInt() throws IOException {
        int value = this.peeked;
        if (this.peeked == 0) {
            value = this.doPeek();
        }
        if (value == 15) {
            int value2 = (int) this.peekedLong;
            if (this.peekedLong != value2) {
                throw new NumberFormatException(
                        "Expected an int but was " + this.peekedLong + this.locationString());
            }
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return value2;
        } else {
            if (value == 16) {
                this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
                this.pos = this.pos + this.peekedNumberLength;
            } else {
                if (value != 8 && value != 9 && value != 10) {
                    throw new IllegalStateException(
                            "Expected an int but was " + this.peek() + this.locationString());
                }
                if (value == 10) {
                    this.peekedString = this.nextUnquotedValue();
                } else {
                    this.peekedString = this.nextQuotedValue((char) (value == 8 ? '\'' : '"'));
                }
                try {
                    int value3 = Integer.parseInt(this.peekedString);
                    this.peeked = 0;
                    this.pathIndices[this.stackSize - 1]++;
                    return value3;
                } catch (NumberFormatException numberFormatException) {
                }
            }
            this.peeked = 11;
            int value4;
            double value5;
            if ((value4 = (int) (value5 = Double.parseDouble(this.peekedString))) != value5) {
                throw new NumberFormatException(
                        "Expected an int but was " + this.peekedString + this.locationString());
            }
            this.peekedString = null;
            this.peeked = 0;
            this.pathIndices[this.stackSize - 1]++;
            return value4;
        }
    }

    @Override
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int value = 0;
        do {
            int value2 = this.peeked;
            if (this.peeked == 0) {
                value2 = this.doPeek();
            }
            if (value2 == 3) {
                this.push(1);
                value++;
            } else if (value2 == 1) {
                this.push(3);
                value++;
            } else if (value2 == 4) {
                this.stackSize--;
                value--;
            } else if (value2 == 2) {
                this.stackSize--;
                value--;
            } else if (value2 == 14 || value2 == 10) {
                this.skipUnquotedValue();
            } else if (value2 == 8 || value2 == 12) {
                this.skipQuotedValue('\'');
            } else if (value2 == 9 || value2 == 13) {
                this.skipQuotedValue('"');
            } else if (value2 == 16) {
                this.pos = this.pos + this.peekedNumberLength;
            }
            this.peeked = 0;
        } while (value != 0);
        this.pathIndices[this.stackSize - 1]++;
        this.pathNames[this.stackSize - 1] = "null";
    }

    private void push(int value) {
        if (this.stackSize == this.stack.length) {
            int[] values = new int[this.stackSize * 2];
            int[] values2 = new int[this.stackSize * 2];
            String[] texts = new String[this.stackSize * 2];
            System.arraycopy(this.stack, 0, values, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, values2, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, texts, 0, this.stackSize);
            this.stack = values;
            this.pathIndices = values2;
            this.pathNames = texts;
        }
        this.stack[this.stackSize++] = value;
    }

    private boolean fillBuffer(int value) throws IOException {
        char[] values = this.buffer;
        this.lineStart = this.lineStart - this.pos;
        if (this.limit != this.pos) {
            this.limit = this.limit - this.pos;
            System.arraycopy(values, this.pos, values, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        int value2;
        while ((value2 = this.in.read(values, this.limit, values.length - this.limit)) != -1) {
            this.limit += value2;
            if (this.lineNumber == 0
                    && this.lineStart == 0
                    && this.limit > 0
                    && values[0] == '\ufeff') {
                this.pos++;
                this.lineStart++;
                value++;
            }
            if (this.limit >= value) {
                return true;
            }
        }
        return false;
    }

    private int nextNonWhitespace(boolean matches) throws IOException {
        char[] values = this.buffer;
        int value = this.pos;
        int value2 = this.limit;
        while (true) {
            if (value == value2) {
                this.pos = value;
                if (!this.fillBuffer(1)) {
                    if (matches) {
                        throw new EOFException("End of input" + this.locationString());
                    }
                    return -1;
                }
                value = this.pos;
                value2 = this.limit;
            }
            char value3;
            if ((value3 = values[value++]) == '\n') {
                this.lineNumber++;
                this.lineStart = value;
            } else if (value3 != ' ' && value3 != '\r' && value3 != '\t') {
                if (value3 == '/') {
                    this.pos = value;
                    if (value == value2) {
                        this.pos--;
                        boolean matches2 = this.fillBuffer(2);
                        this.pos++;
                        if (!matches2) {
                            return value3;
                        }
                    }
                    this.checkLenient();
                    switch (values[this.pos]) {
                        case '*':
                            this.pos++;
                            if (!this.skipTo("*/")) {
                                throw this.syntaxError("Unterminated comment");
                            }
                            value = this.pos + 2;
                            value2 = this.limit;
                            break;
                        case '/':
                            this.pos++;
                            this.skipToEndOfLine();
                            value = this.pos;
                            value2 = this.limit;
                            break;
                        default:
                            return value3;
                    }
                } else {
                    if (value3 != '#') {
                        this.pos = value;
                        return value3;
                    }
                    this.pos = value;
                    this.checkLenient();
                    this.skipToEndOfLine();
                    value = this.pos;
                    value2 = this.limit;
                }
            }
        }
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        while (this.pos < this.limit || this.fillBuffer(1)) {
            char value;
            if ((value = this.buffer[this.pos++]) == '\n') {
                this.lineNumber++;
                this.lineStart = this.pos;
            } else if (value != '\r') {
                continue;
            }
            break;
        }
    }

    private boolean skipTo(String text) throws IOException {
        while (this.pos + text.length() <= this.limit || this.fillBuffer(text.length())) {
            if (this.buffer[this.pos] == '\n') {
                this.lineNumber++;
                this.lineStart = this.pos + 1;
            } else {
                int value = 0;
                while (true) {
                    if (value >= text.length()) {
                        return true;
                    }
                    if (this.buffer[this.pos + value] != text.charAt(value)) {
                        break;
                    }
                    value++;
                }
            }
            this.pos++;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.locationString();
    }

    private String locationString() {
        int value = this.lineNumber + 1;
        int value2 = this.pos - this.lineStart + 1;
        return " at line " + value + " column " + value2 + " path " + this.getPath();
    }

    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder().append('$');
        int value = 0;
        for (int value2 = this.stackSize; value < value2; value++) {
            switch (this.stack[value]) {
                case 1:
                case 2:
                    stringBuilder.append('[').append(this.pathIndices[value]).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    stringBuilder.append('.');
                    if (this.pathNames[value] != null) {
                        stringBuilder.append(this.pathNames[value]);
                    }
                case 6:
                case 7:
                case 8:
            }
        }
        return stringBuilder.toString();
    }

    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        char value;
        switch (value = this.buffer[this.pos++]) {
            case '\n':
                this.lineNumber++;
                this.lineStart = this.pos;
            case '"':
            case '\'':
            case '/':
            case '\\':
                return value;
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                } else {
                    char value2 = '\u0000';
                    int value3 = this.pos;
                    for (int value4 = this.pos + 4; value3 < value4; value3++) {
                        char value5 = this.buffer[value3];
                        value2 = (char) (value2 << 4);
                        if (value5 >= '0' && value5 <= '9') {
                            value2 = (char) (value2 + (value5 - '0'));
                        } else if (value5 >= 'a' && value5 <= 'f') {
                            value2 = (char) (value2 + value5 - 'a' + 10);
                        } else {
                            if (value5 < 'A' || value5 > 'F') {
                                throw new NumberFormatException(
                                        "\\u" + new String(this.buffer, this.pos, 4));
                            }
                            value2 = (char) (value2 + value5 - 'A' + 10);
                        }
                    }
                    this.pos += 4;
                    return value2;
                }
            default:
                throw this.syntaxError("Invalid escape sequence");
        }
    }

    private IOException syntaxError(String text) throws IOException {
        throw new MalformedJsonException(text + this.locationString());
    }

    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        this.pos--;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit
                || this.fillBuffer(NON_EXECUTE_PREFIX.length)) {
            for (int index = 0; index < NON_EXECUTE_PREFIX.length; index++) {
                if (this.buffer[this.pos + index] != NON_EXECUTE_PREFIX[index]) {
                    return;
                }
            }
            this.pos = this.pos + NON_EXECUTE_PREFIX.length;
        }
    }

    static {
        JsonReaderInternalAccess.INSTANCE =
                new JsonReaderInternalAccess() {

                    @Override
                    public void promoteNameToValue(JsonReader jsonReader) throws IOException {
                        if (jsonReader instanceof JsonTreeReader) {
                            ((JsonTreeReader) jsonReader).promoteNameToValue();
                        } else {
                            int value = jsonReader.peeked;
                            if (jsonReader.peeked == 0) {
                                value = jsonReader.doPeek();
                            }
                            if (value == 13) {
                                jsonReader.peeked = 9;
                            } else if (value == 12) {
                                jsonReader.peeked = 8;
                            } else {
                                if (value != 14) {
                                    throw new IllegalStateException(
                                            "Expected a name but was "
                                                    + jsonReader.peek()
                                                    + jsonReader.locationString());
                                }
                                jsonReader.peeked = 10;
                            }
                        }
                    }
                };
    }
}
