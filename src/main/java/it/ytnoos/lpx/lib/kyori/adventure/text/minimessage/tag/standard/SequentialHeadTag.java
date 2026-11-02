package it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.standard;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ObjectComponent;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.Context;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.ParsingException;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.Emitable;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.internal.serializer.SerializableResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.Tag;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import it.ytnoos.lpx.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.ObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import it.ytnoos.lpx.lib.kyori.adventure.util.TriState;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;

final class SequentialHeadTag {

    private static final Pattern UUIDv4_PATTERN =
            Pattern.compile(
                    "[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ABCD][0-9a-f]{3}-[0-9a-f]{12}", 2);

    static final TagResolver RESOLVER =
            SerializableResolver.claimingComponent(
                    "head", SequentialHeadTag::create, SequentialHeadTag::claimComponent);

    private SequentialHeadTag() {}

    static Tag create(ArgumentQueue argumentQueue, Context context) throws ParsingException {
        if (!argumentQueue.hasNext()) {
            return Tag.selfClosingInserting(Component.object(ObjectContents.playerHead().build()));
        }
        Tag.Argument argument;
        String text = (argument = argumentQueue.pop()).value();
        TriState triState;
        if (!argumentQueue.hasNext()) {
            if ((triState = argumentToTriState(argument)) != TriState.NOT_SET) {
                return Tag.selfClosingInserting(
                        Component.object(
                                ObjectContents.playerHead()
                                        .hat(triState.toBooleanOrElse(true))
                                        .build()));
            }
        } else {
            triState = argumentToTriState(argumentQueue.pop());
        }
        if (argumentQueue.hasNext()) {
            throw context.newException("Too many arguments present", argumentQueue);
        } else if (UUIDv4_PATTERN.matcher(text).matches()) {
            return Tag.selfClosingInserting(
                    Component.object(
                            ObjectContents.playerHead()
                                    .id(UUID.fromString(text))
                                    .hat(triState.toBooleanOrElse(true))
                                    .build()));
        } else {
            return text.contains("/") && Key.parseable(text)
                    ? Tag.selfClosingInserting(
                            Component.object(
                                    ObjectContents.playerHead()
                                            .texture(Key.key(text))
                                            .hat(triState.toBooleanOrElse(true))
                                            .build()))
                    : Tag.selfClosingInserting(
                            Component.object(
                                    ObjectContents.playerHead()
                                            .name(text)
                                            .hat(triState.toBooleanOrElse(true))
                                            .build()));
        }
    }

    @Nullable
    static Emitable claimComponent(Component component) {
        if (!(component instanceof ObjectComponent)) {
            return null;
        }
        ObjectContents objectContents;
        if (!((objectContents = ((ObjectComponent) component).contents())
                instanceof PlayerHeadObjectContents)) {
            return null;
        }
        PlayerHeadObjectContents playerHeadObjectContents =
                (PlayerHeadObjectContents) objectContents;
        SequentialHeadTag.PresentType presentType = null;
        if (playerHeadObjectContents.name() != null) {
            presentType = SequentialHeadTag.PresentType.NAME;
        }
        if (playerHeadObjectContents.id() != null) {
            if (presentType != null) {
                return null;
            }
            presentType = SequentialHeadTag.PresentType.ID;
        }
        if (playerHeadObjectContents.texture() != null) {
            if (presentType != null) {
                return null;
            }
            presentType = SequentialHeadTag.PresentType.TEXTURE;
        }
        if (presentType == null) {
            return result -> {
                result.tag("head");
                if (!playerHeadObjectContents.hat()) {
                    result.argument(Boolean.toString(playerHeadObjectContents.hat()));
                }
            };
        } else {
            SequentialHeadTag.PresentType presentType2 = presentType;
            return result2 -> {
                result2.tag("head");
                String text = presentType2.map(playerHeadObjectContents);
                result2.argument(text);
                if (!playerHeadObjectContents.hat()) {
                    result2.argument(Boolean.toString(playerHeadObjectContents.hat()));
                }
            };
        }
    }

    private static TriState argumentToTriState(Tag.Argument argument) {
        if (argument.isTrue()) {
            return TriState.TRUE;
        } else {
            return argument.isFalse() ? TriState.FALSE : TriState.NOT_SET;
        }
    }

    private enum PresentType {
        NAME(PlayerHeadObjectContents::name),
        ID(result -> Objects.requireNonNull(result.id()).toString()),
        TEXTURE(result -> Objects.requireNonNull(result.texture()).asMinimalString());

        private final Function<PlayerHeadObjectContents, String> mappingFunction;

        PresentType(Function<PlayerHeadObjectContents, String> function) {
            this.mappingFunction = function;
        }

        public String map(PlayerHeadObjectContents playerHeadObjectContents) {
            return this.mappingFunction.apply(playerHeadObjectContents);
        }
    }
}
