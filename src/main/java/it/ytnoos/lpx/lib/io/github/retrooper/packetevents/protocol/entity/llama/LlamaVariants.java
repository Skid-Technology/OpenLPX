package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.llama;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class LlamaVariants {

    private static final VersionedRegistry<LlamaVariant> REGISTRY =
            new VersionedRegistry<>("llama_variant");

    public static final LlamaVariant CREAMY = define("creamy");

    public static final LlamaVariant WHITE = define("white");

    public static final LlamaVariant BROWN = define("brown");

    public static final LlamaVariant GRAY = define("gray");

    private LlamaVariants() {}

    @Internal
    public static LlamaVariant define(String text) {
        return REGISTRY.define(text, StaticLlamaVariant::new);
    }

    public static VersionedRegistry<LlamaVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
