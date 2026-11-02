package it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.option.OptionState;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.json.JSONOptions;
import it.ytnoos.lpx.lib.gson.Gson;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.gson.JsonObject;
import it.ytnoos.lpx.lib.gson.JsonParseException;
import it.ytnoos.lpx.lib.gson.TypeAdapter;
import it.ytnoos.lpx.lib.gson.reflect.TypeToken;
import it.ytnoos.lpx.lib.gson.stream.JsonReader;
import it.ytnoos.lpx.lib.gson.stream.JsonToken;
import it.ytnoos.lpx.lib.gson.stream.JsonWriter;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.BlockNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.EntityNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.NBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.NBTComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.ObjectComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.ScoreComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.SelectorComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.StorageNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.SpriteObjectContents;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

final class ComponentSerializerImpl extends TypeAdapter<Component> {

    static final Type COMPONENT_LIST_TYPE = (new TypeToken<List<Component>>() {}).getType();

    static final Type TRANSLATABLE_ARGUMENT_LIST_TYPE =
            (new TypeToken<List<TranslationArgument>>() {}).getType();

    static final Type PROPERTY_LIST_TYPE =
            (new TypeToken<List<PlayerHeadObjectContents.ProfileProperty>>() {}).getType();

    private final boolean emitCompactTextComponent;

    private final Gson gson;

    static TypeAdapter<Component> create(OptionState optionState, Gson gson) {
        return new ComponentSerializerImpl(
                        optionState.value(JSONOptions.EMIT_COMPACT_TEXT_COMPONENT), gson)
                .nullSafe();
    }

    private ComponentSerializerImpl(boolean emitCompactTextComponent, Gson gson2) {
        this.emitCompactTextComponent = emitCompactTextComponent;
        this.gson = gson2;
    }

    public Component read(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken;
        if ((jsonToken = jsonReader.peek()) != JsonToken.STRING
                && jsonToken != JsonToken.NUMBER
                && jsonToken != JsonToken.BOOLEAN) {
            if (jsonToken == JsonToken.BEGIN_ARRAY) {
                ComponentBuilder componentBuilder = null;
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Component component = this.read(jsonReader);
                    if (componentBuilder == null) {
                        componentBuilder = BackwardCompatUtil.toBuilder(component);
                    } else {
                        componentBuilder.append(component);
                    }
                }
                if (componentBuilder == null) {
                    throw notSureHowToDeserialize(jsonReader.getPath());
                }
                jsonReader.endArray();
                return BackwardCompatUtil.build(componentBuilder);
            } else {
                if (jsonToken != JsonToken.BEGIN_OBJECT) {
                    throw notSureHowToDeserialize(jsonReader.getPath());
                }
                JsonObject jsonObject = new JsonObject();
                List values = Collections.emptyList();
                String text2 = null;
                String text3 = null;
                String text4 = null;
                List values2 = null;
                String text5 = null;
                String text6 = null;
                String text7 = null;
                String text8 = null;
                String text9 = null;
                String text10 = null;
                boolean matches = false;
                BlockNBTComponent.Pos pos2 = null;
                String text11 = null;
                Key key2 = null;
                Component component2 = null;
                Key key3 = null;
                Key key4 = null;
                PlayerHeadObjectContents.Builder builder = null;
                boolean matches2 = false;
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String text12;
                    if ((text12 = jsonReader.nextName()).equals("text")) {
                        text2 = GsonHacks.readString(jsonReader);
                    } else if (text12.equals("translate")) {
                        text3 = jsonReader.nextString();
                    } else if (text12.equals("fallback")) {
                        text4 = jsonReader.nextString();
                    } else if (!text12.equals("with")) {
                        if (text12.equals("score")) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {
                                String text13;
                                if ((text13 = jsonReader.nextName()).equals("name")) {
                                    text5 = jsonReader.nextString();
                                } else if (text13.equals("objective")) {
                                    text6 = jsonReader.nextString();
                                } else if (text13.equals("value")) {
                                    text7 = jsonReader.nextString();
                                } else {
                                    jsonReader.skipValue();
                                }
                            }
                            if (text5 == null || text6 == null) {
                                throw new JsonParseException(
                                        "A score component requires a name and objective");
                            }
                            jsonReader.endObject();
                        } else if (text12.equals("selector")) {
                            text8 = jsonReader.nextString();
                        } else if (text12.equals("keybind")) {
                            text9 = jsonReader.nextString();
                        } else if (text12.equals("nbt")) {
                            text10 = jsonReader.nextString();
                        } else if (text12.equals("interpret")) {
                            matches = jsonReader.nextBoolean();
                        } else if (text12.equals("block")) {
                            pos2 =
                                    this.gson.fromJson(
                                            jsonReader, SerializerFactory.BLOCK_NBT_POS_TYPE);
                        } else if (text12.equals("entity")) {
                            text11 = jsonReader.nextString();
                        } else if (text12.equals("storage")) {
                            key2 = this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
                        } else if (text12.equals("extra")) {
                            values = this.gson.fromJson(jsonReader, COMPONENT_LIST_TYPE);
                        } else if (text12.equals("separator")) {
                            component2 = this.read(jsonReader);
                        } else if (text12.equals("atlas")) {
                            key3 = this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
                        } else if (text12.equals("sprite")) {
                            key4 = this.gson.fromJson(jsonReader, SerializerFactory.KEY_TYPE);
                        } else if (BackwardCompatUtil.IS_4_25_0_OR_NEWER
                                && text12.equals("player")) {
                            PlayerHeadObjectContents.Builder builder2;
                            if ((builder2 = builder) == null) {
                                builder2 = ObjectContents.playerHead();
                            }
                            builder = builder2;
                            JsonToken jsonToken2;
                            if ((jsonToken2 = jsonReader.peek()) == JsonToken.STRING) {
                                matches2 = true;
                                builder2.name(jsonReader.nextString());
                            } else if (jsonToken2 != JsonToken.BEGIN_OBJECT) {
                                jsonReader.skipValue();
                            } else {
                                matches2 = true;
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String text14;
                                    if ((text14 = jsonReader.nextName()).equals("name")) {
                                        builder2.name(jsonReader.nextString());
                                    } else if (text14.equals("id")) {
                                        builder2.id(
                                                this.gson.fromJson(
                                                        jsonReader, SerializerFactory.UUID_TYPE));
                                    } else if (!text14.equals("properties")) {
                                        if (text14.equals("texture")) {
                                            builder2.texture(
                                                    this.gson.fromJson(
                                                            jsonReader,
                                                            SerializerFactory.KEY_TYPE));
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                    } else {
                                        JsonToken jsonToken3;
                                        if ((jsonToken3 = jsonReader.peek())
                                                == JsonToken.BEGIN_ARRAY) {
                                            builder2.profileProperties(
                                                    this.gson.fromJson(
                                                            jsonReader, PROPERTY_LIST_TYPE));
                                        } else if (jsonToken3 != JsonToken.BEGIN_OBJECT) {
                                            jsonReader.skipValue();
                                        } else {
                                            jsonReader.beginObject();
                                            while (jsonReader.hasNext()) {
                                                String text15 = jsonReader.nextName();
                                                jsonReader.beginArray();
                                                while (jsonReader.hasNext()) {
                                                    builder2.profileProperty(
                                                            PlayerHeadObjectContents.property(
                                                                    text15,
                                                                    jsonReader.nextString()));
                                                }
                                                jsonReader.endArray();
                                            }
                                            jsonReader.endObject();
                                        }
                                    }
                                }
                                jsonReader.endObject();
                            }
                        } else if (BackwardCompatUtil.IS_4_25_0_OR_NEWER && text12.equals("hat")) {
                            if (builder == null) {
                                builder = ObjectContents.playerHead();
                            }
                            builder.hat(jsonReader.nextBoolean());
                        } else {
                            jsonObject.add(
                                    text12, this.gson.fromJson(jsonReader, JsonElement.class));
                        }
                    } else if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                        values2 = this.gson.fromJson(jsonReader, TRANSLATABLE_ARGUMENT_LIST_TYPE);
                    } else {
                        values2 = this.gson.fromJson(jsonReader, COMPONENT_LIST_TYPE);
                    }
                }
                ComponentBuilder componentBuilder2;
                if (text2 != null) {
                    componentBuilder2 = Component.text().content(text2);
                } else if (text3 != null) {
                    TranslatableComponent.Builder builder3;
                    componentBuilder2 = builder3 = Component.translatable().key(text3);
                    if (values2 != null) {
                        if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                            builder3.arguments(values2);
                        } else {
                            builder3.args(values2);
                        }
                    }
                    if (BackwardCompatUtil.IS_4_13_0_OR_NEWER) {
                        builder3.fallback(text4);
                    }
                } else if (text5 != null && text6 != null) {
                    if (text7 == null) {
                        componentBuilder2 = Component.score().name(text5).objective(text6);
                    } else {
                        componentBuilder2 =
                                Component.score().name(text5).objective(text6).value(text7);
                    }
                } else if (text8 != null) {
                    componentBuilder2 = Component.selector().pattern(text8).separator(component2);
                } else if (text9 != null) {
                    componentBuilder2 = Component.keybind().keybind(text9);
                } else if (text10 != null) {
                    if (pos2 != null) {
                        componentBuilder2 =
                                ((BlockNBTComponent.Builder)
                                                nbt(
                                                        Component.blockNBT(),
                                                        text10,
                                                        matches,
                                                        component2))
                                        .pos(pos2);
                    } else if (text11 != null) {
                        componentBuilder2 =
                                ((EntityNBTComponent.Builder)
                                                nbt(
                                                        Component.entityNBT(),
                                                        text10,
                                                        matches,
                                                        component2))
                                        .selector(text11);
                    } else {
                        if (key2 == null) {
                            throw notSureHowToDeserialize(jsonReader.getPath());
                        }
                        componentBuilder2 =
                                ((StorageNBTComponent.Builder)
                                                nbt(
                                                        Component.storageNBT(),
                                                        text10,
                                                        matches,
                                                        component2))
                                        .storage(key2);
                    }
                } else if (key4 != null) {
                    componentBuilder2 =
                            Component.object()
                                    .contents(
                                            ObjectContents.sprite(
                                                    key3 != null
                                                            ? key3
                                                            : SpriteObjectContents.DEFAULT_ATLAS,
                                                    key4));
                } else {
                    if (builder == null || !matches2) {
                        throw notSureHowToDeserialize(jsonReader.getPath());
                    }
                    componentBuilder2 = Component.object().contents(builder.build());
                }
                componentBuilder2
                        .style(this.gson.fromJson(jsonObject, SerializerFactory.STYLE_TYPE))
                        .append(values);
                jsonReader.endObject();
                return BackwardCompatUtil.build(componentBuilder2);
            }
        } else {
            return Component.text(GsonHacks.readString(jsonReader));
        }
    }

    private static <C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> B nbt(
            B b, String text, boolean matches, @Nullable Component component) {
        return (B) b.nbtPath(text).interpret(matches).separator(component);
    }

    public void write(JsonWriter jsonWriter, Component component) throws IOException {
        if (component instanceof TextComponent
                && component.children().isEmpty()
                && !component.hasStyling()
                && this.emitCompactTextComponent) {
            jsonWriter.value(((TextComponent) component).content());
        } else {
            jsonWriter.beginObject();
            JsonElement jsonElement;
            if (component.hasStyling()
                    && (jsonElement =
                                    this.gson.toJsonTree(
                                            component.style(), SerializerFactory.STYLE_TYPE))
                            .isJsonObject()) {
                for (Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    jsonWriter.name((String) entry.getKey());
                    this.gson.toJson((JsonElement) entry.getValue(), jsonWriter);
                }
            }
            if (!component.children().isEmpty()) {
                jsonWriter.name("extra");
                this.gson.toJson(component.children(), COMPONENT_LIST_TYPE, jsonWriter);
            }
            if (component instanceof TextComponent) {
                jsonWriter.name("text");
                jsonWriter.value(((TextComponent) component).content());
            } else if (component instanceof TranslatableComponent) {
                TranslatableComponent translatableComponent = (TranslatableComponent) component;
                jsonWriter.name("translate");
                jsonWriter.value(translatableComponent.key());
                String text2;
                if (BackwardCompatUtil.IS_4_13_0_OR_NEWER
                        && (text2 = translatableComponent.fallback()) != null) {
                    jsonWriter.name("fallback");
                    jsonWriter.value(text2);
                }
                boolean matches;
                if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                    matches = !translatableComponent.arguments().isEmpty();
                } else {
                    matches = !translatableComponent.args().isEmpty();
                }
                if (matches) {
                    jsonWriter.name("with");
                    if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                        this.gson.toJson(
                                translatableComponent.arguments(),
                                TRANSLATABLE_ARGUMENT_LIST_TYPE,
                                jsonWriter);
                    } else {
                        this.gson.toJson(
                                translatableComponent.args(), COMPONENT_LIST_TYPE, jsonWriter);
                    }
                }
            } else if (component instanceof ScoreComponent) {
                ScoreComponent scoreComponent = (ScoreComponent) component;
                jsonWriter.name("score");
                jsonWriter.beginObject();
                jsonWriter.name("name");
                jsonWriter.value(scoreComponent.name());
                jsonWriter.name("objective");
                jsonWriter.value(scoreComponent.objective());
                if (scoreComponent.value() != null) {
                    jsonWriter.name("value");
                    jsonWriter.value(scoreComponent.value());
                }
                jsonWriter.endObject();
            } else if (component instanceof SelectorComponent) {
                SelectorComponent selectorComponent = (SelectorComponent) component;
                jsonWriter.name("selector");
                jsonWriter.value(selectorComponent.pattern());
                this.serializeSeparator(jsonWriter, selectorComponent.separator());
            } else if (component instanceof KeybindComponent) {
                jsonWriter.name("keybind");
                jsonWriter.value(((KeybindComponent) component).keybind());
            } else if (component instanceof NBTComponent) {
                NBTComponent nBTComponent = (NBTComponent) component;
                jsonWriter.name("nbt");
                jsonWriter.value(nBTComponent.nbtPath());
                jsonWriter.name("interpret");
                jsonWriter.value(nBTComponent.interpret());
                this.serializeSeparator(jsonWriter, nBTComponent.separator());
                if (component instanceof BlockNBTComponent) {
                    jsonWriter.name("block");
                    this.gson.toJson(
                            ((BlockNBTComponent) component).pos(),
                            SerializerFactory.BLOCK_NBT_POS_TYPE,
                            jsonWriter);
                } else if (component instanceof EntityNBTComponent) {
                    jsonWriter.name("entity");
                    jsonWriter.value(((EntityNBTComponent) component).selector());
                } else {
                    if (!(component instanceof StorageNBTComponent)) {
                        throw notSureHowToSerialize(component);
                    }
                    jsonWriter.name("storage");
                    this.gson.toJson(
                            ((StorageNBTComponent) component).storage(),
                            SerializerFactory.KEY_TYPE,
                            jsonWriter);
                }
            } else {
                if (!BackwardCompatUtil.IS_4_25_0_OR_NEWER
                        || !(component instanceof ObjectComponent)) {
                    throw notSureHowToSerialize(component);
                }
                ObjectContents objectContents;
                if ((objectContents = ((ObjectComponent) component).contents())
                        instanceof SpriteObjectContents) {
                    SpriteObjectContents spriteObjectContents;
                    if (!(spriteObjectContents = (SpriteObjectContents) objectContents)
                            .atlas()
                            .equals(SpriteObjectContents.DEFAULT_ATLAS)) {
                        jsonWriter.name("atlas");
                        this.gson.toJson(
                                spriteObjectContents.atlas(),
                                SerializerFactory.KEY_TYPE,
                                jsonWriter);
                    }
                    jsonWriter.name("sprite");
                    this.gson.toJson(
                            spriteObjectContents.sprite(), SerializerFactory.KEY_TYPE, jsonWriter);
                } else {
                    if (!(objectContents instanceof PlayerHeadObjectContents)) {
                        throw notSureHowToSerialize(component);
                    }
                    PlayerHeadObjectContents playerHeadObjectContents =
                            (PlayerHeadObjectContents) objectContents;
                    jsonWriter.name("hat");
                    jsonWriter.value(playerHeadObjectContents.hat());
                    String text3 = playerHeadObjectContents.name();
                    UUID uUID = playerHeadObjectContents.id();
                    List<PlayerHeadObjectContents.ProfileProperty> values =
                            playerHeadObjectContents.profileProperties();
                    Key key2 = playerHeadObjectContents.texture();
                    jsonWriter.name("player");
                    if (text3 != null && uUID == null && values.isEmpty() && key2 == null) {
                        jsonWriter.value(text3);
                    } else {
                        jsonWriter.beginObject();
                        if (text3 != null) {
                            jsonWriter.name("name");
                            jsonWriter.value(text3);
                        }
                        if (uUID != null) {
                            jsonWriter.name("id");
                            this.gson.toJson(uUID, SerializerFactory.UUID_TYPE, jsonWriter);
                        }
                        if (!values.isEmpty()) {
                            jsonWriter.name("properties");
                            this.gson.toJson(values, PROPERTY_LIST_TYPE, jsonWriter);
                        }
                        if (key2 != null) {
                            jsonWriter.name("texture");
                            this.gson.toJson(key2, SerializerFactory.KEY_TYPE, jsonWriter);
                        }
                        jsonWriter.endObject();
                    }
                }
            }
            jsonWriter.endObject();
        }
    }

    private void serializeSeparator(JsonWriter jsonWriter, @Nullable Component component)
            throws IOException {
        if (component != null) {
            jsonWriter.name("separator");
            this.write(jsonWriter, component);
        }
    }

    static JsonParseException notSureHowToDeserialize(Object result) {
        return new JsonParseException("Don't know how to turn " + result + " into a Component");
    }

    private static IllegalArgumentException notSureHowToSerialize(Component component) {
        return new IllegalArgumentException(
                "Don't know how to serialize " + component + " as a Component");
    }
}
