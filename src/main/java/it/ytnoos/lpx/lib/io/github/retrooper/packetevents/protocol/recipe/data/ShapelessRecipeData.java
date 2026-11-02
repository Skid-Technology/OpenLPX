package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.CraftingCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class ShapelessRecipeData implements RecipeData {

    private final String group;

    private final CraftingCategory category;

    private final Ingredient[] ingredients;

    private final ItemStack result;

    @Deprecated
    public ShapelessRecipeData(String text, Ingredient[] ingredients, ItemStack itemStack) {
        this(text, CraftingCategory.MISC, ingredients, itemStack);
    }

    public ShapelessRecipeData(
            String group,
            CraftingCategory craftingCategory,
            Ingredient[] ingredients2,
            ItemStack itemStack) {
        this.group = group;
        this.category = craftingCategory;
        this.ingredients = ingredients2;
        this.result = itemStack;
    }

    public static ShapelessRecipeData read(PacketWrapper<?> packetWrapper) {
        String text = packetWrapper.readString();
        CraftingCategory craftingCategory =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)
                        ? packetWrapper.readEnum(CraftingCategory.values())
                        : CraftingCategory.MISC;
        Ingredient[] ingredients = packetWrapper.readArray(Ingredient::read, Ingredient.class);
        ItemStack itemStack = packetWrapper.readItemStack();
        return new ShapelessRecipeData(text, craftingCategory, ingredients, itemStack);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ShapelessRecipeData shapelessRecipeData) {
        packetWrapper.writeString(shapelessRecipeData.group);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            packetWrapper.writeEnum(shapelessRecipeData.category);
        }
        packetWrapper.writeArray(shapelessRecipeData.ingredients, Ingredient::write);
        packetWrapper.writeItemStack(shapelessRecipeData.result);
    }

    public String getGroup() {
        return this.group;
    }

    public CraftingCategory getCategory() {
        return this.category;
    }

    public Ingredient[] getIngredients() {
        return this.ingredients;
    }

    public ItemStack getResult() {
        return this.result;
    }
}
