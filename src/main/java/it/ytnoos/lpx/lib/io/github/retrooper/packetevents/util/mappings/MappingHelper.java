package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLimiter;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPInputStream;

@Internal
public class MappingHelper {

    public static SequentialNBTReader.Compound decompress(String text) {
        try {
            DataInputStream dataInputStream =
                    new DataInputStream(
                            new GZIPInputStream(
                                    new BufferedInputStream(
                                            PacketEvents.getAPI()
                                                    .getSettings()
                                                    .getResourceProvider()
                                                    .apply("assets/" + text + ".nbt"))));
            return (SequentialNBTReader.Compound)
                    SequentialNBTReader.INSTANCE.deserializeTag(NBTLimiter.noop(), dataInputStream);
        } catch (IOException oException) {
            throw new RuntimeException("Cannot find resource file " + text + ".nbt", oException);
        }
    }

    public static List<ListDiff<String>> createListDiff(SequentialNBTReader.Compound compound) {
        ArrayList<ListDiff<String>> values = new ArrayList<>();
        SequentialNBTReader.List values2 = (SequentialNBTReader.List) compound.next().getValue();
        while (values2.hasNext()) {
            SequentialNBTReader.Compound compound2 =
                    (SequentialNBTReader.Compound) ((NBT) values2.next());
            values.add(
                    new ListDiff.Removal(
                            ((NBTNumber) compound2.next().getValue()).getAsInt(),
                            ((NBTNumber) compound2.next().getValue()).getAsInt()));
        }
        SequentialNBTReader.List values3 = (SequentialNBTReader.List) compound.next().getValue();
        while (values3.hasNext()) {
            SequentialNBTReader.Compound compound3 =
                    (SequentialNBTReader.Compound) ((NBT) values3.next());
            values.add(
                    new ListDiff.Addition(
                            ((NBTNumber) compound3.next().getValue()).getAsInt(),
                            StreamSupport.stream(
                                            ((SequentialNBTReader.List) compound3.next().getValue())
                                                    .spliterator(),
                                            false)
                                    .map(result -> ((NBTString) result).getValue())
                                    .collect(Collectors.toList())));
        }
        SequentialNBTReader.List values4 = (SequentialNBTReader.List) compound.next().getValue();
        while (values4.hasNext()) {
            SequentialNBTReader.Compound compound4 =
                    (SequentialNBTReader.Compound) ((NBT) values4.next());
            values.add(
                    new ListDiff.Changed(
                            ((NBTNumber) compound4.next().getValue()).getAsInt(),
                            ((NBTNumber) compound4.next().getValue()).getAsInt(),
                            StreamSupport.stream(
                                            ((SequentialNBTReader.List) compound4.next().getValue())
                                                    .spliterator(),
                                            false)
                                    .map(result -> ((NBTString) result).getValue())
                                    .collect(Collectors.toList())));
        }
        values.sort(Comparator.comparingInt(ListDiff::getIndex));
        return values;
    }

    public static List<MapDiff<String, Integer>> createDiff(SequentialNBTReader.Compound compound) {
        ArrayList<MapDiff<String, Integer>> values = new ArrayList<>();
        SequentialNBTReader.Compound compound2 =
                (SequentialNBTReader.Compound) compound.next().getValue();
        while (compound2.hasNext()) {
            Entry entry = (Entry) compound2.next();
            values.add(new MapDiff.Removal<>((String) entry.getKey()));
        }
        SequentialNBTReader.Compound compound3 =
                (SequentialNBTReader.Compound) compound.next().getValue();
        while (compound3.hasNext()) {
            Entry entry2 = (Entry) compound3.next();
            values.add(
                    new MapDiff.Addition<>(
                            (String) entry2.getKey(), ((NBTNumber) entry2.getValue()).getAsInt()));
        }
        return values;
    }

    public static <T extends MappedEntity> void registerMapping(
            TypesBuilder typesBuilder,
            Map<String, T>[] entriess,
            Map<Integer, T>[] entriess2,
            TypesBuilderData typesBuilderData,
            T t) {
        ClientVersion[] clientVersions;
        int value = (clientVersions = typesBuilder.getVersions()).length;
        for (int index = 0; index < value; index++) {
            ClientVersion clientVersion = clientVersions[index];
            if (typesBuilderData.getVersions().contains(clientVersion)) {
                int dataIndex = typesBuilder.getDataIndex(clientVersion);
                Map entries;
                if ((entries = entriess[dataIndex]) == null) {
                    entriess[dataIndex] = entries = new HashMap();
                }
                entries.put(typesBuilderData.getName().toString(), t);
                Map entries2;
                if ((entries2 = entriess2[dataIndex]) == null) {
                    entriess2[dataIndex] = entries2 = new HashMap();
                }
                entries2.put(typesBuilderData.getId(clientVersion), t);
            }
        }
    }

    public static int getId(
            ClientVersion clientVersion,
            TypesBuilder typesBuilder,
            TypesBuilderData typesBuilderData) {
        return typesBuilderData.getData()[typesBuilder.getDataIndex(clientVersion)];
    }
}
