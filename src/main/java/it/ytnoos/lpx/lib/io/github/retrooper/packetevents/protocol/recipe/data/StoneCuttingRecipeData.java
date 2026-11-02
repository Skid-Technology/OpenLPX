package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class StoneCuttingRecipeData implements RecipeData {

    private final String group;

    private final Ingredient ingredient;

    private final ItemStack result;

    public StoneCuttingRecipeData(String group, Ingredient ingredient2, ItemStack itemStack) {
        this.group = group;
        this.ingredient = ingredient2;
        this.result = itemStack;
    }

    public static StoneCuttingRecipeData read(PacketWrapper<?> packetWrapper) {
        String text = packetWrapper.readString();
        Ingredient ingredient = Ingredient.read(packetWrapper);
        ItemStack itemStack = packetWrapper.readItemStack();
        return new StoneCuttingRecipeData(text, ingredient, itemStack);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, StoneCuttingRecipeData stoneCuttingRecipeData) {
        packetWrapper.writeString(stoneCuttingRecipeData.group);
        Ingredient.write(packetWrapper, stoneCuttingRecipeData.ingredient);
        packetWrapper.writeItemStack(stoneCuttingRecipeData.result);
    }

    public String getGroup() {
        return this.group;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }
}
