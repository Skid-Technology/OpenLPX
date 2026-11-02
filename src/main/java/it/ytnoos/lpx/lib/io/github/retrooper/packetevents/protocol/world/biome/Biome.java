package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.biome;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface Biome extends MappedEntity, CopyableEntity<Biome>, DeepComparableEntity {

    NbtCodec<Biome> CODEC =
            (new NbtMapCodec<Biome>() {

                        public Biome decode(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            float asFloat =
                                    nBTCompound.getNumberTagOrThrow("temperature").getAsFloat();
                            Biome.TemperatureModifier or =
                                    nBTCompound.getOr(
                                            "temperature_modifier",
                                            Biome.TemperatureModifier.CODEC,
                                            Biome.TemperatureModifier.NONE,
                                            packetWrapper);
                            float asFloat2 =
                                    nBTCompound.getNumberTagOrThrow("downfall").getAsFloat();
                            Biome.Category category = null;
                            Float value = null;
                            Float value2 = null;
                            boolean matches;
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                                matches = nBTCompound.getBoolean("has_precipitation");
                            } else {
                                matches =
                                        nBTCompound.getOrThrow(
                                                        "precipitation",
                                                        Biome.Precipitation.CODEC,
                                                        packetWrapper)
                                                != Biome.Precipitation.NONE;
                                if (packetWrapper
                                        .getServerVersion()
                                        .isOlderThan(ServerVersion.V_1_19)) {
                                    category =
                                            nBTCompound.getOrThrow(
                                                    "category",
                                                    Biome.Category.CODEC,
                                                    packetWrapper);
                                    if (packetWrapper
                                            .getServerVersion()
                                            .isOlderThan(ServerVersion.V_1_18)) {
                                        value =
                                                nBTCompound
                                                        .getNumberTagOrThrow("depth")
                                                        .getAsFloat();
                                        value2 =
                                                nBTCompound
                                                        .getNumberTagOrThrow("scale")
                                                        .getAsFloat();
                                    }
                                }
                            }
                            EnvironmentAttributeMap environmentAttributeMap;
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                                environmentAttributeMap =
                                        nBTCompound.getOr(
                                                "attributes",
                                                EnvironmentAttributeMap.CODEC,
                                                EnvironmentAttributeMap.EMPTY,
                                                packetWrapper);
                            } else {
                                environmentAttributeMap = EnvironmentAttributeMap.EMPTY;
                            }
                            BiomeEffects orThrow =
                                    nBTCompound.getOrThrow(
                                            "effects",
                                            BiomeEffects.codecWithAttributes(
                                                    environmentAttributeMap),
                                            packetWrapper);
                            return new StaticBiome(
                                    null,
                                    matches,
                                    asFloat,
                                    or,
                                    asFloat2,
                                    category,
                                    value,
                                    value2,
                                    orThrow,
                                    environmentAttributeMap);
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                Biome biome)
                                throws NbtCodecException {
                            nBTCompound.setTag("temperature", new NBTFloat(biome.getTemperature()));
                            if (biome.getTemperatureModifier() != Biome.TemperatureModifier.NONE) {
                                nBTCompound.set(
                                        "temperature_modifier",
                                        biome.getTemperatureModifier(),
                                        Biome.TemperatureModifier.CODEC,
                                        packetWrapper);
                            }
                            nBTCompound.setTag("downfall", new NBTFloat(biome.getDownfall()));
                            nBTCompound.set(
                                    "effects",
                                    biome.getEffects(),
                                    BiomeEffects.CODEC,
                                    packetWrapper);
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_19_3)) {
                                nBTCompound.setTag(
                                        "has_precipitation", new NBTByte(biome.hasPrecipitation()));
                                if (packetWrapper
                                        .getServerVersion()
                                        .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                                    nBTCompound.set(
                                            "attributes",
                                            biome.getAttributes(),
                                            EnvironmentAttributeMap.CODEC,
                                            packetWrapper);
                                }
                            } else {
                                nBTCompound.set(
                                        "precipitation",
                                        biome.getPrecipitation(),
                                        Biome.Precipitation.CODEC,
                                        packetWrapper);
                                if (packetWrapper
                                        .getServerVersion()
                                        .isOlderThan(ServerVersion.V_1_19)) {
                                    if (biome.getCategory() != null) {
                                        nBTCompound.set(
                                                "category",
                                                biome.getCategory(),
                                                Biome.Category.CODEC,
                                                packetWrapper);
                                    }
                                    if (packetWrapper
                                            .getServerVersion()
                                            .isOlderThan(ServerVersion.V_1_18)) {
                                        if (biome.getDepth() != null) {
                                            nBTCompound.setTag(
                                                    "depth", new NBTFloat(biome.getDepth()));
                                        }
                                        if (biome.getScale() != null) {
                                            nBTCompound.setTag(
                                                    "scale", new NBTFloat(biome.getScale()));
                                        }
                                    }
                                }
                            }
                        }
                    })
                    .codec();

    boolean hasPrecipitation();

    @Obsolete(since = "1.19.3")
    Biome.Precipitation getPrecipitation();

    float getTemperature();

    Biome.TemperatureModifier getTemperatureModifier();

    float getDownfall();

    @Obsolete(since = "1.19")
    Biome.@Nullable Category getCategory();

    @Obsolete(since = "1.18")
    @Nullable Float getDepth();

    @Obsolete(since = "1.18")
    @Nullable Float getScale();

    BiomeEffects getEffects();

    EnvironmentAttributeMap getAttributes();

    @Deprecated
    static Biome decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(Biome biome, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), biome);
    }

    @Obsolete(since = "1.19")
    enum Category implements CodecNameable {
        NONE("none"),
        TAIGA("taiga"),
        EXTREME_HILLS("extreme_hills"),
        JUNGLE("jungle"),
        MESA("mesa"),
        PLAINS("plains"),
        SAVANNA("savanna"),
        ICY("icy"),
        THE_END("the_end"),
        BEACH("beach"),
        FOREST("forest"),
        OCEAN("ocean"),
        DESERT("desert"),
        RIVER("river"),
        SWAMP("swamp"),
        MUSHROOM("mushroom"),
        NETHER("nether"),
        UNDERGROUND("underground"),
        MOUNTAIN("mountain");

        public static final NbtCodec<Biome.Category> CODEC = NbtCodecs.forEnum(values());

        public static final Index<String, Biome.Category> ID_INDEX =
                Index.create(Biome.Category.class, Biome.Category::getId);

        private final String id;

        Category(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }

    @Obsolete(since = "1.19.3")
    enum Precipitation implements CodecNameable {
        NONE("none"),
        RAIN("rain"),
        SNOW("snow");

        public static final NbtCodec<Biome.Precipitation> CODEC = NbtCodecs.forEnum(values());

        public static final Index<String, Biome.Precipitation> ID_INDEX =
                Index.create(Biome.Precipitation.class, Biome.Precipitation::getId);

        private final String id;

        Precipitation(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }

    enum TemperatureModifier implements CodecNameable {
        NONE("none"),
        FROZEN("frozen");

        public static final NbtCodec<Biome.TemperatureModifier> CODEC = NbtCodecs.forEnum(values());

        public static final Index<String, Biome.TemperatureModifier> ID_INDEX =
                Index.create(Biome.TemperatureModifier.class, Biome.TemperatureModifier::getId);

        private final String id;

        TemperatureModifier(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        @Override
        public String getCodecName() {
            return this.id;
        }
    }
}
