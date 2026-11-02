package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

public interface Palette {

    int size();

    int stateToId(int value);

    int idToState(int value);

    int getBits();
}
