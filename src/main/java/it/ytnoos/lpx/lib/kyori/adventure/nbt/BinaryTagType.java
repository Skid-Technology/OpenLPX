package it.ytnoos.lpx.lib.kyori.adventure.nbt;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BinaryTagType<T extends BinaryTag>
        implements Predicate<BinaryTagType<? extends BinaryTag>> {

    private static final List<BinaryTagType<? extends BinaryTag>> TYPES = new ArrayList<>();

    public abstract byte id();

    abstract boolean numeric();

    @NotNull
    public abstract T read(@NotNull DataInput dataInput) throws IOException;

    public abstract void write(@NotNull T t, @NotNull DataOutput dataOutput) throws IOException;

    static <T extends BinaryTag> void writeUntyped(
            BinaryTagType<? extends BinaryTag> binaryTagType, T t, DataOutput dataOutput)
            throws IOException {
        ((BinaryTagType) binaryTagType).write(t, dataOutput);
    }

    @NotNull
    static BinaryTagType<? extends BinaryTag> binaryTagType(byte value) {
        for (int index = 0; index < TYPES.size(); index++) {
            BinaryTagType binaryTagType2;
            if ((binaryTagType2 = TYPES.get(index)).id() == value) {
                return binaryTagType2;
            }
        }
        throw new IllegalArgumentException(String.valueOf(value));
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static BinaryTagType<? extends BinaryTag> of(byte value) {
        return binaryTagType(value);
    }

    @NotNull
    static <T extends BinaryTag> BinaryTagType<T> register(
            Class<T> classValue,
            byte value,
            BinaryTagType.Reader<T> reader,
            @Nullable BinaryTagType.Writer<T> writer) {
        return register(new BinaryTagType.Impl<>(classValue, value, reader, writer));
    }

    @NotNull
    static <T extends NumberBinaryTag> BinaryTagType<T> registerNumeric(
            Class<T> classValue,
            byte value,
            BinaryTagType.Reader<T> reader,
            BinaryTagType.Writer<T> writer) {
        return register(new BinaryTagType.Impl.Numeric<>(classValue, value, reader, writer));
    }

    private static <T extends BinaryTag, Y extends BinaryTagType<T>> Y register(Y y) {
        TYPES.add(y);
        return (Y) y;
    }

    public boolean test(BinaryTagType<? extends BinaryTag> binaryTagType) {
        return this == binaryTagType || this.numeric() && binaryTagType.numeric();
    }

    static class Impl<T extends BinaryTag> extends BinaryTagType<T> {

        final Class<T> type;

        final byte id;

        private final BinaryTagType.Reader<T> reader;

        @Nullable private final BinaryTagType.Writer<T> writer;

        Impl(
                Class<T> classValue,
                byte id,
                BinaryTagType.Reader<T> reader2,
                @Nullable BinaryTagType.Writer<T> writer2) {
            this.type = classValue;
            this.id = id;
            this.reader = reader2;
            this.writer = writer2;
        }

        @NotNull
        @Override
        public final T read(@NotNull DataInput dataInput) throws IOException {
            return this.reader.read(dataInput);
        }

        @Override
        public final void write(@NotNull T t, @NotNull DataOutput dataOutput) throws IOException {
            if (this.writer != null) {
                this.writer.write((T) t, dataOutput);
            }
        }

        @Override
        public final byte id() {
            return this.id;
        }

        @Override
        boolean numeric() {
            return false;
        }

        @Override
        public String toString() {
            return BinaryTagType.class.getSimpleName()
                    + 91
                    + this.type.getSimpleName()
                    + " "
                    + this.id
                    + "]";
        }

        static class Numeric<T extends BinaryTag> extends BinaryTagType.Impl<T> {

            Numeric(
                    Class<T> classValue,
                    byte value,
                    BinaryTagType.Reader<T> reader,
                    @Nullable BinaryTagType.Writer<T> writer) {
                super(classValue, value, reader, writer);
            }

            @Override
            boolean numeric() {
                return true;
            }

            @Override
            public String toString() {
                return BinaryTagType.class.getSimpleName()
                        + 674090651
                        + this.type.getSimpleName()
                        + " "
                        + this.id
                        + " (numeric)]";
            }
        }
    }

    interface Reader<T extends BinaryTag> {

        @NotNull
        T read(@NotNull DataInput dataInput) throws IOException;
    }

    interface Writer<T extends BinaryTag> {

        void write(@NotNull T t, @NotNull DataOutput dataOutput) throws IOException;
    }
}
