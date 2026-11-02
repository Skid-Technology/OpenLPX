package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemRepairable {

    private MappedEntitySet<ItemType> items;

    public ItemRepairable(MappedEntitySet<ItemType> mappedEntitySet) {
        this.items = mappedEntitySet;
    }

    public static ItemRepairable read(PacketWrapper<?> packetWrapper) {
        MappedEntitySet<ItemType> mappedEntitySet =
                MappedEntitySet.read(packetWrapper, ItemTypes.getRegistry());
        return new ItemRepairable(mappedEntitySet);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemRepairable itemRepairable) {
        MappedEntitySet.write(packetWrapper, itemRepairable.items);
    }

    public MappedEntitySet<ItemType> getItems() {
        return this.items;
    }

    public void setItems(MappedEntitySet<ItemType> mappedEntitySet) {
        this.items = mappedEntitySet;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemRepairable)) {
            return false;
        }
        ItemRepairable itemRepairable = (ItemRepairable) result;
        return this.items.equals(itemRepairable.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.items);
    }

    @Override
    public String toString() {
        return "ItemRepairable{items=" + this.items + 125;
    }
}
