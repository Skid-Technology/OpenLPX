package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

import java.util.Objects;

@NullMarked
public class OnlyWithComponentSlotDisplay extends SlotDisplay<OnlyWithComponentSlotDisplay> {

    private final SlotDisplay<?> source;

    private final ComponentType<?> component;

    public OnlyWithComponentSlotDisplay(
            SlotDisplay<?> slotDisplay, ComponentType<?> componentType) {
        super(SlotDisplayTypes.ONLY_WITH_COMPONENT);
        this.source = slotDisplay;
        this.component = componentType;
    }

    public static OnlyWithComponentSlotDisplay read(PacketWrapper<?> packetWrapper) {
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        ComponentType<?> componentType =
                packetWrapper.readMappedEntity(ComponentTypes.getRegistry());
        return new OnlyWithComponentSlotDisplay(slotDisplay, componentType);
    }

    public static void write(
            PacketWrapper<?> packetWrapper,
            OnlyWithComponentSlotDisplay onlyWithComponentSlotDisplay) {
        SlotDisplay.write(packetWrapper, onlyWithComponentSlotDisplay.source);
        packetWrapper.writeMappedEntity(onlyWithComponentSlotDisplay.component);
    }

    public SlotDisplay<?> getSource() {
        return this.source;
    }

    public ComponentType<?> getComponent() {
        return this.component;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof OnlyWithComponentSlotDisplay)) {
            return false;
        }
        OnlyWithComponentSlotDisplay onlyWithComponentSlotDisplay =
                (OnlyWithComponentSlotDisplay) result;
        return !this.source.equals(onlyWithComponentSlotDisplay.source)
                ? false
                : this.component.equals(onlyWithComponentSlotDisplay.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.component);
    }

    @Override
    public String toString() {
        return "OnlyWithComponentSlotDisplay{source="
                + this.source
                + ", component="
                + this.component
                + 125;
    }
}
