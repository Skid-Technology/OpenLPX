package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;

import org.jetbrains.annotations.Nullable;

public class PacketTypeData {

    @Nullable private PacketTypeCommon packetType;

    private int nativePacketId;

    public PacketTypeData(@Nullable PacketTypeCommon packetTypeCommon, int nativePacketId) {
        this.packetType = packetTypeCommon;
        this.nativePacketId = nativePacketId;
    }

    @Nullable
    public PacketTypeCommon getPacketType() {
        return this.packetType;
    }

    public int getNativePacketId() {
        return this.nativePacketId;
    }

    public void setPacketType(@Nullable PacketTypeCommon packetTypeCommon) {
        this.packetType = packetTypeCommon;
    }

    public void setNativePacketId(int nativePacketId) {
        this.nativePacketId = nativePacketId;
    }
}
