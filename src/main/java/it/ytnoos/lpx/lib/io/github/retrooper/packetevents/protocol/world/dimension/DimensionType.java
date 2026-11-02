package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRef;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntityRefSet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntitySet;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.CodecNameable;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecException;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtMapCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributeMap;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.EnvironmentAttributes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.attributes.timelines.Timeline;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.clock.WorldClock;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.TagKey;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalLong;

@NullMarked
public interface DimensionType
        extends MappedEntity, CopyableEntity<DimensionType>, DeepComparableEntity {

    NbtCodec<DimensionType> CODEC =
            (new NbtMapCodec<DimensionType>() {

                        private final NbtCodec<MappedEntityRef<WorldClock>> clockCodec =
                                MappedEntityRef.codec(WorldClock.CODEC);

                        public DimensionType decode(
                                NBTCompound nBTCompound, PacketWrapper<?> packetWrapper)
                                throws NbtCodecException {
                            DimensionTypeBuilder dimensionTypeBuilder2 =
                                    DimensionTypeBuilder.dimensionTypeBuilder();
                            ServerVersion serverVersion;
                            if ((serverVersion = packetWrapper.getServerVersion())
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                                dimensionTypeBuilder2
                                        .setHasFixedTime(
                                                nBTCompound.getBooleanOr("has_fixed_time", false))
                                        .setSkybox(
                                                nBTCompound.getOr(
                                                        "skybox",
                                                        DimensionType.Skybox.CODEC,
                                                        DimensionType.Skybox.OVERWORLD,
                                                        packetWrapper))
                                        .setCardinalLight(
                                                nBTCompound.getOr(
                                                        "cardinal_light",
                                                        DimensionType.CardinalLight.CODEC,
                                                        DimensionType.CardinalLight.DEFAULT,
                                                        packetWrapper))
                                        .setAttributes(
                                                nBTCompound.getOr(
                                                        "attributes",
                                                        EnvironmentAttributeMap.CODEC,
                                                        EnvironmentAttributeMap.EMPTY,
                                                        packetWrapper))
                                        .setTimelines(
                                                nBTCompound.getOr(
                                                        "timelines",
                                                        MappedEntitySet::decodeRefSet,
                                                        MappedEntitySet.createEmpty(),
                                                        packetWrapper));
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
                                    dimensionTypeBuilder2.setDefaultClock(
                                            nBTCompound.getOrNull(
                                                    "default_clock",
                                                    this.clockCodec,
                                                    packetWrapper));
                                    dimensionTypeBuilder2.setHasEnderDragonFight(
                                            nBTCompound.getBooleanOrThrow(
                                                    "has_ender_dragon_fight"));
                                }
                            } else {
                                Number numberTagValueOrNull =
                                        nBTCompound.getNumberTagValueOrNull("fixed_time");
                                dimensionTypeBuilder2
                                        .setFixedTime(
                                                numberTagValueOrNull != null
                                                        ? numberTagValueOrNull.longValue()
                                                        : null)
                                        .setAttribute(
                                                EnvironmentAttributes.GAMEPLAY_WATER_EVAPORATES,
                                                nBTCompound.getBoolean("ultrawarm"))
                                        .setNatural(nBTCompound.getBoolean("natural"))
                                        .setBedWorks(nBTCompound.getBoolean("bed_works"))
                                        .setRespawnAnchorWorks(
                                                nBTCompound.getBoolean("respawn_anchor_works"))
                                        .setAttribute(
                                                EnvironmentAttributes.GAMEPLAY_PIGLINS_ZOMBIFY,
                                                !nBTCompound.getBoolean("piglin_safe"))
                                        .setAttribute(
                                                EnvironmentAttributes.GAMEPLAY_CAN_START_RAID,
                                                nBTCompound.getBoolean("has_raids"));
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                                    dimensionTypeBuilder2.setEffects(
                                            nBTCompound.getOrThrow(
                                                    "effects",
                                                    ResourceLocation.CODEC,
                                                    packetWrapper));
                                }
                                NBTNumber nBTNumber;
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)
                                        && (nBTNumber =
                                                        nBTCompound.getNumberTagOrNull(
                                                                "cloud_height"))
                                                != null) {
                                    dimensionTypeBuilder2.setAttribute(
                                            EnvironmentAttributes.VISUAL_CLOUD_HEIGHT,
                                            nBTNumber.getAsFloat());
                                }
                            }
                            if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                                dimensionTypeBuilder2.setCoordinateScale(
                                        nBTCompound
                                                .getNumberTagOrThrow("coordinate_scale")
                                                .getAsDouble());
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                                    dimensionTypeBuilder2.setMinY(
                                            nBTCompound.getNumberTagOrThrow("min_y").getAsInt());
                                    dimensionTypeBuilder2.setHeight(
                                            nBTCompound.getNumberTagOrThrow("height").getAsInt());
                                    if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                                        dimensionTypeBuilder2.setMonsterSpawnLightLevel(
                                                nBTCompound.getTagOrThrow(
                                                        "monster_spawn_light_level"));
                                        dimensionTypeBuilder2.setMonsterSpawnBlockLightLimit(
                                                nBTCompound
                                                        .getNumberTagOrThrow(
                                                                "monster_spawn_block_light_limit")
                                                        .getAsInt());
                                    }
                                }
                            } else {
                                dimensionTypeBuilder2.setCoordinateScale(
                                        nBTCompound.getBoolean("shrunk") ? 8.0 : 1.0);
                            }
                            dimensionTypeBuilder2.setInfiniburn(
                                    serverVersion.isNewerThanOrEquals(ServerVersion.V_1_18_2)
                                            ? nBTCompound.getOrThrow(
                                                    "infiniburn", TagKey.CODEC, packetWrapper)
                                            : new TagKey(
                                                    nBTCompound.getOrThrow(
                                                            "infiniburn",
                                                            ResourceLocation.CODEC,
                                                            packetWrapper)));
                            return dimensionTypeBuilder2
                                    .setHasSkylight(nBTCompound.getBooleanOrThrow("has_skylight"))
                                    .setHasCeiling(nBTCompound.getBooleanOrThrow("has_ceiling"))
                                    .setLogicalHeight(
                                            nBTCompound
                                                    .getNumberTagValueOrThrow("logical_height")
                                                    .intValue())
                                    .setAmbientLight(
                                            nBTCompound
                                                    .getNumberTagValueOrThrow("ambient_light")
                                                    .floatValue())
                                    .build();
                        }

                        public void encode(
                                NBTCompound nBTCompound,
                                PacketWrapper<?> packetWrapper,
                                DimensionType dimensionType)
                                throws NbtCodecException {
                            ServerVersion serverVersion;
                            if ((serverVersion = packetWrapper.getServerVersion())
                                    .isNewerThanOrEquals(ServerVersion.V_1_21_11)) {
                                if (dimensionType.hasFixedTime()) {
                                    nBTCompound.setTag("has_fixed_time", new NBTByte(true));
                                }
                                DimensionType.Skybox skybox;
                                if ((skybox = dimensionType.getSkybox())
                                        != DimensionType.Skybox.OVERWORLD) {
                                    nBTCompound.set(
                                            "skybox",
                                            skybox,
                                            DimensionType.Skybox.CODEC,
                                            packetWrapper);
                                }
                                DimensionType.CardinalLight cardinalLight;
                                if ((cardinalLight = dimensionType.getCardinalLight())
                                        != DimensionType.CardinalLight.DEFAULT) {
                                    nBTCompound.set(
                                            "cardinal_light",
                                            cardinalLight,
                                            DimensionType.CardinalLight.CODEC,
                                            packetWrapper);
                                }
                                EnvironmentAttributeMap environmentAttributeMap;
                                if (!(environmentAttributeMap = dimensionType.getAttributes())
                                        .isEmpty()) {
                                    nBTCompound.set(
                                            "attributes",
                                            environmentAttributeMap,
                                            EnvironmentAttributeMap.CODEC,
                                            packetWrapper);
                                }
                                MappedEntityRefSet mappedEntityRefSet;
                                if (!(mappedEntityRefSet = dimensionType.getTimelinesRef())
                                        .isEmpty()) {
                                    nBTCompound.set(
                                            "timelines",
                                            mappedEntityRefSet,
                                            MappedEntitySet::encodeRefSet,
                                            packetWrapper);
                                }
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_26_1)) {
                                    MappedEntityRef mappedEntityRef;
                                    if ((mappedEntityRef = dimensionType.getDefaultClockRef())
                                            != null) {
                                        nBTCompound.set(
                                                "default_clock",
                                                mappedEntityRef,
                                                this.clockCodec,
                                                packetWrapper);
                                    }
                                    nBTCompound.setTag(
                                            "has_ender_dragon_fight",
                                            new NBTByte(dimensionType.isHasEnderDragonFight()));
                                }
                            } else {
                                OptionalLong optionalLong;
                                if ((optionalLong = dimensionType.getFixedTime()).isPresent()) {
                                    nBTCompound.setTag(
                                            "fixed_time", new NBTLong(optionalLong.getAsLong()));
                                }
                                nBTCompound.setTag(
                                        "ultrawarm", new NBTByte(dimensionType.isUltraWarm()));
                                nBTCompound.setTag(
                                        "natural", new NBTByte(dimensionType.isNatural()));
                                nBTCompound.setTag(
                                        "bed_works", new NBTByte(dimensionType.isBedWorking()));
                                nBTCompound.setTag(
                                        "respawn_anchor_works",
                                        new NBTByte(dimensionType.isRespawnAnchorWorking()));
                                nBTCompound.setTag(
                                        "piglin_safe", new NBTByte(dimensionType.isPiglinSafe()));
                                nBTCompound.setTag(
                                        "has_raids", new NBTByte(dimensionType.hasRaids()));
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                                    nBTCompound.set(
                                            "effects",
                                            dimensionType.getEffectsLocation(),
                                            ResourceLocation.CODEC,
                                            packetWrapper);
                                }
                                Integer value;
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_21_6)
                                        && (value = dimensionType.getCloudHeight()) != null) {
                                    nBTCompound.setTag("cloud_height", new NBTInt(value));
                                }
                            }
                            if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                                nBTCompound.setTag(
                                        "coordinate_scale",
                                        new NBTDouble(dimensionType.getCoordinateScale()));
                                if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
                                    nBTCompound.setTag(
                                            "min_y", new NBTInt(dimensionType.getMinY()));
                                    nBTCompound.setTag(
                                            "height", new NBTInt(dimensionType.getHeight()));
                                    if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19)) {
                                        nBTCompound.setTag(
                                                "monster_spawn_light_level",
                                                dimensionType.getMonsterSpawnLightLevel());
                                        nBTCompound.setTag(
                                                "monster_spawn_block_light_limit",
                                                new NBTInt(
                                                        dimensionType
                                                                .getMonsterSpawnBlockLightLimit()));
                                    }
                                }
                            } else {
                                nBTCompound.setTag("shrunk", new NBTByte(dimensionType.isShrunk()));
                            }
                            nBTCompound.setTag(
                                    "has_skylight", new NBTByte(dimensionType.hasSkyLight()));
                            nBTCompound.setTag(
                                    "has_ceiling", new NBTByte(dimensionType.hasCeiling()));
                            nBTCompound.setTag(
                                    "logical_height", new NBTInt(dimensionType.getLogicalHeight()));
                            if (packetWrapper
                                    .getServerVersion()
                                    .isNewerThanOrEquals(ServerVersion.V_1_18_2)) {
                                nBTCompound.set(
                                        "infiniburn",
                                        dimensionType.getInfiniburn(),
                                        TagKey.CODEC,
                                        packetWrapper);
                            } else {
                                nBTCompound.set(
                                        "infiniburn",
                                        dimensionType.getInfiniburn().getId(),
                                        ResourceLocation.CODEC,
                                        packetWrapper);
                            }
                            nBTCompound.setTag(
                                    "ambient_light", new NBTFloat(dimensionType.getAmbientLight()));
                        }
                    })
                    .codec();

    boolean hasFixedTime();

    @Obsolete
    OptionalLong getFixedTime();

    boolean hasSkyLight();

    boolean hasCeiling();

    @Obsolete
    boolean isUltraWarm();

    @Obsolete
    boolean isNatural();

    double getCoordinateScale();

    default boolean isShrunk() {
        return this.getCoordinateScale() > 1.0;
    }

    @Obsolete
    boolean isBedWorking();

    @Obsolete
    boolean isRespawnAnchorWorking();

    int getMinY();

    @Deprecated
    default int getMinY(ClientVersion clientVersion) {
        return this.getMinY();
    }

    int getHeight();

    default int getHeight(ClientVersion clientVersion) {
        return this.getHeight();
    }

    int getLogicalHeight();

    @Deprecated
    default int getLogicalHeight(ClientVersion clientVersion) {
        return this.getLogicalHeight();
    }

    TagKey getInfiniburn();

    @Deprecated
    default String getInfiniburnTag() {
        return this.getInfiniburn().toString();
    }

    @Obsolete
    ResourceLocation getEffectsLocation();

    float getAmbientLight();

    @Obsolete
    @Nullable Integer getCloudHeight();

    @Nullable WorldClock getDefaultClock();

    @Nullable MappedEntityRef<WorldClock> getDefaultClockRef();

    boolean isHasEnderDragonFight();

    @Obsolete
    boolean isPiglinSafe();

    @Obsolete
    boolean hasRaids();

    @Experimental
    NBT getMonsterSpawnLightLevel();

    int getMonsterSpawnBlockLightLimit();

    DimensionType.Skybox getSkybox();

    DimensionType.CardinalLight getCardinalLight();

    EnvironmentAttributeMap getAttributes();

    MappedEntitySet<Timeline> getTimelines();

    MappedEntityRefSet<Timeline> getTimelinesRef();

    default DimensionTypeRef asRef(PacketWrapper<?> packetWrapper) {
        return new DimensionTypeRef.DirectRef(this, packetWrapper);
    }

    @Deprecated
    default DimensionTypeRef asRef(ClientVersion clientVersion) {
        return this.asRef(PacketWrapper.createDummyWrapper(clientVersion));
    }

    @Deprecated
    static DimensionType decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        return CODEC.decode(nBT, PacketWrapper.createDummyWrapper(clientVersion))
                .copy(typesBuilderData);
    }

    @Deprecated
    static NBT encode(DimensionType dimensionType, ClientVersion clientVersion) {
        return CODEC.encode(PacketWrapper.createDummyWrapper(clientVersion), dimensionType);
    }

    enum CardinalLight implements CodecNameable {
        DEFAULT("default"),
        NETHER("nether");

        public static final NbtCodec<DimensionType.CardinalLight> CODEC =
                NbtCodecs.forEnum(values());

        private final String name;

        CardinalLight(String name) {
            this.name = name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }

    enum Skybox implements CodecNameable {
        NONE("none"),
        OVERWORLD("overworld"),
        END("end");

        public static final NbtCodec<DimensionType.Skybox> CODEC = NbtCodecs.forEnum(values());

        private final String name;

        Skybox(String name) {
            this.name = name;
        }

        @Override
        public String getCodecName() {
            return this.name;
        }
    }
}
