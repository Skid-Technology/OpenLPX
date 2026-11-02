package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.IRegistryHolder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

@NullMarked
public interface IComponentMap {

    @Internal
    static StaticComponentMap decode(
            NBT nBT,
            PacketWrapper<?> packetWrapper,
            IRegistry<? extends ComponentType<?>> registry) {
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        NBTCompound nBTCompound = (NBTCompound) nBT;
        StaticComponentMap.Builder builder2 = StaticComponentMap.builder();
        for (Entry<String, NBT> entry : nBTCompound.getTags().entrySet()) {
            ComponentType componentType;
            if ((componentType = (ComponentType) registry.getByName((String) entry.getKey()))
                    == null) {
                throw new IllegalStateException(
                        "Unknown component type named " + (String) entry.getKey() + " encountered");
            }
            Object result = componentType.decode((NBT) entry.getValue(), clientVersion);
            builder2.set(componentType, result);
        }
        return builder2.setRegistries(packetWrapper.getRegistryHolder()).build();
    }

    @Internal
    static NBT encode(PacketWrapper<?> packetWrapper, StaticComponentMap staticComponentMap) {
        staticComponentMap = staticComponentMap.withRegistries(packetWrapper.getRegistryHolder());
        ClientVersion clientVersion = packetWrapper.getServerVersion().toClientVersion();
        NBTCompound nBTCompound = new NBTCompound();
        Iterator<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component
                                        .ComponentType<
                                ?>>
                iterator2 = staticComponentMap.getKeys().iterator();
        while (iterator2.hasNext()) {
            ComponentType componentType;
            String text = (componentType = (ComponentType) iterator2.next()).getName().toString();
            Object result = staticComponentMap.get(componentType);
            NBT nBT = componentType.encode(result, clientVersion);
            nBTCompound.setTag(text, nBT);
        }
        return nBTCompound;
    }

    default <T> Optional<T> getOptional(ComponentType<T> componentType) {
        return Optional.ofNullable(this.get(componentType));
    }

    boolean has(ComponentType<?> componentType);

    @Contract("_, !null -> !null")
    @Nullable
    default <T> T getOr(ComponentType<T> componentType, @Nullable T t) {
        T t2;
        return (T) ((t2 = this.get(componentType)) != null ? t2 : t);
    }

    @Nullable
    <T> T get(ComponentType<T> componentType);

    default <T> void set(ComponentValue<T> componentValue) {
        this.set(componentValue.getType(), componentValue.getValue());
    }

    default <T> void set(ComponentType<T> componentType, @Nullable T t) {
        this.set(componentType, Optional.ofNullable(t));
    }

    default <T> void unset(ComponentType<T> componentType) {
        this.set(componentType, Optional.empty());
    }

    <T> void set(ComponentType<T> componentType, Optional<T> optional);

    IComponentMap withRegistries(IRegistryHolder registryHolder);
}
