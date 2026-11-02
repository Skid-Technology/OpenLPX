package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.ComponentTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class ItemTooltipDisplay {

    private boolean hideTooltip;

    private Set<ComponentType<?>> hiddenComponents;

    public ItemTooltipDisplay(boolean hideTooltip, Set<ComponentType<?>> hiddenComponents) {
        this.hideTooltip = hideTooltip;
        this.hiddenComponents = hiddenComponents;
    }

    public static ItemTooltipDisplay read(PacketWrapper<?> packetWrapper) {
        boolean matches = packetWrapper.readBoolean();
        Set values =
                packetWrapper.readCollection(
                        LinkedHashSet::new,
                        result -> result.readMappedEntity(ComponentTypes.getRegistry()));
        return new ItemTooltipDisplay(matches, values);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemTooltipDisplay itemTooltipDisplay) {
        packetWrapper.writeBoolean(itemTooltipDisplay.hideTooltip);
        packetWrapper.writeCollection(
                itemTooltipDisplay.hiddenComponents, PacketWrapper::writeMappedEntity);
    }

    public boolean isHideTooltip() {
        return this.hideTooltip;
    }

    public void setHideTooltip(boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
    }

    public Set<ComponentType<?>> getHiddenComponents() {
        return this.hiddenComponents;
    }

    public void setHiddenComponents(Set<ComponentType<?>> hiddenComponents) {
        this.hiddenComponents = hiddenComponents;
    }

    @Override
    public boolean equals(Object result) {
        if (!(result instanceof ItemTooltipDisplay)) {
            return false;
        }
        ItemTooltipDisplay itemTooltipDisplay = (ItemTooltipDisplay) result;
        return this.hideTooltip != itemTooltipDisplay.hideTooltip
                ? false
                : this.hiddenComponents.equals(itemTooltipDisplay.hiddenComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hideTooltip, this.hiddenComponents);
    }
}
