package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MessageSender {

    @NotNull private UUID uuid;

    @Nullable private Component displayName;

    @Nullable private Component teamName;

    public MessageSender(
            @NotNull UUID uuid, @Nullable Component component, @Nullable Component component2) {
        this.uuid = uuid;
        this.displayName = component;
        this.teamName = component2;
    }

    public MessageSender(@Nullable Component component, @Nullable Component component2) {
        this(new UUID(0L, 0L), component, component2);
    }

    public MessageSender() {
        this(null, null);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public Component getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(@Nullable Component component) {
        this.displayName = component;
    }

    @Nullable
    public Component getTeamName() {
        return this.teamName;
    }

    public void setTeamName(@Nullable Component component) {
        this.teamName = component;
    }
}
