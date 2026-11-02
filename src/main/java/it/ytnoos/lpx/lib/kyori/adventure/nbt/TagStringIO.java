package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;

public final class TagStringIO {

    private static final TagStringIO INSTANCE = new TagStringIO(new TagStringIO.Builder());

    private final boolean acceptLegacy;

    private final boolean emitLegacy;

    private final boolean acceptHeterogeneousLists;

    private final boolean emitHeterogeneousLists;

    private final String indent;

    @Deprecated
    @NotNull
    public static TagStringIO get() {
        return tagStringIO();
    }

    @NotNull
    public static TagStringIO tagStringIO() {
        return INSTANCE;
    }

    @NotNull
    public static TagStringIO.Builder builder() {
        return new TagStringIO.Builder();
    }

    private TagStringIO(@NotNull TagStringIO.Builder builder) {
        this.acceptLegacy = builder.acceptLegacy;
        this.emitLegacy = builder.emitLegacy;
        this.acceptHeterogeneousLists = builder.acceptHeterogeneousLists;
        this.emitHeterogeneousLists = builder.emitHeterogeneousLists;
        this.indent = builder.indent;
    }

    @NotNull
    public CompoundBinaryTag asCompound(@NotNull String text) throws IOException {
        Objects.requireNonNull(text, "input");
        try {
            CharBuffer charBuffer = new CharBuffer(text);
            TagStringReader tagStringReader;
            (tagStringReader = new TagStringReader(charBuffer)).legacy(this.acceptLegacy);
            tagStringReader.heterogeneousLists(this.acceptHeterogeneousLists);
            CompoundBinaryTag compoundBinaryTag = tagStringReader.compound();
            if (charBuffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            } else {
                return compoundBinaryTag;
            }
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    @NotNull
    public BinaryTag asTag(@NotNull String text) throws IOException {
        Objects.requireNonNull(text, "input");
        try {
            CharBuffer charBuffer = new CharBuffer(text);
            TagStringReader tagStringReader;
            (tagStringReader = new TagStringReader(charBuffer)).legacy(this.acceptLegacy);
            tagStringReader.heterogeneousLists(this.acceptHeterogeneousLists);
            BinaryTag binaryTag = tagStringReader.tag();
            if (charBuffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first Tag");
            } else {
                return binaryTag;
            }
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    @NotNull
    public CompoundBinaryTag asCompound(@NotNull String text, @NotNull Appendable appendable)
            throws IOException {
        Objects.requireNonNull(text, "input");
        Objects.requireNonNull(appendable, "remainder");
        try {
            CharBuffer charBuffer = new CharBuffer(text);
            TagStringReader tagStringReader;
            (tagStringReader = new TagStringReader(charBuffer)).legacy(this.acceptLegacy);
            tagStringReader.heterogeneousLists(this.acceptHeterogeneousLists);
            CompoundBinaryTag compoundBinaryTag = tagStringReader.compound();
            appendable.append(charBuffer.takeRest());
            return compoundBinaryTag;
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    @NotNull
    public BinaryTag asTag(@NotNull String text, @NotNull Appendable appendable)
            throws IOException {
        Objects.requireNonNull(text, "input");
        Objects.requireNonNull(appendable, "remainder");
        try {
            CharBuffer charBuffer = new CharBuffer(text);
            TagStringReader tagStringReader;
            (tagStringReader = new TagStringReader(charBuffer)).legacy(this.acceptLegacy);
            tagStringReader.heterogeneousLists(this.acceptHeterogeneousLists);
            BinaryTag binaryTag = tagStringReader.tag();
            appendable.append(charBuffer.takeRest());
            return binaryTag;
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    @NotNull
    public String asString(@NotNull CompoundBinaryTag compoundBinaryTag) throws IOException {
        return this.asString((BinaryTag) compoundBinaryTag);
    }

    @NotNull
    public String asString(@NotNull BinaryTag binaryTag) throws IOException {
        Objects.requireNonNull(binaryTag, "input");
        StringBuilder stringBuilder = new StringBuilder();
        TagStringWriter tagStringWriter = new TagStringWriter(stringBuilder, this.indent);
        try {
            tagStringWriter.legacy(this.emitLegacy);
            tagStringWriter.heterogeneousLists(this.emitHeterogeneousLists);
            tagStringWriter.writeTag(binaryTag);
        } catch (Throwable throwable) {
            try {
                tagStringWriter.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        tagStringWriter.close();
        return stringBuilder.toString();
    }

    public void toWriter(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Writer writer)
            throws IOException {
        this.toWriter((BinaryTag) compoundBinaryTag, writer);
    }

    public void toWriter(@NotNull BinaryTag binaryTag, @NotNull Writer writer) throws IOException {
        Objects.requireNonNull(binaryTag, "input");
        Objects.requireNonNull(writer, "dest");
        TagStringWriter tagStringWriter = new TagStringWriter(writer, this.indent);
        try {
            tagStringWriter.legacy(this.emitLegacy);
            tagStringWriter.heterogeneousLists(this.emitHeterogeneousLists);
            tagStringWriter.writeTag(binaryTag);
        } catch (Throwable throwable) {
            try {
                tagStringWriter.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        tagStringWriter.close();
    }

    public static class Builder {

        private boolean acceptLegacy = true;

        private boolean emitLegacy = false;

        private boolean acceptHeterogeneousLists = false;

        private boolean emitHeterogeneousLists = false;

        private String indent = "";

        Builder() {}

        @NotNull
        public TagStringIO.Builder indent(int value) {
            if (value == 0) {
                this.indent = "";
            } else if (!this.indent.isEmpty() && this.indent.charAt(0) != ' '
                    || value != this.indent.length()) {
                char[] values;
                Arrays.fill(values = new char[value], ' ');
                this.indent = String.copyValueOf(values);
            }
            return this;
        }

        @NotNull
        public TagStringIO.Builder indentTab(int value) {
            if (value == 0) {
                this.indent = "";
            } else if (!this.indent.isEmpty() && this.indent.charAt(0) != '\t'
                    || value != this.indent.length()) {
                char[] values;
                Arrays.fill(values = new char[value], '\t');
                this.indent = String.copyValueOf(values);
            }
            return this;
        }

        @NotNull
        public TagStringIO.Builder acceptLegacy(boolean acceptLegacy) {
            this.acceptLegacy = acceptLegacy;
            return this;
        }

        @NotNull
        public TagStringIO.Builder emitLegacy(boolean emitLegacy) {
            this.emitLegacy = emitLegacy;
            return this;
        }

        @NotNull
        public TagStringIO.Builder acceptHeterogeneousLists(boolean acceptHeterogeneousLists) {
            this.acceptHeterogeneousLists = acceptHeterogeneousLists;
            return this;
        }

        @NotNull
        public TagStringIO.Builder emitHeterogeneousLists(boolean emitHeterogeneousLists) {
            this.emitHeterogeneousLists = emitHeterogeneousLists;
            return this;
        }

        @NotNull
        public TagStringIO build() {
            return new TagStringIO(this);
        }
    }
}
