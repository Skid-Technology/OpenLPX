package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;

final class ClickEventActionSerializer {

    static final TypeAdapter<ClickEvent.Action> INSTANCE =
            IndexedSerializer.lenient("click action", ClickEvent.Action.NAMES);

    private ClickEventActionSerializer() {}
}
