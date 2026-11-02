package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Obsolete;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemCustomModelData {

    private List<Float> floats;

    private List<Boolean> flags;

    private List<String> strings;

    private List<Color> colors;

    public ItemCustomModelData(
            List<Float> floats, List<Boolean> flags, List<String> strings, List<Color> colors) {
        this.floats = floats;
        this.flags = flags;
        this.strings = strings;
        this.colors = colors;
    }

    public ItemCustomModelData(int value) {
        this.floats = new ArrayList<>(1);
        this.flags = new ArrayList<>(0);
        this.strings = new ArrayList<>(0);
        this.colors = new ArrayList<>(0);
        this.setLegacyId(value);
    }

    public static ItemCustomModelData read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_4)
                ? new ItemCustomModelData(
                        packetWrapper.readList(PacketWrapper::readFloat),
                        packetWrapper.readList(PacketWrapper::readBoolean),
                        packetWrapper.readList(PacketWrapper::readString),
                        packetWrapper.readList(Color::read))
                : new ItemCustomModelData(packetWrapper.readVarInt());
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ItemCustomModelData itemCustomModelData) {
        if (packetWrapper.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_21_4)) {
            packetWrapper.writeList(itemCustomModelData.floats, PacketWrapper::writeFloat);
            packetWrapper.writeList(itemCustomModelData.flags, PacketWrapper::writeBoolean);
            packetWrapper.writeList(itemCustomModelData.strings, PacketWrapper::writeString);
            packetWrapper.writeList(itemCustomModelData.colors, Color::write);
        } else {
            packetWrapper.writeVarInt(itemCustomModelData.getLegacyId());
        }
    }

    public List<Float> getFloats() {
        return this.floats;
    }

    public void setFloats(List<Float> floats) {
        this.floats = floats;
    }

    public List<Boolean> getFlags() {
        return this.flags;
    }

    public void setFlags(List<Boolean> flags) {
        this.flags = flags;
    }

    public List<String> getStrings() {
        return this.strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<Color> getColors() {
        return this.colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    @Obsolete
    public int getLegacyId() {
        return !this.floats.isEmpty() ? this.floats.get(0).intValue() : 0;
    }

    @Obsolete
    public void setLegacyId(int value) {
        if (this.flags.isEmpty()) {
            this.floats.add((float) value);
        } else {
            this.floats.set(0, (float) value);
        }
    }

    @Override
    public final boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ItemCustomModelData)) {
            return false;
        } else {
            ItemCustomModelData itemCustomModelData = (ItemCustomModelData) result;
            if (!this.floats.equals(itemCustomModelData.floats)) {
                return false;
            } else if (!this.flags.equals(itemCustomModelData.flags)) {
                return false;
            } else {
                return !this.strings.equals(itemCustomModelData.strings)
                        ? false
                        : this.colors.equals(itemCustomModelData.colors);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.floats, this.flags, this.strings, this.colors);
    }

    @Override
    public String toString() {
        return "ItemCustomModelData{floats="
                + this.floats
                + ", flags=, strings="
                + this.flags
                + this.strings
                + ", colors="
                + this.colors
                + 125;
    }
}
