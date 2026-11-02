package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.debug.DebugSubscription;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperPlayServerDebugEntityValue
        extends PacketWrapper<WrapperPlayServerDebugEntityValue> {

    private int entityId;

    private DebugSubscription.Update<?> update;

    public WrapperPlayServerDebugEntityValue(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerDebugEntityValue(int entityId, DebugSubscription.Update<?> update2) {
        super(PacketType.Play.Server.DEBUG_ENTITY_VALUE);
        this.entityId = entityId;
        this.update = update2;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.update = DebugSubscription.Update.read(this);
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        DebugSubscription.Update.write(this, this.update);
    }

    public void copy(WrapperPlayServerDebugEntityValue wrapperPlayServerDebugEntityValue) {
        this.entityId = wrapperPlayServerDebugEntityValue.entityId;
        this.update = wrapperPlayServerDebugEntityValue.update;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public DebugSubscription.Update<?> getUpdate() {
        return this.update;
    }

    public void setUpdate(DebugSubscription.Update<?> update2) {
        this.update = update2;
    }
}
