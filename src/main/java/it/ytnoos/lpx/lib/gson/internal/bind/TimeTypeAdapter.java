package it.ytnoos.lpx.lib.gson.internal.bind;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonSyntaxException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeTypeAdapter extends TypeAdapter<Time> {

    public static final TypeAdapterFactory FACTORY =
            new TypeAdapterFactory() {

                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                    return (((TypeAdapter<T>)
                            (typeToken.getRawType() == Time.class ? new TimeTypeAdapter() : null)));
                }
            };

    private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

    public synchronized Time read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        try {
            Date date = this.format.parse(jsonReader.nextString());
            return new Time(date.getTime());
        } catch (ParseException parseException) {
            throw new JsonSyntaxException(parseException);
        }
    }

    public synchronized void write(JsonWriter jsonWriter, Time time) throws IOException {
        jsonWriter.value(time == null ? null : this.format.format(time));
    }
}
