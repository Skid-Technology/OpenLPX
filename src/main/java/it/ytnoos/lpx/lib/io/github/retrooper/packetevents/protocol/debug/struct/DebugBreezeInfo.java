package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class DebugBreezeInfo {

    @Nullable private final Integer attackTarget;

    @Nullable private final Vector3i jumpTarget;

    public DebugBreezeInfo(@Nullable Integer attackTarget, @Nullable Vector3i vector3i) {
        this.attackTarget = attackTarget;
        this.jumpTarget = vector3i;
    }

    public static DebugBreezeInfo read(PacketWrapper<?> packetWrapper) {
        Integer value = packetWrapper.readOptional(PacketWrapper::readVarInt);
        Vector3i vector3i = packetWrapper.readOptional(PacketWrapper::readBlockPosition);
        return new DebugBreezeInfo(value, vector3i);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugBreezeInfo debugBreezeInfo) {
        packetWrapper.writeOptional(debugBreezeInfo.attackTarget, PacketWrapper::writeVarInt);
        packetWrapper.writeOptional(debugBreezeInfo.jumpTarget, PacketWrapper::writeBlockPosition);
    }

    @Nullable
    public Integer getAttackTarget() {
        return this.attackTarget;
    }

    @Nullable
    public Vector3i getJumpTarget() {
        return this.jumpTarget;
    }
}
