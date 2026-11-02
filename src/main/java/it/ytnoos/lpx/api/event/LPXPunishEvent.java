package it.ytnoos.lpx.api.event;

import it.ytnoos.lpx.api.check.abstraction.Check;
import it.ytnoos.lpx.api.player.ProtocolPlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LPXPunishEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ProtocolPlayer protocolPlayer;

    private final Check<?> check;

    public LPXPunishEvent(ProtocolPlayer protocolPlayer2, Check<?> check2) {
        this.protocolPlayer = protocolPlayer2;
        this.check = check2;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public ProtocolPlayer getProtocolPlayer() {
        return this.protocolPlayer;
    }

    public Check<?> getCheck() {
        return this.check;
    }
}
