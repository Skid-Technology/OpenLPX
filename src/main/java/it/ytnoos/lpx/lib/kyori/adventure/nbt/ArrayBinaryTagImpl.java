package it.ytnoos.lpx.lib.kyori.adventure.nbt;

abstract class ArrayBinaryTagImpl extends AbstractBinaryTag implements ArrayBinaryTag {

    static void checkIndex(int value, int value2) {
        if (value < 0 || value >= value2) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + value);
        }
    }
}
