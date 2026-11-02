package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ShapelessCraftingRecipeDisplay extends RecipeDisplay<ShapelessCraftingRecipeDisplay> {

    private List<SlotDisplay<?>> ingredients;

    private SlotDisplay<?> result;

    private SlotDisplay<?> craftingStation;

    public ShapelessCraftingRecipeDisplay(
            List<SlotDisplay<?>> ingredients,
            SlotDisplay<?> slotDisplay,
            SlotDisplay<?> slotDisplay2) {
        super(RecipeDisplayTypes.CRAFTING_SHAPELESS);
        this.ingredients = ingredients;
        this.result = slotDisplay;
        this.craftingStation = slotDisplay2;
    }

    public static ShapelessCraftingRecipeDisplay read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(SlotDisplay::read);
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        return new ShapelessCraftingRecipeDisplay(values, slotDisplay, slotDisplay2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            ShapelessCraftingRecipeDisplay shapelessCraftingRecipeDisplay) {
        packetWrapper.writeList(shapelessCraftingRecipeDisplay.ingredients, SlotDisplay::write);
        SlotDisplay.write(packetWrapper, shapelessCraftingRecipeDisplay.result);
        SlotDisplay.write(packetWrapper, shapelessCraftingRecipeDisplay.craftingStation);
    }

    public List<SlotDisplay<?>> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<SlotDisplay<?>> ingredients) {
        this.ingredients = ingredients;
    }

    public SlotDisplay<?> getResult() {
        return this.result;
    }

    public void setResult(SlotDisplay<?> slotDisplay) {
        this.result = slotDisplay;
    }

    public SlotDisplay<?> getCraftingStation() {
        return this.craftingStation;
    }

    public void setCraftingStation(SlotDisplay<?> slotDisplay) {
        this.craftingStation = slotDisplay;
    }

    @Override
    public boolean equals(Object result2) {
        if (this == result2) {
            return true;
        } else if (!(result2 instanceof ShapelessCraftingRecipeDisplay)) {
            return false;
        } else {
            ShapelessCraftingRecipeDisplay shapelessCraftingRecipeDisplay =
                    (ShapelessCraftingRecipeDisplay) result2;
            if (!this.ingredients.equals(shapelessCraftingRecipeDisplay.ingredients)) {
                return false;
            } else {
                return !this.result.equals(shapelessCraftingRecipeDisplay.result)
                        ? false
                        : this.craftingStation.equals(
                                shapelessCraftingRecipeDisplay.craftingStation);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ingredients, this.result, this.craftingStation);
    }

    @Override
    public String toString() {
        return "ShapelessCraftingRecipeDisplay{ingredients="
                + this.ingredients
                + ", result="
                + this.result
                + ", craftingStation="
                + this.craftingStation
                + 125;
    }
}
