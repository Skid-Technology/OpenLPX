package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.internal.Internals;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

final class DataComponentValueConversionImpl<I, O>
        implements DataComponentValueConverterRegistry.Conversion<I, O> {

    private final Class<I> source;

    private final Class<O> destination;

    private final BiFunction<Key, I, O> conversion;

    DataComponentValueConversionImpl(
            @NotNull Class<I> classValue,
            @NotNull Class<O> classValue2,
            @NotNull BiFunction<Key, I, O> biFunction) {
        this.source = classValue;
        this.destination = classValue2;
        this.conversion = biFunction;
    }

    @NotNull
    @Override
    public Class<I> source() {
        return this.source;
    }

    @NotNull
    @Override
    public Class<O> destination() {
        return this.destination;
    }

    @NotNull
    @Override
    public O convert(@NotNull Key key, @NotNull I i) {
        return this.conversion.apply(
                Objects.requireNonNull(key, "key"), Objects.requireNonNull((I) i, "input"));
    }

    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("source", this.source),
                ExaminableProperty.of("destination", this.destination),
                ExaminableProperty.of("conversion", this.conversion));
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            DataComponentValueConversionImpl dataComponentValueConversionImpl =
                    (DataComponentValueConversionImpl) result;
            return Objects.equals(this.source, dataComponentValueConversionImpl.source)
                    && Objects.equals(
                            this.destination, dataComponentValueConversionImpl.destination)
                    && Objects.equals(this.conversion, dataComponentValueConversionImpl.conversion);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.destination, this.conversion);
    }
}
