package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;

final class TagStringWriter implements AutoCloseable {

    private final Appendable out;

    private final String indent;

    private int level;

    private boolean needsSeparator;

    private boolean legacy;

    private boolean heterogeneousLists;

    TagStringWriter(Appendable appendable, String indent) {
        this.out = appendable;
        this.indent = indent;
    }

    public TagStringWriter legacy(boolean legacy) {
        this.legacy = legacy;
        return this;
    }

    public TagStringWriter heterogeneousLists(boolean heterogeneousLists) {
        this.heterogeneousLists = heterogeneousLists;
        return this;
    }

    public TagStringWriter writeTag(BinaryTag binaryTag) throws IOException {
        BinaryTagType binaryTagType;
        if ((binaryTagType = binaryTag.type()) == BinaryTagTypes.COMPOUND) {
            return this.writeCompound((CompoundBinaryTag) binaryTag);
        } else if (binaryTagType == BinaryTagTypes.LIST) {
            return this.writeList((ListBinaryTag) binaryTag);
        } else if (binaryTagType == BinaryTagTypes.BYTE_ARRAY) {
            return this.writeByteArray((ByteArrayBinaryTag) binaryTag);
        } else if (binaryTagType == BinaryTagTypes.INT_ARRAY) {
            return this.writeIntArray((IntArrayBinaryTag) binaryTag);
        } else if (binaryTagType == BinaryTagTypes.LONG_ARRAY) {
            return this.writeLongArray((LongArrayBinaryTag) binaryTag);
        } else if (binaryTagType == BinaryTagTypes.STRING) {
            return this.value(((StringBinaryTag) binaryTag).value(), '\u0000');
        } else if (binaryTagType == BinaryTagTypes.BYTE) {
            return this.value(Byte.toString(((ByteBinaryTag) binaryTag).value()), 'b');
        } else if (binaryTagType == BinaryTagTypes.SHORT) {
            return this.value(Short.toString(((ShortBinaryTag) binaryTag).value()), 's');
        } else if (binaryTagType == BinaryTagTypes.INT) {
            return this.value(Integer.toString(((IntBinaryTag) binaryTag).value()), 'i');
        } else if (binaryTagType == BinaryTagTypes.LONG) {
            return this.value(
                    Long.toString(((LongBinaryTag) binaryTag).value()), Character.toUpperCase('l'));
        } else if (binaryTagType == BinaryTagTypes.FLOAT) {
            return this.value(Float.toString(((FloatBinaryTag) binaryTag).value()), 'f');
        } else if (binaryTagType == BinaryTagTypes.DOUBLE) {
            return this.value(Double.toString(((DoubleBinaryTag) binaryTag).value()), 'd');
        } else {
            throw new IOException("Unknown tag type: " + binaryTagType);
        }
    }

    private TagStringWriter writeCompound(CompoundBinaryTag compoundBinaryTag) throws IOException {
        this.beginCompound();
        for (Entry entry : (Iterable<Entry>) ((Iterable) compoundBinaryTag)) {
            this.key((String) entry.getKey());
            this.writeTag((BinaryTag) entry.getValue());
        }
        this.endCompound();
        return this;
    }

    private TagStringWriter writeList(ListBinaryTag listBinaryTag) throws IOException {
        ListBinaryTag listBinaryTag2 =
                this.heterogeneousLists
                        ? listBinaryTag.unwrapHeterogeneity()
                        : listBinaryTag.wrapHeterogeneity();
        this.beginList();
        int value = 0;
        boolean matches =
                this.prettyPrinting() && this.breakListElement(listBinaryTag2.elementType());
        for (BinaryTag binaryTag : (Iterable<BinaryTag>) ((Iterable) listBinaryTag2)) {
            this.printAndResetSeparator(!matches);
            if (matches) {
                this.newlineIndent();
            }
            if (this.legacy) {
                this.out.append(String.valueOf(value++));
                this.appendSeparator(':');
            }
            this.writeTag(binaryTag);
        }
        this.endList(matches);
        return this;
    }

    private TagStringWriter writeByteArray(ByteArrayBinaryTag byteArrayBinaryTag)
            throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        this.beginArray('b');
        char value2 = Character.toUpperCase('b');
        byte[] values = ByteArrayBinaryTagImpl.value(byteArrayBinaryTag);
        int value3 = 0;
        for (int value4 = values.length; value3 < value4; value3++) {
            this.printAndResetSeparator(true);
            this.value(Byte.toString(values[value3]), value2);
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeIntArray(IntArrayBinaryTag intArrayBinaryTag) throws IOException {
        if (this.legacy) {
            this.beginList();
        } else {
            this.beginArray('i');
        }
        int[] values = IntArrayBinaryTagImpl.value(intArrayBinaryTag);
        int value2 = 0;
        for (int value3 = values.length; value2 < value3; value2++) {
            this.printAndResetSeparator(true);
            this.value(Integer.toString(values[value2]), 'i');
        }
        this.endArray();
        return this;
    }

    private TagStringWriter writeLongArray(LongArrayBinaryTag longArrayBinaryTag)
            throws IOException {
        if (this.legacy) {
            throw new IOException("Legacy Mojangson only supports integer arrays!");
        }
        this.beginArray('l');
        long[] values = LongArrayBinaryTagImpl.value(longArrayBinaryTag);
        int value2 = 0;
        for (int value3 = values.length; value2 < value3; value2++) {
            this.printAndResetSeparator(true);
            this.value(Long.toString(values[value2]), 'l');
        }
        this.endArray();
        return this;
    }

    public TagStringWriter beginCompound() throws IOException {
        this.printAndResetSeparator(false);
        this.level++;
        this.out.append('{');
        return this;
    }

    public TagStringWriter endCompound() throws IOException {
        this.level--;
        this.newlineIndent();
        this.out.append('}');
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter key(String text) throws IOException {
        this.printAndResetSeparator(false);
        this.newlineIndent();
        this.writeMaybeQuoted(text, false);
        this.appendSeparator(':');
        return this;
    }

    public TagStringWriter value(String text, char value2) throws IOException {
        if (value2 == 0) {
            this.writeMaybeQuoted(text, true);
        } else {
            this.out.append(text);
            if (value2 != 'i') {
                this.out.append(value2);
            }
        }
        this.needsSeparator = true;
        return this;
    }

    public TagStringWriter beginList() throws IOException {
        this.printAndResetSeparator(false);
        this.level++;
        this.out.append('[');
        return this;
    }

    public TagStringWriter endList(boolean matches) throws IOException {
        this.level--;
        if (matches) {
            this.newlineIndent();
        }
        this.out.append(']');
        this.needsSeparator = true;
        return this;
    }

    private TagStringWriter beginArray(char value) throws IOException {
        this.beginList().out.append(Character.toUpperCase(value)).append(';');
        if (this.prettyPrinting()) {
            this.out.append(' ');
        }
        return this;
    }

    private TagStringWriter endArray() throws IOException {
        return this.endList(false);
    }

    private void writeMaybeQuoted(String text, boolean matches) throws IOException {
        if (!matches) {
            for (int index = 0; index < text.length(); index++) {
                if (!Tokens.id(text.charAt(index))) {
                    matches = true;
                    break;
                }
            }
        }
        if (matches) {
            this.out.append('"');
            this.out.append(escape(text, '"'));
            this.out.append('"');
        } else {
            this.out.append(text);
        }
    }

    private static String escape(String text, char value) {
        StringBuilder stringBuilder = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char value2;
            if ((value2 = text.charAt(index)) == value || value2 == '\\') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(value2);
        }
        return stringBuilder.toString();
    }

    private void printAndResetSeparator(boolean matches) throws IOException {
        if (this.needsSeparator) {
            this.out.append(',');
            if (matches && this.prettyPrinting()) {
                this.out.append(' ');
            }
            this.needsSeparator = false;
        }
    }

    private boolean breakListElement(BinaryTagType<?> binaryTagType) {
        return binaryTagType == BinaryTagTypes.COMPOUND
                || binaryTagType == BinaryTagTypes.LIST
                || binaryTagType == BinaryTagTypes.BYTE_ARRAY
                || binaryTagType == BinaryTagTypes.INT_ARRAY
                || binaryTagType == BinaryTagTypes.LONG_ARRAY;
    }

    private boolean prettyPrinting() {
        return this.indent.length() > 0;
    }

    private void newlineIndent() throws IOException {
        if (this.prettyPrinting()) {
            this.out.append(Tokens.NEWLINE);
            for (int index = 0; index < this.level; index++) {
                this.out.append(this.indent);
            }
        }
    }

    private Appendable appendSeparator(char value) throws IOException {
        this.out.append(value);
        if (this.prettyPrinting()) {
            this.out.append(' ');
        }
        return this.out;
    }

    @Override
    public void close() throws IOException {
        if (this.level != 0) {
            throw new IllegalStateException(
                    "Document finished with unbalanced start and end objects");
        }
        if (this.out instanceof Writer) {
            ((Writer) this.out).flush();
        }
    }
}
