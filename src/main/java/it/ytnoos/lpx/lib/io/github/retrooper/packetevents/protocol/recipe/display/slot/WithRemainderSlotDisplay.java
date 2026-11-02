package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class WithRemainderSlotDisplay extends SlotDisplay<WithRemainderSlotDisplay> {

    private SlotDisplay<?> input;

    private SlotDisplay<?> remainder;

    public WithRemainderSlotDisplay(SlotDisplay<?> slotDisplay, SlotDisplay<?> slotDisplay2) {
        super(SlotDisplayTypes.WITH_REMAINDER);
        this.input = slotDisplay;
        this.remainder = slotDisplay2;
    }

    public static WithRemainderSlotDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        return new WithRemainderSlotDisplay(slotDisplay, slotDisplay2);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, WithRemainderSlotDisplay withRemainderSlotDisplay) {
        SlotDisplay.write(packetWrapper, withRemainderSlotDisplay.input);
        SlotDisplay.write(packetWrapper, withRemainderSlotDisplay.remainder);
    }

    public SlotDisplay<?> getInput() {
        return this.input;
    }

    public void setInput(SlotDisplay<?> slotDisplay) {
        this.input = slotDisplay;
    }

    public SlotDisplay<?> getRemainder() {
        return this.remainder;
    }

    public void setRemainder(SlotDisplay<?> slotDisplay) {
        this.remainder = slotDisplay;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof WithRemainderSlotDisplay)) {
            return false;
        }
        WithRemainderSlotDisplay withRemainderSlotDisplay = (WithRemainderSlotDisplay) result;
        return !this.input.equals(withRemainderSlotDisplay.input)
                ? false
                : this.remainder.equals(withRemainderSlotDisplay.remainder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.input, this.remainder);
    }

    @Override
    public String toString() {
        return "WithRemainderSlotDisplay{input="
                + this.input
                + ", remainder="
                + this.remainder
                + 125;
    }
}
