package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value.ValueType;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jspecify.annotations.Nullable;

@NonExtendable
public interface Option<V> {

    @Deprecated
    static Option<Boolean> booleanOption(String text, boolean matches) {
        return OptionSchema.globalSchema().booleanOption(text, matches);
    }

    @Deprecated
    static <E extends Enum<E>> Option<E> enumOption(String text, Class<E> classValue, E e) {
        return OptionSchema.globalSchema().enumOption(text, classValue, (E) e);
    }

    String id();

    @Deprecated
    default Class<V> type() {
        return this.valueType().type();
    }

    ValueType<V> valueType();

    @Nullable V defaultValue();
}
