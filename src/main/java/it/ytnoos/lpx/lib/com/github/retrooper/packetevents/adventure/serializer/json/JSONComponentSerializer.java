package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.ComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface JSONComponentSerializer extends ComponentSerializer<Component, Component, String> {

    @NotNull
    static JSONComponentSerializer json() {
        return JSONComponentSerializerAccessor.Instances.INSTANCE;
    }

    static JSONComponentSerializer.@NotNull Builder builder() {
        return JSONComponentSerializerAccessor.Instances.BUILDER_SUPPLIER.get();
    }

    interface Builder {

        @NotNull
        JSONComponentSerializer.Builder options(@NotNull OptionState optionState);

        @NotNull
        JSONComponentSerializer.Builder editOptions(
                @NotNull Consumer<OptionState.Builder> consumer);

        @Deprecated
        @NotNull
        JSONComponentSerializer.Builder downsampleColors();

        @NotNull
        JSONComponentSerializer.Builder legacyHoverEventSerializer(
                @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer2);

        @Deprecated
        @NotNull
        JSONComponentSerializer.Builder emitLegacyHoverEvent();

        @NotNull
        JSONComponentSerializer build();
    }

    @PlatformAPI
    @Internal
    interface Provider {

        @PlatformAPI
        @Internal
        @NotNull
        JSONComponentSerializer instance();

        @PlatformAPI
        @Internal
        @NotNull
        Supplier<JSONComponentSerializer.Builder> builder();
    }
}
