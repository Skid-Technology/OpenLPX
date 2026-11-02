package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.player.Combat;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;
import it.ytnoos.lpx.lib.kyori.adventure.text.Component;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerCombatEvent extends PacketWrapper<WrapperPlayServerCombatEvent> {

    private Combat combat;

    private int duration;

    private int entityId;

    private int playerId;

    @Nullable private Component deathMessage;

    public WrapperPlayServerCombatEvent(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerCombatEvent(Combat combat2) {
        super(PacketType.Play.Server.COMBAT_EVENT);
        this.combat = combat2;
    }

    public WrapperPlayServerCombatEvent(Combat combat2, int duration, int entityId) {
        super(PacketType.Play.Server.COMBAT_EVENT);
        this.combat = combat2;
        this.duration = duration;
        this.entityId = entityId;
    }

    public WrapperPlayServerCombatEvent(
            Combat combat2, int playerId, int entityId, @Nullable Component component) {
        super(PacketType.Play.Server.COMBAT_EVENT);
        this.combat = combat2;
        this.playerId = playerId;
        this.entityId = entityId;
        this.deathMessage = component;
    }

    @Override
    public void read() {
        this.combat = Combat.getById(this.readVarInt());
        switch (this.combat) {
            case END_COMBAT:
                this.duration = this.readVarInt();
                this.entityId = this.readInt();
                break;
            case ENTITY_DEAD:
                this.playerId = this.readVarInt();
                this.entityId = this.readInt();
                this.deathMessage = this.readComponent();
            case ENTER_COMBAT:
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.combat.getId());
        switch (this.combat) {
            case END_COMBAT:
                this.writeVarInt(this.duration);
                this.writeInt(this.entityId);
                break;
            case ENTITY_DEAD:
                this.writeVarInt(this.playerId);
                this.writeInt(this.entityId);
                this.writeComponent(this.deathMessage);
            case ENTER_COMBAT:
        }
    }

    public void copy(WrapperPlayServerCombatEvent wrapperPlayServerCombatEvent) {
        this.combat = wrapperPlayServerCombatEvent.combat;
        this.duration = wrapperPlayServerCombatEvent.duration;
        this.entityId = wrapperPlayServerCombatEvent.entityId;
        this.playerId = wrapperPlayServerCombatEvent.playerId;
        this.deathMessage = wrapperPlayServerCombatEvent.deathMessage;
    }

    public Combat getCombat() {
        return this.combat;
    }

    public void setCombat(Combat combat2) {
        this.combat = combat2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Optional<Component> getDeathMessage() {
        return Optional.ofNullable(this.deathMessage);
    }

    public void setDeathMessage(@Nullable Component component) {
        this.deathMessage = component;
    }
}
