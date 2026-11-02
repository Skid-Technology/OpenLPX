package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class AnyFuelSlotDisplay extends SlotDisplay<AnyFuelSlotDisplay> {

    public static final AnyFuelSlotDisplay INSTANCE = new AnyFuelSlotDisplay();

    private AnyFuelSlotDisplay() {
        super(SlotDisplayTypes.ANY_FUEL);
    }

    public static AnyFuelSlotDisplay read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(
            PacketWrapper<?> packetWrapper, AnyFuelSlotDisplay anyFuelSlotDisplay) {}
}
