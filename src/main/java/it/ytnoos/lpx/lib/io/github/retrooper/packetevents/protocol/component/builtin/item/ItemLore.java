package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ItemLore {

    public static final ItemLore EMPTY = new ItemLore(Collections.emptyList());

    private List<Component> lines;

    public ItemLore(List<Component> lines) {
        this.lines = lines;
    }

    public static ItemLore read(PacketWrapper<?> packetWrapper) {
        List values = packetWrapper.readList(PacketWrapper::readComponent);
        return new ItemLore(values);
    }

    public static void write(PacketWrapper<?> packetWrapper, ItemLore itemLore) {
        packetWrapper.writeList(itemLore.lines, PacketWrapper::writeComponent);
    }

    public void addLine(Component component) {
        this.lines.add(component);
    }

    public List<Component> getLines() {
        return this.lines;
    }

    public void setLines(List<Component> lines) {
        this.lines = lines;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        }
        if (!(result instanceof ItemLore)) {
            return false;
        }
        ItemLore itemLore = (ItemLore) result;
        return this.lines.equals(itemLore.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.lines);
    }

    @Override
    public String toString() {
        return "ItemLore{lines=" + this.lines + 125;
    }
}
