package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.Option;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionSchema;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;

import org.jetbrains.annotations.NotNull;

public final class JSONOptions {

    private static final OptionSchema.Mutable UNSAFE_SCHEMA = OptionSchema.globalSchema();

    public static final Option<Boolean> EMIT_RGB = Option.booleanOption(key("emit/rgb"), true);

    public static final Option<JSONOptions.HoverEventValueMode> EMIT_HOVER_EVENT_TYPE =
            UNSAFE_SCHEMA.enumOption(
                    key("emit/hover_value_mode"),
                    JSONOptions.HoverEventValueMode.class,
                    JSONOptions.HoverEventValueMode.SNAKE_CASE);

    public static final Option<JSONOptions.ClickEventValueMode> EMIT_CLICK_EVENT_TYPE =
            Option.enumOption(
                    key("emit/click_value_mode"),
                    JSONOptions.ClickEventValueMode.class,
                    JSONOptions.ClickEventValueMode.SNAKE_CASE);

    public static final Option<Boolean> EMIT_COMPACT_TEXT_COMPONENT =
            UNSAFE_SCHEMA.booleanOption(key("emit/compact_text_component"), true);

    public static final Option<Boolean> EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY =
            UNSAFE_SCHEMA.booleanOption(key("emit/hover_show_entity_id_as_int_array"), true);

    public static final Option<Boolean> EMIT_HOVER_SHOW_ENTITY_KEY_AS_TYPE_AND_UUID_AS_ID =
            UNSAFE_SCHEMA.booleanOption(
                    key("emit/hover_show_entity_key_as_type_and_uuid_as_id"), false);

    public static final Option<Boolean> VALIDATE_STRICT_EVENTS =
            UNSAFE_SCHEMA.booleanOption(key("validate/strict_events"), true);

    public static final Option<Boolean> EMIT_DEFAULT_ITEM_HOVER_QUANTITY =
            UNSAFE_SCHEMA.booleanOption(key("emit/default_item_hover_quantity"), true);

    public static final Option<JSONOptions.ShowItemHoverDataMode> SHOW_ITEM_HOVER_DATA_MODE =
            UNSAFE_SCHEMA.enumOption(
                    key("emit/show_item_hover_data"),
                    JSONOptions.ShowItemHoverDataMode.class,
                    JSONOptions.ShowItemHoverDataMode.EMIT_EITHER);

    public static final Option<JSONOptions.ShadowColorEmitMode> SHADOW_COLOR_MODE =
            UNSAFE_SCHEMA.enumOption(
                    key("emit/shadow_color"),
                    JSONOptions.ShadowColorEmitMode.class,
                    JSONOptions.ShadowColorEmitMode.EMIT_INTEGER);

    public static final Option<Boolean> EMIT_CHANGE_PAGE_CLICK_EVENT_PAGE_AS_STRING =
            UNSAFE_SCHEMA.booleanOption(key("emit/change_page_click_event_page_as_string"), false);

    public static final Option<Boolean> EMIT_CLICK_URL_HTTPS =
            UNSAFE_SCHEMA.booleanOption(key("emit/click_url_https"), false);

    private static final OptionSchema SCHEMA = OptionSchema.childSchema(UNSAFE_SCHEMA).frozenView();

    private static final OptionState.Versioned BY_DATA_VERSION =
            SCHEMA.versionedStateBuilder()
                    .version(
                            0,
                            result ->
                                    result.value(
                                                    EMIT_HOVER_EVENT_TYPE,
                                                    JSONOptions.HoverEventValueMode.VALUE_FIELD)
                                            .value(
                                                    EMIT_CLICK_EVENT_TYPE,
                                                    JSONOptions.ClickEventValueMode.CAMEL_CASE)
                                            .value(EMIT_RGB, false)
                                            .value(EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY, false)
                                            .value(
                                                    EMIT_HOVER_SHOW_ENTITY_KEY_AS_TYPE_AND_UUID_AS_ID,
                                                    true)
                                            .value(VALIDATE_STRICT_EVENTS, false)
                                            .value(EMIT_DEFAULT_ITEM_HOVER_QUANTITY, false)
                                            .value(
                                                    SHOW_ITEM_HOVER_DATA_MODE,
                                                    JSONOptions.ShowItemHoverDataMode
                                                            .EMIT_LEGACY_NBT)
                                            .value(
                                                    SHADOW_COLOR_MODE,
                                                    JSONOptions.ShadowColorEmitMode.NONE)
                                            .value(
                                                    EMIT_CHANGE_PAGE_CLICK_EVENT_PAGE_AS_STRING,
                                                    true))
                    .version(
                            2526,
                            result ->
                                    result.value(
                                                    EMIT_HOVER_EVENT_TYPE,
                                                    JSONOptions.HoverEventValueMode.CAMEL_CASE)
                                            .value(EMIT_RGB, true))
                    .version(
                            3679,
                            result ->
                                    result.value(EMIT_COMPACT_TEXT_COMPONENT, true)
                                            .value(EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY, true)
                                            .value(VALIDATE_STRICT_EVENTS, true))
                    .version(
                            3819,
                            result ->
                                    result.value(EMIT_DEFAULT_ITEM_HOVER_QUANTITY, true)
                                            .value(
                                                    SHOW_ITEM_HOVER_DATA_MODE,
                                                    JSONOptions.ShowItemHoverDataMode
                                                            .EMIT_DATA_COMPONENTS))
                    .version(
                            4174,
                            result ->
                                    result.value(
                                            SHADOW_COLOR_MODE,
                                            JSONOptions.ShadowColorEmitMode.EMIT_INTEGER))
                    .version(
                            4298,
                            result ->
                                    result.value(
                                                    EMIT_HOVER_EVENT_TYPE,
                                                    JSONOptions.HoverEventValueMode.SNAKE_CASE)
                                            .value(
                                                    EMIT_CLICK_EVENT_TYPE,
                                                    JSONOptions.ClickEventValueMode.SNAKE_CASE)
                                            .value(
                                                    EMIT_HOVER_SHOW_ENTITY_KEY_AS_TYPE_AND_UUID_AS_ID,
                                                    false)
                                            .value(EMIT_CLICK_URL_HTTPS, true))
                    .version(
                            4422,
                            result ->
                                    result.value(
                                            EMIT_CHANGE_PAGE_CLICK_EVENT_PAGE_AS_STRING, false))
                    .build();

    private static final OptionState MOST_COMPATIBLE =
            SCHEMA.stateBuilder()
                    .value(EMIT_HOVER_EVENT_TYPE, JSONOptions.HoverEventValueMode.ALL)
                    .value(EMIT_CLICK_EVENT_TYPE, JSONOptions.ClickEventValueMode.BOTH)
                    .value(EMIT_HOVER_SHOW_ENTITY_ID_AS_INT_ARRAY, false)
                    .value(EMIT_COMPACT_TEXT_COMPONENT, false)
                    .value(VALIDATE_STRICT_EVENTS, false)
                    .value(SHOW_ITEM_HOVER_DATA_MODE, JSONOptions.ShowItemHoverDataMode.EMIT_EITHER)
                    .value(SHADOW_COLOR_MODE, JSONOptions.ShadowColorEmitMode.EMIT_INTEGER)
                    .value(EMIT_CLICK_URL_HTTPS, true)
                    .build();

    private JSONOptions() {}

    private static String key(String text) {
        return "adventure:json/" + text;
    }

    @NotNull
    public static OptionSchema schema() {
        return SCHEMA;
    }

    public static OptionState.@NotNull Versioned byDataVersion() {
        return BY_DATA_VERSION;
    }

    @NotNull
    public static OptionState compatibility() {
        return MOST_COMPATIBLE;
    }

    public enum ClickEventValueMode {
        SNAKE_CASE,
        CAMEL_CASE,
        BOTH
    }

    public enum HoverEventValueMode {
        SNAKE_CASE,
        CAMEL_CASE,
        VALUE_FIELD,
        ALL;

        @Deprecated public static final JSONOptions.HoverEventValueMode MODERN_ONLY = CAMEL_CASE;

        @Deprecated public static final JSONOptions.HoverEventValueMode LEGACY_ONLY = VALUE_FIELD;

        @Deprecated public static final JSONOptions.HoverEventValueMode BOTH = ALL;
    }

    public enum ShadowColorEmitMode {
        NONE,
        EMIT_INTEGER,
        EMIT_ARRAY
    }

    public enum ShowItemHoverDataMode {
        EMIT_LEGACY_NBT,
        EMIT_DATA_COMPONENTS,
        EMIT_EITHER
    }
}
