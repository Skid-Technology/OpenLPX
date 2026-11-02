package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class ShapedCraftingRecipeDisplay extends RecipeDisplay<ShapedCraftingRecipeDisplay> {

    private int width;

    private int height;

    private List<SlotDisplay<?>> ingredients;

    private SlotDisplay<?> result;

    private SlotDisplay<?> craftingStation;

    public ShapedCraftingRecipeDisplay(
            int width,
            int height,
            List<SlotDisplay<?>> ingredients,
            SlotDisplay<?> slotDisplay,
            SlotDisplay<?> slotDisplay2) {
        super(RecipeDisplayTypes.CRAFTING_SHAPED);
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = slotDisplay;
        this.craftingStation = slotDisplay2;
    }

    public static ShapedCraftingRecipeDisplay read(PacketWrapper<?> packetWrapper) {
        int value = packetWrapper.readVarInt();
        int value2 = packetWrapper.readVarInt();
        List values = packetWrapper.readList(SlotDisplay::read);
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        return new ShapedCraftingRecipeDisplay(value, value2, values, slotDisplay, slotDisplay2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            ShapedCraftingRecipeDisplay shapedCraftingRecipeDisplay) {
        packetWrapper.writeVarInt(shapedCraftingRecipeDisplay.width);
        packetWrapper.writeVarInt(shapedCraftingRecipeDisplay.height);
        packetWrapper.writeList(shapedCraftingRecipeDisplay.ingredients, SlotDisplay::write);
        SlotDisplay.write(packetWrapper, shapedCraftingRecipeDisplay.result);
        SlotDisplay.write(packetWrapper, shapedCraftingRecipeDisplay.craftingStation);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
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
        } else if (!(result2 instanceof ShapedCraftingRecipeDisplay)) {
            return false;
        } else {
            ShapedCraftingRecipeDisplay shapedCraftingRecipeDisplay =
                    (ShapedCraftingRecipeDisplay) result2;
            if (this.width != shapedCraftingRecipeDisplay.width) {
                return false;
            } else if (this.height != shapedCraftingRecipeDisplay.height) {
                return false;
            } else if (!this.ingredients.equals(shapedCraftingRecipeDisplay.ingredients)) {
                return false;
            } else {
                return !this.result.equals(shapedCraftingRecipeDisplay.result)
                        ? false
                        : this.craftingStation.equals(shapedCraftingRecipeDisplay.craftingStation);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.width, this.height, this.ingredients, this.result, this.craftingStation);
    }

    @Override
    public String toString() {
        return "ShapedCraftingRecipeDisplay{width="
                + this.width
                + ", height="
                + this.height
                + ", ingredients="
                + this.ingredients
                + ", result="
                + this.result
                + ", craftingStation="
                + this.craftingStation
                + 125;
    }
}
