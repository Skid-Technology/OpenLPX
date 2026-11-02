package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class StonecutterRecipeDisplay extends RecipeDisplay<StonecutterRecipeDisplay> {

    private SlotDisplay<?> input;

    private SlotDisplay<?> result;

    private SlotDisplay<?> craftingStation;

    public StonecutterRecipeDisplay(
            SlotDisplay<?> slotDisplay, SlotDisplay<?> slotDisplay2, SlotDisplay<?> slotDisplay3) {
        super(RecipeDisplayTypes.STONECUTTER);
        this.input = slotDisplay;
        this.result = slotDisplay2;
        this.craftingStation = slotDisplay3;
    }

    public static StonecutterRecipeDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay3 = SlotDisplay.read(packetWrapper);
        return new StonecutterRecipeDisplay(slotDisplay, slotDisplay2, slotDisplay3);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, StonecutterRecipeDisplay stonecutterRecipeDisplay) {
        SlotDisplay.write(packetWrapper, stonecutterRecipeDisplay.input);
        SlotDisplay.write(packetWrapper, stonecutterRecipeDisplay.result);
        SlotDisplay.write(packetWrapper, stonecutterRecipeDisplay.craftingStation);
    }

    public SlotDisplay<?> getInput() {
        return this.input;
    }

    public void setInput(SlotDisplay<?> slotDisplay) {
        this.input = slotDisplay;
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
        } else if (!(result2 instanceof StonecutterRecipeDisplay)) {
            return false;
        } else {
            StonecutterRecipeDisplay stonecutterRecipeDisplay = (StonecutterRecipeDisplay) result2;
            if (!this.input.equals(stonecutterRecipeDisplay.input)) {
                return false;
            } else {
                return !this.result.equals(stonecutterRecipeDisplay.result)
                        ? false
                        : this.craftingStation.equals(stonecutterRecipeDisplay.craftingStation);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.input, this.result, this.craftingStation);
    }

    @Override
    public String toString() {
        return "StonecutterRecipeDisplay{input="
                + this.input
                + ", result="
                + this.result
                + ", craftingStation="
                + this.craftingStation
                + 125;
    }
}
