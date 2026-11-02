package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer;

public interface ByteBufAllocationOperator {

    Object wrappedBuffer(byte[] values);

    Object copiedBuffer(byte[] values);

    Object buffer();

    Object buffer(int value);

    Object directBuffer();

    Object directBuffer(int value);

    Object compositeBuffer();

    Object compositeBuffer(int value);

    Object emptyBuffer();
}
