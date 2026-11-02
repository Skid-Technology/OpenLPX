package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.input;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class InputControlTypes {

    private static final VersionedRegistry<InputControlType<?>> REGISTRY =
            new VersionedRegistry<>("input_control_type");

    public static final InputControlType<BooleanInputControl> BOOLEAN =
            define("boolean", BooleanInputControl::decode, BooleanInputControl::encode);

    public static final InputControlType<NumberRangeInputControl> NUMBER_RANGE =
            define(
                    "number_range",
                    NumberRangeInputControl::decode,
                    NumberRangeInputControl::encode);

    public static final InputControlType<SingleOptionInputControl> SINGLE_OPTION =
            define(
                    "single_option",
                    SingleOptionInputControl::decode,
                    SingleOptionInputControl::encode);

    public static final InputControlType<TextInputControl> TEXT =
            define("text", TextInputControl::decode, TextInputControl::encode);

    private InputControlTypes() {}

    public static VersionedRegistry<InputControlType<?>> getRegistry() {
        return REGISTRY;
    }

    @Internal
    public static <T extends InputControl> InputControlType<T> define(
            String text, NbtMapDecoder<T> nbtMapDecoder, NbtMapEncoder<T> nbtMapEncoder) {
        return REGISTRY.define(
                text,
                result ->
                        new StaticInputControlType<>(
                                result,
                                ((NbtMapDecoder) nbtMapDecoder),
                                ((NbtMapEncoder) nbtMapEncoder)));
    }

    static {
        REGISTRY.unloadMappings();
    }
}
