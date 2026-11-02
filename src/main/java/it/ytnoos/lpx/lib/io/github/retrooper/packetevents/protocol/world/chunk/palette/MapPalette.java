package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class MapPalette implements Palette {

    private final int bits;

    private final int[] idToState;

    private final Int2IntHashMap stateToId;

    private int nextId = 0;

    public MapPalette(int bits) {
        this.bits = bits;
        this.idToState = new int[1 << bits];
        this.stateToId = new Int2IntHashMap(1 << bits);
    }

    @Deprecated
    public MapPalette(int value, NetStreamInput netStreamInput) {
        this(value);
        int value2 = netStreamInput.readVarInt();
        for (int index = 0; index < value2; index++) {
            int value3 = netStreamInput.readVarInt();
            this.idToState[index] = value3;
            this.stateToId.putIfAbsent(value3, index);
        }
        this.nextId = value2;
    }

    public MapPalette(int value, PacketWrapper<?> packetWrapper) {
        this(value);
        int value2 = packetWrapper.readVarInt();
        for (int index = 0; index < value2; index++) {
            int value3 = packetWrapper.readVarInt();
            this.idToState[index] = value3;
            this.stateToId.putIfAbsent(value3, index);
        }
        this.nextId = value2;
    }

    @Override
    public int size() {
        return this.nextId;
    }

    @Override
    public int stateToId(int value) {
        int value2;
        if ((value2 = this.stateToId.get(value)) == -1 && this.size() < this.idToState.length) {
            value2 = this.nextId++;
            this.idToState[value2] = value;
            this.stateToId.put(value, value2);
        }
        return value2;
    }

    @Override
    public int idToState(int value) {
        return value >= 0 && value < this.size() ? this.idToState[value] : 0;
    }

    @Override
    public int getBits() {
        return this.bits;
    }
}
