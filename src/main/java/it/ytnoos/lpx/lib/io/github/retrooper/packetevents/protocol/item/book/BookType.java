package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.book;

public enum BookType {
    CRAFTING,
    FURNACE,
    BLAST_FURNACE,
    SMOKER;

    private static final BookType[] VALUES = values();

    public int getId() {
        return this.ordinal();
    }

    public static BookType getById(int value) {
        return VALUES[value];
    }
}
