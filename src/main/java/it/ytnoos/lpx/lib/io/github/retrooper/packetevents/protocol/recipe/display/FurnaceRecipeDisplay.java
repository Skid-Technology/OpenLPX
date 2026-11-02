package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class FurnaceRecipeDisplay extends RecipeDisplay<FurnaceRecipeDisplay> {

    private SlotDisplay<?> ingredient;

    private SlotDisplay<?> fuel;

    private SlotDisplay<?> result;

    private SlotDisplay<?> craftingStation;

    private int duration;

    private float experience;

    public FurnaceRecipeDisplay(
            SlotDisplay<?> slotDisplay,
            SlotDisplay<?> slotDisplay2,
            SlotDisplay<?> slotDisplay3,
            SlotDisplay<?> slotDisplay4,
            int duration,
            float experience) {
        super(RecipeDisplayTypes.FURNACE);
        this.ingredient = slotDisplay;
        this.fuel = slotDisplay2;
        this.result = slotDisplay3;
        this.craftingStation = slotDisplay4;
        this.duration = duration;
        this.experience = experience;
    }

    public static FurnaceRecipeDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay3 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay4 = SlotDisplay.read(packetWrapper);
        int value = packetWrapper.readVarInt();
        float value2 = packetWrapper.readFloat();
        return new FurnaceRecipeDisplay(
                slotDisplay, slotDisplay2, slotDisplay3, slotDisplay4, value, value2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, FurnaceRecipeDisplay furnaceRecipeDisplay) {
        SlotDisplay.write(packetWrapper, furnaceRecipeDisplay.ingredient);
        SlotDisplay.write(packetWrapper, furnaceRecipeDisplay.fuel);
        SlotDisplay.write(packetWrapper, furnaceRecipeDisplay.result);
        SlotDisplay.write(packetWrapper, furnaceRecipeDisplay.craftingStation);
        packetWrapper.writeVarInt(furnaceRecipeDisplay.duration);
        packetWrapper.writeFloat(furnaceRecipeDisplay.experience);
    }

    public SlotDisplay<?> getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(SlotDisplay<?> slotDisplay) {
        this.ingredient = slotDisplay;
    }

    public SlotDisplay<?> getFuel() {
        return this.fuel;
    }

    public void setFuel(SlotDisplay<?> slotDisplay) {
        this.fuel = slotDisplay;
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

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getExperience() {
        return this.experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object result2) {
        if (this == result2) {
            return true;
        } else if (!(result2 instanceof FurnaceRecipeDisplay)) {
            return false;
        } else {
            FurnaceRecipeDisplay furnaceRecipeDisplay = (FurnaceRecipeDisplay) result2;
            if (this.duration != furnaceRecipeDisplay.duration) {
                return false;
            } else if (Float.compare(furnaceRecipeDisplay.experience, this.experience) != 0) {
                return false;
            } else if (!this.ingredient.equals(furnaceRecipeDisplay.ingredient)) {
                return false;
            } else if (!this.fuel.equals(furnaceRecipeDisplay.fuel)) {
                return false;
            } else {
                return !this.result.equals(furnaceRecipeDisplay.result)
                        ? false
                        : this.craftingStation.equals(furnaceRecipeDisplay.craftingStation);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.ingredient,
                this.fuel,
                this.result,
                this.craftingStation,
                this.duration,
                this.experience);
    }

    @Override
    public String toString() {
        return "FurnaceRecipeDisplay{ingredient="
                + this.ingredient
                + ", fuel="
                + this.fuel
                + ", result="
                + this.result
                + ", craftingStation="
                + this.craftingStation
                + ", duration="
                + this.duration
                + ", experience="
                + this.experience
                + '}';
    }
}
