package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonDeserializationContext;
import it.ytnoos.lpx.lib.gson.JsonDeserializer;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.JsonSerializationContext;
import it.ytnoos.lpx.lib.gson.JsonSerializer;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.$Gson$Preconditions;
import it.ytnoos.lpx.lib.gson.internal.Streams;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T> extends TypeAdapter<T> {

    private final JsonSerializer<T> serializer;

    private final JsonDeserializer<T> deserializer;

    private final Gson gson;

    private final TypeToken<T> typeToken;

    private final TypeAdapterFactory skipPast;

    private final TreeTypeAdapter<T>.GsonContextImpl context =
            new TreeTypeAdapter.GsonContextImpl();

    private TypeAdapter<T> delegate;

    public TreeTypeAdapter(
            JsonSerializer<T> jsonSerializer,
            JsonDeserializer<T> jsonDeserializer,
            Gson gson2,
            TypeToken<T> typeToken2,
            TypeAdapterFactory typeAdapterFactory) {
        this.serializer = jsonSerializer;
        this.deserializer = jsonDeserializer;
        this.gson = gson2;
        this.typeToken = typeToken2;
        this.skipPast = typeAdapterFactory;
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        if (this.deserializer == null) {
            return this.delegate().read(jsonReader);
        }
        JsonElement jsonElement;
        return (jsonElement = Streams.parse(jsonReader)).isJsonNull()
                ? null
                : this.deserializer.deserialize(
                        jsonElement, this.typeToken.getType(), this.context);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        if (this.serializer == null) {
            this.delegate().write(jsonWriter, (T) t);
        } else if (t == null) {
            jsonWriter.nullValue();
        } else {
            Streams.write(
                    this.serializer.serialize((T) t, this.typeToken.getType(), this.context),
                    jsonWriter);
        }
    }

    private TypeAdapter<T> delegate() {
        TypeAdapter typeAdapter = this.delegate;
        return this.delegate != null
                ? typeAdapter
                : (this.delegate = this.gson.getDelegateAdapter(this.skipPast, this.typeToken));
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object result) {
        return new TreeTypeAdapter.SingleTypeFactory(result, typeToken, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(
            TypeToken<?> typeToken, Object result) {
        boolean matches = typeToken.getType() == typeToken.getRawType();
        return new TreeTypeAdapter.SingleTypeFactory(result, typeToken, matches, null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> classValue, Object result) {
        return new TreeTypeAdapter.SingleTypeFactory(result, null, false, classValue);
    }

    private final class GsonContextImpl
            implements JsonSerializationContext, JsonDeserializationContext {

        private GsonContextImpl() {}

        @Override
        public JsonElement serialize(Object result) {
            return TreeTypeAdapter.this.gson.toJsonTree(result);
        }

        @Override
        public JsonElement serialize(Object result, Type type) {
            return TreeTypeAdapter.this.gson.toJsonTree(result, type);
        }

        @Override
        public <R> R deserialize(JsonElement jsonElement, Type type) throws JsonParseException {
            return TreeTypeAdapter.this.gson.fromJson(jsonElement, type);
        }
    }

    private static final class SingleTypeFactory implements TypeAdapterFactory {

        private final TypeToken<?> exactType;

        private final boolean matchRawType;

        private final Class<?> hierarchyType;

        private final JsonSerializer<?> serializer;

        private final JsonDeserializer<?> deserializer;

        SingleTypeFactory(
                Object result, TypeToken<?> typeToken, boolean matchRawType, Class<?> classValue) {
            this.serializer = result instanceof JsonSerializer ? (JsonSerializer) result : null;
            this.deserializer =
                    result instanceof JsonDeserializer ? (JsonDeserializer) result : null;
            $Gson$Preconditions.checkArgument(this.serializer != null || this.deserializer != null);
            this.exactType = typeToken;
            this.matchRawType = matchRawType;
            this.hierarchyType = classValue;
        }

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return (this.exactType != null
                            ? this.exactType.equals(typeToken)
                                    || this.matchRawType
                                            && this.exactType.getType() == typeToken.getRawType()
                            : this.hierarchyType.isAssignableFrom(typeToken.getRawType()))
                    ? new TreeTypeAdapter<>(
                            (JsonSerializer<T>) this.serializer,
                            (JsonDeserializer<T>) this.deserializer,
                            gson,
                            typeToken,
                            this)
                    : null;
        }
    }
}
