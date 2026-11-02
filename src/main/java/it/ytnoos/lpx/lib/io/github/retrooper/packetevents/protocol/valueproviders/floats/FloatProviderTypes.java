package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.valueproviders.floats;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class FloatProviderTypes {

    private static final VersionedRegistry<FloatProviderType<?>> REGISTRY =
            new VersionedRegistry<>("float_provider_type");

    public static final FloatProviderType<ConstantFloat> CONSTANT =
            define("constant", ConstantFloat.MAP_CODEC);

    public static final FloatProviderType<UniformFloat> UNIFORM =
            define("uniform", UniformFloat.MAP_CODEC);

    public static final FloatProviderType<ClampedNormalFloat> CLAMPED_NORMAL =
            define("clamped_normal", ClampedNormalFloat.MAP_CODEC);

    public static final FloatProviderType<TrapezoidFloat> TRAPEZOID =
            define("trapezoid", TrapezoidFloat.MAP_CODEC);

    private FloatProviderTypes() {}

    @Internal
    public static <T extends FloatProvider> FloatProviderType<T> define(
            String text, NbtMapCodec<T> nbtMapCodec) {
        return REGISTRY.define(
                text, result -> new StaticFloatProviderType<>(result, ((NbtMapCodec) nbtMapCodec)));
    }

    public static VersionedRegistry<FloatProviderType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
