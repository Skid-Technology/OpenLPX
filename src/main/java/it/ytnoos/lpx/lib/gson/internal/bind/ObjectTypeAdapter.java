package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.LinkedTreeMap;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

public final class ObjectTypeAdapter extends TypeAdapter<Object> {

    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() {

                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                    return (((TypeAdapter<T>)
                            (typeToken.getRawType() == Object.class
                                    ? new ObjectTypeAdapter(gson)
                                    : null)));
                }
            };

    private final Gson gson;

    ObjectTypeAdapter(Gson gson2) {
        this.gson = gson2;
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken = jsonReader.peek();
        switch (jsonToken) {
            case BEGIN_ARRAY:
                ArrayList values = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    values.add(this.read(jsonReader));
                }
                jsonReader.endArray();
                return values;
            case BEGIN_OBJECT:
                LinkedTreeMap linkedTreeMap = new LinkedTreeMap();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    linkedTreeMap.put(jsonReader.nextName(), this.read(jsonReader));
                }
                jsonReader.endObject();
                return linkedTreeMap;
            case STRING:
                return jsonReader.nextString();
            case NUMBER:
                return jsonReader.nextDouble();
            case BOOLEAN:
                return jsonReader.nextBoolean();
            case NULL:
                jsonReader.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void write(JsonWriter jsonWriter, Object result) throws IOException {
        if (result == null) {
            jsonWriter.nullValue();
        } else {
            TypeAdapter typeAdapter;
            if ((typeAdapter = this.gson.getAdapter(result.getClass()))
                    instanceof ObjectTypeAdapter) {
                jsonWriter.beginObject();
                jsonWriter.endObject();
            } else {
                typeAdapter.write(jsonWriter, result);
            }
        }
    }
}
