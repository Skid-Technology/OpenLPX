package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat.ChatTypes;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WrapperPlayServerSystemChatMessage
        extends PacketWrapper<WrapperPlayServerSystemChatMessage> {

    @Deprecated public static boolean HANDLE_JSON = true;

    @Nullable private ChatType type;

    private boolean overlay;

    private Component message;

    public WrapperPlayServerSystemChatMessage(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    @Deprecated
    public WrapperPlayServerSystemChatMessage(@NotNull ChatType chatType, Component component) {
        super(PacketType.Play.Server.SYSTEM_CHAT_MESSAGE);
        this.type = chatType;
        if (chatType == ChatTypes.GAME_INFO) {
            this.overlay = true;
        }
        this.message = component;
    }

    @Deprecated
    public WrapperPlayServerSystemChatMessage(@NotNull ChatType chatType, String text) {
        super(PacketType.Play.Server.SYSTEM_CHAT_MESSAGE);
        this.message = this.getSerializers().fromJson(text);
        this.type = chatType;
        if (chatType == ChatTypes.GAME_INFO) {
            this.overlay = true;
        }
    }

    public WrapperPlayServerSystemChatMessage(boolean overlay, Component component) {
        super(PacketType.Play.Server.SYSTEM_CHAT_MESSAGE);
        this.message = component;
        this.overlay = overlay;
        this.type = overlay ? ChatTypes.GAME_INFO : ChatTypes.SYSTEM;
    }

    @Deprecated
    public WrapperPlayServerSystemChatMessage(boolean overlay, String text) {
        super(PacketType.Play.Server.SYSTEM_CHAT_MESSAGE);
        this.message = this.getSerializers().fromJson(text);
        this.overlay = overlay;
        this.type = overlay ? ChatTypes.GAME_INFO : ChatTypes.SYSTEM;
    }

    @Override
    public void read() {
        this.message = this.readComponent();
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
            this.overlay = this.readBoolean();
        } else {
            this.type = this.readMappedEntity(ChatTypes.getRegistry());
        }
    }

    @Override
    public void write() {
        this.writeComponent(this.message);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)) {
            this.writeBoolean(this.overlay);
        } else if (this.type == null) {
            if (this.overlay) {
                this.writeMappedEntity(ChatTypes.GAME_INFO);
            } else {
                this.writeMappedEntity(ChatTypes.SYSTEM);
            }
        } else {
            this.writeMappedEntity(this.type);
        }
    }

    public void copy(WrapperPlayServerSystemChatMessage wrapperPlayServerSystemChatMessage) {
        this.type = wrapperPlayServerSystemChatMessage.type;
        this.overlay = wrapperPlayServerSystemChatMessage.overlay;
        this.message = wrapperPlayServerSystemChatMessage.message;
    }

    @Deprecated
    @Nullable
    public ChatType getType() {
        return this.type;
    }

    @Deprecated
    public void setType(@Nullable ChatType chatType) {
        this.type = chatType;
    }

    @Deprecated
    public String getMessageJson() {
        return this.getSerializers().asJson(this.getMessage());
    }

    @Deprecated
    public void setMessageJson(String text) {
        this.setMessage(this.getSerializers().fromJson(text));
    }

    public Component getMessage() {
        return this.message;
    }

    public void setMessage(Component component) {
        this.message = component;
    }

    public boolean isOverlay() {
        return this.overlay;
    }

    public void setOverlay(boolean overlay) {
        this.overlay = overlay;
    }
}
