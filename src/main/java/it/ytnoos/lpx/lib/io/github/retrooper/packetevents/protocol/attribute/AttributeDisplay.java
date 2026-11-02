package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface AttributeDisplay {

    static AttributeDisplay read(PacketWrapper<?> packetWrapper) {
        return packetWrapper
                .readMappedEntity(AttributeDisplayTypes.getRegistry())
                .read(packetWrapper);
    }

    static void write(PacketWrapper<?> packetWrapper, AttributeDisplay attributeDisplay) {
        packetWrapper.writeMappedEntity(attributeDisplay.getType());
        ((AttributeDisplayType<AttributeDisplay>) attributeDisplay.getType())
                .write(packetWrapper, attributeDisplay);
    }

    AttributeDisplayType<?> getType();
}
