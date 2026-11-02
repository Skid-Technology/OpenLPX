package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemContainerLoot {

    private ResourceLocation lootTable;

    private long seed;

    public ItemContainerLoot(ResourceLocation resourceLocation, long seed) {
        this.lootTable = resourceLocation;
        this.seed = seed;
    }

    public static ItemContainerLoot read(PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound = packetWrapper.readNBT();
        ResourceLocation resourceLocation =
                new ResourceLocation(nBTCompound.getStringTagValueOrThrow("loot_table"));
        NBTNumber nBTNumber;
        long value =
                (nBTNumber = nBTCompound.getNumberTagOrNull("seed")) == null
                        ? 0L
                        : nBTNumber.getAsLong();
        return new ItemContainerLoot(resourceLocation, value);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemContainerLoot itemContainerLoot) {
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("loot_table", new NBTString(itemContainerLoot.lootTable.toString()));
        if (itemContainerLoot.seed != 0L) {
            nBTCompound.setTag("seed", new NBTLong(itemContainerLoot.seed));
        }
        packetWrapper.writeNBT(nBTCompound);
    }

    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    public void setLootTable(ResourceLocation resourceLocation) {
        this.lootTable = resourceLocation;
    }

    public long getSeed() {
        return this.seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemContainerLoot)) {
            return false;
        }
        ItemContainerLoot itemContainerLoot = (ItemContainerLoot) result;
        return this.seed != itemContainerLoot.seed
                ? false
                : this.lootTable.equals(itemContainerLoot.lootTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.lootTable, this.seed);
    }
}
