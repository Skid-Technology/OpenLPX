package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

final class BinaryTagReaderImpl implements BinaryTagIO.Reader {

    private final long maxBytes;

    static final BinaryTagIO.Reader UNLIMITED = new BinaryTagReaderImpl(-1L);

    static final BinaryTagIO.Reader DEFAULT_LIMIT = new BinaryTagReaderImpl(131082L);

    BinaryTagReaderImpl(long maxBytes) {
        this.maxBytes = maxBytes;
    }

    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull Path path, BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        CompoundBinaryTag compoundBinaryTag;
        try {
            compoundBinaryTag = this.read(inputStream, compression);
        } catch (Throwable throwable) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return compoundBinaryTag;
    }

    @NotNull
    @Override
    public CompoundBinaryTag read(
            @NotNull InputStream inputStream, BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataInputStream dataInputStream =
                new DataInputStream(
                        new BufferedInputStream(
                                compression.decompress(IOStreamUtil.closeShield(inputStream))));
        CompoundBinaryTag compoundBinaryTag;
        try {
            compoundBinaryTag = this.read((DataInput) dataInputStream);
        } catch (Throwable throwable) {
            try {
                dataInputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataInputStream.close();
        return compoundBinaryTag;
    }

    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull DataInput dataInput) throws IOException {
        return this.read(dataInput, true);
    }

    @NotNull
    private CompoundBinaryTag read(@NotNull DataInput dataInput, boolean matches)
            throws IOException {
        if (!(dataInput instanceof TrackingDataInput)) {
            dataInput = new TrackingDataInput(dataInput, this.maxBytes);
        }
        requireCompound(BinaryTagType.binaryTagType(dataInput.readByte()));
        if (matches) {
            dataInput.skipBytes(dataInput.readUnsignedShort());
        }
        return BinaryTagTypes.COMPOUND.read(dataInput);
    }

    @NotNull
    @Override
    public CompoundBinaryTag readNameless(
            @NotNull Path path, BinaryTagIO.@NotNull Compression compression) throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        CompoundBinaryTag compoundBinaryTag;
        try {
            compoundBinaryTag = this.readNameless(inputStream, compression);
        } catch (Throwable throwable) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return compoundBinaryTag;
    }

    @NotNull
    @Override
    public CompoundBinaryTag readNameless(
            @NotNull InputStream inputStream, BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataInputStream dataInputStream =
                new DataInputStream(
                        new BufferedInputStream(
                                compression.decompress(IOStreamUtil.closeShield(inputStream))));
        CompoundBinaryTag compoundBinaryTag;
        try {
            compoundBinaryTag = this.readNameless((DataInput) dataInputStream);
        } catch (Throwable throwable) {
            try {
                dataInputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataInputStream.close();
        return compoundBinaryTag;
    }

    @NotNull
    @Override
    public CompoundBinaryTag readNameless(@NotNull DataInput dataInput) throws IOException {
        return this.read(dataInput, false);
    }

    @Override
    @NotNull
    public Entry<String, CompoundBinaryTag> readNamed(
            @NotNull Path path, BinaryTagIO.@NotNull Compression compression) throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        Entry<String, CompoundBinaryTag> entry;
        try {
            entry = this.readNamed(inputStream, compression);
        } catch (Throwable throwable) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (inputStream != null) {
            inputStream.close();
        }
        return entry;
    }

    @Override
    @NotNull
    public Entry<String, CompoundBinaryTag> readNamed(
            @NotNull InputStream inputStream, BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataInputStream dataInputStream =
                new DataInputStream(
                        new BufferedInputStream(
                                compression.decompress(IOStreamUtil.closeShield(inputStream))));
        Entry<String, CompoundBinaryTag> entry;
        try {
            entry = this.readNamed((DataInput) dataInputStream);
        } catch (Throwable throwable) {
            try {
                dataInputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataInputStream.close();
        return entry;
    }

    @Override
    @NotNull
    public Entry<String, CompoundBinaryTag> readNamed(@NotNull DataInput dataInput)
            throws IOException {
        requireCompound(BinaryTagType.binaryTagType(dataInput.readByte()));
        String text = dataInput.readUTF();
        return new SimpleImmutableEntry<>(text, BinaryTagTypes.COMPOUND.read(dataInput));
    }

    private static void requireCompound(BinaryTagType<? extends BinaryTag> binaryTagType)
            throws IOException {
        if (binaryTagType != BinaryTagTypes.COMPOUND) {
            throw new IOException(
                    String.format(
                            "Expected root tag to be a %s, was %s",
                            BinaryTagTypes.COMPOUND, binaryTagType));
        }
    }
}
