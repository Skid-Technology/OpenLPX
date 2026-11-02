package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map.Entry;

final class BinaryTagWriterImpl implements BinaryTagIO.Writer {

    static final BinaryTagIO.Writer INSTANCE = new BinaryTagWriterImpl();

    @Override
    public void write(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull Path path,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        OutputStream outputStream = Files.newOutputStream(path);
        try {
            this.write(compoundBinaryTag, outputStream, compression);
        } catch (Throwable throwable) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    @Override
    public void write(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull OutputStream outputStream,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataOutputStream dataOutputStream =
                new DataOutputStream(
                        new BufferedOutputStream(
                                compression.compress(IOStreamUtil.closeShield(outputStream))));
        try {
            this.write(compoundBinaryTag, (DataOutput) dataOutputStream);
        } catch (Throwable throwable) {
            try {
                dataOutputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataOutputStream.close();
    }

    @Override
    public void write(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput)
            throws IOException {
        this.write(compoundBinaryTag, dataOutput, true);
    }

    private void write(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull DataOutput dataOutput,
            boolean matches)
            throws IOException {
        dataOutput.writeByte(BinaryTagTypes.COMPOUND.id());
        if (matches) {
            dataOutput.writeUTF("");
        }
        BinaryTagTypes.COMPOUND.write(compoundBinaryTag, dataOutput);
    }

    @Override
    public void writeNameless(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull Path path,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        OutputStream outputStream = Files.newOutputStream(path);
        try {
            this.writeNameless(compoundBinaryTag, outputStream, compression);
        } catch (Throwable throwable) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    @Override
    public void writeNameless(
            @NotNull CompoundBinaryTag compoundBinaryTag,
            @NotNull OutputStream outputStream,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataOutputStream dataOutputStream =
                new DataOutputStream(
                        new BufferedOutputStream(
                                compression.compress(IOStreamUtil.closeShield(outputStream))));
        try {
            this.writeNameless(compoundBinaryTag, (DataOutput) dataOutputStream);
        } catch (Throwable throwable) {
            try {
                dataOutputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataOutputStream.close();
    }

    @Override
    public void writeNameless(
            @NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput)
            throws IOException {
        this.write(compoundBinaryTag, dataOutput, false);
    }

    @Override
    public void writeNamed(
            @NotNull Entry<String, CompoundBinaryTag> entry,
            @NotNull Path path,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        OutputStream outputStream = Files.newOutputStream(path);
        try {
            this.writeNamed(entry, outputStream, compression);
        } catch (Throwable throwable) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
            }
            throw throwable;
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    @Override
    public void writeNamed(
            @NotNull Entry<String, CompoundBinaryTag> entry,
            @NotNull OutputStream outputStream,
            BinaryTagIO.@NotNull Compression compression)
            throws IOException {
        DataOutputStream dataOutputStream =
                new DataOutputStream(
                        new BufferedOutputStream(
                                compression.compress(IOStreamUtil.closeShield(outputStream))));
        try {
            this.writeNamed(entry, (DataOutput) dataOutputStream);
        } catch (Throwable throwable) {
            try {
                dataOutputStream.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
            throw throwable;
        }
        dataOutputStream.close();
    }

    @Override
    public void writeNamed(
            @NotNull Entry<String, CompoundBinaryTag> entry, @NotNull DataOutput dataOutput)
            throws IOException {
        dataOutput.writeByte(BinaryTagTypes.COMPOUND.id());
        dataOutput.writeUTF((String) entry.getKey());
        BinaryTagTypes.COMPOUND.write((CompoundBinaryTag) entry.getValue(), dataOutput);
    }
}
