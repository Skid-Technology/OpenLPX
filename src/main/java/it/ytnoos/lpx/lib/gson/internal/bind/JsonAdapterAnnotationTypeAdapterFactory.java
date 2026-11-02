package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonDeserializer;
import it.ytnoos.lpx.lib.gson.JsonSerializer;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.annotations.JsonAdapter;
import it.ytnoos.lpx.lib.gson.internal.ConstructorConstructor;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {

    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor2) {
        this.constructorConstructor = constructorConstructor2;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        JsonAdapter jsonAdapter;
        return (TypeAdapter<T>)
                ((jsonAdapter = typeToken.getRawType().getAnnotation(JsonAdapter.class)) == null
                        ? null
                        : this.getTypeAdapter(
                                this.constructorConstructor, gson, typeToken, jsonAdapter));
    }

    TypeAdapter<?> getTypeAdapter(
            ConstructorConstructor constructorConstructor,
            Gson gson,
            TypeToken<?> typeToken,
            JsonAdapter jsonAdapter) {
        Object result;
        TypeAdapter typeAdapter;
        if ((result = constructorConstructor.get(TypeToken.get(jsonAdapter.value())).construct())
                instanceof TypeAdapter) {
            typeAdapter = (TypeAdapter) result;
        } else if (result instanceof TypeAdapterFactory) {
            typeAdapter = ((TypeAdapterFactory) result).create(gson, typeToken);
        } else {
            if (!(result instanceof JsonSerializer) && !(result instanceof JsonDeserializer)) {
                throw new IllegalArgumentException(
                        "@JsonAdapter value must be TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer reference.");
            }
            JsonSerializer jsonSerializer =
                    result instanceof JsonSerializer ? (JsonSerializer) result : null;
            JsonDeserializer jsonDeserializer =
                    result instanceof JsonDeserializer ? (JsonDeserializer) result : null;
            typeAdapter =
                    new TreeTypeAdapter(jsonSerializer, jsonDeserializer, gson, typeToken, null);
        }
        if (typeAdapter != null && jsonAdapter.nullSafe()) {
            typeAdapter = typeAdapter.nullSafe();
        }
        return typeAdapter;
    }
}
