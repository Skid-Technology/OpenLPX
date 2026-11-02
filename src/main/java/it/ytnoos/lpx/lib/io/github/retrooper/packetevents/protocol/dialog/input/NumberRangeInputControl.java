package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class NumberRangeInputControl implements InputControl {

    private final int width;

    private final Component label;

    private final String labelFormat;

    private final NumberRangeInputControl.RangeInfo rangeInfo;

    public NumberRangeInputControl(
            int width,
            Component component,
            String labelFormat,
            NumberRangeInputControl.RangeInfo rangeInfo2) {
        this.width = width;
        this.label = component;
        this.labelFormat = labelFormat;
        this.rangeInfo = rangeInfo2;
    }

    public static NumberRangeInputControl decode(
            NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        int value = nBTCompound.getNumberTagValueOrDefault("width", 200).intValue();
        Component orThrow =
                nBTCompound.getOrThrow(
                        "label", AdventureSerializer.serializer(packetWrapper), packetWrapper);
        String stringTagValueOrDefault =
                nBTCompound.getStringTagValueOrDefault("label_format", "options.generic_value");
        NumberRangeInputControl.RangeInfo rangeInfo =
                NumberRangeInputControl.RangeInfo.decode(nBTCompound, packetWrapper);
        return new NumberRangeInputControl(value, orThrow, stringTagValueOrDefault, rangeInfo);
    }

    public static void encode(
            NBTCompound nBTCompound,
            PacketWrapper<?> packetWrapper,
            NumberRangeInputControl numberRangeInputControl) {
        if (numberRangeInputControl.width != 200) {
            nBTCompound.setTag("width", new NBTInt(numberRangeInputControl.width));
        }
        nBTCompound.set(
                "label",
                numberRangeInputControl.label,
                AdventureSerializer.serializer(packetWrapper),
                packetWrapper);
        if (!"options.generic_value".equals(numberRangeInputControl.labelFormat)) {
            nBTCompound.setTag("label_format", new NBTString(numberRangeInputControl.labelFormat));
        }
        NumberRangeInputControl.RangeInfo.encode(
                nBTCompound, packetWrapper, numberRangeInputControl.rangeInfo);
    }

    @Override
    public InputControlType<?> getType() {
        return InputControlTypes.NUMBER_RANGE;
    }

    public int getWidth() {
        return this.width;
    }

    public Component getLabel() {
        return this.label;
    }

    public String getLabelFormat() {
        return this.labelFormat;
    }

    public NumberRangeInputControl.RangeInfo getRangeInfo() {
        return this.rangeInfo;
    }

    public static final class RangeInfo {

        private final float start;

        private final float end;

        @Nullable private final Float initial;

        @Nullable private final Float step;

        public RangeInfo(float start, float end, @Nullable Float initial, @Nullable Float step) {
            if (initial != null) {
                float value5 = Math.min(start, end);
                float value6 = Math.max(start, end);
                if (initial < value5 || initial > value6) {
                    throw new IllegalArgumentException(
                            "Initial value "
                                    + initial
                                    + " is outside of range ["
                                    + value5
                                    + ", "
                                    + value6
                                    + "]");
                }
            }
            this.start = start;
            this.end = end;
            this.initial = initial;
            this.step = step;
        }

        public static NumberRangeInputControl.RangeInfo decode(
                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
            float value = nBTCompound.getNumberTagValueOrThrow("start").floatValue();
            float value2 = nBTCompound.getNumberTagValueOrThrow("end").floatValue();
            NBTNumber nBTNumber;
            Float value3 =
                    (nBTNumber = nBTCompound.getNumberTagOrNull("initial")) != null
                            ? nBTNumber.getAsFloat()
                            : null;
            NBTNumber nBTNumber2;
            Float value4 =
                    (nBTNumber2 = nBTCompound.getNumberTagOrNull("step")) != null
                            ? nBTNumber2.getAsFloat()
                            : null;
            return new NumberRangeInputControl.RangeInfo(value, value2, value3, value4);
        }

        public static void encode(
                NBTCompound nBTCompound,
                PacketWrapper<?> packetWrapper,
                NumberRangeInputControl.RangeInfo rangeInfo) {
            nBTCompound.setTag("start", new NBTFloat(rangeInfo.start));
            nBTCompound.setTag("end", new NBTFloat(rangeInfo.end));
            if (rangeInfo.initial != null) {
                nBTCompound.setTag("initial", new NBTFloat(rangeInfo.initial));
            }
            if (rangeInfo.step != null) {
                nBTCompound.setTag("step", new NBTFloat(rangeInfo.step));
            }
        }

        public float getStart() {
            return this.start;
        }

        public float getEnd() {
            return this.end;
        }

        @Nullable
        public Float getInitial() {
            return this.initial;
        }

        @Nullable
        public Float getStep() {
            return this.step;
        }
    }
}
