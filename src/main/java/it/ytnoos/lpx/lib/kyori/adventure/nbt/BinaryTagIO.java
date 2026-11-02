package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map.Entry;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public final class BinaryTagIO {

    private BinaryTagIO() {}

    @NotNull
    public static BinaryTagIO.Reader unlimitedReader() {
        return BinaryTagReaderImpl.UNLIMITED;
    }

    @NotNull
    public static BinaryTagIO.Reader reader() {
        return BinaryTagReaderImpl.DEFAULT_LIMIT;
    }

    @NotNull
    public static BinaryTagIO.Reader reader(long value) {
        if (value <= 0L) {
            throw new IllegalArgumentException("The size limit must be greater than zero");
        } else {
            return new BinaryTagReaderImpl(value);
        }
    }

    @NotNull
    public static BinaryTagIO.Writer writer() {
        return BinaryTagWriterImpl.INSTANCE;
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readPath(@NotNull Path path) throws IOException {
        return reader().read(path);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readInputStream(@NotNull InputStream inputStream)
            throws IOException {
        return reader().read(inputStream);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedPath(@NotNull Path path) throws IOException {
        return reader().read(path, BinaryTagIO.Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedInputStream(@NotNull InputStream inputStream)
            throws IOException {
        return reader().read(inputStream, BinaryTagIO.Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readDataInput(@NotNull DataInput dataInput) throws IOException {
        return reader().read(dataInput);
    }

    @Deprecated
    public static void writePath(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path)
            throws IOException {
        writer().write(compoundBinaryTag, path);
    }

    @Deprecated
    public static void writeOutputStream(
            @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream)
            throws IOException {
        writer().write(compoundBinaryTag, outputStream);
    }

    @Deprecated
    public static void writeCompressedPath(
            @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path) throws IOException {
        writer().write(compoundBinaryTag, path, BinaryTagIO.Compression.GZIP);
    }

    @Deprecated
    public static void writeCompressedOutputStream(
            @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream)
            throws IOException {
        writer().write(compoundBinaryTag, outputStream, BinaryTagIO.Compression.GZIP);
    }

    @Deprecated
    public static void writeDataOutput(
            @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput)
            throws IOException {
        writer().write(compoundBinaryTag, dataOutput);
    }

    static {
        BinaryTagTypes.COMPOUND.id();
    }

    public abstract static class Compression {

        public static final BinaryTagIO.Compression NONE =
                new BinaryTagIO.Compression() {

                    @NotNull
                    @Override
                    InputStream decompress(@NotNull InputStream inputStream) {
                        return inputStream;
                    }

                    @NotNull
                    @Override
                    OutputStream compress(@NotNull OutputStream outputStream) {
                        return outputStream;
                    }

                    @Override
                    public String toString() {
                        return "Compression.NONE";
                    }
                };

        public static final BinaryTagIO.Compression GZIP =
                new BinaryTagIO.Compression() {

                    @NotNull
                    @Override
                    InputStream decompress(@NotNull InputStream inputStream) throws IOException {
                        return new GZIPInputStream(inputStream);
                    }

                    @NotNull
                    @Override
                    OutputStream compress(@NotNull OutputStream outputStream) throws IOException {
                        return new GZIPOutputStream(outputStream);
                    }

                    @Override
                    public String toString() {
                        return "Compression.GZIP";
                    }
                };

        public static final BinaryTagIO.Compression ZLIB =
                new BinaryTagIO.Compression() {

                    @NotNull
                    @Override
                    InputStream decompress(@NotNull InputStream inputStream) {
                        return new InflaterInputStream(inputStream);
                    }

                    @NotNull
                    @Override
                    OutputStream compress(@NotNull OutputStream outputStream) {
                        return new DeflaterOutputStream(outputStream);
                    }

                    @Override
                    public String toString() {
                        return "Compression.ZLIB";
                    }
                };

        @NotNull
        abstract InputStream decompress(@NotNull InputStream inputStream) throws IOException;

        @NotNull
        abstract OutputStream compress(@NotNull OutputStream outputStream) throws IOException;
    }

    public interface Reader {

        @NotNull
        default CompoundBinaryTag read(@NotNull Path path) throws IOException {
            return this.read(path, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        CompoundBinaryTag read(@NotNull Path path, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        default CompoundBinaryTag read(@NotNull InputStream inputStream) throws IOException {
            return this.read(inputStream, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        CompoundBinaryTag read(
                @NotNull InputStream inputStream, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        CompoundBinaryTag read(@NotNull DataInput dataInput) throws IOException;

        @NotNull
        default CompoundBinaryTag readNameless(@NotNull Path path) throws IOException {
            return this.readNameless(path, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        CompoundBinaryTag readNameless(
                @NotNull Path path, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        default CompoundBinaryTag readNameless(@NotNull InputStream inputStream)
                throws IOException {
            return this.readNameless(inputStream, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        CompoundBinaryTag readNameless(
                @NotNull InputStream inputStream, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        CompoundBinaryTag readNameless(@NotNull DataInput dataInput) throws IOException;

        @NotNull
        default Entry<String, CompoundBinaryTag> readNamed(@NotNull Path path) throws IOException {
            return this.readNamed(path, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        Entry<String, CompoundBinaryTag> readNamed(
                @NotNull Path path, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        default Entry<String, CompoundBinaryTag> readNamed(@NotNull InputStream inputStream)
                throws IOException {
            return this.readNamed(inputStream, BinaryTagIO.Compression.NONE);
        }

        @NotNull
        Entry<String, CompoundBinaryTag> readNamed(
                @NotNull InputStream inputStream, @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        @NotNull
        Entry<String, CompoundBinaryTag> readNamed(@NotNull DataInput dataInput) throws IOException;
    }

    public interface Writer {

        default void write(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path)
                throws IOException {
            this.write(compoundBinaryTag, path, BinaryTagIO.Compression.NONE);
        }

        void write(
                @NotNull CompoundBinaryTag compoundBinaryTag,
                @NotNull Path path,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        default void write(
                @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream)
                throws IOException {
            this.write(compoundBinaryTag, outputStream, BinaryTagIO.Compression.NONE);
        }

        void write(
                @NotNull CompoundBinaryTag compoundBinaryTag,
                @NotNull OutputStream outputStream,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        void write(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput)
                throws IOException;

        default void writeNameless(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path)
                throws IOException {
            this.writeNameless(compoundBinaryTag, path, BinaryTagIO.Compression.NONE);
        }

        void writeNameless(
                @NotNull CompoundBinaryTag compoundBinaryTag,
                @NotNull Path path,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        default void writeNameless(
                @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream)
                throws IOException {
            this.writeNameless(compoundBinaryTag, outputStream, BinaryTagIO.Compression.NONE);
        }

        void writeNameless(
                @NotNull CompoundBinaryTag compoundBinaryTag,
                @NotNull OutputStream outputStream,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        void writeNameless(
                @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput)
                throws IOException;

        default void writeNamed(@NotNull Entry<String, CompoundBinaryTag> entry, @NotNull Path path)
                throws IOException {
            this.writeNamed(entry, path, BinaryTagIO.Compression.NONE);
        }

        void writeNamed(
                @NotNull Entry<String, CompoundBinaryTag> entry,
                @NotNull Path path,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        default void writeNamed(
                @NotNull Entry<String, CompoundBinaryTag> entry, @NotNull OutputStream outputStream)
                throws IOException {
            this.writeNamed(entry, outputStream, BinaryTagIO.Compression.NONE);
        }

        void writeNamed(
                @NotNull Entry<String, CompoundBinaryTag> entry,
                @NotNull OutputStream outputStream,
                @NotNull BinaryTagIO.Compression compression)
                throws IOException;

        void writeNamed(
                @NotNull Entry<String, CompoundBinaryTag> entry, @NotNull DataOutput dataOutput)
                throws IOException;
    }
}
