package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.internal.bind.util.ISO8601Utils;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter<Date> {

    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() {

                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                    return (((TypeAdapter<T>)
                            (typeToken.getRawType() == Date.class ? new DateTypeAdapter() : null)));
                }
            };

    private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);

    private final DateFormat localFormat = DateFormat.getDateTimeInstance(2, 2);

    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return this.deserializeToDate(jsonReader.nextString());
        }
    }

    private synchronized Date deserializeToDate(String text) {
        try {
            return this.localFormat.parse(text);
        } catch (ParseException parseException) {
            try {
                return this.enUsFormat.parse(text);
            } catch (ParseException parseException2) {
                try {
                    return ISO8601Utils.parse(text, new ParsePosition(0));
                } catch (ParseException parseException3) {
                    throw new JsonSyntaxException(text, parseException3);
                }
            }
        }
    }

    public synchronized void write(JsonWriter jsonWriter, Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
        } else {
            String text = this.enUsFormat.format(date);
            jsonWriter.value(text);
        }
    }
}
