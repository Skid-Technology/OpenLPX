package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class SmithingRecipeDisplay extends RecipeDisplay<SmithingRecipeDisplay> {

    private SlotDisplay<?> template;

    private SlotDisplay<?> base;

    private SlotDisplay<?> addition;

    private SlotDisplay<?> result;

    private SlotDisplay<?> craftingStation;

    public SmithingRecipeDisplay(
            SlotDisplay<?> slotDisplay,
            SlotDisplay<?> slotDisplay2,
            SlotDisplay<?> slotDisplay3,
            SlotDisplay<?> slotDisplay4,
            SlotDisplay<?> slotDisplay5) {
        super(RecipeDisplayTypes.SMITHING);
        this.template = slotDisplay;
        this.base = slotDisplay2;
        this.addition = slotDisplay3;
        this.result = slotDisplay4;
        this.craftingStation = slotDisplay5;
    }

    public static SmithingRecipeDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay3 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay4 = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay5 = SlotDisplay.read(packetWrapper);
        return new SmithingRecipeDisplay(
                slotDisplay, slotDisplay2, slotDisplay3, slotDisplay4, slotDisplay5);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SmithingRecipeDisplay smithingRecipeDisplay) {
        SlotDisplay.write(packetWrapper, smithingRecipeDisplay.template);
        SlotDisplay.write(packetWrapper, smithingRecipeDisplay.base);
        SlotDisplay.write(packetWrapper, smithingRecipeDisplay.addition);
        SlotDisplay.write(packetWrapper, smithingRecipeDisplay.result);
        SlotDisplay.write(packetWrapper, smithingRecipeDisplay.craftingStation);
    }

    public SlotDisplay<?> getTemplate() {
        return this.template;
    }

    public void setTemplate(SlotDisplay<?> slotDisplay) {
        this.template = slotDisplay;
    }

    public SlotDisplay<?> getBase() {
        return this.base;
    }

    public void setBase(SlotDisplay<?> slotDisplay) {
        this.base = slotDisplay;
    }

    public SlotDisplay<?> getAddition() {
        return this.addition;
    }

    public void setAddition(SlotDisplay<?> slotDisplay) {
        this.addition = slotDisplay;
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
        } else if (!(result2 instanceof SmithingRecipeDisplay)) {
            return false;
        } else {
            SmithingRecipeDisplay smithingRecipeDisplay = (SmithingRecipeDisplay) result2;
            if (!this.template.equals(smithingRecipeDisplay.template)) {
                return false;
            } else if (!this.base.equals(smithingRecipeDisplay.base)) {
                return false;
            } else if (!this.addition.equals(smithingRecipeDisplay.addition)) {
                return false;
            } else {
                return !this.result.equals(smithingRecipeDisplay.result)
                        ? false
                        : this.craftingStation.equals(smithingRecipeDisplay.craftingStation);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.template, this.base, this.addition, this.result, this.craftingStation);
    }

    @Override
    public String toString() {
        return "SmithingRecipeDisplay{template="
                + this.template
                + ", base="
                + this.base
                + ", addition="
                + this.addition
                + ", result="
                + this.result
                + ", craftingStation="
                + this.craftingStation
                + 125;
    }
}
