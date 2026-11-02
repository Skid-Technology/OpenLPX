package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemTooltipStyle {

    private ResourceLocation tooltipLoc;

    public ItemTooltipStyle(ResourceLocation resourceLocation) {
        this.tooltipLoc = resourceLocation;
    }

    public static ItemTooltipStyle read(PacketWrapper<?> packetWrapper) {
        return new ItemTooltipStyle(packetWrapper.readIdentifier());
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemTooltipStyle itemTooltipStyle) {
        packetWrapper.writeIdentifier(itemTooltipStyle.tooltipLoc);
    }

    public ResourceLocation getTooltipLoc() {
        return this.tooltipLoc;
    }

    public void setTooltipLoc(ResourceLocation resourceLocation) {
        this.tooltipLoc = resourceLocation;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemTooltipStyle)) {
            return false;
        }
        ItemTooltipStyle itemTooltipStyle = (ItemTooltipStyle) result;
        return this.tooltipLoc.equals(itemTooltipStyle.tooltipLoc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.tooltipLoc);
    }

    @Override
    public String toString() {
        return "ItemTooltipStyle{tooltipLoc=" + this.tooltipLoc + 125;
    }
}
