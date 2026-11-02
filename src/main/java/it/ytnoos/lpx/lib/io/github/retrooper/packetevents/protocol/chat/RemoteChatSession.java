package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.chat;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.PublicProfileKey;

import java.util.UUID;

public class RemoteChatSession {

    private final UUID sessionId;

    private final PublicProfileKey publicProfileKey;

    public RemoteChatSession(UUID sessionId, PublicProfileKey publicProfileKey2) {
        this.sessionId = sessionId;
        this.publicProfileKey = publicProfileKey2;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public PublicProfileKey getPublicProfileKey() {
        return this.publicProfileKey;
    }
}
