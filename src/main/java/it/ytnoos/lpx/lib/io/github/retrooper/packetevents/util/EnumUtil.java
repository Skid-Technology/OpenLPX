package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

public class EnumUtil {

    public static Enum<?> valueOf(Class<? extends Enum<?>> classValue, String text) {
        if (classValue == null) {
            return null;
        }
        Enum[] enums;
        int value = (enums = (Enum[]) classValue.getEnumConstants()).length;
        for (int index = 0; index < value; index++) {
            Enum enumValue;
            if ((enumValue = enums[index]).name().equals(text)) {
                return enumValue;
            }
        }
        return null;
    }

    public static Enum<?> valueByIndex(Class<? extends Enum<?>> classValue, int value) {
        return classValue == null ? null : ((Enum[]) classValue.getEnumConstants())[value];
    }
}
