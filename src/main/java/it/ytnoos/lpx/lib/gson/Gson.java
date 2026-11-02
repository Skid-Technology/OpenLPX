package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.ConstructorConstructor;
import it.ytnoos.lpx.lib.gson.internal.Excluder;
import it.ytnoos.lpx.lib.gson.internal.Primitives;
import it.ytnoos.lpx.lib.gson.internal.Streams;
import it.ytnoos.lpx.lib.gson.internal.bind.ArrayTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.CollectionTypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.bind.DateTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.bind.JsonTreeReader;
import it.ytnoos.lpx.lib.gson.internal.bind.JsonTreeWriter;
import it.ytnoos.lpx.lib.gson.internal.bind.MapTypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.bind.ObjectTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.ReflectiveTypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.bind.SqlDateTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.TimeTypeAdapter;
import it.ytnoos.lpx.lib.gson.internal.bind.TypeAdapters;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.gson.stream.MalformedJsonException;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {

    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;

    static final boolean DEFAULT_LENIENT = false;

    static final boolean DEFAULT_PRETTY_PRINT = false;

    static final boolean DEFAULT_ESCAPE_HTML = true;

    static final boolean DEFAULT_SERIALIZE_NULLS = false;

    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;

    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;

    private static final TypeToken<?> NULL_KEY_SURROGATE = new TypeToken<Object>() {};

    private final ThreadLocal<Map<TypeToken<?>, Gson.FutureTypeAdapter<?>>> calls =
            new ThreadLocal<>();

    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<>();

    private final List<TypeAdapterFactory> factories;

    private final ConstructorConstructor constructorConstructor;

    private final Excluder excluder;

    private final FieldNamingStrategy fieldNamingStrategy;

    private final boolean serializeNulls;

    private final boolean htmlSafe;

    private final boolean generateNonExecutableJson;

    private final boolean prettyPrinting;

    private final boolean lenient;

    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    public Gson() {
        this(
                Excluder.DEFAULT,
                FieldNamingPolicy.IDENTITY,
                Collections.emptyMap(),
                false,
                false,
                false,
                true,
                false,
                false,
                false,
                LongSerializationPolicy.DEFAULT,
                Collections.emptyList());
    }

    Gson(
            Excluder excluder2,
            FieldNamingStrategy fieldNamingStrategy2,
            Map<Type, InstanceCreator<?>> entries,
            boolean serializeNulls,
            boolean matches2,
            boolean generateNonExecutableJson,
            boolean htmlSafe,
            boolean prettyPrinting,
            boolean lenient,
            boolean matches7,
            LongSerializationPolicy longSerializationPolicy,
            List<TypeAdapterFactory> values) {
        this.constructorConstructor = new ConstructorConstructor(entries);
        this.excluder = excluder2;
        this.fieldNamingStrategy = fieldNamingStrategy2;
        this.serializeNulls = serializeNulls;
        this.generateNonExecutableJson = generateNonExecutableJson;
        this.htmlSafe = htmlSafe;
        this.prettyPrinting = prettyPrinting;
        this.lenient = lenient;
        ArrayList values2;
        (values2 = new ArrayList()).add(TypeAdapters.JSON_ELEMENT_FACTORY);
        values2.add(ObjectTypeAdapter.FACTORY);
        values2.add(excluder2);
        values2.addAll(values);
        values2.add(TypeAdapters.STRING_FACTORY);
        values2.add(TypeAdapters.INTEGER_FACTORY);
        values2.add(TypeAdapters.BOOLEAN_FACTORY);
        values2.add(TypeAdapters.BYTE_FACTORY);
        values2.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter typeAdapter = longAdapter(longSerializationPolicy);
        values2.add(TypeAdapters.newFactory(long.class, Long.class, typeAdapter));
        values2.add(
                TypeAdapters.newFactory(double.class, Double.class, this.doubleAdapter(matches7)));
        values2.add(TypeAdapters.newFactory(float.class, Float.class, this.floatAdapter(matches7)));
        values2.add(TypeAdapters.NUMBER_FACTORY);
        values2.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        values2.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        values2.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter(typeAdapter)));
        values2.add(
                TypeAdapters.newFactory(
                        AtomicLongArray.class, atomicLongArrayAdapter(typeAdapter)));
        values2.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        values2.add(TypeAdapters.CHARACTER_FACTORY);
        values2.add(TypeAdapters.STRING_BUILDER_FACTORY);
        values2.add(TypeAdapters.STRING_BUFFER_FACTORY);
        values2.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        values2.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        values2.add(TypeAdapters.URL_FACTORY);
        values2.add(TypeAdapters.URI_FACTORY);
        values2.add(TypeAdapters.UUID_FACTORY);
        values2.add(TypeAdapters.CURRENCY_FACTORY);
        values2.add(TypeAdapters.LOCALE_FACTORY);
        values2.add(TypeAdapters.INET_ADDRESS_FACTORY);
        values2.add(TypeAdapters.BIT_SET_FACTORY);
        values2.add(DateTypeAdapter.FACTORY);
        values2.add(TypeAdapters.CALENDAR_FACTORY);
        values2.add(TimeTypeAdapter.FACTORY);
        values2.add(SqlDateTypeAdapter.FACTORY);
        values2.add(TypeAdapters.TIMESTAMP_FACTORY);
        values2.add(ArrayTypeAdapter.FACTORY);
        values2.add(TypeAdapters.CLASS_FACTORY);
        values2.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        values2.add(new MapTypeAdapterFactory(this.constructorConstructor, matches2));
        this.jsonAdapterFactory =
                new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        values2.add(this.jsonAdapterFactory);
        values2.add(TypeAdapters.ENUM_FACTORY);
        values2.add(
                new ReflectiveTypeAdapterFactory(
                        this.constructorConstructor,
                        fieldNamingStrategy2,
                        excluder2,
                        this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList(values2);
    }

    public Excluder excluder() {
        return this.excluder;
    }

    public FieldNamingStrategy fieldNamingStrategy() {
        return this.fieldNamingStrategy;
    }

    public boolean serializeNulls() {
        return this.serializeNulls;
    }

    public boolean htmlSafe() {
        return this.htmlSafe;
    }

    private TypeAdapter<Number> doubleAdapter(boolean result) {
        return result
                ? TypeAdapters.DOUBLE
                : new TypeAdapter<Number>() {

                    public Double read(JsonReader result) throws IOException {
                        if (result.peek() == JsonToken.NULL) {
                            result.nextNull();
                            return null;
                        } else {
                            return result.nextDouble();
                        }
                    }

                    public void write(JsonWriter result, Number number) throws IOException {
                        if (number == null) {
                            result.nullValue();
                        } else {
                            Gson.checkValidFloatingPoint(number.doubleValue());
                            result.value(number);
                        }
                    }
                };
    }

    private TypeAdapter<Number> floatAdapter(boolean result) {
        return result
                ? TypeAdapters.FLOAT
                : new TypeAdapter<Number>() {

                    public Float read(JsonReader result) throws IOException {
                        if (result.peek() == JsonToken.NULL) {
                            result.nextNull();
                            return null;
                        } else {
                            return (float) result.nextDouble();
                        }
                    }

                    public void write(JsonWriter result, Number number) throws IOException {
                        if (number == null) {
                            result.nullValue();
                        } else {
                            Gson.checkValidFloatingPoint(number.floatValue());
                            result.value(number);
                        }
                    }
                };
    }

    static void checkValidFloatingPoint(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException(
                    value
                            + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter<Number> longAdapter(
            LongSerializationPolicy longSerializationPolicy) {
        return longSerializationPolicy == LongSerializationPolicy.DEFAULT
                ? TypeAdapters.LONG
                : new TypeAdapter<Number>() {

                    public Number read(JsonReader result) throws IOException {
                        if (result.peek() == JsonToken.NULL) {
                            result.nextNull();
                            return null;
                        } else {
                            return result.nextLong();
                        }
                    }

                    public void write(JsonWriter result, Number number) throws IOException {
                        if (number == null) {
                            result.nullValue();
                        } else {
                            result.value(number.toString());
                        }
                    }
                };
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(
            final TypeAdapter<Number> typeAdapter) {
        return (new TypeAdapter<AtomicLong>() {

                    public void write(JsonWriter result, AtomicLong result2) throws IOException {
                        typeAdapter.write(result, result2.get());
                    }

                    public AtomicLong read(JsonReader result) throws IOException {
                        Number result2 = (Number) typeAdapter.read(result);
                        return new AtomicLong(result2.longValue());
                    }
                })
                .nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(
            final TypeAdapter<Number> typeAdapter) {
        return (new TypeAdapter<AtomicLongArray>() {

                    public void write(JsonWriter result, AtomicLongArray atomicLongArray)
                            throws IOException {
                        result.beginArray();
                        int result2 = 0;
                        for (int result3 = atomicLongArray.length(); result2 < result3; result2++) {
                            typeAdapter.write(result, atomicLongArray.get(result2));
                        }
                        result.endArray();
                    }

                    public AtomicLongArray read(JsonReader result) throws IOException {
                        ArrayList<Long> values = new ArrayList<>();
                        result.beginArray();
                        while (result.hasNext()) {
                            long result2 = ((Number) typeAdapter.read(result)).longValue();
                            values.add(result2);
                        }
                        result.endArray();
                        int size2 = values.size();
                        AtomicLongArray result3 = new AtomicLongArray(size2);
                        for (int index = 0; index < size2; index++) {
                            result3.set(index, (Long) values.get(index));
                        }
                        return result3;
                    }
                })
                .nullSafe();
    }

    public <T> TypeAdapter<T> getAdapter(TypeToken<T> typeToken) {
        TypeAdapter typeAdapter;
        if ((typeAdapter =
                        this.typeTokenCache.get(typeToken == null ? NULL_KEY_SURROGATE : typeToken))
                != null) {
            return typeAdapter;
        }
        Map<TypeToken<?>, it.ytnoos.lpx.lib.gson.Gson.FutureTypeAdapter<?>> entries =
                this.calls.get();
        boolean matches = false;
        if (entries == null) {
            entries = new HashMap();
            this.calls.set(entries);
            matches = true;
        }
        Gson.FutureTypeAdapter futureTypeAdapter;
        if ((futureTypeAdapter = (Gson.FutureTypeAdapter) entries.get(typeToken)) != null) {
            return futureTypeAdapter;
        }
        try {
            Gson.FutureTypeAdapter futureTypeAdapter2 = new Gson.FutureTypeAdapter();
            entries.put(typeToken, futureTypeAdapter2);
            Iterator<it.ytnoos.lpx.lib.gson.TypeAdapterFactory> iterator2 =
                    this.factories.iterator();
            while (iterator2.hasNext()) {
                TypeAdapter typeAdapter2;
                if ((typeAdapter2 = ((TypeAdapterFactory) iterator2.next()).create(this, typeToken))
                        != null) {
                    futureTypeAdapter2.setDelegate(typeAdapter2);
                    this.typeTokenCache.put(typeToken, typeAdapter2);
                    return typeAdapter2;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + typeToken);
        } finally {
            entries.remove(typeToken);
            if (matches) {
                this.calls.remove();
            }
        }
    }

    public <T> TypeAdapter<T> getDelegateAdapter(
            TypeAdapterFactory typeAdapterFactory, TypeToken<T> typeToken) {
        if (!this.factories.contains(typeAdapterFactory)) {
            typeAdapterFactory = this.jsonAdapterFactory;
        }
        boolean matches = false;
        for (TypeAdapterFactory typeAdapterFactory2 : this.factories) {
            if (!matches) {
                if (typeAdapterFactory2 == typeAdapterFactory) {
                    matches = true;
                }
            } else {
                TypeAdapter typeAdapter;
                if ((typeAdapter = typeAdapterFactory2.create(this, typeToken)) != null) {
                    return typeAdapter;
                }
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> classValue) {
        return this.getAdapter(TypeToken.get(classValue));
    }

    public JsonElement toJsonTree(Object result) {
        return result == null ? JsonNull.INSTANCE : this.toJsonTree(result, result.getClass());
    }

    public JsonElement toJsonTree(Object result, Type type) {
        JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
        this.toJson(result, type, jsonTreeWriter);
        return jsonTreeWriter.get();
    }

    public String toJson(Object result) {
        return result == null
                ? this.toJson(JsonNull.INSTANCE)
                : this.toJson(result, result.getClass());
    }

    public String toJson(Object result, Type type) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(result, type, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(Object result, Appendable appendable) throws JsonIOException {
        if (result != null) {
            this.toJson(result, result.getClass(), appendable);
        } else {
            this.toJson(JsonNull.INSTANCE, appendable);
        }
    }

    public void toJson(Object result, Type type, Appendable appendable) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(appendable));
            this.toJson(result, type, jsonWriter);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        }
    }

    public void toJson(Object result, Type type, JsonWriter jsonWriter) throws JsonIOException {
        TypeAdapter adapter = this.getAdapter(TypeToken.get(type));
        boolean lenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean htmlSafe2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean serializeNulls2 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            adapter.write(jsonWriter, result);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        } finally {
            jsonWriter.setLenient(lenient);
            jsonWriter.setHtmlSafe(htmlSafe2);
            jsonWriter.setSerializeNulls(serializeNulls2);
        }
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(JsonElement jsonElement, Appendable appendable) throws JsonIOException {
        try {
            JsonWriter jsonWriter = this.newJsonWriter(Streams.writerForAppendable(appendable));
            this.toJson(jsonElement, jsonWriter);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        }
    }

    public JsonWriter newJsonWriter(Writer writer) throws IOException {
        if (this.generateNonExecutableJson) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.prettyPrinting) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.serializeNulls);
        return jsonWriter;
    }

    public JsonReader newJsonReader(Reader reader) {
        JsonReader jsonReader;
        (jsonReader = new JsonReader(reader)).setLenient(this.lenient);
        return jsonReader;
    }

    public void toJson(JsonElement jsonElement, JsonWriter jsonWriter) throws JsonIOException {
        boolean lenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean htmlSafe2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean serializeNulls2 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, jsonWriter);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        } finally {
            jsonWriter.setLenient(lenient);
            jsonWriter.setHtmlSafe(htmlSafe2);
            jsonWriter.setSerializeNulls(serializeNulls2);
        }
    }

    public <T> T fromJson(String text, Class<T> classValue) throws JsonSyntaxException {
        Object result = this.fromJson(text, (Type) classValue);
        return Primitives.<T>wrap(classValue).cast(result);
    }

    public <T> T fromJson(String text, Type type) throws JsonSyntaxException {
        if (text == null) {
            return null;
        }
        StringReader stringReader = new StringReader(text);
        return this.fromJson(stringReader, type);
    }

    public <T> T fromJson(Reader reader, Class<T> classValue)
            throws JsonSyntaxException, JsonIOException {
        JsonReader jsonReader = this.newJsonReader(reader);
        Object result;
        assertFullConsumption(result = this.fromJson(jsonReader, classValue), jsonReader);
        return Primitives.<T>wrap(classValue).cast(result);
    }

    public <T> T fromJson(Reader reader, Type type) throws JsonIOException, JsonSyntaxException {
        JsonReader jsonReader = this.newJsonReader(reader);
        Object result;
        assertFullConsumption(result = this.fromJson(jsonReader, type), jsonReader);
        return (T) result;
    }

    private static void assertFullConsumption(Object result, JsonReader jsonReader) {
        try {
            if (result != null && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
        } catch (MalformedJsonException malformedJsonException) {
            throw new JsonSyntaxException(malformedJsonException);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        }
    }

    public <T> T fromJson(JsonReader jsonReader, Type type)
            throws JsonIOException, JsonSyntaxException {
        boolean matches = true;
        boolean lenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            jsonReader.peek();
            matches = false;
            TypeToken typeToken = TypeToken.get(type);
            return ((T) this.<T>getAdapter(typeToken).read(jsonReader));
        } catch (EOFException eOFException) {
            if (matches) {
                return null;
            } else {
                throw new JsonSyntaxException(eOFException);
            }
        } catch (IllegalStateException illegalStateException) {
            throw new JsonSyntaxException(illegalStateException);
        } catch (IOException oException) {
            throw new JsonSyntaxException(oException);
        } finally {
            jsonReader.setLenient(lenient);
        }
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> classValue) throws JsonSyntaxException {
        Object result = this.fromJson(jsonElement, (Type) classValue);
        return Primitives.<T>wrap(classValue).cast(result);
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) throws JsonSyntaxException {
        return jsonElement == null ? null : this.fromJson(new JsonTreeReader(jsonElement), type);
    }

    @Override
    public String toString() {
        return "{serializeNulls:"
                + this.serializeNulls
                + "factories:"
                + this.factories
                + ",instanceCreators:"
                + this.constructorConstructor
                + "}";
    }

    static class FutureTypeAdapter<T> extends TypeAdapter<T> {

        private TypeAdapter<T> delegate;

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            } else {
                return this.delegate.read(jsonReader);
            }
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(jsonWriter, (T) t);
        }
    }
}
