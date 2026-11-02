package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson.impl;

import com.google.auto.service.AutoService;

import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@Internal
@AutoService(JSONComponentSerializer.Provider.class)
public final class JSONComponentSerializerProviderImpl
        implements JSONComponentSerializer.Provider, Services.Fallback {

    @NotNull
    @Override
    public JSONComponentSerializer instance() {
        return GsonComponentSerializer.gson();
    }

    @NotNull
    @Override
    public Supplier<JSONComponentSerializer.Builder> builder() {
        return GsonComponentSerializer::builder;
    }

    @Override
    public String toString() {
        return "JSONComponentSerializerProviderImpl[GsonComponentSerializer]";
    }
}
