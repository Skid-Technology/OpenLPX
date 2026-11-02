package it.ytnoos.lpx.lib.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils {

    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean matches) {
        return format(date, matches, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean matches, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar;
        (gregorianCalendar = new GregorianCalendar(timeZone, Locale.US)).setTime(date);
        int value =
                "yyyy-MM-ddThh:mm:ss".length()
                        + (matches ? ".sss".length() : 0)
                        + (timeZone.getRawOffset() == 0 ? "Z".length() : "+hh:mm".length());
        StringBuilder stringBuilder;
        padInt(stringBuilder = new StringBuilder(value), gregorianCalendar.get(1), "yyyy".length());
        stringBuilder.append('-');
        padInt(stringBuilder, gregorianCalendar.get(2) + 1, "MM".length());
        stringBuilder.append('-');
        padInt(stringBuilder, gregorianCalendar.get(5), "dd".length());
        stringBuilder.append('T');
        padInt(stringBuilder, gregorianCalendar.get(11), "hh".length());
        stringBuilder.append(':');
        padInt(stringBuilder, gregorianCalendar.get(12), "mm".length());
        stringBuilder.append(':');
        padInt(stringBuilder, gregorianCalendar.get(13), "ss".length());
        if (matches) {
            stringBuilder.append('.');
            padInt(stringBuilder, gregorianCalendar.get(14), "sss".length());
        }
        int value2;
        if ((value2 = timeZone.getOffset(gregorianCalendar.getTimeInMillis())) != 0) {
            int value3 = Math.abs(value2 / 60000 / 60);
            int value4 = Math.abs(value2 / 60000 % 60);
            stringBuilder.append((char) (value2 < 0 ? '-' : '+'));
            padInt(stringBuilder, value3, "hh".length());
            stringBuilder.append(':');
            padInt(stringBuilder, value4, "mm".length());
        } else {
            stringBuilder.append('Z');
        }
        return stringBuilder.toString();
    }

    public static Date parse(String text, ParsePosition parsePosition) throws ParseException {
        RuntimeException runtimeException;
        try {
            int index = parsePosition.getIndex();
            int value = index + 4;
            int value2 = parseInt(text, index, value);
            if (checkOffset(text, value, '-')) {
                value++;
            }
            index = value + 2;
            int value3 = parseInt(text, value, index);
            if (checkOffset(text, index, '-')) {
                index++;
            }
            int value4 = index + 2;
            int value5 = parseInt(text, index, value4);
            int value6 = 0;
            int value7 = 0;
            int value8 = 0;
            int value9 = 0;
            boolean matches;
            if (!(matches = checkOffset(text, value4, 'T')) && text.length() <= value4) {
                GregorianCalendar gregorianCalendar =
                        new GregorianCalendar(value2, value3 - 1, value5);
                parsePosition.setIndex(value4);
                return gregorianCalendar.getTime();
            }
            if (matches) {
                int value10 = ++value4;
                index = value4 + 2;
                value6 = parseInt(text, value10, index);
                if (checkOffset(text, index, ':')) {
                    index++;
                }
                value4 = index + 2;
                value7 = parseInt(text, index, value4);
                if (checkOffset(text, value4, ':')) {
                    value4++;
                }
                char value11;
                if (text.length() > value4
                        && (value11 = text.charAt(value4)) != 'Z'
                        && value11 != '+'
                        && value11 != '-') {
                    value10 = value4;
                    value4 += 2;
                    if ((value8 = parseInt(text, value10, value4)) > 59 && value8 < 63) {
                        value8 = 59;
                    }
                    if (checkOffset(text, value4, '.')) {
                        int value12;
                        int value13 =
                                Math.min(value12 = indexOfNonDigit(text, ++value4 + 1), value4 + 3);
                        int value14 = parseInt(text, value4, value13);
                        switch (value13 - value4) {
                            case 1:
                                value9 = value14 * 100;
                                break;
                            case 2:
                                value9 = value14 * 10;
                                break;
                            default:
                                value9 = value14;
                        }
                        value4 = value12;
                    }
                }
            }
            if (text.length() <= value4) {
                throw new IllegalArgumentException("No time zone indicator");
            }
            TimeZone timeZone;
            char value15;
            if ((value15 = text.charAt(value4)) == 'Z') {
                timeZone = TIMEZONE_UTC;
                value4++;
            } else {
                if (value15 != '+' && value15 != '-') {
                    throw new IndexOutOfBoundsException(
                            "Invalid time zone indicator '" + value15 + "'");
                }
                String text2;
                text2 = (text2 = text.substring(value4)).length() >= 5 ? text2 : text2 + "00";
                value4 += text2.length();
                if (!"+0000".equals(text2) && !"+00:00".equals(text2)) {
                    String text3;
                    String text4;
                    if (!(text3 = (timeZone = TimeZone.getTimeZone(text4 = "GMT" + text2)).getID())
                                    .equals(text4)
                            && !text3.replace(":", "").equals(text4)) {
                        throw new IndexOutOfBoundsException(
                                "Mismatching time zone indicator: "
                                        + text4
                                        + " given, resolves to "
                                        + timeZone.getID());
                    }
                } else {
                    timeZone = TIMEZONE_UTC;
                }
            }
            GregorianCalendar gregorianCalendar2;
            (gregorianCalendar2 = new GregorianCalendar(timeZone)).setLenient(false);
            gregorianCalendar2.set(1, value2);
            gregorianCalendar2.set(2, value3 - 1);
            gregorianCalendar2.set(5, value5);
            gregorianCalendar2.set(11, value6);
            gregorianCalendar2.set(12, value7);
            gregorianCalendar2.set(13, value8);
            gregorianCalendar2.set(14, value9);
            parsePosition.setIndex(value4);
            return gregorianCalendar2.getTime();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            runtimeException = indexOutOfBoundsException;
        } catch (NumberFormatException numberFormatException) {
            runtimeException = numberFormatException;
        } catch (IllegalArgumentException illegalArgumentException) {
            runtimeException = illegalArgumentException;
        }
        String text5 = text == null ? null : '"' + text + "'";
        String text6;
        if ((text6 = runtimeException.getMessage()) == null || text6.isEmpty()) {
            text6 = "(" + runtimeException.getClass().getName() + ")";
        }
        ParseException parseException;
        (parseException =
                        new ParseException(
                                "Failed to parse date [" + text5 + "]: " + text6,
                                parsePosition.getIndex()))
                .initCause(runtimeException);
        throw parseException;
    }

    private static boolean checkOffset(String text, int value, char value2) {
        return value < text.length() && text.charAt(value) == value2;
    }

    private static int parseInt(String text, int value, int value2) throws NumberFormatException {
        if (value >= 0 && value2 <= text.length() && value <= value2) {
            int value3 = value;
            int value4 = 0;
            if (value3 < value2) {
                int value5;
                if ((value5 = Character.digit(text.charAt(value3++), 10)) < 0) {
                    throw new NumberFormatException(
                            "Invalid number: " + text.substring(value, value2));
                }
                value4 = -value5;
            }
            while (value3 < value2) {
                int value6;
                if ((value6 = Character.digit(text.charAt(value3++), 10)) < 0) {
                    throw new NumberFormatException(
                            "Invalid number: " + text.substring(value, value2));
                }
                value4 = value4 * 10 - value6;
            }
            return -value4;
        } else {
            throw new NumberFormatException(text);
        }
    }

    private static void padInt(StringBuilder stringBuilder, int value, int value2) {
        String text = Integer.toString(value);
        for (int value3 = value2 - text.length(); value3 > 0; value3--) {
            stringBuilder.append('0');
        }
        stringBuilder.append(text);
    }

    private static int indexOfNonDigit(String text, int value) {
        for (int value2 = value; value2 < text.length(); value2++) {
            char value3;
            if ((value3 = text.charAt(value2)) < '0' || value3 > '9') {
                return value2;
            }
        }
        return text.length();
    }
}
