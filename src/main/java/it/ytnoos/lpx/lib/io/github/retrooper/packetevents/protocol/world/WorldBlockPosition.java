package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;

import org.jetbrains.annotations.NotNull;

public class WorldBlockPosition {

    public static final ResourceLocation OVERWORLD_DIMENSION =
            ResourceLocation.minecraft("overworld");

    private ResourceLocation world;

    private Vector3i blockPosition;

    public WorldBlockPosition(
            @NotNull ResourceLocation resourceLocation, @NotNull Vector3i vector3i) {
        this.world = resourceLocation;
        this.blockPosition = vector3i;
    }

    public WorldBlockPosition(
            @NotNull ResourceLocation resourceLocation, int value, int value2, int value3) {
        this.world = resourceLocation;
        this.blockPosition = new Vector3i(value, value2, value3);
    }

    @Deprecated
    public WorldBlockPosition(@NotNull Dimension dimension, @NotNull Vector3i vector3i) {
        this(new ResourceLocation(dimension.getDimensionName()), vector3i);
    }

    public WorldBlockPosition(@NotNull DimensionType dimensionType, @NotNull Vector3i vector3i) {
        this(new ResourceLocation(dimensionType.getName()), vector3i);
    }

    public ResourceLocation getWorld() {
        return this.world;
    }

    public void setWorld(ResourceLocation resourceLocation) {
        this.world = resourceLocation;
    }

    public Vector3i getBlockPosition() {
        return this.blockPosition;
    }

    public void setBlockPosition(Vector3i vector3i) {
        this.blockPosition = vector3i;
    }
}
