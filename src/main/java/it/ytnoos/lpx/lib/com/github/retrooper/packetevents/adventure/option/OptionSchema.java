package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

@NonExtendable
public interface OptionSchema {

    static OptionSchema.Mutable globalSchema() {
        return OptionSchemaImpl.Instances.GLOBAL;
    }

    static OptionSchema.Mutable childSchema(OptionSchema optionSchema) {
        OptionSchemaImpl optionSchemaImpl;
        if (optionSchema instanceof OptionSchemaImpl.MutableImpl) {
            optionSchemaImpl =
                    (OptionSchemaImpl) ((OptionSchema.Mutable) optionSchema).frozenView();
        } else {
            optionSchemaImpl = (OptionSchemaImpl) optionSchema;
        }
        return new OptionSchemaImpl(Objects.requireNonNull(optionSchemaImpl, "impl"))
        .new MutableImpl();
    }

    static OptionSchema.Mutable emptySchema() {
        return new OptionSchemaImpl(null).new MutableImpl();
    }

    Set<Option<?>> knownOptions();

    boolean has(Option<?> option);

    OptionState.Builder stateBuilder();

    OptionState.VersionedBuilder versionedStateBuilder();

    OptionState emptyState();

    @NonExtendable
    interface Mutable extends OptionSchema {

        Option<String> stringOption(String text, @Nullable String text2);

        Option<Boolean> booleanOption(String text, boolean matches);

        Option<Integer> intOption(String text, int value);

        Option<Double> doubleOption(String text, double value);

        <E extends Enum<E>> Option<E> enumOption(String text, Class<E> classValue, @Nullable E e);

        OptionSchema frozenView();
    }
}
