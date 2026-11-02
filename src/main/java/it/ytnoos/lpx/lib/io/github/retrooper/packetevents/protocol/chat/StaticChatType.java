package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.mapper.AbstractMappedEntity;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.util.mappings.TypesBuilderData;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class StaticChatType extends AbstractMappedEntity implements ChatType {

    @UnknownNullability("only nullable for 1.19")
    private final ChatTypeDecoration chatDecoration;

    @Nullable private final ChatTypeDecoration overlayDecoration;

    @UnknownNullability("only nullable for 1.19")
    private final ChatTypeDecoration narrationDecoration;

    private final ChatType.@Nullable NarrationPriority narrationPriority;

    public StaticChatType(
            ChatTypeDecoration chatTypeDecoration, ChatTypeDecoration chatTypeDecoration2) {
        this(null, chatTypeDecoration, null, chatTypeDecoration2, null);
    }

    @Internal
    public StaticChatType(
            @Nullable TypesBuilderData typesBuilderData,
            ChatTypeDecoration chatTypeDecoration,
            ChatTypeDecoration chatTypeDecoration2) {
        this(typesBuilderData, chatTypeDecoration, null, chatTypeDecoration2, null);
    }

    public StaticChatType(
            @UnknownNullability("only nullable for 1.19") ChatTypeDecoration chatTypeDecoration,
            @Nullable ChatTypeDecoration chatTypeDecoration2,
            @UnknownNullability("only nullable for 1.19") ChatTypeDecoration chatTypeDecoration3,
            ChatType.@Nullable NarrationPriority narrationPriority) {
        this(null, chatTypeDecoration, chatTypeDecoration2, chatTypeDecoration3, narrationPriority);
    }

    @Internal
    public StaticChatType(
            @Nullable TypesBuilderData typesBuilderData,
            @Nullable ChatTypeDecoration chatTypeDecoration,
            @Nullable ChatTypeDecoration chatTypeDecoration2,
            @Nullable ChatTypeDecoration chatTypeDecoration3,
            ChatType.@Nullable NarrationPriority narrationPriority2) {
        super(typesBuilderData);
        this.chatDecoration = chatTypeDecoration;
        this.overlayDecoration = chatTypeDecoration2;
        this.narrationDecoration = chatTypeDecoration3;
        this.narrationPriority = narrationPriority2;
    }

    public ChatType copy(@Nullable TypesBuilderData typesBuilderData) {
        return new StaticChatType(
                typesBuilderData,
                this.chatDecoration,
                this.overlayDecoration,
                this.narrationDecoration,
                this.narrationPriority);
    }

    @Override
    @UnknownNullability("only nullable for 1.19")
    public ChatTypeDecoration getChatDecoration() {
        return this.chatDecoration;
    }

    @Override
    @Nullable
    public ChatTypeDecoration getOverlayDecoration() {
        return this.overlayDecoration;
    }

    @Override
    @UnknownNullability("only nullable for 1.19")
    public ChatTypeDecoration getNarrationDecoration() {
        return this.narrationDecoration;
    }

    @Override
    public ChatType.@Nullable NarrationPriority getNarrationPriority() {
        return this.narrationPriority;
    }

    @Override
    public boolean deepEquals(@Nullable Object result) {
        if (this == result) {
            return true;
        } else if (!(result instanceof StaticChatType)) {
            return false;
        } else {
            StaticChatType staticChatType = (StaticChatType) result;
            if (!Objects.equals(this.chatDecoration, staticChatType.chatDecoration)) {
                return false;
            } else if (!Objects.equals(this.overlayDecoration, staticChatType.overlayDecoration)) {
                return false;
            } else {
                return !Objects.equals(this.narrationDecoration, staticChatType.narrationDecoration)
                        ? false
                        : this.narrationPriority == staticChatType.narrationPriority;
            }
        }
    }

    @Override
    public int deepHashCode() {
        return Objects.hash(
                this.chatDecoration,
                this.overlayDecoration,
                this.narrationDecoration,
                this.narrationPriority);
    }

    @Override
    public String toString() {
        return "StaticChatType{chatDecoration="
                + this.chatDecoration
                + ", overlayDecoration="
                + this.overlayDecoration
                + ", narrationDecoration="
                + this.narrationDecoration
                + ", narrationPriority="
                + this.narrationPriority
                + -2051325206;
    }
}
