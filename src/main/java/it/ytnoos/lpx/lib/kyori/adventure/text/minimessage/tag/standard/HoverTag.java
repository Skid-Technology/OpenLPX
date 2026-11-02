package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.key.InvalidKeyException;
import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.nbt.api.BinaryTagHolder;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.DataComponentValue;
import it.ytnoos.lpx.lib.kyori.adventure.text.event.HoverEvent;
import it.ytnoos.lpx.lib.kyori.adventure.text.format.Style;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.StyleClaim;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.TokenEmitter;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.UUID;

final class HoverTag {

    static final TagResolver RESOLVER =
            SerializableResolver.claimingStyle(
                    "hover",
                    HoverTag::create,
                    StyleClaim.claim("hover", Style::hoverEvent, HoverTag::emit));

    private HoverTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        String text =
                argumentQueue.popOr("Hover event requires an action as its first argument").value();
        HoverEvent.Action action;
        HoverTag.ActionHandler actionHandler2;
        if ((actionHandler2 = actionHandler(action = HoverEvent.Action.NAMES.value(text)))
                == null) {
            throw context.newException(
                    "Don't know how to turn '" + argumentQueue + "' into a hover event",
                    argumentQueue);
        } else {
            return Tag.styling(
                    HoverEvent.hoverEvent(action, actionHandler2.parse(argumentQueue, context)));
        }
    }

    static void emit(HoverEvent<?> hoverEvent, TokenEmitter tokenEmitter) {
        HoverTag.ActionHandler actionHandler2 = actionHandler(hoverEvent.action());
        tokenEmitter.tag("hover").argument(HoverEvent.Action.NAMES.key(hoverEvent.action()));
        actionHandler2.emit(hoverEvent.value(), tokenEmitter);
    }

    @Nullable
    static <V> HoverTag.ActionHandler<V> actionHandler(HoverEvent.Action<V> action) {
        HoverTag.ActionHandler actionHandler2 = null;
        if (action == HoverEvent.Action.SHOW_TEXT) {
            actionHandler2 = HoverTag.ShowText.INSTANCE;
        } else if (action == HoverEvent.Action.SHOW_ITEM) {
            actionHandler2 = HoverTag.ShowItem.INSTANCE;
        } else if (action == HoverEvent.Action.SHOW_ENTITY) {
            actionHandler2 = HoverTag.ShowEntity.INSTANCE;
        }
        return actionHandler2;
    }

    @NotNull
    static String compactAsString(@NotNull Key key) {
        return key.namespace().equals("minecraft") ? key.value() : key.asString();
    }

    interface ActionHandler<V> {

        @NotNull
        V parse(@NotNull ArgumentQueue argumentQueue, @NotNull Context context)
                throws ParsingException;

        void emit(V v, TokenEmitter tokenEmitter);
    }

    static final class ShowEntity implements HoverTag.ActionHandler<HoverEvent.ShowEntity> {

        static final HoverTag.ShowEntity INSTANCE = new HoverTag.ShowEntity();

        private ShowEntity() {}

        public HoverEvent.@NotNull ShowEntity parse(
                @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
                throws ParsingException {
            try {
                Key key2 =
                        Key.key(argumentQueue.popOr("Show entity needs a type argument").value());
                UUID uUID =
                        UUID.fromString(
                                argumentQueue.popOr("Show entity needs an entity UUID").value());
                if (argumentQueue.hasNext()) {
                    Component component = context.deserialize(argumentQueue.pop().value());
                    return HoverEvent.ShowEntity.showEntity(key2, uUID, component);
                } else {
                    return HoverEvent.ShowEntity.showEntity(key2, uUID);
                }
            } catch (IllegalArgumentException | InvalidKeyException exception) {
                throw context.newException(
                        "Exception parsing show_entity hover", exception, argumentQueue);
            }
        }

        public void emit(HoverEvent.ShowEntity showEntity, TokenEmitter tokenEmitter) {
            tokenEmitter
                    .argument(HoverTag.compactAsString(showEntity.type()))
                    .argument(showEntity.id().toString());
            if (showEntity.name() != null) {
                tokenEmitter.argument(showEntity.name());
            }
        }
    }

    static final class ShowItem implements HoverTag.ActionHandler<HoverEvent.ShowItem> {

        private static final HoverTag.ShowItem INSTANCE = new HoverTag.ShowItem();

        private ShowItem() {}

        public HoverEvent.@NotNull ShowItem parse(
                @NotNull ArgumentQueue argumentQueue, @NotNull Context context)
                throws ParsingException {
            try {
                Key key2 =
                        Key.key(
                                argumentQueue
                                        .popOr("Show item hover needs at least an item ID")
                                        .value());
                int value2;
                if (argumentQueue.hasNext()) {
                    OptionalInt optionalInt = argumentQueue.pop().asInt();
                    value2 =
                            optionalInt.orElseThrow(
                                    () ->
                                            context.newException(
                                                    "The count argument was not a valid integer"));
                } else {
                    value2 = 1;
                }
                int value3 = value2;
                if (!argumentQueue.hasNext()) {
                    return HoverEvent.ShowItem.showItem(key2, value3);
                }
                String text;
                if ((text = argumentQueue.peek().value()).startsWith("{")) {
                    argumentQueue.pop();
                    return legacyShowItem(key2, value3, text);
                }
                HashMap entries = new HashMap();
                while (argumentQueue.hasNext()) {
                    Key key3 = Key.key(argumentQueue.pop().value());
                    String text2 =
                            argumentQueue.popOr("a value was expected for key " + key3).value();
                    entries.put(key3, BinaryTagHolder.binaryTagHolder(text2));
                }
                return HoverEvent.ShowItem.showItem(key2, value3, entries);
            } catch (InvalidKeyException | NumberFormatException exception) {
                throw context.newException(
                        "Exception parsing show_item hover", exception, argumentQueue);
            }
        }

        private static HoverEvent.@NotNull ShowItem legacyShowItem(
                Key key, int value, String text) {
            return HoverEvent.ShowItem.showItem(key, value, BinaryTagHolder.binaryTagHolder(text));
        }

        public void emit(HoverEvent.ShowItem showItem, TokenEmitter tokenEmitter) {
            tokenEmitter.argument(HoverTag.compactAsString(showItem.item()));
            if (showItem.count() != 1
                    || hasLegacy(showItem)
                    || !showItem.dataComponents().isEmpty()) {
                tokenEmitter.argument(Integer.toString(showItem.count()));
                if (hasLegacy(showItem)) {
                    emitLegacyHover(showItem, tokenEmitter);
                } else {
                    for (Entry<Key, DataComponentValue.TagSerializable> entry :
                            showItem.dataComponentsAs(DataComponentValue.TagSerializable.class)
                                    .entrySet()) {
                        tokenEmitter.argument(((Key) entry.getKey()).asMinimalString());
                        tokenEmitter.argument(
                                ((DataComponentValue.TagSerializable) entry.getValue())
                                        .asBinaryTag()
                                        .string());
                    }
                }
            }
        }

        static boolean hasLegacy(HoverEvent.ShowItem showItem) {
            return showItem.nbt() != null;
        }

        static void emitLegacyHover(HoverEvent.ShowItem showItem, TokenEmitter tokenEmitter) {
            if (showItem.nbt() != null) {
                tokenEmitter.argument(showItem.nbt().string());
            }
        }
    }

    static final class ShowText implements HoverTag.ActionHandler<Component> {

        private static final HoverTag.ShowText INSTANCE = new HoverTag.ShowText();

        private ShowText() {}

        @NotNull
        public Component parse(@NotNull ArgumentQueue argumentQueue, @NotNull Context context)
                throws ParsingException {
            return context.deserialize(
                    argumentQueue.popOr("show_text action requires a message").value());
        }

        public void emit(Component component, TokenEmitter tokenEmitter) {
            tokenEmitter.argument(component);
        }
    }
}
