package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.$Gson$Types;
import it.ytnoos.lpx.lib.gson.internal.ConstructorConstructor;
import it.ytnoos.lpx.lib.gson.internal.ObjectConstructor;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {

    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor2) {
        this.constructorConstructor = constructorConstructor2;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }
        Type collectionElementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter adapter = gson.getAdapter(TypeToken.get(collectionElementType));
        ObjectConstructor objectConstructor = this.constructorConstructor.get(typeToken);
        return new CollectionTypeAdapterFactory.Adapter(
                gson, collectionElementType, adapter, objectConstructor);
    }

    private static final class Adapter<E> extends TypeAdapter<Collection<E>> {

        private final TypeAdapter<E> elementTypeAdapter;

        private final ObjectConstructor<? extends Collection<E>> constructor;

        public Adapter(
                Gson gson,
                Type type,
                TypeAdapter<E> typeAdapter,
                ObjectConstructor<? extends Collection<E>> objectConstructor) {
            this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(gson, typeAdapter, type);
            this.constructor = objectConstructor;
        }

        public Collection<E> read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection<E> values = this.constructor.construct();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                E e = this.elementTypeAdapter.read(jsonReader);
                values.add(e);
            }
            jsonReader.endArray();
            return values;
        }

        public void write(JsonWriter jsonWriter, Collection<E> values) throws IOException {
            if (values == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.beginArray();
                for (Object result : values) {
                    this.elementTypeAdapter.write(jsonWriter, (E) result);
                }
                jsonWriter.endArray();
            }
        }
    }
}
