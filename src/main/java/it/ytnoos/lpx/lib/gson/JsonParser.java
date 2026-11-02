package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.Streams;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {

    public JsonElement parse(String text) throws JsonSyntaxException {
        return this.parse(new StringReader(text));
    }

    public JsonElement parse(Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement jsonElement;
            if (!(jsonElement = this.parse(jsonReader)).isJsonNull()
                    && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            } else {
                return jsonElement;
            }
        } catch (MalformedJsonException malformedJsonException) {
            throw new JsonSyntaxException(malformedJsonException);
        } catch (IOException oException) {
            throw new JsonIOException(oException);
        } catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
        }
    }

    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        boolean lenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            return Streams.parse(jsonReader);
        } catch (StackOverflowError stackOverflowError) {
            throw new JsonParseException(
                    "Failed parsing JSON source: " + jsonReader + " to Json", stackOverflowError);
        } catch (OutOfMemoryError outOfMemoryError) {
            throw new JsonParseException(
                    "Failed parsing JSON source: " + jsonReader + " to Json", outOfMemoryError);
        } finally {
            jsonReader.setLenient(lenient);
        }
    }
}
