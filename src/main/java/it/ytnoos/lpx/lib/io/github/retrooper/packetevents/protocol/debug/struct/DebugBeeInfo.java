package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.struct;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public final class DebugBeeInfo {

    @Nullable private final Vector3i hivePos;

    @Nullable private final Vector3i flowerPos;

    private final int travelTicks;

    private final List<Vector3i> blacklistedHives;

    public DebugBeeInfo(
            @Nullable Vector3i vector3i,
            @Nullable Vector3i vector3i2,
            int travelTicks,
            List<Vector3i> blacklistedHives) {
        this.hivePos = vector3i;
        this.flowerPos = vector3i2;
        this.travelTicks = travelTicks;
        this.blacklistedHives = blacklistedHives;
    }

    public static DebugBeeInfo read(PacketWrapper<?> packetWrapper) {
        Vector3i vector3i = packetWrapper.readOptional(PacketWrapper::readBlockPosition);
        Vector3i vector3i2 = packetWrapper.readOptional(PacketWrapper::readBlockPosition);
        int value = packetWrapper.readVarInt();
        List values = packetWrapper.readList(PacketWrapper::readBlockPosition);
        return new DebugBeeInfo(vector3i, vector3i2, value, values);
    }

    public static void write(PacketWrapper<?> packetWrapper, DebugBeeInfo debugBeeInfo) {
        packetWrapper.writeOptional(debugBeeInfo.hivePos, PacketWrapper::writeBlockPosition);
        packetWrapper.writeOptional(debugBeeInfo.flowerPos, PacketWrapper::writeBlockPosition);
        packetWrapper.writeVarInt(debugBeeInfo.travelTicks);
        packetWrapper.writeList(debugBeeInfo.blacklistedHives, PacketWrapper::writeBlockPosition);
    }

    @Nullable
    public Vector3i getHivePos() {
        return this.hivePos;
    }

    @Nullable
    public Vector3i getFlowerPos() {
        return this.flowerPos;
    }

    public int getTravelTicks() {
        return this.travelTicks;
    }

    public List<Vector3i> getBlacklistedHives() {
        return this.blacklistedHives;
    }
}
