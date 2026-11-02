package it.ytnoos.lpx.lib.gson;

import it.ytnoos.lpx.lib.gson.internal.bind.util.ISO8601Utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DefaultDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat enUsFormat;

    private final DateFormat localFormat;

    DefaultDateTypeAdapter() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    DefaultDateTypeAdapter(String text) {
        this(new SimpleDateFormat(text, Locale.US), new SimpleDateFormat(text));
    }

    DefaultDateTypeAdapter(int value) {
        this(DateFormat.getDateInstance(value, Locale.US), DateFormat.getDateInstance(value));
    }

    public DefaultDateTypeAdapter(int value, int value2) {
        this(
                DateFormat.getDateTimeInstance(value, value2, Locale.US),
                DateFormat.getDateTimeInstance(value, value2));
    }

    DefaultDateTypeAdapter(DateFormat dateFormat, DateFormat dateFormat2) {
        this.enUsFormat = dateFormat;
        this.localFormat = dateFormat2;
    }

    public JsonElement serialize(
            Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        synchronized (this.localFormat) {
            String text = this.enUsFormat.format(date);
            return new JsonPrimitive(text);
        }
    }

    public Date deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        if (!(jsonElement instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        } else {
            Date date = this.deserializeToDate(jsonElement);
            if (type == Date.class) {
                return date;
            } else if (type == Timestamp.class) {
                return new Timestamp(date.getTime());
            } else if (type == java.sql.Date.class) {
                return new java.sql.Date(date.getTime());
            } else {
                throw new IllegalArgumentException(
                        this.getClass() + " cannot deserialize to " + type);
            }
        }
    }

    private Date deserializeToDate(JsonElement jsonElement) {
        synchronized (this.localFormat) {
            Date date;
            try {
                date = this.localFormat.parse(jsonElement.getAsString());
            } catch (ParseException parseException) {
                try {
                    date = this.enUsFormat.parse(jsonElement.getAsString());
                } catch (ParseException parseException2) {
                    try {
                        date = ISO8601Utils.parse(jsonElement.getAsString(), new ParsePosition(0));
                    } catch (ParseException parseException3) {
                        throw new JsonSyntaxException(jsonElement.getAsString(), parseException3);
                    }
                    return date;
                }
                return date;
            }
            return date;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder;
        (stringBuilder = new StringBuilder()).append(DefaultDateTypeAdapter.class.getSimpleName());
        stringBuilder.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}
