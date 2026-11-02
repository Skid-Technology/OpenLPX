package it.ytnoos.lpx.lib.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter implements Closeable, Flushable {

    private static final String[] REPLACEMENT_CHARS = new String[128];

    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;

    private final Writer out;

    private int[] stack = new int[32];

    private int stackSize = 0;

    private String indent;

    private String separator;

    private boolean lenient;

    private boolean htmlSafe;

    private String deferredName;

    private boolean serializeNulls;

    public JsonWriter(Writer writer) {
        this.push(6);
        this.separator = ":";
        this.serializeNulls = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.out = writer;
    }

    public final void setIndent(String indent) {
        if (indent.length() == 0) {
            this.indent = null;
            this.separator = ":";
        } else {
            this.indent = indent;
            this.separator = ": ";
        }
    }

    public final void setLenient(boolean lenient) {
        this.lenient = lenient;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public final void setHtmlSafe(boolean htmlSafe) {
        this.htmlSafe = htmlSafe;
    }

    public final boolean isHtmlSafe() {
        return this.htmlSafe;
    }

    public final void setSerializeNulls(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }

    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }

    public JsonWriter beginArray() throws IOException {
        this.writeDeferredName();
        return this.open(1, "[");
    }

    public JsonWriter endArray() throws IOException {
        return this.close(1, 2, "]");
    }

    public JsonWriter beginObject() throws IOException {
        this.writeDeferredName();
        return this.open(3, "{");
    }

    public JsonWriter endObject() throws IOException {
        return this.close(3, 5, "}");
    }

    private JsonWriter open(int value, String text) throws IOException {
        this.beforeValue();
        this.push(value);
        this.out.write(text);
        return this;
    }

    private JsonWriter close(int value, int value2, String text) throws IOException {
        int value3;
        if ((value3 = this.peek()) != value2 && value3 != value) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        this.stackSize--;
        if (value3 == value2) {
            this.newline();
        }
        this.out.write(text);
        return this;
    }

    private void push(int value) {
        if (this.stackSize == this.stack.length) {
            int[] values = new int[this.stackSize * 2];
            System.arraycopy(this.stack, 0, values, 0, this.stackSize);
            this.stack = values;
        }
        this.stack[this.stackSize++] = value;
    }

    private int peek() {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            return this.stack[this.stackSize - 1];
        }
    }

    private void replaceTop(int value) {
        this.stack[this.stackSize - 1] = value;
    }

    public JsonWriter name(String deferredName) throws IOException {
        if (deferredName == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = deferredName;
        return this;
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            this.beforeName();
            this.string(this.deferredName);
            this.deferredName = null;
        }
    }

    public JsonWriter value(String text) throws IOException {
        if (text == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue();
        this.string(text);
        return this;
    }

    public JsonWriter jsonValue(String text) throws IOException {
        if (text == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue();
        this.out.append(text);
        return this;
    }

    public JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (!this.serializeNulls) {
                this.deferredName = null;
                return this;
            }
            this.writeDeferredName();
        }
        this.beforeValue();
        this.out.write("null");
        return this;
    }

    public JsonWriter value(boolean matches) throws IOException {
        this.writeDeferredName();
        this.beforeValue();
        this.out.write(matches ? "true" : "false");
        return this;
    }

    public JsonWriter value(Boolean matches) throws IOException {
        if (matches == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue();
        this.out.write(matches ? "true" : "false");
        return this;
    }

    public JsonWriter value(double value2) throws IOException {
        if (!Double.isNaN(value2) && !Double.isInfinite(value2)) {
            this.writeDeferredName();
            this.beforeValue();
            this.out.append(Double.toString(value2));
            return this;
        } else {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value2);
        }
    }

    public JsonWriter value(long value2) throws IOException {
        this.writeDeferredName();
        this.beforeValue();
        this.out.write(Long.toString(value2));
        return this;
    }

    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return this.nullValue();
        } else {
            this.writeDeferredName();
            String text = number.toString();
            if (this.lenient
                    || !text.equals("-Infinity")
                            && !text.equals("Infinity")
                            && !text.equals("NaN")) {
                this.beforeValue();
                this.out.append(text);
                return this;
            } else {
                throw new IllegalArgumentException(
                        "Numeric values must be finite, but was " + number);
            }
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        int value = this.stackSize;
        if (this.stackSize <= 1 && (value != 1 || this.stack[value - 1] == 7)) {
            this.stackSize = 0;
        } else {
            throw new IOException("Incomplete document");
        }
    }

    private void string(String text) throws IOException {
        String[] texts = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
        this.out.write("\"");
        int value = 0;
        int size = text.length();
        for (int index = 0; index < size; index++) {
            char value2;
            String text2;
            if ((value2 = text.charAt(index)) < 128) {
                if ((text2 = texts[value2]) == null) {
                    continue;
                }
            } else if (value2 == 8232) {
                text2 = "\\u2028";
            } else {
                if (value2 != 8233) {
                    continue;
                }
                text2 = "\\u2029";
            }
            if (value < index) {
                this.out.write(text, value, index - value);
            }
            this.out.write(text2);
            value = index + 1;
        }
        if (value < size) {
            this.out.write(text, value, size - value);
        }
        this.out.write("\"");
    }

    private void newline() throws IOException {
        if (this.indent != null) {
            this.out.write("\n");
            int value = 1;
            for (int value2 = this.stackSize; value < value2; value++) {
                this.out.write(this.indent);
            }
        }
    }

    private void beforeName() throws IOException {
        int value;
        if ((value = this.peek()) == 5) {
            this.out.write(44);
        } else if (value != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.newline();
        this.replaceTop(4);
    }

    private void beforeValue() throws IOException {
        switch (this.peek()) {
            case 1:
                this.replaceTop(2);
                this.newline();
                break;
            case 2:
                this.out.append(',');
                this.newline();
                break;
            case 3:
            case 5:
            default:
                throw new IllegalStateException("Nesting problem.");
            case 4:
                this.out.append(this.separator);
                this.replaceTop(5);
                break;
            case 7:
                if (!this.lenient) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            case 6:
                this.replaceTop(7);
        }
    }

    static {
        for (int index = 0; index <= 31; index++) {
            REPLACEMENT_CHARS[index] = String.format("\\u%04x", index);
        }
        REPLACEMENT_CHARS[34] = "\\\"";
        REPLACEMENT_CHARS[92] = "\\\\";
        REPLACEMENT_CHARS[9] = "\\t";
        REPLACEMENT_CHARS[8] = "\\b";
        REPLACEMENT_CHARS[10] = "\\n";
        REPLACEMENT_CHARS[13] = "\\r";
        REPLACEMENT_CHARS[12] = "\\f";
        HTML_SAFE_REPLACEMENT_CHARS = (String[]) REPLACEMENT_CHARS.clone();
        HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
        HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }
}
