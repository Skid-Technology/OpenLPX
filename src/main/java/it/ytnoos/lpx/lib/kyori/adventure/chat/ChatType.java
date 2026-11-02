package it.ytnoos.lpx.lib.kyori.adventure.chat;

import it.ytnoos.lpx.lib.kyori.adventure.key.Key;
import it.ytnoos.lpx.lib.kyori.adventure.key.Keyed;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.text.ComponentLike;
import it.ytnoos.lpx.lib.kyori.examination.Examinable;
import it.ytnoos.lpx.lib.kyori.examination.ExaminableProperty;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public interface ChatType extends Examinable, Keyed {

    ChatType CHAT = new ChatTypeImpl(Key.key("chat"));

    ChatType SAY_COMMAND = new ChatTypeImpl(Key.key("say_command"));

    ChatType MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("msg_command_incoming"));

    ChatType MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("msg_command_outgoing"));

    ChatType TEAM_MSG_COMMAND_INCOMING = new ChatTypeImpl(Key.key("team_msg_command_incoming"));

    ChatType TEAM_MSG_COMMAND_OUTGOING = new ChatTypeImpl(Key.key("team_msg_command_outgoing"));

    ChatType EMOTE_COMMAND = new ChatTypeImpl(Key.key("emote_command"));

    @NotNull
    static ChatType chatType(@NotNull Keyed keyed) {
        return keyed instanceof ChatType
                ? (ChatType) keyed
                : new ChatTypeImpl(Objects.requireNonNull(keyed, "key").key());
    }

    @Contract(value = "_ -> new", pure = true)
    default ChatType.@NotNull Bound bind(@NotNull ComponentLike componentLike) {
        return this.bind(componentLike, null);
    }

    @Contract(value = "_, _ -> new", pure = true)
    default ChatType.@NotNull Bound bind(
            @NotNull ComponentLike componentLike, @Nullable ComponentLike componentLike2) {
        return new ChatTypeImpl.BoundImpl(
                this,
                Objects.requireNonNull(componentLike.asComponent(), "name"),
                ComponentLike.unbox(componentLike2));
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("key", this.key()));
    }

    @NotNull
    @Override
    Key key();

    interface Bound extends Examinable {

        @Contract(pure = true)
        @NotNull
        ChatType type();

        @Contract(pure = true)
        @NotNull
        Component name();

        @Contract(pure = true)
        @Nullable
        Component target();

        @NotNull
        @Override
        default Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(
                    ExaminableProperty.of("type", this.type()),
                    ExaminableProperty.of("name", this.name()),
                    ExaminableProperty.of("target", this.target()));
        }
    }
}
