package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStackSerialization;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayClientCreativeInventoryAction
        extends PacketWrapper<WrapperPlayClientCreativeInventoryAction> {

    private int slot;

    private ItemStack itemStack;

    public WrapperPlayClientCreativeInventoryAction(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientCreativeInventoryAction(int slot, ItemStack itemStack2) {
        super(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION);
        this.slot = slot;
        this.itemStack = itemStack2;
    }

    @Override
    public void read() {
        this.slot =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_5)
                        ? this.readUnsignedShort()
                        : this.readShort();
        this.itemStack =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)
                        ? ItemStackSerialization.readUntrusted(this)
                        : ItemStackSerialization.read(this);
    }

    @Override
    public void write() {
        this.writeShort(this.slot);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            ItemStackSerialization.writeUntrusted(this, this.itemStack);
        } else {
            ItemStackSerialization.write(this, this.itemStack);
        }
    }

    public void copy(
            WrapperPlayClientCreativeInventoryAction wrapperPlayClientCreativeInventoryAction) {
        this.slot = wrapperPlayClientCreativeInventoryAction.slot;
        this.itemStack = wrapperPlayClientCreativeInventoryAction.itemStack;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack2) {
        this.itemStack = itemStack2;
    }
}
