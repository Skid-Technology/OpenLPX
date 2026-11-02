package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.attribute;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class OverrideAttributeDisplay implements AttributeDisplay {

    private final Component component;

    public OverrideAttributeDisplay(Component component2) {
        this.component = component2;
    }

    public static OverrideAttributeDisplay read(PacketWrapper<?> packetWrapper) {
        return new OverrideAttributeDisplay(packetWrapper.readComponent());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, OverrideAttributeDisplay overrideAttributeDisplay) {
        packetWrapper.writeComponent(overrideAttributeDisplay.component);
    }

    @Override
    public AttributeDisplayType<?> getType() {
        return AttributeDisplayTypes.OVERRIDE;
    }

    public Component getComponent() {
        return this.component;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof OverrideAttributeDisplay)) {
            return false;
        }
        OverrideAttributeDisplay overrideAttributeDisplay = (OverrideAttributeDisplay) result;
        return this.component.equals(overrideAttributeDisplay.component);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.component);
    }
}
