package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.UnknownNullability;

@Obsolete
public class SmithingRecipeData implements RecipeData {

    @UnknownNullability private final Ingredient template;

    private final Ingredient base;

    private final Ingredient addition;

    private final ItemStack result;

    @Deprecated
    public SmithingRecipeData(Ingredient ingredient, Ingredient ingredient2, ItemStack itemStack) {
        this(null, ingredient, ingredient2, itemStack);
    }

    public SmithingRecipeData(
            @UnknownNullability Ingredient ingredient,
            Ingredient ingredient2,
            Ingredient ingredient3,
            ItemStack itemStack) {
        this.template = ingredient;
        this.base = ingredient2;
        this.addition = ingredient3;
        this.result = itemStack;
    }

    public static SmithingRecipeData read(PacketWrapper<?> packetWrapper) {
        return read(packetWrapper, false);
    }

    public static SmithingRecipeData read(PacketWrapper<?> packetWrapper, boolean matches) {
        Ingredient ingredient =
                !packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20)
                                && (!packetWrapper
                                                .getServerVersion()
                                                .isNewerThanOrEquals(ServerVersion.V_1_19_4)
                                        || matches)
                        ? null
                        : Ingredient.read(packetWrapper);
        Ingredient ingredient2 = Ingredient.read(packetWrapper);
        Ingredient ingredient3 = Ingredient.read(packetWrapper);
        ItemStack itemStack = packetWrapper.readItemStack();
        return new SmithingRecipeData(ingredient, ingredient2, ingredient3, itemStack);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SmithingRecipeData smithingRecipeData) {
        write(packetWrapper, smithingRecipeData, false);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            SmithingRecipeData smithingRecipeData,
            boolean matches) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_20)
                || packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_4)
                        && !matches) {
            Ingredient.write(packetWrapper, smithingRecipeData.template);
        }
        Ingredient.write(packetWrapper, smithingRecipeData.base);
        Ingredient.write(packetWrapper, smithingRecipeData.addition);
        packetWrapper.writeItemStack(smithingRecipeData.result);
    }

    @UnknownNullability
    public Ingredient getTemplate() {
        return this.template;
    }

    public Ingredient getBase() {
        return this.base;
    }

    public Ingredient getAddition() {
        return this.addition;
    }

    public ItemStack getResult() {
        return this.result;
    }
}
