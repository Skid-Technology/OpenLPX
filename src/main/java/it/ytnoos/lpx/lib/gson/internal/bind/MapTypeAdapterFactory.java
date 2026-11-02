package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.$Gson$Types;
import it.ytnoos.lpx.lib.gson.internal.ConstructorConstructor;
import it.ytnoos.lpx.lib.gson.internal.JsonReaderInternalAccess;
import it.ytnoos.lpx.lib.gson.internal.ObjectConstructor;
import it.ytnoos.lpx.lib.gson.internal.Streams;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {

    private final ConstructorConstructor constructorConstructor;

    final boolean complexMapKeySerialization;

    public MapTypeAdapterFactory(
            ConstructorConstructor constructorConstructor2, boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor2;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }
        Class<?> rawType2 = $Gson$Types.getRawType(type);
        Type[] mapKeyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawType2);
        TypeAdapter keyAdapter = this.getKeyAdapter(gson, mapKeyAndValueTypes[0]);
        TypeAdapter adapter = gson.getAdapter(TypeToken.get(mapKeyAndValueTypes[1]));
        ObjectConstructor objectConstructor = this.constructorConstructor.get(typeToken);
        return new MapTypeAdapterFactory.Adapter(
                gson,
                mapKeyAndValueTypes[0],
                keyAdapter,
                mapKeyAndValueTypes[1],
                adapter,
                objectConstructor);
    }

    private TypeAdapter<?> getKeyAdapter(Gson gson, Type type) {
        return type != boolean.class && type != Boolean.class
                ? gson.getAdapter(TypeToken.get(type))
                : TypeAdapters.BOOLEAN_AS_STRING;
    }

    private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {

        private final TypeAdapter<K> keyTypeAdapter;

        private final TypeAdapter<V> valueTypeAdapter;

        private final ObjectConstructor<? extends Map<K, V>> constructor;

        public Adapter(
                Gson gson,
                Type type,
                TypeAdapter<K> typeAdapter,
                Type type2,
                TypeAdapter<V> typeAdapter2,
                ObjectConstructor<? extends Map<K, V>> objectConstructor) {
            this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(gson, typeAdapter, type);
            this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(gson, typeAdapter2, type2);
            this.constructor = objectConstructor;
        }

        public Map<K, V> read(JsonReader jsonReader) throws IOException {
            JsonToken jsonToken;
            if ((jsonToken = jsonReader.peek()) == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map<K, V> entries = this.constructor.construct();
            if (jsonToken == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    K k = this.keyTypeAdapter.read(jsonReader);
                    V v = this.valueTypeAdapter.read(jsonReader);
                    if (entries.put(k, v) != null) {
                        throw new JsonSyntaxException("duplicate key: " + k);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(jsonReader);
                    K k2 = this.keyTypeAdapter.read(jsonReader);
                    V v2 = this.valueTypeAdapter.read(jsonReader);
                    if (entries.put(k2, v2) != null) {
                        throw new JsonSyntaxException("duplicate key: " + k2);
                    }
                }
                jsonReader.endObject();
            }
            return entries;
        }

        public void write(JsonWriter jsonWriter, Map<K, V> entries) throws IOException {
            if (entries == null) {
                jsonWriter.nullValue();
            } else if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
                jsonWriter.beginObject();
                for (Entry<K, V> entry : entries.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    this.valueTypeAdapter.write(jsonWriter, (V) entry.getValue());
                }
                jsonWriter.endObject();
            } else {
                boolean matches = false;
                ArrayList<JsonElement> values = new ArrayList<>(entries.size());
                ArrayList<V> values2 = new ArrayList<>(entries.size());
                for (Entry<K, V> entry2 : entries.entrySet()) {
                    JsonElement jsonElement = this.keyTypeAdapter.toJsonTree((K) entry2.getKey());
                    values.add(jsonElement);
                    values2.add(entry2.getValue());
                    matches |= jsonElement.isJsonArray() || jsonElement.isJsonObject();
                }
                if (matches) {
                    jsonWriter.beginArray();
                    for (int index = 0; index < values.size(); index++) {
                        jsonWriter.beginArray();
                        Streams.write((JsonElement) values.get(index), jsonWriter);
                        this.valueTypeAdapter.write(jsonWriter, (V) values2.get(index));
                        jsonWriter.endArray();
                    }
                    jsonWriter.endArray();
                } else {
                    jsonWriter.beginObject();
                    for (int index2 = 0; index2 < values.size(); index2++) {
                        JsonElement jsonElement2 = (JsonElement) values.get(index2);
                        jsonWriter.name(this.keyToString(jsonElement2));
                        this.valueTypeAdapter.write(jsonWriter, (V) values2.get(index2));
                    }
                    jsonWriter.endObject();
                }
            }
        }

        private String keyToString(JsonElement jsonElement) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive;
                if ((jsonPrimitive = jsonElement.getAsJsonPrimitive()).isNumber()) {
                    return String.valueOf(jsonPrimitive.getAsNumber());
                } else if (jsonPrimitive.isBoolean()) {
                    return Boolean.toString(jsonPrimitive.getAsBoolean());
                } else if (jsonPrimitive.isString()) {
                    return jsonPrimitive.getAsString();
                } else {
                    throw new AssertionError();
                }
            } else if (jsonElement.isJsonNull()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }
}
