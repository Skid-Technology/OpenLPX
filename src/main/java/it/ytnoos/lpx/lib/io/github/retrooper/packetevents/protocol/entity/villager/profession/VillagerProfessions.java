package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.profession;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

public final class VillagerProfessions {

    private static final VersionedRegistry<VillagerProfession> REGISTRY =
            new VersionedRegistry<>("villager_profession");

    public static final VillagerProfession NONE = define("none");

    public static final VillagerProfession ARMORER = define("armorer");

    public static final VillagerProfession BUTCHER = define("butcher");

    public static final VillagerProfession CARTOGRAPHER = define("cartographer");

    public static final VillagerProfession CLERIC = define("cleric");

    public static final VillagerProfession FARMER = define("farmer");

    public static final VillagerProfession FISHERMAN = define("fisherman");

    public static final VillagerProfession FLETCHER = define("fletcher");

    public static final VillagerProfession LEATHERWORKER = define("leatherworker");

    public static final VillagerProfession LIBRARIAN = define("librarian");

    public static final VillagerProfession MASON = define("mason");

    public static final VillagerProfession NITWIT = define("nitwit");

    public static final VillagerProfession SHEPHERD = define("shepherd");

    public static final VillagerProfession TOOLSMITH = define("toolsmith");

    public static final VillagerProfession WEAPONSMITH = define("weaponsmith");

    private VillagerProfessions() {}

    public static VersionedRegistry<VillagerProfession> getRegistry() {
        return REGISTRY;
    }

    @Deprecated
    @Internal
    public static VillagerProfession define(int value, String text) {
        return define(text);
    }

    @Internal
    public static VillagerProfession define(String text) {
        return REGISTRY.define(text, StaticVillagerProfession::new);
    }

    @Deprecated
    public static VillagerProfession getById(int value) {
        return getById(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), value);
    }

    public static VillagerProfession getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static VillagerProfession getByName(String text) {
        return REGISTRY.getByName(text);
    }

    static {
        REGISTRY.unloadMappings();
    }
}
