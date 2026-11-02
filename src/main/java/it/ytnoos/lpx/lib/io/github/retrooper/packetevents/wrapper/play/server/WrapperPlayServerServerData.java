package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class WrapperPlayServerServerData extends PacketWrapper<WrapperPlayServerServerData> {

    @Nullable private Component motd;

    @Nullable private String icon;

    private boolean previewsChat;

    private boolean enforceSecureChat;

    public WrapperPlayServerServerData(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerServerData(
            @Nullable Component component, @Nullable String text, boolean matches) {
        this(component, text, matches, false);
    }

    public WrapperPlayServerServerData(
            @Nullable Component component,
            @Nullable String icon,
            boolean previewsChat,
            boolean enforceSecureChat) {
        super(PacketType.Play.Server.SERVER_DATA);
        this.motd = component;
        this.icon = icon;
        this.previewsChat = previewsChat;
        this.enforceSecureChat = enforceSecureChat;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_4) || this.readBoolean()) {
            this.motd = this.readComponent();
        }
        if (this.readBoolean()) {
            if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_4)) {
                byte[] values = this.readByteArray();
                this.icon =
                        "data:image/png;base64,"
                                + new String(
                                        Base64.getEncoder().encode(values), StandardCharsets.UTF_8);
            } else {
                this.icon = this.readString();
            }
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_19_3)) {
            this.previewsChat = this.readBoolean();
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.enforceSecureChat = this.readBoolean();
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_4)) {
            this.writeComponent(this.motd);
            byte[] values;
            if (this.icon == null) {
                values = null;
            } else {
                String text = this.icon.substring("data:image/png;base64,".length());
                values = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
            }
            this.writeOptional(values, PacketWrapper::writeByteArray);
        } else {
            this.writeOptional(this.motd, PacketWrapper::writeComponent);
            this.writeOptional(this.icon, PacketWrapper::writeString);
        }
        if (this.serverVersion.isOlderThan(ServerVersion.V_1_19_3)) {
            this.writeBoolean(this.previewsChat);
        }
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_19_1)
                && this.serverVersion.isOlderThan(ServerVersion.V_1_20_5)) {
            this.writeBoolean(this.enforceSecureChat);
        }
    }

    public void copy(WrapperPlayServerServerData wrapperPlayServerServerData) {
        this.motd = wrapperPlayServerServerData.motd;
        this.icon = wrapperPlayServerServerData.icon;
        this.previewsChat = wrapperPlayServerServerData.previewsChat;
        this.enforceSecureChat = wrapperPlayServerServerData.enforceSecureChat;
    }

    @Nullable
    public Component getMOTD() {
        return this.motd;
    }

    public void setMOTD(@Nullable Component component) {
        this.motd = component;
    }

    public Optional<String> getIcon() {
        return Optional.ofNullable(this.icon);
    }

    public void setIcon(@Nullable String icon) {
        this.icon = icon;
    }

    public boolean isPreviewsChat() {
        return this.previewsChat;
    }

    public void setPreviewsChat(boolean previewsChat) {
        this.previewsChat = previewsChat;
    }

    public boolean isEnforceSecureChat() {
        return this.enforceSecureChat;
    }

    public void setEnforceSecureChat(boolean enforceSecureChat) {
        this.enforceSecureChat = enforceSecureChat;
    }
}
