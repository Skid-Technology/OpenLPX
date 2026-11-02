package it.ytnoos.lpx.lib.gson;

import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy {
    IDENTITY {

        @Override
        public String translateName(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE {

        @Override
        public String translateName(Field field) {
            return upperCaseFirstLetter(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {

        @Override
        public String translateName(Field field) {
            return upperCaseFirstLetter(separateCamelCase(field.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {

        @Override
        public String translateName(Field field) {
            return separateCamelCase(field.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    },
    LOWER_CASE_WITH_DASHES {

        @Override
        public String translateName(Field field) {
            return separateCamelCase(field.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };

    FieldNamingPolicy() {}

    static String separateCamelCase(String text, String text2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < text.length(); index++) {
            char value;
            if (Character.isUpperCase(value = text.charAt(index)) && stringBuilder.length() != 0) {
                stringBuilder.append(text2);
            }
            stringBuilder.append(value);
        }
        return stringBuilder.toString();
    }

    static String upperCaseFirstLetter(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        int value = 0;
        char value2;
        for (value2 = text.charAt(value);
                value < text.length() - 1 && !Character.isLetter(value2);
                value2 = text.charAt(++value)) {
            stringBuilder.append(value2);
        }
        if (value == text.length()) {
            return stringBuilder.toString();
        } else if (!Character.isUpperCase(value2)) {
            String text2 = modifyString(Character.toUpperCase(value2), text, ++value);
            return stringBuilder.append(text2).toString();
        } else {
            return text;
        }
    }

    private static String modifyString(char value, String text, int value2) {
        return value2 < text.length() ? value + text.substring(value2) : String.valueOf(value);
    }
}
