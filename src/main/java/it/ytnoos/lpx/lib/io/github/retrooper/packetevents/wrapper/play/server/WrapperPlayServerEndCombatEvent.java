package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.manager.server.ServerVersion;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WrapperPlayServerEndCombatEvent
        extends PacketWrapper<WrapperPlayServerEndCombatEvent> {

    private int duration;

    private Integer entityId;

    public WrapperPlayServerEndCombatEvent(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerEndCombatEvent(int duration, @Nullable Integer entityId) {
        super(PacketType.Play.Server.END_COMBAT_EVENT);
        this.duration = duration;
        this.entityId = entityId;
    }

    @Override
    public void read() {
        this.duration = this.readVarInt();
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            this.entityId = this.readInt();
        }
    }

    @Override
    public void write() {
        this.writeVarInt(this.duration);
        if (this.serverVersion.isOlderThanOrEquals(ServerVersion.V_1_19_4)) {
            int value = this.entityId != null ? this.entityId : 0;
            this.writeInt(value);
        }
    }

    public void copy(WrapperPlayServerEndCombatEvent wrapperPlayServerEndCombatEvent) {
        this.duration = wrapperPlayServerEndCombatEvent.duration;
        this.entityId = wrapperPlayServerEndCombatEvent.entityId;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Optional<Integer> getEntityId() {
        return Optional.ofNullable(this.entityId);
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
