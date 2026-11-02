package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public interface MappedEntityRefSet<T extends MappedEntity> {

    default MappedEntitySet<T> resolve(PacketWrapper<?> packetWrapper, IRegistry<T> registry) {
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        return this.resolve(clientVersion, packetWrapper.replaceRegistry(registry));
    }

    default MappedEntitySet<T> resolve(
            ClientVersion clientVersion, IRegistryHolder registryHolder, IRegistry<T> registry) {
        IRegistry registryOr = registryHolder.getRegistryOr(registry, clientVersion);
        return this.resolve(clientVersion, registryOr);
    }

    MappedEntitySet<T> resolve(ClientVersion clientVersion, IRegistry<T> registry);

    boolean isEmpty();
}
