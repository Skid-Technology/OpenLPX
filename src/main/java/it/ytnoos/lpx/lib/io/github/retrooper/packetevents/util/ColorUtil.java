package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;

public class ColorUtil {

    public static String toString(NamedTextColor namedTextColor) {
        String text = "§";
        if (namedTextColor == null) {
            return text + "f";
        }
        switch (namedTextColor.toString()) {
            case "black":
                return text + "0";
            case "dark_blue":
                return text + "1";
            case "dark_green":
                return text + "2";
            case "dark_aqua":
                return text + "3";
            case "dark_red":
                return text + "4";
            case "dark_purple":
                return text + "5";
            case "gold":
                return text + "6";
            case "gray":
                return text + "7";
            case "dark_gray":
                return text + "8";
            case "blue":
                return text + "9";
            case "green":
                return text + "a";
            case "aqua":
                return text + "b";
            case "red":
                return text + "c";
            case "light_purple":
                return text + "d";
            case "yellow":
                return text + "e";
            case "white":
                return text + "f";
            default:
                return text + "f";
        }
    }

    public static int getId(NamedTextColor namedTextColor) {
        if (namedTextColor == null) {
            return -1;
        }
        switch (namedTextColor.toString()) {
            case "black":
                return 0;
            case "dark_blue":
                return 1;
            case "dark_green":
                return 2;
            case "dark_aqua":
                return 3;
            case "dark_red":
                return 4;
            case "dark_purple":
                return 5;
            case "gold":
                return 6;
            case "gray":
                return 7;
            case "dark_gray":
                return 8;
            case "blue":
                return 9;
            case "green":
                return 10;
            case "aqua":
                return 11;
            case "red":
                return 12;
            case "light_purple":
                return 13;
            case "yellow":
                return 14;
            case "white":
                return 15;
            default:
                return 15;
        }
    }

    public static NamedTextColor fromId(int value) {
        if (value < 0) {
            return null;
        }
        switch (value) {
            case 0:
                return NamedTextColor.BLACK;
            case 1:
                return NamedTextColor.DARK_BLUE;
            case 2:
                return NamedTextColor.DARK_GREEN;
            case 3:
                return NamedTextColor.DARK_AQUA;
            case 4:
                return NamedTextColor.DARK_RED;
            case 5:
                return NamedTextColor.DARK_PURPLE;
            case 6:
                return NamedTextColor.GOLD;
            case 7:
                return NamedTextColor.GRAY;
            case 8:
                return NamedTextColor.DARK_GRAY;
            case 9:
                return NamedTextColor.BLUE;
            case 10:
                return NamedTextColor.GREEN;
            case 11:
                return NamedTextColor.AQUA;
            case 12:
                return NamedTextColor.RED;
            case 13:
                return NamedTextColor.LIGHT_PURPLE;
            case 14:
                return NamedTextColor.YELLOW;
            case 15:
                return NamedTextColor.WHITE;
            default:
                return NamedTextColor.WHITE;
        }
    }
}
