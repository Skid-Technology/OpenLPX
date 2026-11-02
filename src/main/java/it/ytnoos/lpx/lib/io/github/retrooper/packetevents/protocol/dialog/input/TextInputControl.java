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

@NullMarked
public class TextInputControl implements InputControl {

    private final int width;

    private final Component label;

    private final boolean labelVisible;

    private final String initial;

    private final int maxLength;

    private final TextInputControl.@Nullable MultilineOptions multiline;

    public TextInputControl(
            int width,
            Component component,
            boolean labelVisible,
            String initial,
            int maxLength,
            TextInputControl.@Nullable MultilineOptions multilineOptions) {
        if (initial.length() > maxLength) {
            throw new IllegalArgumentException("Default text length exceeds allowed size");
        }
        this.width = width;
        this.label = component;
        this.labelVisible = labelVisible;
        this.initial = initial;
        this.maxLength = maxLength;
        this.multiline = multilineOptions;
    }

    public static TextInputControl decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        int value = nBTCompound.getNumberTagValueOrDefault("width", 200).intValue();
        Component orThrow =
                nBTCompound.getOrThrow(
                        "label", AdventureSerializer.serializer(packetWrapper), packetWrapper);
        boolean booleanOr = nBTCompound.getBooleanOr("label_visible", true);
        String stringTagValueOrDefault = nBTCompound.getStringTagValueOrDefault("initial", "");
        int value2 = nBTCompound.getNumberTagValueOrDefault("max_length", 32).intValue();
        TextInputControl.MultilineOptions orNull =
                nBTCompound.getOrNull(
                        "multiline", TextInputControl.MultilineOptions::decode, packetWrapper);
        return new TextInputControl(
                value, orThrow, booleanOr, stringTagValueOrDefault, value2, orNull);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            TextInputControl textInputControl) {
        if (textInputControl.width != 200) {
            nBTCompound.setTag("width", new NBTInt(textInputControl.width));
        }
        nBTCompound.set(
                "label",
                textInputControl.label,
                AdventureSerializer.serializer(packetWrapper),
                packetWrapper);
        if (!textInputControl.labelVisible) {
            nBTCompound.setTag("label_visible", new NBTByte(false));
        }
        if (!textInputControl.initial.isEmpty()) {
            nBTCompound.setTag("initial", new NBTString(textInputControl.initial));
        }
        if (textInputControl.maxLength != 32) {
            nBTCompound.setTag("max_length", new NBTInt(textInputControl.maxLength));
        }
        if (textInputControl.multiline != null) {
            nBTCompound.set(
                    "multiline",
                    textInputControl.multiline,
                    TextInputControl.MultilineOptions::encode,
                    packetWrapper);
        }
    }

    @Override
    public InputControlType<?> getType() {
        return InputControlTypes.TEXT;
    }

    public int getWidth() {
        return this.width;
    }

    public Component getLabel() {
        return this.label;
    }

    public boolean isLabelVisible() {
        return this.labelVisible;
    }

    public String getInitial() {
        return this.initial;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public TextInputControl.@Nullable MultilineOptions getMultiline() {
        return this.multiline;
    }

    public static final class MultilineOptions {

        @Nullable private final Integer maxLines;

        @Nullable private final Integer height;

        public MultilineOptions(@Nullable Integer maxLines, @Nullable Integer height) {
            this.maxLines = maxLines;
            this.height = height;
        }

        public static TextInputControl.MultilineOptions decode(
                NBT nBT, PacketWrapper<?> packetWrapper) {
            NBTCompound nBTCompound;
            Number numberTagValueOrNull =
                    (nBTCompound = (NBTCompound) nBT).getNumberTagValueOrNull("max_lines");
            Number numberTagValueOrNull2 = nBTCompound.getNumberTagValueOrNull("height");
            return new TextInputControl.MultilineOptions(
                    numberTagValueOrNull != null ? numberTagValueOrNull.intValue() : null,
                    numberTagValueOrNull2 != null ? numberTagValueOrNull2.intValue() : null);
        }

        public static NBT encode(
                PacketWrapper<?> packetWrapper,
                TextInputControl.MultilineOptions multilineOptions) {
            NBTCompound nBTCompound = new NBTCompound();
            if (multilineOptions.maxLines != null) {
                nBTCompound.setTag("max_lines", new NBTInt(multilineOptions.maxLines));
            }
            if (multilineOptions.height != null) {
                nBTCompound.setTag("height", new NBTInt(multilineOptions.height));
            }
            return nBTCompound;
        }

        @Nullable
        public Integer getMaxLines() {
            return this.maxLines;
        }

        @Nullable
        public Integer getHeight() {
            return this.height;
        }
    }
}
