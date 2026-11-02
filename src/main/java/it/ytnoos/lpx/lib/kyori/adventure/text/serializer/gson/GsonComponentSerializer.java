package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.GsonBuilder;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.kyori.adventure.builder.AbstractBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.kyori.adventure.util.Buildable;
import it.ytnoos.lpx.lib.kyori.adventure.util.PlatformAPI;
import it.ytnoos.lpx.lib.kyori.option.OptionState;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface GsonComponentSerializer
        extends JSONComponentSerializer,
                Buildable<GsonComponentSerializer, GsonComponentSerializer.Builder> {

    @NotNull
    static GsonComponentSerializer gson() {
        return GsonComponentSerializerImpl.Instances.INSTANCE;
    }

    @NotNull
    static GsonComponentSerializer colorDownsamplingGson() {
        return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
    }

    static GsonComponentSerializer.Builder builder() {
        return new GsonComponentSerializerImpl.BuilderImpl();
    }

    @NotNull
    Gson serializer();

    @NotNull
    UnaryOperator<GsonBuilder> populator();

    @NotNull
    Component deserializeFromTree(@NotNull JsonElement jsonElement);

    @NotNull
    JsonElement serializeToTree(@NotNull Component component);

    interface Builder
            extends AbstractBuilder<GsonComponentSerializer>,
                    Buildable.Builder<GsonComponentSerializer>,
                    JSONComponentSerializer.Builder {

        @NotNull
        GsonComponentSerializer.Builder options(@NotNull OptionState optionState);

        @NotNull
        GsonComponentSerializer.Builder editOptions(
                @NotNull Consumer<OptionState.Builder> consumer);

        @NotNull
        default GsonComponentSerializer.Builder downsampleColors() {
            return this.editOptions(result -> result.value(JSONOptions.EMIT_RGB, false));
        }

        @Deprecated
        @NotNull
        default GsonComponentSerializer.Builder legacyHoverEventSerializer(
                @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer2) {
            return this.legacyHoverEventSerializer(
                    (it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json
                                    .LegacyHoverEventSerializer)
                            legacyHoverEventSerializer2);
        }

        @NotNull
        GsonComponentSerializer.Builder legacyHoverEventSerializer(
                @Nullable
                        it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json
                                        .LegacyHoverEventSerializer
                                legacyHoverEventSerializer2);

        @Deprecated
        @NotNull
        default GsonComponentSerializer.Builder emitLegacyHoverEvent() {
            return this.editOptions(
                    result ->
                            result.value(
                                    JSONOptions.EMIT_HOVER_EVENT_TYPE,
                                    JSONOptions.HoverEventValueMode.ALL));
        }

        @NotNull
        GsonComponentSerializer build();
    }

    @PlatformAPI
    @Internal
    interface Provider {

        @PlatformAPI
        @Internal
        @NotNull
        GsonComponentSerializer gson();

        @PlatformAPI
        @Internal
        @NotNull
        GsonComponentSerializer gsonLegacy();

        @PlatformAPI
        @Internal
        @NotNull
        Consumer<GsonComponentSerializer.Builder> builder();
    }
}
