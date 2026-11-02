package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class EmptySlotDisplay extends SlotDisplay<EmptySlotDisplay> {

    public static final EmptySlotDisplay INSTANCE = new EmptySlotDisplay();

    private EmptySlotDisplay() {
        super(SlotDisplayTypes.EMPTY);
    }

    public static EmptySlotDisplay read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(PacketWrapper<?> packetWrapper, EmptySlotDisplay emptySlotDisplay) {}
}
