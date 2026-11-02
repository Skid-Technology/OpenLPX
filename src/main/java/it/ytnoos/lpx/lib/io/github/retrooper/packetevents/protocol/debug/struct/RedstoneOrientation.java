package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class RedstoneOrientation {

    private final int index;

    public RedstoneOrientation(int index) {
        this.index = index;
    }

    public static RedstoneOrientation read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        return new RedstoneOrientation(value);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, RedstoneOrientation redstoneOrientation) {
        packetWrapper.writeVarInt(redstoneOrientation.index);
    }

    public int getIndex() {
        return this.index;
    }
}
