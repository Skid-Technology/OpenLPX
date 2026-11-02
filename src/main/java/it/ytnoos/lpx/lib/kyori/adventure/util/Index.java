package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Index<K, V> {

    private final Map<K, V> keyToValue;

    private final Map<V, K> valueToKey;

    private Index(Map<K, V> entries, Map<V, K> entries2) {
        this.keyToValue = entries;
        this.valueToKey = entries2;
    }

    @NotNull
    public static <K, V extends Enum<V>> Index<K, V> create(
            Class<V> classValue, @NotNull Function<? super V, ? extends K> function) {
        return create(classValue, function, (V[]) classValue.getEnumConstants());
    }

    @SafeVarargs
    @NotNull
    public static <K, V extends Enum<V>> Index<K, V> create(
            Class<V> classValue,
            @NotNull Function<? super V, ? extends K> function,
            @NotNull V... v) {
        return create((V[]) v, result -> new EnumMap<>(((Class) classValue)), function);
    }

    @SafeVarargs
    @NotNull
    public static <K, V> Index<K, V> create(
            @NotNull Function<? super V, ? extends K> function, @NotNull V... v) {
        return create((V[]) v, (IntFunction<Map<V, K>>) (HashMap::new), function);
    }

    @NotNull
    public static <K, V> Index<K, V> create(
            @NotNull Function<? super V, ? extends K> function, @NotNull List<V> values) {
        return create(values, (IntFunction<Map<V, K>>) (HashMap::new), function);
    }

    @NotNull
    private static <K, V> Index<K, V> create(
            V[] vs,
            IntFunction<Map<V, K>> intFunction,
            @NotNull Function<? super V, ? extends K> function) {
        return create(Arrays.asList((V[]) vs), intFunction, function);
    }

    @NotNull
    private static <K, V> Index<K, V> create(
            List<V> values,
            IntFunction<Map<V, K>> intFunction,
            @NotNull Function<? super V, ? extends K> function) {
        int size2 = values.size();
        HashMap entries = new HashMap(size2);
        Map entries2 = (Map) intFunction.apply(size2);
        for (int index = 0; index < size2; index++) {
            V v = values.get(index);
            Object result = function.apply(v);
            if (entries.putIfAbsent(result, v) != null) {
                throw new IllegalStateException(
                        String.format(
                                "Key %s already mapped to value %s", result, entries.get(result)));
            }
            if (entries2.putIfAbsent(v, result) != null) {
                throw new IllegalStateException(
                        String.format("Value %s already mapped to key %s", v, entries2.get(v)));
            }
        }
        return new Index<>(
                Collections.unmodifiableMap(entries), Collections.unmodifiableMap(entries2));
    }

    @NotNull
    public Set<K> keys() {
        return Collections.unmodifiableSet(this.keyToValue.keySet());
    }

    @Nullable
    public K key(@NotNull V v) {
        return this.valueToKey.get(v);
    }

    @NotNull
    public K keyOrThrow(@NotNull V v) {
        K k;
        if ((k = this.key((V) v)) == null) {
            throw new NoSuchElementException("There is no key for value " + v);
        } else {
            return (K) k;
        }
    }

    @Contract("_, null -> null; _, !null -> !null")
    public K keyOr(@NotNull V v, @Nullable K k) {
        K k2;
        return (K) ((k2 = this.key((V) v)) == null ? k : k2);
    }

    @NotNull
    public Set<V> values() {
        return Collections.unmodifiableSet(this.valueToKey.keySet());
    }

    @Nullable
    public V value(@NotNull K k) {
        return this.keyToValue.get(k);
    }

    @NotNull
    public V valueOrThrow(@NotNull K k) {
        V v;
        if ((v = this.value((K) k)) == null) {
            throw new NoSuchElementException("There is no value for key " + k);
        } else {
            return (V) v;
        }
    }

    @Contract("_, null -> null; _, !null -> !null")
    public V valueOr(@NotNull K k, @Nullable V v) {
        V v2;
        return (V) ((v2 = this.value((K) k)) == null ? v : v2);
    }

    @NotNull
    public Map<K, V> keyToValue() {
        return Collections.unmodifiableMap(this.keyToValue);
    }

    @NotNull
    public Map<V, K> valueToKey() {
        return Collections.unmodifiableMap(this.valueToKey);
    }
}
