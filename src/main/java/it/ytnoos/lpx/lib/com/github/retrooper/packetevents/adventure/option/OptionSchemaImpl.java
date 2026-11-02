package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value.ValueType;

import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class OptionSchemaImpl implements OptionSchema {

    final OptionState emptyState;

    final ConcurrentMap<String, Option<?>> options = new ConcurrentHashMap<>();

    OptionSchemaImpl(@Nullable OptionSchemaImpl optionSchemaImpl) {
        if (optionSchemaImpl != null) {
            this.options.putAll(optionSchemaImpl.options);
        }
        this.emptyState = new OptionStateImpl(this, new IdentityHashMap<>());
    }

    @Override
    public Set<Option<?>> knownOptions() {
        return Collections.unmodifiableSet(new HashSet<>(this.options.values()));
    }

    @Override
    public boolean has(Option<?> option) {
        Option option2;
        return (option2 = this.options.get(option.id())) != null && option2.equals(option);
    }

    @Override
    public OptionState.Builder stateBuilder() {
        return new OptionStateImpl.BuilderImpl(this);
    }

    @Override
    public OptionState.VersionedBuilder versionedStateBuilder() {
        return new OptionStateImpl.VersionedBuilderImpl(this);
    }

    @Override
    public OptionState emptyState() {
        return this.emptyState;
    }

    @Override
    public String toString() {
        return "OptionSchemaImpl{options=" + this.options + 125;
    }

    static final class Instances {

        static OptionSchemaImpl.MutableImpl GLOBAL = new OptionSchemaImpl(null).new MutableImpl();
    }

    final class MutableImpl implements OptionSchema.Mutable {

        <T> Option<T> register(String text, ValueType<T> valueType, @Nullable T t) {
            OptionImpl optionImpl =
                    new OptionImpl<>(
                            Objects.requireNonNull(text, "id"),
                            Objects.requireNonNull(valueType, "type"),
                            t);
            if (OptionSchemaImpl.this.options.putIfAbsent(text, optionImpl) != null) {
                throw new IllegalStateException(
                        "Key "
                                + text
                                + " has already been used. Option keys must be unique within a schema.");
            } else {
                return optionImpl;
            }
        }

        @Override
        public Option<String> stringOption(String text, @Nullable String text2) {
            return this.register(text, ValueType.stringType(), text2);
        }

        @Override
        public Option<Boolean> booleanOption(String text, boolean matches) {
            return this.register(text, ValueType.booleanType(), matches);
        }

        @Override
        public Option<Integer> intOption(String text, int value) {
            return this.register(text, ValueType.integerType(), value);
        }

        @Override
        public Option<Double> doubleOption(String text, double value) {
            return this.register(text, ValueType.doubleType(), value);
        }

        @Override
        public <E extends Enum<E>> Option<E> enumOption(
                String text, Class<E> classValue, @Nullable E e) {
            return this.register(text, ValueType.enumType(classValue), (E) e);
        }

        @Override
        public OptionSchema frozenView() {
            return OptionSchemaImpl.this;
        }

        @Override
        public Set<Option<?>> knownOptions() {
            return OptionSchemaImpl.this.knownOptions();
        }

        @Override
        public boolean has(Option<?> option) {
            return OptionSchemaImpl.this.has(option);
        }

        @Override
        public OptionState.Builder stateBuilder() {
            return OptionSchemaImpl.this.stateBuilder();
        }

        @Override
        public OptionState.VersionedBuilder versionedStateBuilder() {
            return OptionSchemaImpl.this.versionedStateBuilder();
        }

        @Override
        public OptionState emptyState() {
            return OptionSchemaImpl.this.emptyState();
        }

        @Override
        public String toString() {
            return "MutableImpl{schema=" + OptionSchemaImpl.this + "}";
        }
    }
}
