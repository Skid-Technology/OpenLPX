package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.bind.JsonTreeReader;
import it.ytnoos.lpx.lib.gson.internal.bind.JsonTreeWriter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class TypeAdapter<T> {

    public abstract void write(JsonWriter jsonWriter, T t) throws IOException;

    public final void toJson(Writer writer, T t) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(writer);
        this.write(jsonWriter, (T) t);
    }

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter result, T t) throws IOException {
                if (t == null) {
                    result.nullValue();
                } else {
                    TypeAdapter.this.write(result, t);
                }
            }

            @Override
            public T read(JsonReader result) throws IOException {
                if (result.peek() == JsonToken.NULL) {
                    result.nextNull();
                    return null;
                } else {
                    return (T) TypeAdapter.this.read(result);
                }
            }
        };
    }

    public final String toJson(T t) {
        StringWriter stringWriter = new StringWriter();
        try {
            this.toJson(stringWriter, (T) t);
        } catch (IOException oException) {
            throw new AssertionError(oException);
        }
        return stringWriter.toString();
    }

    public final JsonElement toJsonTree(T t) {
        try {
            JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
            this.write(jsonTreeWriter, (T) t);
            return jsonTreeWriter.get();
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        }
    }

    public abstract T read(JsonReader jsonReader) throws IOException;

    public final T fromJson(Reader reader) throws IOException {
        JsonReader jsonReader = new JsonReader(reader);
        return this.read(jsonReader);
    }

    public final T fromJson(String text) throws IOException {
        return this.fromJson(new StringReader(text));
    }

    public final T fromJsonTree(JsonElement jsonElement) {
        try {
            JsonTreeReader jsonTreeReader = new JsonTreeReader(jsonElement);
            return this.read(jsonTreeReader);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        }
    }
}
