package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation.fluids;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Fluids {

    private static final VersionedRegistry<Fluid> REGISTRY = new VersionedRegistry<>("fluid");

    public static final Fluid EMPTY = define("empty");

    public static final Fluid FLOWING_WATER = define("flowing_water");

    public static final Fluid WATER = define("water");

    public static final Fluid FLOWING_LAVA = define("flowing_lava");

    public static final Fluid LAVA = define("lava");

    private Fluids() {}

    public static VersionedRegistry<Fluid> getRegistry() {
        return REGISTRY;
    }

    private static Fluid define(String text) {
        return REGISTRY.define(text, StaticFluid::new);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
