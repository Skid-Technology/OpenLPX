package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NullMarked
public final class SimpleRegistry<T extends MappedEntity> implements IRegistry<T> {

    private final ResourceLocation registryKey;

    private final Map<String, T> typeMap = new HashMap<>();

    private final Map<Integer, T> typeIdMap = new HashMap<>();

    private final Map<String, Integer> reverseTypeIdMap = new HashMap<>();

    public SimpleRegistry(String text) {
        this(new ResourceLocation(text));
    }

    public SimpleRegistry(ResourceLocation resourceLocation) {
        this.registryKey = resourceLocation;
    }

    @Internal
    public <Z extends T> Z define(String text, int value, Z z) {
        return this.define(new ResourceLocation(text), value, (Z) z);
    }

    @Internal
    public <Z extends T> Z define(ResourceLocation resourceLocation, int value, Z z) {
        String text = resourceLocation.toString();
        this.typeMap.put(text, (T) z);
        this.typeIdMap.put(value, (T) z);
        this.reverseTypeIdMap.put(text, value);
        return (Z) z;
    }

    @Nullable
    @Override
    public T getByName(String text) {
        return this.typeMap.get(ResourceLocation.normString(text));
    }

    @Nullable
    @Override
    public T getById(ClientVersion clientVersion, int value) {
        return this.typeIdMap.get(value);
    }

    @Override
    public int getId(String text, ClientVersion clientVersion) {
        String text2 = ResourceLocation.normString(text);
        return this.reverseTypeIdMap.getOrDefault(text2, -1);
    }

    @Override
    public int getId(MappedEntity mappedEntity, ClientVersion clientVersion) {
        return this.getId(mappedEntity.getName().toString(), clientVersion);
    }

    @Override
    public Collection<T> getEntries() {
        return Collections.unmodifiableCollection(this.typeMap.values());
    }

    @Override
    public int size() {
        return this.typeMap.size();
    }

    @Override
    public ResourceLocation getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public String toString() {
        return "SimpleRegistry[" + this.registryKey + 93;
    }
}
