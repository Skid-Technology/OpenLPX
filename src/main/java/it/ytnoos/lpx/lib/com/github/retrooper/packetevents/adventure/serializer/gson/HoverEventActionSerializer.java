package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;

final class HoverEventActionSerializer {

    static final TypeAdapter<HoverEvent.Action<?>> INSTANCE =
            IndexedSerializer.lenient("hover action", HoverEvent.Action.NAMES);

    private HoverEventActionSerializer() {}
}
