package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.trimpattern.TrimPattern;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.Objects;

public class SmithingTrimSlotDisplay extends SlotDisplay<SmithingTrimSlotDisplay> {

    private SlotDisplay<?> base;

    private SlotDisplay<?> material;

    private TrimPattern trimPattern;

    @Obsolete private SlotDisplay<?> pattern;

    public SmithingTrimSlotDisplay(
            SlotDisplay<?> slotDisplay, SlotDisplay<?> slotDisplay2, TrimPattern trimPattern2) {
        super(SlotDisplayTypes.SMITHING_TRIM);
        this.base = slotDisplay;
        this.material = slotDisplay2;
        this.trimPattern = trimPattern2;
    }

    @Obsolete
    public SmithingTrimSlotDisplay(
            SlotDisplay<?> slotDisplay, SlotDisplay<?> slotDisplay2, SlotDisplay<?> slotDisplay3) {
        super(SlotDisplayTypes.SMITHING_TRIM);
        this.base = slotDisplay;
        this.material = slotDisplay2;
        this.pattern = slotDisplay3;
    }

    public static SmithingTrimSlotDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            TrimPattern trimPattern = TrimPattern.read(packetWrapper);
            return new SmithingTrimSlotDisplay(slotDisplay, slotDisplay2, trimPattern);
        } else {
            SlotDisplay slotDisplay3 = SlotDisplay.read(packetWrapper);
            return new SmithingTrimSlotDisplay(slotDisplay, slotDisplay2, slotDisplay3);
        }
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SmithingTrimSlotDisplay smithingTrimSlotDisplay) {
        SlotDisplay.write(packetWrapper, smithingTrimSlotDisplay.base);
        SlotDisplay.write(packetWrapper, smithingTrimSlotDisplay.material);
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_5)) {
            TrimPattern.write(packetWrapper, smithingTrimSlotDisplay.trimPattern);
        } else {
            SlotDisplay.write(packetWrapper, smithingTrimSlotDisplay.pattern);
        }
    }

    public SlotDisplay<?> getBase() {
        return this.base;
    }

    public void setBase(SlotDisplay<?> slotDisplay) {
        this.base = slotDisplay;
    }

    public SlotDisplay<?> getMaterial() {
        return this.material;
    }

    public void setMaterial(SlotDisplay<?> slotDisplay) {
        this.material = slotDisplay;
    }

    @Obsolete
    public SlotDisplay<?> getPattern() {
        return this.pattern;
    }

    @Obsolete
    public void setPattern(SlotDisplay<?> slotDisplay) {
        this.pattern = slotDisplay;
    }

    public TrimPattern getTrimPattern() {
        return this.trimPattern;
    }

    public void setTrimPattern(TrimPattern trimPattern2) {
        this.trimPattern = trimPattern2;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof SmithingTrimSlotDisplay)) {
            return false;
        } else {
            SmithingTrimSlotDisplay smithingTrimSlotDisplay = (SmithingTrimSlotDisplay) result;
            if (!this.base.equals(smithingTrimSlotDisplay.base)) {
                return false;
            } else if (!this.material.equals(smithingTrimSlotDisplay.material)) {
                return false;
            } else {
                return !Objects.equals(this.pattern, smithingTrimSlotDisplay.pattern)
                        ? false
                        : Objects.equals(this.trimPattern, smithingTrimSlotDisplay.trimPattern);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.base, this.material, this.pattern, this.trimPattern);
    }

    @Override
    public String toString() {
        return "SmithingTrimSlotDisplay{base="
                + this.base
                + ", material="
                + this.material
                + ", trimPattern="
                + this.trimPattern
                + ", pattern="
                + this.pattern
                + 125;
    }
}
