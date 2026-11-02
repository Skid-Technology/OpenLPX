package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stats;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.serializer.SequentialNBTReader;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.MappingHelper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Statistics {

    private static final Map<String, Statistic> STATISTIC_MAP = new HashMap<>();

    public static Statistic getById(String text) {
        return STATISTIC_MAP.get(text);
    }

    static {
        ServerVersion serverVersion;
        if ((serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                .isOlderThan(ServerVersion.V_1_12_2)) {
            try {
                SequentialNBTReader.Compound compound =
                        MappingHelper.decompress("mappings/data/statistics");
                try {
                    compound.skipOne();
                    SequentialNBTReader.Compound compound2 =
                            (SequentialNBTReader.Compound) compound.next().getValue();
                    if (serverVersion.isOlderThanOrEquals(ServerVersion.V_1_8_3)) {
                        compound2.skipOne();
                    }
                    SequentialNBTReader.Compound compound3 =
                            (SequentialNBTReader.Compound) compound2.next().getValue();
                    while (compound3.hasNext()) {
                        final Entry entry;
                        final String value =
                                ((NBTString) (entry = (Entry) compound3.next()).getValue())
                                        .getValue();
                        Statistic statistic =
                                new Statistic() {

                                    private Component cachedDisplay;

                                    @Override
                                    public String getId() {
                                        return (String) entry.getKey();
                                    }

                                    @Override
                                    public Component display() {
                                        if (this.cachedDisplay == null) {
                                            this.cachedDisplay =
                                                    AdventureSerializer.serializer()
                                                            .fromJson(value);
                                        }
                                        return this.cachedDisplay;
                                    }

                                    @Override
                                    public boolean equals(Object result) {
                                        return result instanceof Statistic
                                                ? ((Statistic) result).getId().equals(this.getId())
                                                : false;
                                    }
                                };
                        STATISTIC_MAP.put((String) entry.getKey(), statistic);
                    }
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
                throw new RuntimeException("Cannot load statistics mappings", oException);
            }
        }
    }
}
