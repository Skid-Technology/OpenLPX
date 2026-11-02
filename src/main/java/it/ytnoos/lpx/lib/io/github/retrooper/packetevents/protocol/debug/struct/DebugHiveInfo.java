package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugHiveInfo {

    private final StateType type;

    private final int occupantCount;

    private final int honeyLevel;

    private final boolean sedated;

    public DebugHiveInfo(StateType stateType, int occupantCount, int honeyLevel, boolean sedated) {
        this.type = stateType;
        this.occupantCount = occupantCount;
        this.honeyLevel = honeyLevel;
        this.sedated = sedated;
    }

    public static DebugHiveInfo read(PacketWrapper<?> packetWrapper) {
        StateType stateType =
                packetWrapper.readMappedEntity(StateTypes.getRegistry()).getStateType();
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        boolean matches = packetWrapper.readBoolean();
        return new DebugHiveInfo(stateType, value, value2, matches);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugHiveInfo debugHiveInfo) {
        packetWrapper.writeMappedEntity(debugHiveInfo.type.getMapped());
        packetWrapper.writeVarInt(debugHiveInfo.occupantCount);
        packetWrapper.writeVarInt(debugHiveInfo.honeyLevel);
        packetWrapper.writeBoolean(debugHiveInfo.sedated);
    }

    public StateType getType() {
        return this.type;
    }

    public int getOccupantCount() {
        return this.occupantCount;
    }

    public int getHoneyLevel() {
        return this.honeyLevel;
    }

    public boolean isSedated() {
        return this.sedated;
    }
}
