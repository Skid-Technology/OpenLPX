package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.function.BiFunction;

@NullMarked
public interface IRegistry<T extends MappedEntity> extends BiFunction<ClientVersion, Integer, T> {

    default T getByNameOrThrow(ClientVersion clientVersion, ResourceLocation resourceLocation) {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getByName(clientVersion, resourceLocation)) == null) {
            throw new IllegalArgumentException(
                    "Can't resolve '"
                            + resourceLocation
                            + "' in '"
                            + this.getRegistryKey()
                            + "' for "
                            + clientVersion);
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    default T getByName(ClientVersion clientVersion, ResourceLocation resourceLocation) {
        return this.getByName(clientVersion, resourceLocation.toString());
    }

    default T getByNameOrThrow(ClientVersion clientVersion, String text) {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getByName(clientVersion, text)) == null) {
            String text2 = ResourceLocation.normString(text);
            throw new IllegalArgumentException(
                    "Can't resolve '"
                            + text2
                            + "' in '"
                            + this.getRegistryKey()
                            + "' for "
                            + clientVersion);
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    default T getByName(ClientVersion clientVersion, String text) {
        return this.getByName(text);
    }

    default T getByNameOrThrow(ResourceLocation resourceLocation) {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getByName(resourceLocation)) == null) {
            throw new IllegalArgumentException(
                    "Can't resolve '" + resourceLocation + "' in '" + this.getRegistryKey() + "'");
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    default T getByName(ResourceLocation resourceLocation) {
        return this.getByName(resourceLocation.toString());
    }

    default T getByNameOrThrow(String text) {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getByName(text)) == null) {
            String text2 = ResourceLocation.normString(text);
            throw new IllegalArgumentException(
                    "Can't resolve '" + text2 + "' in '" + this.getRegistryKey() + "'");
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    T getByName(String text);

    default T getByIdOrThrow(ClientVersion clientVersion, int value) {
        MappedEntity mappedEntity;
        if ((mappedEntity = this.getById(clientVersion, value)) == null) {
            throw new IllegalArgumentException(
                    "Can't resolve #"
                            + value
                            + " ("
                            + clientVersion
                            + ") in '"
                            + this.getRegistryKey()
                            + "'");
        } else {
            return (T) mappedEntity;
        }
    }

    @Nullable
    T getById(ClientVersion clientVersion, int value);

    default int getId(String text, ClientVersion clientVersion) {
        return this.getId(this.getByNameOrThrow(clientVersion, text), clientVersion);
    }

    int getId(MappedEntity mappedEntity, ClientVersion clientVersion);

    Collection<T> getEntries();

    int size();

    ResourceLocation getRegistryKey();

    default T apply(ClientVersion clientVersion, Integer value) {
        return this.getByIdOrThrow(clientVersion, value);
    }
}
