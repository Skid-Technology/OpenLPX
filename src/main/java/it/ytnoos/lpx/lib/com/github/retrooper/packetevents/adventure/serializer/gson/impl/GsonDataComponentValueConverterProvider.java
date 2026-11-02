package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.impl;

import com.google.auto.service.AutoService;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonDataComponentValue;
import it.ytnoos.lpx.lib.gson.JsonNull;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValueConverterRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

@AutoService(DataComponentValueConverterRegistry.Provider.class)
@Internal
public final class GsonDataComponentValueConverterProvider
        implements DataComponentValueConverterRegistry.Provider {

    private static final Key ID = Key.key("adventure", "serializer/gson");

    @NotNull
    @Override
    public Key id() {
        return ID;
    }

    @NotNull
    @Override
    public Iterable<DataComponentValueConverterRegistry.Conversion<?, ?>> conversions() {
        return Collections.singletonList(
                DataComponentValueConverterRegistry.Conversion.convert(
                        DataComponentValue.Removed.class,
                        GsonDataComponentValue.class,
                        (result, result2) ->
                                GsonDataComponentValue.gsonDataComponentValue(JsonNull.INSTANCE)));
    }
}
