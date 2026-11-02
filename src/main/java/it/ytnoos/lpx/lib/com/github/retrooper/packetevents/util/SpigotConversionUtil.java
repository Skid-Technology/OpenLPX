package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.type.ItemType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.GameMode;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.HumanoidArm;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.potion.PotionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Dimension;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.Location;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.SimpleTypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SpigotConversionUtil {

    private SpigotConversionUtil() {}

    public static Location fromBukkitLocation(org.bukkit.Location location) {
        return new Location(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public static org.bukkit.Location toBukkitLocation(World world, Location location) {
        return new org.bukkit.Location(
                world,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public static PotionType fromBukkitPotionEffectType(PotionEffectType potionEffectType) {
        ServerVersion version = PacketEvents.getAPI().getServerManager().getVersion();
        int id = potionEffectType.getId();
        if (version.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            id--;
        }
        return PotionTypes.getById(id, version);
    }

    public static PotionEffectType toBukkitPotionEffectType(PotionType potionType) {
        ClientVersion clientVersion =
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion();
        int id = potionType.getId(clientVersion);
        if (clientVersion.isNewerThanOrEquals(ClientVersion.V_1_20_2)) {
            id++;
        }
        return PotionEffectType.getById(id);
    }

    public static GameMode fromBukkitGameMode(org.bukkit.GameMode gameMode) {
        return GameMode.getById(gameMode.getValue());
    }

    public static org.bukkit.GameMode toBukkitGameMode(GameMode gameMode) {
        return org.bukkit.GameMode.getByValue(gameMode.getId());
    }

    public static WrappedBlockState fromBukkitBlockData(BlockData blockData) {
        String asString = blockData.getAsString(false);
        return WrappedBlockState.getByString(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), asString);
    }

    public static BlockData toBukkitBlockData(WrappedBlockState wrappedBlockState) {
        return Bukkit.createBlockData(wrappedBlockState.toString());
    }

    public static EntityType fromBukkitEntityType(org.bukkit.entity.EntityType entityType) {
        ServerVersion serverVersion;
        if ((serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                .isNewerThanOrEquals(ServerVersion.V_1_14)) {
            return EntityTypes.getByName(entityType.getKey().toString());
        } else if (serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13)) {
            return EntityTypes.getByName("minecraft:" + entityType.getName());
        } else {
            return entityType.getTypeId() == -1
                    ? null
                    : EntityTypes.getById(serverVersion.toClientVersion(), entityType.getTypeId());
        }
    }

    public static org.bukkit.entity.EntityType toBukkitEntityType(EntityType entityType) {
        ServerVersion serverVersion;
        return (serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                        .isNewerThanOrEquals(ServerVersion.V_1_13)
                ? org.bukkit.entity.EntityType.fromName(entityType.getName().getKey())
                : org.bukkit.entity.EntityType.fromId(
                        entityType.getId(serverVersion.toClientVersion()));
    }

    public static ItemType fromBukkitItemMaterial(Material material) {
        return fromBukkitItemStack(new ItemStack(material)).getType();
    }

    public static Material toBukkitItemMaterial(ItemType itemType) {
        return toBukkitItemStack(
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack
                                .builder()
                                .type(itemType)
                                .build())
                .getType();
    }

    public static WrappedBlockState fromBukkitMaterialData(MaterialData materialData) {
        int blockDataCombinedId = SpigotReflectionUtil.getBlockDataCombinedId(materialData);
        return WrappedBlockState.getByGlobalId(
                PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(),
                blockDataCombinedId);
    }

    public static MaterialData toBukkitMaterialData(WrappedBlockState wrappedBlockState) {
        return SpigotReflectionUtil.getBlockDataByCombinedId(wrappedBlockState.getGlobalId());
    }

    public static it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack
            fromBukkitItemStack(ItemStack itemStack) {
        return SpigotReflectionUtil.decodeBukkitItemStack(itemStack);
    }

    public static ItemStack toBukkitItemStack(
            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack itemStack) {
        return SpigotReflectionUtil.encodeBukkitItemStack(itemStack);
    }

    public static DimensionType typeFromBukkitWorld(World world) {
        ServerVersion serverVersion;
        if ((serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                .isOlderThan(ServerVersion.V_1_14)) {
            int id = world.getEnvironment().getId();
            return DimensionTypes.getRegistry().getById(serverVersion.toClientVersion(), id);
        } else if (serverVersion.isOlderThan(ServerVersion.V_1_16)) {
            int dimensionId =
                    SpigotReflectionUtil.getDimensionId(
                            SpigotReflectionUtil.convertBukkitWorldToWorldServer(world));
            return DimensionTypes.getRegistry()
                    .getById(serverVersion.toClientVersion(), dimensionId);
        } else {
            Object result;
            NBTCompound nBTCompound =
                    SpigotReflectionUtil.fromMinecraftNBT(
                            SpigotReflectionUtil.convertWorldServerDimensionToNMSNbt(
                                    result =
                                            SpigotReflectionUtil.convertBukkitWorldToWorldServer(
                                                    world)));
            ResourceLocation resourceLocation =
                    new ResourceLocation(SpigotReflectionUtil.getDimensionKey(result));
            int dimensionId2 = SpigotReflectionUtil.getDimensionId(result);
            return DimensionType.CODEC
                    .decode(
                            nBTCompound,
                            PacketWrapper.createDummyWrapper(serverVersion.toClientVersion()))
                    .copy(new SimpleTypesBuilderData(resourceLocation, dimensionId2));
        }
    }

    @Deprecated
    public static Dimension fromBukkitWorld(World world) {
        ServerVersion serverVersion;
        if ((serverVersion = PacketEvents.getAPI().getServerManager().getVersion())
                .isOlderThan(ServerVersion.V_1_14)) {
            return new Dimension(world.getEnvironment().getId());
        }
        if (serverVersion.isOlderThan(ServerVersion.V_1_16)) {
            Object result = SpigotReflectionUtil.convertBukkitWorldToWorldServer(world);
            return new Dimension(SpigotReflectionUtil.getDimensionId(result));
        }
        Object result2;
        Object result3 =
                SpigotReflectionUtil.convertWorldServerDimensionToNMSNbt(
                        result2 = SpigotReflectionUtil.convertBukkitWorldToWorldServer(world));
        Dimension dimension = new Dimension(SpigotReflectionUtil.fromMinecraftNBT(result3));
        if (serverVersion.isOlderThan(ServerVersion.V_1_16_2)) {
            dimension.setDimensionName(SpigotReflectionUtil.getDimensionKey(result2));
        }
        dimension.setId(SpigotReflectionUtil.getDimensionId(result2));
        return dimension;
    }

    public static ParticleType<?> fromBukkitParticle(Enum<?> enumValue) {
        return SpigotReflectionUtil.toPacketEventsParticle(enumValue);
    }

    public static Enum<?> toBukkitParticle(ParticleType<?> particleType) {
        return SpigotReflectionUtil.fromPacketEventsParticle(particleType);
    }

    @Nullable
    public static Entity getEntityById(@Nullable World world, int value) {
        return SpigotReflectionUtil.getEntityById(world, value);
    }

    public static Pose toBukkitPose(EntityPose entityPose) {
        return Pose.values()[entityPose.ordinal()];
    }

    public static EntityPose fromBukkitPose(Pose pose) {
        return EntityPose.values()[pose.ordinal()];
    }

    public static MainHand toBukkitHand(HumanoidArm humanoidArm) {
        return MainHand.values()[humanoidArm.ordinal()];
    }

    public static List<EntityData<?>> getEntityMetadata(Entity entity) {
        return SpigotReflectionUtil.getEntityMetadata(entity);
    }
}
