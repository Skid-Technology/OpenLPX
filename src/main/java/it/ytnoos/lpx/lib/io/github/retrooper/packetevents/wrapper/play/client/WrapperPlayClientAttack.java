package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.client;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketReceiveEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class WrapperPlayClientAttack extends PacketWrapper<WrapperPlayClientAttack> {

    private int entityId;

    public WrapperPlayClientAttack(PacketReceiveEvent packetReceiveEvent) {
        super(packetReceiveEvent);
    }

    public WrapperPlayClientAttack(int entityId) {
        super(PacketType.Play.Client.ATTACK);
        this.entityId = entityId;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
    }

    public void copy(WrapperPlayClientAttack wrapperPlayClientAttack) {
        this.entityId = wrapperPlayClientAttack.entityId;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
}
