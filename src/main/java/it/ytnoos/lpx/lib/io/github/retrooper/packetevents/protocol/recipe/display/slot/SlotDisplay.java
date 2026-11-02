package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public abstract class SlotDisplay<T extends SlotDisplay<?>> {

    protected final SlotDisplayType<T> type;

    public SlotDisplay(SlotDisplayType<T> slotDisplayType) {
        this.type = slotDisplayType;
    }

    public static SlotDisplay<?> read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readMappedEntity(SlotDisplayTypes.getRegistry()).read(packetWrapper);
    }

    public static <T extends SlotDisplay<?>> void write(PacketWrapper<?> packetWrapper, T t) {
        packetWrapper.writeMappedEntity(t.getType());
        ((SlotDisplayType) t.getType()).write(packetWrapper, t);
    }

    public SlotDisplayType<T> getType() {
        return this.type;
    }
}
