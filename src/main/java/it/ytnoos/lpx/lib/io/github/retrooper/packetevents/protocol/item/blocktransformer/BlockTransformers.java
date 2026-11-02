package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.blocktransformer;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
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
public final class BlockTransformers {

    private static final Map<ResourceLocation, NBTList<NBT>> TRANSFORMER_DATA = new HashMap<>();

    private static final VersionedRegistry<BlockTransformer> REGISTRY;

    public static final BlockTransformer AXE;

    public static final BlockTransformer HOE;

    public static final BlockTransformer SHOVEL;

    private BlockTransformers() {}

    @Internal
    public static BlockTransformer define(String text) {
        PacketWrapper packetWrapper = PacketWrapper.createDummyWrapper(ClientVersion.getLatest());
        return REGISTRY.define(
                text,
                result -> {
                    NBTList nBTList;
                    if ((nBTList = TRANSFORMER_DATA.get(result.getName())) == null) {
                        throw new IllegalArgumentException(
                                "Can't define block transformer "
                                        + result.getName()
                                        + ", no data found");
                    } else {
                        return BlockTransformer.DIRECT_CODEC
                                .decode(nBTList, packetWrapper)
                                .copy(result);
                    }
                });
    }

    public static VersionedRegistry<BlockTransformer> getRegistry() {
        return REGISTRY;
    }

    static {
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/block_transformer");
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2 =
                        (SequentialNBTReader.Compound) compound.next().getValue();
                while (compound2.hasNext()) {
                    Entry entry = (Entry) compound2.next();
                    ResourceLocation resourceLocation =
                            new ResourceLocation((String) entry.getKey());
                    TRANSFORMER_DATA.put(
                            resourceLocation,
                            ((SequentialNBTReader.List) entry.getValue()).readFully());
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
            throw new RuntimeException("Error while reading block transformer data", oException);
        }
        REGISTRY = new VersionedRegistry<>("block_transformer");
        AXE = define("axe");
        HOE = define("hoe");
        SHOVEL = define("shovel");
        TRANSFORMER_DATA.clear();
        REGISTRY.unloadMappings();
    }
}
