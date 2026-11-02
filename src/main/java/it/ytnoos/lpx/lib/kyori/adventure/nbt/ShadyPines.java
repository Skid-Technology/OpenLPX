package it.ytnoos.lpx.lib.kyori.adventure.nbt;

final class ShadyPines {

    private ShadyPines() {}

    static int floor(double value) {
        int value2 = (int) value;
        return value < value2 ? value2 - 1 : value2;
    }

    static int floor(float value) {
        int value2 = (int) value;
        return value < value2 ? value2 - 1 : value2;
    }
}
