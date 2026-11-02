package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@NullMarked
@Internal
public final class MapUtil {

    private MapUtil() {}

    public static boolean isDeepEqual(@Nullable Object result, @Nullable Object result2) {
        if (result == result2) {
            return true;
        } else if (result == null || result2 == null) {
            return false;
        } else if (result instanceof DeepComparableEntity) {
            return ((DeepComparableEntity) result).deepEquals(result2);
        } else {
            return result2 instanceof DeepComparableEntity
                    ? ((DeepComparableEntity) result2).deepEquals(result2)
                    : Objects.equals(result, result2);
        }
    }

    public static <K> boolean isDeepEqual(Map<K, ?> entries, Map<K, ?> entries2) {
        if (entries.isEmpty() && entries2.isEmpty()) {
            return true;
        }
        if (entries.size() != entries2.size()) {
            return false;
        }
        for (Entry<K, ?> entry : entries.entrySet()) {
            Object result = entries2.get(entry.getKey());
            if (!isDeepEqual(entry.getValue(), result)) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> createMap(Entry<? extends K, ? extends V>... entry) {
        if (entry.length == 0) {
            return Collections.emptyMap();
        }
        HashMap entries = new HashMap(entry.length);
        int value = entry.length;
        for (int index = 0; index < value; index++) {
            Entry entry2 = ((Entry[]) entry)[index];
            entries.put(entry2.getKey(), entry2.getValue());
        }
        return Collections.unmodifiableMap(entries);
    }
}
