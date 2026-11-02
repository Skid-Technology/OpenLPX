package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_1;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.CopyableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.DeepComparableEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.MappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBT;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.nbt.NBTString;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.ClientVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.adventure.AdventureIndexUtil;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;
import it.ytnoos.lpx.lib.kyori.adventure.util.Index;

import org.jetbrains.annotations.ApiStatus.Obsolete;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public interface ChatType extends MappedEntity, CopyableEntity<ChatType>, DeepComparableEntity {

    @UnknownNullability("only nullable for 1.19")
    ChatTypeDecoration getChatDecoration();

    @Obsolete(since = "1.19.1")
    @Nullable ChatTypeDecoration getOverlayDecoration();

    @UnknownNullability("only nullable for 1.19")
    ChatTypeDecoration getNarrationDecoration();

    @Obsolete(since = "1.19.1")
    ChatType.@Nullable NarrationPriority getNarrationPriority();

    static ChatType readDirect(PacketWrapper<?> packetWrapper) {
        ChatTypeDecoration chatTypeDecoration = ChatTypeDecoration.read(packetWrapper);
        ChatTypeDecoration chatTypeDecoration2 = ChatTypeDecoration.read(packetWrapper);
        return new StaticChatType(chatTypeDecoration, chatTypeDecoration2);
    }

    static void writeDirect(PacketWrapper<?> packetWrapper, ChatType chatType) {
        ChatTypeDecoration.write(packetWrapper, chatType.getChatDecoration());
        ChatTypeDecoration.write(packetWrapper, chatType.getNarrationDecoration());
    }

    static ChatType decode(
            NBT nBT, ClientVersion clientVersion, @Nullable TypesBuilderData typesBuilderData) {
        NBTCompound nBTCompound;
        NBTCompound compoundTagOrNull =
                (nBTCompound = (NBTCompound) nBT).getCompoundTagOrNull("chat");
        NBTCompound compoundTagOrNull2 = nBTCompound.getCompoundTagOrNull("narration");
        ChatTypeDecoration chatTypeDecoration = null;
        ChatType.NarrationPriority narrationPriority = null;
        if (clientVersion.isOlderThan(ClientVersion.V_1_19_1)) {
            NBTCompound nBTCompound2;
            if ((nBTCompound2 = nBTCompound.getCompoundTagOrNull("overlay")) != null
                    && (nBTCompound2 = nBTCompound2.getCompoundTagOrNull("description")) != null) {
                chatTypeDecoration = ChatTypeDecoration.decode(nBTCompound2, clientVersion);
            }
            if (compoundTagOrNull != null) {
                compoundTagOrNull = compoundTagOrNull.getCompoundTagOrNull("description");
            }
            if (compoundTagOrNull2 != null) {
                narrationPriority =
                        AdventureIndexUtil.indexValueOrThrow(
                                ChatType.NarrationPriority.ID_INDEX,
                                compoundTagOrNull2.getStringTagValueOrThrow("priority"));
                compoundTagOrNull2 = compoundTagOrNull2.getCompoundTagOrNull("description");
            }
        } else {
            Objects.requireNonNull(compoundTagOrNull, "NBT chat does not exist");
            Objects.requireNonNull(compoundTagOrNull2, "NBT narration does not exist");
        }
        ChatTypeDecoration chatTypeDecoration2 =
                compoundTagOrNull == null
                        ? null
                        : ChatTypeDecoration.decode(compoundTagOrNull, clientVersion);
        ChatTypeDecoration chatTypeDecoration3 =
                compoundTagOrNull2 == null
                        ? null
                        : ChatTypeDecoration.decode(compoundTagOrNull2, clientVersion);
        return new StaticChatType(
                typesBuilderData,
                chatTypeDecoration2,
                chatTypeDecoration,
                chatTypeDecoration3,
                narrationPriority);
    }

    static NBT encode(ChatType chatType, ClientVersion clientVersion) {
        NBTCompound nBTCompound = new NBTCompound();
        NBT nBT =
                chatType.getChatDecoration() == null
                        ? null
                        : ChatTypeDecoration.encode(chatType.getChatDecoration(), clientVersion);
        NBT nBT2 =
                chatType.getNarrationDecoration() == null
                        ? null
                        : ChatTypeDecoration.encode(
                                chatType.getNarrationDecoration(), clientVersion);
        if (clientVersion.isOlderThan(ClientVersion.V_1_19_1)) {
            ChatTypeDecoration chatTypeDecoration;
            if ((chatTypeDecoration = chatType.getOverlayDecoration()) != null) {
                NBTCompound nBTCompound2;
                (nBTCompound2 = new NBTCompound())
                        .setTag(
                                "description",
                                ChatTypeDecoration.encode(chatTypeDecoration, clientVersion));
                nBTCompound.setTag("overlay", nBTCompound2);
            }
            if (nBT2 != null) {
                NBTCompound nBTCompound3;
                (nBTCompound3 = new NBTCompound()).setTag("description", nBT2);
                if (chatType.getNarrationPriority() != null) {
                    nBTCompound3.setTag(
                            "priority", new NBTString(chatType.getNarrationPriority().getId()));
                }
                nBT2 = nBTCompound3;
            }
            if (nBT != null) {
                NBTCompound nBTCompound4;
                (nBTCompound4 = new NBTCompound()).setTag("description", nBT);
                nBT = nBTCompound4;
            }
        }
        if (nBT != null) {
            nBTCompound.setTag("chat", nBT);
        }
        if (nBT2 != null) {
            nBTCompound.setTag("narration", nBT2);
        }
        return nBTCompound;
    }

    class Bound extends ChatMessage_v1_19_1.ChatTypeBoundNetwork {

        public Bound(ChatType chatType, Component component, @Nullable Component component2) {
            super(chatType, component, component2);
        }
    }

    @Obsolete(since = "1.19.1")
    enum NarrationPriority {
        CHAT("chat"),
        SYSTEM("system");

        public static final Index<String, ChatType.NarrationPriority> ID_INDEX =
                Index.create(ChatType.NarrationPriority.class, ChatType.NarrationPriority::getId);

        private final String id;

        NarrationPriority(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }
}
