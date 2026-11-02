package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class DefaultAttributeDisplay implements AttributeDisplay {

    public static final DefaultAttributeDisplay INSTANCE = new DefaultAttributeDisplay();

    private DefaultAttributeDisplay() {}

    public static DefaultAttributeDisplay read(PacketWrapper<?> packetWrapper) {
        return INSTANCE;
    }

    public static void write(
            PacketWrapper<?> packetWrapper, DefaultAttributeDisplay defaultAttributeDisplay) {}

    @Override
    public AttributeDisplayType<?> getType() {
        return AttributeDisplayTypes.DEFAULT;
    }
}
