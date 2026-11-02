package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.generation;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.MappingHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jspecify.annotations.NullMarked;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@NullMarked
public final class BlockStateProviders {

    private static final Map<ResourceLocation, NBTCompound> PROVIDER_DATA = new HashMap<>();

    private static final VersionedRegistry<BlockStateProvider> REGISTRY;

    public static final BlockStateProvider CAVE_VINES_BODY;

    public static final BlockStateProvider CAVE_VINES_HEAD;

    public static final BlockStateProvider FLOWER_FLOWER_FOREST;

    public static final BlockStateProvider FLOWER_MEADOW;

    public static final BlockStateProvider FLOWER_PLAIN;

    public static final BlockStateProvider MANGROVE_PROPAGULE;

    public static final BlockStateProvider PODZOL_BENEATH_TREE;

    public static final BlockStateProvider SOIL_BENEATH_TREE;

    private BlockStateProviders() {}

    @Internal
    public static BlockStateProvider define(String text) {
        PacketWrapper packetWrapper = PacketWrapper.createDummyWrapper(ClientVersion.getLatest());
        return REGISTRY.define(
                text,
                result -> {
                    NBTCompound nBTCompound;
                    if ((nBTCompound = PROVIDER_DATA.get(result.getName())) == null) {
                        throw new IllegalArgumentException(
                                "Can't define block state provider "
                                        + result.getName()
                                        + ", no data found");
                    } else {
                        return BlockStateProvider.DIRECT_CODEC
                                .decode(nBTCompound, packetWrapper)
                                .copy(result);
                    }
                });
    }

    public static VersionedRegistry<BlockStateProvider> getRegistry() {
        return REGISTRY;
    }

    static {
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/worldgen/block_state_provider");
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2 =
                        (SequentialNBTReader.Compound) compound.next().getValue();
                while (compound2.hasNext()) {
                    Entry entry = (Entry) compound2.next();
                    ResourceLocation resourceLocation =
                            new ResourceLocation((String) entry.getKey());
                    PROVIDER_DATA.put(
                            resourceLocation,
                            ((SequentialNBTReader.Compound) entry.getValue()).readFully());
                }
            } catch (Throwable throwable) {
                if (compound != null) {
                    try {
                        compound.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                }
                throw throwable;
            }
            if (compound != null) {
                compound.close();
            }
        } catch (IOException oException) {
            throw new RuntimeException("Error while reading block state provider data", oException);
        }
        REGISTRY = new VersionedRegistry<>("worldgen/block_state_provider");
        CAVE_VINES_BODY = define("cave_vines_body");
        CAVE_VINES_HEAD = define("cave_vines_head");
        FLOWER_FLOWER_FOREST = define("flower_flower_forest");
        FLOWER_MEADOW = define("flower_meadow");
        FLOWER_PLAIN = define("flower_plain");
        MANGROVE_PROPAGULE = define("mangrove_propagule");
        PODZOL_BENEATH_TREE = define("podzol_beneath_tree");
        SOIL_BENEATH_TREE = define("soil_beneath_tree");
        PROVIDER_DATA.clear();
        REGISTRY.unloadMappings();
    }
}
