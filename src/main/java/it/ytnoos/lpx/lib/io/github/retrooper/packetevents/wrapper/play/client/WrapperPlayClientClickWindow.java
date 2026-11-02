package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.HashedStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class WrapperPlayClientClickWindow extends PacketWrapper<WrapperPlayClientClickWindow> {

    private int windowID;

    @Nullable private Integer stateID;

    private int slot;

    private int button;

    @Nullable private Integer actionNumber;

    private WrapperPlayClientClickWindow.WindowClickType windowClickType;

    @Nullable private Map<Integer, ItemStack> slots;

    @Nullable private Map<Integer, Optional<HashedStack>> hashedSlots;

    @Nullable private ItemStack carriedItemStack;

    @Nullable private HashedStack carriedHashedStack;

    public WrapperPlayClientClickWindow(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    @Obsolete
    public WrapperPlayClientClickWindow(
            int windowID,
            Optional<Integer> optional,
            int slot,
            int button,
            Optional<Integer> optional2,
            WrapperPlayClientClickWindow.WindowClickType windowClickType2,
            Optional<Map<Integer, ItemStack>> optional3,
            ItemStack itemStack) {
        super(PacketType.Play.Client.CLICK_WINDOW);
        this.windowID = windowID;
        this.stateID = (Integer) optional.orElse(null);
        this.slot = slot;
        this.button = button;
        this.actionNumber = (Integer) optional2.orElse(null);
        this.windowClickType = windowClickType2;
        this.slots = (Map<Integer, ItemStack>) optional3.orElse(null);
        this.carriedItemStack = itemStack;
    }

    public WrapperPlayClientClickWindow(
            int windowID,
            @Nullable Integer stateID,
            int slot,
            int button,
            WrapperPlayClientClickWindow.WindowClickType windowClickType2,
            @Nullable Map<Integer, Optional<HashedStack>> entries,
            @Nullable Optional<HashedStack> optional) {
        super(PacketType.Play.Client.CLICK_WINDOW);
        this.windowID = windowID;
        this.stateID = stateID;
        this.slot = slot;
        this.button = button;
        this.windowClickType = windowClickType2;
        this.hashedSlots = entries;
        this.carriedHashedStack = optional != null ? (HashedStack) optional.orElse(null) : null;
    }

    @Override
    public void read() {
        this.windowID = this.readContainerId();
        this.stateID =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)
                        ? this.readVarInt()
                        : null;
        this.slot = this.readShort();
        this.button = this.readByte();
        this.actionNumber =
                this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)
                        ? null
                        : Integer.valueOf(this.readShort());
        this.windowClickType =
                WrapperPlayClientClickWindow.WindowClickType.getById(this.readVarInt());
        this.readSlots();
    }

    protected void readSlots() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            PacketWrapper.Reader<Integer> reader = result -> Math.toIntExact(result.readShort());
            PacketWrapper.Reader<Optional<HashedStack>> reader2 = HashedStack::readOptional;
            this.hashedSlots = this.readMap(reader, reader2, this::callReadSlotsEvent, 128);
            this.carriedHashedStack = HashedStack.read(this);
        } else {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                PacketWrapper.Reader<Integer> reader3 =
                        result -> Math.toIntExact(result.readShort());
                PacketWrapper.Reader<ItemStack> reader4 = PacketWrapper::readItemStack;
                this.slots = this.readMap(reader3, reader4, this::callReadSlotsEvent, 128);
            }
            this.carriedItemStack = this.readItemStack();
        }
    }

    public void copy(WrapperPlayClientClickWindow wrapperPlayClientClickWindow) {
        this.windowID = wrapperPlayClientClickWindow.windowID;
        this.stateID = wrapperPlayClientClickWindow.stateID;
        this.slot = wrapperPlayClientClickWindow.slot;
        this.button = wrapperPlayClientClickWindow.button;
        this.actionNumber = wrapperPlayClientClickWindow.actionNumber;
        this.windowClickType = wrapperPlayClientClickWindow.windowClickType;
        this.slots = wrapperPlayClientClickWindow.slots;
        this.hashedSlots = wrapperPlayClientClickWindow.hashedSlots;
        this.carriedItemStack = wrapperPlayClientClickWindow.carriedItemStack;
        this.carriedHashedStack = wrapperPlayClientClickWindow.carriedHashedStack;
    }

    @Override
    public void write() {
        boolean newerThanOrEquals = this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17);
        this.writeContainerId(this.windowID);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17_1)) {
            this.writeVarInt(this.stateID != null ? this.stateID : -1);
        }
        this.writeShort(this.slot);
        this.writeByte(this.button);
        if (!newerThanOrEquals) {
            this.writeShort(this.actionNumber != null ? this.actionNumber : -1);
        }
        this.writeVarInt(this.windowClickType.ordinal());
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            this.writeMap(
                    this.hashedSlots != null ? this.hashedSlots : Collections.emptyMap(),
                    PacketWrapper::writeShort,
                    HashedStack::writeOptional);
            HashedStack.write(this, this.carriedHashedStack);
        } else {
            if (newerThanOrEquals) {
                this.writeMap(
                        this.slots != null ? this.slots : Collections.emptyMap(),
                        PacketWrapper::writeShort,
                        PacketWrapper::writeItemStack);
            }
            this.writeItemStack(this.carriedItemStack);
        }
    }

    public int getWindowId() {
        return this.windowID;
    }

    public void setWindowId(int windowID) {
        this.windowID = windowID;
    }

    public Optional<Integer> getStateId() {
        return Optional.ofNullable(this.stateID);
    }

    public void setStateID(Optional<Integer> optional) {
        this.stateID = (Integer) optional.orElse(null);
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getButton() {
        return this.button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    @Obsolete
    public Optional<Integer> getActionNumber() {
        return Optional.ofNullable(this.actionNumber);
    }

    @Obsolete
    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    public WrapperPlayClientClickWindow.WindowClickType getWindowClickType() {
        return this.windowClickType;
    }

    public void setWindowClickType(WrapperPlayClientClickWindow.WindowClickType windowClickType2) {
        this.windowClickType = windowClickType2;
    }

    @Obsolete
    public Optional<Map<Integer, ItemStack>> getSlots() {
        if (this.slots != null) {
            return Optional.of(this.slots);
        }
        if (this.hashedSlots != null) {
            HashMap entries = new HashMap(this.hashedSlots.size());
            Iterator<Entry<Integer, Optional<HashedStack>>> iterator2 =
                    this.hashedSlots.entrySet().iterator();
            while (iterator2.hasNext()) {
                Entry entry;
                HashedStack hashedStack =
                        (HashedStack)
                                ((Optional) (entry = (Entry) iterator2.next()).getValue())
                                        .orElse(null);
                entries.put(
                        (Integer) entry.getKey(),
                        hashedStack != null ? hashedStack.asItemStack() : ItemStack.EMPTY);
            }
            return Optional.of(entries);
        } else {
            return Optional.empty();
        }
    }

    @Obsolete
    public void setSlots(Map<Integer, ItemStack> entries) {
        this.setSlots(Optional.ofNullable(entries));
    }

    @Obsolete
    public void setSlots(Optional<Map<Integer, ItemStack>> optional) {
        this.slots = (Map<Integer, ItemStack>) optional.orElse(null);
        if (this.slots != null) {
            this.hashedSlots = new HashMap<>(this.slots.size());
            for (Entry<Integer, ItemStack> entry : this.slots.entrySet()) {
                this.hashedSlots.put(
                        (Integer) entry.getKey(),
                        HashedStack.toOptionalFromItemStack((ItemStack) entry.getValue()));
            }
        } else {
            this.hashedSlots = null;
        }
    }

    @UnknownNullability
    public Map<Integer, Optional<HashedStack>> getHashedSlots() {
        return this.hashedSlots;
    }

    public void setHashedSlots(Map<Integer, Optional<HashedStack>> entries) {
        this.hashedSlots = entries;
    }

    @Obsolete
    public ItemStack getCarriedItemStack() {
        if (this.carriedItemStack != null) {
            return this.carriedItemStack;
        } else {
            return this.carriedHashedStack != null
                    ? this.carriedHashedStack.asItemStack()
                    : ItemStack.EMPTY;
        }
    }

    @Nullable
    public ItemStack getLegacyCarriedItemStack() {
        return this.carriedItemStack;
    }

    @Nullable
    public Map<Integer, ItemStack> getLegacySlots() {
        return this.slots;
    }

    @Obsolete
    public void setCarriedItemStack(ItemStack itemStack) {
        this.carriedItemStack = itemStack;
        this.carriedHashedStack = HashedStack.fromItemStack(itemStack);
    }

    public Optional<HashedStack> getCarriedHashedStack() {
        return Optional.ofNullable(this.carriedHashedStack);
    }

    public void setCarriedHashedStack(@Nullable HashedStack hashedStack) {
        this.carriedHashedStack = hashedStack;
    }

    public void setCarriedHashedStack(Optional<HashedStack> optional) {
        this.carriedHashedStack = (HashedStack) optional.orElse(null);
    }

    public enum WindowClickType {
        PICKUP,
        QUICK_MOVE,
        SWAP,
        CLONE,
        THROW,
        QUICK_CRAFT,
        PICKUP_ALL,
        UNKNOWN;

        public static final WrapperPlayClientClickWindow.WindowClickType[] VALUES = values();

        public static WrapperPlayClientClickWindow.WindowClickType getById(int value) {
            return value >= 0 && value < VALUES.length - 1 ? VALUES[value] : UNKNOWN;
        }
    }
}
