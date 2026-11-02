package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class HiddenAttributeDisplay implements AttributeDisplay {

    public static final HiddenAttributeDisplay INSTANCE = new HiddenAttributeDisplay();

    private HiddenAttributeDisplay() {}

    public static HiddenAttributeDisplay read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(
            PacketWrapper<?> packetWrapper, HiddenAttributeDisplay hiddenAttributeDisplay) {}

    @Override
    public AttributeDisplayType<?> getType() {
        return AttributeDisplayTypes.HIDDEN;
    }
}
