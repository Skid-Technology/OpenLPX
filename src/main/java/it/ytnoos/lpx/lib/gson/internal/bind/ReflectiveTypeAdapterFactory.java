package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.FieldNamingStrategy;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.annotations.JsonAdapter;
import it.ytnoos.lpx.lib.gson.annotations.SerializedName;
import it.ytnoos.lpx.lib.gson.internal.$Gson$Types;
import it.ytnoos.lpx.lib.gson.internal.ConstructorConstructor;
import it.ytnoos.lpx.lib.gson.internal.Excluder;
import it.ytnoos.lpx.lib.gson.internal.ObjectConstructor;
import it.ytnoos.lpx.lib.gson.internal.Primitives;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {

    private final ConstructorConstructor constructorConstructor;

    private final FieldNamingStrategy fieldNamingPolicy;

    private final Excluder excluder;

    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    public ReflectiveTypeAdapterFactory(
            ConstructorConstructor constructorConstructor2,
            FieldNamingStrategy fieldNamingStrategy,
            Excluder excluder2,
            JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.constructorConstructor = constructorConstructor2;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder2;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
    }

    public boolean excludeField(Field field, boolean matches) {
        return excludeField(field, matches, this.excluder);
    }

    static boolean excludeField(Field field, boolean matches, Excluder excluder) {
        return !excluder.excludeClass(field.getType(), matches)
                && !excluder.excludeField(field, matches);
    }

    private List<String> getFieldNames(Field field) {
        SerializedName serializedName;
        if ((serializedName = field.getAnnotation(SerializedName.class)) == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName(field));
        }
        String text = serializedName.value();
        String[] texts;
        if ((texts = serializedName.alternate()).length == 0) {
            return Collections.singletonList(text);
        }
        ArrayList<String> values;
        (values = new ArrayList(texts.length + 1)).add(text);
        String[] texts2 = texts;
        int value2 = texts.length;
        for (int index = 0; index < value2; index++) {
            String text2 = texts2[index];
            values.add(text2);
        }
        return values;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        ObjectConstructor objectConstructor = this.constructorConstructor.get(typeToken);
        return new ReflectiveTypeAdapterFactory.Adapter<>(
                objectConstructor, this.getBoundFields(gson, typeToken, rawType));
    }

    private ReflectiveTypeAdapterFactory.BoundField createBoundField(
            final Gson gson,
            final Field field,
            String text,
            final TypeToken<?> typeToken,
            boolean matches,
            boolean matches2) {
        final boolean primitive = Primitives.isPrimitive(typeToken.getRawType());
        JsonAdapter annotation = field.getAnnotation(JsonAdapter.class);
        TypeAdapter typeAdapter = null;
        if (annotation != null) {
            typeAdapter =
                    this.jsonAdapterFactory.getTypeAdapter(
                            this.constructorConstructor, gson, typeToken, annotation);
        }
        final boolean matches3 = typeAdapter != null;
        if (typeAdapter == null) {
            typeAdapter = gson.getAdapter(typeToken);
        }
        final TypeAdapter typeAdapter2 = typeAdapter;
        return new ReflectiveTypeAdapterFactory.BoundField(text, matches, matches2) {

            @Override
            void write(JsonWriter result, Object result2)
                    throws IOException, IllegalAccessException {
                Object result3 = field.get(result2);
                (matches3
                                ? typeAdapter2
                                : new TypeAdapterRuntimeTypeWrapper<>(
                                        gson, typeAdapter2, typeToken.getType()))
                        .write(result, result3);
            }

            @Override
            void read(JsonReader result, Object result2)
                    throws IOException, IllegalAccessException {
                Object result3;
                if ((result3 = typeAdapter2.read(result)) != null || !primitive) {
                    field.set(result2, result3);
                }
            }

            @Override
            public boolean writeField(Object result) throws IOException, IllegalAccessException {
                return !this.serialized ? false : field.get(result) != result;
            }
        };
    }

    private Map<String, ReflectiveTypeAdapterFactory.BoundField> getBoundFields(
            Gson gson, TypeToken<?> typeToken, Class<?> classValue) {
        LinkedHashMap<String, ReflectiveTypeAdapterFactory.BoundField> entries =
                new LinkedHashMap<>();
        if (classValue.isInterface()) {
            return entries;
        }
        Type type = typeToken.getType();
        while (classValue != Object.class) {
            Field[] fields;
            int value = (fields = classValue.getDeclaredFields()).length;
            for (int index = 0; index < value; index++) {
                Field field = fields[index];
                boolean matches = this.excludeField(field, true);
                boolean matches2 = this.excludeField(field, false);
                if (matches || matches2) {
                    field.setAccessible(true);
                    Type type2 =
                            $Gson$Types.resolve(
                                    typeToken.getType(), classValue, field.getGenericType());
                    List<String> fieldNames = this.getFieldNames(field);
                    ReflectiveTypeAdapterFactory.BoundField boundField = null;
                    for (int index2 = 0; index2 < fieldNames.size(); index2++) {
                        String text = (String) fieldNames.get(index2);
                        if (index2 != 0) {
                            matches = false;
                        }
                        ReflectiveTypeAdapterFactory.BoundField boundField2 =
                                this.createBoundField(
                                        gson, field, text, TypeToken.get(type2), matches, matches2);
                        ReflectiveTypeAdapterFactory.BoundField boundField3 =
                                entries.put(text, boundField2);
                        if (boundField == null) {
                            boundField = boundField3;
                        }
                    }
                    if (boundField != null) {
                        throw new IllegalArgumentException(
                                type + " declares multiple JSON fields named " + boundField.name);
                    }
                }
            }
            classValue =
                    (typeToken =
                                    TypeToken.get(
                                            $Gson$Types.resolve(
                                                    typeToken.getType(),
                                                    classValue,
                                                    classValue.getGenericSuperclass())))
                            .getRawType();
        }
        return entries;
    }

    public static final class Adapter<T> extends TypeAdapter<T> {

        private final ObjectConstructor<T> constructor;

        private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;

        Adapter(
                ObjectConstructor<T> objectConstructor,
                Map<String, ReflectiveTypeAdapterFactory.BoundField> entries) {
            this.constructor = objectConstructor;
            this.boundFields = entries;
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T t = this.constructor.construct();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String text = jsonReader.nextName();
                    ReflectiveTypeAdapterFactory.BoundField boundField = this.boundFields.get(text);
                    if (boundField != null && boundField.deserialized) {
                        boundField.read(jsonReader, t);
                    } else {
                        jsonReader.skipValue();
                    }
                }
            } catch (IllegalStateException illegalStateException) {
                throw new JsonSyntaxException(illegalStateException);
            } catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
            jsonReader.endObject();
            return (T) t;
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (t == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.beginObject();
                try {
                    for (ReflectiveTypeAdapterFactory.BoundField boundField :
                            this.boundFields.values()) {
                        if (boundField.writeField(t)) {
                            jsonWriter.name(boundField.name);
                            boundField.write(jsonWriter, t);
                        }
                    }
                } catch (IllegalAccessException illegalAccessException) {
                    throw new AssertionError(illegalAccessException);
                }
                jsonWriter.endObject();
            }
        }
    }

    abstract static class BoundField {

        final String name;

        final boolean serialized;

        final boolean deserialized;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }

        abstract boolean writeField(Object result) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter jsonWriter, Object result)
                throws IOException, IllegalAccessException;

        abstract void read(JsonReader jsonReader, Object result)
                throws IOException, IllegalAccessException;
    }
}
