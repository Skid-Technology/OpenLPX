package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;

import java.io.IOException;

final class ProfilePropertySerializer
        extends TypeAdapter<PlayerHeadObjectContents.ProfileProperty> {

    static final TypeAdapter<PlayerHeadObjectContents.ProfileProperty> INSTANCE =
            new ProfilePropertySerializer().nullSafe();

    private ProfilePropertySerializer() {}

    public void write(
            JsonWriter jsonWriter, PlayerHeadObjectContents.ProfileProperty profileProperty)
            throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name");
        jsonWriter.value(profileProperty.name());
        jsonWriter.name("value");
        jsonWriter.value(profileProperty.value());
        if (profileProperty.signature() != null) {
            jsonWriter.name("signature");
            jsonWriter.value(profileProperty.signature());
        }
        jsonWriter.endObject();
    }

    public PlayerHeadObjectContents.ProfileProperty read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String text = null;
        String text2 = null;
        String text3 = null;
        while (jsonReader.hasNext()) {
            String text4;
            if ((text4 = jsonReader.nextName()).equals("name")) {
                text = jsonReader.nextString();
            } else if (text4.equals("value")) {
                text2 = jsonReader.nextString();
            } else if (text4.equals("signature")) {
                text3 = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        if (text != null && text2 != null) {
            return PlayerHeadObjectContents.property(text, text2, text3);
        } else {
            throw new JsonParseException("A profile property requires both a name and value");
        }
    }
}
