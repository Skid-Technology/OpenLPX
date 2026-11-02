package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.recipe.display.slot.SlotDisplay;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class SingleInputOptionDisplay {

    private MappedEntitySet<ItemType> input;

    private SlotDisplay<?> optionDisplay;

    public SingleInputOptionDisplay(
            MappedEntitySet<ItemType> mappedEntitySet, SlotDisplay<?> slotDisplay) {
        this.input = mappedEntitySet;
        this.optionDisplay = slotDisplay;
    }

    public static SingleInputOptionDisplay read(PacketWrapper<?> packetWrapper) {
        MappedEntitySet<ItemType> mappedEntitySet =
                MappedEntitySet.read(packetWrapper, ItemTypes.getRegistry());
        SlotDisplay slotDisplay = SlotDisplay.read(packetWrapper);
        return new SingleInputOptionDisplay(mappedEntitySet, slotDisplay);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, SingleInputOptionDisplay singleInputOptionDisplay) {
        MappedEntitySet.write(packetWrapper, singleInputOptionDisplay.input);
        SlotDisplay.write(packetWrapper, singleInputOptionDisplay.optionDisplay);
    }

    public MappedEntitySet<ItemType> getInput() {
        return this.input;
    }

    public void setInput(MappedEntitySet<ItemType> mappedEntitySet) {
        this.input = mappedEntitySet;
    }

    public SlotDisplay<?> getOptionDisplay() {
        return this.optionDisplay;
    }

    public void setOptionDisplay(SlotDisplay<?> slotDisplay) {
        this.optionDisplay = slotDisplay;
    }
}
