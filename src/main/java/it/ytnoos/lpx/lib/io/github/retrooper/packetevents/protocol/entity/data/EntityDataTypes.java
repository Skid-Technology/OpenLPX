package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.color.DyeColor;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.armadillo.ArmadilloState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cat.CatVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken.ChickenSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.chicken.ChickenVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.cow.CowVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.struct.CopperGolemState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.struct.WeatheringCopperState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.frog.FrogVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.nautilus.ZombieNautilusVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pig.PigVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.sniffer.SnifferState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.villager.VillagerData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfSoundVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.wolfvariant.WolfVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.Particle;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.HumanoidArm;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.BlockFace;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.painting.PaintingVariant;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Quaternion4f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3f;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.Vector3i;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public final class EntityDataTypes {

    private static final VersionedRegistry<EntityDataType<?>> REGISTRY =
            new VersionedRegistry<>("entity_data_serializer");

    public static final EntityDataType<Byte> BYTE =
            define("byte", PacketWrapper::readByte, PacketWrapper::writeByte);

    public static final EntityDataType<Short> SHORT =
            define("short", PacketWrapper::readShort, PacketWrapper::writeShort);

    public static final EntityDataType<Integer> INT =
            define(
                    "int",
                    result ->
                            result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)
                                    ? result.readVarInt()
                                    : result.readInt(),
                    (result, result2) -> {
                        if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                            result.writeVarInt(result2);
                        } else {
                            result.writeInt(result2);
                        }
                    });

    public static final EntityDataType<Long> LONG =
            define("long", PacketWrapper::readVarLong, PacketWrapper::writeVarLong);

    public static final EntityDataType<Float> FLOAT =
            define("float", PacketWrapper::readFloat, PacketWrapper::writeFloat);

    public static final EntityDataType<String> STRING =
            define("string", PacketWrapper::readString, PacketWrapper::writeString);

    @Deprecated
    public static final EntityDataType<String> COMPONENT =
            define(
                    "component",
                    PacketWrapper::readComponentJSON,
                    PacketWrapper::writeComponentJSON);

    public static final EntityDataType<Component> ADV_COMPONENT =
            define("component", PacketWrapper::readComponent, PacketWrapper::writeComponent);

    @Deprecated
    public static final EntityDataType<Optional<String>> OPTIONAL_COMPONENT =
            define(
                    "optional_component",
                    readOptionalComponentJSONDeserializer(),
                    writeOptionalComponentJSONSerializer());

    public static final EntityDataType<Optional<Component>> OPTIONAL_ADV_COMPONENT =
            define(
                    "optional_component",
                    readOptionalComponentDeserializer(),
                    writeOptionalComponentSerializer());

    public static final EntityDataType<ItemStack> ITEMSTACK =
            define("itemstack", PacketWrapper::readItemStack, PacketWrapper::writeItemStack);

    public static final EntityDataType<Optional<ItemStack>> OPTIONAL_ITEMSTACK =
            define(
                    "optional_itemstack",
                    result -> Optional.of(result.readItemStack()),
                    (result, result2) -> result.writeItemStack((ItemStack) result2.orElse(null)));

    public static final EntityDataType<Boolean> BOOLEAN =
            define("boolean", PacketWrapper::readBoolean, PacketWrapper::writeBoolean);

    public static final EntityDataType<Vector3f> ROTATION =
            define(
                    "rotation",
                    result ->
                            new Vector3f(
                                    result.readFloat(), result.readFloat(), result.readFloat()),
                    (result, result2) -> {
                        result.writeFloat(result2.x);
                        result.writeFloat(result2.y);
                        result.writeFloat(result2.z);
                    });

    public static final EntityDataType<Vector3i> BLOCK_POSITION =
            define(
                    "block_position",
                    result -> {
                        if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                            return result.readBlockPosition();
                        }
                        int value = result.readInt();
                        int value2 = result.readInt();
                        int value3 = result.readInt();
                        return new Vector3i(value, value2, value3);
                    },
                    (result, result2) -> {
                        if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                            result.writeBlockPosition(result2);
                        } else {
                            result.writeInt(result2.getX());
                            result.writeInt(result2.getY());
                            result.writeInt(result2.getZ());
                        }
                    });

    public static final EntityDataType<Optional<Vector3i>> OPTIONAL_BLOCK_POSITION =
            define(
                    "optional_block_position",
                    readOptionalBlockPositionDeserializer(),
                    writeOptionalBlockPositionSerializer());

    public static final EntityDataType<BlockFace> BLOCK_FACE =
            define(
                    "block_face",
                    result -> BlockFace.getBlockFaceByValue(result.readVarInt()),
                    (result, result2) -> result.writeVarInt(result2.getFaceValue()));

    public static final EntityDataType<Optional<UUID>> OPTIONAL_UUID =
            define(
                    "optional_uuid",
                    result ->
                            Optional.ofNullable(
                                    (UUID) result.readOptional(PacketWrapper::readUUID)),
                    (result, result2) ->
                            result.writeOptional(
                                    (UUID) result2.orElse(null), PacketWrapper::writeUUID));

    public static final EntityDataType<Integer> BLOCK_STATE =
            define("block_state", readIntDeserializer(), writeIntSerializer());

    public static final EntityDataType<Integer> OPTIONAL_BLOCK_STATE =
            define("optional_block_state", readIntDeserializer(), writeIntSerializer());

    @Obsolete
    public static final EntityDataType<NBTCompound> NBT =
            define("nbt", PacketWrapper::readNBT, PacketWrapper::writeNBT);

    public static final EntityDataType<Particle<?>> PARTICLE =
            define("particle", Particle::read, Particle::write);

    public static final EntityDataType<VillagerData> VILLAGER_DATA =
            define(
                    "villager_data",
                    PacketWrapper::readVillagerData,
                    PacketWrapper::writeVillagerData);

    public static final EntityDataType<Optional<Integer>> OPTIONAL_INT =
            define(
                    "optional_int",
                    result -> {
                        int value;
                        return (value = result.readVarInt()) == 0
                                ? Optional.empty()
                                : Optional.of(value - 1);
                    },
                    (result, result2) -> result.writeVarInt(result2.orElse(-1) + 1));

    public static final EntityDataType<EntityPose> ENTITY_POSE =
            define(
                    "entity_pose",
                    result -> {
                        int value = result.readVarInt();
                        return EntityPose.getById(
                                result.getServerVersion().toClientVersion(), value);
                    },
                    (result, result2) ->
                            result.writeVarInt(
                                    result2.getId(result.getServerVersion().toClientVersion())));

    @Deprecated
    public static final EntityDataType<Integer> CAT_VARIANT =
            define("cat_variant_type", readIntDeserializer(), writeIntSerializer());

    public static final EntityDataType<CatVariant> TYPED_CAT_VARIANT =
            define("cat_variant_type", CatVariant::read, CatVariant::write);

    @Deprecated
    public static final EntityDataType<Integer> FROG_VARIANT =
            define("frog_variant_type", readIntDeserializer(), writeIntSerializer());

    public static final EntityDataType<FrogVariant> TYPED_FROG_VARIANT =
            define("frog_variant_type", FrogVariant::read, FrogVariant::write);

    public static final EntityDataType<Optional<WorldBlockPosition>> OPTIONAL_GLOBAL_POSITION =
            define(
                    "optional_global_position",
                    result ->
                            Optional.ofNullable(
                                    (WorldBlockPosition)
                                            result.readOptional(
                                                    result2 ->
                                                            new WorldBlockPosition(
                                                                    new ResourceLocation(
                                                                            result2.readString(
                                                                                    32767)),
                                                                    result2.readBlockPosition()))),
                    (result, result2) ->
                            result.writeOptional(
                                    (WorldBlockPosition) result2.orElse(null),
                                    (result3, result4) -> {
                                        result3.writeString(result4.getWorld().toString());
                                        result3.writeBlockPosition(result4.getBlockPosition());
                                    }));

    @Deprecated
    public static final EntityDataType<Integer> PAINTING_VARIANT_TYPE =
            define("painting_variant_type", readIntDeserializer(), writeIntSerializer());

    public static final EntityDataType<PaintingVariant> PAINTING_VARIANT =
            define("painting_variant_type", PaintingVariant::read, PaintingVariant::write);

    public static final EntityDataType<SnifferState> SNIFFER_STATE =
            define(
                    "sniffer_state",
                    result -> {
                        int value = result.readVarInt();
                        return SnifferState.values()[value];
                    },
                    (result, result2) -> result.writeVarInt(result2.ordinal()));

    public static final EntityDataType<Vector3f> VECTOR3F =
            define(
                    "vector3f",
                    result ->
                            new Vector3f(
                                    result.readFloat(), result.readFloat(), result.readFloat()),
                    (result, result2) -> {
                        result.writeFloat(result2.x);
                        result.writeFloat(result2.y);
                        result.writeFloat(result2.z);
                    });

    public static final EntityDataType<Quaternion4f> QUATERNION =
            define(
                    "quaternion",
                    result ->
                            new Quaternion4f(
                                    result.readFloat(),
                                    result.readFloat(),
                                    result.readFloat(),
                                    result.readFloat()),
                    (result, result2) -> {
                        result.writeFloat(result2.getX());
                        result.writeFloat(result2.getY());
                        result.writeFloat(result2.getZ());
                        result.writeFloat(result2.getW());
                    });

    public static final EntityDataType<ArmadilloState> ARMADILLO_STATE =
            define(
                    "armadillo_state",
                    result -> ArmadilloState.values()[result.readVarInt()],
                    (result, result2) -> result.writeVarInt(result2.ordinal()));

    public static final EntityDataType<List<Particle<?>>> PARTICLES =
            define(
                    "particles",
                    result -> result.readList(Particle::read),
                    (result, result2) -> result.writeList(result2, Particle::write));

    @Deprecated
    public static final EntityDataType<Integer> WOLF_VARIANT =
            define("wolf_variant_type", readIntDeserializer(), writeIntSerializer());

    public static final EntityDataType<WolfVariant> TYPED_WOLF_VARIANT =
            define("wolf_variant_type", WolfVariant::read, WolfVariant::write);

    public static final EntityDataType<CowVariant> COW_VARIANT =
            define("cow_variant_type", CowVariant::read, CowVariant::write);

    public static final EntityDataType<WolfSoundVariant> WOLF_SOUND_VARIANT =
            define("wolf_sound_variant_type", WolfSoundVariant::read, WolfSoundVariant::write);

    public static final EntityDataType<PigVariant> PIG_VARIANT =
            define("pig_variant_type", PigVariant::read, PigVariant::write);

    public static final EntityDataType<ChickenVariant> CHICKEN_VARIANT =
            define("chicken_variant_type", ChickenVariant::read, ChickenVariant::write);

    public static final EntityDataType<CopperGolemState> COPPER_GOLEM_STATE =
            define("copper_golem_state", CopperGolemState::read, CopperGolemState::write);

    public static final EntityDataType<WeatheringCopperState> WEATHERING_COPPER_STATE =
            define(
                    "weathering_copper_state",
                    WeatheringCopperState::read,
                    WeatheringCopperState::write);

    public static final EntityDataType<ItemProfile> RESOLVABLE_PROFILE =
            define("resolvable_profile", ItemProfile::read, ItemProfile::write);

    public static final EntityDataType<ZombieNautilusVariant> ZOMBIE_NAUTILUS_VARIANT =
            define(
                    "zombie_nautilus_variant",
                    ZombieNautilusVariant::read,
                    ZombieNautilusVariant::write);

    public static final EntityDataType<HumanoidArm> HUMANOID_ARM =
            define("humanoid_arm", HumanoidArm::read, HumanoidArm::write);

    public static final EntityDataType<CatSoundVariant> CAT_SOUND_VARIANT =
            define("cat_sound_variant_type", CatSoundVariant::read, CatSoundVariant::write);

    public static final EntityDataType<ChickenSoundVariant> CHICKEN_SOUND_VARIANT =
            define(
                    "chicken_sound_variant_type",
                    ChickenSoundVariant::read,
                    ChickenSoundVariant::write);

    public static final EntityDataType<CowSoundVariant> COW_SOUND_VARIANT =
            define("cow_sound_variant_type", CowSoundVariant::read, CowSoundVariant::write);

    public static final EntityDataType<PigSoundVariant> PIG_SOUND_VARIANT =
            define("pig_sound_variant_type", PigSoundVariant::read, PigSoundVariant::write);

    public static final EntityDataType<DyeColor> DYE_COLOR =
            define("dye_color", DyeColor::read, DyeColor::write);

    private EntityDataTypes() {}

    public static VersionedRegistry<EntityDataType<?>> getRegistry() {
        return REGISTRY;
    }

    public static Collection<EntityDataType<?>> values() {
        return REGISTRY.getEntries();
    }

    @Nullable
    public static EntityDataType<?> getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    @Nullable
    public static EntityDataType<?> getByName(String text) {
        return REGISTRY.getByName(text);
    }

    @Internal
    public static <T, Z extends T> EntityDataType<Z> define(
            String text, PacketWrapper.Reader<Z> reader, PacketWrapper.Writer<T> writer) {
        return REGISTRY.define(
                text,
                result -> {
                    return new EntityDataType<>(
                            result, ((PacketWrapper.Reader) reader), writer::accept);
                });
    }

    private static PacketWrapper.Reader<Integer> readIntDeserializer() {
        return result ->
                result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)
                        ? result.readVarInt()
                        : result.readInt();
    }

    private static PacketWrapper.Writer<Number> writeIntSerializer() {
        return (result, result2) -> {
            if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                result.writeVarInt(result2.intValue());
            } else {
                result.writeInt(result2.intValue());
            }
        };
    }

    @Deprecated
    private static PacketWrapper.Reader<Optional<String>> readOptionalComponentJSONDeserializer() {
        return result -> result.readJavaOptional(PacketWrapper::readComponentJSON);
    }

    @Deprecated
    private static PacketWrapper.Writer<Optional<String>> writeOptionalComponentJSONSerializer() {
        return (result, result2) ->
                result.writeJavaOptional(result2, PacketWrapper::writeComponentJSON);
    }

    private static PacketWrapper.Reader<Optional<Component>> readOptionalComponentDeserializer() {
        return result -> result.readJavaOptional(PacketWrapper::readComponent);
    }

    private static PacketWrapper.Writer<Optional<Component>> writeOptionalComponentSerializer() {
        return (result, result2) ->
                result.writeJavaOptional(result2, PacketWrapper::writeComponent);
    }

    private static PacketWrapper.Reader<Optional<Vector3i>>
            readOptionalBlockPositionDeserializer() {
        return result ->
                result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)
                        ? result.readJavaOptional(PacketWrapper::readBlockPosition)
                        : result.readJavaOptional(
                                result2 ->
                                        new Vector3i(
                                                result2.readInt(),
                                                result2.readInt(),
                                                result2.readInt()));
    }

    private static PacketWrapper.Writer<Optional<Vector3i>> writeOptionalBlockPositionSerializer() {
        return (result, result2) -> {
            if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_9)) {
                result.writeJavaOptional(result2, PacketWrapper::writeBlockPosition);
            } else {
                result.writeJavaOptional(
                        result2,
                        (result3, result4) -> {
                            ((PacketWrapper) result).writeInt(result4.getX());
                            ((PacketWrapper) result).writeInt(result4.getY());
                            ((PacketWrapper) result).writeInt(result4.getZ());
                        });
            }
        };
    }

    static {
        REGISTRY.unloadMappings();
    }
}
