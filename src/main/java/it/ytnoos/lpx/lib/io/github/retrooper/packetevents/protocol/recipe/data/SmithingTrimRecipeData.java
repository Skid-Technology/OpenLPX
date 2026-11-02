package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.Ingredient;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

@Obsolete
public class SmithingTrimRecipeData implements RecipeData {

    private final Ingredient template;

    private final Ingredient base;

    private final Ingredient addition;

    public SmithingTrimRecipeData(
            Ingredient ingredient, Ingredient ingredient2, Ingredient ingredient3) {
        this.template = ingredient;
        this.base = ingredient2;
        this.addition = ingredient3;
    }

    public static SmithingTrimRecipeData read(PacketWrapper<?> packetWrapper) {
        Ingredient ingredient = Ingredient.read(packetWrapper);
        Ingredient ingredient2 = Ingredient.read(packetWrapper);
        Ingredient ingredient3 = Ingredient.read(packetWrapper);
        return new SmithingTrimRecipeData(ingredient, ingredient2, ingredient3);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SmithingTrimRecipeData smithingTrimRecipeData) {
        Ingredient.write(packetWrapper, smithingTrimRecipeData.template);
        Ingredient.write(packetWrapper, smithingTrimRecipeData.base);
        Ingredient.write(packetWrapper, smithingTrimRecipeData.addition);
    }

    public Ingredient getTemplate() {
        return this.template;
    }

    public Ingredient getBase() {
        return this.base;
    }

    public Ingredient getAddition() {
        return this.addition;
    }
}
