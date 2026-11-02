package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value.ValueSource;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

@NonExtendable
public interface OptionState {

    @Deprecated
    static OptionState emptyOptionState() {
        return OptionSchema.globalSchema().emptyState();
    }

    @Deprecated
    static OptionState.Builder optionState() {
        return OptionSchema.globalSchema().stateBuilder();
    }

    @Deprecated
    static OptionState.VersionedBuilder versionedOptionState() {
        return OptionSchema.globalSchema().versionedStateBuilder();
    }

    OptionSchema schema();

    boolean has(Option<?> option);

    @Nullable <V> V value(Option<V> option);

    @NonExtendable
    interface Builder {

        <V> OptionState.Builder value(Option<V> option, @Nullable V v);

        OptionState.Builder values(OptionState optionState);

        OptionState.Builder values(ValueSource valueSource);

        OptionState build();
    }

    @NonExtendable
    interface Versioned extends OptionState {

        Map<Integer, OptionState> childStates();

        OptionState.Versioned at(int value);
    }

    @NonExtendable
    interface VersionedBuilder {

        OptionState.VersionedBuilder version(int value, Consumer<OptionState.Builder> consumer);

        OptionState.Versioned build();
    }
}
