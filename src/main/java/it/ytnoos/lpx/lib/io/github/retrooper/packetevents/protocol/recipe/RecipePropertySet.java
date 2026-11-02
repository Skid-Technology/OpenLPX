package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.LinkedHashSet;
import java.util.Set;

public final class RecipePropertySet {

    public static final ResourceLocation SMITHING_BASE =
            ResourceLocation.minecraft("smithing_base");

    public static final ResourceLocation SMITHING_TEMPLATE =
            ResourceLocation.minecraft("smithing_template");

    public static final ResourceLocation SMITHING_ADDITION =
            ResourceLocation.minecraft("smithing_addition");

    public static final ResourceLocation FURNACE_INPUT =
            ResourceLocation.minecraft("furnace_input");

    public static final ResourceLocation BLAST_FURNACE_INPUT =
            ResourceLocation.minecraft("blast_furnace_input");

    public static final ResourceLocation SMOKER_INPUT = ResourceLocation.minecraft("smoker_input");

    public static final ResourceLocation CAMPFIRE_INPUT =
            ResourceLocation.minecraft("campfire_input");

    private Set<ItemType> items;

    public RecipePropertySet(Set<ItemType> items) {
        this.items = items;
    }

    public static RecipePropertySet read(PacketWrapper<?> packetWrapper) {
        LinkedHashSet linkedHashSet =
                packetWrapper.readCollection(
                        LinkedHashSet::new,
                        result -> result.readMappedEntity(ItemTypes.getRegistry()));
        return new RecipePropertySet(linkedHashSet);
    }

    public static void write(PacketWrapper<?> packetWrapper, RecipePropertySet recipePropertySet) {
        packetWrapper.writeCollection(recipePropertySet.items, PacketWrapper::writeMappedEntity);
    }

    public Set<ItemType> getItems() {
        return this.items;
    }

    public void setItems(Set<ItemType> items) {
        this.items = items;
    }
}
