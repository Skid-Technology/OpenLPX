package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.filter;

import java.util.BitSet;

public class FilterMask {

    public static final FilterMask FULLY_FILTERED =
            new FilterMask(new BitSet(0), FilterMaskType.FULLY_FILTERED);

    public static final FilterMask PASS_THROUGH =
            new FilterMask(new BitSet(0), FilterMaskType.PASS_THROUGH);

    private final BitSet mask;

    private final FilterMaskType type;

    private FilterMask(BitSet bitSet, FilterMaskType filterMaskType) {
        this.type = filterMaskType;
        this.mask = bitSet;
    }

    public FilterMask(BitSet bitSet) {
        this.type = FilterMaskType.PARTIALLY_FILTERED;
        this.mask = bitSet;
    }

    public BitSet getMask() {
        return this.mask;
    }

    public FilterMaskType getType() {
        return this.type;
    }
}
