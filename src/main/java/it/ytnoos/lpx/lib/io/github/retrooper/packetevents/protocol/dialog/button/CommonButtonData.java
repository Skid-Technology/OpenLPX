package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.button;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class CommonButtonData {

    private final Component label;

    @Nullable private final Component tooltip;

    private final int width;

    public CommonButtonData(Component component, @Nullable Component component2, int width) {
        this.label = component;
        this.tooltip = component2;
        this.width = width;
    }

    public static CommonButtonData decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        AdventureSerializer adventureSerializer = AdventureSerializer.serializer(packetWrapper);
        Component orThrow = nBTCompound.getOrThrow("label", adventureSerializer, packetWrapper);
        Component orNull = nBTCompound.getOrNull("tooltip", adventureSerializer, packetWrapper);
        int value = nBTCompound.getNumberTagValueOrDefault("width", 150).intValue();
        return new CommonButtonData(orThrow, orNull, value);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            CommonButtonData commonButtonData) {
        AdventureSerializer adventureSerializer = AdventureSerializer.serializer(packetWrapper);
        nBTCompound.set("label", commonButtonData.label, adventureSerializer, packetWrapper);
        if (commonButtonData.tooltip != null) {
            nBTCompound.set(
                    "tooltip", commonButtonData.tooltip, adventureSerializer, packetWrapper);
        }
        if (commonButtonData.width != 150) {
            nBTCompound.setTag("width", new NBTInt(commonButtonData.width));
        }
    }

    public Component getLabel() {
        return this.label;
    }

    @Nullable
    public Component getTooltip() {
        return this.tooltip;
    }

    public int getWidth() {
        return this.width;
    }
}
