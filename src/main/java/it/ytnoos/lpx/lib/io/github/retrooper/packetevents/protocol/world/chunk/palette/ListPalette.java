package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk.palette;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Arrays;

public class ListPalette implements Palette {

    private final int bits;

    private final int[] data;

    private int nextId;

    public ListPalette(int bits) {
        this.bits = bits;
        this.data = new int[1 << bits];
        this.nextId = 0;
    }

    @Deprecated
    public ListPalette(int value, NetStreamInput netStreamInput) {
        this(value);
        int value2 = netStreamInput.readVarInt();
        for (int index = 0; index < value2; index++) {
            this.data[index] = netStreamInput.readVarInt();
        }
        this.nextId = value2;
    }

    public ListPalette(int value, PacketWrapper<?> packetWrapper) {
        this(value);
        int value2 = packetWrapper.readVarInt();
        for (int index = 0; index < value2; index++) {
            this.data[index] = packetWrapper.readVarInt();
        }
        this.nextId = value2;
    }

    public ListPalette(int bits, int[] values) {
        this.bits = bits;
        int value2 = 1 << this.bits;
        if (values.length > value2) {
            throw new IllegalArgumentException(
                    "Data length exceeds the max size the bits can hold");
        }
        this.data = Arrays.copyOf(values, value2);
        this.nextId = values.length;
    }

    @Override
    public int size() {
        return this.nextId;
    }

    @Override
    public int stateToId(int value) {
        int value2 = -1;
        for (int index = 0; index < this.nextId; index++) {
            if (this.data[index] == value) {
                value2 = index;
                break;
            }
        }
        if (value2 == -1 && this.size() < this.data.length) {
            value2 = this.nextId++;
            this.data[value2] = value;
        }
        return value2;
    }

    @Override
    public int idToState(int value) {
        return value >= 0 && value < this.size() ? this.data[value] : 0;
    }

    @Override
    public int getBits() {
        return this.bits;
    }
}
