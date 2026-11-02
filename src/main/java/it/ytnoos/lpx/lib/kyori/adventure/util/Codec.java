package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

public interface Codec<D, E, DX extends Throwable, EX extends Throwable> {

    @NotNull
    static <D, E, DX extends Throwable, EX extends Throwable> Codec<D, E, DX, EX> codec(
            @NotNull final Codec.Decoder<D, E, DX> decoder,
            @NotNull final Codec.Encoder<D, E, EX> encoder) {
        return new Codec<D, E, DX, EX>() {

            @NotNull
            @Override
            public D decode(@NotNull E result) throws DX {
                return (D) decoder.decode(result);
            }

            @NotNull
            @Override
            public E encode(@NotNull D result) throws EX {
                return (E) encoder.encode(result);
            }
        };
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "5.0.0")
    @NotNull
    static <D, E, DX extends Throwable, EX extends Throwable> Codec<D, E, DX, EX> of(
            @NotNull final Codec.Decoder<D, E, DX> decoder,
            @NotNull final Codec.Encoder<D, E, EX> encoder) {
        return new Codec<D, E, DX, EX>() {

            @NotNull
            @Override
            public D decode(@NotNull E result) throws DX {
                return (D) decoder.decode(result);
            }

            @NotNull
            @Override
            public E encode(@NotNull D result) throws EX {
                return (E) encoder.encode(result);
            }
        };
    }

    @NotNull
    D decode(@NotNull E e) throws DX;

    @NotNull
    E encode(@NotNull D d) throws EX;

    interface Decoder<D, E, X extends Throwable> {

        @NotNull
        D decode(@NotNull E e) throws X;
    }

    interface Encoder<D, E, X extends Throwable> {

        @NotNull
        E encode(@NotNull D d) throws X;
    }
}
