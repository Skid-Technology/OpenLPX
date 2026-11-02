package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSetPlayerInventory
        extends PacketWrapper<WrapperPlayServerSetPlayerInventory> {

    private int slot;

    private ItemStack stack;

    public WrapperPlayServerSetPlayerInventory(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSetPlayerInventory(int slot, ItemStack itemStack) {
        super(PacketType.Play.Server.SET_PLAYER_INVENTORY);
        this.slot = slot;
        this.stack = itemStack;
    }

    @Override
    public void read() {
        this.slot = this.readVarInt();
        this.stack = this.readItemStack();
    }

    @Override
    public void write() {
        this.writeVarInt(this.slot);
        this.writeItemStack(this.stack);
    }

    public void copy(WrapperPlayServerSetPlayerInventory wrapperPlayServerSetPlayerInventory) {
        this.slot = wrapperPlayServerSetPlayerInventory.slot;
        this.stack = wrapperPlayServerSetPlayerInventory.stack;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack itemStack) {
        this.stack = itemStack;
    }
}
