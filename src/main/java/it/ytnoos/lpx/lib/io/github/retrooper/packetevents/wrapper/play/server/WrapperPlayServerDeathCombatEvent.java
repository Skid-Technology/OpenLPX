package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerDeathCombatEvent
        extends PacketWrapper<WrapperPlayServerDeathCombatEvent> {

    private int playerId;

    private Integer entityId;

    private Component deathMessage;

    public WrapperPlayServerDeathCombatEvent(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDeathCombatEvent(
            int playerId, @Nullable Integer entityId, Component component) {
        super(PacketType.Play.Server.DEATH_COMBAT_EVENT);
        this.playerId = playerId;
        this.entityId = entityId;
        this.deathMessage = component;
    }

    @Override
    public void read() {
        this.playerId = this.readVarInt();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            this.entityId = this.readInt();
        }
        this.deathMessage = this.readComponent();
    }

    @Override
    public void write() {
        this.writeVarInt(this.playerId);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            int value = this.entityId != null ? this.entityId : 0;
            this.writeInt(value);
        }
        this.writeComponent(this.deathMessage);
    }

    public void copy(WrapperPlayServerDeathCombatEvent wrapperPlayServerDeathCombatEvent) {
        this.playerId = wrapperPlayServerDeathCombatEvent.playerId;
        this.entityId = wrapperPlayServerDeathCombatEvent.entityId;
        this.deathMessage = wrapperPlayServerDeathCombatEvent.deathMessage;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Optional<Integer> getEntityId() {
        return Optional.ofNullable(this.entityId);
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public Component getDeathMessage() {
        return this.deathMessage;
    }

    public void setDeathMessage(Component component) {
        this.deathMessage = component;
    }
}
