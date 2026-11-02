package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.ints;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class IntProviderTypes {

    private static final VersionedRegistry<IntProviderType<?>> REGISTRY =
            new VersionedRegistry<>("int_provider_type");

    public static final IntProviderType<ConstantInt> CONSTANT =
            define("constant", ConstantInt.MAP_CODEC);

    public static final IntProviderType<UniformInt> UNIFORM =
            define("uniform", UniformInt.MAP_CODEC);

    public static final IntProviderType<BiasedToBottomInt> BIASED_TO_BOTTOM =
            define("biased_to_bottom", BiasedToBottomInt.MAP_CODEC);

    public static final IntProviderType<VeryBiasedToBottomInt> VERY_BIASED_TO_BOTTOM =
            define("very_biased_to_bottom", VeryBiasedToBottomInt.MAP_CODEC);

    public static final IntProviderType<ClampedInt> CLAMPED =
            define("clamped", ClampedInt.MAP_CODEC);

    public static final IntProviderType<WeightedListInt> WEIGHTED_LIST =
            define("weighted_list", WeightedListInt.MAP_CODEC);

    public static final IntProviderType<ClampedNormalInt> CLAMPED_NORMAL =
            define("clamped_normal", ClampedNormalInt.MAP_CODEC);

    public static final IntProviderType<TrapezoidInt> TRAPEZOID =
            define("trapezoid", TrapezoidInt.MAP_CODEC);

    private IntProviderTypes() {}

    @Internal
    public static <T extends IntProvider> IntProviderType<T> define(
            String text, NbtMapCodec<T> nbtMapCodec) {
        return REGISTRY.define(
                text, result -> new StaticIntProviderType<>(result, ((NbtMapCodec) nbtMapCodec)));
    }

    public static VersionedRegistry<IntProviderType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
