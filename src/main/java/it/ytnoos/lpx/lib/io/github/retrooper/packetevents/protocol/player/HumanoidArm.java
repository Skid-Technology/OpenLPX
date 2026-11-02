package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public enum HumanoidArm {
    LEFT,
    RIGHT;

    public static final HumanoidArm[] VALUES = values();

    public int getId() {
        return this == RIGHT ? 0 : 1;
    }

    public static HumanoidArm getById(int value) {
        return value == 0 ? RIGHT : LEFT;
    }

    public static HumanoidArm read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readVarInt() == 1 ? RIGHT : LEFT;
    }

    public static void write(PacketWrapper<?> packetWrapper, HumanoidArm humanoidArm) {
        packetWrapper.writeEnum(humanoidArm);
    }
}
