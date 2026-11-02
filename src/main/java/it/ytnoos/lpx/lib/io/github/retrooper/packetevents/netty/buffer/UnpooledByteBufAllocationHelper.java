package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;

public class UnpooledByteBufAllocationHelper {

    public static Object wrappedBuffer(byte[] values) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .wrappedBuffer(values);
    }

    public static Object copiedBuffer(byte[] values) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .copiedBuffer(values);
    }

    public static Object buffer() {
        return PacketEvents.getAPI().getNettyManager().getByteBufAllocationOperator().buffer();
    }

    public static Object directBuffer() {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .directBuffer();
    }

    public static Object compositeBuffer() {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .compositeBuffer();
    }

    public static Object buffer(int value) {
        return PacketEvents.getAPI().getNettyManager().getByteBufAllocationOperator().buffer(value);
    }

    public static Object directBuffer(int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .directBuffer(value);
    }

    public static Object compositeBuffer(int value) {
        return PacketEvents.getAPI()
                .getNettyManager()
                .getByteBufAllocationOperator()
                .compositeBuffer(value);
    }

    public static Object emptyBuffer() {
        return PacketEvents.getAPI().getNettyManager().getByteBufAllocationOperator().emptyBuffer();
    }
}
