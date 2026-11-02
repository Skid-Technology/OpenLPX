package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityBuilder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.MapUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.VersionRange;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

@NullMarked
public final class VersionedRegistry<T extends MappedEntity> implements IRegistry<T> {

    private final ResourceLocation registryKey;

    private final TypesBuilder typesBuilder;

    private final ClientVersion[] extraSteps;

    private final Map<String, T>[] typeNames;

    private final Map<Integer, T>[] typeIds;

    private final Set<T> entries = new HashSet<>();

    public VersionedRegistry(String text) {
        this(text, new ClientVersion[0]);
    }

    public VersionedRegistry(String text, ClientVersion... clientVersion) {
        this(text, "registries/" + text, clientVersion);
    }

    public VersionedRegistry(String text, String text2) {
        this(text, text2, new ClientVersion[0]);
    }

    public VersionedRegistry(String text, String text2, ClientVersion... clientVersion) {
        this(new ResourceLocation(text), text2, clientVersion);
    }

    public VersionedRegistry(ResourceLocation resourceLocation, String text) {
        this(resourceLocation, text, new ClientVersion[0]);
    }

    public VersionedRegistry(
            ResourceLocation resourceLocation, String text, ClientVersion... clientVersion) {
        this.registryKey = resourceLocation;
        this.typesBuilder = new TypesBuilder(text);
        this.extraSteps = clientVersion;
        this.postLoadMappings();
        int size2 = this.typesBuilder.getVersionMapper().size();
        this.typeNames = new Map[size2];
        this.typeIds = new Map[size2];
    }

    @Internal
    public <Z extends T> Z defineWithBuilder(
            String text, MappedEntityBuilder<Z> mappedEntityBuilder) {
        return this.define(text, VersionRange.ALL_VERSIONS, mappedEntityBuilder::build);
    }

    @Internal
    public <Z extends T> Z define(String text, Function<TypesBuilderData, Z> function) {
        return this.define(text, VersionRange.ALL_VERSIONS, function);
    }

    @Internal
    public <Z extends T> Z define(
            String text, VersionRange versionRange, Function<TypesBuilderData, Z> function) {
        TypesBuilderData typesBuilderData = this.typesBuilder.define(text, versionRange);
        MappedEntity mappedEntity = (MappedEntity) function.apply(typesBuilderData);
        MappingHelper.registerMapping(
                this.typesBuilder,
                this.typeNames,
                this.typeIds,
                typesBuilderData,
                (T) mappedEntity);
        return (Z) mappedEntity;
    }

    @VisibleForTesting
    @Internal
    public TypesBuilder getTypesBuilder() {
        return this.typesBuilder;
    }

    @VisibleForTesting
    @Internal
    public void postLoadMappings() {
        this.typesBuilder.registry = this;
        ClientVersion[] clientVersions = this.extraSteps;
        int value = this.extraSteps.length;
        for (int index = 0; index < value; index++) {
            ClientVersion clientVersion = clientVersions[index];
            this.typesBuilder.addExtraVersionStep(clientVersion);
        }
    }

    @Internal
    public void unloadMappings() {
        this.typesBuilder.unloadFileMappings();
        Map<String, T> entries2 = this.typeNames[0];
        for (int index = 1; index < this.typeNames.length; index++) {
            Map<String, T> entries3 = this.typeNames[index];
            if (MapUtil.isDeepEqual(entries2, entries3)) {
                this.typeNames[index] = entries2;
            } else {
                entries2 = entries3;
            }
        }
        HashSet<String> values = new HashSet<>();
        for (int value = this.typeNames.length - 1; value >= 0; value--) {
            for (Entry<String, T> entry : this.typeNames[value].entrySet()) {
                if (values.add((String) entry.getKey())) {
                    this.entries.add((T) entry.getValue());
                }
            }
        }
    }

    @Nullable
    @Override
    public T getByName(ClientVersion clientVersion, ResourceLocation resourceLocation) {
        int dataIndex = this.typesBuilder.getDataIndex(clientVersion);
        return this.typeNames[dataIndex].get(resourceLocation.toString());
    }

    @Nullable
    @Override
    public T getByName(ClientVersion clientVersion, String text) {
        int dataIndex = this.typesBuilder.getDataIndex(clientVersion);
        return this.typeNames[dataIndex].get(ResourceLocation.normString(text));
    }

    @Nullable
    @Override
    public T getByName(ResourceLocation resourceLocation) {
        ClientVersion clientVersion =
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion();
        return this.getByName(clientVersion, resourceLocation);
    }

    @Nullable
    @Override
    public T getByName(String text) {
        ClientVersion clientVersion =
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion();
        return this.getByName(clientVersion, text);
    }

    @Nullable
    @Override
    public T getById(ClientVersion clientVersion, int value) {
        int dataIndex = this.typesBuilder.getDataIndex(clientVersion);
        return this.typeIds[dataIndex].get(value);
    }

    @Override
    public int getId(MappedEntity mappedEntity, ClientVersion clientVersion) {
        return mappedEntity.getId(clientVersion);
    }

    @Override
    public Collection<T> getEntries() {
        return Collections.unmodifiableCollection(this.entries);
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public ResourceLocation getRegistryKey() {
        return this.registryKey;
    }

    @Override
    public String toString() {
        return "VersionedRegistry[" + this.registryKey + 93;
    }
}
