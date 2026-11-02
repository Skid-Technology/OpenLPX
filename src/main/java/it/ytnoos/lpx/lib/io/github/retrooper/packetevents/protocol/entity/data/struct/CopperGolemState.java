package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public enum CopperGolemState {
    IDLE,
    GETTING_ITEM,
    GETTING_NO_ITEM,
    DROPPING_ITEM,
    DROPPING_NO_ITEM;

    private static final CopperGolemState[] STATES = values();

    public static CopperGolemState read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readEnum(STATES);
    }

    public static void write(PacketWrapper<?> packetWrapper, CopperGolemState copperGolemState) {
        packetWrapper.writeEnum(copperGolemState);
    }
}
