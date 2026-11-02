package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.impl.netty.buffer;

import io.netty.buffer.Unpooled;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufAllocationOperator;

public class ByteBufAllocationOperatorImpl implements ByteBufAllocationOperator {

    @Override
    public Object wrappedBuffer(byte[] values) {
        return Unpooled.wrappedBuffer(values);
    }

    @Override
    public Object copiedBuffer(byte[] values) {
        return Unpooled.copiedBuffer(values);
    }

    @Override
    public Object buffer() {
        return Unpooled.buffer();
    }

    @Override
    public Object buffer(int value) {
        return Unpooled.buffer(value);
    }

    @Override
    public Object directBuffer() {
        return Unpooled.directBuffer();
    }

    @Override
    public Object directBuffer(int value) {
        return Unpooled.directBuffer(value);
    }

    @Override
    public Object compositeBuffer() {
        return Unpooled.compositeBuffer();
    }

    @Override
    public Object compositeBuffer(int value) {
        return Unpooled.compositeBuffer(value);
    }

    @Override
    public Object emptyBuffer() {
        return Unpooled.EMPTY_BUFFER;
    }
}
