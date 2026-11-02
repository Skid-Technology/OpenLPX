package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class TagSlotDisplay extends SlotDisplay<TagSlotDisplay> {

    private MappedEntitySet<ItemType> items;

    public TagSlotDisplay(ResourceLocation resourceLocation) {
        this(new MappedEntitySet<>(resourceLocation));
    }

    public TagSlotDisplay(MappedEntitySet<ItemType> mappedEntitySet) {
        super(SlotDisplayTypes.TAG);
        this.items = mappedEntitySet;
    }

    public static TagSlotDisplay read(PacketWrapper<?> packetWrapper) {
        MappedEntitySet mappedEntitySet =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)
                        ? MappedEntitySet.read(packetWrapper, ItemTypes.getRegistry())
                        : new MappedEntitySet(packetWrapper.readIdentifier());
        return new TagSlotDisplay(mappedEntitySet);
    }

    public static void write(PacketWrapper<?> packetWrapper, TagSlotDisplay tagSlotDisplay) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_26_3)) {
            MappedEntitySet.write(packetWrapper, tagSlotDisplay.items);
        } else {
            packetWrapper.writeIdentifier(tagSlotDisplay.getItemTag());
        }
    }

    public MappedEntitySet<ItemType> getItems() {
        return this.items;
    }

    public void setItems(MappedEntitySet<ItemType> mappedEntitySet) {
        this.items = mappedEntitySet;
    }

    public ResourceLocation getItemTag() {
        ResourceLocation resourceLocation;
        if ((resourceLocation = this.items.getTagKey()) == null) {
            throw new IllegalStateException("No tag key present for " + this);
        } else {
            return resourceLocation;
        }
    }

    public void setItemTag(ResourceLocation resourceLocation) {
        this.items = new MappedEntitySet<>(resourceLocation);
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof TagSlotDisplay)) {
            return false;
        }
        TagSlotDisplay tagSlotDisplay = (TagSlotDisplay) result;
        return this.items.equals(tagSlotDisplay.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.items);
    }

    @Override
    public String toString() {
        return "TagSlotDisplay{items=" + this.items + 125;
    }
}
