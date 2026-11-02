package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

@Internal
public interface IRegistryHolder {

    default <T extends MappedEntity> IRegistry<T> getRegistryOr(IRegistry<T> registry) {
        return this.getRegistryOr(
                registry, PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    default <T extends MappedEntity> IRegistry<T> getRegistryOr(
            IRegistry<T> registry, ClientVersion clientVersion) {
        IRegistry registry2;
        return (registry2 = this.getRegistry(registry.getRegistryKey(), clientVersion)) != null
                ? registry2
                : registry;
    }

    @Nullable
    default IRegistry<?> getRegistry(ResourceLocation resourceLocation) {
        return this.getRegistry(
                resourceLocation,
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    @Nullable
    IRegistry<?> getRegistry(ResourceLocation resourceLocation, ClientVersion clientVersion);
}
