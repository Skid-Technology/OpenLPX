package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class CatVariants {

    private static final VersionedRegistry<CatVariant> REGISTRY =
            new VersionedRegistry<>("cat_variant");

    public static final CatVariant ALL_BLACK = define("all_black");

    public static final CatVariant BLACK = define("black");

    public static final CatVariant BRITISH_SHORTHAIR = define("british_shorthair");

    public static final CatVariant CALICO = define("calico");

    public static final CatVariant JELLIE = define("jellie");

    public static final CatVariant PERSIAN = define("persian");

    public static final CatVariant RAGDOLL = define("ragdoll");

    public static final CatVariant RED = define("red");

    public static final CatVariant SIAMESE = define("siamese");

    public static final CatVariant TABBY = define("tabby");

    public static final CatVariant WHITE = define("white");

    private CatVariants() {}

    @Internal
    public static CatVariant define(String text) {
        ResourceLocation resourceLocation = new ResourceLocation("entity/cat/cat_" + text);
        ResourceLocation resourceLocation2 =
                new ResourceLocation("entity/cat/cat_" + text + "_baby");
        return REGISTRY.define(
                text, result -> new StaticCatVariant(result, resourceLocation, resourceLocation2));
    }

    public static VersionedRegistry<CatVariant> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }
}
