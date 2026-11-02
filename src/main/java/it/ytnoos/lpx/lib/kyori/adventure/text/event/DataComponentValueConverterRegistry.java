package it.ytnoos.lpx.lib.kyori.adventure.text.event;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class DataComponentValueConverterRegistry {

    private static final Set<DataComponentValueConverterRegistry.Provider> PROVIDERS =
            Services.services(DataComponentValueConverterRegistry.Provider.class);

    private DataComponentValueConverterRegistry() {}

    public static Set<Key> knownProviders() {
        return Collections.unmodifiableSet(
                PROVIDERS.stream()
                        .map(DataComponentValueConverterRegistry.Provider::id)
                        .collect(Collectors.toSet()));
    }

    @NotNull
    public static <O extends DataComponentValue> O convert(
            @NotNull Class<O> classValue,
            @NotNull Key key,
            @NotNull DataComponentValue dataComponentValue) {
        if (classValue.isInstance(dataComponentValue)) {
            return (O) classValue.cast(dataComponentValue);
        }
        DataComponentValueConverterRegistry.RegisteredConversion registeredConversion;
        if ((registeredConversion =
                        DataComponentValueConverterRegistry.ConversionCache.converter(
                                (Class<? extends DataComponentValue>) dataComponentValue.getClass(),
                                classValue))
                == null) {
            throw new IllegalArgumentException(
                    "There is no data holder converter registered to convert from a "
                            + dataComponentValue.getClass()
                            + " instance to a "
                            + classValue
                            + " (on field "
                            + key
                            + ")");
        }
        try {
            return (O)
                    ((DataComponentValueConverterRegistry.Conversion<DataComponentValue, ?>)
                                    registeredConversion.conversion)
                            .convert(key, dataComponentValue);
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Failed to convert data component value of type "
                            + dataComponentValue.getClass()
                            + " to type "
                            + classValue
                            + " due to an error in a converter provided by "
                            + registeredConversion.provider.asString()
                            + "!",
                    exception);
        }
    }

    @NonExtendable
    public interface Conversion<I, O> extends Examinable {

        @NotNull
        static <I1, O1> DataComponentValueConverterRegistry.Conversion<I1, O1> convert(
                @NotNull Class<I1> classValue,
                @NotNull Class<O1> classValue2,
                @NotNull BiFunction<Key, I1, O1> biFunction) {
            return new DataComponentValueConversionImpl(
                    Objects.requireNonNull(classValue, "src"),
                    Objects.requireNonNull(classValue2, "dst"),
                    Objects.requireNonNull(biFunction, "op"));
        }

        @Contract(pure = true)
        @NotNull
        Class<I> source();

        @Contract(pure = true)
        @NotNull
        Class<O> destination();

        @NotNull
        O convert(@NotNull Key key, @NotNull I i);
    }

    static final class ConversionCache {

        private static final ConcurrentMap<
                        Class<?>,
                        ConcurrentMap<
                                Class<?>, DataComponentValueConverterRegistry.RegisteredConversion>>
                CACHE = new ConcurrentHashMap<>();

        private static final Map<
                        Class<?>, Set<DataComponentValueConverterRegistry.RegisteredConversion>>
                CONVERSIONS = collectConversions();

        private static Map<Class<?>, Set<DataComponentValueConverterRegistry.RegisteredConversion>>
                collectConversions() {
            ConcurrentHashMap entries = new ConcurrentHashMap();
            Iterator<
                            it.ytnoos.lpx.lib.kyori.adventure.text.event
                                    .DataComponentValueConverterRegistry.Provider>
                    iterator2 = DataComponentValueConverterRegistry.PROVIDERS.iterator();
            while (iterator2.hasNext()) {
                DataComponentValueConverterRegistry.Provider provider;
                Key key =
                        (provider = (DataComponentValueConverterRegistry.Provider) iterator2.next())
                                .id();
                DataComponentValueConverterRegistry.Provider provider2 = provider;
                Key key2 =
                        Objects.requireNonNull(
                                key, () -> "ID of provider " + provider2 + " is null");
                for (DataComponentValueConverterRegistry.Conversion conversion :
                        provider.conversions()) {
                    ((Set)
                                    entries.computeIfAbsent(
                                            conversion.source(),
                                            result -> ConcurrentHashMap.newKeySet()))
                            .add(
                                    new DataComponentValueConverterRegistry.RegisteredConversion(
                                            key2, conversion));
                }
            }
            for (Entry entry : ((Iterable<Entry>) ((Iterable<?>) entries.entrySet()))) {
                entry.setValue(Collections.unmodifiableSet((Set) entry.getValue()));
            }
            return new ConcurrentHashMap<>(entries);
        }

        static DataComponentValueConverterRegistry.RegisteredConversion compute(
                Class<?> classValue, Class<?> classValue2) {
            ArrayDeque values;
            (values = new ArrayDeque()).add(classValue);
            Class classValue3;
            while ((classValue3 = (Class) values.poll()) != null) {
                Set<
                                it.ytnoos.lpx.lib.kyori.adventure.text.event
                                        .DataComponentValueConverterRegistry.RegisteredConversion>
                        values2;
                if ((values2 = CONVERSIONS.get(classValue3)) != null) {
                    DataComponentValueConverterRegistry.RegisteredConversion registeredConversion =
                            null;
                    Iterator<
                                    it.ytnoos.lpx.lib.kyori.adventure.text.event
                                            .DataComponentValueConverterRegistry
                                            .RegisteredConversion>
                            iterator2 = values2.iterator();
                    while (iterator2.hasNext()) {
                        DataComponentValueConverterRegistry.RegisteredConversion
                                registeredConversion2;
                        Class<?> classValue4 =
                                (registeredConversion2 =
                                                (DataComponentValueConverterRegistry
                                                                .RegisteredConversion)
                                                        iterator2.next())
                                        .conversion.destination();
                        if (classValue2.equals(classValue4)) {
                            return registeredConversion2;
                        }
                        if (classValue2.isAssignableFrom(classValue4)
                                && (registeredConversion == null
                                        || classValue4.isAssignableFrom(
                                                registeredConversion.conversion.destination()))) {
                            registeredConversion = registeredConversion2;
                        }
                    }
                    if (registeredConversion != null) {
                        return registeredConversion;
                    }
                }
                addSupertypes(classValue3, values);
            }
            return DataComponentValueConverterRegistry.RegisteredConversion.NONE;
        }

        private static void addSupertypes(Class<?> classValue, Deque<Class<?>> values) {
            if (classValue.getSuperclass() != null) {
                values.add(classValue.getSuperclass());
            }
            values.addAll(Arrays.asList(classValue.getInterfaces()));
        }

        @Nullable
        static DataComponentValueConverterRegistry.RegisteredConversion converter(
                Class<? extends DataComponentValue> classValue,
                Class<? extends DataComponentValue> classValue2) {
            ConcurrentMap concurrentMap =
                    CACHE.computeIfAbsent(classValue, result -> new ConcurrentHashMap<>());
            DataComponentValueConverterRegistry.RegisteredConversion registeredConversion;
            return (registeredConversion =
                                    ((RegisteredConversion)
                                            concurrentMap.computeIfAbsent(
                                                    classValue2,
                                                    result2 ->
                                                            compute(
                                                                    ((Class) classValue),
                                                                    ((Class) classValue2)))))
                            == DataComponentValueConverterRegistry.RegisteredConversion.NONE
                    ? null
                    : registeredConversion;
        }
    }

    public interface Provider {

        @NotNull
        Key id();

        @NotNull
        Iterable<DataComponentValueConverterRegistry.Conversion<?, ?>> conversions();
    }

    static final class RegisteredConversion {

        static final DataComponentValueConverterRegistry.RegisteredConversion NONE =
                new DataComponentValueConverterRegistry.RegisteredConversion(null, null);

        final Key provider;

        final DataComponentValueConverterRegistry.Conversion<?, ?> conversion;

        RegisteredConversion(
                Key key, DataComponentValueConverterRegistry.Conversion<?, ?> conversion2) {
            this.provider = key;
            this.conversion = conversion2;
        }
    }
}
