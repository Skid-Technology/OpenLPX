package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream.NetStreamInput;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class NibbleArray3d {

    private final byte[] data;

    public NibbleArray3d(byte[] data) {
        this.data = data;
    }

    public NibbleArray3d(int value) {
        this(new byte[value >> 1]);
    }

    @Deprecated
    public NibbleArray3d(NetStreamInput netStreamInput, int value) {
        this(netStreamInput.readBytes(value));
    }

    public NibbleArray3d(PacketWrapper<?> packetWrapper, int value) {
        this(packetWrapper.readBytes(value));
    }

    public byte[] getData() {
        return this.data;
    }

    public int get(int value, int value2, int value3) {
        int value4;
        int value5 = (value4 = value2 << 8 | value3 << 4 | value) >> 1;
        return (value4 & 1) == 0 ? this.data[value5] & 15 : this.data[value5] >> 4 & 15;
    }

    public void set(int value, int value2, int value3, int value4) {
        int value5;
        int value6 = (value5 = value2 << 8 | value3 << 4 | value) >> 1;
        if ((value5 & 1) == 0) {
            this.data[value6] = (byte) (this.data[value6] & 240 | value4 & 15);
        } else {
            this.data[value6] = (byte) (this.data[value6] & 15 | (value4 & 15) << 4);
        }
    }
}
