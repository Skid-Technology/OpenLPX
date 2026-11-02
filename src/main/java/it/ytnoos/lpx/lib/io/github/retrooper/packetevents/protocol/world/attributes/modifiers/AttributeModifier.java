package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.modifiers;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.AlphaColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.Color;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttribute;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MapUtil;

import org.jspecify.annotations.NullMarked;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@NullMarked
public interface AttributeModifier<T, A> {

    Map<AttributeModifier.Operation, AttributeModifier<Boolean, ?>> BOOLEAN_LIBRARY =
            MapUtil.createMap(
                    new SimpleEntry<>(AttributeModifier.Operation.AND, BooleanModifier.AND),
                    new SimpleEntry<>(AttributeModifier.Operation.NAND, BooleanModifier.NAND),
                    new SimpleEntry<>(AttributeModifier.Operation.OR, BooleanModifier.OR),
                    new SimpleEntry<>(AttributeModifier.Operation.NOR, BooleanModifier.NOR),
                    new SimpleEntry<>(AttributeModifier.Operation.XOR, BooleanModifier.XOR),
                    new SimpleEntry<>(AttributeModifier.Operation.XNOR, BooleanModifier.XNOR));

    Map<AttributeModifier.Operation, AttributeModifier<Float, ?>> FLOAT_LIBRARY =
            MapUtil.createMap(
                    new SimpleEntry<>(
                            AttributeModifier.Operation.ALPHA_BLEND, FloatModifier.ALPHA_BLEND),
                    new SimpleEntry<>(AttributeModifier.Operation.ADD, FloatModifier.ADD),
                    new SimpleEntry<>(AttributeModifier.Operation.SUBTRACT, FloatModifier.SUBTRACT),
                    new SimpleEntry<>(AttributeModifier.Operation.MULTIPLY, FloatModifier.MULTIPLY),
                    new SimpleEntry<>(AttributeModifier.Operation.MINIMUM, FloatModifier.MINIMUM),
                    new SimpleEntry<>(AttributeModifier.Operation.MAXIMUM, FloatModifier.MAXIMUM));

    Map<AttributeModifier.Operation, AttributeModifier<Color, ?>> RGB_COLOR_LIBRARY =
            MapUtil.createMap(
                    new SimpleEntry<>(
                            AttributeModifier.Operation.ALPHA_BLEND, ColorModifier.ALPHA_BLEND),
                    new SimpleEntry<>(AttributeModifier.Operation.ADD, ColorModifier.ADD),
                    new SimpleEntry<>(AttributeModifier.Operation.SUBTRACT, ColorModifier.SUBTRACT),
                    new SimpleEntry<>(AttributeModifier.Operation.MULTIPLY, ColorModifier.MULTIPLY),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.BLEND_TO_GRAY,
                            ColorModifier.BLEND_TO_GRAY));

    Map<AttributeModifier.Operation, AttributeModifier<AlphaColor, ?>> ARGB_COLOR_LIBRARY =
            MapUtil.createMap(
                    new SimpleEntry<>(
                            AttributeModifier.Operation.ALPHA_BLEND,
                            AlphaColorModifier.ALPHA_BLEND),
                    new SimpleEntry<>(AttributeModifier.Operation.ADD, AlphaColorModifier.ADD),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.SUBTRACT, AlphaColorModifier.SUBTRACT),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.MULTIPLY, AlphaColorModifier.MULTIPLY),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.BLEND_TO_GRAY,
                            AlphaColorModifier.BLEND_TO_GRAY));

    Map<AttributeModifier.Operation, AttributeModifier<Integer, ?>> INTEGER_LIBRARY =
            MapUtil.createMap(
                    new SimpleEntry<>(AttributeModifier.Operation.ADD, IntegerModifier.ADD),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.SUBTRACT, IntegerModifier.SUBTRACT),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.MULTIPLY, IntegerModifier.MULTIPLY),
                    new SimpleEntry<>(AttributeModifier.Operation.MINIMUM, IntegerModifier.MINIMUM),
                    new SimpleEntry<>(
                            AttributeModifier.Operation.MAXIMUM, IntegerModifier.MAXIMUM));

    static <T> Map<AttributeModifier.Operation, AttributeModifier<List<T>, ?>> listLibrary() {
        AttributeModifier.ListAppendModifier<T> listAppendModifier =
                (AttributeModifier.ListAppendModifier<T>)
                        AttributeModifier.ListAppendModifier.INSTANCE;
        return MapUtil.createMap(
                new SimpleEntry<>(AttributeModifier.Operation.APPEND, listAppendModifier));
    }

    static <T> AttributeModifier<T, T> override() {
        return ((AttributeModifier<T, T>) AttributeModifier.OverrideModifier.INSTANCE);
    }

    T apply(T t, A a);

    NbtCodec<A> argumentCodec(EnvironmentAttribute<T> environmentAttribute);

    final class ListAppendModifier<T> implements AttributeModifier<List<T>, List<T>> {

        private static final AttributeModifier.ListAppendModifier<?> INSTANCE =
                new AttributeModifier.ListAppendModifier();

        public List<T> apply(List<T> values, List<T> values2) {
            if (values2.isEmpty()) {
                return values;
            }
            if (values.isEmpty()) {
                return values2;
            }
            ArrayList values3;
            (values3 = new ArrayList(values.size() + values2.size())).addAll(values);
            values3.addAll(values2);
            return Collections.unmodifiableList(values3);
        }

        @Override
        public NbtCodec<List<T>> argumentCodec(EnvironmentAttribute<List<T>> environmentAttribute) {
            return environmentAttribute.getType().getValueCodec();
        }
    }

    enum Operation implements CodecNameable {
        OVERRIDE("override"),
        ALPHA_BLEND("alpha_blend"),
        ADD("add"),
        SUBTRACT("subtract"),
        MULTIPLY("multiply"),
        BLEND_TO_GRAY("blend_to_gray"),
        MINIMUM("minimum"),
        MAXIMUM("maximum"),
        AND("and"),
        NAND("nand"),
        OR("or"),
        NOR("nor"),
        XOR("xor"),
        XNOR("xnor"),
        APPEND("append"),
        OVERLAY("overlay");

        public static final NbtCodec<AttributeModifier.Operation> CODEC =
                NbtCodecs.forEnum(values());

        private final String name;

        Operation(String name) {
            this.name = name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }

    final class OverrideModifier<T> implements AttributeModifier<T, T> {

        private static final AttributeModifier.OverrideModifier<?> INSTANCE =
                new AttributeModifier.OverrideModifier();

        @Override
        public T apply(T t, T t2) {
            return (T) t2;
        }

        @Override
        public NbtCodec<T> argumentCodec(EnvironmentAttribute<T> environmentAttribute) {
            return environmentAttribute.getType().getValueCodec();
        }
    }
}
