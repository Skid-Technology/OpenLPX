package it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.play.server;

import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.event.PacketSendEvent;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.protocol.packettype.PacketType;
import it.ytnoos.lpx.lib.io.github.retrooper.packetevents.wrapper.PacketWrapper;

public class WrapperPlayServerSetPassengers extends PacketWrapper<WrapperPlayServerSetPassengers> {

    private int entityId;

    private int[] passengers;

    public WrapperPlayServerSetPassengers(PacketSendEvent packetSendEvent) {
        super(packetSendEvent);
    }

    public WrapperPlayServerSetPassengers(int entityId, int[] passengers) {
        super(PacketType.Play.Server.SET_PASSENGERS);
        this.entityId = entityId;
        this.passengers = passengers;
    }

    @Override
    public void read() {
        this.entityId = this.readVarInt();
        this.passengers = this.readVarIntArray();
    }

    @Override
    public void write() {
        this.writeVarInt(this.entityId);
        this.writeVarIntArray(this.passengers);
    }

    public void copy(WrapperPlayServerSetPassengers wrapperPlayServerSetPassengers) {
        this.entityId = wrapperPlayServerSetPassengers.entityId;
        this.passengers = wrapperPlayServerSetPassengers.passengers;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int[] getPassengers() {
        return this.passengers;
    }

    public void setPassengers(int[] passengers) {
        this.passengers = passengers;
    }
}
