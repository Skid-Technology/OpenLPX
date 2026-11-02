package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

public class GlobalPalette implements Palette {

    public static final int BITS_PER_ENTRY = 15;

    public static final GlobalPalette INSTANCE = new GlobalPalette();

    @Override
    public int size() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int stateToId(int value) {
        return value;
    }

    @Override
    public int idToState(int value) {
        return value;
    }

    @Override
    public int getBits() {
        return 15;
    }
}
