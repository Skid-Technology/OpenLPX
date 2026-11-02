package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import java.util.NoSuchElementException;

public final class AdventureIndexUtil {

    private AdventureIndexUtil() {}

    public static <K, V> V indexValueOrThrow(Index<K, V> index, K k) {
        V v;
        if ((v = index.value(k)) == null) {
            throw new NoSuchElementException("There is no value for key " + k);
        } else {
            return (V) v;
        }
    }

    public static <K, V> K indexKeyOrThrow(Index<K, V> index, V v) {
        K k;
        if ((k = index.key(v)) == null) {
            throw new NoSuchElementException("There is no key for value " + v);
        } else {
            return (K) k;
        }
    }
}
