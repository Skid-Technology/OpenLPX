package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.compat.ReflectionNames;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonArray;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonIOException;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonPrimitive;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.annotations.SerializedName;
import it.ytnoos.lpx.lib.gson.internal.LazilyParsedNumber;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {

    public static final TypeAdapter<Class> CLASS =
            new TypeAdapter<Class>() {

                public void write(JsonWriter jsonWriter, Class classValue) throws IOException {
                    if (classValue == null) {
                        jsonWriter.nullValue();
                    } else {
                        throw new UnsupportedOperationException(
                                "Attempted to serialize java.lang.Class: "
                                        + classValue.getName()
                                        + ". Forgot to register a type adapter?");
                    }
                }

                public Class read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        throw new UnsupportedOperationException(
                                "Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
                    }
                }
            };

    public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);

    public static final TypeAdapter<BitSet> BIT_SET =
            new TypeAdapter<BitSet>() {

                public BitSet read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    BitSet bitSet = new BitSet();
                    jsonReader.beginArray();
                    int value = 0;
                    for (JsonToken jsonToken = jsonReader.peek();
                            jsonToken != JsonToken.END_ARRAY;
                            jsonToken = jsonReader.peek()) {
                        boolean matches;
                        switch (jsonToken) {
                            case NUMBER:
                                matches = jsonReader.nextInt() != 0;
                                break;
                            case BOOLEAN:
                                matches = jsonReader.nextBoolean();
                                break;
                            case STRING:
                                String text = jsonReader.nextString();
                                try {
                                    matches = Integer.parseInt(text) != 0;
                                    break;
                                } catch (NumberFormatException numberFormatException) {
                                    throw new JsonSyntaxException(
                                            "Error: Expecting: bitset number value (1, 0), Found: "
                                                    + text);
                                }
                            default:
                                throw new JsonSyntaxException(
                                        "Invalid bitset value type: " + jsonToken);
                        }
                        if (matches) {
                            bitSet.set(value);
                        }
                        value++;
                    }
                    jsonReader.endArray();
                    return bitSet;
                }

                public void write(JsonWriter jsonWriter, BitSet bitSet) throws IOException {
                    if (bitSet == null) {
                        jsonWriter.nullValue();
                    } else {
                        jsonWriter.beginArray();
                        for (int index = 0; index < bitSet.length(); index++) {
                            int value2 = bitSet.get(index) ? 1 : 0;
                            jsonWriter.value(value2);
                        }
                        jsonWriter.endArray();
                    }
                }
            };

    public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);

    public static final TypeAdapter<Boolean> BOOLEAN =
            new TypeAdapter<Boolean>() {

                public Boolean read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return jsonReader.peek() == JsonToken.STRING
                                ? Boolean.parseBoolean(jsonReader.nextString())
                                : jsonReader.nextBoolean();
                    }
                }

                public void write(JsonWriter jsonWriter, Boolean matches) throws IOException {
                    jsonWriter.value(matches);
                }
            };

    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING =
            new TypeAdapter<Boolean>() {

                public Boolean read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return Boolean.valueOf(jsonReader.nextString());
                    }
                }

                public void write(JsonWriter jsonWriter, Boolean matches) throws IOException {
                    jsonWriter.value(matches == null ? "null" : matches.toString());
                }
            };

    public static final TypeAdapterFactory BOOLEAN_FACTORY =
            newFactory(boolean.class, Boolean.class, BOOLEAN);

    public static final TypeAdapter<Number> BYTE =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return (byte) jsonReader.nextInt();
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapterFactory BYTE_FACTORY = newFactory(byte.class, Byte.class, BYTE);

    public static final TypeAdapter<Number> SHORT =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return (short) jsonReader.nextInt();
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapterFactory SHORT_FACTORY =
            newFactory(short.class, Short.class, SHORT);

    public static final TypeAdapter<Number> INTEGER =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return jsonReader.nextInt();
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapterFactory INTEGER_FACTORY =
            newFactory(int.class, Integer.class, INTEGER);

    public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER =
            (new TypeAdapter<AtomicInteger>() {

                        public AtomicInteger read(JsonReader jsonReader) throws IOException {
                            try {
                                return new AtomicInteger(jsonReader.nextInt());
                            } catch (NumberFormatException numberFormatException) {
                                throw new JsonSyntaxException(numberFormatException);
                            }
                        }

                        public void write(JsonWriter jsonWriter, AtomicInteger atomicInteger)
                                throws IOException {
                            jsonWriter.value(atomicInteger.get());
                        }
                    })
                    .nullSafe();

    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY =
            newFactory(AtomicInteger.class, ATOMIC_INTEGER);

    public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN =
            (new TypeAdapter<AtomicBoolean>() {

                        public AtomicBoolean read(JsonReader jsonReader) throws IOException {
                            return new AtomicBoolean(jsonReader.nextBoolean());
                        }

                        public void write(JsonWriter jsonWriter, AtomicBoolean atomicBoolean)
                                throws IOException {
                            jsonWriter.value(atomicBoolean.get());
                        }
                    })
                    .nullSafe();

    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY =
            newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);

    public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY =
            (new TypeAdapter<AtomicIntegerArray>() {

                        public AtomicIntegerArray read(JsonReader jsonReader) throws IOException {
                            ArrayList<Integer> values = new ArrayList<>();
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                try {
                                    int value = jsonReader.nextInt();
                                    values.add(value);
                                } catch (NumberFormatException numberFormatException) {
                                    throw new JsonSyntaxException(numberFormatException);
                                }
                            }
                            jsonReader.endArray();
                            int size2 = values.size();
                            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size2);
                            for (int index = 0; index < size2; index++) {
                                atomicIntegerArray.set(index, (Integer) values.get(index));
                            }
                            return atomicIntegerArray;
                        }

                        public void write(
                                JsonWriter jsonWriter, AtomicIntegerArray atomicIntegerArray)
                                throws IOException {
                            jsonWriter.beginArray();
                            int value2 = 0;
                            for (int size = atomicIntegerArray.length(); value2 < size; value2++) {
                                jsonWriter.value(atomicIntegerArray.get(value2));
                            }
                            jsonWriter.endArray();
                        }
                    })
                    .nullSafe();

    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY =
            newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);

    public static final TypeAdapter<Number> LONG =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return jsonReader.nextLong();
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapter<Number> FLOAT =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return (float) jsonReader.nextDouble();
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapter<Number> DOUBLE =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return jsonReader.nextDouble();
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapter<Number> NUMBER =
            new TypeAdapter<Number>() {

                public Number read(JsonReader jsonReader) throws IOException {
                    JsonToken jsonToken = jsonReader.peek();
                    switch (jsonToken) {
                        case NUMBER:
                            return new LazilyParsedNumber(jsonReader.nextString());
                        case NULL:
                            jsonReader.nextNull();
                            return null;
                        default:
                            throw new JsonSyntaxException("Expecting number, got: " + jsonToken);
                    }
                }

                public void write(JsonWriter jsonWriter, Number number) throws IOException {
                    jsonWriter.value(number);
                }
            };

    public static final TypeAdapterFactory NUMBER_FACTORY = newFactory(Number.class, NUMBER);

    public static final TypeAdapter<Character> CHARACTER =
            new TypeAdapter<Character>() {

                public Character read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        String text;
                        if ((text = jsonReader.nextString()).length() != 1) {
                            throw new JsonSyntaxException("Expecting character, got: " + text);
                        } else {
                            return text.charAt(0);
                        }
                    }
                }

                public void write(JsonWriter jsonWriter, Character character) throws IOException {
                    jsonWriter.value(character == null ? null : String.valueOf(character));
                }
            };

    public static final TypeAdapterFactory CHARACTER_FACTORY =
            newFactory(char.class, Character.class, CHARACTER);

    public static final TypeAdapter<String> STRING =
            new TypeAdapter<String>() {

                public String read(JsonReader jsonReader) throws IOException {
                    JsonToken jsonToken;
                    if ((jsonToken = jsonReader.peek()) == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return jsonToken == JsonToken.BOOLEAN
                                ? Boolean.toString(jsonReader.nextBoolean())
                                : jsonReader.nextString();
                    }
                }

                public void write(JsonWriter jsonWriter, String text) throws IOException {
                    jsonWriter.value(text);
                }
            };

    public static final TypeAdapter<BigDecimal> BIG_DECIMAL =
            new TypeAdapter<BigDecimal>() {

                public BigDecimal read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return new BigDecimal(jsonReader.nextString());
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, BigDecimal bigDecimal) throws IOException {
                    jsonWriter.value(bigDecimal);
                }
            };

    public static final TypeAdapter<BigInteger> BIG_INTEGER =
            new TypeAdapter<BigInteger>() {

                public BigInteger read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        return new BigInteger(jsonReader.nextString());
                    } catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }

                public void write(JsonWriter jsonWriter, BigInteger bigInteger) throws IOException {
                    jsonWriter.value(bigInteger);
                }
            };

    public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);

    public static final TypeAdapter<StringBuilder> STRING_BUILDER =
            new TypeAdapter<StringBuilder>() {

                public StringBuilder read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return new StringBuilder(jsonReader.nextString());
                    }
                }

                public void write(JsonWriter jsonWriter, StringBuilder stringBuilder)
                        throws IOException {
                    jsonWriter.value(stringBuilder == null ? null : stringBuilder.toString());
                }
            };

    public static final TypeAdapterFactory STRING_BUILDER_FACTORY =
            newFactory(StringBuilder.class, STRING_BUILDER);

    public static final TypeAdapter<StringBuffer> STRING_BUFFER =
            new TypeAdapter<StringBuffer>() {

                public StringBuffer read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return new StringBuffer(jsonReader.nextString());
                    }
                }

                public void write(JsonWriter jsonWriter, StringBuffer stringBuffer)
                        throws IOException {
                    jsonWriter.value(stringBuffer == null ? null : stringBuffer.toString());
                }
            };

    public static final TypeAdapterFactory STRING_BUFFER_FACTORY =
            newFactory(StringBuffer.class, STRING_BUFFER);

    public static final TypeAdapter<URL> URL =
            new TypeAdapter<URL>() {

                public URL read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        String text = jsonReader.nextString();
                        return "null".equals(text) ? null : new URL(text);
                    }
                }

                public void write(JsonWriter jsonWriter, URL uRL) throws IOException {
                    jsonWriter.value(uRL == null ? null : uRL.toExternalForm());
                }
            };

    public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);

    public static final TypeAdapter<URI> URI =
            new TypeAdapter<URI>() {

                public URI read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    try {
                        String text = jsonReader.nextString();
                        return "null".equals(text) ? null : new URI(text);
                    } catch (URISyntaxException uRISyntaxException) {
                        throw new JsonIOException(uRISyntaxException);
                    }
                }

                public void write(JsonWriter jsonWriter, URI uRI) throws IOException {
                    jsonWriter.value(uRI == null ? null : uRI.toASCIIString());
                }
            };

    public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);

    public static final TypeAdapter<InetAddress> INET_ADDRESS =
            new TypeAdapter<InetAddress>() {

                public InetAddress read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return InetAddress.getByName(jsonReader.nextString());
                    }
                }

                public void write(JsonWriter jsonWriter, InetAddress inetAddress)
                        throws IOException {
                    jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
                }
            };

    public static final TypeAdapterFactory INET_ADDRESS_FACTORY =
            newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);

    public static final TypeAdapter<UUID> UUID =
            new TypeAdapter<UUID>() {

                public UUID read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    } else {
                        return java.util.UUID.fromString(jsonReader.nextString());
                    }
                }

                public void write(JsonWriter jsonWriter, UUID uUID) throws IOException {
                    jsonWriter.value(uUID == null ? null : uUID.toString());
                }
            };

    public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);

    public static final TypeAdapter<Currency> CURRENCY =
            (new TypeAdapter<Currency>() {

                        public Currency read(JsonReader jsonReader) throws IOException {
                            return Currency.getInstance(jsonReader.nextString());
                        }

                        public void write(JsonWriter jsonWriter, Currency currency)
                                throws IOException {
                            jsonWriter.value(currency.getCurrencyCode());
                        }
                    })
                    .nullSafe();

    public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);

    public static final TypeAdapterFactory TIMESTAMP_FACTORY =
            new TypeAdapterFactory() {

                @Override
                public <T> TypeAdapter<T> create(Gson result, TypeToken<T> typeToken) {
                    if (typeToken.getRawType() != Timestamp.class) {
                        return null;
                    }
                    final TypeAdapter adapter = result.getAdapter(Date.class);
                    return (TypeAdapter<T>)
                            (new TypeAdapter<Timestamp>() {

                                public Timestamp read(JsonReader result) throws IOException {
                                    Date result2;
                                    return (result2 = (Date) adapter.read(result)) != null
                                            ? new Timestamp(result2.getTime())
                                            : null;
                                }

                                public void write(JsonWriter result, Timestamp result2)
                                        throws IOException {
                                    adapter.write(result, result2);
                                }
                            });
                }
            };

    public static final TypeAdapter<Calendar> CALENDAR =
            new TypeAdapter<Calendar>() {

                public Calendar read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    jsonReader.beginObject();
                    int value = 0;
                    int value2 = 0;
                    int value3 = 0;
                    int value4 = 0;
                    int value5 = 0;
                    int value6 = 0;
                    while (jsonReader.peek() != JsonToken.END_OBJECT) {
                        String text = jsonReader.nextName();
                        int value7 = jsonReader.nextInt();
                        if ("year".equals(text)) {
                            value = value7;
                        } else if ("month".equals(text)) {
                            value2 = value7;
                        } else if ("dayOfMonth".equals(text)) {
                            value3 = value7;
                        } else if ("hourOfDay".equals(text)) {
                            value4 = value7;
                        } else if ("minute".equals(text)) {
                            value5 = value7;
                        } else if ("second".equals(text)) {
                            value6 = value7;
                        }
                    }
                    jsonReader.endObject();
                    return new GregorianCalendar(value, value2, value3, value4, value5, value6);
                }

                public void write(JsonWriter jsonWriter, Calendar calendar) throws IOException {
                    if (calendar == null) {
                        jsonWriter.nullValue();
                    } else {
                        jsonWriter.beginObject();
                        jsonWriter.name("year");
                        jsonWriter.value(calendar.get(1));
                        jsonWriter.name("month");
                        jsonWriter.value(calendar.get(2));
                        jsonWriter.name("dayOfMonth");
                        jsonWriter.value(calendar.get(5));
                        jsonWriter.name("hourOfDay");
                        jsonWriter.value(calendar.get(11));
                        jsonWriter.name("minute");
                        jsonWriter.value(calendar.get(12));
                        jsonWriter.name("second");
                        jsonWriter.value(calendar.get(13));
                        jsonWriter.endObject();
                    }
                }
            };

    public static final TypeAdapterFactory CALENDAR_FACTORY =
            newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);

    public static final TypeAdapter<Locale> LOCALE =
            new TypeAdapter<Locale>() {

                public Locale read(JsonReader jsonReader) throws IOException {
                    if (jsonReader.peek() == JsonToken.NULL) {
                        jsonReader.nextNull();
                        return null;
                    }
                    String text = jsonReader.nextString();
                    StringTokenizer stringTokenizer = new StringTokenizer(text, "_");
                    String text2 = null;
                    String text3 = null;
                    String text4 = null;
                    if (stringTokenizer.hasMoreElements()) {
                        text2 = stringTokenizer.nextToken();
                    }
                    if (stringTokenizer.hasMoreElements()) {
                        text3 = stringTokenizer.nextToken();
                    }
                    if (stringTokenizer.hasMoreElements()) {
                        text4 = stringTokenizer.nextToken();
                    }
                    if (text3 == null && text4 == null) {
                        return new Locale(text2);
                    } else {
                        return text4 == null
                                ? new Locale(text2, text3)
                                : new Locale(text2, text3, text4);
                    }
                }

                public void write(JsonWriter jsonWriter, Locale locale) throws IOException {
                    jsonWriter.value(locale == null ? null : locale.toString());
                }
            };

    public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);

    public static final TypeAdapter<JsonElement> JSON_ELEMENT =
            new TypeAdapter<JsonElement>() {

                public JsonElement read(JsonReader jsonReader) throws IOException {
                    switch (jsonReader.peek()) {
                        case NUMBER:
                            String text = jsonReader.nextString();
                            return new JsonPrimitive(new LazilyParsedNumber(text));
                        case BOOLEAN:
                            return new JsonPrimitive(jsonReader.nextBoolean());
                        case STRING:
                            return new JsonPrimitive(jsonReader.nextString());
                        case NULL:
                            jsonReader.nextNull();
                            return JsonNull.INSTANCE;
                        case BEGIN_ARRAY:
                            JsonArray jsonArray = new JsonArray();
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                jsonArray.add(this.read(jsonReader));
                            }
                            jsonReader.endArray();
                            return jsonArray;
                        case BEGIN_OBJECT:
                            JsonObject jsonObject = new JsonObject();
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                jsonObject.add(jsonReader.nextName(), this.read(jsonReader));
                            }
                            jsonReader.endObject();
                            return jsonObject;
                        case END_DOCUMENT:
                        case NAME:
                        case END_OBJECT:
                        case END_ARRAY:
                        default:
                            throw new IllegalArgumentException();
                    }
                }

                public void write(JsonWriter jsonWriter, JsonElement jsonElement)
                        throws IOException {
                    if (jsonElement == null || jsonElement.isJsonNull()) {
                        jsonWriter.nullValue();
                    } else if (jsonElement.isJsonPrimitive()) {
                        JsonPrimitive jsonPrimitive;
                        if ((jsonPrimitive = jsonElement.getAsJsonPrimitive()).isNumber()) {
                            jsonWriter.value(jsonPrimitive.getAsNumber());
                        } else if (jsonPrimitive.isBoolean()) {
                            jsonWriter.value(jsonPrimitive.getAsBoolean());
                        } else {
                            jsonWriter.value(jsonPrimitive.getAsString());
                        }
                    } else if (jsonElement.isJsonArray()) {
                        jsonWriter.beginArray();
                        for (JsonElement jsonElement2 : jsonElement.getAsJsonArray()) {
                            this.write(jsonWriter, jsonElement2);
                        }
                        jsonWriter.endArray();
                    } else {
                        if (!jsonElement.isJsonObject()) {
                            throw new IllegalArgumentException(
                                    "Couldn't write " + jsonElement.getClass());
                        }
                        jsonWriter.beginObject();
                        for (Entry entry : jsonElement.getAsJsonObject().entrySet()) {
                            jsonWriter.name((String) entry.getKey());
                            this.write(jsonWriter, (JsonElement) entry.getValue());
                        }
                        jsonWriter.endObject();
                    }
                }
            };

    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY =
            newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);

    public static final TypeAdapterFactory ENUM_FACTORY =
            new TypeAdapterFactory() {

                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                    Class<? super T> rawType = typeToken.getRawType();
                    if (Enum.class.isAssignableFrom(rawType) && rawType != Enum.class) {
                        if (!rawType.isEnum()) {
                            rawType = rawType.getSuperclass();
                        }
                        return new TypeAdapters.EnumTypeAdapter(rawType);
                    } else {
                        return null;
                    }
                }
            };

    private TypeAdapters() {
        throw new UnsupportedOperationException();
    }

    public static <TT> TypeAdapterFactory newFactory(
            final TypeToken<TT> typeToken, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken2) {
                return (((TypeAdapter<T>) (typeToken2.equals(typeToken) ? typeAdapter : null)));
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> classValue, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                return (((TypeAdapter<T>)
                        (typeToken.getRawType() == classValue ? typeAdapter : null)));
            }

            @Override
            public String toString() {
                return "Factory[type=" + classValue.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> classValue,
            final Class<TT> classValue2,
            final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<? super T> classValue3;
                return (((TypeAdapter<T>)
                        ((classValue3 = typeToken.getRawType()) != classValue
                                        && classValue3 != classValue2
                                ? null
                                : typeAdapter)));
            }

            @Override
            public String toString() {
                return "Factory[type="
                        + classValue2.getName()
                        + "+"
                        + classValue.getName()
                        + ",adapter="
                        + typeAdapter
                        + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(
            final Class<TT> classValue,
            final Class<? extends TT> classValue2,
            final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                Class<? super T> classValue3;
                return (((TypeAdapter<T>)
                        ((classValue3 = typeToken.getRawType()) != classValue
                                        && classValue3 != classValue2
                                ? null
                                : typeAdapter)));
            }

            @Override
            public String toString() {
                return "Factory[type="
                        + classValue.getName()
                        + "+"
                        + classValue2.getName()
                        + ",adapter="
                        + typeAdapter
                        + "]";
            }
        };
    }

    public static <T1> TypeAdapterFactory newTypeHierarchyFactory(
            final Class<T1> classValue, final TypeAdapter<T1> typeAdapter) {
        return new TypeAdapterFactory() {

            @Override
            public <T2> TypeAdapter<T2> create(Gson result, TypeToken<T2> typeToken) {
                final Class<? super T2> rawType = typeToken.getRawType();
                return (TypeAdapter<T2>)
                        (!classValue.isAssignableFrom(rawType)
                                ? null
                                : new TypeAdapter<T1>() {

                                    @Override
                                    public void write(JsonWriter result, T1 t1) throws IOException {
                                        typeAdapter.write(result, t1);
                                    }

                                    @Override
                                    public T1 read(JsonReader result) throws IOException {
                                        Object result2;
                                        if ((result2 = typeAdapter.read(result)) != null
                                                && !rawType.isInstance(result2)) {
                                            throw new JsonSyntaxException(
                                                    "Expected a "
                                                            + rawType.getName()
                                                            + " but was "
                                                            + result2.getClass().getName());
                                        } else {
                                            return (T1) result2;
                                        }
                                    }
                                });
            }

            @Override
            public String toString() {
                return "Factory[typeHierarchy="
                        + classValue.getName()
                        + ",adapter="
                        + typeAdapter
                        + "]";
            }
        };
    }

    private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

        private final Map<String, T> nameToConstant = new HashMap<>();

        private final Map<T, String> constantToName = new HashMap<>();

        public EnumTypeAdapter(Class<T> classValue) {
            try {
                Enum[] enums;
                int value2 = (enums = (Enum[]) classValue.getEnumConstants()).length;
                for (int index = 0; index < value2; index++) {
                    Enum enumValue;
                    String text = (enumValue = enums[index]).name();
                    SerializedName serializedName;
                    if ((serializedName =
                                    classValue
                                            .getField(ReflectionNames.fieldName(classValue, text))
                                            .getAnnotation(SerializedName.class))
                            != null) {
                        text = serializedName.value();
                        String[] texts;
                        int value3 = (texts = serializedName.alternate()).length;
                        for (int index2 = 0; index2 < value3; index2++) {
                            String text2 = texts[index2];
                            this.nameToConstant.put(text2, (T) enumValue);
                        }
                    }
                    this.nameToConstant.put(text, (T) enumValue);
                    this.constantToName.put((T) enumValue, text);
                }
            } catch (NoSuchFieldException noSuchFieldException) {
                throw new AssertionError(noSuchFieldException);
            }
        }

        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            } else {
                return this.nameToConstant.get(jsonReader.nextString());
            }
        }

        public void write(JsonWriter jsonWriter, T t) throws IOException {
            jsonWriter.value(t == null ? null : this.constantToName.get(t));
        }
    }
}
