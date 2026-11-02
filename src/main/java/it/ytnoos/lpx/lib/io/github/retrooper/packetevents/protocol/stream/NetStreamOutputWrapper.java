package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stream;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class NetStreamOutputWrapper extends NetStreamOutput {

    private final PacketWrapper<?> wrapper;

    public NetStreamOutputWrapper(PacketWrapper<?> packetWrapper) {
        super(null);
        this.wrapper = packetWrapper;
    }

    @Override
    public void write(int value) {
        this.wrapper.writeByte(value);
    }

    @Override
    public void write(byte[] values) {
        this.write(values, 0, values.length);
    }

    @Override
    public void write(byte[] values, int value, int value2) {
        ByteBufHelper.writeBytes(this.wrapper.buffer, values, value, value2);
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}
}
