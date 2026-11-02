package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.chunk;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.Nullable;

public enum HeightmapType {
    WORLD_SURFACE_WG("WORLD_SURFACE_WG", false),
    WORLD_SURFACE("WORLD_SURFACE", true),
    OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", false),
    OCEAN_FLOOR("OCEAN_FLOOR", false),
    MOTION_BLOCKING("MOTION_BLOCKING", true),
    MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", true);

    public static final Index<String, HeightmapType> SERIALIZATION_KEY_INDEX =
            Index.create(HeightmapType.class, HeightmapType::getSerializationKey);

    private final String serializationKey;

    private final boolean client;

    HeightmapType(String serializationKey, boolean client) {
        this.serializationKey = serializationKey;
        this.client = client;
    }

    @Nullable
    public static HeightmapType getHeightmapType(String text) {
        return SERIALIZATION_KEY_INDEX.value(text);
    }

    public static HeightmapType read(PacketWrapper<?> packetWrapper) {
        return packetWrapper.readEnum(HeightmapType.class);
    }

    public static void write(PacketWrapper<?> packetWrapper, HeightmapType heightmapType) {
        packetWrapper.writeEnum(heightmapType);
    }

    public String getSerializationKey() {
        return this.serializationKey;
    }

    public boolean isClient() {
        return this.client;
    }
}
