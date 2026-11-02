package it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson;

import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.TypeAdapterFactory;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.BlockNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.kyori.option.OptionState;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

final class SerializerFactory implements TypeAdapterFactory {

    static final Class<Key> KEY_TYPE = Key.class;

    static final Class<Component> COMPONENT_TYPE = Component.class;

    static final Class<Style> STYLE_TYPE = Style.class;

    static final Class<ClickEvent.Action> CLICK_ACTION_TYPE = ClickEvent.Action.class;

    static final Class<HoverEvent.Action> HOVER_ACTION_TYPE = HoverEvent.Action.class;

    static final Class<HoverEvent.ShowItem> SHOW_ITEM_TYPE = HoverEvent.ShowItem.class;

    static final Class<HoverEvent.ShowEntity> SHOW_ENTITY_TYPE = HoverEvent.ShowEntity.class;

    static final Class<String> STRING_TYPE = String.class;

    static final Class<TextColorWrapper> COLOR_WRAPPER_TYPE = TextColorWrapper.class;

    static final Class<TextColor> COLOR_TYPE = TextColor.class;

    static final Class<ShadowColor> SHADOW_COLOR_TYPE = ShadowColor.class;

    static final Class<TextDecoration> TEXT_DECORATION_TYPE = TextDecoration.class;

    static final Class<BlockNBTComponent.Pos> BLOCK_NBT_POS_TYPE = BlockNBTComponent.Pos.class;

    static final Class<UUID> UUID_TYPE = UUID.class;

    static final Class<TranslationArgument> TRANSLATION_ARGUMENT_TYPE = TranslationArgument.class;

    static final Class<PlayerHeadObjectContents.ProfileProperty> PROFILE_PROPERTY_TYPE =
            PlayerHeadObjectContents.ProfileProperty.class;

    private final OptionState features;

    private final it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer
            legacyHoverSerializer;

    SerializerFactory(
            OptionState optionState,
            @Nullable
                    it.ytnoos.lpx.lib.kyori.adventure.text.serializer.json
                                    .LegacyHoverEventSerializer
                            legacyHoverEventSerializer) {
        this.features = optionState;
        this.legacyHoverSerializer = legacyHoverEventSerializer;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson2, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        if (COMPONENT_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ComponentSerializerImpl.create(this.features, gson2);
        } else if (KEY_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) KeySerializer.INSTANCE;
        } else if (STYLE_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>)
                    StyleSerializer.create(
                            ((it.ytnoos.lpx.lib.kyori.adventure.text.serializer.gson
                                            .LegacyHoverEventSerializer)
                                    this.legacyHoverSerializer),
                            this.features,
                            gson2);
        } else if (CLICK_ACTION_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ClickEventActionSerializer.INSTANCE;
        } else if (HOVER_ACTION_TYPE.isAssignableFrom(rawType)) {
            return ((TypeAdapter<T>) HoverEventActionSerializer.INSTANCE);
        } else if (SHOW_ITEM_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ShowItemSerializer.create(gson2, this.features);
        } else if (SHOW_ENTITY_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ShowEntitySerializer.create(gson2, this.features);
        } else if (COLOR_WRAPPER_TYPE.isAssignableFrom(rawType)) {
            return ((TypeAdapter<T>) TextColorWrapper.Serializer.INSTANCE);
        } else if (COLOR_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>)
                    (this.features.value(JSONOptions.EMIT_RGB)
                            ? TextColorSerializer.INSTANCE
                            : TextColorSerializer.DOWNSAMPLE_COLOR);
        } else if (SHADOW_COLOR_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) ShadowColorSerializer.create(this.features);
        } else if (TEXT_DECORATION_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) TextDecorationSerializer.INSTANCE;
        } else if (BLOCK_NBT_POS_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) BlockNBTComponentPosSerializer.INSTANCE;
        } else if (UUID_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) UUIDSerializer.uuidSerializer(this.features);
        } else if (TRANSLATION_ARGUMENT_TYPE.isAssignableFrom(rawType)) {
            return (TypeAdapter<T>) TranslationArgumentSerializer.create(gson2);
        } else {
            return (TypeAdapter<T>)
                    (PROFILE_PROPERTY_TYPE.isAssignableFrom(rawType)
                            ? ProfilePropertySerializer.INSTANCE
                            : null);
        }
    }
}
