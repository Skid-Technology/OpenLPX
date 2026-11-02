package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WrapperPlayServerResourcePackSend
        extends PacketWrapper<WrapperPlayServerResourcePackSend> {

    public static final int MAX_HASH_LENGTH = 40;

    private UUID packId;

    private String url;

    private String hash;

    private boolean required;

    private Component prompt;

    public WrapperPlayServerResourcePackSend(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerResourcePackSend(
            String text, String text2, boolean matches, @Nullable Component component) {
        this(UUID.randomUUID(), text, text2, matches, component);
    }

    public WrapperPlayServerResourcePackSend(
            UUID packId, String url, String hash, boolean required, @Nullable Component component) {
        super(PacketType.Play.Server.RESOURCE_PACK_SEND);
        if (hash.length() > 40) {
            throw new IllegalArgumentException(
                    "Hash is too long (max 40, was " + hash.length() + ")");
        }
        this.packId = packId;
        this.url = url;
        this.hash = hash;
        this.required = required;
        this.prompt = component;
    }

    @Override
    public void read() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.packId = this.readUUID();
        }
        this.url = this.readString();
        this.hash = this.readString(40);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.required = this.readBoolean();
            if (this.readBoolean()) {
                this.prompt = this.readComponent();
            }
        }
    }

    @Override
    public void write() {
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_20_3)) {
            this.writeUUID(this.packId);
        }
        this.writeString(this.url);
        this.writeString(this.hash, 40);
        if (this.serverVersion.isNewerThanOrEquals(ServerVersion.V_1_17)) {
            this.writeBoolean(this.required);
            this.writeBoolean(this.prompt != null);
            if (this.prompt != null) {
                this.writeComponent(this.prompt);
            }
        }
    }

    public void copy(WrapperPlayServerResourcePackSend wrapperPlayServerResourcePackSend) {
        this.packId = wrapperPlayServerResourcePackSend.packId;
        this.url = wrapperPlayServerResourcePackSend.url;
        this.hash = wrapperPlayServerResourcePackSend.hash;
        this.required = wrapperPlayServerResourcePackSend.required;
        this.prompt = wrapperPlayServerResourcePackSend.prompt;
    }

    public UUID getPackId() {
        return this.packId;
    }

    public void setPackId(UUID packId) {
        this.packId = packId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Component getPrompt() {
        return this.prompt;
    }

    public void setPrompt(Component component) {
        this.prompt = component;
    }
}
