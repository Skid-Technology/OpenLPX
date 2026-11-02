package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value.ValueSource;

import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;

final class OptionStateImpl implements OptionState {

    private final OptionSchema schema;

    private final IdentityHashMap<Option<?>, Object> values;

    OptionStateImpl(OptionSchema optionSchema, IdentityHashMap<Option<?>, Object> identityHashMap) {
        this.schema = optionSchema;
        this.values = new IdentityHashMap<>(identityHashMap);
    }

    @Override
    public OptionSchema schema() {
        return this.schema;
    }

    @Override
    public boolean has(Option<?> option) {
        return this.values.containsKey(Objects.requireNonNull(option, "flag"));
    }

    @Override
    @Nullable
    public <V> V value(Option<V> option) {
        V v;
        return (V)
                ((v =
                                        option.valueType()
                                                .type()
                                                .cast(
                                                        this.values.get(
                                                                Objects.requireNonNull(
                                                                        option, "flag"))))
                                == null
                        ? option.defaultValue()
                        : v);
    }

    @Override
    public boolean equals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (result != null && this.getClass() == result.getClass()) {
            OptionStateImpl optionStateImpl = (OptionStateImpl) result;
            return Objects.equals(this.values, optionStateImpl.values);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.values);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{values=" + this.values + 125;
    }

    static final class BuilderImpl implements OptionState.Builder {

        private final OptionSchema schema;

        private final IdentityHashMap<Option<?>, Object> values = new IdentityHashMap<>();

        BuilderImpl(OptionSchema optionSchema) {
            this.schema = optionSchema;
        }

        @Override
        public OptionState build() {
            return this.values.isEmpty()
                    ? this.schema.emptyState()
                    : new OptionStateImpl(this.schema, this.values);
        }

        @Override
        public <V> OptionState.Builder value(Option<V> option, @Nullable V v) {
            if (!this.schema.has(Objects.requireNonNull(option, "option"))) {
                throw new IllegalStateException(
                        "Option '" + option.id() + "' was not present in active schema");
            }
            if (v == null) {
                this.values.remove(option);
            } else {
                this.values.put(option, v);
            }
            return this;
        }

        private void putAll(Map<Option<?>, Object> entries) {
            for (Entry<
                            it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option
                                            .Option<
                                    ?>,
                            Object>
                    entry : entries.entrySet()) {
                if (!this.schema.has((Option<?>) entry.getKey())) {
                    throw new IllegalStateException(
                            "Option '"
                                    + ((Option) entry.getKey()).id()
                                    + "' was not present in active schema");
                }
                this.values.put((Option<?>) entry.getKey(), entry.getValue());
            }
        }

        @Override
        public OptionState.Builder values(OptionState optionState) {
            if (optionState instanceof OptionStateImpl) {
                this.putAll(((OptionStateImpl) optionState).values);
            } else {
                if (!(optionState instanceof OptionStateImpl.VersionedImpl)) {
                    throw new IllegalArgumentException(
                            "existing set "
                                    + optionState
                                    + " is of an unknown implementation type");
                }
                this.putAll(
                        ((OptionStateImpl) ((OptionStateImpl.VersionedImpl) optionState).filtered)
                                .values);
            }
            return this;
        }

        @Override
        public OptionState.Builder values(ValueSource valueSource) {
            for (it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.Option<?>
                    option2 : this.schema.knownOptions()) {
                Object result;
                if ((result = valueSource.value(option2)) != null) {
                    this.values.put(option2, result);
                }
            }
            return this;
        }
    }

    static final class VersionedBuilderImpl implements OptionState.VersionedBuilder {

        private final OptionSchema schema;

        private final Map<Integer, OptionStateImpl.BuilderImpl> builders = new TreeMap<>();

        VersionedBuilderImpl(OptionSchema optionSchema) {
            this.schema = optionSchema;
        }

        @Override
        public OptionState.Versioned build() {
            if (this.builders.isEmpty()) {
                return new OptionStateImpl.VersionedImpl(
                        this.schema, Collections.emptySortedMap(), 0, this.schema.emptyState());
            }
            TreeMap treeMap = new TreeMap();
            for (Entry<
                            Integer,
                            it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option
                                    .OptionStateImpl.BuilderImpl>
                    entry : this.builders.entrySet()) {
                treeMap.put(
                        (Integer) entry.getKey(),
                        ((OptionStateImpl.BuilderImpl) entry.getValue()).build());
            }
            return new OptionStateImpl.VersionedImpl(
                    this.schema,
                    treeMap,
                    (Integer) treeMap.lastKey(),
                    OptionStateImpl.VersionedImpl.flattened(
                            this.schema, treeMap, (Integer) treeMap.lastKey()));
        }

        @Override
        public OptionState.VersionedBuilder version(
                int value, Consumer<OptionState.Builder> consumer) {
            Consumer<
                            it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option
                                    .OptionState.Builder>
                    consumer2 = Objects.requireNonNull(consumer, "versionBuilder");
            Map<
                            Integer,
                            it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option
                                    .OptionStateImpl.BuilderImpl>
                    entries = this.builders;
            consumer2.accept(
                    entries.computeIfAbsent(
                            ((Integer) value),
                            result -> new OptionStateImpl.BuilderImpl(this.schema)));
            return this;
        }
    }

    static final class VersionedImpl implements OptionState.Versioned {

        private final OptionSchema schema;

        private final SortedMap<Integer, OptionState> sets;

        private final int targetVersion;

        private final OptionState filtered;

        VersionedImpl(
                OptionSchema optionSchema,
                SortedMap<Integer, OptionState> sortedMap,
                int targetVersion,
                OptionState optionState) {
            this.schema = optionSchema;
            this.sets = sortedMap;
            this.targetVersion = targetVersion;
            this.filtered = optionState;
        }

        @Override
        public OptionSchema schema() {
            return this.schema;
        }

        @Override
        public boolean has(Option<?> option) {
            return this.filtered.has(option);
        }

        @Override
        @Nullable
        public <V> V value(Option<V> option) {
            return this.filtered.value(option);
        }

        @Override
        public Map<Integer, OptionState> childStates() {
            return Collections.unmodifiableSortedMap(this.sets.headMap(this.targetVersion + 1));
        }

        @Override
        public OptionState.Versioned at(int value) {
            return new OptionStateImpl.VersionedImpl(
                    this.schema, this.sets, value, flattened(this.schema, this.sets, value));
        }

        public static OptionState flattened(
                OptionSchema optionSchema, SortedMap<Integer, OptionState> sortedMap, int value) {
            SortedMap sortedMap2 = sortedMap.headMap(value + 1);
            OptionState.Builder builder = optionSchema.stateBuilder();
            for (OptionState optionState :
                    (Iterable<OptionState>) ((Iterable) sortedMap2.values())) {
                builder.values(optionState);
            }
            return builder.build();
        }

        @Override
        public boolean equals(@Nullable Object result) {
            if (this == result) {
                return true;
            } else if (result != null && this.getClass() == result.getClass()) {
                OptionStateImpl.VersionedImpl versionedImpl =
                        (OptionStateImpl.VersionedImpl) result;
                return this.targetVersion == versionedImpl.targetVersion
                        && Objects.equals(this.schema, versionedImpl.schema)
                        && Objects.equals(this.sets, versionedImpl.sets)
                        && Objects.equals(this.filtered, versionedImpl.filtered);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.schema, this.sets, this.targetVersion, this.filtered);
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName()
                    + "{schema="
                    + this.schema
                    + ", sets="
                    + this.sets
                    + ", targetVersion="
                    + this.targetVersion
                    + ", filtered="
                    + this.filtered
                    + 125;
        }
    }
}
