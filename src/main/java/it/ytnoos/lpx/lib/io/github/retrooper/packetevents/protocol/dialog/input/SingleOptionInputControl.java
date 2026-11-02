package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

@NullMarked
public class SingleOptionInputControl implements InputControl {

    private final int width;

    private final List<SingleOptionInputControl.Entry> options;

    private final Component label;

    private final boolean labelVisible;

    public SingleOptionInputControl(
            int width,
            List<SingleOptionInputControl.Entry> options,
            Component component,
            boolean labelVisible) {
        boolean matches2 = false;
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input
                                .SingleOptionInputControl.Entry>
                iterator2 = options.iterator();
        while (iterator2.hasNext()) {
            if (((SingleOptionInputControl.Entry) iterator2.next()).initial) {
                if (matches2) {
                    throw new IllegalArgumentException("Multiple initial values");
                }
                matches2 = true;
            }
        }
        this.width = width;
        this.options = options;
        this.label = component;
        this.labelVisible = labelVisible;
    }

    public static SingleOptionInputControl decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        int value = nBTCompound.getNumberTagValueOrDefault("width", 200).intValue();
        List listOrThrow =
                nBTCompound.getListOrThrow(
                        "options", SingleOptionInputControl.Entry::decode, packetWrapper);
        Component orThrow =
                nBTCompound.getOrThrow(
                        "label", AdventureSerializer.serializer(packetWrapper), packetWrapper);
        boolean booleanOr = nBTCompound.getBooleanOr("label_visible", true);
        return new SingleOptionInputControl(value, listOrThrow, orThrow, booleanOr);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            SingleOptionInputControl singleOptionInputControl) {
        if (singleOptionInputControl.width != 200) {
            nBTCompound.setTag("width", new NBTInt(singleOptionInputControl.width));
        }
        nBTCompound.setList(
                "options",
                singleOptionInputControl.options,
                SingleOptionInputControl.Entry::encode,
                packetWrapper);
        nBTCompound.set(
                "label",
                singleOptionInputControl.label,
                AdventureSerializer.serializer(packetWrapper),
                packetWrapper);
        if (!singleOptionInputControl.labelVisible) {
            nBTCompound.setTag("label_visible", new NBTByte(false));
        }
    }

    @Override
    public InputControlType<?> getType() {
        return InputControlTypes.SINGLE_OPTION;
    }

    public int getWidth() {
        return this.width;
    }

    public List<SingleOptionInputControl.Entry> getOptions() {
        return this.options;
    }

    public Component getLabel() {
        return this.label;
    }

    public boolean isLabelVisible() {
        return this.labelVisible;
    }

    public static final class Entry {

        private final String id;

        @Nullable private final Component display;

        private final boolean initial;

        public Entry(String id, @Nullable Component component, boolean initial) {
            this.id = id;
            this.display = component;
            this.initial = initial;
        }

        public static SingleOptionInputControl.Entry decode(
                NBT nBT, PacketWrapper<?> packetWrapper) {
            if (nBT instanceof NBTString) {
                return new SingleOptionInputControl.Entry(
                        ((NBTString) nBT).getValue(), null, false);
            }
            NBTCompound nBTCompound;
            String stringTagValueOrThrow =
                    (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("id");
            Component orNull =
                    nBTCompound.getOrNull(
                            "display",
                            AdventureSerializer.serializer(packetWrapper),
                            packetWrapper);
            boolean booleanOr = nBTCompound.getBooleanOr("initial", false);
            return new SingleOptionInputControl.Entry(stringTagValueOrThrow, orNull, booleanOr);
        }

        public static NBT encode(
                PacketWrapper<?> packetWrapper, SingleOptionInputControl.Entry entry) {
            NBTCompound nBTCompound;
            (nBTCompound = new NBTCompound()).setTag("id", new NBTString(entry.id));
            if (entry.display != null) {
                nBTCompound.set(
                        "display",
                        entry.display,
                        AdventureSerializer.serializer(packetWrapper),
                        packetWrapper);
            }
            if (entry.initial) {
                nBTCompound.setTag("initial", new NBTByte(true));
            }
            return nBTCompound;
        }

        public String getId() {
            return this.id;
        }

        @Nullable
        public Component getDisplay() {
            return this.display;
        }

        public boolean isInitial() {
            return this.initial;
        }
    }
}
