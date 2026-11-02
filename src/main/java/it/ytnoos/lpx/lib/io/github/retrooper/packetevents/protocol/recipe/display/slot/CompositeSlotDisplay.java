package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;
import java.util.Objects;

public class CompositeSlotDisplay extends SlotDisplay<CompositeSlotDisplay> {

    private List<SlotDisplay<?>> contents;

    public CompositeSlotDisplay(List<SlotDisplay<?>> contents) {
        super(SlotDisplayTypes.COMPOSITE);
        this.contents = contents;
    }

    public static CompositeSlotDisplay read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(SlotDisplay::read);
        return new CompositeSlotDisplay(values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, CompositeSlotDisplay compositeSlotDisplay) {
        packetWrapper.writeList(compositeSlotDisplay.contents, SlotDisplay::write);
    }

    public List<SlotDisplay<?>> getContents() {
        return this.contents;
    }

    public void setContents(List<SlotDisplay<?>> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof CompositeSlotDisplay)) {
            return false;
        }
        CompositeSlotDisplay compositeSlotDisplay = (CompositeSlotDisplay) result;
        return this.contents.equals(compositeSlotDisplay.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.contents);
    }

    @Override
    public String toString() {
        return "CompositeSlotDisplay{contents=" + this.contents + 125;
    }
}
