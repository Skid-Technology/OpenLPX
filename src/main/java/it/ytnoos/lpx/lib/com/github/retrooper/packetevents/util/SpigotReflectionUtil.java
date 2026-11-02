package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.MapMaker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import it.ytnoos.lpx.compat.ReflectionNames;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.ByteBufHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.netty.buffer.UnpooledByteBufAllocationHelper;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.entity.data.EntityData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.TextureProperty;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.NestedClassUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.Reflection;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.reflection.ReflectionObject;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Internal
public final class SpigotReflectionUtil {

    private static final String MODIFIED_PACKAGE_NAME;

    public static final String LEGACY_NMS_PACKAGE;

    public static final String OBC_PACKAGE;

    public static ServerVersion VERSION;

    public static boolean V_1_19_OR_HIGHER;

    public static boolean V_1_17_OR_HIGHER;

    public static boolean V_1_12_OR_HIGHER;

    public static Class<?> MINECRAFT_SERVER_CLASS;

    public static Class<?> NMS_PACKET_DATA_SERIALIZER_CLASS;

    public static Class<?> NMS_ITEM_STACK_CLASS;

    public static Class<?> NMS_IMATERIAL_CLASS;

    public static Class<?> NMS_ENTITY_CLASS;

    public static Class<?> ENTITY_PLAYER_CLASS;

    public static Class<?> BOUNDING_BOX_CLASS;

    public static Class<?> NMS_MINECRAFT_KEY_CLASS;

    public static Class<?> ENTITY_HUMAN_CLASS;

    public static Class<?> PLAYER_CONNECTION_CLASS;

    public static Class<?> TRANSFER_COOKIE_CONNECTION_CLASS;

    public static Class<?> SERVER_LOGIN_PACKET_LISTENER_IMPL_CLASS;

    public static Class<?> SERVER_COMMON_PACKETLISTENER_IMPL_CLASS;

    public static Class<?> SERVER_CONNECTION_CLASS;

    public static Class<?> NETWORK_MANAGER_CLASS;

    public static Class<?> NMS_ENUM_PARTICLE_CLASS;

    public static Class<?> MOB_EFFECT_LIST_CLASS;

    public static Class<?> NMS_ITEM_CLASS;

    public static Class<?> DEDICATED_SERVER_CLASS;

    public static Class<?> LEVEL_CLASS;

    public static Class<?> SERVER_LEVEL_CLASS;

    public static Class<?> ENUM_PROTOCOL_DIRECTION_CLASS;

    public static Class<?> GAME_PROFILE_CLASS;

    public static Class<?> CRAFT_WORLD_CLASS;

    public static Class<?> CRAFT_SERVER_CLASS;

    public static Class<?> CRAFT_PLAYER_CLASS;

    public static Class<?> CRAFT_ENTITY_CLASS;

    public static Class<?> CRAFT_ITEM_STACK_CLASS;

    public static Class<?> CRAFT_PARTICLE_CLASS;

    public static Class<?> LEVEL_ENTITY_GETTER_CLASS;

    public static Class<?> ENTITY_ACCESS_CLASS;

    public static Class<?> PERSISTENT_ENTITY_SECTION_MANAGER_CLASS;

    public static Class<?> PAPER_ENTITY_LOOKUP_CLASS;

    public static Class<?> CRAFT_MAGIC_NUMBERS_CLASS;

    public static Class<?> IBLOCK_DATA_CLASS;

    public static Class<?> BLOCK_CLASS;

    public static Class<?> CRAFT_BLOCK_DATA_CLASS;

    public static Class<?> PROPERTY_MAP_CLASS;

    public static Class<?> DIMENSION_MANAGER_CLASS;

    public static Class<?> MOJANG_CODEC_CLASS;

    public static Class<?> MOJANG_ENCODER_CLASS;

    public static Class<?> DATA_RESULT_CLASS;

    public static Class<?> DYNAMIC_OPS_NBT_CLASS;

    public static Class<?> NMS_NBT_COMPOUND_CLASS;

    public static Class<?> NMS_NBT_BASE_CLASS;

    public static Class<?> NBT_COMPRESSION_STREAM_TOOLS_CLASS;

    public static Class<?> STREAM_CODEC;

    public static Class<?> STREAM_DECODER;

    public static Class<?> STREAM_ENCODER;

    public static Class<?> REGISTRY_FRIENDLY_BYTE_BUF;

    public static Class<?> REGISTRY_ACCESS;

    public static Class<?> REGISTRY_ACCESS_FROZEN;

    public static Class<?> RESOURCE_KEY;

    public static Class<?> REGISTRY;

    public static Class<?> WRITABLE_REGISTRY;

    public static Class<?> NBT_ACCOUNTER;

    public static Class<?> CHUNK_PROVIDER_SERVER_CLASS;

    public static Class<?> ICHUNKPROVIDER_CLASS;

    public static Class<?> CHUNK_STATUS_CLASS;

    public static Class<?> BLOCK_POSITION_CLASS;

    public static Class<?> PLAYER_CHUNK_MAP_CLASS;

    public static Class<?> PLAYER_CHUNK_CLASS;

    public static Class<?> CHUNK_CLASS;

    public static Class<?> IBLOCKACCESS_CLASS;

    public static Class<?> ICHUNKACCESS_CLASS;

    public static Class<?> REMOTE_CHAT_SESSION_CLASS;

    public static Class<?> DATA_WATCHER_CLASS;

    public static Class<?> CLIENTBOUND_SET_ENTITY_DATA_PACKET_CLASS;

    public static Class<?> DATA_WATCHER_ITEM_CLASS;

    public static Class<?> DATA_WATCHER_VALUE_CLASS;

    public static Class<?> PAPER_COMMON_CONNECTION_CLASS;

    public static Class<?> REGISTRY_OPS;

    public static Class<?> CHANNEL_CLASS;

    public static Class<?> BYTE_BUF_CLASS;

    public static Class<?> BYTE_TO_MESSAGE_DECODER;

    public static Class<?> MESSAGE_TO_BYTE_ENCODER;

    public static Field ENTITY_PLAYER_PING_FIELD;

    public static Field ENTITY_BOUNDING_BOX_FIELD;

    public static Field BYTE_BUF_IN_PACKET_DATA_SERIALIZER;

    public static Field DIMENSION_CODEC_FIELD;

    public static Field DYNAMIC_OPS_NBT_INSTANCE_FIELD;

    public static Field CHUNK_PROVIDER_SERVER_FIELD;

    public static Field CRAFT_PARTICLE_PARTICLES_FIELD;

    public static Field NMS_MK_KEY_FIELD;

    public static Field LEGACY_NMS_PARTICLE_KEY_FIELD;

    public static Field LEGACY_NMS_KEY_TO_NMS_PARTICLE;

    public static Field REMOTE_CHAT_SESSION_FIELD;

    public static Field REGISTRY_KEY_LOCATION_FIELD;

    public static Field DATA_WATCHER_FIELD;

    public static Field PAPER_CONNECTION_HANDLE_FIELD;

    public static Field PACKETLISTENER_CONNECTION_FIELD;

    public static Field CONNECTION_CHANNEL_FIELD;

    public static Field ENTITY_ID_COUNTER;

    public static Method IS_DEBUGGING;

    public static Method GET_CRAFT_PLAYER_HANDLE_METHOD;

    public static Method GET_CRAFT_ENTITY_HANDLE_METHOD;

    public static Method GET_CRAFT_WORLD_HANDLE_METHOD;

    public static Method GET_MOB_EFFECT_LIST_ID_METHOD;

    public static Method GET_MOB_EFFECT_LIST_BY_ID_METHOD;

    public static Method GET_ITEM_ID_METHOD;

    public static Method GET_ITEM_BY_ID_METHOD;

    public static Method GET_BUKKIT_ENTITY_METHOD;

    public static Method GET_LEVEL_ENTITY_GETTER_ITERABLE_METHOD;

    public static Method GET_ENTITY_BY_ID_LEVEL_ENTITY_GETTER_METHOD;

    public static Method GET_ENTITY_BY_ID_METHOD;

    public static Method CRAFT_ITEM_STACK_AS_BUKKIT_COPY;

    public static Method CRAFT_ITEM_STACK_AS_NMS_COPY;

    public static Method BUKKIT_PARTICLE_TO_NMS_ENUM_PARTICLE;

    public static Method NMS_ENUM_PARTICLE_TO_BUKKIT_PARTICLE;

    public static Method READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD;

    public static Method WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD;

    public static Method GET_COMBINED_ID;

    public static Method GET_BY_COMBINED_ID;

    public static Method GET_CRAFT_BLOCK_DATA_FROM_IBLOCKDATA;

    public static Method PROPERTY_MAP_GET_METHOD;

    public static Method GET_DIMENSION_MANAGER;

    public static Method GET_DIMENSION_ID;

    public static Method GET_DIMENSION_KEY;

    public static Method CODEC_ENCODE_METHOD;

    public static Method DATA_RESULT_GET_METHOD;

    public static Method READ_NBT_FROM_STREAM_METHOD;

    public static Method WRITE_NBT_TO_STREAM_METHOD;

    public static Method STREAM_DECODER_DECODE;

    public static Method STREAM_ENCODER_ENCODE;

    public static Method CREATE_REGISTRY_RESOURCE_KEY;

    public static Method GET_REGISTRY_OR_THROW;

    public static Method GET_DIMENSION_TYPES;

    public static Method GET_REGISTRY_ID;

    public static Method NBT_ACCOUNTER_UNLIMITED_HEAP;

    public static Method CHUNK_CACHE_GET_IBLOCKACCESS;

    public static Method CHUNK_CACHE_GET_ICHUNKACCESS;

    public static Method IBLOCKACCESS_GET_BLOCK_DATA;

    public static Method CHUNK_GET_BLOCK_DATA;

    public static Method PLAYER_CHUNK_MAP_GET_PLAYER_CHUNK;

    public static Method PLAYER_CHUNK_GET_CHUNK;

    public static Method LEGACY_DATA_WATCHER_WRITE_METHOD;

    public static Method CLIENTBOUND_SET_ENTITY_DATA_PACKET_WRITE_DATA_WATCHER_METHOD;

    public static Method GET_DATA_VALUE_FROM_DATA_ITEM_METHOD;

    public static Method GET_TPS;

    public static Method REGISTRY_OPS_CREATE;

    public static Method GET_NEXT_ENTITY_ID;

    private static Constructor<?> NMS_ITEM_STACK_CONSTRUCTOR;

    private static Constructor<?> NMS_PACKET_DATA_SERIALIZER_CONSTRUCTOR;

    private static Constructor<?> NMS_MINECRAFT_KEY_CONSTRUCTOR;

    private static Constructor<?> REGISTRY_FRIENDLY_BYTE_BUF_CONSTRUCTOR;

    private static Constructor<?> BLOCK_POSITION_CONSTRUCTOR;

    private static Object MINECRAFT_SERVER_INSTANCE;

    private static Object MINECRAFT_SERVER_CONNECTION_INSTANCE;

    private static Object MINECRAFT_SERVER_REGISTRY_ACCESS;

    private static Object ITEM_STACK_OPTIONAL_STREAM_CODEC;

    private static Object DIMENSION_TYPE_REGISTRY_KEY;

    private static boolean PAPER_ENTITY_LOOKUP_EXISTS;

    private static boolean PAPER_ENTITY_LOOKUP_LEGACY;

    private static boolean IS_OBFUSCATED;

    public static Map<Integer, Entity> ENTITY_ID_CACHE;

    private static void initConstructors() {
        Class<?> classValue = NMS_IMATERIAL_CLASS != null ? NMS_IMATERIAL_CLASS : NMS_ITEM_CLASS;
        try {
            NMS_ITEM_STACK_CONSTRUCTOR = NMS_ITEM_STACK_CLASS.getConstructor(classValue, int.class);
            NMS_PACKET_DATA_SERIALIZER_CONSTRUCTOR =
                    NMS_PACKET_DATA_SERIALIZER_CLASS.getConstructor(BYTE_BUF_CLASS);
            if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_9)) {
                NMS_MINECRAFT_KEY_CONSTRUCTOR =
                        NMS_MINECRAFT_KEY_CLASS.getDeclaredConstructor(String.class, String.class);
                NMS_MINECRAFT_KEY_CONSTRUCTOR.setAccessible(true);
            }
            if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                REGISTRY_FRIENDLY_BYTE_BUF_CONSTRUCTOR =
                        REGISTRY_FRIENDLY_BYTE_BUF.getConstructor(BYTE_BUF_CLASS, REGISTRY_ACCESS);
            }
            if (BLOCK_POSITION_CLASS != null) {
                BLOCK_POSITION_CONSTRUCTOR =
                        BLOCK_POSITION_CLASS.getConstructor(int.class, int.class, int.class);
            }
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        }
    }

    private static void initMethods() {
        IS_DEBUGGING = Reflection.getMethod(MINECRAFT_SERVER_CLASS, "isDebugging", 0);
        GET_BUKKIT_ENTITY_METHOD = Reflection.getMethod(NMS_ENTITY_CLASS, CRAFT_ENTITY_CLASS, 0);
        GET_CRAFT_PLAYER_HANDLE_METHOD = Reflection.getMethod(CRAFT_PLAYER_CLASS, "getHandle", 0);
        GET_CRAFT_ENTITY_HANDLE_METHOD = Reflection.getMethod(CRAFT_ENTITY_CLASS, "getHandle", 0);
        GET_CRAFT_WORLD_HANDLE_METHOD = Reflection.getMethod(CRAFT_WORLD_CLASS, "getHandle", 0);
        GET_MOB_EFFECT_LIST_ID_METHOD =
                Reflection.getMethod(MOB_EFFECT_LIST_CLASS, V_1_19_OR_HIGHER ? "g" : "getId", 0);
        GET_MOB_EFFECT_LIST_BY_ID_METHOD =
                Reflection.getMethod(MOB_EFFECT_LIST_CLASS, V_1_19_OR_HIGHER ? "a" : "fromId", 0);
        GET_ITEM_ID_METHOD =
                Reflection.getMethod(NMS_ITEM_CLASS, V_1_19_OR_HIGHER ? "g" : "getId", 0);
        GET_ITEM_BY_ID_METHOD = Reflection.getMethod(NMS_ITEM_CLASS, NMS_ITEM_CLASS, 0);
        if (V_1_17_OR_HIGHER) {
            GET_LEVEL_ENTITY_GETTER_ITERABLE_METHOD =
                    Reflection.getMethod(LEVEL_ENTITY_GETTER_CLASS, Iterable.class, 0);
            GET_ENTITY_BY_ID_LEVEL_ENTITY_GETTER_METHOD =
                    Reflection.getMethod(
                            LEVEL_ENTITY_GETTER_CLASS, ENTITY_ACCESS_CLASS, 0, int.class);
        }
        if (DIMENSION_MANAGER_CLASS != null) {
            GET_DIMENSION_KEY = Reflection.getMethod(LEVEL_CLASS, "getTypeKey", 0);
            GET_DIMENSION_MANAGER = Reflection.getMethod(LEVEL_CLASS, DIMENSION_MANAGER_CLASS, 0);
            GET_DIMENSION_ID = Reflection.getMethod(DIMENSION_MANAGER_CLASS, int.class, 0);
        }
        CODEC_ENCODE_METHOD = Reflection.getMethod(MOJANG_ENCODER_CLASS, "encodeStart", 0);
        DATA_RESULT_GET_METHOD = Reflection.getMethod(DATA_RESULT_CLASS, "result", 0);
        String text =
                VERSION.isOlderThan(ServerVersion.V_1_9)
                        ? "a"
                        : (VERSION.isOlderThan(ServerVersion.V_1_17) ? "getEntity" : "b");
        GET_ENTITY_BY_ID_METHOD =
                Reflection.getMethodExact(SERVER_LEVEL_CLASS, text, NMS_ENTITY_CLASS, int.class);
        if (GET_ENTITY_BY_ID_METHOD == null) {
            GET_ENTITY_BY_ID_METHOD =
                    Reflection.getMethodExact(
                            SERVER_LEVEL_CLASS, "getEntity", NMS_ENTITY_CLASS, int.class);
        }
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isOlderThanOrEquals(ServerVersion.V_1_12_2)) {
            BUKKIT_PARTICLE_TO_NMS_ENUM_PARTICLE =
                    Reflection.getMethod(CRAFT_PARTICLE_CLASS, "toNMS", NMS_ENUM_PARTICLE_CLASS);
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_9)) {
                Class<?> classByNameWithoutException =
                        Reflection.getClassByNameWithoutException("org.bukkit.Particle");
                NMS_ENUM_PARTICLE_TO_BUKKIT_PARTICLE =
                        Reflection.getMethod(
                                CRAFT_PARTICLE_CLASS, "toBukkit", classByNameWithoutException);
            }
        }
        CRAFT_ITEM_STACK_AS_BUKKIT_COPY =
                Reflection.getMethodExact(
                        CRAFT_ITEM_STACK_CLASS, "asBukkitCopy", null, NMS_ITEM_STACK_CLASS);
        if (CRAFT_ITEM_STACK_AS_BUKKIT_COPY == null) {
            CRAFT_ITEM_STACK_AS_BUKKIT_COPY =
                    Reflection.getMethod(CRAFT_ITEM_STACK_CLASS, "asBukkitCopy", 0);
        }
        CRAFT_ITEM_STACK_AS_NMS_COPY =
                Reflection.getMethod(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", ItemStack.class);
        READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD =
                Reflection.getMethodExact(
                        NMS_PACKET_DATA_SERIALIZER_CLASS, "k", NMS_ITEM_STACK_CLASS);
        if (READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD == null) {
            READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD =
                    Reflection.getMethod(NMS_PACKET_DATA_SERIALIZER_CLASS, NMS_ITEM_STACK_CLASS, 0);
        }
        WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD =
                Reflection.getMethodExact(
                        NMS_PACKET_DATA_SERIALIZER_CLASS,
                        "a",
                        NMS_PACKET_DATA_SERIALIZER_CLASS,
                        NMS_ITEM_STACK_CLASS);
        if (WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD == null) {
            WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD =
                    Reflection.getMethod(NMS_PACKET_DATA_SERIALIZER_CLASS, 0, NMS_ITEM_STACK_CLASS);
        }
        GET_COMBINED_ID = Reflection.getMethod(BLOCK_CLASS, int.class, 0, IBLOCK_DATA_CLASS);
        GET_BY_COMBINED_ID = Reflection.getMethod(BLOCK_CLASS, IBLOCK_DATA_CLASS, 0, int.class);
        if (CRAFT_BLOCK_DATA_CLASS != null) {
            GET_CRAFT_BLOCK_DATA_FROM_IBLOCKDATA =
                    Reflection.getMethodExact(
                            CRAFT_BLOCK_DATA_CLASS,
                            "fromData",
                            CRAFT_BLOCK_DATA_CLASS,
                            IBLOCK_DATA_CLASS);
        }
        READ_NBT_FROM_STREAM_METHOD =
                Reflection.getMethod(NBT_COMPRESSION_STREAM_TOOLS_CLASS, 0, DataInputStream.class);
        if (READ_NBT_FROM_STREAM_METHOD == null) {
            if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
                READ_NBT_FROM_STREAM_METHOD =
                        Reflection.getMethod(
                                NBT_COMPRESSION_STREAM_TOOLS_CLASS,
                                0,
                                DataInput.class,
                                NBT_ACCOUNTER);
            } else {
                READ_NBT_FROM_STREAM_METHOD =
                        Reflection.getMethod(
                                NBT_COMPRESSION_STREAM_TOOLS_CLASS, 0, DataInput.class);
            }
        }
        if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_2)
                && VERSION.isOlderThan(ServerVersion.V_1_20_5)) {
            WRITE_NBT_TO_STREAM_METHOD =
                    Reflection.getMethod(
                            NBT_COMPRESSION_STREAM_TOOLS_CLASS,
                            "a",
                            NMS_NBT_BASE_CLASS,
                            DataOutput.class);
        } else {
            WRITE_NBT_TO_STREAM_METHOD =
                    Reflection.getMethod(
                            NBT_COMPRESSION_STREAM_TOOLS_CLASS,
                            0,
                            VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_2)
                                    ? NMS_NBT_BASE_CLASS
                                    : NMS_NBT_COMPOUND_CLASS,
                            DataOutput.class);
        }
        if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            NBT_ACCOUNTER_UNLIMITED_HEAP = Reflection.getMethod(NBT_ACCOUNTER, NBT_ACCOUNTER, 0);
        }
        if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            STREAM_DECODER_DECODE = STREAM_DECODER.getMethods()[0];
            STREAM_ENCODER_ENCODE = STREAM_ENCODER.getMethods()[0];
        }
        CREATE_REGISTRY_RESOURCE_KEY =
                Reflection.getMethod(RESOURCE_KEY, 0, NMS_MINECRAFT_KEY_CLASS);
        GET_REGISTRY_OR_THROW =
                Reflection.getMethod(
                        REGISTRY_ACCESS,
                        VERSION.isNewerThanOrEquals(ServerVersion.V_1_17)
                                ? REGISTRY
                                : WRITABLE_REGISTRY,
                        0,
                        RESOURCE_KEY);
        GET_DIMENSION_TYPES = Reflection.getMethod(REGISTRY_ACCESS_FROZEN, REGISTRY, 0);
        GET_REGISTRY_ID = Reflection.getMethod(REGISTRY, int.class, 0, Object.class);
        if (IBLOCKACCESS_CLASS != null) {
            CHUNK_CACHE_GET_IBLOCKACCESS =
                    Reflection.getMethod(
                            CHUNK_PROVIDER_SERVER_CLASS,
                            IBLOCKACCESS_CLASS,
                            0,
                            int.class,
                            int.class);
            IBLOCKACCESS_GET_BLOCK_DATA =
                    Reflection.getMethod(IBLOCKACCESS_CLASS, IBLOCK_DATA_CLASS, 0);
        }
        if (ICHUNKACCESS_CLASS != null) {
            CHUNK_CACHE_GET_ICHUNKACCESS =
                    Reflection.getMethod(
                            CHUNK_PROVIDER_SERVER_CLASS,
                            ICHUNKACCESS_CLASS,
                            0,
                            int.class,
                            int.class,
                            boolean.class);
        }
        if (IBLOCK_DATA_CLASS != null) {
            CHUNK_GET_BLOCK_DATA =
                    Reflection.getMethod(CHUNK_CLASS, IBLOCK_DATA_CLASS, 0, BLOCK_POSITION_CLASS);
        }
        if (PLAYER_CHUNK_CLASS != null) {
            PLAYER_CHUNK_MAP_GET_PLAYER_CHUNK =
                    Reflection.getMethod(PLAYER_CHUNK_MAP_CLASS, PLAYER_CHUNK_CLASS, 0, long.class);
        }
        if (CHUNK_CLASS != null) {
            PLAYER_CHUNK_GET_CHUNK = Reflection.getMethod(PLAYER_CHUNK_CLASS, CHUNK_CLASS, 0);
        }
        LEGACY_DATA_WATCHER_WRITE_METHOD =
                Reflection.getMethod(
                        DATA_WATCHER_CLASS, void.class, 0, NMS_PACKET_DATA_SERIALIZER_CLASS);
        CLIENTBOUND_SET_ENTITY_DATA_PACKET_WRITE_DATA_WATCHER_METHOD =
                Reflection.getMethod(
                        CLIENTBOUND_SET_ENTITY_DATA_PACKET_CLASS,
                        0,
                        List.class,
                        REGISTRY_FRIENDLY_BYTE_BUF);
        GET_DATA_VALUE_FROM_DATA_ITEM_METHOD =
                Reflection.getMethod(DATA_WATCHER_ITEM_CLASS, DATA_WATCHER_VALUE_CLASS, 0);
        GET_TPS = Reflection.getMethod(Server.class, "getTPS");
        REGISTRY_OPS_CREATE = Reflection.getMethod(REGISTRY_OPS, REGISTRY_OPS, 0);
        GET_NEXT_ENTITY_ID = Reflection.getMethod(SERVER_LEVEL_CLASS, "getNextEntityId");
    }

    private static void initFields() {
        ENTITY_BOUNDING_BOX_FIELD =
                Reflection.getField(NMS_ENTITY_CLASS, BOUNDING_BOX_CLASS, 0, true);
        ENTITY_PLAYER_PING_FIELD = Reflection.getField(ENTITY_PLAYER_CLASS, "ping");
        BYTE_BUF_IN_PACKET_DATA_SERIALIZER =
                Reflection.getField(NMS_PACKET_DATA_SERIALIZER_CLASS, BYTE_BUF_CLASS, 0, true);
        CRAFT_PARTICLE_PARTICLES_FIELD = Reflection.getField(CRAFT_PARTICLE_CLASS, "particles");
        NMS_MK_KEY_FIELD = Reflection.getField(NMS_MINECRAFT_KEY_CLASS, "key");
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isOlderThanOrEquals(ServerVersion.V_1_12_2)) {
            LEGACY_NMS_PARTICLE_KEY_FIELD = Reflection.getField(NMS_ENUM_PARTICLE_CLASS, "X");
            LEGACY_NMS_KEY_TO_NMS_PARTICLE = Reflection.getField(NMS_ENUM_PARTICLE_CLASS, "ac");
        }
        DIMENSION_CODEC_FIELD = Reflection.getField(DIMENSION_MANAGER_CLASS, MOJANG_CODEC_CLASS, 0);
        DYNAMIC_OPS_NBT_INSTANCE_FIELD =
                Reflection.getField(DYNAMIC_OPS_NBT_CLASS, DYNAMIC_OPS_NBT_CLASS, 0);
        CHUNK_PROVIDER_SERVER_FIELD =
                Reflection.getField(SERVER_LEVEL_CLASS, CHUNK_PROVIDER_SERVER_CLASS, 0);
        if (CHUNK_PROVIDER_SERVER_FIELD == null) {
            CHUNK_PROVIDER_SERVER_FIELD =
                    Reflection.getField(SERVER_LEVEL_CLASS, ICHUNKPROVIDER_CLASS, 0);
        }
        PAPER_ENTITY_LOOKUP_EXISTS =
                Reflection.getField(SERVER_LEVEL_CLASS, PAPER_ENTITY_LOOKUP_CLASS, 0) != null;
        if (PAPER_ENTITY_LOOKUP_EXISTS) {
            PAPER_ENTITY_LOOKUP_LEGACY =
                    Reflection.getField(LEVEL_CLASS, PAPER_ENTITY_LOOKUP_CLASS, 0) == null;
        }
        REMOTE_CHAT_SESSION_FIELD =
                Reflection.getField(ENTITY_PLAYER_CLASS, REMOTE_CHAT_SESSION_CLASS, 0);
        REGISTRY_KEY_LOCATION_FIELD = Reflection.getField(RESOURCE_KEY, NMS_MINECRAFT_KEY_CLASS, 1);
        DATA_WATCHER_FIELD = Reflection.getField(NMS_ENTITY_CLASS, DATA_WATCHER_CLASS, 0, true);
        PAPER_CONNECTION_HANDLE_FIELD =
                Reflection.getField(
                        PAPER_COMMON_CONNECTION_CLASS, SERVER_COMMON_PACKETLISTENER_IMPL_CLASS, 0);
        PACKETLISTENER_CONNECTION_FIELD =
                Reflection.getField(
                        SERVER_COMMON_PACKETLISTENER_IMPL_CLASS, NETWORK_MANAGER_CLASS, 0);
        CONNECTION_CHANNEL_FIELD = Reflection.getField(NETWORK_MANAGER_CLASS, CHANNEL_CLASS, 0);
        ENTITY_ID_COUNTER = Reflection.getField(NMS_ENTITY_CLASS, "entityCount");
        if (ENTITY_ID_COUNTER == null) {
            ENTITY_ID_COUNTER = Reflection.getField(NMS_ENTITY_CLASS, AtomicInteger.class, 0);
        }
        if (ENTITY_ID_COUNTER == null) {
            ENTITY_ID_COUNTER = Reflection.getField(SERVER_LEVEL_CLASS, "ENTITY_COUNTER");
        }
    }

    private static void initClasses() {
        IS_OBFUSCATED =
                Reflection.getClassByNameWithoutException(
                                "net.minecraft.server.network.PlayerConnection")
                        != null;
        MINECRAFT_SERVER_CLASS = getServerClass("server.MinecraftServer", "MinecraftServer");
        NMS_PACKET_DATA_SERIALIZER_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "network.PacketDataSerializer" : "network.FriendlyByteBuf",
                        "PacketDataSerializer");
        NMS_ITEM_STACK_CLASS = getServerClass("world.item.ItemStack", "ItemStack");
        NMS_IMATERIAL_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "world.level.IMaterial" : "world.level.ItemLike",
                        "IMaterial");
        NMS_ENTITY_CLASS = getServerClass("world.entity.Entity", "Entity");
        ENTITY_PLAYER_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "server.level.EntityPlayer" : "server.level.ServerPlayer",
                        "EntityPlayer");
        BOUNDING_BOX_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "world.phys.AxisAlignedBB" : "world.phys.AABB",
                        "AxisAlignedBB");
        String text =
                VERSION.isNewerThanOrEquals(ServerVersion.V_1_21_11)
                        ? "resources.Identifier"
                        : "resources.ResourceLocation";
        NMS_MINECRAFT_KEY_CLASS =
                getServerClass(IS_OBFUSCATED ? "resources.MinecraftKey" : text, "MinecraftKey");
        ENTITY_HUMAN_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "world.entity.player.EntityHuman"
                                : "world.entity.player.Player",
                        "EntityHuman");
        PLAYER_CONNECTION_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "server.network.PlayerConnection"
                                : "server.network.ServerGamePacketListenerImpl",
                        "PlayerConnection");
        SERVER_LOGIN_PACKET_LISTENER_IMPL_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "server.network.LoginListener"
                                : "server.network.ServerLoginPacketListenerImpl",
                        "LoginListener");
        SERVER_COMMON_PACKETLISTENER_IMPL_CLASS =
                getServerClass(
                        "server.network.ServerCommonPacketListenerImpl",
                        "ServerCommonPacketListenerImpl");
        TRANSFER_COOKIE_CONNECTION_CLASS =
                getOBCClass("entity.CraftPlayer$TransferCookieConnection");
        PAPER_COMMON_CONNECTION_CLASS =
                Reflection.getClassByNameWithoutException(
                        "io.papermc.paper.connection.PaperCommonConnection");
        SERVER_CONNECTION_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "server.network.ServerConnection"
                                : "server.network.ServerConnectionListener",
                        "ServerConnection");
        NETWORK_MANAGER_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "network.NetworkManager" : "network.Connection",
                        "NetworkManager");
        MOB_EFFECT_LIST_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "world.effect.MobEffectList" : "world.effect.MobEffect",
                        "MobEffectList");
        NMS_ITEM_CLASS = getServerClass("world.item.Item", "Item");
        DEDICATED_SERVER_CLASS =
                getServerClass("server.dedicated.DedicatedServer", "DedicatedServer");
        LEVEL_CLASS =
                getServerClass(IS_OBFUSCATED ? "world.level.World" : "world.level.Level", "World");
        SERVER_LEVEL_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "server.level.WorldServer" : "server.level.ServerLevel",
                        "WorldServer");
        ENUM_PROTOCOL_DIRECTION_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "network.protocol.EnumProtocolDirection"
                                : "network.protocol.PacketFlow",
                        "EnumProtocolDirection");
        if (V_1_17_OR_HIGHER) {
            LEVEL_ENTITY_GETTER_CLASS = getServerClass("world.level.entity.LevelEntityGetter", "");
            PERSISTENT_ENTITY_SECTION_MANAGER_CLASS =
                    getServerClass("world.level.entity.PersistentEntitySectionManager", "");
            PAPER_ENTITY_LOOKUP_CLASS =
                    Reflection.getClassByNameWithoutException(
                            "ca.spottedleaf.moonrise.patches.chunk_system.level.entity.EntityLookup");
            if (PAPER_ENTITY_LOOKUP_CLASS == null) {
                PAPER_ENTITY_LOOKUP_CLASS =
                        Reflection.getClassByNameWithoutException(
                                "io.papermc.paper.chunk.system.entity.EntityLookup");
            }
            ENTITY_ACCESS_CLASS = getServerClass("world.level.entity.EntityAccess", "");
        }
        DIMENSION_MANAGER_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "world.level.dimension.DimensionManager"
                                : "world.level.dimension.DimensionType",
                        "DimensionManager");
        MOJANG_CODEC_CLASS =
                Reflection.getClassByNameWithoutException("com.mojang.serialization.Codec");
        MOJANG_ENCODER_CLASS =
                Reflection.getClassByNameWithoutException("com.mojang.serialization.Encoder");
        DATA_RESULT_CLASS =
                Reflection.getClassByNameWithoutException("com.mojang.serialization.DataResult");
        DYNAMIC_OPS_NBT_CLASS =
                getServerClass(IS_OBFUSCATED ? "nbt.DynamicOpsNBT" : "nbt.NbtOps", "DynamicOpsNBT");
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isOlderThanOrEquals(ServerVersion.V_1_12_2)) {
            NMS_ENUM_PARTICLE_CLASS = getServerClass(null, "EnumParticle");
        }
        CRAFT_MAGIC_NUMBERS_CLASS = getOBCClass("util.CraftMagicNumbers");
        IBLOCK_DATA_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "world.level.block.state.IBlockData"
                                : "world.level.block.state.BlockState",
                        "IBlockData");
        BLOCK_CLASS = getServerClass("world.level.block.Block", "Block");
        CRAFT_BLOCK_DATA_CLASS = getOBCClass("block.data.CraftBlockData");
        GAME_PROFILE_CLASS =
                Reflection.getClassByNameWithoutException("com.mojang.authlib.GameProfile");
        CRAFT_WORLD_CLASS = getOBCClass("CraftWorld");
        CRAFT_PLAYER_CLASS = getOBCClass("entity.CraftPlayer");
        CRAFT_SERVER_CLASS = getOBCClass("CraftServer");
        CRAFT_ENTITY_CLASS = getOBCClass("entity.CraftEntity");
        CRAFT_ITEM_STACK_CLASS = getOBCClass("inventory.CraftItemStack");
        CRAFT_PARTICLE_CLASS = getOBCClass("CraftParticle");
        CHANNEL_CLASS = getNettyClass("channel.Channel");
        BYTE_BUF_CLASS = getNettyClass("buffer.ByteBuf");
        BYTE_TO_MESSAGE_DECODER = getNettyClass("handler.codec.ByteToMessageDecoder");
        MESSAGE_TO_BYTE_ENCODER = getNettyClass("handler.codec.MessageToByteEncoder");
        NMS_NBT_COMPOUND_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "nbt.NBTTagCompound" : "nbt.CompoundTag", "NBTTagCompound");
        NMS_NBT_BASE_CLASS = getServerClass(IS_OBFUSCATED ? "nbt.NBTBase" : "nbt.Tag", "NBTBase");
        NBT_COMPRESSION_STREAM_TOOLS_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "nbt.NBTCompressedStreamTools" : "nbt.NbtIo",
                        "NBTCompressedStreamTools");
        NBT_ACCOUNTER =
                getServerClass(
                        IS_OBFUSCATED ? "nbt.NBTReadLimiter" : "nbt.NbtAccounter",
                        "NBTReadLimiter");
        CHUNK_PROVIDER_SERVER_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "server.level.ChunkProviderServer"
                                : "server.level.ServerChunkCache",
                        "ChunkProviderServer");
        ICHUNKPROVIDER_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "world.level.chunk.IChunkProvider"
                                : "world.level.chunk.ChunkSource",
                        "IChunkProvider");
        CHUNK_STATUS_CLASS = getServerClass("world.level.chunk.status.ChunkStatus", "");
        if (CHUNK_STATUS_CLASS == null) {
            CHUNK_STATUS_CLASS = getServerClass("world.level.ChunkStatus", "");
        }
        BLOCK_POSITION_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "core.BlockPosition" : "core.BlockPos", "BlockPosition");
        PLAYER_CHUNK_MAP_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "server.level.PlayerChunkMap" : "server.level.ChunkMap",
                        "");
        PLAYER_CHUNK_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "server.level.PlayerChunk" : "server.level.ChunkHolder",
                        "");
        CHUNK_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "world.level.chunk.Chunk" : "world.level.chunk.LevelChunk",
                        "Chunk");
        IBLOCKACCESS_CLASS =
                getServerClass(
                        IS_OBFUSCATED ? "world.level.IBlockAccess" : "world.level.BlockGetter",
                        "IBlockAccess");
        ICHUNKACCESS_CLASS =
                getServerClass(
                        IS_OBFUSCATED
                                ? "world.level.chunk.IChunkAccess"
                                : "world.level.chunk.ChunkAccess",
                        "IChunkAccess");
        if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
            STREAM_CODEC =
                    Reflection.getClassByNameWithoutException(
                            "net.minecraft.network.codec.StreamCodec");
            STREAM_DECODER =
                    Reflection.getClassByNameWithoutException(
                            "net.minecraft.network.codec.StreamDecoder");
            STREAM_ENCODER =
                    Reflection.getClassByNameWithoutException(
                            "net.minecraft.network.codec.StreamEncoder");
            REGISTRY_FRIENDLY_BYTE_BUF =
                    Reflection.getClassByNameWithoutException(
                            "net.minecraft.network.RegistryFriendlyByteBuf");
        }
        REGISTRY_ACCESS =
                getServerClass(
                        IS_OBFUSCATED ? "core.IRegistryCustom" : "core.RegistryAccess",
                        "IRegistryCustom");
        REGISTRY_ACCESS_FROZEN =
                getServerClass(
                        IS_OBFUSCATED
                                ? "core.IRegistryCustom$Dimension"
                                : "core.RegistryAccess$Frozen",
                        "IRegistryCustom$Dimension");
        RESOURCE_KEY = getServerClass("resources.ResourceKey", "ResourceKey");
        REGISTRY = getServerClass(IS_OBFUSCATED ? "core.IRegistry" : "core.Registry", "IRegistry");
        WRITABLE_REGISTRY =
                getServerClass(
                        IS_OBFUSCATED ? "core.IRegistryWritable" : "core.WritableRegistry",
                        "IRegistryWritable");
        REMOTE_CHAT_SESSION_CLASS =
                Reflection.getClassByNameWithoutException(
                        "net.minecraft.network.chat.RemoteChatSession");
        DATA_WATCHER_CLASS = getServerClass("network.syncher.DataWatcher", "DataWatcher");
        if (DATA_WATCHER_CLASS == null) {
            DATA_WATCHER_CLASS = getServerClass("network.syncher.SynchedEntityData", "DataWatcher");
        }
        CLIENTBOUND_SET_ENTITY_DATA_PACKET_CLASS =
                getServerClass(
                        "network.protocol.game.ClientboundSetEntityDataPacket",
                        "PacketPlayOutEntityMetadata");
        DATA_WATCHER_ITEM_CLASS = NestedClassUtil.getNestedClass(DATA_WATCHER_CLASS, 0);
        DATA_WATCHER_VALUE_CLASS = NestedClassUtil.getNestedClass(DATA_WATCHER_CLASS, 1);
        REGISTRY_OPS = getServerClass("resources.RegistryOps", "RegistryOps");
    }

    private static void initObjects() {
        try {
            if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_20_5)) {
                ITEM_STACK_OPTIONAL_STREAM_CODEC =
                        Reflection.getField(NMS_ITEM_STACK_CLASS, STREAM_CODEC, 0).get(null);
            }
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    public static void init() {
        VERSION = PacketEvents.getAPI().getServerManager().getVersion();
        V_1_19_OR_HIGHER = VERSION.isNewerThanOrEquals(ServerVersion.V_1_19);
        V_1_17_OR_HIGHER = VERSION.isNewerThanOrEquals(ServerVersion.V_1_17);
        V_1_12_OR_HIGHER = VERSION.isNewerThanOrEquals(ServerVersion.V_1_12);
        initClasses();
        initFields();
        initMethods();
        initConstructors();
        initObjects();
    }

    @Nullable
    public static Class<?> getServerClass(String text, String text2) {
        return V_1_17_OR_HIGHER
                ? Reflection.getClassByNameWithoutException("net.minecraft." + text)
                : Reflection.getClassByNameWithoutException(LEGACY_NMS_PACKAGE + text2);
    }

    public static boolean isMinecraftServerInstanceDebugging() {
        Object result;
        if ((result = getMinecraftServerInstance(Bukkit.getServer())) != null
                && IS_DEBUGGING != null) {
            try {
                return (Boolean) IS_DEBUGGING.invoke(result);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                IS_DEBUGGING = null;
                return false;
            }
        } else {
            return false;
        }
    }

    public static Object getMinecraftServerInstance(Server server) {
        if (MINECRAFT_SERVER_INSTANCE == null) {
            try {
                Field field;
                if ((field = Reflection.getField(CRAFT_SERVER_CLASS, MINECRAFT_SERVER_CLASS, 0))
                        == null) {
                    MINECRAFT_SERVER_INSTANCE =
                            Reflection.getField(MINECRAFT_SERVER_CLASS, MINECRAFT_SERVER_CLASS, 0)
                                    .get(null);
                } else {
                    MINECRAFT_SERVER_INSTANCE = field.get(server);
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return MINECRAFT_SERVER_INSTANCE;
    }

    public static Object getMinecraftServerConnectionInstance() {
        if (MINECRAFT_SERVER_CONNECTION_INSTANCE == null) {
            try {
                MINECRAFT_SERVER_CONNECTION_INSTANCE =
                        Reflection.getField(MINECRAFT_SERVER_CLASS, SERVER_CONNECTION_CLASS, 0)
                                .get(getMinecraftServerInstance(Bukkit.getServer()));
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return MINECRAFT_SERVER_CONNECTION_INSTANCE;
    }

    public static double getTPS() {
        return recentTPS()[0];
    }

    public static double[] recentTPS() {
        if (GET_TPS != null) {
            try {
                return (double[]) GET_TPS.invoke(Bukkit.getServer());
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new RuntimeException(exception);
            }
        } else {
            return new ReflectionObject(
                            getMinecraftServerInstance(Bukkit.getServer()), MINECRAFT_SERVER_CLASS)
                    .readDoubleArray(0);
        }
    }

    public static Class<?> getNMSClass(String text) throws ClassNotFoundException {
        return Class.forName(ReflectionNames.className(LEGACY_NMS_PACKAGE + text));
    }

    public static Class<?> getOBCClass(String text) {
        return Reflection.getClassByNameWithoutException(OBC_PACKAGE + text);
    }

    public static Class<?> getNettyClass(String text) {
        return Reflection.getClassByNameWithoutException("io.netty." + text);
    }

    public static Entity getBukkitEntity(Object result) {
        Object result2 = null;
        try {
            result2 = GET_BUKKIT_ENTITY_METHOD.invoke(result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        return (Entity) result2;
    }

    public static Object getNMSEntity(Entity entity) {
        Object result = CRAFT_ENTITY_CLASS.cast(entity);
        try {
            return GET_CRAFT_ENTITY_HANDLE_METHOD.invoke(result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getNMSAxisAlignedBoundingBox(Object result) {
        try {
            return ENTITY_BOUNDING_BOX_FIELD.get(NMS_ENTITY_CLASS.cast(result));
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
    }

    public static Object getCraftPlayer(Player player) {
        return CRAFT_PLAYER_CLASS.cast(player);
    }

    public static Object getEntityPlayer(Player player) {
        Object craftPlayer = getCraftPlayer(player);
        try {
            return GET_CRAFT_PLAYER_HANDLE_METHOD.invoke(craftPlayer);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getPlayerConnection(Player player) {
        Object result;
        if ((result = getEntityPlayer(player)) == null) {
            return null;
        }
        ReflectionObject reflectionObject = new ReflectionObject(result, ENTITY_PLAYER_CLASS);
        return TRANSFER_COOKIE_CONNECTION_CLASS != null
                ? reflectionObject.readObject(0, TRANSFER_COOKIE_CONNECTION_CLASS)
                : reflectionObject.readObject(0, PLAYER_CONNECTION_CLASS);
    }

    public static Object getGameProfile(Player player) {
        Object entityPlayer = getEntityPlayer(player);
        return new ReflectionObject(entityPlayer, ENTITY_HUMAN_CLASS)
                .readObject(0, GAME_PROFILE_CLASS);
    }

    public static List<TextureProperty> getUserProfile(Player player) {
        if (PROPERTY_MAP_CLASS == null) {
            PROPERTY_MAP_CLASS =
                    Reflection.getClassByNameWithoutException(
                            "com.mojang.authlib.properties.PropertyMap");
            PROPERTY_MAP_GET_METHOD =
                    Reflection.getMethodExact(
                            PROPERTY_MAP_CLASS, "get", Collection.class, Object.class);
        }
        Object gameProfile = getGameProfile(player);
        Object result = new ReflectionObject(gameProfile).readObject(0, PROPERTY_MAP_CLASS);
        Collection values = null;
        try {
            values = (Collection) PROPERTY_MAP_GET_METHOD.invoke(result, "textures");
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        ArrayList<TextureProperty> values2 = new ArrayList<>();
        for (Object result2 : values) {
            ReflectionObject reflectionObject = new ReflectionObject(result2);
            String text = reflectionObject.readString(1);
            String text2 = reflectionObject.readString(2);
            TextureProperty textureProperty = new TextureProperty((String) "textures", text, text2);
            values2.add(textureProperty);
        }
        return values2;
    }

    public static Object getNetworkManager(Player player) {
        Object result;
        if ((result = getPlayerConnection(player)) == null) {
            return null;
        }
        Class classValue;
        if (SERVER_COMMON_PACKETLISTENER_IMPL_CLASS != null) {
            classValue =
                    result.getClass() == SERVER_LOGIN_PACKET_LISTENER_IMPL_CLASS
                            ? SERVER_LOGIN_PACKET_LISTENER_IMPL_CLASS
                            : SERVER_COMMON_PACKETLISTENER_IMPL_CLASS;
        } else {
            classValue = PLAYER_CONNECTION_CLASS;
        }
        ReflectionObject reflectionObject = new ReflectionObject(result, classValue);
        try {
            return reflectionObject.readObject(0, NETWORK_MANAGER_CLASS);
        } catch (Exception exception) {
            try {
                result = reflectionObject.read(0, PLAYER_CONNECTION_CLASS);
                return new ReflectionObject(result, PLAYER_CONNECTION_CLASS)
                        .readObject(0, NETWORK_MANAGER_CLASS);
            } catch (Exception exception2) {
                exception.printStackTrace();
                return null;
            }
        }
    }

    @Nullable
    public static Object getChannel(Player player) {
        Object result;
        return (result = getNetworkManager(player)) == null
                ? null
                : new ReflectionObject(result, NETWORK_MANAGER_CLASS).readObject(0, CHANNEL_CLASS);
    }

    public static Object getChannelFromPaperConnection(Object result) {
        try {
            Object result2 = PAPER_CONNECTION_HANDLE_FIELD.get(result);
            Object result3 = PACKETLISTENER_CONNECTION_FIELD.get(result2);
            return CONNECTION_CHANNEL_FIELD.get(result3);
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    @Deprecated
    public static int getPlayerPingLegacy(Player player) {
        if (V_1_17_OR_HIGHER) {
            return -1;
        }
        if (ENTITY_PLAYER_PING_FIELD != null) {
            Object entityPlayer = getEntityPlayer(player);
            try {
                return ENTITY_PLAYER_PING_FIELD.getInt(entityPlayer);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
        return -1;
    }

    public static List<Object> getNetworkManagers() {
        ReflectionObject reflectionObject =
                new ReflectionObject(getMinecraftServerConnectionInstance());
        int value = 0;
        while (true) {
            try {
                List<Object> values;
                Iterator iterator2 =
                        (values = reflectionObject.readObject(value, List.class)).iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().getClass().isAssignableFrom(NETWORK_MANAGER_CLASS)) {
                        return values;
                    }
                }
            } catch (Exception exception) {
                return reflectionObject.readObject(1, List.class);
            }
            value++;
        }
    }

    public static Object convertBukkitServerToNMSServer(Server server) {
        Object result = CRAFT_SERVER_CLASS.cast(server);
        ReflectionObject reflectionObject = new ReflectionObject(result);
        try {
            return reflectionObject.readObject(0, MINECRAFT_SERVER_CLASS);
        } catch (Exception exception) {
            reflectionObject.readObject(0, DEDICATED_SERVER_CLASS);
            return null;
        }
    }

    public static Object convertBukkitWorldToWorldServer(World world) {
        Object result = CRAFT_WORLD_CLASS.cast(world);
        try {
            return GET_CRAFT_WORLD_HANDLE_METHOD.invoke(result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object convertWorldServerDimensionToNMSNbt(Object result) {
        try {
            Object result2 = GET_DIMENSION_MANAGER.invoke(result);
            Object result3 = DIMENSION_CODEC_FIELD.get(null);
            Object result4 = DYNAMIC_OPS_NBT_INSTANCE_FIELD.get(null);
            if (VERSION.isOlderThan(ServerVersion.V_1_16_2)) {
                Object result5 = result2;
                result2 = (Supplier<Object>) () -> result5;
            }
            if (REGISTRY_OPS_CREATE != null) {
                result4 = REGISTRY_OPS_CREATE.invoke(null, result4, getFrozenRegistryAccess());
            }
            Object result6 = CODEC_ENCODE_METHOD.invoke(result3, result4, result2);
            return ((Optional) DATA_RESULT_GET_METHOD.invoke(result6)).orElse(null);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getDimensionId(Object result) {
        try {
            Object result2 = GET_DIMENSION_MANAGER.invoke(result);
            Object result3;
            if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                if (DIMENSION_TYPE_REGISTRY_KEY == null) {
                    Object result4 =
                            NMS_MINECRAFT_KEY_CONSTRUCTOR.newInstance(
                                    "minecraft", "dimension_type");
                    DIMENSION_TYPE_REGISTRY_KEY =
                            CREATE_REGISTRY_RESOURCE_KEY.invoke(null, result4);
                }
                result3 =
                        GET_REGISTRY_OR_THROW.invoke(
                                getFrozenRegistryAccess(), DIMENSION_TYPE_REGISTRY_KEY);
            } else {
                result3 = GET_DIMENSION_TYPES.invoke(getFrozenRegistryAccess());
            }
            return (Integer) GET_REGISTRY_ID.invoke(result3, result2);
        } catch (IllegalAccessException
                | InvocationTargetException
                | InstantiationException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public static String getDimensionKey(Object result) {
        try {
            Object result2 = GET_DIMENSION_KEY.invoke(result);
            return REGISTRY_KEY_LOCATION_FIELD.get(result2).toString();
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String fromStringToJSON(String text) {
        return text == null ? null : "{\"text\": \"" + text + "\"}";
    }

    public static int generateEntityId(World world) {
        if (GET_NEXT_ENTITY_ID != null) {
            try {
                Object result = GET_CRAFT_WORLD_HANDLE_METHOD.invoke(world);
                return (Integer) GET_NEXT_ENTITY_ID.invoke(result);
            } catch (ReflectiveOperationException reflectiveOperationException) {
                throw new RuntimeException(
                        "Failed to generate entity id: ", reflectiveOperationException);
            }
        } else {
            return generateEntityId();
        }
    }

    public static int generateEntityId() {
        try {
            if (ENTITY_ID_COUNTER.getType() == AtomicInteger.class) {
                return ((AtomicInteger) ENTITY_ID_COUNTER.get(null)).incrementAndGet();
            }
            int intValue = ENTITY_ID_COUNTER.getInt(null);
            ENTITY_ID_COUNTER.set(null, intValue + 1);
            return intValue;
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            throw new IllegalStateException("Failed to generate a new unique entity ID!");
        }
    }

    public static int getEffectId(Object result) {
        try {
            return (Integer) GET_MOB_EFFECT_LIST_ID_METHOD.invoke(null, result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public static Object getMobEffectListById(int value) {
        try {
            return GET_MOB_EFFECT_LIST_BY_ID_METHOD.invoke(null, value);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static int getNMSItemId(Object result) {
        try {
            return (Integer) GET_ITEM_ID_METHOD.invoke(null, result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public static Object getNMSItemById(int value) {
        try {
            return GET_ITEM_BY_ID_METHOD.invoke(null, value);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object createNMSItemStack(Object result, int value) {
        try {
            return NMS_ITEM_STACK_CONSTRUCTOR.newInstance(result, value);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack
            decodeBukkitItemStack(ItemStack itemStack) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
        try {
            Object result = createPacketDataSerializer(byteBuf);
            Object result2 = toNMSItemStack(itemStack);
            writeNMSItemStackPacketDataSerializer(result, result2);
            return PacketWrapper.createUniversalPacketWrapper(byteBuf).readItemStack();
        } finally {
            ByteBufHelper.release(byteBuf);
        }
    }

    public static ItemStack encodeBukkitItemStack(
            it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.item.ItemStack itemStack) {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.buffer();
        try {
            PacketWrapper packetWrapper;
            (packetWrapper = PacketWrapper.createUniversalPacketWrapper(byteBuf))
                    .writeItemStack(itemStack);
            return toBukkitItemStack(
                    readNMSItemStackPacketDataSerializer(
                            createPacketDataSerializer(packetWrapper.getBuffer())));
        } finally {
            ByteBufHelper.release(byteBuf);
        }
    }

    public static int getBlockDataCombinedId(MaterialData materialData) {
        int value;
        if (PacketEvents.getAPI()
                .getServerManager()
                .getVersion()
                .isNewerThanOrEquals(ServerVersion.V_1_13)) {
            value = -1;
        } else {
            value = materialData.getItemType().getId() << 4 | materialData.getData();
        }
        return value;
    }

    public static MaterialData getBlockDataByCombinedId(int value) {
        Object result = null;
        try {
            result = GET_BY_COMBINED_ID.invoke(null, value);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
        try {
            Class<?> classValue;
            Object result2 =
                    (classValue =
                                    Reflection.getClassByNameWithoutException(
                                            "org.bukkit.block.data.BlockData"))
                            .cast(GET_CRAFT_BLOCK_DATA_FROM_IBLOCKDATA.invoke(null, result));
            Method method = Reflection.getMethod(classValue, Material.class, 0);
            return new MaterialData((Material) method.invoke(result2));
        } catch (IllegalAccessException | InvocationTargetException exception2) {
            exception2.printStackTrace();
            return null;
        }
    }

    public static Object createNMSItemStack(int value, int value2) {
        try {
            Object nMSItemById = getNMSItemById(value);
            return NMS_ITEM_STACK_CONSTRUCTOR.newInstance(nMSItemById, value2);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object createPacketDataSerializer(Object result) {
        try {
            return REGISTRY_FRIENDLY_BYTE_BUF_CONSTRUCTOR != null
                    ? REGISTRY_FRIENDLY_BYTE_BUF_CONSTRUCTOR.newInstance(
                            result, getFrozenRegistryAccess())
                    : NMS_PACKET_DATA_SERIALIZER_CONSTRUCTOR.newInstance(result);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object createBlockPosition(int value, int value2, int value3) {
        try {
            return BLOCK_POSITION_CONSTRUCTOR.newInstance(value, value2, value3);
        } catch (InstantiationException
                | IllegalAccessException
                | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getFrozenRegistryAccess() {
        if (MINECRAFT_SERVER_REGISTRY_ACCESS == null) {
            try {
                if (VERSION.isNewerThanOrEquals(ServerVersion.V_1_16_2)) {
                    MINECRAFT_SERVER_REGISTRY_ACCESS =
                            Reflection.getMethod(
                                            MINECRAFT_SERVER_CLASS,
                                            VERSION.isNewerThanOrEquals(ServerVersion.V_1_18_2)
                                                    ? REGISTRY_ACCESS_FROZEN
                                                    : REGISTRY_ACCESS,
                                            0)
                                    .invoke(getMinecraftServerInstance(Bukkit.getServer()));
                } else {
                    MINECRAFT_SERVER_REGISTRY_ACCESS =
                            Reflection.getField(MINECRAFT_SERVER_CLASS, REGISTRY_ACCESS_FROZEN, 0)
                                    .get(getMinecraftServerInstance(Bukkit.getServer()));
                }
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
        return MINECRAFT_SERVER_REGISTRY_ACCESS;
    }

    public static ItemStack toBukkitItemStack(Object result) {
        try {
            return (ItemStack) CRAFT_ITEM_STACK_AS_BUKKIT_COPY.invoke(null, result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object toNMSItemStack(ItemStack itemStack) {
        try {
            return CRAFT_ITEM_STACK_AS_NMS_COPY.invoke(null, itemStack);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object readNMSItemStackPacketDataSerializer(Object result) {
        try {
            return READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD != null
                    ? READ_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD.invoke(result)
                    : STREAM_DECODER_DECODE.invoke(ITEM_STACK_OPTIONAL_STREAM_CODEC, result);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object writeNMSItemStackPacketDataSerializer(Object result, Object result2) {
        try {
            return WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD != null
                    ? WRITE_ITEM_STACK_IN_PACKET_DATA_SERIALIZER_METHOD.invoke(result, result2)
                    : STREAM_ENCODER_ENCODE.invoke(
                            ITEM_STACK_OPTIONAL_STREAM_CODEC, result, result2);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static NBTCompound fromMinecraftNBT(Object result) {
        byte[] values;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                try {
                    writeNmsNbtToStream(result, dataOutputStream);
                    values = byteArrayOutputStream.toByteArray();
                } catch (Throwable throwable) {
                    try {
                        dataOutputStream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                dataOutputStream.close();
            } catch (Throwable throwable3) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable throwable4) {
                    throwable3.addSuppressed(throwable4);
                }
                throw throwable3;
            }
            byteArrayOutputStream.close();
        } catch (IOException oException) {
            throw new RuntimeException(oException);
        }
        Object result2 = UnpooledByteBufAllocationHelper.wrappedBuffer(values);
        try {
            return PacketWrapper.createUniversalPacketWrapper(result2).readNBT();
        } finally {
            ByteBufHelper.release(result2);
        }
    }

    public static Object toMinecraftNBT(NBTCompound nBTCompound) {
        Object result = UnpooledByteBufAllocationHelper.buffer();
        byte[] values;
        try {
            PacketWrapper.createUniversalPacketWrapper(result).writeNBT(nBTCompound);
            values = ByteBufHelper.copyBytes(result);
        } finally {
            ByteBufHelper.release(result);
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(values);
            Object result2;
            try {
                DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                try {
                    result2 = readNmsNbtFromStream(dataInputStream);
                } catch (Throwable throwable) {
                    try {
                        dataInputStream.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
                dataInputStream.close();
            } catch (Throwable throwable3) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable throwable4) {
                    throwable3.addSuppressed(throwable4);
                }
                throw throwable3;
            }
            byteArrayInputStream.close();
            return result2;
        } catch (IOException oException) {
            oException.printStackTrace();
            return null;
        }
    }

    public static void writeNmsNbtToStream(Object result, DataOutput dataOutput) {
        try {
            WRITE_NBT_TO_STREAM_METHOD.invoke(null, result, dataOutput);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    public static Object readNmsNbtFromStream(DataInputStream dataInputStream) {
        try {
            if (NBT_ACCOUNTER_UNLIMITED_HEAP != null) {
                Object result = NBT_ACCOUNTER_UNLIMITED_HEAP.invoke(null);
                return READ_NBT_FROM_STREAM_METHOD.invoke(null, dataInputStream, result);
            } else {
                return READ_NBT_FROM_STREAM_METHOD.invoke(null, dataInputStream);
            }
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static Entity getEntityByIdWithWorldUnsafe(World world, int value) {
        if (world == null) {
            return null;
        }
        Entity entity;
        if ((entity = ENTITY_ID_CACHE.getOrDefault(value, null)) != null) {
            return entity;
        }
        try {
            Object result = GET_CRAFT_WORLD_HANDLE_METHOD.invoke(world);
            Object result2;
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_17)) {
                ReflectionObject reflectionObject =
                        PAPER_ENTITY_LOOKUP_LEGACY
                                ? new ReflectionObject(result, SERVER_LEVEL_CLASS)
                                : new ReflectionObject(result, LEVEL_CLASS);
                Object result3;
                if (PAPER_ENTITY_LOOKUP_EXISTS) {
                    result3 = reflectionObject.readObject(0, PAPER_ENTITY_LOOKUP_CLASS);
                } else {
                    Object result4 =
                            reflectionObject.readObject(0, PERSISTENT_ENTITY_SECTION_MANAGER_CLASS);
                    result3 =
                            new ReflectionObject(result4).readObject(0, LEVEL_ENTITY_GETTER_CLASS);
                }
                result2 = GET_ENTITY_BY_ID_LEVEL_ENTITY_GETTER_METHOD.invoke(result3, value);
            } else {
                result2 = GET_ENTITY_BY_ID_METHOD.invoke(result, value);
            }
            if (result2 == null) {
                return null;
            }
            Entity entity2;
            if ((entity2 = getBukkitEntity(result2)) == null) {
                return null;
            }
            ENTITY_ID_CACHE.put(value, entity2);
            return entity2;
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(
                    "Error while looking up entity by id " + value + " in " + world, exception);
        }
    }

    @Deprecated
    @Nullable
    public static Entity getEntityById(@Nullable World world, int value) {
        Entity entity;
        if (world != null && (entity = getEntityByIdWithWorldUnsafe(world, value)) != null) {
            return entity;
        }
        Iterator<World> iterator2 = Bukkit.getWorlds().iterator();
        while (iterator2.hasNext()) {
            Entity entity2;
            if ((entity2 = getEntityByIdWithWorldUnsafe((World) iterator2.next(), value)) != null) {
                return entity2;
            }
        }
        return null;
    }

    @Deprecated
    @Nullable
    public static Entity getEntityById(int value) {
        return getEntityById(null, value);
    }

    public static List<Entity> getEntityList(World world) {
        if (V_1_17_OR_HIGHER) {
            Object result = convertBukkitWorldToWorldServer(world);
            ReflectionObject reflectionObject = new ReflectionObject(result);
            Object result2;
            if (PAPER_ENTITY_LOOKUP_EXISTS) {
                if (!PAPER_ENTITY_LOOKUP_LEGACY) {
                    reflectionObject = new ReflectionObject(result, LEVEL_CLASS);
                }
                result2 = reflectionObject.readObject(0, PAPER_ENTITY_LOOKUP_CLASS);
            } else {
                Object result3 =
                        reflectionObject.readObject(0, PERSISTENT_ENTITY_SECTION_MANAGER_CLASS);
                result2 = new ReflectionObject(result3).readObject(0, LEVEL_ENTITY_GETTER_CLASS);
            }
            Iterable iterable = null;
            try {
                iterable = (Iterable) GET_LEVEL_ENTITY_GETTER_ITERABLE_METHOD.invoke(result2);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
            ArrayList<Entity> values = new ArrayList<>();
            if (iterable != null) {
                Iterator iterator2 = iterable.iterator();
                while (iterator2.hasNext()) {
                    Entity bukkitEntity = getBukkitEntity(iterator2.next());
                    values.add(bukkitEntity);
                }
            }
            return values;
        } else {
            return world.getEntities();
        }
    }

    public static ParticleType<?> toPacketEventsParticle(Enum<?> enumValue) {
        try {
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_13)) {
                if (CRAFT_PARTICLE_PARTICLES_FIELD == null) {
                    return ParticleTypes.getByName(((Particle) enumValue).getKey().toString());
                }
                BiMap biMap = (BiMap) CRAFT_PARTICLE_PARTICLES_FIELD.get(null);
                if (enumValue.name().equals("BLOCK_DUST")) {
                    enumValue = Enum.valueOf((Class<Enum>) enumValue.getClass(), "BLOCK_CRACK");
                }
                return ParticleTypes.getByName(biMap.get(enumValue).toString());
            } else {
                Object result = BUKKIT_PARTICLE_TO_NMS_ENUM_PARTICLE.invoke(null, enumValue);
                String text = (String) LEGACY_NMS_PARTICLE_KEY_FIELD.get(result);
                return ParticleTypes.getByName(
                        NMS_MINECRAFT_KEY_CONSTRUCTOR.newInstance("minecraft", text).toString());
            }
        } catch (IllegalAccessException
                | InvocationTargetException
                | InstantiationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Enum<?> fromPacketEventsParticle(ParticleType<?> particleType) {
        try {
            if (PacketEvents.getAPI()
                    .getServerManager()
                    .getVersion()
                    .isNewerThanOrEquals(ServerVersion.V_1_13)) {
                if (CRAFT_PARTICLE_PARTICLES_FIELD == null) {
                    ResourceLocation name = particleType.getName();
                    return (Enum<?>)
                            Registry.PARTICLE_TYPE.get(
                                    new NamespacedKey(name.getNamespace(), name.getKey()));
                } else {
                    BiMap biMap = (BiMap) CRAFT_PARTICLE_PARTICLES_FIELD.get(null);
                    Object result =
                            NMS_MINECRAFT_KEY_CONSTRUCTOR.newInstance(
                                    particleType.getName().getNamespace(),
                                    particleType.getName().getKey());
                    return (Enum<?>) biMap.inverse().get(result);
                }
            } else {
                Object result2 =
                        ((Map) LEGACY_NMS_KEY_TO_NMS_PARTICLE.get(null))
                                .get(particleType.getName().getKey());
                return (Enum<?>) NMS_ENUM_PARTICLE_TO_BUKKIT_PARTICLE.invoke(null, result2);
            }
        } catch (IllegalAccessException
                | InvocationTargetException
                | InstantiationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static Object getRemoteChatSession(Player player) {
        Object entityPlayer = getEntityPlayer(player);
        try {
            return REMOTE_CHAT_SESSION_FIELD.get(entityPlayer);
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return null;
        }
    }

    public static List<EntityData<?>> getEntityMetadata(@NotNull Entity entity) {
        Object result =
                PacketEvents.getAPI().getNettyManager().getByteBufAllocationOperator().buffer();
        try {
            Object nMSEntity = getNMSEntity(entity);
            Object result2 = DATA_WATCHER_FIELD.get(nMSEntity);
            Object result3 = createPacketDataSerializer(result);
            if (LEGACY_DATA_WATCHER_WRITE_METHOD != null) {
                LEGACY_DATA_WATCHER_WRITE_METHOD.invoke(result2, result3);
            } else {
                Object[] results =
                        new ReflectionObject(result2)
                                .readObjectArray(
                                        0, (Class<? extends Object>) DATA_WATCHER_ITEM_CLASS);
                ArrayList values = new ArrayList(results.length);
                int value = results.length;
                for (int index = 0; index < value; index++) {
                    Object result4 = results[index];
                    values.add(GET_DATA_VALUE_FROM_DATA_ITEM_METHOD.invoke(result4));
                }
                CLIENTBOUND_SET_ENTITY_DATA_PACKET_WRITE_DATA_WATCHER_METHOD.invoke(
                        null, values, result3);
            }
            return PacketWrapper.createUniversalPacketWrapper(result).readEntityMetadata();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            ByteBufHelper.release(result);
        }
    }

    static {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String text;
        try {
            text = name.replace(".", ",").split(",")[3];
        } catch (Exception exception) {
            text = "";
        }
        MODIFIED_PACKAGE_NAME = text;
        LEGACY_NMS_PACKAGE = "net.minecraft.server." + MODIFIED_PACKAGE_NAME + ".";
        OBC_PACKAGE = name + ".";
        PAPER_ENTITY_LOOKUP_EXISTS = false;
        PAPER_ENTITY_LOOKUP_LEGACY = true;
        ENTITY_ID_CACHE = new MapMaker().weakValues().makeMap();
    }
}
