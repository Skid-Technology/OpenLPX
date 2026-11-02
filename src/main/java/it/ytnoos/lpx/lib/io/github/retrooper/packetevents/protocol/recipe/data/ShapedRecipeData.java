package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.CraftingCategory;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class ShapedRecipeData implements RecipeData {

    private final String group;

    private final CraftingCategory category;

    private final ItemStack result;

    private final boolean showNotification;

    private final int width;

    private final int height;

    private final Ingredient[] ingredients;

    @Deprecated
    public ShapedRecipeData(
            int value, int value2, String text, Ingredient[] ingredients, ItemStack itemStack) {
        this(text, CraftingCategory.MISC, itemStack, true, value, value2, ingredients);
    }

    public ShapedRecipeData(
            String group,
            CraftingCategory craftingCategory,
            ItemStack itemStack,
            boolean showNotification,
            int width,
            int height,
            Ingredient[] ingredients2) {
        if (width * height != ingredients2.length) {
            throw new IllegalArgumentException(
                    "Illegal ingredients length, found "
                            + ingredients2.length
                            + " but expected "
                            + width
                            + " * "
                            + height);
        }
        this.group = group;
        this.category = craftingCategory;
        this.result = itemStack;
        this.showNotification = showNotification;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients2;
    }

    public static ShapedRecipeData read(PacketWrapper<?> packetWrapper) {
        int value = 0;
        int value2 = 0;
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_3)) {
            value = packetWrapper.readVarInt();
            value2 = packetWrapper.readVarInt();
        }
        String text = packetWrapper.readString();
        CraftingCategory craftingCategory =
                packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)
                        ? packetWrapper.readEnum(CraftingCategory.values())
                        : CraftingCategory.MISC;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            value = packetWrapper.readVarInt();
            value2 = packetWrapper.readVarInt();
        }
        Ingredient[] ingredients = new Ingredient[value * value2];
        for (int index = 0; index < ingredients.length; index++) {
            ingredients[index] = Ingredient.read(packetWrapper);
        }
        ItemStack itemStack = packetWrapper.readItemStack();
        boolean matches = true;
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_4)) {
            matches = packetWrapper.readBoolean();
        }
        return new ShapedRecipeData(
                text, craftingCategory, itemStack, matches, value, value2, ingredients);
    }

    public static void write(PacketWrapper<?> packetWrapper, ShapedRecipeData shapedRecipeData) {
        if (packetWrapper.getServerVersion().isOlderThan(ServerVersion.V_1_20_3)) {
            packetWrapper.writeVarInt(shapedRecipeData.width);
            packetWrapper.writeVarInt(shapedRecipeData.height);
        }
        packetWrapper.writeString(shapedRecipeData.group);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
            packetWrapper.writeEnum(shapedRecipeData.category);
        }
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            packetWrapper.writeVarInt(shapedRecipeData.width);
            packetWrapper.writeVarInt(shapedRecipeData.height);
        }
        Ingredient[] ingredients2 = shapedRecipeData.ingredients;
        int value = shapedRecipeData.ingredients.length;
        for (int index = 0; index < value; index++) {
            Ingredient ingredient = ingredients2[index];
            Ingredient.write(packetWrapper, ingredient);
        }
        packetWrapper.writeItemStack(shapedRecipeData.result);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_4)) {
            packetWrapper.writeBoolean(shapedRecipeData.showNotification);
        }
    }

    public String getGroup() {
        return this.group;
    }

    public CraftingCategory getCategory() {
        return this.category;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public boolean isShowNotification() {
        return this.showNotification;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Ingredient[] getIngredients() {
        return this.ingredients;
    }
}
