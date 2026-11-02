package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure;

import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.ComponentSerializer;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.BackwardCompatUtil;
import it.ytnoos.lpx.lib.com.github.retrooper.packetevents.adventure.serializer.gson.GsonDataComponentValue;
import it.ytnoos.lpx.lib.gson.JsonElement;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.PacketEvents;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.component.builtin.item.ItemProfile;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.dialog.Dialog;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByte;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTByteArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTDouble;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTEnd;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTFloat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTInt;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTIntArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTList;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLong;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTLongArray;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTShort;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.codec.NBTCodec;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtCodecs;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.util.NbtDecoder;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.UniqueIdUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.BlockNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentBuilder;
import it.ytnoos.lpx.lib.kyori.adventure.text.EntityNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.KeybindComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.NBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.ObjectComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.ScoreComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.SelectorComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.StorageNBTComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TextComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslatableComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.TranslationArgument;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.ClickEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.NamedTextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.ShadowColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextColor;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.TextDecoration;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.SpriteObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class AdventureNBTSerializer implements ComponentSerializer<Component, Component, NBT> {

    private static final Set<TextDecoration> DECORATIONS = TextDecoration.NAMES.values();

    private final ClientVersion version;

    private final boolean downsampleColor;

    public AdventureNBTSerializer(ClientVersion clientVersion, boolean downsampleColor) {
        this.version = clientVersion;
        this.downsampleColor = downsampleColor;
    }

    @Deprecated
    public AdventureNBTSerializer(boolean matches) {
        this(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), matches);
    }

    @Deprecated
    @Contract("!null -> !null")
    @Nullable
    public Component deserializeOrNull(@Nullable NBT nBT) {
        return this.deserializeOrNull(nBT, PacketWrapper.createDummyWrapper(this.version));
    }

    @Contract("!null, _ -> !null")
    @Nullable
    public Component deserializeOrNull(@Nullable NBT nBT, PacketWrapper<?> packetWrapper) {
        return nBT != null ? this.deserialize(nBT, packetWrapper) : null;
    }

    @Deprecated
    @Contract("_, !null -> !null")
    @Nullable
    public Component deserializeOr(@Nullable NBT nBT, @Nullable Component component) {
        return this.deserializeOr(nBT, component, PacketWrapper.createDummyWrapper(this.version));
    }

    @Contract("_, !null, _ -> !null")
    @Nullable
    public Component deserializeOr(
            @Nullable NBT nBT, @Nullable Component component, PacketWrapper<?> packetWrapper) {
        return nBT != null ? this.deserialize(nBT, packetWrapper) : component;
    }

    @Deprecated
    @Contract("!null -> !null")
    @Nullable
    public NBT serializeOrNull(@Nullable Component component) {
        return this.serializeOrNull(component, PacketWrapper.createDummyWrapper(this.version));
    }

    @Contract("!null, _ -> !null")
    @Nullable
    public NBT serializeOrNull(@Nullable Component component, PacketWrapper<?> packetWrapper) {
        return component != null ? this.serialize(component, packetWrapper) : null;
    }

    @Deprecated
    @Contract("_, !null -> !null")
    @Nullable
    public NBT serializeOr(@Nullable Component component, @Nullable NBT nBT) {
        return this.serializeOr(component, nBT, PacketWrapper.createDummyWrapper(this.version));
    }

    @Contract("_, !null, _ -> !null")
    @Nullable
    public NBT serializeOr(
            @Nullable Component component, @Nullable NBT nBT, PacketWrapper<?> packetWrapper) {
        return component != null ? this.serialize(component, packetWrapper) : nBT;
    }

    @Deprecated
    @NotNull
    public Component deserialize(@NotNull NBT nBT) {
        return this.deserialize(nBT, PacketWrapper.createDummyWrapper(this.version));
    }

    @NotNull
    public Component deserialize(@NotNull NBT nBT, PacketWrapper<?> packetWrapper) {
        if (nBT.getType() == NBTType.STRING) {
            return Component.text(((NBTString) nBT).getValue());
        }
        if (nBT.getType() == NBTType.BYTE && ((NBTByte) nBT).getAsByte() < 2) {
            return Component.text(((NBTByte) nBT).getAsByte() == 1);
        }
        if (nBT instanceof NBTNumber) {
            return Component.text(((NBTNumber) nBT).getAsNumber().toString());
        }
        if (nBT.getType() == NBTType.LIST) {
            List<Component> values;
            if ((values = this.deserializeComponentList(((NBTList) nBT).getTags(), packetWrapper))
                    .isEmpty()) {
                throw new IllegalArgumentException("Component list may not be empty");
            }
            Component component = (Component) values.get(0);
            ArrayList values2;
            (values2 = new ArrayList(component.children().size() + values.size() - 1))
                    .addAll(component.children());
            values2.addAll(values.subList(1, values.size()));
            return component.children(values2);
        } else {
            NBTCompound nBTCompound = requireType(nBT, NBTType.COMPOUND);
            AdventureNBTSerializer.NBTReader nBTReader =
                    new AdventureNBTSerializer.NBTReader(packetWrapper, nBTCompound);
            Function<NBT, String> function =
                    result -> {
                        if (result.getType() == NBTType.STRING) {
                            return ((NBTString) result).getValue();
                        } else if (result.getType() == NBTType.BYTE
                                && ((NBTByte) result).getAsByte() < 2) {
                            return String.valueOf(((NBTByte) result).getAsByte() == 1);
                        } else if (result instanceof NBTNumber) {
                            return ((NBTNumber) result).getAsNumber().toString();
                        } else {
                            throw new IllegalStateException(
                                    "Don't know how to deserialize "
                                            + result.getType()
                                            + " to text");
                        }
                    };
            String text2;
            if ((text2 = ((String) nBTReader.read("text", function))) == null) {
                text2 = ((String) nBTReader.read("", function));
            }
            String text3 = nBTReader.readUTF("translate", Function.identity());
            String text4 = nBTReader.readUTF("fallback", Function.identity());
            List values3;
            if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                NBTType nBTType;
                if ((nBTType = nBTReader.type("with")) == NBTType.INT_ARRAY) {
                    values3 =
                            nBTReader.readIntArray(
                                    "with",
                                    result -> {
                                        ArrayList<TranslationArgument> result2 =
                                                new ArrayList<>(result.length);
                                        int[] result3 = result;
                                        int value = result.length;
                                        for (int index = 0; index < value; index++) {
                                            int result4 = result3[index];
                                            result2.add(TranslationArgument.numeric(result4));
                                        }
                                        return result2;
                                    });
                } else if (nBTType == NBTType.BYTE_ARRAY) {
                    values3 =
                            nBTReader.readByteArray(
                                    "with",
                                    result -> {
                                        ArrayList result2 = new ArrayList(result.length);
                                        byte[] result3 = result;
                                        int value = result.length;
                                        for (int index = 0; index < value; index++) {
                                            byte result4 = result3[index];
                                            result2.add(TranslationArgument.bool(result4 != 0));
                                        }
                                        return result2;
                                    });
                } else if (nBTType == NBTType.LONG_ARRAY) {
                    values3 =
                            nBTReader.readLongArray(
                                    "with",
                                    result -> {
                                        ArrayList result2 = new ArrayList(result.length);
                                        long[] result3 = result;
                                        int value = result.length;
                                        for (int index = 0; index < value; index++) {
                                            long result4 = result3[index];
                                            result2.add(TranslationArgument.numeric(result4));
                                        }
                                        return result2;
                                    });
                } else {
                    values3 =
                            nBTReader.readList(
                                    "with",
                                    result2 ->
                                            this.deserializeTranslationArgumentList(
                                                    result2, ((PacketWrapper) packetWrapper)));
                }
            } else {
                values3 =
                        nBTReader.readList(
                                "with",
                                result2 ->
                                        this.deserializeComponentList(
                                                result2, ((PacketWrapper) packetWrapper)));
            }
            AdventureNBTSerializer.NBTReader nBTReader2 = nBTReader.child("score");
            String text5 = nBTReader.readUTF("selector", Function.identity());
            String text6 = nBTReader.readUTF("keybind", Function.identity());
            String text7 = nBTReader.readUTF("nbt", Function.identity());
            boolean matches =
                    Optional.ofNullable(nBTReader.readBoolean("interpret", Function.identity()))
                            .orElse(false);
            BlockNBTComponent.Pos pos2 =
                    nBTReader.readUTF("block", BlockNBTComponent.Pos::fromString);
            String text8 = nBTReader.readUTF("entity", Function.identity());
            Key key2 = nBTReader.readUTF("storage", Key::key);
            List<Component> values4 =
                    nBTReader.readList(
                            "extra",
                            result2 ->
                                    this.deserializeComponentList(
                                            result2, ((PacketWrapper) packetWrapper)));
            Component component2 =
                    nBTReader.read(
                            "separator",
                            result2 -> this.deserialize(result2, ((PacketWrapper) packetWrapper)));
            NBT nBT2 = nBTReader.read("player", Function.identity());
            String text9 = nBTReader.readUTF("sprite", Function.identity());
            Style style2 = this.deserializeStyle(nBTCompound, packetWrapper);
            ComponentBuilder componentBuilder;
            if (text2 != null) {
                componentBuilder = Component.text().content(text2);
            } else if (text3 != null) {
                TranslatableComponent.Builder builder;
                componentBuilder = builder = Component.translatable().key(text3);
                if (values3 != null) {
                    if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                        builder.arguments(values3);
                    } else {
                        builder.args(values3);
                    }
                }
                if (BackwardCompatUtil.IS_4_13_0_OR_NEWER) {
                    builder.fallback(text4);
                }
            } else if (nBTReader2 != null) {
                componentBuilder =
                        Component.score()
                                .name(nBTReader2.readUTF("name", Function.identity()))
                                .objective(nBTReader2.readUTF("objective", Function.identity()));
            } else if (text5 != null) {
                componentBuilder = Component.selector().pattern(text5).separator(component2);
            } else if (text6 != null) {
                componentBuilder = Component.keybind().keybind(text6);
            } else if (text7 != null) {
                if (pos2 != null) {
                    componentBuilder =
                            Component.blockNBT()
                                    .nbtPath(text7)
                                    .interpret(matches)
                                    .separator(component2)
                                    .pos(pos2);
                } else if (text8 != null) {
                    componentBuilder =
                            Component.entityNBT()
                                    .nbtPath(text7)
                                    .interpret(matches)
                                    .separator(component2)
                                    .selector(text8);
                } else {
                    if (key2 == null) {
                        throw new IllegalStateException(
                                "Illegal nbt component, block/entity/storage is missing");
                    }
                    componentBuilder =
                            Component.storageNBT()
                                    .nbtPath(text7)
                                    .interpret(matches)
                                    .separator(component2)
                                    .storage(key2);
                }
            } else if (nBT2 != null) {
                if (BackwardCompatUtil.IS_4_25_0_OR_NEWER) {
                    ItemProfile itemProfile = ItemProfile.decode(nBT2, packetWrapper);
                    PlayerHeadObjectContents playerHeadObjectContents =
                            ObjectContents.playerHead()
                                    .id(itemProfile.getId())
                                    .name(itemProfile.getName())
                                    .profileProperties(itemProfile.getAdventureProperties())
                                    .hat(
                                            Optional.ofNullable(
                                                            nBTReader.readBoolean(
                                                                    "hat", Function.identity()))
                                                    .orElse(true))
                                    .build();
                    componentBuilder = Component.object().contents(playerHeadObjectContents);
                } else {
                    componentBuilder = Component.text();
                }
            } else if (text9 != null) {
                if (BackwardCompatUtil.IS_4_25_0_OR_NEWER) {
                    Key key3 = Key.key(text9);
                    Key key4 = nBTReader.readUTF("atlas", result -> Key.key(result));
                    componentBuilder =
                            Component.object()
                                    .contents(
                                            key4 != null
                                                    ? ObjectContents.sprite(key4, key3)
                                                    : ObjectContents.sprite(key3));
                } else {
                    componentBuilder = Component.text();
                }
            } else {
                componentBuilder = Component.empty().toBuilder();
            }
            componentBuilder.style(style2);
            if (values4 != null) {
                componentBuilder.append(values4);
            }
            return BackwardCompatUtil.build(componentBuilder);
        }
    }

    @Deprecated
    @NotNull
    public NBT serialize(@NotNull Component component) {
        return this.serialize(component, PacketWrapper.createDummyWrapper(this.version));
    }

    @NotNull
    public NBT serialize(@NotNull Component component, PacketWrapper<?> packetWrapper) {
        return component instanceof TextComponent
                        && !component.hasStyling()
                        && component.children().isEmpty()
                ? new NBTString(((TextComponent) component).content())
                : this.serializeComponent(component, packetWrapper);
    }

    @NotNull
    private NBTCompound serializeComponent(Component component, PacketWrapper<?> packetWrapper) {
        AdventureNBTSerializer.NBTWriter nBTWriter =
                new AdventureNBTSerializer.NBTWriter(new NBTCompound());
        if (component instanceof TextComponent) {
            nBTWriter.writeUTF("text", ((TextComponent) component).content());
        } else if (component instanceof TranslatableComponent) {
            nBTWriter.writeUTF("translate", ((TranslatableComponent) component).key());
            String text2;
            if (BackwardCompatUtil.IS_4_13_0_OR_NEWER
                    && (text2 = ((TranslatableComponent) component).fallback()) != null) {
                nBTWriter.writeUTF("fallback", text2);
            }
            if (BackwardCompatUtil.IS_4_15_0_OR_NEWER) {
                List<TranslationArgument> values;
                if (!(values = ((TranslatableComponent) component).arguments()).isEmpty()) {
                    nBTWriter.writeList(
                            "with",
                            NBTType.COMPOUND,
                            this.serializeTranslationArgumentList(values, packetWrapper));
                }
            } else {
                List<Component> values2;
                if (!(values2 = ((TranslatableComponent) component).args()).isEmpty()) {
                    nBTWriter.writeList(
                            "with",
                            NBTType.COMPOUND,
                            this.serializeComponentList(values2, packetWrapper));
                }
            }
        } else if (component instanceof ScoreComponent) {
            AdventureNBTSerializer.NBTWriter nBTWriter2 = nBTWriter.child("score");
            String text3 = ((ScoreComponent) component).name();
            nBTWriter2.writeUTF("name", text3);
            String text4 = ((ScoreComponent) component).objective();
            nBTWriter2.writeUTF("objective", text4);
        } else if (component instanceof SelectorComponent) {
            nBTWriter.writeUTF("selector", ((SelectorComponent) component).pattern());
            Component component2;
            if ((component2 = ((SelectorComponent) component).separator()) != null) {
                nBTWriter.write("separator", this.serialize(component2, packetWrapper));
            }
        } else if (component instanceof KeybindComponent) {
            nBTWriter.writeUTF("keybind", ((KeybindComponent) component).keybind());
        } else if (component instanceof NBTComponent) {
            String text5 = ((NBTComponent) component).nbtPath();
            nBTWriter.writeUTF("nbt", text5);
            if (((NBTComponent) component).interpret()) {
                nBTWriter.writeBoolean("interpret", true);
            }
            Component component3;
            if ((component3 = ((NBTComponent) component).separator()) != null) {
                nBTWriter.write("separator", this.serialize(component3, packetWrapper));
            }
            if (component instanceof BlockNBTComponent) {
                BlockNBTComponent.Pos pos2 = ((BlockNBTComponent) component).pos();
                nBTWriter.writeUTF("block", pos2.asString());
            } else if (component instanceof EntityNBTComponent) {
                String text6 = ((EntityNBTComponent) component).selector();
                nBTWriter.writeUTF("entity", text6);
            } else if (component instanceof StorageNBTComponent) {
                Key key2 = ((StorageNBTComponent) component).storage();
                nBTWriter.writeUTF("storage", key2.asString());
            }
        } else if (component instanceof ObjectComponent) {
            if (BackwardCompatUtil.IS_4_25_0_OR_NEWER
                    && this.version.isNewerThanOrEquals(ClientVersion.V_1_21_9)) {
                ObjectContents objectContents;
                if ((objectContents = ((ObjectComponent) component).contents())
                        instanceof PlayerHeadObjectContents) {
                    PlayerHeadObjectContents playerHeadObjectContents;
                    ItemProfile itemProfile =
                            ItemProfile.fromAdventure(
                                    playerHeadObjectContents =
                                            (PlayerHeadObjectContents) objectContents);
                    nBTWriter.write("player", ItemProfile.encode(packetWrapper, itemProfile));
                    if (!playerHeadObjectContents.hat()) {
                        nBTWriter.writeBoolean("hat", playerHeadObjectContents.hat());
                    }
                } else if (objectContents instanceof SpriteObjectContents) {
                    SpriteObjectContents spriteObjectContents;
                    if (!(spriteObjectContents = (SpriteObjectContents) objectContents)
                            .atlas()
                            .equals(SpriteObjectContents.DEFAULT_ATLAS)) {
                        nBTWriter.writeUTF("atlas", spriteObjectContents.atlas().toString());
                    }
                    nBTWriter.writeUTF("sprite", spriteObjectContents.sprite().toString());
                }
            } else {
                nBTWriter.writeUTF("text", "");
            }
        }
        if (component.hasStyling()) {
            Map<String, NBT> tags = this.serializeStyle(component.style(), packetWrapper).getTags();
            tags.forEach(nBTWriter::write);
        }
        List<Component> values3;
        if (!(values3 = component.children()).isEmpty()) {
            nBTWriter.writeList(
                    "extra", NBTType.COMPOUND, this.serializeComponentList(values3, packetWrapper));
        }
        return nBTWriter.compound;
    }

    @Deprecated
    @NotNull
    public Style deserializeStyle(NBTCompound nBTCompound) {
        return this.deserializeStyle(nBTCompound, PacketWrapper.createDummyWrapper(this.version));
    }

    @NotNull
    public Style deserializeStyle(NBTCompound nBTCompound, PacketWrapper<?> packetWrapper) {
        if (nBTCompound.isEmpty()) {
            return Style.empty();
        }
        Style.Builder builder = Style.style();
        AdventureNBTSerializer.NBTReader nBTReader;
        String text;
        if ((text =
                        (nBTReader =
                                        new AdventureNBTSerializer.NBTReader(
                                                packetWrapper, nBTCompound))
                                .getUTF("font"))
                != null) {
            builder.font(Key.key(text));
        }
        String text2;
        TextColor textColor;
        if ((text2 = nBTReader.getUTF("color")) != null
                && (textColor = this.deserializeColor(text2)) != null) {
            builder.color(textColor);
        }
        Number number;
        if (BackwardCompatUtil.IS_4_18_0_OR_NEWER
                && (number = nBTReader.getNumber("shadow_color")) != null) {
            builder.shadowColor(ShadowColor.shadowColor(number.intValue()));
        }
        for (TextDecoration textDecoration : DECORATIONS) {
            Boolean matches;
            if ((matches = nBTReader.getBoolean(textDecoration.toString())) != null) {
                builder.decoration(textDecoration, TextDecoration.State.byBoolean(matches));
            }
        }
        String text3;
        if ((text3 = nBTReader.getUTF("insertion")) != null) {
            builder.insertion(text3);
        }
        boolean newerThanOrEquals = this.version.isNewerThanOrEquals(ClientVersion.V_1_21_5);
        AdventureNBTSerializer.NBTReader nBTReader2;
        if ((nBTReader2 = nBTReader.child(newerThanOrEquals ? "click_event" : "clickEvent"))
                != null) {
            Index<String, ClickEvent.Action> index = ClickEvent.Action.NAMES;
            ClickEvent.Action action;
            ClickEvent clickEvent2;
            switch ((action = nBTReader2.readUTF("action", index::value)).toString()) {
                case "open_url":
                    clickEvent2 =
                            ClickEvent.openUrl(
                                    nBTReader2.readUTF(
                                            newerThanOrEquals ? "url" : "value",
                                            Function.identity()));
                    break;
                case "open_file":
                    clickEvent2 =
                            ClickEvent.openFile(
                                    nBTReader2.readUTF(
                                            newerThanOrEquals ? "path" : "value",
                                            Function.identity()));
                    break;
                case "run_command":
                    clickEvent2 =
                            ClickEvent.runCommand(
                                    nBTReader2.readUTF(
                                            newerThanOrEquals ? "command" : "value",
                                            Function.identity()));
                    break;
                case "suggest_command":
                    clickEvent2 =
                            ClickEvent.suggestCommand(
                                    nBTReader2.readUTF(
                                            newerThanOrEquals ? "command" : "value",
                                            Function.identity()));
                    break;
                case "change_page":
                    clickEvent2 =
                            ClickEvent.changePage(
                                    newerThanOrEquals
                                            ? nBTReader2.readNumber("page", Number::intValue)
                                            : nBTReader2.readUTF("value", Integer::parseInt));
                    break;
                case "copy_to_clipboard":
                    clickEvent2 =
                            ClickEvent.copyToClipboard(
                                    nBTReader2.readUTF("value", Function.identity()));
                    break;
                case "show_dialog":
                    clickEvent2 =
                            ClickEvent.showDialog(
                                    Dialog.decode(
                                            nBTReader2.read("dialog", Function.identity()),
                                            packetWrapper));
                    break;
                case "custom":
                    Key key2 = nBTReader2.readUTF("id", Key::key);
                    NBT nBT = nBTReader2.read("payload", Function.identity());
                    clickEvent2 =
                            ClickEvent.custom(
                                    key2, new NbtTagHolder(nBT != null ? nBT : NBTEnd.INSTANCE));
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported clickevent: " + action);
            }
            builder.clickEvent(clickEvent2);
        }
        AdventureNBTSerializer.NBTReader nBTReader3;
        if ((nBTReader3 = nBTReader.child(newerThanOrEquals ? "hover_event" : "hoverEvent"))
                != null) {
            Index index2 = HoverEvent.Action.NAMES;
            switch (nBTReader3.readUTF("action", index2::value).toString()) {
                case "show_text":
                    String text4 = newerThanOrEquals ? "value" : "contents";
                    builder.hoverEvent(
                            HoverEvent.showText(
                                    nBTReader3.read(
                                            text4,
                                            result ->
                                                    this.deserialize(
                                                            result,
                                                            ((PacketWrapper) packetWrapper)))));
                    break;
                case "show_item":
                    if (!newerThanOrEquals && nBTReader3.type("contents") == NBTType.STRING) {
                        builder.hoverEvent(
                                HoverEvent.showItem(nBTReader3.readUTF("contents", Key::key), 1));
                    } else {
                        AdventureNBTSerializer.NBTReader nBTReader4;
                        if ((nBTReader4 =
                                        newerThanOrEquals
                                                ? nBTReader3
                                                : nBTReader3.child("contents"))
                                != null) {
                            Key key3 = nBTReader4.readUTF("id", Key::key);
                            Integer value;
                            int value2 =
                                    (value = nBTReader4.readNumber("count", Number::intValue))
                                                    == null
                                            ? 1
                                            : value;
                            BinaryTagHolder binaryTagHolder;
                            if ((binaryTagHolder =
                                                    nBTReader4.readUTF(
                                                            "tag",
                                                            BinaryTagHolder::binaryTagHolder))
                                            == null
                                    && BackwardCompatUtil.IS_4_17_0_OR_NEWER) {
                                Map entries =
                                        nBTReader4.readCompound(
                                                "components",
                                                result2 -> {
                                                    HashMap result = new HashMap(result2.size());
                                                    Iterator<Entry<String, NBT>> iterator2 =
                                                            result2.getTags().entrySet().iterator();
                                                    while (iterator2.hasNext()) {
                                                        Entry entry;
                                                        if (((String)
                                                                        (entry =
                                                                                        (Entry)
                                                                                                iterator2
                                                                                                        .next())
                                                                                .getKey())
                                                                .startsWith("!")) {
                                                            Key key4 =
                                                                    Key.key(
                                                                            ((String)
                                                                                            entry
                                                                                                    .getKey())
                                                                                    .substring(1));
                                                            result.put(
                                                                    key4,
                                                                    DataComponentValue.removed());
                                                        } else {
                                                            Key key5 =
                                                                    Key.key(
                                                                            (String)
                                                                                    entry.getKey());
                                                            result.put(
                                                                    key5,
                                                                    new NbtTagHolder(
                                                                            (NBT)
                                                                                    entry
                                                                                            .getValue()));
                                                        }
                                                    }
                                                    return result;
                                                });
                                builder.hoverEvent(
                                        HoverEvent.showItem(
                                                key3,
                                                value2,
                                                entries == null
                                                        ? Collections.emptyMap()
                                                        : entries));
                            } else {
                                builder.hoverEvent(
                                        HoverEvent.showItem(key3, value2, binaryTagHolder));
                            }
                        }
                    }
                    break;
                case "show_entity":
                    AdventureNBTSerializer.NBTReader nBTReader5;
                    if ((nBTReader5 = newerThanOrEquals ? nBTReader3 : nBTReader3.child("contents"))
                            != null) {
                        Key key6 = nBTReader5.readUTF(newerThanOrEquals ? "id" : "type", Key::key);
                        UUID uUID =
                                nBTReader5.read(
                                        newerThanOrEquals ? "uuid" : "id", NbtCodecs.LENIENT_UUID);
                        builder.hoverEvent(
                                HoverEvent.showEntity(
                                        key6,
                                        uUID,
                                        nBTReader5.read(
                                                "name",
                                                result ->
                                                        this.deserialize(
                                                                result,
                                                                ((PacketWrapper) packetWrapper)))));
                    }
            }
        }
        return builder.build();
    }

    @Deprecated
    @NotNull
    public NBTCompound serializeStyle(Style style) {
        return this.serializeStyle(style, PacketWrapper.createDummyWrapper(this.version));
    }

    @NotNull
    public NBTCompound serializeStyle(Style style, PacketWrapper<?> packetWrapper) {
        if (style.isEmpty()) {
            return new NBTCompound();
        }
        AdventureNBTSerializer.NBTWriter nBTWriter =
                new AdventureNBTSerializer.NBTWriter(new NBTCompound());
        Key key2;
        if ((key2 = style.font()) != null) {
            nBTWriter.writeUTF("font", key2.asString());
        }
        TextColor textColor;
        if ((textColor = style.color()) != null) {
            nBTWriter.writeUTF("color", this.serializeColor(textColor));
        }
        ShadowColor shadowColor2;
        if (BackwardCompatUtil.IS_4_18_0_OR_NEWER && (shadowColor2 = style.shadowColor()) != null) {
            nBTWriter.writeInt("shadow_color", shadowColor2.value());
        }
        for (TextDecoration textDecoration : DECORATIONS) {
            TextDecoration.State state;
            if ((state = style.decoration(textDecoration)) != TextDecoration.State.NOT_SET) {
                nBTWriter.writeBoolean(
                        textDecoration.toString(), state == TextDecoration.State.TRUE);
            }
        }
        String text2;
        if ((text2 = style.insertion()) != null) {
            nBTWriter.writeUTF("insertion", text2);
        }
        ClickEvent clickEvent2;
        if ((clickEvent2 = style.clickEvent()) != null) {
            boolean newerThanOrEquals = this.version.isNewerThanOrEquals(ClientVersion.V_1_21_5);
            AdventureNBTSerializer.NBTWriter nBTWriter2;
            (nBTWriter2 = nBTWriter.child(newerThanOrEquals ? "click_event" : "clickEvent"))
                    .writeUTF("action", clickEvent2.action().toString());
            if (!newerThanOrEquals) {
                if (clickEvent2.action() != ClickEvent.Action.CHANGE_PAGE) {
                    nBTWriter2.writeUTF("value", AdventureSupportUtil.getStringValue(clickEvent2));
                } else {
                    nBTWriter2.writeUTF(
                            "value",
                            Integer.toString(AdventureSupportUtil.getIntValue(clickEvent2)));
                }
            } else {
                switch (clickEvent2.action().name().toLowerCase(Locale.ROOT)) {
                    case "open_url":
                        nBTWriter2.writeUTF(
                                "url", AdventureSupportUtil.getStringValue(clickEvent2));
                        break;
                    case "open_file":
                        nBTWriter2.writeUTF(
                                "path", AdventureSupportUtil.getStringValue(clickEvent2));
                        break;
                    case "run_command":
                    case "suggest_command":
                        nBTWriter2.writeUTF(
                                "command", AdventureSupportUtil.getStringValue(clickEvent2));
                        break;
                    case "change_page":
                        nBTWriter2.writeInt("page", AdventureSupportUtil.getIntValue(clickEvent2));
                        break;
                    case "copy_to_clipboard":
                        nBTWriter2.writeUTF(
                                "value", AdventureSupportUtil.getStringValue(clickEvent2));
                        break;
                    case "show_dialog":
                        Dialog dialog2 =
                                (Dialog)
                                        ((ClickEvent.Payload.Dialog) clickEvent2.payload())
                                                .dialog();
                        nBTWriter2.write("dialog", Dialog.encode(packetWrapper, dialog2));
                        break;
                    case "custom":
                        ClickEvent.Payload.Custom custom =
                                (ClickEvent.Payload.Custom) clickEvent2.payload();
                        nBTWriter2.writeUTF("id", custom.key().asString());
                        BinaryTagHolder binaryTagHolder;
                        if ((binaryTagHolder = custom.nbt()) instanceof NbtTagHolder) {
                            NBT nBT;
                            if (!((nBT = ((NbtTagHolder) binaryTagHolder).getTag())
                                    instanceof NBTEnd)) {
                                nBTWriter2.write("payload", nBT);
                            }
                        } else {
                            String text3;
                            if (binaryTagHolder != null
                                    && !(text3 = binaryTagHolder.string()).isEmpty()) {
                                nBTWriter2.write("payload", AdventureNbtUtil.fromString(text3));
                            }
                        }
                        break;
                    default:
                        throw new UnsupportedOperationException(
                                "Unsupported clickevent: " + clickEvent2);
                }
            }
        }
        HoverEvent hoverEvent2;
        if ((hoverEvent2 = style.hoverEvent()) != null) {
            boolean newerThanOrEquals2 = this.version.isNewerThanOrEquals(ClientVersion.V_1_21_5);
            AdventureNBTSerializer.NBTWriter nBTWriter3;
            (nBTWriter3 = nBTWriter.child(newerThanOrEquals2 ? "hover_event" : "hoverEvent"))
                    .writeUTF("action", hoverEvent2.action().toString());
            switch (hoverEvent2.action().toString()) {
                case "show_text":
                    nBTWriter3.write(
                            newerThanOrEquals2 ? "value" : "contents",
                            this.serialize((Component) hoverEvent2.value(), packetWrapper));
                    break;
                case "show_item":
                    HoverEvent.ShowItem showItem;
                    Key key3 = (showItem = (HoverEvent.ShowItem) hoverEvent2.value()).item();
                    int value2 = showItem.count();
                    BinaryTagHolder binaryTagHolder2 = showItem.nbt();
                    boolean matches =
                            !BackwardCompatUtil.IS_4_17_0_OR_NEWER
                                    || showItem.dataComponents().isEmpty();
                    if (!newerThanOrEquals2 && value2 == 1 && binaryTagHolder2 == null && matches) {
                        nBTWriter3.writeUTF("contents", key3.asString());
                    } else {
                        AdventureNBTSerializer.NBTWriter nBTWriter4;
                        (nBTWriter4 =
                                        newerThanOrEquals2
                                                ? nBTWriter3
                                                : nBTWriter3.child("contents"))
                                .writeUTF("id", key3.asString());
                        if (!newerThanOrEquals2 || value2 != 1) {
                            nBTWriter4.writeInt("count", value2);
                        }
                        if (binaryTagHolder2 != null) {
                            nBTWriter4.writeUTF("tag", binaryTagHolder2.string());
                        }
                        if (!matches) {
                            AdventureNBTSerializer.NBTWriter nBTWriter5 =
                                    nBTWriter4.child("components");
                            Iterator<Entry<Key, DataComponentValue>> iterator2 =
                                    showItem.dataComponents().entrySet().iterator();
                            while (iterator2.hasNext()) {
                                Entry entry;
                                if ((entry = (Entry) iterator2.next()).getValue()
                                        == DataComponentValue.removed()) {
                                    nBTWriter5.writeCompound(
                                            "!" + entry.getKey(), new NBTCompound());
                                } else if (entry.getValue() instanceof NbtTagHolder) {
                                    NBT tag = ((NbtTagHolder) entry.getValue()).getTag();
                                    nBTWriter5.write(((Key) entry.getKey()).toString(), tag);
                                } else if (entry.getValue()
                                        instanceof DataComponentValue.TagSerializable) {
                                    NBT nBT2 =
                                            AdventureNbtUtil.fromString(
                                                    ((DataComponentValue.TagSerializable)
                                                                    entry.getValue())
                                                            .asBinaryTag()
                                                            .string());
                                    nBTWriter5.write(((Key) entry.getKey()).toString(), nBT2);
                                } else if (entry.getValue() instanceof GsonDataComponentValue) {
                                    JsonElement jsonElement =
                                            ((GsonDataComponentValue) entry.getValue()).element();
                                    nBTWriter5.write(
                                            ((Key) entry.getKey()).toString(),
                                            NBTCodec.jsonToNBT(jsonElement));
                                }
                            }
                        }
                    }
                    break;
                case "show_entity":
                    HoverEvent.ShowEntity showEntity = (HoverEvent.ShowEntity) hoverEvent2.value();
                    AdventureNBTSerializer.NBTWriter nBTWriter6;
                    if ((nBTWriter6 =
                                    newerThanOrEquals2 ? nBTWriter3 : nBTWriter3.child("contents"))
                            != null) {
                        nBTWriter6.writeUTF(
                                newerThanOrEquals2 ? "id" : "type", showEntity.type().asString());
                        nBTWriter6.writeIntArray(
                                newerThanOrEquals2 ? "uuid" : "id",
                                UniqueIdUtil.toIntArray(showEntity.id()));
                        if (showEntity.name() != null) {
                            nBTWriter6.write(
                                    "name", this.serialize(showEntity.name(), packetWrapper));
                        }
                    }
            }
        }
        return nBTWriter.compound;
    }

    @Nullable
    private TextColor deserializeColor(@NotNull String text) {
        TextColor textColor;
        if (text.startsWith("#")) {
            textColor = TextColor.fromHexString(text);
        } else {
            textColor = NamedTextColor.NAMES.value(text);
        }
        if (textColor == null) {
            return null;
        } else {
            return this.downsampleColor ? NamedTextColor.nearestTo(textColor) : textColor;
        }
    }

    @NotNull
    private String serializeColor(@NotNull TextColor textColor) {
        if (textColor instanceof NamedTextColor) {
            return NamedTextColor.NAMES.key((NamedTextColor) textColor);
        } else {
            return this.downsampleColor
                    ? NamedTextColor.NAMES.key(NamedTextColor.nearestTo(textColor))
                    : String.format(Locale.ROOT, "%c%06X", '#', textColor.value());
        }
    }

    @NotNull
    private List<Component> deserializeComponentList(
            List<?> values, PacketWrapper<?> packetWrapper) {
        if (values.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Component> values2 = new ArrayList<>(values.size());
        for (Object result : values) {
            values2.add(this.deserialize((NBT) result, packetWrapper));
        }
        return values2;
    }

    private List<NBTCompound> serializeComponentList(
            List<Component> values, PacketWrapper<?> packetWrapper) {
        ArrayList<NBTCompound> values2 = new ArrayList<>(values.size());
        for (Component component : values) {
            values2.add(this.serializeComponent(component, packetWrapper));
        }
        return values2;
    }

    @NotNull
    private List<TranslationArgument> deserializeTranslationArgumentList(
            List<?> values, PacketWrapper<?> packetWrapper) {
        if (values.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<TranslationArgument> values2 = new ArrayList<>(values.size());
        Iterator<?> iterator2 = values.iterator();
        while (iterator2.hasNext()) {
            Object result;
            if ((result = iterator2.next()) instanceof NBTByte) {
                values2.add(TranslationArgument.bool(((NBTByte) result).getAsByte() != 0));
            } else if (result instanceof NBTNumber) {
                values2.add(TranslationArgument.numeric(((NBTNumber) result).getAsNumber()));
            } else if (result instanceof NBTString) {
                values2.add(
                        TranslationArgument.component(
                                Component.text(((NBTString) result).getValue())));
            } else {
                values2.add(
                        TranslationArgument.component(
                                this.deserialize(
                                        requireType((NBT) result, NBTType.COMPOUND),
                                        packetWrapper)));
            }
        }
        return values2;
    }

    private List<NBTCompound> serializeTranslationArgumentList(
            List<TranslationArgument> values, PacketWrapper<?> packetWrapper) {
        ArrayList<NBTCompound> values2 = new ArrayList<>(values.size());
        for (TranslationArgument translationArgument : values) {
            values2.add(this.serializeComponent(translationArgument.asComponent(), packetWrapper));
        }
        return values2;
    }

    private static <T extends NBT> T requireType(NBT nBT, NBTType<T> nBTType) {
        if (nBT.getType() != nBTType) {
            throw new IllegalArgumentException("Expected " + nBTType + " but got " + nBT.getType());
        } else {
            return (T) nBT;
        }
    }

    static class NBTReader {

        private final PacketWrapper<?> wrapper;

        private final NBTCompound compound;

        public NBTReader(PacketWrapper<?> packetWrapper, NBTCompound nBTCompound) {
            this.wrapper = packetWrapper;
            this.compound = nBTCompound;
        }

        public void useBoolean(String text, Consumer<Boolean> consumer) {
            this.useNumber(text, result -> ((Consumer) consumer).accept(result.byteValue() != 0));
        }

        public <R> R readBoolean(String text, Function<Boolean, R> function) {
            return this.readNumber(
                    text, result -> (R) ((Function) function).apply(result.byteValue() != 0));
        }

        public void useNumber(String text, Consumer<Number> consumer) {
            this.useTag(
                    text,
                    result -> {
                        if (result instanceof NBTNumber) {
                            ((Consumer) consumer).accept(((NBTNumber) result).getAsNumber());
                        } else {
                            throw new IllegalArgumentException(
                                    "Expected number but got " + result.getType());
                        }
                    });
        }

        public <R> R readNumber(String text, Function<Number, R> function) {
            return this.withTag(
                    text,
                    result -> {
                        if (result instanceof NBTNumber) {
                            return (R)
                                    ((Function) function).apply(((NBTNumber) result).getAsNumber());
                        } else {
                            throw new IllegalArgumentException(
                                    "Expected number but got " + result.getType());
                        }
                    });
        }

        public void useUTF(String text, Consumer<String> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(
                                                            result, NBTType.STRING)
                                                    .getValue()));
        }

        public <R> R readUTF(String text, Function<String, R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                                    result, NBTType.STRING)
                                                            .getValue()));
        }

        @Nullable
        public String getUTF(String text) {
            NBT nBT;
            return (nBT = this.compound.getTagOrNull(text)) == null
                    ? null
                    : AdventureNBTSerializer.requireType(nBT, NBTType.STRING).getValue();
        }

        @Nullable
        public Number getNumber(String text) {
            NBT nBT;
            if ((nBT = this.compound.getTagOrNull(text)) == null) {
                return null;
            } else if (nBT instanceof NBTNumber) {
                return ((NBTNumber) nBT).getAsNumber();
            } else {
                throw new IllegalArgumentException("Expected number but got " + nBT.getType());
            }
        }

        @Nullable
        public Boolean getBoolean(String text) {
            Number number;
            return (number = this.getNumber(text)) == null ? null : number.byteValue() != 0;
        }

        public void useByteArray(String text, Consumer<byte[]> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(
                                                            result, NBTType.BYTE_ARRAY)
                                                    .getValue()));
        }

        public <R> R readByteArray(String text, Function<byte[], R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                                    result, NBTType.BYTE_ARRAY)
                                                            .getValue()));
        }

        public void useIntArray(String text, Consumer<int[]> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(
                                                            result, NBTType.INT_ARRAY)
                                                    .getValue()));
        }

        public <R> R readIntArray(String text, Function<int[], R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                                    result, NBTType.INT_ARRAY)
                                                            .getValue()));
        }

        public void useLongArray(String text, Consumer<long[]> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(
                                                            result, NBTType.LONG_ARRAY)
                                                    .getValue()));
        }

        public <R> R readLongArray(String text, Function<long[], R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                                    result, NBTType.LONG_ARRAY)
                                                            .getValue()));
        }

        public void useCompound(String text, Consumer<NBTCompound> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(
                                                    result, NBTType.COMPOUND)));
        }

        public <R> R readCompound(String text, Function<NBTCompound, R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                            result, NBTType.COMPOUND)));
        }

        public void useList(String text, Consumer<List<?>> consumer) {
            this.useTag(
                    text,
                    result ->
                            ((Consumer) consumer)
                                    .accept(
                                            AdventureNBTSerializer.requireType(result, NBTType.LIST)
                                                    .getTags()));
        }

        public <R> R readList(String text, Function<List<?>, R> function) {
            return this.withTag(
                    text,
                    result ->
                            (R)
                                    ((Function) function)
                                            .apply(
                                                    AdventureNBTSerializer.requireType(
                                                                    result, NBTType.LIST)
                                                            .getTags()));
        }

        public void use(String text, Consumer<NBT> consumer) {
            this.useTag(text, consumer);
        }

        public <R> R read(String text, NbtDecoder<R> nbtDecoder) {
            return this.withTag(
                    text, result -> (R) ((NbtDecoder) nbtDecoder).decode(result, this.wrapper));
        }

        public <R> R read(String text, Function<NBT, R> function) {
            return this.withTag(text, function);
        }

        public AdventureNBTSerializer.NBTReader child(String text) {
            return this.withTag(
                    text,
                    result -> {
                        NBTCompound nBTCompound =
                                AdventureNBTSerializer.requireType(result, NBTType.COMPOUND);
                        return new AdventureNBTSerializer.NBTReader(this.wrapper, nBTCompound);
                    });
        }

        public NBTType<?> type(String text) {
            return this.withTag(text, NBT::getType);
        }

        private void useTag(String text, Consumer<NBT> consumer) {
            NBT nBT;
            if ((nBT = this.compound.getTagOrNull(text)) != null) {
                consumer.accept(nBT);
            }
        }

        private <R> R withTag(String text, Function<NBT, R> function) {
            NBT nBT;
            return (R)
                    ((nBT = this.compound.getTagOrNull(text)) == null ? null : function.apply(nBT));
        }
    }

    static class NBTWriter {

        private final NBTCompound compound;

        public NBTWriter(NBTCompound nBTCompound) {
            this.compound = nBTCompound;
        }

        public void writeBoolean(String text, boolean matches) {
            this.compound.setTag(text, new NBTByte((byte) (matches ? 1 : 0)));
        }

        public void writeByte(String text, byte value) {
            this.compound.setTag(text, new NBTByte(value));
        }

        public void writeShort(String text, short value) {
            this.compound.setTag(text, new NBTShort(value));
        }

        public void writeInt(String text, int value) {
            this.compound.setTag(text, new NBTInt(value));
        }

        public void writeLong(String text, long value) {
            this.compound.setTag(text, new NBTLong(value));
        }

        public void writeFloat(String text, float value) {
            this.compound.setTag(text, new NBTFloat(value));
        }

        public void writeDouble(String text, double value) {
            this.compound.setTag(text, new NBTDouble(value));
        }

        public void writeUTF(String text, String text2) {
            this.compound.setTag(text, new NBTString(text2));
        }

        public void writeByteArray(String text, byte[] values) {
            this.compound.setTag(text, new NBTByteArray(values));
        }

        public void writeIntArray(String text, int[] values) {
            this.compound.setTag(text, new NBTIntArray(values));
        }

        public void writeLongArray(String text, long[] values) {
            this.compound.setTag(text, new NBTLongArray(values));
        }

        public <T extends NBT> void writeList(String text, NBTType<T> nBTType, List<T> values) {
            this.compound.setTag(text, new NBTList(nBTType, values));
        }

        public void writeCompound(String text, NBTCompound nBTCompound) {
            this.compound.setTag(text, nBTCompound);
        }

        public void write(String text, NBT nBT) {
            this.compound.setTag(text, nBT);
        }

        public AdventureNBTSerializer.NBTWriter child(String text) {
            NBTCompound nBTCompound = new NBTCompound();
            this.compound.setTag(text, nBTCompound);
            return new AdventureNBTSerializer.NBTWriter(nBTCompound);
        }
    }
}
