package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class NBTDecodeEvent extends PacketEvent {

    private final PacketWrapper<?> wrapper;

    private final ProtocolPacketEvent event;

    private final int decodedSizeBytes;

    private NBT nbt;

    public NBTDecodeEvent(
            PacketWrapper<?> packetWrapper, ProtocolPacketEvent protocolPacketEvent, NBT nBT)
            throws PacketProcessException {
        this(packetWrapper, protocolPacketEvent, nBT, nBT.getDecodedSizeBytes());
    }

    public NBTDecodeEvent(
            PacketWrapper<?> packetWrapper,
            ProtocolPacketEvent protocolPacketEvent,
            NBT nBT,
            int decodedSizeBytes)
            throws PacketProcessException {
        this.wrapper = packetWrapper;
        this.event = protocolPacketEvent;
        this.nbt = nBT;
        this.decodedSizeBytes = decodedSizeBytes;
        if (nBT != NBTEnd.INSTANCE && nBT.getDecodedSizeBytes() < 0) {
            nBT.setDecodedSizeBytes(decodedSizeBytes);
        }
    }

    public PacketWrapper<?> getWrapper() {
        return this.wrapper;
    }

    public ProtocolPacketEvent getEvent() {
        return this.event;
    }

    public NBT getNbt() {
        return this.nbt;
    }

    public int getDecodedSizeBytes() {
        return this.decodedSizeBytes;
    }

    public void setNbt(NBT nBT) {
        if (nBT != NBTEnd.INSTANCE && nBT.getDecodedSizeBytes() < 0) {
            nBT.setDecodedSizeBytes(this.decodedSizeBytes);
        }
        this.nbt = nBT;
    }
}
