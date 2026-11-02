package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.type;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Collection;

public final class VillagerTypes {

    private static final VersionedRegistry<VillagerType> REGISTRY =
            new VersionedRegistry<>("villager_type");

    public static final VillagerType DESERT = define("desert");

    public static final VillagerType JUNGLE = define("jungle");

    public static final VillagerType PLAINS = define("plains");

    public static final VillagerType SAVANNA = define("savanna");

    public static final VillagerType SNOW = define("snow");

    public static final VillagerType SWAMP = define("swamp");

    public static final VillagerType TAIGA = define("taiga");

    private VillagerTypes() {}

    public static VersionedRegistry<VillagerType> getRegistry() {
        return REGISTRY;
    }

    @Deprecated
    @Internal
    public static VillagerType define(int value, String text) {
        return define(text);
    }

    @Internal
    public static VillagerType define(String text) {
        return REGISTRY.define(text, StaticVillagerType::new);
    }

    @Deprecated
    public static VillagerType getById(int value) {
        return getById(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), value);
    }

    public static VillagerType getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static VillagerType getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static Collection<VillagerType> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
