package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception;

public class PacketLimitException extends RuntimeException {

    private final PacketLimitException.Limit limit;

    private final long value;

    private final long max;

    public PacketLimitException(PacketLimitException.Limit limit2, long value, long max) {
        super(
                limit2.getDescription() + " exceeded (" + value + " > " + max + ")",
                null,
                false,
                false);
        this.limit = limit2;
        this.value = value;
        this.max = max;
    }

    public PacketLimitException.Limit getLimit() {
        return this.limit;
    }

    public long getValue() {
        return this.value;
    }

    public long getMax() {
        return this.max;
    }

    public enum Limit {
        ITEM_DEPTH("Item depth"),
        ITEMS_PER_PACKET("Items per packet"),
        COLLECTION_SIZE("Collection size"),
        MAP_SIZE("Map size"),
        ARRAY_SIZE("Array size"),
        STRING_LENGTH("String length"),
        BYTE_ARRAY_LENGTH("Byte array length"),
        LENGTH_PREFIXED_SIZE("Length-prefixed size"),
        PAYLOAD_SIZE("Payload size"),
        BOOK_PAGES("Book pages"),
        NBT_SIZE("NBT size"),
        NBT_DEPTH("NBT depth"),
        NBT_ELEMENTS("NBT elements per packet");

        private final String description;

        Limit(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
}
