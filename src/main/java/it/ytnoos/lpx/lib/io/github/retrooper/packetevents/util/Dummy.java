package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public final class Dummy {

    public static final Dummy DUMMY = new Dummy();

    private Dummy() {}

    public static Dummy dummyRead(PacketWrapper<?> packetWrapper) {
        return DUMMY;
    }

    public static Dummy dummyReadNbt(PacketWrapper<?> packetWrapper) {
        packetWrapper.readNBTRaw();
        return DUMMY;
    }

    public static void dummyWrite(PacketWrapper<?> packetWrapper, Dummy dummy) {}

    public static void dummyWriteNbt(PacketWrapper<?> packetWrapper, Dummy dummy) {
        packetWrapper.writeByte(10);
        packetWrapper.writeByte(0);
    }

    public static Dummy dummy() {
        return DUMMY;
    }

    @Override
    public String toString() {
        return "Dummy{}";
    }
}
