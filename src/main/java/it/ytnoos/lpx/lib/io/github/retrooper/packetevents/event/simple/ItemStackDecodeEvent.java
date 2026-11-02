package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.simple;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.ProtocolPacketEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.exception.PacketProcessException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class ItemStackDecodeEvent extends PacketEvent {

    private final PacketWrapper<?> wrapper;

    private final ProtocolPacketEvent event;

    private final int decodedSizeBytes;

    private ItemStack itemStack;

    public ItemStackDecodeEvent(
            PacketWrapper<?> packetWrapper,
            ProtocolPacketEvent protocolPacketEvent,
            ItemStack itemStack)
            throws PacketProcessException {
        this(packetWrapper, protocolPacketEvent, itemStack, itemStack.getDecodedSizeBytes());
    }

    public ItemStackDecodeEvent(
            PacketWrapper<?> packetWrapper,
            ProtocolPacketEvent protocolPacketEvent,
            ItemStack itemStack2,
            int decodedSizeBytes)
            throws PacketProcessException {
        this.wrapper = packetWrapper;
        this.event = protocolPacketEvent;
        this.itemStack = itemStack2;
        this.decodedSizeBytes = decodedSizeBytes;
        if (itemStack2.getDecodedSizeBytes() < 0) {
            itemStack2.setDecodedSizeBytes(decodedSizeBytes);
        }
    }

    public PacketWrapper<?> getWrapper() {
        return this.wrapper;
    }

    public ProtocolPacketEvent getEvent() {
        return this.event;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getDecodedSizeBytes() {
        return this.decodedSizeBytes;
    }

    public void setItemStack(ItemStack itemStack2) {
        if (itemStack2.getDecodedSizeBytes() < 0) {
            itemStack2.setDecodedSizeBytes(this.decodedSizeBytes);
        }
        this.itemStack = itemStack2;
    }
}
