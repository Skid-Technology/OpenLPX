package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines;

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
public final class Timelines {

    private static final VersionedRegistry<Timeline> REGISTRY = new VersionedRegistry<>("timeline");

    private static final Map<ResourceLocation, NBTCompound> TIMELINE_DATA = new HashMap<>();

    public static final Timeline DAY;

    public static final Timeline EARLY_GAME;

    public static final Timeline MOON;

    public static final Timeline VILLAGER_SCHEDULE;

    private Timelines() {}

    @Internal
    public static Timeline define(String text) {
        return REGISTRY.define(
                text,
                result -> {
                    NBTCompound nBTCompound;
                    if ((nBTCompound = TIMELINE_DATA.get(result.getName())) != null) {
                        PacketWrapper packetWrapper =
                                PacketWrapper.createDummyWrapper(ClientVersion.getLatest());
                        return Timeline.CODEC.decode(nBTCompound, packetWrapper).copy(result);
                    } else {
                        throw new IllegalArgumentException(
                                "Can't define timeline " + result.getName() + ", no data found");
                    }
                });
    }

    public static VersionedRegistry<Timeline> getRegistry() {
        return REGISTRY;
    }

    static {
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/data/timeline");
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2 =
                        (SequentialNBTReader.Compound) compound.next().getValue();
                while (compound2.hasNext()) {
                    Entry entry = (Entry) compound2.next();
                    ResourceLocation resourceLocation =
                            new ResourceLocation((String) entry.getKey());
                    TIMELINE_DATA.put(
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
            throw new RuntimeException("Error while reading timeline data", oException);
        }
        DAY = define("day");
        EARLY_GAME = define("early_game");
        MOON = define("moon");
        VILLAGER_SCHEDULE = define("villager_schedule");
        REGISTRY.unloadMappings();
    }
}
