package it.ytnoos.lpx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftVersionParser {

    private static final Pattern PATTERN = Pattern.compile("\\d+(\\.\\d+)?");

    private MinecraftVersionParser() {}

    public static int calculateLimit(String text) {
        Matcher matcher2 = PATTERN.matcher(text);
        int value = -1;
        while (matcher2.find()) {
            try {
                value = Integer.parseInt(matcher2.group());
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return value;
    }
}
