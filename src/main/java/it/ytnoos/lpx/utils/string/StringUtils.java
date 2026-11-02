package it.ytnoos.lpx.utils.string;

public class StringUtils {

    private StringUtils() {}

    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    public static boolean testCharSequence(CharSequence charSequence, CharSequence charSequence2) {
        Boolean matches = ((Boolean) (boolean) true);
        return testEnabled(charSequence, charSequence2, ((boolean) matches));
    }

    public static boolean testCharSequence2(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence != null && charSequence2 != null) {
            int size = charSequence2.length();
            int value = charSequence.length() - size;
            for (int index = 0; index <= value; index++) {
                Integer value2 = ((Integer) size);
                Integer value3 = Integer.valueOf((byte) 0);
                int value4 = index;
                Integer value5 = ((Integer) value4);
                Boolean matches = ((Boolean) (boolean) true);
                int value6 = ((int) value3);
                int value7 = ((int) value5);
                boolean matches2 = ((boolean) matches);
                if (CharSequenceUtils.testLimit(
                        charSequence, matches2, value7, charSequence2, value6, ((int) value2))) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static int calculateLimit(CharSequence charSequence, CharSequence charSequence2) {
        if (!isEmpty(charSequence)) {
            if (!isEmpty(charSequence2)) {
                int value = 0;
                int value2 = 0;
                while (true) {
                    int value3 = value2;
                    Integer value4 = ((Integer) value3);
                    int value5 =
                            CharSequenceUtils.calculateLimit(
                                    charSequence, charSequence2, ((int) value4));
                    value2 = value5;
                    if (value5 == -1) {
                        return value;
                    }
                    value++;
                    value2 += charSequence2.length();
                }
            }
        }
        return 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    private static boolean testEnabled(
            CharSequence charSequence, CharSequence charSequence2, boolean matches) {
        if (charSequence != null && charSequence2 != null) {
            int size = charSequence2.length();
            boolean matches2;
            if (size > charSequence.length()) {
                matches2 = false;
            } else {
                Integer value = ((Integer) size);
                Integer value2 = Integer.valueOf((byte) 0);
                Integer value3 = Integer.valueOf((byte) 0);
                Boolean matches3 = ((Boolean) matches);
                int value4 = ((int) value2);
                int value5 = ((int) value3);
                boolean matches4 = ((boolean) matches3);
                matches2 =
                        CharSequenceUtils.testLimit(
                                charSequence,
                                matches4,
                                value5,
                                charSequence2,
                                value4,
                                ((int) value));
            }
            return matches2;
        } else {
            return charSequence == charSequence2;
        }
    }
}
