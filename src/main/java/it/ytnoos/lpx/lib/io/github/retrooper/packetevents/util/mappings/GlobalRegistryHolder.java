package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.User;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

@Internal
public final class GlobalRegistryHolder implements IRegistryHolder {

    public static final IRegistryHolder INSTANCE = new GlobalRegistryHolder();

    private GlobalRegistryHolder() {}

    public static Object getGlobalRegistryCacheKey(
            @Nullable User user, ClientVersion clientVersion) {
        return clientVersion;
    }

    @Nullable
    @Override
    public IRegistry<?> getRegistry(
            ResourceLocation resourceLocation, ClientVersion clientVersion) {
        SynchronizedRegistriesHandler.RegistryEntry registryEntry;
        if ((registryEntry = SynchronizedRegistriesHandler.getRegistryEntry(resourceLocation))
                == null) {
            return null;
        }
        Object globalRegistryCacheKey = getGlobalRegistryCacheKey(null, clientVersion);
        return registryEntry.getSyncedRegistry(globalRegistryCacheKey);
    }
}
