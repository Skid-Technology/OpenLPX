package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class Filterable<T> {

    private T raw;

    @Nullable private T filtered;

    public Filterable(T t) {
        this((T) t, (T) null);
    }

    public Filterable(T t, Optional<T> optional) {
        this((T) t, (T) optional.orElse(null));
    }

    public Filterable(T t, @Nullable T t2) {
        this.raw = (T) t;
        this.filtered = (T) t2;
    }

    public static <T> Filterable<T> read(
            PacketWrapper<?> packetWrapper, PacketWrapper.Reader<T> reader) {
        T t = reader.apply(packetWrapper);
        Object result = packetWrapper.readOptional(reader);
        return new Filterable<>((T) t, (T) result);
    }

    public static <T> void write(
            PacketWrapper<?> packetWrapper,
            Filterable<T> filterable,
            PacketWrapper.Writer<T> writer) {
        writer.accept(packetWrapper, filterable.raw);
        packetWrapper.writeOptional(filterable.filtered, writer);
    }

    public T getRaw() {
        return this.raw;
    }

    public void setRaw(T t) {
        this.raw = (T) t;
    }

    @Nullable
    public T getFiltered() {
        return this.filtered;
    }

    public void setFiltered(@Nullable T t) {
        this.filtered = (T) t;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof Filterable)) {
            return false;
        }
        Filterable filterable = (Filterable) result;
        return !this.raw.equals(filterable.raw)
                ? false
                : Objects.equals(this.filtered, filterable.filtered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.raw, this.filtered);
    }
}
