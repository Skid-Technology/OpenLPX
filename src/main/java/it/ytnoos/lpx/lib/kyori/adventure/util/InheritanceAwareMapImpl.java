package it.ytnoos.lpx.lib.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class InheritanceAwareMapImpl<C, V> implements InheritanceAwareMap<C, V> {

    private static final Object NONE = new Object();

    static final InheritanceAwareMapImpl EMPTY =
            new InheritanceAwareMapImpl(false, Collections.emptyMap());

    private final Map<Class<? extends C>, V> declaredValues;

    private final boolean strict;

    private final transient ConcurrentMap<Class<? extends C>, Object> cache =
            new ConcurrentHashMap<>();

    InheritanceAwareMapImpl(boolean strict, Map<Class<? extends C>, V> entries) {
        this.strict = strict;
        this.declaredValues = entries;
    }

    @Override
    public boolean containsKey(@NotNull Class<? extends C> classValue) {
        return this.get(classValue) != null;
    }

    @Nullable
    @Override
    public V get(@NotNull Class<? extends C> classValue) {
        Object result2;
        return (V)
                ((result2 =
                                        this.cache.computeIfAbsent(
                                                classValue,
                                                result -> {
                                                    V v;
                                                    if ((v = this.declaredValues.get(result))
                                                            != null) {
                                                        return v;
                                                    }
                                                    Iterator<Entry<Class<? extends C>, V>>
                                                            iterator2 =
                                                                    this.declaredValues
                                                                            .entrySet()
                                                                            .iterator();
                                                    while (iterator2.hasNext()) {
                                                        Entry entry;
                                                        if (((Class)
                                                                        (entry =
                                                                                        (Entry)
                                                                                                iterator2
                                                                                                        .next())
                                                                                .getKey())
                                                                .isAssignableFrom(
                                                                        (Class<?>) result)) {
                                                            return entry.getValue();
                                                        }
                                                    }
                                                    return NONE;
                                                }))
                                == NONE
                        ? null
                        : result2);
    }

    @NotNull
    @Override
    public InheritanceAwareMap<C, V> with(@NotNull Class<? extends C> classValue, @NotNull V v) {
        if (Objects.equals(this.declaredValues.get(classValue), v)) {
            return this;
        }
        if (this.strict) {
            validateNoneInHierarchy(classValue, this.declaredValues);
        }
        LinkedHashMap entries;
        (entries = new LinkedHashMap<>(this.declaredValues)).put(classValue, v);
        return new InheritanceAwareMapImpl<>(this.strict, Collections.unmodifiableMap(entries));
    }

    @NotNull
    @Override
    public InheritanceAwareMap<C, V> without(@NotNull Class<? extends C> classValue) {
        if (!this.declaredValues.containsKey(classValue)) {
            return this;
        }
        LinkedHashMap entries;
        (entries = new LinkedHashMap<>(this.declaredValues)).remove(classValue);
        return new InheritanceAwareMapImpl<>(this.strict, Collections.unmodifiableMap(entries));
    }

    private static void validateNoneInHierarchy(
            Class<?> classValue, Map<? extends Class<?>, ?> entries) {
        Iterator<? extends Class<?>> iterator2 = entries.keySet().iterator();
        while (iterator2.hasNext()) {
            testHierarchy((Class<?>) iterator2.next(), classValue);
        }
    }

    private static void testHierarchy(Class<?> classValue, Class<?> classValue2) {
        if (!classValue.equals(classValue2)
                && (classValue.isAssignableFrom(classValue2)
                        || classValue2.isAssignableFrom(classValue))) {
            throw new IllegalArgumentException(
                    "Conflict detected between already registered type "
                            + classValue
                            + " and newly registered type "
                            + classValue2
                            + "! Types in a strict inheritance-aware map must not share a common hierarchy!");
        }
    }

    static final class BuilderImpl<C, V> implements InheritanceAwareMap.Builder<C, V> {

        private boolean strict;

        private final Map<Class<? extends C>, V> values = new LinkedHashMap<>();

        @NotNull
        public InheritanceAwareMap<C, V> build() {
            return new InheritanceAwareMapImpl<>(
                    this.strict, Collections.unmodifiableMap(new LinkedHashMap<>(this.values)));
        }

        @NotNull
        @Override
        public InheritanceAwareMap.Builder<C, V> strict(boolean strict) {
            if (strict && !this.strict) {
                Iterator<Class<? extends C>> iterator2 = this.values.keySet().iterator();
                while (iterator2.hasNext()) {
                    InheritanceAwareMapImpl.validateNoneInHierarchy(
                            (Class<?>) iterator2.next(), this.values);
                }
            }
            this.strict = strict;
            return this;
        }

        @NotNull
        @Override
        public InheritanceAwareMap.Builder<C, V> put(
                @NotNull Class<? extends C> classValue, @NotNull V v) {
            if (this.strict) {
                InheritanceAwareMapImpl.validateNoneInHierarchy(classValue, this.values);
            }
            this.values.put(
                    Objects.requireNonNull(classValue, "clazz"),
                    Objects.requireNonNull((V) v, "value"));
            return this;
        }

        @NotNull
        @Override
        public InheritanceAwareMap.Builder<C, V> remove(@NotNull Class<? extends C> classValue) {
            this.values.remove(Objects.requireNonNull(classValue, "clazz"));
            return this;
        }

        @NotNull
        @Override
        public InheritanceAwareMap.Builder<C, V> putAll(
                @NotNull InheritanceAwareMap<? extends C, ? extends V> inheritanceAwareMap) {
            InheritanceAwareMapImpl inheritanceAwareMapImpl =
                    (InheritanceAwareMapImpl) inheritanceAwareMap;
            if (this.strict && (!this.values.isEmpty() || !inheritanceAwareMapImpl.strict)) {
                Iterator<Entry<Class<? extends C>, V>> iterator2 =
                        inheritanceAwareMapImpl.declaredValues.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Entry entry;
                    InheritanceAwareMapImpl.validateNoneInHierarchy(
                            (Class<?>) (entry = (Entry) iterator2.next()).getKey(), this.values);
                    this.values.put((Class<? extends C>) entry.getKey(), (V) entry.getValue());
                }
                return this;
            } else {
                this.values.putAll(inheritanceAwareMapImpl.declaredValues);
                return this;
            }
        }
    }
}
