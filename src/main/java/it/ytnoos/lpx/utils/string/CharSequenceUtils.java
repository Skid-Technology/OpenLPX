package it.ytnoos.lpx.utils.string;

public class CharSequenceUtils {

    public static final char[] LIMITS = new char[0];

    public static CharSequence subSequence(CharSequence charSequence, int value) {
        return charSequence == null ? null : charSequence.subSequence(value, charSequence.length());
    }

    static int indexOf(CharSequence charSequence, int value, int value2) {
        int value3 = value2;
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(value, value3);
        }
        int size = charSequence.length();
        if (value3 < 0) {
            value3 = 0;
        }
        if (value < 65536) {
            for (int value4 = value3; value4 < size; value4++) {
                if (charSequence.charAt(value4) == value) {
                    return value4;
                }
            }
            return -1;
        } else {
            if (value <= 1114111) {
                char[] values = Character.toChars(value);
                for (int value5 = value3; value5 < size - 1; value5++) {
                    char value6 = charSequence.charAt(value5);
                    char value7 = charSequence.charAt(value5 + 1);
                    if (value6 == values[0] && value7 == values[1]) {
                        return value5;
                    }
                }
            }
            return -1;
        }
    }

    static int calculateLimit(CharSequence charSequence, CharSequence charSequence2, int value) {
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(charSequence2.toString(), value);
        } else if (charSequence instanceof StringBuilder) {
            return ((StringBuilder) charSequence).indexOf(charSequence2.toString(), value);
        } else {
            return charSequence instanceof StringBuffer
                    ? ((StringBuffer) charSequence).indexOf(charSequence2.toString(), value)
                    : charSequence.toString().indexOf(charSequence2.toString(), value);
        }
    }

    static int calculateLimit(CharSequence charSequence, int value, int value2) {
        int value3 = value2;
        if (charSequence instanceof String) {
            return ((String) charSequence).lastIndexOf(value, value3);
        }
        int size = charSequence.length();
        if (value3 < 0) {
            return -1;
        }
        if (value3 >= size) {
            value3 = size - 1;
        }
        if (value < 65536) {
            for (int value4 = value3; value4 >= 0; value4--) {
                if (charSequence.charAt(value4) == value) {
                    return value4;
                }
            }
            return -1;
        } else {
            if (value <= 1114111) {
                char[] values = Character.toChars(value);
                if (value3 == size - 1) {
                    return -1;
                }
                for (int value5 = value3; value5 >= 0; value5--) {
                    char value6 = charSequence.charAt(value5);
                    char value7 = charSequence.charAt(value5 + 1);
                    if (values[0] == value6 && values[1] == value7) {
                        return value5;
                    }
                }
            }
            return -1;
        }
    }

    static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2, int value) {
        int value2 = value;
        if (charSequence2 != null && charSequence != null) {
            if (charSequence2 instanceof String) {
                if (charSequence instanceof String) {
                    return ((String) charSequence).lastIndexOf((String) charSequence2, value2);
                }
                if (charSequence instanceof StringBuilder) {
                    return ((StringBuilder) charSequence)
                            .lastIndexOf((String) charSequence2, value2);
                }
                if (charSequence instanceof StringBuffer) {
                    return ((StringBuffer) charSequence)
                            .lastIndexOf((String) charSequence2, value2);
                }
            }
            int size = charSequence.length();
            int size2 = charSequence2.length();
            if (value2 > size) {
                value2 = size;
            }
            if (value2 >= 0 && size2 >= 0 && size2 <= size) {
                if (size2 == 0) {
                    return value2;
                }
                if (size2 <= 16) {
                    if (charSequence instanceof String) {
                        return ((String) charSequence)
                                .lastIndexOf(charSequence2.toString(), value2);
                    }
                    if (charSequence instanceof StringBuilder) {
                        return ((StringBuilder) charSequence)
                                .lastIndexOf(charSequence2.toString(), value2);
                    }
                    if (charSequence instanceof StringBuffer) {
                        return ((StringBuffer) charSequence)
                                .lastIndexOf(charSequence2.toString(), value2);
                    }
                }
                if (value2 + size2 > size) {
                    value2 = size - size2;
                }
                char value3 = charSequence2.charAt(0);
                int value4 = value2;
                do {
                    while (charSequence.charAt(value4) != value3) {
                        if (--value4 < 0) {
                            return -1;
                        }
                    }
                    int value5 = value4;
                    Integer value6 = ((Integer) value5);
                    Integer value7 = ((Integer) size2);
                    if (testLimit(charSequence, charSequence2, value7, value6)) {
                        return value4;
                    }
                } while (--value4 >= 0);
                return -1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private static boolean testLimit(
            CharSequence charSequence, CharSequence charSequence2, int value, int value2) {
        int value3 = 1;
        for (int value4 = value - 1; value3 <= value4; value4--) {
            char value5 = charSequence.charAt(value2 + value3);
            if (value5 != charSequence2.charAt(value3)
                    || charSequence.charAt(value2 + value4) != charSequence2.charAt(value4)) {
                return false;
            }
            value3++;
        }
        return true;
    }

    public static char[] createLimits(CharSequence charSequence) {
        int size = StringUtils.length(charSequence);
        if (size == 0) {
            return LIMITS;
        }
        if (charSequence instanceof String) {
            return ((String) charSequence).toCharArray();
        }
        char[] values = new char[size];
        for (int index = 0; index < size; index++) {
            values[index] = charSequence.charAt(index);
        }
        return values;
    }

    static boolean testLimit(
            CharSequence charSequence,
            boolean matches,
            int value,
            CharSequence charSequence2,
            int value2,
            int value3) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String) charSequence)
                    .regionMatches(matches, value, (String) charSequence2, value2, value3);
        }
        int value4 = value;
        int value5 = value2;
        int value6 = value3;
        int value7 = charSequence.length() - value;
        int value8 = charSequence2.length() - value2;
        if (value3 >= 0) {
            if (value7 >= value3 && value8 >= value3) {
                while (value6-- > 0) {
                    char value9 = charSequence.charAt(value4++);
                    char value10 = charSequence2.charAt(value5++);
                    if (value9 != value10) {
                        if (!matches) {
                            return false;
                        }
                        char value11 = Character.toUpperCase(value9);
                        char value12 = Character.toUpperCase(value10);
                        if (value11 != value12
                                && Character.toLowerCase(value11)
                                        != Character.toLowerCase(value12)) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
