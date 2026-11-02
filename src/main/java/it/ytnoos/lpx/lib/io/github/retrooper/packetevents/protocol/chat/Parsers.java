package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.resources.ResourceLocation;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@NullMarked
public final class Parsers {

    private static final VersionedRegistry<Parsers.Parser> REGISTRY =
            new VersionedRegistry<>("command_argument_type");

    public static final Parsers.Parser BRIGADIER_BOOL = define("brigadier:bool", null, null);

    public static final Parsers.Parser BRIGADIER_FLOAT =
            define(
                    "brigadier:float",
                    result -> {
                        byte value3;
                        float value =
                                ((value3 = result.readByte()) & 1) != 0
                                        ? result.readFloat()
                                        : -Float.MAX_VALUE;
                        float value2 = (value3 & 2) != 0 ? result.readFloat() : Float.MAX_VALUE;
                        return Arrays.asList(value3, value, value2);
                    },
                    (result, result2) -> {
                        byte value = (Byte) result2.get(0);
                        result.writeByte(value);
                        if ((value & 1) != 0) {
                            result.writeFloat((Float) result2.get(1));
                        }
                        if ((value & 2) != 0) {
                            result.writeFloat((Float) result2.get(2));
                        }
                    });

    public static final Parsers.Parser BRIGADIER_DOUBLE =
            define(
                    "brigadier:double",
                    result -> {
                        byte value3;
                        double value =
                                ((value3 = result.readByte()) & 1) != 0
                                        ? result.readDouble()
                                        : -Double.MAX_VALUE;
                        double value2 = (value3 & 2) != 0 ? result.readDouble() : Double.MAX_VALUE;
                        return Arrays.asList(value3, value, value2);
                    },
                    (result, result2) -> {
                        byte value = (Byte) result2.get(0);
                        result.writeByte(value);
                        if ((value & 1) != 0) {
                            result.writeDouble((Double) result2.get(1));
                        }
                        if ((value & 2) != 0) {
                            result.writeDouble((Double) result2.get(2));
                        }
                    });

    public static final Parsers.Parser BRIGADIER_INTEGER =
            define(
                    "brigadier:integer",
                    result -> {
                        byte value3;
                        int value =
                                ((value3 = result.readByte()) & 1) != 0
                                        ? result.readInt()
                                        : Integer.MIN_VALUE;
                        int value2 = (value3 & 2) != 0 ? result.readInt() : Integer.MAX_VALUE;
                        return Arrays.asList(value3, value, value2);
                    },
                    (result, result2) -> {
                        byte value = (Byte) result2.get(0);
                        result.writeByte(value);
                        if ((value & 1) != 0) {
                            result.writeInt((Integer) result2.get(1));
                        }
                        if ((value & 2) != 0) {
                            result.writeInt((Integer) result2.get(2));
                        }
                    });

    public static final Parsers.Parser BRIGADIER_LONG =
            define(
                    "brigadier:long",
                    result -> {
                        byte value3;
                        long value =
                                ((value3 = result.readByte()) & 1) != 0
                                        ? result.readLong()
                                        : Long.MIN_VALUE;
                        long value2 = (value3 & 2) != 0 ? result.readLong() : Long.MAX_VALUE;
                        return Arrays.asList(value3, value, value2);
                    },
                    (result, result2) -> {
                        byte value = (Byte) result2.get(0);
                        result.writeByte(value);
                        if ((value & 1) != 0) {
                            result.writeLong((Long) result2.get(1));
                        }
                        if ((value & 2) != 0) {
                            result.writeLong((Long) result2.get(2));
                        }
                    });

    public static final Parsers.Parser BRIGADIER_STRING =
            define(
                    "brigadier:string",
                    result -> Collections.singletonList(result.readVarInt()),
                    (result, result2) -> result.writeVarInt((Integer) result2.get(0)));

    public static final Parsers.Parser ENTITY =
            define(
                    "entity",
                    result -> Collections.singletonList(result.readByte()),
                    (result, result2) -> result.writeByte(((Byte) result2.get(0)).intValue()));

    public static final Parsers.Parser GAME_PROFILE = define("game_profile", null, null);

    public static final Parsers.Parser BLOCK_POS = define("block_pos", null, null);

    public static final Parsers.Parser COLUMN_POS = define("column_pos", null, null);

    public static final Parsers.Parser VEC3 = define("vec3", null, null);

    public static final Parsers.Parser VEC2 = define("vec2", null, null);

    public static final Parsers.Parser BLOCK_STATE = define("block_state", null, null);

    public static final Parsers.Parser BLOCK_PREDICATE = define("block_predicate", null, null);

    public static final Parsers.Parser ITEM_STACK = define("item_stack", null, null);

    public static final Parsers.Parser ITEM_PREDICATE = define("item_predicate", null, null);

    @Obsolete public static final Parsers.Parser COLOR = define("color", null, null);

    public static final Parsers.Parser COMPONENT = define("component", null, null);

    public static final Parsers.Parser STYLE = define("style", null, null);

    public static final Parsers.Parser MESSAGE = define("message", null, null);

    public static final Parsers.Parser NBT_COMPOUND_TAG = define("nbt_compound_tag", null, null);

    @Obsolete public static final Parsers.Parser NBT = define("nbt", null, null);

    public static final Parsers.Parser NBT_TAG = define("nbt_tag", null, null);

    public static final Parsers.Parser NBT_PATH = define("nbt_path", null, null);

    public static final Parsers.Parser OBJECTIVE = define("objective", null, null);

    public static final Parsers.Parser OBJECTIVE_CRITERIA =
            define("objective_criteria", null, null);

    public static final Parsers.Parser OPERATION = define("operation", null, null);

    public static final Parsers.Parser PARTICLE = define("particle", null, null);

    public static final Parsers.Parser ANGLE = define("angle", null, null);

    public static final Parsers.Parser ROTATION = define("rotation", null, null);

    public static final Parsers.Parser SCOREBOARD_SLOT = define("scoreboard_slot", null, null);

    public static final Parsers.Parser SCORE_HOLDER =
            define(
                    "score_holder",
                    result -> Collections.singletonList(result.readByte()),
                    (result, result2) -> result.writeByte(((Byte) result2.get(0)).intValue()));

    public static final Parsers.Parser SWIZZLE = define("swizzle", null, null);

    public static final Parsers.Parser TEAM = define("team", null, null);

    public static final Parsers.Parser ITEM_SLOT = define("item_slot", null, null);

    public static final Parsers.Parser ITEM_SLOTS = define("item_slots", null, null);

    public static final Parsers.Parser RESOURCE_LOCATION = define("resource_location", null, null);

    public static final Parsers.Parser MOB_EFFECT = define("mob_effect", null, null);

    public static final Parsers.Parser FUNCTION = define("function", null, null);

    public static final Parsers.Parser ENTITY_ANCHOR = define("entity_anchor", null, null);

    public static final Parsers.Parser INT_RANGE = define("int_range", null, null);

    public static final Parsers.Parser FLOAT_RANGE = define("float_range", null, null);

    public static final Parsers.Parser ITEM_ENCHANTMENT = define("item_enchantment", null, null);

    public static final Parsers.Parser ENTITY_SUMMON = define("entity_summon", null, null);

    public static final Parsers.Parser DIMENSION = define("dimension", null, null);

    public static final Parsers.Parser GAMEMODE = define("gamemode", null, null);

    public static final Parsers.Parser TIME =
            define(
                    "time",
                    result ->
                            Collections.singletonList(
                                    result.getServerVersion()
                                                    .isNewerThanOrEquals(ServerVersion.V_1_19_4)
                                            ? result.readInt()
                                            : 0),
                    (result, result2) -> {
                        if (result.getServerVersion().isNewerThanOrEquals(ServerVersion.V_1_19_4)) {
                            result.writeInt((Integer) result2.get(0));
                        }
                    });

    public static final Parsers.Parser RESOURCE_OR_TAG =
            define(
                    "resource_or_tag",
                    result -> Collections.singletonList(result.readIdentifier()),
                    (result, result2) -> result.writeIdentifier((ResourceLocation) result2.get(0)));

    public static final Parsers.Parser RESOURCE_OR_TAG_KEY =
            define(
                    "resource_or_tag_key",
                    result -> Collections.singletonList(result.readIdentifier()),
                    (result, result2) -> result.writeIdentifier((ResourceLocation) result2.get(0)));

    public static final Parsers.Parser RESOURCE =
            define(
                    "resource",
                    result -> Collections.singletonList(result.readIdentifier()),
                    (result, result2) -> result.writeIdentifier((ResourceLocation) result2.get(0)));

    public static final Parsers.Parser RESOURCE_KEY =
            define(
                    "resource_key",
                    result -> Collections.singletonList(result.readIdentifier()),
                    (result, result2) -> result.writeIdentifier((ResourceLocation) result2.get(0)));

    public static final Parsers.Parser TEMPLATE_MIRROR = define("template_mirror", null, null);

    public static final Parsers.Parser TEMPLATE_ROTATION = define("template_rotation", null, null);

    public static final Parsers.Parser HEIGHTMAP = define("heightmap", null, null);

    public static final Parsers.Parser LOOT_TABLE = define("loot_table", null, null);

    public static final Parsers.Parser LOOT_PREDICATE = define("loot_predicate", null, null);

    public static final Parsers.Parser LOOT_MODIFIER = define("loot_modifier", null, null);

    public static final Parsers.Parser UUID = define("uuid", null, null);

    public static final Parsers.Parser RESOURCE_SELECTOR =
            define(
                    "resource_selector",
                    result -> Collections.singletonList(result.readIdentifier()),
                    (result, result2) -> result.writeIdentifier((ResourceLocation) result2.get(0)));

    public static final Parsers.Parser HEX_COLOR = define("hex_color", null, null);

    public static final Parsers.Parser DIALOG = define("dialog", null, null);

    public static final Parsers.Parser TEAM_COLOR = define("team_color", null, null);

    public static final Parsers.Parser CONTEXT_FLOAT_PROVIDER =
            define("context_float_provider", null, null);

    public static final Parsers.Parser CONTEXT_INT_PROVIDER =
            define("context_int_provider", null, null);

    public static final Parsers.Parser SLOT_SOURCE = define("slot_source", null, null);

    public static final Parsers.Parser FEATURE = define("feature", null, null);

    public static final Parsers.Parser SWING_ANIMATION = define("swing_animation", null, null);

    private Parsers() {}

    @Internal
    public static Parsers.Parser define(String text) {
        return define(text, null, null);
    }

    @Internal
    public static Parsers.Parser define(
            String text, @Nullable Parsers.Reader reader, @Nullable Parsers.Writer writer) {
        return REGISTRY.define(text, result -> new Parsers.Parser(result, reader, writer));
    }

    @Nullable
    public static Parsers.Parser getByName(String text) {
        return REGISTRY.getByName(text);
    }

    @Nullable
    public static Parsers.Parser getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static List<Parsers.Parser> getParsers() {
        return new ArrayList<>(REGISTRY.getEntries());
    }

    public static VersionedRegistry<Parsers.Parser> getRegistry() {
        return REGISTRY;
    }

    static {
        REGISTRY.unloadMappings();
    }

    public static final class Parser extends AbstractMappedEntity {

        @Nullable private final Parsers.Reader reader;

        @Nullable private final Parsers.Writer writer;

        @Deprecated
        public Parser(
                String text,
                @Nullable Function<PacketWrapper<?>, List<Object>> function,
                @Nullable BiConsumer<PacketWrapper<?>, List<Object>> biConsumer) {
            this(
                    new TypesBuilderData(new ResourceLocation(text), new int[0]),
                    function == null ? null : function::apply,
                    biConsumer == null ? null : biConsumer::accept);
        }

        @Internal
        public Parser(
                @Nullable TypesBuilderData typesBuilderData,
                @Nullable Parsers.Reader reader2,
                @Nullable Parsers.Writer writer2) {
            super(typesBuilderData);
            this.reader = reader2;
            this.writer = writer2;
        }

        public Optional<List<Object>> readProperties(PacketWrapper<?> packetWrapper) {
            return this.reader != null
                    ? Optional.of(this.reader.apply(packetWrapper))
                    : Optional.empty();
        }

        public void writeProperties(PacketWrapper<?> packetWrapper, List<Object> values) {
            if (this.writer != null) {
                this.writer.accept(packetWrapper, values);
            }
        }
    }

    @FunctionalInterface
    public interface Reader extends Function<PacketWrapper<?>, List<Object>> {}

    @FunctionalInterface
    public interface Writer extends BiConsumer<PacketWrapper<?>, List<Object>> {}
}
