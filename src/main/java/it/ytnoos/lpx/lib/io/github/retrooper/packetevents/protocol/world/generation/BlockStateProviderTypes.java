package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.CopyPropertiesProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.DualNoiseProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.NoiseProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.NoiseThresholdProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.RandomBlockProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.RandomizedIntStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.RotatedBlockProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.RuleBasedStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.SimpleStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.provider.WeightedStateProvider;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class BlockStateProviderTypes {

    private static final VersionedRegistry<BlockStateProviderType<?>> REGISTRY =
            new VersionedRegistry<>("worldgen/block_state_provider_type");

    public static final BlockStateProviderType<CopyPropertiesProvider> COPY_PROPERTIES =
            define("copy_properties", CopyPropertiesProvider.MAP_CODEC);

    public static final BlockStateProviderType<DualNoiseProvider> DUAL_NOISE =
            define("dual_noise", DualNoiseProvider.MAP_CODEC);

    public static final BlockStateProviderType<NoiseProvider> NOISE =
            define("noise", NoiseProvider.MAP_CODEC);

    public static final BlockStateProviderType<NoiseThresholdProvider> NOISE_THRESHOLD =
            define("noise_threshold", NoiseThresholdProvider.MAP_CODEC);

    public static final BlockStateProviderType<RandomBlockProvider> RANDOM_BLOCK =
            define("random_block", RandomBlockProvider.MAP_CODEC);

    public static final BlockStateProviderType<RandomizedIntStateProvider> RANDOMIZED_INT =
            define("randomized_int", RandomizedIntStateProvider.MAP_CODEC);

    public static final BlockStateProviderType<RotatedBlockProvider> ROTATED =
            define("rotated", RotatedBlockProvider.MAP_CODEC);

    public static final BlockStateProviderType<RuleBasedStateProvider> RULE_BASED =
            define("rule_based", RuleBasedStateProvider.MAP_CODEC);

    public static final BlockStateProviderType<SimpleStateProvider> SIMPLE =
            define("simple", SimpleStateProvider.MAP_CODEC);

    public static final BlockStateProviderType<WeightedStateProvider> WEIGHTED =
            define("weighted", WeightedStateProvider.MAP_CODEC);

    private BlockStateProviderTypes() {}

    @Internal
    public static <T extends BlockStateProvider> BlockStateProviderType<T> define(
            String text, NbtMapCodec<T> nbtMapCodec) {
        return REGISTRY.define(
                text,
                result -> new StaticBlockStateProviderType<>(result, ((NbtMapCodec) nbtMapCodec)));
    }

    public static VersionedRegistry<BlockStateProviderType<?>> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
