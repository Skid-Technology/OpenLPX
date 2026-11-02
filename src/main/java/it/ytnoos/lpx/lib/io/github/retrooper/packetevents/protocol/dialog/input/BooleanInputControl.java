package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class BooleanInputControl implements InputControl {

    private final Component label;

    private final boolean initial;

    private final String onTrue;

    private final String onFalse;

    public BooleanInputControl(
            Component component, boolean initial, String onTrue, String onFalse) {
        this.label = component;
        this.initial = initial;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
    }

    public static BooleanInputControl decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        Component orThrow =
                nBTCompound.getOrThrow(
                        "label", AdventureSerializer.serializer(packetWrapper), packetWrapper);
        boolean booleanValue = nBTCompound.getBoolean("initial");
        String stringTagValueOrDefault = nBTCompound.getStringTagValueOrDefault("on_true", "true");
        String stringTagValueOrDefault2 =
                nBTCompound.getStringTagValueOrDefault("on_false", "false");
        return new BooleanInputControl(
                orThrow, booleanValue, stringTagValueOrDefault, stringTagValueOrDefault2);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            BooleanInputControl booleanInputControl) {
        nBTCompound.set(
                "label",
                booleanInputControl.label,
                AdventureSerializer.serializer(packetWrapper),
                packetWrapper);
        if (booleanInputControl.initial) {
            nBTCompound.setTag("initial", new NBTByte(true));
        }
        if (!"true".equals(booleanInputControl.onTrue)) {
            nBTCompound.setTag("on_true", new NBTString(booleanInputControl.onTrue));
        }
        if (!"false".equals(booleanInputControl.onFalse)) {
            nBTCompound.setTag("on_false", new NBTString(booleanInputControl.onFalse));
        }
    }

    @Override
    public InputControlType<?> getType() {
        return InputControlTypes.BOOLEAN;
    }

    public Component getLabel() {
        return this.label;
    }

    public boolean isInitial() {
        return this.initial;
    }

    public String getOnTrue() {
        return this.onTrue;
    }

    public String getOnFalse() {
        return this.onFalse;
    }
}
