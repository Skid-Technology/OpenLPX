package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.VersionedRegistry;

import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Collection;

public final class ChatTypes {

    private static final VersionedRegistry<ChatType> REGISTRY =
            new VersionedRegistry<>("chat_type");

    public static final ChatType CHAT = define("chat");

    public static final ChatType SAY_COMMAND =
            define("say_command", ChatTypeDecoration.withSender("chat.type.announcement"));

    public static final ChatType MSG_COMMAND_INCOMING =
            define(
                    "msg_command_incoming",
                    ChatTypeDecoration.incomingDirectMessage("commands.message.display.incoming"));

    public static final ChatType MSG_COMMAND_OUTGOING =
            define(
                    "msg_command_outgoing",
                    ChatTypeDecoration.outgoingDirectMessage("commands.message.display.outgoing"));

    public static final ChatType TEAM_MSG_COMMAND_INCOMING =
            define(
                    "team_msg_command_incoming",
                    ChatTypeDecoration.teamMessage("chat.type.team.text"));

    public static final ChatType TEAM_MSG_COMMAND_OUTGOING =
            define(
                    "team_msg_command_outgoing",
                    ChatTypeDecoration.teamMessage("chat.type.team.sent"));

    public static final ChatType EMOTE_COMMAND =
            define(
                    "emote_command",
                    ChatTypeDecoration.withSender("chat.type.emote"),
                    ChatTypeDecoration.withSender("chat.type.emote"));

    public static final ChatType RAW = define("raw");

    @Deprecated public static final ChatType SYSTEM = define("system");

    @Deprecated public static final ChatType GAME_INFO = define("game_info");

    @Deprecated public static final ChatType MSG_COMMAND = define("msg_command");

    @Deprecated public static final ChatType TEAM_MSG_COMMAND = define("team_msg_command");

    private ChatTypes() {}

    @Internal
    public static ChatType define(String text) {
        return define(text, ChatTypeDecoration.withSender("chat.type.text"));
    }

    @Internal
    public static ChatType define(String text, ChatTypeDecoration chatTypeDecoration) {
        return define(
                text, chatTypeDecoration, ChatTypeDecoration.withSender("chat.type.text.narrate"));
    }

    @Internal
    public static ChatType define(
            String text,
            ChatTypeDecoration chatTypeDecoration,
            ChatTypeDecoration chatTypeDecoration2) {
        return REGISTRY.define(
                text,
                result -> new StaticChatType(result, chatTypeDecoration, chatTypeDecoration2));
    }

    public static VersionedRegistry<ChatType> getRegistry() {
        return REGISTRY;
    }

    public static ChatType getByName(String text) {
        return REGISTRY.getByName(text);
    }

    public static ChatType getById(ClientVersion clientVersion, int value) {
        return REGISTRY.getById(clientVersion, value);
    }

    public static Collection<ChatType> values() {
        return REGISTRY.getEntries();
    }

    static {
        REGISTRY.unloadMappings();
    }
}
