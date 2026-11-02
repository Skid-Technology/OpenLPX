package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class WithAnyPotionSlotDisplay extends SlotDisplay<WithAnyPotionSlotDisplay> {

    private final SlotDisplay<?> display;

    public WithAnyPotionSlotDisplay(SlotDisplay<?> slotDisplay) {
        super(SlotDisplayTypes.WITH_ANY_POTION);
        this.display = slotDisplay;
    }

    public static WithAnyPotionSlotDisplay read(PacketWrapper<?> packetWrapper) {
        return new WithAnyPotionSlotDisplay(SlotDisplay.read(packetWrapper));
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WithAnyPotionSlotDisplay withAnyPotionSlotDisplay) {
        SlotDisplay.write(packetWrapper, withAnyPotionSlotDisplay.display);
    }

    public SlotDisplay<?> getDisplay() {
        return this.display;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof WithAnyPotionSlotDisplay)) {
            return false;
        }
        WithAnyPotionSlotDisplay withAnyPotionSlotDisplay = (WithAnyPotionSlotDisplay) result;
        return this.display.equals(withAnyPotionSlotDisplay.display);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.display);
    }

    @Override
    public String toString() {
        return "WithAnyPotionSlotDisplay{display=" + this.display + 125;
    }
}
