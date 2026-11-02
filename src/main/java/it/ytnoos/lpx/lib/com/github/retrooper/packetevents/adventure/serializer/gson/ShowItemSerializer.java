package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

final class ShowItemSerializer extends TypeAdapter<HoverEvent.ShowItem> {

    private final Gson gson;

    private final boolean emitDefaultQuantity;

    private final JSONOptions.ShowItemHoverDataMode itemDataMode;

    static TypeAdapter<HoverEvent.ShowItem> create(Gson gson, OptionState optionState) {
        return new ShowItemSerializer(
                        gson,
                        optionState.value(JSONOptions.EMIT_DEFAULT_ITEM_HOVER_QUANTITY),
                        optionState.value(JSONOptions.SHOW_ITEM_HOVER_DATA_MODE))
                .nullSafe();
    }

    private ShowItemSerializer(
            Gson gson2,
            boolean emitDefaultQuantity,
            JSONOptions.ShowItemHoverDataMode showItemHoverDataMode) {
        this.gson = gson2;
        this.emitDefaultQuantity = emitDefaultQuantity;
        this.itemDataMode = showItemHoverDataMode;
    }

    public HoverEvent.ShowItem read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        Key key2 = null;
        int value = 1;
        BinaryTagHolder binaryTagHolder2 = null;
        HashMap entries = null;
        while (jsonReader.hasNext()) {
            String text;
            if ((text = jsonReader.nextName()).equals("id")) {
                key2 = this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
            } else if (text.equals("count")) {
                value = jsonReader.nextInt();
            } else if (text.equals("tag")) {
                JsonToken jsonToken;
                if ((jsonToken = jsonReader.peek()) != JsonToken.STRING
                        && jsonToken != JsonToken.NUMBER) {
                    if (jsonToken == JsonToken.BOOLEAN) {
                        if (BackwardCompatUtil.IS_4_10_0_OR_NEWER) {
                            binaryTagHolder2 =
                                    BinaryTagHolder.binaryTagHolder(
                                            String.valueOf(jsonReader.nextBoolean()));
                        } else {
                            binaryTagHolder2 =
                                    BinaryTagHolder.of(String.valueOf(jsonReader.nextBoolean()));
                        }
                    } else {
                        if (jsonToken != JsonToken.NULL) {
                            throw new JsonParseException("Expected tag to be a string");
                        }
                        jsonReader.nextNull();
                    }
                } else if (BackwardCompatUtil.IS_4_10_0_OR_NEWER) {
                    binaryTagHolder2 = BinaryTagHolder.binaryTagHolder(jsonReader.nextString());
                } else {
                    binaryTagHolder2 = BinaryTagHolder.of(jsonReader.nextString());
                }
            } else if (!text.equals("components")) {
                jsonReader.skipValue();
            } else {
                jsonReader.beginObject();
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    String text2;
                    Key key3;
                    boolean matches;
                    if ((text2 = jsonReader.nextName()).startsWith("!")) {
                        key3 = Key.key(text2.substring(1));
                        matches = true;
                    } else {
                        key3 = Key.key(text2);
                        matches = false;
                    }
                    JsonElement jsonElement = this.gson.fromJson(jsonReader, JsonElement.class);
                    if (entries == null) {
                        entries = new HashMap();
                    }
                    entries.put(
                            key3,
                            matches
                                    ? DataComponentValue.removed()
                                    : GsonDataComponentValue.gsonDataComponentValue(jsonElement));
                }
                jsonReader.endObject();
            }
        }
        if (key2 == null) {
            throw new JsonParseException("Not sure how to deserialize show_item hover event");
        }
        jsonReader.endObject();
        return entries != null
                ? HoverEvent.ShowItem.showItem(key2, value, entries)
                : BackwardCompatUtil.createShowItem(key2, value, binaryTagHolder2);
    }

    public void write(JsonWriter jsonWriter, HoverEvent.ShowItem showItem) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        this.gson.toJson(showItem.item(), SerializerFactory.KEY_TYPE, jsonWriter);
        int value2;
        if ((value2 = showItem.count()) != 1 || this.emitDefaultQuantity) {
            jsonWriter.name("count");
            jsonWriter.value(value2);
        }
        if (!(!BackwardCompatUtil.IS_4_17_0_OR_NEWER
                                ? Collections.emptyMap()
                                : showItem.dataComponents())
                        .isEmpty()
                && this.itemDataMode != JSONOptions.ShowItemHoverDataMode.EMIT_LEGACY_NBT) {
            jsonWriter.name("components");
            jsonWriter.beginObject();
            Iterator<
                            Entry<
                                    Key,
                                    it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure
                                            .serializer.gson.GsonDataComponentValue>>
                    iterator2 =
                            showItem.dataComponentsAs(GsonDataComponentValue.class)
                                    .entrySet()
                                    .iterator();
            while (iterator2.hasNext()) {
                Entry entry;
                JsonElement jsonElement;
                if ((jsonElement =
                                ((GsonDataComponentValue)
                                                (entry = (Entry) iterator2.next()).getValue())
                                        .element())
                        instanceof JsonNull) {
                    jsonWriter.name("!" + ((Key) entry.getKey()).asString());
                    jsonWriter.beginObject().endObject();
                } else {
                    jsonWriter.name(((Key) entry.getKey()).asString());
                    this.gson.toJson(jsonElement, jsonWriter);
                }
            }
            jsonWriter.endObject();
        } else if (this.itemDataMode != JSONOptions.ShowItemHoverDataMode.EMIT_DATA_COMPONENTS) {
            maybeWriteLegacy(jsonWriter, showItem);
        }
        jsonWriter.endObject();
    }

    private static void maybeWriteLegacy(JsonWriter jsonWriter, HoverEvent.ShowItem showItem)
            throws IOException {
        BinaryTagHolder binaryTagHolder;
        if ((binaryTagHolder = showItem.nbt()) != null) {
            jsonWriter.name("tag");
            jsonWriter.value(binaryTagHolder.string());
        }
    }
}
