package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.util.Codec;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map.Entry;

final class StyleSerializer extends TypeAdapter<Style> {

    private static final TextDecoration[] DECORATIONS =
            new TextDecoration[] {
                TextDecoration.BOLD,
                TextDecoration.ITALIC,
                TextDecoration.UNDERLINED,
                TextDecoration.STRIKETHROUGH,
                TextDecoration.OBFUSCATED
            };

    private final it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json
                    .LegacyHoverEventSerializer
            legacyHover;

    private final BackwardCompatUtil.ShowAchievementToComponent compatShowAchievement;

    private final boolean emitValueFieldHover;

    private final boolean emitCamelCaseHover;

    private final boolean emitSnakeCaseHover;

    private final boolean emitCamelCaseClick;

    private final boolean emitSnakeCaseClick;

    private final boolean strictEventValues;

    private final boolean emitShadowColor;

    private final boolean emitStringPage;

    private final boolean emitClickUrlHttps;

    private final Gson gson;

    static TypeAdapter<Style> create(
            @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer,
            @Nullable BackwardCompatUtil.ShowAchievementToComponent showAchievementToComponent,
            OptionState optionState,
            Gson gson) {
        JSONOptions.HoverEventValueMode hoverEventValueMode =
                optionState.value(JSONOptions.EMIT_HOVER_EVENT_TYPE);
        JSONOptions.ClickEventValueMode clickEventValueMode =
                optionState.value(JSONOptions.EMIT_CLICK_EVENT_TYPE);
        return new StyleSerializer(
                        legacyHoverEventSerializer,
                        showAchievementToComponent,
                        hoverEventValueMode == JSONOptions.HoverEventValueMode.VALUE_FIELD
                                || hoverEventValueMode == JSONOptions.HoverEventValueMode.ALL,
                        hoverEventValueMode == JSONOptions.HoverEventValueMode.CAMEL_CASE
                                || hoverEventValueMode == JSONOptions.HoverEventValueMode.ALL,
                        hoverEventValueMode == JSONOptions.HoverEventValueMode.SNAKE_CASE
                                || hoverEventValueMode == JSONOptions.HoverEventValueMode.ALL,
                        clickEventValueMode == JSONOptions.ClickEventValueMode.CAMEL_CASE
                                || clickEventValueMode == JSONOptions.ClickEventValueMode.BOTH,
                        clickEventValueMode == JSONOptions.ClickEventValueMode.SNAKE_CASE
                                || clickEventValueMode == JSONOptions.ClickEventValueMode.BOTH,
                        optionState.value(JSONOptions.VALIDATE_STRICT_EVENTS),
                        optionState.value(JSONOptions.SHADOW_COLOR_MODE)
                                != JSONOptions.ShadowColorEmitMode.NONE,
                        optionState.value(JSONOptions.EMIT_CHANGE_PAGE_CLICK_EVENT_PAGE_AS_STRING),
                        optionState.value(JSONOptions.EMIT_CLICK_URL_HTTPS),
                        gson)
                .nullSafe();
    }

    private StyleSerializer(
            @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer,
            @Nullable BackwardCompatUtil.ShowAchievementToComponent showAchievementToComponent,
            boolean emitValueFieldHover,
            boolean emitCamelCaseHover,
            boolean emitSnakeCaseHover,
            boolean emitCamelCaseClick,
            boolean emitSnakeCaseClick,
            boolean strictEventValues,
            boolean emitShadowColor,
            boolean emitStringPage,
            boolean emitClickUrlHttps,
            Gson gson2) {
        this.legacyHover = legacyHoverEventSerializer;
        this.compatShowAchievement = showAchievementToComponent;
        this.emitValueFieldHover = emitValueFieldHover;
        this.emitCamelCaseHover = emitCamelCaseHover;
        this.emitSnakeCaseHover = emitSnakeCaseHover;
        this.emitCamelCaseClick = emitCamelCaseClick;
        this.emitSnakeCaseClick = emitSnakeCaseClick;
        this.strictEventValues = strictEventValues;
        this.emitShadowColor = emitShadowColor;
        this.emitStringPage = emitStringPage;
        this.emitClickUrlHttps = emitClickUrlHttps;
        this.gson = gson2;
    }

    public Style read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Style.Builder builder = Style.style();
        while (jsonReader.hasNext()) {
            String text;
            if ((text = jsonReader.nextName()).equals("font")) {
                builder.font(this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE));
            } else if (text.equals("color")) {
                TextColorWrapper textColorWrapper;
                if ((textColorWrapper =
                                        this.gson.fromJson(
                                                jsonReader, SerializerFactory.COLOR_WRAPPER_TYPE))
                                .color
                        != null) {
                    builder.color(textColorWrapper.color);
                } else if (textColorWrapper.decoration != null) {
                    builder.decoration(textColorWrapper.decoration, TextDecoration.State.TRUE);
                }
            } else if (BackwardCompatUtil.IS_4_18_0_OR_NEWER && text.equals("shadow_color")) {
                builder.shadowColor(
                        this.gson.fromJson(jsonReader, SerializerFactory.SHADOW_COLOR_TYPE));
            } else if (TextDecoration.NAMES.keys().contains(text)) {
                builder.decoration(
                        TextDecoration.NAMES.value(text), GsonHacks.readBoolean(jsonReader));
            } else if (text.equals("insertion")) {
                builder.insertion(jsonReader.nextString());
            } else if (!text.equals("click_event") && !text.equals("clickEvent")) {
                if (!text.equals("hover_event") && !text.equals("hoverEvent")) {
                    jsonReader.skipValue();
                } else {
                    JsonObject jsonObject;
                    JsonPrimitive jsonPrimitive;
                    if ((jsonObject = this.gson.fromJson(jsonReader, JsonObject.class)) != null
                            && (jsonPrimitive = jsonObject.getAsJsonPrimitive("action")) != null) {
                        String text2 =
                                this.gson.fromJson(jsonPrimitive, SerializerFactory.STRING_TYPE);
                        boolean matches = false;
                        HoverEvent.Action action;
                        if (text2.equals("show_achievement")) {
                            try {
                                action = HoverEvent.Action.SHOW_ACHIEVEMENT;
                            } catch (NoSuchFieldError noSuchFieldError) {
                                action = HoverEvent.Action.SHOW_TEXT;
                            }
                            matches = true;
                        } else {
                            action =
                                    this.gson.fromJson(
                                            jsonPrimitive, SerializerFactory.HOVER_ACTION_TYPE);
                        }
                        if (action.readable()) {
                            Class classValue = action.type();
                            Object result;
                            if (jsonObject.has("contents")) {
                                JsonElement jsonElement;
                                if (GsonHacks.isNullOrEmpty(
                                        jsonElement = jsonObject.get("contents"))) {
                                    if (this.strictEventValues) {
                                        throw ComponentSerializerImpl.notSureHowToDeserialize(
                                                jsonElement);
                                    }
                                    result = null;
                                } else if (SerializerFactory.COMPONENT_TYPE.isAssignableFrom(
                                        classValue)) {
                                    result =
                                            this.gson.fromJson(
                                                    jsonElement, SerializerFactory.COMPONENT_TYPE);
                                } else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(
                                        classValue)) {
                                    result =
                                            this.gson.fromJson(
                                                    jsonElement, SerializerFactory.SHOW_ITEM_TYPE);
                                } else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(
                                        classValue)) {
                                    result =
                                            this.gson.fromJson(
                                                    jsonElement,
                                                    SerializerFactory.SHOW_ENTITY_TYPE);
                                } else {
                                    result = null;
                                }
                            } else if (jsonObject.has("value")) {
                                JsonElement jsonElement2;
                                if (GsonHacks.isNullOrEmpty(
                                        jsonElement2 = jsonObject.get("value"))) {
                                    if (this.strictEventValues) {
                                        throw ComponentSerializerImpl.notSureHowToDeserialize(
                                                jsonElement2);
                                    }
                                    result = null;
                                } else if (Component.class.isAssignableFrom(classValue)) {
                                    if (matches && this.compatShowAchievement != null) {
                                        String text3 =
                                                this.gson.fromJson(
                                                        jsonElement2,
                                                        SerializerFactory.STRING_TYPE);
                                        result = this.compatShowAchievement.convert(text3);
                                    } else {
                                        Component component =
                                                this.gson.fromJson(
                                                        jsonElement2,
                                                        SerializerFactory.COMPONENT_TYPE);
                                        result = this.legacyHoverEventContents(action, component);
                                    }
                                } else if (String.class.isAssignableFrom(classValue)) {
                                    result =
                                            this.gson.fromJson(
                                                    jsonElement2, SerializerFactory.STRING_TYPE);
                                } else {
                                    result = null;
                                }
                            } else if (SerializerFactory.SHOW_ITEM_TYPE.isAssignableFrom(
                                    classValue)) {
                                result =
                                        this.gson.fromJson(
                                                jsonObject, SerializerFactory.SHOW_ITEM_TYPE);
                            } else if (SerializerFactory.SHOW_ENTITY_TYPE.isAssignableFrom(
                                    classValue)) {
                                result =
                                        this.gson.fromJson(
                                                jsonObject, SerializerFactory.SHOW_ENTITY_TYPE);
                            } else {
                                if (this.strictEventValues) {
                                    throw ComponentSerializerImpl.notSureHowToDeserialize(
                                            jsonObject);
                                }
                                result = null;
                            }
                            if (result != null) {
                                builder.hoverEvent(HoverEvent.hoverEvent(action, result));
                            }
                        }
                    }
                }
            } else {
                jsonReader.beginObject();
                ClickEvent.Action action2 = null;
                String text4 = null;
                Key key2 = null;
                Integer value2 = null;
                while (jsonReader.hasNext()) {
                    String text5;
                    if ((text5 = jsonReader.nextName()).equals("action")) {
                        action2 =
                                this.gson.fromJson(jsonReader, SerializerFactory.CLICK_ACTION_TYPE);
                    } else if (text5.equals("page")) {
                        if (jsonReader.peek() == JsonToken.NUMBER) {
                            value2 = jsonReader.nextInt();
                        } else if (jsonReader.peek() == JsonToken.STRING) {
                            value2 = Integer.parseInt(jsonReader.nextString());
                        } else {
                            if (jsonReader.peek() == JsonToken.NULL) {
                                throw ComponentSerializerImpl.notSureHowToDeserialize(text5);
                            }
                            jsonReader.skipValue();
                        }
                    } else if (text5.equals("value")
                            || text5.equals("url")
                            || text5.equals("path")
                            || text5.equals("command")
                            || text5.equals("payload")) {
                        if (jsonReader.peek() == JsonToken.NULL) {
                            if (this.strictEventValues) {
                                throw ComponentSerializerImpl.notSureHowToDeserialize(text5);
                            }
                            jsonReader.nextNull();
                        } else {
                            text4 = jsonReader.nextString();
                        }
                    } else if (text5.equals("id")) {
                        key2 = Key.key(jsonReader.nextString());
                    } else {
                        jsonReader.skipValue();
                    }
                }
                if (action2 != null && action2.readable()) {
                    switch (action2.toString()) {
                        case "open_url":
                            if (text4 != null) {
                                builder.clickEvent(ClickEvent.openUrl(text4));
                            }
                            break;
                        case "run_command":
                            if (text4 != null) {
                                builder.clickEvent(ClickEvent.runCommand(text4));
                            }
                            break;
                        case "suggest_command":
                            if (text4 != null) {
                                builder.clickEvent(ClickEvent.suggestCommand(text4));
                            }
                            break;
                        case "copy_to_clipboard":
                            if (text4 != null) {
                                builder.clickEvent(ClickEvent.copyToClipboard(text4));
                            }
                            break;
                        case "change_page":
                            if (value2 != null) {
                                builder.clickEvent(ClickEvent.changePage(value2));
                            } else if (text4 != null) {
                                builder.clickEvent(ClickEvent.changePage(Integer.parseInt(text4)));
                            }
                            break;
                        case "custom":
                            if (key2 != null) {
                                if (BackwardCompatUtil.IS_4_23_0_OR_NEWER) {
                                    builder.clickEvent(
                                            ClickEvent.custom(
                                                    key2, BinaryTagHolder.binaryTagHolder(text4)));
                                } else {
                                    builder.clickEvent(ClickEvent.custom(key2, text4));
                                }
                            }
                    }
                }
                jsonReader.endObject();
            }
        }
        jsonReader.endObject();
        return builder.build();
    }

    private Object legacyHoverEventContents(HoverEvent.Action<?> action, Component component) {
        if (action == HoverEvent.Action.SHOW_TEXT) {
            return component;
        }
        if (this.legacyHover != null) {
            try {
                if (action == HoverEvent.Action.SHOW_ENTITY) {
                    return this.legacyHover.deserializeShowEntity(component, this.decoder());
                }
                if (action == HoverEvent.Action.SHOW_ITEM) {
                    return this.legacyHover.deserializeShowItem(component);
                }
            } catch (IOException oException) {
                throw new JsonParseException(oException);
            }
        }
        throw new UnsupportedOperationException();
    }

    private Codec.Decoder<Component, String, JsonParseException> decoder() {
        return result -> this.gson.fromJson(result, SerializerFactory.COMPONENT_TYPE);
    }

    private Codec.Encoder<Component, String, JsonParseException> encoder() {
        return result -> this.gson.toJson(result, SerializerFactory.COMPONENT_TYPE);
    }

    public void write(JsonWriter jsonWriter, Style style) throws IOException {
        jsonWriter.beginObject();
        int value2 = 0;
        for (int value3 = DECORATIONS.length; value2 < value3; value2++) {
            TextDecoration textDecoration = DECORATIONS[value2];
            TextDecoration.State state;
            if ((state = style.decoration(textDecoration)) != TextDecoration.State.NOT_SET) {
                String text = TextDecoration.NAMES.key(textDecoration);
                assert text != null;
                jsonWriter.name(text);
                jsonWriter.value(state == TextDecoration.State.TRUE);
            }
        }
        TextColor textColor;
        if ((textColor = style.color()) != null) {
            jsonWriter.name("color");
            this.gson.toJson(textColor, SerializerFactory.COLOR_TYPE, jsonWriter);
        }
        ShadowColor shadowColor2;
        if (BackwardCompatUtil.IS_4_18_0_OR_NEWER
                && (shadowColor2 = style.shadowColor()) != null
                && this.emitShadowColor) {
            jsonWriter.name("shadow_color");
            this.gson.toJson(shadowColor2, SerializerFactory.SHADOW_COLOR_TYPE, jsonWriter);
        }
        String text2;
        if ((text2 = style.insertion()) != null) {
            jsonWriter.name("insertion");
            jsonWriter.value(text2);
        }
        ClickEvent clickEvent2;
        if ((clickEvent2 = style.clickEvent()) != null) {
            ClickEvent.Action action2 = clickEvent2.action();
            if (this.emitSnakeCaseClick) {
                jsonWriter.name("click_event");
                jsonWriter.beginObject();
                jsonWriter.name("action");
                this.gson.toJson(action2, SerializerFactory.CLICK_ACTION_TYPE, jsonWriter);
                if (action2.readable()) {
                    Object result;
                    if ((result =
                                            BackwardCompatUtil.IS_4_22_0_OR_NEWER
                                                    ? clickEvent2.payload()
                                                    : clickEvent2.value())
                                    instanceof String
                            || result instanceof ClickEvent.Payload.Text) {
                        switch (action2.toString()) {
                            case "open_url":
                                jsonWriter.name("url");
                                break;
                            case "run_command":
                            case "suggest_command":
                                jsonWriter.name("command");
                                break;
                            default:
                                jsonWriter.name("value");
                        }
                        String text3 =
                                result instanceof String
                                        ? (String) result
                                        : ((ClickEvent.Payload.Text) result).value();
                        if ("open_url".equals(action2.toString())
                                && this.emitClickUrlHttps
                                && !isValidUrlScheme(text3)) {
                            text3 = "https://" + text3;
                        }
                        jsonWriter.value(text3);
                    } else if (result instanceof ClickEvent.Payload.Custom) {
                        ClickEvent.Payload.Custom custom = (ClickEvent.Payload.Custom) result;
                        jsonWriter.name("id");
                        this.gson.toJson(custom.key(), SerializerFactory.KEY_TYPE, jsonWriter);
                        if (BackwardCompatUtil.IS_4_23_0_OR_NEWER) {
                            BinaryTagHolder binaryTagHolder;
                            if ((binaryTagHolder = custom.nbt()) != null) {
                                jsonWriter.name("payload");
                                jsonWriter.value(binaryTagHolder.string());
                            }
                        } else {
                            jsonWriter.name("payload");
                            jsonWriter.value(custom.data());
                        }
                    } else if (result instanceof ClickEvent.Payload.Int) {
                        ClickEvent.Payload.Int intValue = (ClickEvent.Payload.Int) result;
                        jsonWriter.name("page");
                        if (this.emitStringPage) {
                            jsonWriter.value(String.valueOf(intValue.integer()));
                        } else {
                            jsonWriter.value(intValue.integer());
                        }
                    }
                }
                jsonWriter.endObject();
            }
            if (this.emitCamelCaseClick
                    && (!BackwardCompatUtil.IS_4_22_0_OR_NEWER
                            || action2.supports(ClickEvent.runCommand("text").payload()))) {
                jsonWriter.name("clickEvent");
                jsonWriter.beginObject();
                jsonWriter.name("action");
                this.gson.toJson(action2, SerializerFactory.CLICK_ACTION_TYPE, jsonWriter);
                jsonWriter.name("value");
                String text4;
                if (BackwardCompatUtil.IS_4_22_0_OR_NEWER) {
                    text4 = ((ClickEvent.Payload.Text) clickEvent2.payload()).value();
                } else {
                    text4 = clickEvent2.value();
                }
                if ("open_url".equals(action2.toString())
                        && this.emitClickUrlHttps
                        && !isValidUrlScheme(text4)) {
                    text4 = "https://" + text4;
                }
                jsonWriter.value(text4);
                jsonWriter.endObject();
            }
        }
        HoverEvent hoverEvent2;
        if ((hoverEvent2 = style.hoverEvent()) != null
                && ((this.emitSnakeCaseHover || this.emitCamelCaseHover)
                                && !hoverEvent2.action().toString().equals("show_achievement")
                        || this.emitValueFieldHover)) {
            HoverEvent.Action action3 = hoverEvent2.action();
            if (this.emitSnakeCaseHover && !action3.toString().equals("show_achievement")) {
                jsonWriter.name("hover_event");
                jsonWriter.beginObject();
                jsonWriter.name("action");
                this.gson.toJson(action3, SerializerFactory.HOVER_ACTION_TYPE, jsonWriter);
                if (action3 == HoverEvent.Action.SHOW_ITEM) {
                    for (Entry<String, JsonElement> entry :
                            this.gson
                                    .toJsonTree(
                                            hoverEvent2.value(), SerializerFactory.SHOW_ITEM_TYPE)
                                    .getAsJsonObject()
                                    .entrySet()) {
                        jsonWriter.name((String) entry.getKey());
                        this.gson.toJson((JsonElement) entry.getValue(), jsonWriter);
                    }
                } else if (action3 == HoverEvent.Action.SHOW_ENTITY) {
                    for (Entry<String, JsonElement> entry2 :
                            this.gson
                                    .toJsonTree(
                                            hoverEvent2.value(), SerializerFactory.SHOW_ENTITY_TYPE)
                                    .getAsJsonObject()
                                    .entrySet()) {
                        jsonWriter.name((String) entry2.getKey());
                        this.gson.toJson((JsonElement) entry2.getValue(), jsonWriter);
                    }
                } else {
                    if (action3 != HoverEvent.Action.SHOW_TEXT) {
                        throw new JsonParseException(
                                "Don't know how to serialize " + hoverEvent2.value());
                    }
                    jsonWriter.name("value");
                    this.gson.toJson(
                            hoverEvent2.value(), SerializerFactory.COMPONENT_TYPE, jsonWriter);
                }
                jsonWriter.endObject();
            }
            if (this.emitCamelCaseHover || this.emitValueFieldHover) {
                jsonWriter.name("hoverEvent");
                jsonWriter.beginObject();
                jsonWriter.name("action");
                this.gson.toJson(action3, SerializerFactory.HOVER_ACTION_TYPE, jsonWriter);
                if (this.emitCamelCaseHover && !action3.toString().equals("show_achievement")) {
                    jsonWriter.name("contents");
                    if (action3 == HoverEvent.Action.SHOW_ITEM) {
                        this.gson.toJson(
                                hoverEvent2.value(), SerializerFactory.SHOW_ITEM_TYPE, jsonWriter);
                    } else if (action3 == HoverEvent.Action.SHOW_ENTITY) {
                        this.gson.toJson(
                                hoverEvent2.value(),
                                SerializerFactory.SHOW_ENTITY_TYPE,
                                jsonWriter);
                    } else {
                        if (action3 != HoverEvent.Action.SHOW_TEXT) {
                            throw new JsonParseException(
                                    "Don't know how to serialize " + hoverEvent2.value());
                        }
                        this.gson.toJson(
                                hoverEvent2.value(), SerializerFactory.COMPONENT_TYPE, jsonWriter);
                    }
                }
                if (this.emitValueFieldHover) {
                    jsonWriter.name("value");
                    this.serializeLegacyHoverEvent(hoverEvent2, jsonWriter);
                }
                jsonWriter.endObject();
            }
        }
        Key key2;
        if ((key2 = style.font()) != null) {
            jsonWriter.name("font");
            this.gson.toJson(key2, SerializerFactory.KEY_TYPE, jsonWriter);
        }
        jsonWriter.endObject();
    }

    private void serializeLegacyHoverEvent(HoverEvent<?> hoverEvent, JsonWriter jsonWriter)
            throws IOException {
        if (hoverEvent.action() == HoverEvent.Action.SHOW_TEXT) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.COMPONENT_TYPE, jsonWriter);
        } else if (hoverEvent.action().toString().equals("show_achievement")) {
            this.gson.toJson(hoverEvent.value(), SerializerFactory.STRING_TYPE, jsonWriter);
        } else if (this.legacyHover != null) {
            Component component = null;
            try {
                if (hoverEvent.action() == HoverEvent.Action.SHOW_ENTITY) {
                    component =
                            this.legacyHover.serializeShowEntity(
                                    (HoverEvent.ShowEntity) hoverEvent.value(), this.encoder());
                } else if (hoverEvent.action() == HoverEvent.Action.SHOW_ITEM) {
                    component =
                            this.legacyHover.serializeShowItem(
                                    (HoverEvent.ShowItem) hoverEvent.value());
                }
            } catch (IOException oException) {
                throw new JsonSyntaxException(oException);
            }
            if (component != null) {
                this.gson.toJson(component, SerializerFactory.COMPONENT_TYPE, jsonWriter);
            } else {
                jsonWriter.nullValue();
            }
        } else {
            jsonWriter.nullValue();
        }
    }

    private static boolean isValidUrlScheme(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }

    static {
        EnumSet values = EnumSet.allOf(TextDecoration.class);
        TextDecoration[] textDecorations = DECORATIONS;
        int value = DECORATIONS.length;
        for (int index = 0; index < value; index++) {
            TextDecoration textDecoration = textDecorations[index];
            values.remove(textDecoration);
        }
        if (!values.isEmpty()) {
            throw new IllegalStateException(
                    "Gson serializer is missing some text decorations: " + values);
        }
    }
}
