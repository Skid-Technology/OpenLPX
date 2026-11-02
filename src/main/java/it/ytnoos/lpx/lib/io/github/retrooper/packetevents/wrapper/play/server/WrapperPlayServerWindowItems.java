package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrapperPlayServerWindowItems extends PacketWrapper<WrapperPlayServerWindowItems> {

    private int windowID;

    private int stateID;

    private List<ItemStack> items;

    private Optional<ItemStack> carriedItem;

    public WrapperPlayServerWindowItems(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerWindowItems(
            int windowID, int stateID, List<ItemStack> items, @Nullable ItemStack itemStack) {
        super(PacketType.Play.Server.WINDOW_ITEMS);
        this.windowID = windowID;
        this.stateID = stateID;
        this.items = items;
        this.carriedItem = Optional.ofNullable(itemStack);
    }

    @Override
    public void read() {
        this.windowID = this.readContainerId();
        boolean matches;
        if (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            this.stateID = this.readVarInt();
        }
        int value = matches ? this.readVarInt() : this.readShort();
        this.items = new ArrayList<>(value);
        for (int index = 0; index < value; index++) {
            this.items.add(this.readItemStack());
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            this.carriedItem = Optional.of(this.readItemStack());
        } else {
            this.carriedItem = Optional.empty();
        }
    }

    @Override
    public void write() {
        this.writeContainerId(this.windowID);
        boolean matches;
        if (matches = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            this.writeVarInt(this.stateID);
        }
        if (matches) {
            this.writeVarInt(this.items.size());
        } else {
            this.writeShort(this.items.size());
        }
        for (ItemStack itemStack : this.items) {
            this.writeItemStack(itemStack);
        }
        if (matches) {
            this.writeItemStack(this.carriedItem.orElse(ItemStack.EMPTY));
        }
    }

    public void copy(WrapperPlayServerWindowItems wrapperPlayServerWindowItems) {
        this.windowID = wrapperPlayServerWindowItems.windowID;
        this.stateID = wrapperPlayServerWindowItems.stateID;
        this.items = wrapperPlayServerWindowItems.items;
        this.carriedItem = wrapperPlayServerWindowItems.carriedItem;
    }

    public int getWindowId() {
        return this.windowID;
    }

    public void setWindowId(int windowID) {
        this.windowID = windowID;
    }

    public int getStateId() {
        return this.stateID;
    }

    public void setStateId(int stateID) {
        this.stateID = stateID;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public Optional<ItemStack> getCarriedItem() {
        return this.carriedItem;
    }

    public void setCarriedItem(@Nullable ItemStack itemStack) {
        this.carriedItem = Optional.ofNullable(itemStack);
    }
}
