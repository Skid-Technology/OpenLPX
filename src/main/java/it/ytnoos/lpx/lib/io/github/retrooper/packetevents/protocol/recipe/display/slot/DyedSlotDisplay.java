package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class DyedSlotDisplay extends SlotDisplay<DyedSlotDisplay> {

    private final SlotDisplay<?> source;

    private final SlotDisplay<?> target;

    public DyedSlotDisplay(SlotDisplay<?> slotDisplay, SlotDisplay<?> slotDisplay2) {
        super(SlotDisplayTypes.DYED);
        this.source = slotDisplay;
        this.target = slotDisplay2;
    }

    public static DyedSlotDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        SlotDisplay slotDisplay2 = SlotDisplay.read(packetWrapper);
        return new DyedSlotDisplay(slotDisplay, slotDisplay2);
    }

    public static void write(PacketWrapper<?> packetWrapper, DyedSlotDisplay dyedSlotDisplay) {
        SlotDisplay.write(packetWrapper, dyedSlotDisplay.source);
        SlotDisplay.write(packetWrapper, dyedSlotDisplay.target);
    }

    public SlotDisplay<?> getSource() {
        return this.source;
    }

    public SlotDisplay<?> getTarget() {
        return this.target;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof DyedSlotDisplay)) {
            return false;
        }
        DyedSlotDisplay dyedSlotDisplay = (DyedSlotDisplay) result;
        return !this.source.equals(dyedSlotDisplay.source)
                ? false
                : this.target.equals(dyedSlotDisplay.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.target);
    }

    @Override
    public String toString() {
        return "DyedSlotDisplay{source=" + this.source + ", target=" + this.target + 125;
    }
}
