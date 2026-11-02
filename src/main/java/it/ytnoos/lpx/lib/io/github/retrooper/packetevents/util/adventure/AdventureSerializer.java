package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonComponentSerializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.legacyimpl.NBTLegacyHoverEventSerializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.legacy.LegacyComponentSerializer;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.stats.Statistics;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtEncoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.flattener.ComponentFlattener;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.util.EnumMap;
import java.util.Map;

@NullMarked
public final class AdventureSerializer implements NbtEncoder<Component>, NbtDecoder<Component> {

    private static final Map<ClientVersion, AdventureSerializer> SERIALIZERS =
            new EnumMap<>(ClientVersion.class);

    private final ClientVersion version;

    @Nullable private GsonComponentSerializer gson;

    @Nullable private LegacyComponentSerializer legacy;

    @Nullable private AdventureNBTSerializer nbt;

    private AdventureSerializer(ClientVersion clientVersion) {
        this.version = clientVersion;
    }

    public static String stringify(ComponentLike componentLike) {
        StringBuilder stringBuilder = new StringBuilder();
        ComponentFlattener componentFlattener = ComponentFlattener.basic();
        Component component = componentLike.asComponent();
        componentFlattener.flatten(component, stringBuilder::append);
        return stringBuilder.toString();
    }

    public static AdventureSerializer serializer(PacketWrapper<?> packetWrapper) {
        return serializer(packetWrapper.getServerVersion().toClientVersion());
    }

    public static AdventureSerializer serializer(ClientVersion clientVersion) {
        AdventureSerializer adventureSerializer;
        if ((adventureSerializer = SERIALIZERS.get(clientVersion)) != null) {
            return adventureSerializer;
        }
        synchronized (SERIALIZERS) {
            return SERIALIZERS.computeIfAbsent(clientVersion, AdventureSerializer::new);
        }
    }

    public static AdventureSerializer serializer() {
        return serializer(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion());
    }

    @Deprecated
    public static GsonComponentSerializer getGsonSerializer() {
        return serializer().gson();
    }

    @Deprecated
    public static LegacyComponentSerializer getLegacyGsonSerializer() {
        return serializer().legacy();
    }

    @Deprecated
    public static LegacyComponentSerializer getLegacySerializer() {
        return serializer().legacy();
    }

    @Deprecated
    public static AdventureNBTSerializer getNBTSerializer() {
        return serializer().nbt();
    }

    @Deprecated
    public static String asVanilla(Component component) {
        return serializer().asLegacy(component);
    }

    @Deprecated
    public static Component fromLegacyFormat(String text) {
        return serializer().fromLegacy(text);
    }

    @Deprecated
    public static String toLegacyFormat(Component component) {
        return serializer().asLegacy(component);
    }

    @Deprecated
    public static Component parseComponent(String text) {
        return serializer().fromJson(text);
    }

    @Deprecated
    public static Component parseJsonTree(JsonElement jsonElement) {
        return serializer().fromJsonTree(jsonElement);
    }

    @Deprecated
    public static String toJson(Component component) {
        return serializer().asJson(component);
    }

    @Deprecated
    public static JsonElement toJsonTree(Component component) {
        return serializer().asJsonTree(component);
    }

    @Deprecated
    public static Component fromNbt(NBT nBT) {
        return serializer().fromNbtTag(nBT);
    }

    @Deprecated
    public static NBT toNbt(Component component) {
        return serializer().asNbtTag(component);
    }

    public Component fromLegacy(String text) {
        return this.legacy().deserializeOrNull(text);
    }

    public String asLegacy(Component component) {
        return this.legacy().serializeOrNull(component);
    }

    public Component fromJson(String text) {
        return this.gson().deserializeOrNull(text);
    }

    public String asJson(Component component) {
        return this.gson().serializeOrNull(component);
    }

    @Contract("!null -> !null")
    @Nullable
    public Component fromJsonTree(@Nullable JsonElement jsonElement) {
        return jsonElement != null ? this.gson().deserializeFromTree(jsonElement) : null;
    }

    @Contract("!null -> !null")
    @Nullable
    public JsonElement asJsonTree(@Nullable Component component) {
        return component != null ? this.gson().serializeToTree(component) : null;
    }

    @Deprecated
    public Component fromNbtTag(NBT nBT) {
        return this.fromNbtTag(nBT, PacketWrapper.createDummyWrapper(this.version));
    }

    public Component fromNbtTag(NBT nBT, PacketWrapper<?> packetWrapper) {
        return this.nbt().deserializeOrNull(nBT, packetWrapper);
    }

    @Deprecated
    public NBT asNbtTag(Component component) {
        return this.asNbtTag(component, PacketWrapper.createDummyWrapper(this.version));
    }

    public NBT asNbtTag(Component component, PacketWrapper<?> packetWrapper) {
        return this.nbt().serializeOrNull(component, packetWrapper);
    }

    public GsonComponentSerializer gson() {
        if (this.gson == null) {
            GsonComponentSerializer.Builder builder2 = GsonComponentSerializer.builder();
            this.gson =
                    builder2.editOptions(
                                    result -> {
                                        result.values(JSONOptions.byDataVersion().at(0));
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_16)) {
                                            result.value(
                                                    JSONOptions.EMIT_HOVER_EVENT_TYPE,
                                                    JSONOptions.HoverEventValueMode.CAMEL_CASE);
                                            if (!PacketEvents.getAPI()
                                                    .getSettings()
                                                    .shouldDownsampleColors()) {
                                                result.value(JSONOptions.EMIT_RGB, true);
                                            }
                                        }
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_20_3)) {
                                            result.value(
                                                    JSONOptions.EMIT_COMPACT_TEXT_COMPONENT, true);
                                            result.value(
                                                    JSONOptions
                                                            .EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY,
                                                    true);
                                            result.value(JSONOptions.VALIDATE_STRICT_EVENTS, true);
                                        }
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_20_5)) {
                                            result.value(
                                                    JSONOptions.EMIT_DEFAULT_ITEM_HOVER_QUANTITY,
                                                    true);
                                            result.value(
                                                    JSONOptions.SHOW_ITEM_HOVER_DATA_MODE,
                                                    JSONOptions.ShowItemHoverDataMode
                                                            .EMIT_DATA_COMPONENTS);
                                        }
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_21_4)) {
                                            result.value(
                                                    JSONOptions.SHADOW_COLOR_MODE,
                                                    JSONOptions.ShadowColorEmitMode.EMIT_INTEGER);
                                        }
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_21_5)) {
                                            result.value(
                                                    JSONOptions.EMIT_HOVER_EVENT_TYPE,
                                                    JSONOptions.HoverEventValueMode.SNAKE_CASE);
                                            result.value(
                                                    JSONOptions.EMIT_CLICK_EVENT_TYPE,
                                                    JSONOptions.ClickEventValueMode.SNAKE_CASE);
                                            result.value(
                                                    JSONOptions
                                                            .EMIT_HOVER_SHOW_ENTITY_KEY_AS_TYPE_AND_UUID_AS_ID,
                                                    false);
                                            result.value(JSONOptions.EMIT_CLICK_URL_HTTPS, true);
                                        }
                                        if (this.version.isNewerThanOrEquals(
                                                ClientVersion.V_1_21_6)) {
                                            result.value(
                                                    JSONOptions
                                                            .EMIT_CHANGE_PAGE_CLICK_EVENT_PAGE_AS_STRING,
                                                    false);
                                        }
                                    })
                            .legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get())
                            .showAchievementToComponent(
                                    result2 -> Statistics.getById(result2).display())
                            .build();
        }
        return this.gson;
    }

    public LegacyComponentSerializer legacy() {
        if (this.legacy == null) {
            LegacyComponentSerializer.Builder builder2 = LegacyComponentSerializer.builder();
            if (this.version.isNewerThanOrEquals(ClientVersion.V_1_16)) {
                builder2.hexColors();
            }
            this.legacy = builder2.build();
        }
        return this.legacy;
    }

    public AdventureNBTSerializer nbt() {
        if (this.nbt == null) {
            boolean matches =
                    this.version.isOlderThan(ClientVersion.V_1_16)
                            || PacketEvents.getAPI().getSettings().shouldDownsampleColors();
            this.nbt = new AdventureNBTSerializer(this.version, matches);
        }
        return this.nbt;
    }

    public Component decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        return this.nbt().deserialize(nBT, packetWrapper);
    }

    public NBT encode(PacketWrapper<?> packetWrapper, Component component) {
        return this.nbt().serialize(component, packetWrapper);
    }
}
