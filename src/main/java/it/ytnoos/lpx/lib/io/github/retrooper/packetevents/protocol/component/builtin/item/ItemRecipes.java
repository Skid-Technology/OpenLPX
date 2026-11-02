package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemRecipes {

    private List<ResourceLocation> recipes;

    public ItemRecipes(List<ResourceLocation> recipes) {
        this.recipes = recipes;
    }

    public static ItemRecipes read(PacketWrapper<?> packetWrapper) {
        NBTList nBTList;
        int size2 = (nBTList = (NBTList) packetWrapper.readNBTRaw()).size();
        ArrayList<ResourceLocation> values = new ArrayList<>(size2);
        for (int index = 0; index < size2; index++) {
            NBTString nBTString = (NBTString) nBTList.getTag(index);
            values.add(new ResourceLocation(nBTString.getValue()));
        }
        return new ItemRecipes(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemRecipes itemRecipes) {
        NBTList nBTList = NBTList.createStringList();
        for (ResourceLocation resourceLocation : itemRecipes.recipes) {
            nBTList.addTag(new NBTString(resourceLocation.toString()));
        }
        packetWrapper.writeNBTRaw(nBTList);
    }

    public void addRecipe(ResourceLocation resourceLocation) {
        this.recipes.add(resourceLocation);
    }

    public void removeRecipe(ResourceLocation resourceLocation) {
        this.recipes.remove(resourceLocation);
    }

    public List<ResourceLocation> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<ResourceLocation> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemRecipes)) {
            return false;
        }
        ItemRecipes itemRecipes = (ItemRecipes) result;
        return this.recipes.equals(itemRecipes.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.recipes);
    }
}
