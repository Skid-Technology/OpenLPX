package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.VersionMapper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.VersionRange;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Internal
public class TypesBuilder {

    private final String mapPath;

    private Map<ClientVersion, Map<String, Integer>> entries = new HashMap<>();

    private VersionMapper versionMapper;

    @Nullable VersionedRegistry<?> registry;

    public TypesBuilder(String mapPath, boolean matches) {
        this.mapPath = mapPath;
        if (!matches) {
            this.load();
        }
    }

    public TypesBuilder(String text) {
        this(text, false);
    }

    public void load() {
        if (this.entries == null) {
            this.entries = new HashMap<>();
        }
        try {
            SequentialNBTReader.Compound compound =
                    MappingHelper.decompress("mappings/" + this.mapPath);
            try {
                compound.skipOne();
                SequentialNBTReader.Compound compound2;
                int asInt =
                        ((NBTNumber)
                                        (compound2 =
                                                        (SequentialNBTReader.Compound)
                                                                compound.next().getValue())
                                                .next()
                                                .getValue())
                                .getAsInt();
                SequentialNBTReader.Compound compound3 =
                        (SequentialNBTReader.Compound) compound2.next().getValue();
                ClientVersion[] clientVersions = new ClientVersion[asInt];
                Entry<String, NBT> entry;
                if (((NBT) (entry = compound3.next()).getValue()).getType() == NBTType.LIST) {
                    this.loadAsArray(entry, compound3, clientVersions);
                } else {
                    this.loadAsMap(entry, compound3, clientVersions);
                }
                this.versionMapper = new VersionMapper(clientVersions);
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
            throw new RuntimeException("Unable to load mapping files.", oException);
        }
    }

    private void loadAsArray(
            Entry<String, NBT> entry,
            SequentialNBTReader.Compound compound,
            ClientVersion[] clientVersions) {
        ClientVersion clientVersion = ClientVersion.valueOf((String) entry.getKey());
        clientVersions[0] = clientVersion;
        ArrayList<String> values = new ArrayList<>();
        SequentialNBTReader.List values2 = (SequentialNBTReader.List) entry.getValue();
        while (values2.hasNext()) {
            NBT nBT = (NBT) values2.next();
            values.add(((NBTString) nBT).getValue());
        }
        Consumer consumer;
        (consumer =
                        result -> {
                            HashMap entries2 = new HashMap();
                            int size2 = values.size();
                            for (int index = 0; index < size2; index++) {
                                entries2.put((String) values.get(index), index);
                            }
                            this.entries.put(((ClientVersion) result), entries2);
                        })
                .accept(clientVersion);
        int value = 1;
        while (compound.hasNext()) {
            Entry entry2;
            ClientVersion clientVersion2 =
                    ClientVersion.valueOf((String) (entry2 = (Entry) compound.next()).getKey());
            clientVersions[value++] = clientVersion2;
            List<it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.ListDiff<String>>
                    values3;
            for (int value2 =
                            (values3 =
                                                    MappingHelper.createListDiff(
                                                            (SequentialNBTReader.Compound)
                                                                    entry2.getValue()))
                                            .size()
                                    - 1;
                    value2 >= 0;
                    value2--) {
                ((ListDiff) values3.get(value2)).applyTo(values);
            }
            consumer.accept(clientVersion2);
        }
    }

    private void loadAsMap(
            Entry<String, NBT> entry,
            SequentialNBTReader.Compound compound,
            ClientVersion[] clientVersions) {
        ClientVersion clientVersion = ClientVersion.valueOf((String) entry.getKey());
        clientVersions[0] = clientVersion;
        Map<String, Integer> entries2 =
                StreamSupport.stream(
                                (java.util.Spliterator<Entry<String, NBT>>)
                                        (java.util.Spliterator<?>)
                                                ((SequentialNBTReader.Compound) entry.getValue())
                                                        .spliterator(),
                                false)
                        .collect(
                                Collectors.toMap(
                                        Entry::getKey,
                                        result -> ((NBTNumber) result.getValue()).getAsInt()));
        Consumer consumer;
        (consumer =
                        result2 -> {
                            HashMap entries3 = new HashMap(entries2);
                            this.entries.put(((ClientVersion) result2), entries3);
                        })
                .accept(clientVersion);
        int value = 1;
        while (compound.hasNext()) {
            Entry entry2;
            ClientVersion clientVersion2 =
                    ClientVersion.valueOf((String) (entry2 = (Entry) compound.next()).getKey());
            clientVersions[value++] = clientVersion2;
            Iterator<
                            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings
                                            .MapDiff<
                                    String, Integer>>
                    iterator2 =
                            MappingHelper.createDiff(
                                            (SequentialNBTReader.Compound) entry2.getValue())
                                    .iterator();
            while (iterator2.hasNext()) {
                ((MapDiff) iterator2.next()).applyTo(entries2);
            }
            consumer.accept(clientVersion2);
        }
    }

    @Nullable
    public VersionedRegistry<?> getRegistry() {
        return this.registry;
    }

    public ClientVersion[] getVersions() {
        return this.versionMapper.getVersions();
    }

    public ClientVersion[] getReversedVersions() {
        return this.versionMapper.getReversedVersions();
    }

    public int getDataIndex(ClientVersion clientVersion) {
        return this.versionMapper.getIndex(clientVersion);
    }

    public VersionMapper getVersionMapper() {
        return this.versionMapper;
    }

    public void addExtraVersionStep(ClientVersion clientVersion) {
        VersionMapper versionMapper2 = this.versionMapper.withExtra(clientVersion);
        if (this.versionMapper != versionMapper2) {
            int index = this.versionMapper.getIndex(clientVersion);
            ClientVersion clientVersion2 = this.versionMapper.getVersions()[index];
            this.entries.put(clientVersion, this.entries.get(clientVersion2));
            this.versionMapper = versionMapper2;
        }
    }

    @VisibleForTesting
    public boolean isMappingDataLoaded() {
        return this.entries != null;
    }

    public void unloadFileMappings() {
        this.entries.clear();
        this.entries = null;
    }

    public TypesBuilderData define(String text, VersionRange versionRange) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        int[] values = new int[this.getVersions().length];
        int value = 0;
        ClientVersion[] clientVersions;
        int value2 = (clientVersions = this.getVersions()).length;
        for (int index = 0; index < value2; index++) {
            ClientVersion clientVersion = clientVersions[index];
            Map<String, Integer> entries2;
            if ((entries2 = this.entries.get(clientVersion)).containsKey(text)) {
                int value3 = (Integer) entries2.get(text);
                values[value] = value3;
            } else {
                values[value] = -1;
            }
            value++;
        }
        return new TypesBuilderData(resourceLocation, values, this, versionRange);
    }

    @Nullable
    public Map<ClientVersion, Map<String, Integer>> getEntries() {
        return this.entries;
    }
}
