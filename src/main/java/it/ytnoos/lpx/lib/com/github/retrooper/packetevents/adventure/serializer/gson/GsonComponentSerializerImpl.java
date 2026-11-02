package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.GsonBuilder;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

final class GsonComponentSerializerImpl implements GsonComponentSerializer {

    private static final Optional<GsonComponentSerializer.Provider> SERVICE =
            Services.service(GsonComponentSerializer.Provider.class);

    static final Consumer<GsonComponentSerializer.Builder> BUILDER =
            SERVICE.map(GsonComponentSerializer.Provider::builder).orElseGet(() -> result -> {});

    private final Gson serializer;

    private final UnaryOperator<GsonBuilder> populator;

    @Nullable
    private final it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json
                    .LegacyHoverEventSerializer
            legacyHoverSerializer;

    private final BackwardCompatUtil.@Nullable ShowAchievementToComponent compatShowAchievement;

    private final OptionState flags;

    GsonComponentSerializerImpl(
            OptionState optionState,
            @Nullable
                    it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json
                                    .LegacyHoverEventSerializer
                            legacyHoverEventSerializer,
            BackwardCompatUtil.@Nullable ShowAchievementToComponent showAchievementToComponent) {
        this.flags = optionState;
        this.legacyHoverSerializer = legacyHoverEventSerializer;
        this.compatShowAchievement = showAchievementToComponent;
        this.populator =
                result -> {
                    result.registerTypeAdapterFactory(
                            new SerializerFactory(
                                    optionState,
                                    legacyHoverEventSerializer,
                                    showAchievementToComponent));
                    return result;
                };
        this.serializer = this.populator.apply(new GsonBuilder().disableHtmlEscaping()).create();
    }

    @NotNull
    @Override
    public Gson serializer() {
        return this.serializer;
    }

    @NotNull
    @Override
    public UnaryOperator<GsonBuilder> populator() {
        return this.populator;
    }

    @NotNull
    public Component deserialize(@NotNull String text) {
        Component component;
        if ((component = this.serializer().fromJson(text, Component.class)) == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(text);
        } else {
            return component;
        }
    }

    @Nullable
    public Component deserializeOr(@Nullable String text, @Nullable Component component) {
        if (text == null) {
            return component;
        }
        Component component2;
        return (component2 = this.serializer().fromJson(text, Component.class)) == null
                ? component
                : component2;
    }

    @NotNull
    public String serialize(@NotNull Component component) {
        return this.serializer().toJson(component);
    }

    @NotNull
    @Override
    public Component deserializeFromTree(@NotNull JsonElement jsonElement) {
        Component component;
        if ((component = this.serializer().fromJson(jsonElement, Component.class)) == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(jsonElement);
        } else {
            return component;
        }
    }

    @NotNull
    @Override
    public JsonElement serializeToTree(@NotNull Component component) {
        return this.serializer().toJsonTree(component);
    }

    @NotNull
    public GsonComponentSerializer.Builder toBuilder() {
        return new GsonComponentSerializerImpl.BuilderImpl(this);
    }

    static final class BuilderImpl implements GsonComponentSerializer.Builder {

        private OptionState flags = JSONOptions.byDataVersion();

        @Nullable
        private it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json
                        .LegacyHoverEventSerializer
                legacyHoverSerializer;

        private BackwardCompatUtil.@Nullable ShowAchievementToComponent compatShowAchievement;

        BuilderImpl() {
            GsonComponentSerializerImpl.BUILDER.accept(this);
        }

        BuilderImpl(GsonComponentSerializerImpl gsonComponentSerializerImpl) {
            this();
            this.flags = gsonComponentSerializerImpl.flags;
            this.legacyHoverSerializer = gsonComponentSerializerImpl.legacyHoverSerializer;
            this.compatShowAchievement = gsonComponentSerializerImpl.compatShowAchievement;
        }

        @NotNull
        @Override
        public GsonComponentSerializer.Builder options(@NotNull OptionState optionState) {
            this.flags = Objects.requireNonNull(optionState, "flags");
            return this;
        }

        @NotNull
        @Override
        public GsonComponentSerializer.Builder editOptions(
                @NotNull Consumer<OptionState.Builder> consumer) {
            OptionState.Builder builder = JSONOptions.schema().stateBuilder().values(this.flags);
            Objects.requireNonNull(consumer, "flagEditor").accept(builder);
            this.flags = builder.build();
            return this;
        }

        @NotNull
        @Override
        public GsonComponentSerializer.Builder legacyHoverEventSerializer(
                @Nullable
                        it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer
                                        .json.LegacyHoverEventSerializer
                                legacyHoverEventSerializer2) {
            this.legacyHoverSerializer = legacyHoverEventSerializer2;
            return this;
        }

        @NotNull
        @Override
        public GsonComponentSerializer.Builder showAchievementToComponent(
                BackwardCompatUtil.@Nullable ShowAchievementToComponent
                        showAchievementToComponent2) {
            this.compatShowAchievement = showAchievementToComponent2;
            return this;
        }

        @NotNull
        @Override
        public GsonComponentSerializer build() {
            return new GsonComponentSerializerImpl(
                    this.flags, this.legacyHoverSerializer, this.compatShowAchievement);
        }
    }

    static final class Instances {

        static final GsonComponentSerializer INSTANCE =
                GsonComponentSerializerImpl.SERVICE
                        .map(GsonComponentSerializer.Provider::gson)
                        .orElseGet(
                                () ->
                                        new GsonComponentSerializerImpl(
                                                JSONOptions.byDataVersion(), null, null));

        static final GsonComponentSerializer LEGACY_INSTANCE =
                GsonComponentSerializerImpl.SERVICE
                        .map(GsonComponentSerializer.Provider::gsonLegacy)
                        .orElseGet(
                                () ->
                                        new GsonComponentSerializerImpl(
                                                JSONOptions.byDataVersion().at(2525), null, null));
    }
}
