package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;

final class TextDecorationSerializer {

    static final TypeAdapter<TextDecoration> INSTANCE =
            IndexedSerializer.strict("text decoration", TextDecoration.NAMES);

    private TextDecorationSerializer() {}
}
