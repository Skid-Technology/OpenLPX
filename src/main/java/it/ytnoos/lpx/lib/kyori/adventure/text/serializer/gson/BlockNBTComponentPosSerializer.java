package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.text.BlockNBTComponent;

import java.io.IOException;

final class BlockNBTComponentPosSerializer extends TypeAdapter<BlockNBTComponent.Pos> {

    static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE =
            new BlockNBTComponentPosSerializer().nullSafe();

    private BlockNBTComponentPosSerializer() {}

    public BlockNBTComponent.Pos read(JsonReader jsonReader) throws IOException {
        String text = jsonReader.nextString();
        try {
            return BlockNBTComponent.Pos.fromString(text);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JsonParseException("Don't know how to turn " + text + " into a Position");
        }
    }

    public void write(JsonWriter jsonWriter, BlockNBTComponent.Pos pos) throws IOException {
        jsonWriter.value(pos.asString());
    }
}
