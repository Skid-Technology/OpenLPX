package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@NonExtendable
public interface GsonDataComponentValue extends DataComponentValue {

    static GsonDataComponentValue gsonDataComponentValue(@NotNull JsonElement jsonElement) {
        return jsonElement instanceof JsonNull
                ? GsonDataComponentValueImpl.RemovedGsonComponentValueImpl.INSTANCE
                : new GsonDataComponentValueImpl(Objects.requireNonNull(jsonElement, "data"));
    }

    @NotNull
    JsonElement element();
}
