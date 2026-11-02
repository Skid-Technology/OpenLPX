package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {

    private final Gson context;

    private final TypeAdapter<T> delegate;

    private final Type type;

    TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type2) {
        this.context = gson;
        this.delegate = typeAdapter;
        this.type = type2;
    }

    @Override
    public T read(JsonReader jsonReader) throws IOException {
        return this.delegate.read(jsonReader);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        TypeAdapter typeAdapter = this.delegate;
        Type type2;
        if ((type2 = this.getRuntimeTypeIfMoreSpecific(this.type, t)) != this.type) {
            TypeAdapter typeAdapter2;
            if (!((typeAdapter2 = this.context.getAdapter(TypeToken.get(type2)))
                    instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                typeAdapter = typeAdapter2;
            } else if (!(this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                typeAdapter = this.delegate;
            } else {
                typeAdapter = typeAdapter2;
            }
        }
        typeAdapter.write(jsonWriter, t);
    }

    private Type getRuntimeTypeIfMoreSpecific(Type type, Object result) {
        if (result != null
                && (type == Object.class
                        || type instanceof TypeVariable
                        || type instanceof Class)) {
            type = result.getClass();
        }
        return type;
    }
}
