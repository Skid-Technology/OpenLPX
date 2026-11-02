package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class SingletonPalette implements Palette {

    private final int state;

    @Deprecated
    public SingletonPalette(NetStreamInput netStreamInput) {
        this(netStreamInput.readVarInt());
    }

    public SingletonPalette(PacketWrapper<?> packetWrapper) {
        this(packetWrapper.readVarInt());
    }

    public SingletonPalette(int state) {
        this.state = state;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public int stateToId(int value) {
        return this.state == value ? 0 : -1;
    }

    @Override
    public int idToState(int value) {
        return value == 0 ? this.state : 0;
    }

    @Override
    public int getBits() {
        return 0;
    }
}
