package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.CookingCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class CookedRecipeData implements RecipeData {

    private final String group;

    private final CookingCategory category;

    private final Ingredient ingredient;

    private final ItemStack result;

    private final float experience;

    private final int cookingTime;

    @Deprecated
    public CookedRecipeData(
            String text, Ingredient ingredient, ItemStack itemStack, float value, int value2) {
        this(text, CookingCategory.MISC, ingredient, itemStack, value, value2);
    }

    public CookedRecipeData(
            String group,
            CookingCategory cookingCategory,
            Ingredient ingredient2,
            ItemStack itemStack,
            float experience,
            int cookingTime) {
        this.group = group;
        this.category = cookingCategory;
        this.ingredient = ingredient2;
        this.result = itemStack;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    public static CookedRecipeData read(PacketWrapper<?> packetWrapper) {
        String text = packetWrapper.readString();
        CookingCategory cookingCategory =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)
                        ? packetWrapper.readEnum(CookingCategory.values())
                        : CookingCategory.MISC;
        Ingredient ingredient = Ingredient.read(packetWrapper);
        ItemStack itemStack = packetWrapper.readItemStack();
        float value = packetWrapper.readFloat();
        int value2 = packetWrapper.readVarInt();
        return new CookedRecipeData(text, cookingCategory, ingredient, itemStack, value, value2);
    }

    public static void write(PacketWrapper<?> packetWrapper, CookedRecipeData cookedRecipeData) {
        packetWrapper.writeString(cookedRecipeData.group);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            packetWrapper.writeEnum(cookedRecipeData.category);
        }
        Ingredient.write(packetWrapper, cookedRecipeData.ingredient);
        packetWrapper.writeItemStack(cookedRecipeData.result);
        packetWrapper.writeFloat(cookedRecipeData.experience);
        packetWrapper.writeVarInt(cookedRecipeData.cookingTime);
    }

    public String getGroup() {
        return this.group;
    }

    public CookingCategory getCategory() {
        return this.category;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }
}
