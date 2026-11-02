package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.TypedEntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ItemBees {

    private List<ItemBees.BeeEntry> bees;

    public ItemBees(List<ItemBees.BeeEntry> bees) {
        this.bees = bees;
    }

    public static ItemBees read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(ItemBees.BeeEntry::read, 128);
        return new ItemBees(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemBees itemBees) {
        packetWrapper.writeList(itemBees.bees, ItemBees.BeeEntry::write);
    }

    public void addBee(ItemBees.BeeEntry beeEntry) {
        this.bees.add(beeEntry);
    }

    public List<ItemBees.BeeEntry> getBees() {
        return this.bees;
    }

    public void setBees(List<ItemBees.BeeEntry> bees) {
        this.bees = bees;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemBees)) {
            return false;
        }
        ItemBees itemBees = (ItemBees) result;
        return this.bees.equals(itemBees.bees);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.bees);
    }

    public static class BeeEntry {

        private TypedEntityData entityData;

        private int ticksInHive;

        private int minTicksInHive;

        @Deprecated
        public BeeEntry(NBTCompound nBTCompound, int value, int value2) {
            this(new TypedEntityData(nBTCompound), value, value2);
        }

        public BeeEntry(TypedEntityData typedEntityData, int ticksInHive, int minTicksInHive) {
            this.entityData = typedEntityData;
            this.ticksInHive = ticksInHive;
            this.minTicksInHive = minTicksInHive;
        }

        public static ItemBees.BeeEntry read(PacketWrapper<?> packetWrapper) {
            TypedEntityData typedEntityData = TypedEntityData.read(packetWrapper);
            int value = packetWrapper.readVarInt();
            int value2 = packetWrapper.readVarInt();
            return new ItemBees.BeeEntry(typedEntityData, value, value2);
        }

        public static void write(PacketWrapper<?> packetWrapper, ItemBees.BeeEntry beeEntry) {
            TypedEntityData.write(packetWrapper, beeEntry.entityData);
            packetWrapper.writeVarInt(beeEntry.ticksInHive);
            packetWrapper.writeVarInt(beeEntry.minTicksInHive);
        }

        public TypedEntityData getTypedEntityData() {
            return this.entityData;
        }

        public void setTypedEntityData(TypedEntityData typedEntityData) {
            this.entityData = typedEntityData;
        }

        public NBTCompound getEntityData() {
            return this.entityData.getCompound();
        }

        public void setEntityData(NBTCompound nBTCompound) {
            this.entityData = new TypedEntityData(nBTCompound);
        }

        public int getTicksInHive() {
            return this.ticksInHive;
        }

        public void setTicksInHive(int ticksInHive) {
            this.ticksInHive = ticksInHive;
        }

        public int getMinTicksInHive() {
            return this.minTicksInHive;
        }

        public void setMinTicksInHive(int minTicksInHive) {
            this.minTicksInHive = minTicksInHive;
        }

        @Override
        public boolean equals(Object result) {
            if (this == result) {
                return true;
            } else if (!(result instanceof ItemBees.BeeEntry)) {
                return false;
            } else {
                ItemBees.BeeEntry beeEntry = (ItemBees.BeeEntry) result;
                if (this.ticksInHive != beeEntry.ticksInHive) {
                    return false;
                } else {
                    return this.minTicksInHive != beeEntry.minTicksInHive
                            ? false
                            : this.entityData.equals(beeEntry.entityData);
                }
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.entityData, this.ticksInHive, this.minTicksInHive);
        }
    }
}
