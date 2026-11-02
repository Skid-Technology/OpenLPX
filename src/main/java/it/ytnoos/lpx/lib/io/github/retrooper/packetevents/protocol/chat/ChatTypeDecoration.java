package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class ChatTypeDecoration {

    private final String translationKey;

    private final List<ChatTypeDecoration.Parameter> parameters;

    private final Style style;

    public ChatTypeDecoration(
            String translationKey, List<ChatTypeDecoration.Parameter> values, Style style2) {
        this.translationKey = translationKey;
        this.parameters = Collections.unmodifiableList(new ArrayList<>(values));
        this.style = style2;
    }

    public static ChatTypeDecoration read(PacketWrapper<?> packetWrapper) {
        String text = packetWrapper.readString();
        List values2 =
                packetWrapper.readList(
                        result -> result.readEnum(ChatTypeDecoration.Parameter.values()));
        Style style = packetWrapper.readStyle();
        return new ChatTypeDecoration(text, values2, style);
    }

    public static void write(
            PacketWrapper<?> packetWrapper, ChatTypeDecoration chatTypeDecoration) {
        packetWrapper.writeString(chatTypeDecoration.translationKey);
        packetWrapper.writeList(chatTypeDecoration.parameters, PacketWrapper::writeEnum);
        packetWrapper.writeStyle(chatTypeDecoration.style);
    }

    @Deprecated
    public static ChatTypeDecoration decode(NBT nBT, ClientVersion clientVersion) {
        return decode(nBT, PacketWrapper.createDummyWrapper(clientVersion));
    }

    public static ChatTypeDecoration decode(NBT nBT, PacketWrapper<?> packetWrapper) {
        NBTCompound nBTCompound;
        String stringTagValueOrThrow =
                (nBTCompound = (NBTCompound) nBT).getStringTagValueOrThrow("translation_key");
        NBT nBT2;
        List<
                        it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat
                                .ChatTypeDecoration.Parameter>
                values;
        if ((nBT2 = nBTCompound.getTagOrThrow("parameters")) instanceof NBTList) {
            values = new ArrayList();
            Iterator iterator2 = ((NBTList) nBT2).getTags().iterator();
            while (iterator2.hasNext()) {
                String value = ((NBTString) ((NBT) iterator2.next())).getValue();
                values.add(
                        AdventureIndexUtil.indexValueOrThrow(
                                ChatTypeDecoration.Parameter.ID_INDEX, value));
            }
        } else {
            String value2 = ((NBTString) nBT2).getValue();
            values =
                    Collections.singletonList(
                            AdventureIndexUtil.indexValueOrThrow(
                                    ChatTypeDecoration.Parameter.ID_INDEX, value2));
        }
        NBTCompound nBTCompound2;
        Style style =
                (nBTCompound2 = nBTCompound.getCompoundTagOrNull("style")) == null
                        ? Style.empty()
                        : packetWrapper
                                .getSerializers()
                                .nbt()
                                .deserializeStyle(nBTCompound2, packetWrapper);
        return new ChatTypeDecoration(stringTagValueOrThrow, values, style);
    }

    @Deprecated
    public static NBT encode(ChatTypeDecoration chatTypeDecoration, ClientVersion clientVersion) {
        return encode(chatTypeDecoration, PacketWrapper.createDummyWrapper(clientVersion));
    }

    public static NBT encode(
            ChatTypeDecoration chatTypeDecoration, PacketWrapper<?> packetWrapper) {
        NBTList nBTList = NBTList.createStringList();
        for (ChatTypeDecoration.Parameter parameter : chatTypeDecoration.parameters) {
            nBTList.addTag(new NBTString(parameter.getId()));
        }
        NBTCompound nBTCompound;
        (nBTCompound = new NBTCompound())
                .setTag("translation_key", new NBTString(chatTypeDecoration.translationKey));
        nBTCompound.setTag("parameters", nBTList);
        if (!chatTypeDecoration.style.isEmpty()) {
            nBTCompound.setTag(
                    "style",
                    packetWrapper
                            .getSerializers()
                            .nbt()
                            .serializeStyle(chatTypeDecoration.style, packetWrapper));
        }
        return nBTCompound;
    }

    public static ChatTypeDecoration withSender(String text) {
        return new ChatTypeDecoration(
                text,
                Arrays.asList(
                        ChatTypeDecoration.Parameter.SENDER, ChatTypeDecoration.Parameter.CONTENT),
                Style.empty());
    }

    public static ChatTypeDecoration incomingDirectMessage(String text) {
        return new ChatTypeDecoration(
                text,
                Arrays.asList(
                        ChatTypeDecoration.Parameter.SENDER, ChatTypeDecoration.Parameter.CONTENT),
                Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    public static ChatTypeDecoration outgoingDirectMessage(String text) {
        return new ChatTypeDecoration(
                text,
                Arrays.asList(
                        ChatTypeDecoration.Parameter.TARGET, ChatTypeDecoration.Parameter.CONTENT),
                Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    public static ChatTypeDecoration teamMessage(String text) {
        return new ChatTypeDecoration(
                text,
                Arrays.asList(
                        ChatTypeDecoration.Parameter.TARGET,
                        ChatTypeDecoration.Parameter.SENDER,
                        ChatTypeDecoration.Parameter.CONTENT),
                Style.empty());
    }

    public Component decorate(Component component, ChatType.Bound bound) {
        ComponentLike[] componentLikes = new ComponentLike[this.parameters.size()];
        for (int index = 0; index < componentLikes.length; index++) {
            ChatTypeDecoration.Parameter parameter = this.parameters.get(index);
            componentLikes[index] = parameter.selector.apply(component, bound);
        }
        return Component.translatable(this.translationKey, null, this.style, componentLikes);
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public List<ChatTypeDecoration.Parameter> getParameters() {
        return this.parameters;
    }

    public Style getStyle() {
        return this.style;
    }

    @Override
    public boolean equals(Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof ChatTypeDecoration)) {
            return false;
        } else {
            ChatTypeDecoration chatTypeDecoration = (ChatTypeDecoration) result;
            if (!this.translationKey.equals(chatTypeDecoration.translationKey)) {
                return false;
            } else {
                return !this.parameters.equals(chatTypeDecoration.parameters)
                        ? false
                        : this.style.equals(chatTypeDecoration.style);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.translationKey, this.parameters, this.style);
    }

    public enum Parameter {
        SENDER("sender", (result, result2) -> result2.getName()),
        TARGET(
                "target",
                (result, result2) ->
                        result2.getTargetName() != null
                                ? result2.getTargetName()
                                : Component.empty()),
        CONTENT("content", (result, result2) -> result);

        public static final Index<String, ChatTypeDecoration.Parameter> ID_INDEX =
                Index.create(
                        ChatTypeDecoration.Parameter.class, ChatTypeDecoration.Parameter::getId);

        private final String id;

        private final BiFunction<Component, ChatType.Bound, Component> selector;

        Parameter(String id, BiFunction<Component, ChatType.Bound, Component> biFunction) {
            this.id = id;
            this.selector = biFunction;
        }

        public String getId() {
            return this.id;
        }

        @Deprecated
        @Nullable
        public static ChatTypeDecoration.Parameter valueByName(String text) {
            return ID_INDEX.value(text);
        }
    }
}
