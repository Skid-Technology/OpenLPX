package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.Objects;

public class ItemModel {

    private ResourceLocation modelLocation;

    public ItemModel(ResourceLocation resourceLocation) {
        this.modelLocation = resourceLocation;
    }

    public static ItemModel read(PacketWrapper<?> packetWrapper) {
        return new ItemModel(packetWrapper.readIdentifier());
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemModel itemModel) {
        packetWrapper.writeIdentifier(itemModel.modelLocation);
    }

    public ResourceLocation getModelLocation() {
        return this.modelLocation;
    }

    public void setModelLocation(ResourceLocation resourceLocation) {
        this.modelLocation = resourceLocation;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemModel)) {
            return false;
        }
        ItemModel itemModel = (ItemModel) result;
        return this.modelLocation.equals(itemModel.modelLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.modelLocation);
    }

    @Override
    public String toString() {
        return "ItemModel{modelLocation=" + this.modelLocation + 125;
    }
}
