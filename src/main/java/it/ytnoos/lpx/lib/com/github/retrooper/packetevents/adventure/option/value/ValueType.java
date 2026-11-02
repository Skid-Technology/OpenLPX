package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.value;

import java.util.Objects;

public interface ValueType<T> {

    static ValueType<String> stringType() {
        return ValueTypeImpl.Types.STRING;
    }

    static ValueType<Boolean> booleanType() {
        return ValueTypeImpl.Types.BOOLEAN;
    }

    static ValueType<Integer> integerType() {
        return ValueTypeImpl.Types.INT;
    }

    static ValueType<Double> doubleType() {
        return ValueTypeImpl.Types.DOUBLE;
    }

    static <E extends Enum<E>> ValueType<E> enumType(Class<E> classValue) {
        return new ValueTypeImpl.EnumType<>(Objects.requireNonNull(classValue, "enumClazz"));
    }

    Class<T> type();

    T parse(String text) throws IllegalArgumentException;
}
