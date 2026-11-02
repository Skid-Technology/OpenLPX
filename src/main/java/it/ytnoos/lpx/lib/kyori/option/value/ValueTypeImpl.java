package it.ytnoos.lpx.lib.kyori.option.value;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

abstract class ValueTypeImpl<T> implements ValueType<T> {

    private final Class<T> type;

    ValueTypeImpl(Class<T> classValue) {
        this.type = classValue;
    }

    @Override
    public Class<T> type() {
        return this.type;
    }

    static IllegalArgumentException doNotKnowHowToTurn(
            String text, Class<?> classValue, @Nullable String text2) {
        throw new IllegalArgumentException(
                "Do not know how to turn value '"
                        + text
                        + "' into a "
                        + classValue.getName()
                        + (text2 == null ? "" : ": " + text2));
    }

    static final class EnumType<E extends Enum<E>> extends ValueTypeImpl<E> {

        private final Map<String, E> values = new HashMap<>();

        EnumType(Class<E> classValue) {
            super(classValue);
            Enum[] enums;
            int value = (enums = (Enum[]) classValue.getEnumConstants()).length;
            for (int index = 0; index < value; index++) {
                Enum enumValue = enums[index];
                this.values.put(enumValue.name().toLowerCase(Locale.ROOT), (E) enumValue);
            }
        }

        public E parse(String text) throws IllegalArgumentException {
            Enum enumValue;
            if ((enumValue = this.values.get(text.toLowerCase(Locale.ROOT))) == null) {
                throw doNotKnowHowToTurn(text, this.type(), null);
            } else {
                return (E) enumValue;
            }
        }
    }

    static final class Types {

        static ValueType<String> STRING =
                new ValueTypeImpl<String>(String.class) {

                    public String parse(String text) throws IllegalArgumentException {
                        return text;
                    }
                };

        static ValueType<Boolean> BOOLEAN =
                new ValueTypeImpl<Boolean>(Boolean.class) {

                    public Boolean parse(String text) throws IllegalArgumentException {
                        if (text.equalsIgnoreCase("true")) {
                            return Boolean.TRUE;
                        } else if (text.equalsIgnoreCase("false")) {
                            return Boolean.FALSE;
                        } else {
                            throw doNotKnowHowToTurn(text, Boolean.class, null);
                        }
                    }
                };

        static ValueType<Integer> INT =
                new ValueTypeImpl<Integer>(Integer.class) {

                    public Integer parse(String text) throws IllegalArgumentException {
                        try {
                            return Integer.decode(text);
                        } catch (NumberFormatException numberFormatException) {
                            throw doNotKnowHowToTurn(
                                    text, Integer.class, numberFormatException.getMessage());
                        }
                    }
                };

        static ValueType<Double> DOUBLE =
                new ValueTypeImpl<Double>(Double.class) {

                    public Double parse(String text) throws IllegalArgumentException {
                        try {
                            return Double.parseDouble(text);
                        } catch (NumberFormatException numberFormatException) {
                            throw doNotKnowHowToTurn(
                                    text, Double.class, numberFormatException.getMessage());
                        }
                    }
                };

        private Types() {}
    }
}
